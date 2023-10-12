package com.wanted.preonboarding.dto;

import io.micrometer.common.util.StringUtils;

public enum SearchType {
    TITLE("title","제목"),CONTENTS("contents","내용"),TITLEANDCONTENTS("titlecontents","제목&내용");

    private String value;
    private String valueName;

    SearchType(String value, String valueName) {
        this.value = value;
        this.valueName = valueName;
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
