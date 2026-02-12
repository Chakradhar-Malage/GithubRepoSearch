 package com.Chakradhar.GithubRepoSearch.Service;

import com.Chakradhar.GithubRepoSearch.dto.RepositoryDto;
import com.Chakradhar.GithubRepoSearch.dto.SearchRequestDto;
import com.Chakradhar.GithubRepoSearch.dto.SearchResponseDto;
import com.Chakradhar.GithubRepoSearch.entity.RepositoryEntity;
import com.Chakradhar.GithubRepoSearch.repository.GithubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.*;
import java.util.Map;


@Service
public class GithubService {

    @Autowired
    private GithubRepository githubRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public SearchResponseDto searchAndSave(SearchRequestDto request) {

        String url = UriComponentsBuilder
                .fromUriString("https://api.github.com/search/repositories")
                .queryParam("q", buildQuery(request))
                .queryParam("sort",
                    request.getSort() != null ? request.getSort() : "stars")
                .toUriString();

        System.out.println("GitHub API URL: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "GithubRepoSearch-App");
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response =
            restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("GitHub API error: " + response.getStatusCode());
        }

        Map<String, Object> body = response.getBody();

        System.out.println("Full body: " + body);

        List<Map<String, Object>> items =
            (List<Map<String, Object>>) body.get("items");

        if (items == null || items.isEmpty()) {
            return new SearchResponseDto("No repositories found", new ArrayList<>());
        }

        List<RepositoryDto> dtos = new ArrayList<>();

        for (Map<String, Object> item : items) {
            RepositoryEntity entityObj = mapToEntity(item);
            githubRepository.save(entityObj);
            dtos.add(mapToDto(entityObj));
        }

        return new SearchResponseDto("Success", dtos);
    }


    private String buildQuery(SearchRequestDto request) {

        if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
            throw new IllegalArgumentException("Query must not be empty");
        }

        // Convert spaces to +
        String query = request.getQuery().trim().replace(" ", "+");

        if (request.getLanguage() != null && !request.getLanguage().trim().isEmpty()) {
            query += "+language:" + request.getLanguage().trim().toLowerCase();
        }

        return query;
    }



    // Add your mapToEntity and mapToDto methods here (from previous guidance)
    private RepositoryEntity mapToEntity(Map item) {
        RepositoryEntity entity = new RepositoryEntity();
        entity.setId(((Number) item.get("id")).longValue());  // Handle as Number to avoid cast issues
        entity.setName((String) item.get("name"));
        entity.setDescription((String) item.get("description"));
        entity.setOwner((String) ((Map) item.get("owner")).get("login"));
        entity.setLanguage((String) item.get("language"));
        entity.setStars(((Number) item.get("stargazers_count")).intValue());
        entity.setForks(((Number) item.get("forks_count")).intValue());
        entity.setLastUpdated(java.time.Instant.parse((String) item.get("updated_at")));
        return entity;
    }

    private RepositoryDto mapToDto(RepositoryEntity entity) {
        RepositoryDto dto = new RepositoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setOwner(entity.getOwner());
        dto.setLanguage(entity.getLanguage());
        dto.setStars(entity.getStars());
        dto.setForks(entity.getForks());
        dto.setLastUpdated(entity.getLastUpdated());
        return dto;
    }

    // Add the getRepositories method if not already present (from previous guidance)
    public List<RepositoryDto> getRepositories(String language, Integer minStars, String sort) {
        String effectiveSort = (sort == null) ? "stars" : sort;
        List<RepositoryEntity> entities =
        	    githubRepository.findFiltered(language, minStars, effectiveSort);
	        if (language != null) {
	            language = language.trim();
	        }
        	// Sorting
        	if ("forks".equalsIgnoreCase(effectiveSort)) {
        	    entities.sort(Comparator.comparing(RepositoryEntity::getForks).reversed());
        	}
        	else if ("updated".equalsIgnoreCase(effectiveSort)) {
        	    entities.sort(Comparator.comparing(RepositoryEntity::getLastUpdated).reversed());
        	}
        	else { // default = stars
        	    entities.sort(Comparator.comparing(RepositoryEntity::getStars).reversed());
        	}

        return entities.stream().map(this::mapToDto).toList();
    }
}