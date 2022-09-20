package com.example.authsample.security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {

	    // AuthenticationManagerの設定
	    this.authenticationManager = authenticationManager;

	    // ログインパスを設定
	    setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));



	    // ログイン成功時はtokenを発行してレスポンスにセットする
	    this.setAuthenticationSuccessHandler((req,res,ex) -> {
	        // トークンの作成
	    	System.out.println( "auth success" );
	        String token = JWT.create()
	                .withIssuer("distant-view") //発行者
	                .withIssuedAt( new Date() )
	                .withClaim("username", ex.getName()) //keyに対してvalueの設定。汎用的な様々な値を保持できる
	                .withClaim("role", ex.getAuthorities().iterator().next().toString())
	                .sign(Algorithm.HMAC256("secret")); // 利用アルゴリズムを指定してJWTを新規作成
	        System.out.println( token );
	        res.setHeader("X-AUTH-TOKEN", token); // tokeをX-AUTH-TOKENというKeyにセットする
	        res.setStatus(200);

	        System.out.println( SecurityContextHolder.getContext().getAuthentication().getAuthorities() );
	        res.getWriter().write("Success@" + SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next() );
	    });

	    // ログイン失敗時
	    this.setAuthenticationFailureHandler((req,res,ex) -> {

	        res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    });
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
	    try {
	        ServletInputStream stream = request.getInputStream();
	        // リクエストのjsonの値をUserFormにマッピングします。
	        UserForm form = new ObjectMapper().readValue(request.getInputStream(), UserForm.class);
	        // これでデフォルトのProviderを利用しつつ、ユーザーレコードの取得に関してはUserDetailsServiceの実装クラスのloadUserByUsernameを利用する
	        return this.authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(form.getUserid(), form.getPassword(), new ArrayList<>())
	        );
	    } catch (IOException e) {
	    	e.printStackTrace();
	        throw new RuntimeException(e);
	    }
	}
}


@Getter
@Setter
class UserForm {
    private String userid;
    private String password;
}
