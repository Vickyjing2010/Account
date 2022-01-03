package com.test.demo.configuration;

import com.test.demo.service.JWTManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    @Value("${request.url.whiteLists}")
    private String whiteList;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // get token from header authorization
        final String token = request.getHeader(JWTManager.AUTHORIZATION);
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        //check whether the request path is in whiteList, is in the list, no need to check authorization
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        if(isNoNeedAuth(path)) {
            return true;
        }
        if(token.isEmpty()) {
            log.error("token is empty, authorization failed!");
            return false;
        }
        JWTManager.validateToken(token);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        JWTCache.removeUserJWTToken();
    }

    private boolean isNoNeedAuth(String path) {
        boolean noNeedAuth = Arrays.asList(this.whiteList.split(",")).stream().anyMatch(url-> url.contains(path));
        return noNeedAuth;
    }
}
