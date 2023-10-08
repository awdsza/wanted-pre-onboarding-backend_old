package com.wanted.preonboarding.dto;

import io.micrometer.common.util.StringUtils;

public enum SearchType {
    TITLE("title"),CONTENTS("contents"),TITLEANDCONTENTS("titlecontents");

    private String value;

    SearchType(String value) {
        this.value = value;
    }

    public String getKey(){
        return name();
    }
    public static SearchType getValue(String code){
        if(StringUtils.isBlank(code)){
            throw new IllegalArgumentException();
        }
        SearchType[] values = SearchType.values();
        for(SearchType t : values){
            if(t.getValue().equals(code)){
                return t;
            }
        }
        throw new IllegalArgumentException("맞는 검색 타입이 없습니다.");
    }
    public String getValue() {
        return value;
    }
}
