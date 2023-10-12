package com.wanted.preonboarding.config;

import com.wanted.preonboarding.interceptor.AuthenicationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeList = new ArrayList<String>();
        excludeList.add("/login");
        excludeList.add("/member");
        registry
                .addInterceptor(new AuthenicationInterceptor())
                .addPathPatterns("/board/**")
                .excludePathPatterns(excludeList);

    }
}
