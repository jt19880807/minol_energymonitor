package com.minol.energymonitor.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minol.energymonitor.domain.entity.SysUser;
import com.minol.energymonitor.utils.JsonUtils;
import com.minol.energymonitor.utils.TokenAuthentication;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    //登录时需要验证调用
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        // JSON反序列化成 AccountCredentials
        String requestURI = httpServletRequest.getRequestURI();
        String queryString = httpServletRequest.getQueryString();
        // 从Header中拿到token
        String token = httpServletRequest.getHeader("Authorization");
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
//        ServletInputStream ss= httpServletRequest.getInputStream();
        SysUser creds = new ObjectMapper().readValue(httpServletRequest.getInputStream(), SysUser.class);
//         //返回一个验证令牌
//        return getAuthenticationManager().authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        creds.getUsername(),
//                        creds.getPassword()
//                )
//        );
        //返回一个验证令牌
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

    }
    //验证成功调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)throws IOException, ServletException {
        TokenAuthentication.addAuthentication(response, authResult.getName());
    }

    //验证失败调用
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        String msg=failed.getLocalizedMessage();
        response.getWriter().println(JsonUtils.fillResultString(500, msg, JSONObject.NULL));
    }
}
