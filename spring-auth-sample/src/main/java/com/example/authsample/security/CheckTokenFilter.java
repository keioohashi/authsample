package com.example.authsample.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class CheckTokenFilter extends OncePerRequestFilter  {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    	String s =request.getRequestURI();

        // headerからTokenを取得する
        String token = request.getHeader("X-AUTH-TOKEN");

        //　チェック処理
        if(token == null ){
            filterChain.doFilter(request,response);
            System.out.println("Check Fail");
            return;
        }
        // Tokenの検証と認証を行う
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("secret")).build().verify(token);
        // usernameの取得
        String username = decodedJWT.getClaim("username").toString();

        String role = decodedJWT.getClaim("role").toString();

        System.out.println("username:" + username );
        System.out.println("role:" + role );

        // ログイン状態を設定する
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,null,Collections.singletonList(new SimpleGrantedAuthority( role ) ) ) );
        System.out.println("Check Success");
        filterChain.doFilter(request,response);
    }
}
