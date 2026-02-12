package com.Chakradhar.GithubRepoSearch.dto;

import lombok.Data;
import java.util.List;

@Data
public class SearchResponseDto {
    private String message;
    private List<RepositoryDto> repositories;

    public SearchResponseDto(String message, List<RepositoryDto> repositories) {
        this.message = message;
        this.repositories = repositories;
    }
    
    public List<RepositoryDto> getRepositories(){
    	return repositories;
    }
}
