package com.Chakradhar.GithubRepoSearch.dto;

import lombok.Data;

@Data
public class SearchRequestDto {
    public void setQuery(String query) {
		this.query = query;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	private String query;
    private String language;
    private String sort;
    
    public String getSort() {
        return sort;
    }
    
    public String getQuery() {
    	return query;
    }
    
    public String getLanguage() {
    	return language;
    }
}

