package com.Chakradhar.GithubRepoSearch.Service;

import com.Chakradhar.GithubRepoSearch.dto.SearchRequestDto;
import com.Chakradhar.GithubRepoSearch.dto.SearchResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GithubServiceTest {

    @Autowired
    private GithubService githubService;

    @Test
    void testSearchAndSave() {
        SearchRequestDto request = new SearchRequestDto();
        request.setQuery("spring boot");
        request.setLanguage("Java");
        request.setSort("stars");

        SearchResponseDto response = githubService.searchAndSave(request);
        assertNotNull(response.getRepositories());
        // Add more assertions, e.g., response.getRepositories().size() > 0;
    }

    // Add more tests for edge cases: empty query, invalid sort, etc.
}