package com.wanted.preonboarding.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchBoardForm {

    private String keyword;

    @Enumerated(value = EnumType.STRING)
    private SearchType searchType;


    private Integer page = 1;

    private SearchBoardForm(String keyword, SearchType searchType, Integer page) {
        this.keyword = keyword;
        this.searchType = searchType;
        this.page = page == null ? 1 : page;
    }

    static public SearchBoardForm createForm(String keyword, SearchType searchType,int page) {
        return new SearchBoardForm(keyword,searchType,page);
    }
}
