package com.wanted.preonboarding.config;

import io.micrometer.common.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    private static class StringToEnumConverter<T extends Enum> implements Converter<String, T> {

        private final Map<String, T> constantMap;

        public StringToEnumConverter(Class<T> enumType) {
            T[] enumConstants = enumType.getEnumConstants();
            constantMap = Arrays.stream(enumConstants)
                    .collect(Collectors.toMap(enumConstant -> enumConstant.name().toLowerCase(), Function.identity()));
        }

        @Override
        public T convert(String source) {
            if(StringUtils.isBlank(source)){
                return null;
            }
            return constantMap.get(source);

        }
    }

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }
}
