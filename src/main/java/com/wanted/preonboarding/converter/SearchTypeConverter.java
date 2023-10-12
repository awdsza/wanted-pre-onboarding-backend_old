package com.wanted.preonboarding.converter;

import com.wanted.preonboarding.dto.SearchType;
import org.springframework.core.convert.converter.Converter;

public class SearchTypeConverter implements Converter<String, SearchType> {
    @Override
    public SearchType convert(String source) {
        return SearchType.getValue(source);
    }
}
