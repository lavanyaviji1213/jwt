package com.login.login.Config;

import com.login.login.Common.RequestMeta;
import com.login.login.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    private RequestMeta requestMeta;

    public JwtInterceptor(RequestMeta requestMeta) {
        this.requestMeta = requestMeta;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String auth = request.getHeader("authorization");

        if (!(request.getRequestURI().contains("login") || request.getRequestURI().contains("signup"))) {
            System.out.println("login  " + auth);
            Claims claims = jwtUtils.verify(auth);

            requestMeta.setUserName(claims.get("userName").toString());
            requestMeta.setUserId(Long.valueOf(claims.getIssuer()));
            requestMeta.setCompanyNo(claims.get("companyNo").toString());

        }
        return super.preHandle(request,response,handler);
    }
}