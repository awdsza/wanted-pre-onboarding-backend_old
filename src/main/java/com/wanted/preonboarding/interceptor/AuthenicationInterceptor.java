package com.wanted.preonboarding.interceptor;

import com.wanted.preonboarding.dto.ResponseDto;
import com.wanted.preonboarding.dto.UserDto;
import com.wanted.preonboarding.exception.InvalidLoginAccessException;
import com.wanted.preonboarding.exception.InvalidUserDifferentException;
import com.wanted.preonboarding.exception.TokenExpiredException;
import com.wanted.preonboarding.util.JWTTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Component
public class AuthenicationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.isBlank(token)) {
            throw new InvalidLoginAccessException();
        }
        UserDto userDto = UserDto.createUserDto(String.valueOf(JWTTokenUtil.getValue(token, "email")));
        request.setAttribute("user",userDto);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
