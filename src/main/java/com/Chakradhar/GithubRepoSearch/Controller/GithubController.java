package com.Chakradhar.GithubRepoSearch.Controller;

import com.Chakradhar.GithubRepoSearch.dto.RepositoryDto;
import com.Chakradhar.GithubRepoSearch.dto.SearchRequestDto;
import com.Chakradhar.GithubRepoSearch.dto.SearchResponseDto;
import com.Chakradhar.GithubRepoSearch.Service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    @Autowired
    private GithubService githubService;

    @PostMapping("/search")
    public ResponseEntity<SearchResponseDto> search(@RequestBody SearchRequestDto request) {
        return ResponseEntity.ok(githubService.searchAndSave(request));
    }

    @GetMapping("/repositories")
    public ResponseEntity<Map<String, List<RepositoryDto>>> getRepositories(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer minStars,
            @RequestParam(required = false, defaultValue = "stars") String sort) {
        List<RepositoryDto> repos = githubService.getRepositories(language, minStars, sort);
        return ResponseEntity.ok(Map.of("repositories", repos));
    }
}