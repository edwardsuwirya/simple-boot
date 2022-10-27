package com.enigmacamp.hellospring.controller.interceptor;

import com.enigmacamp.hellospring.exception.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("login")) {
            return true;
        }

        Optional token = Optional.ofNullable(request.getHeader("token"));
        if (token.isEmpty()) {
            throw new UnauthorizedException("Not a valid user");
        }

        return true;
    }
}
