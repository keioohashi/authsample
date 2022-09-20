package com.example.authsample.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {


    http.cors().configurationSource(this.createCorsConfigurationSource());

        // 認証
        http.authorizeRequests()
        		.antMatchers("/**").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/**").authenticated();
        // 独自フィルターの利用
        // デフォルトのAuthenticationManagerを利用する
        http.addFilter(new AuthenticationFilter(authenticationManager()));
        http.addFilterAfter(new CheckTokenFilter(), AuthenticationFilter.class);


        http.csrf().ignoringAntMatchers("/**");


        HttpStatusReturningLogoutSuccessHandler handler = new HttpStatusReturningLogoutSuccessHandler() {
    		@Override
    		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
    				Authentication authentication) throws IOException {
    			super.onLogoutSuccess(request, response, authentication);
    		}
        };


        http.logout()
        .logoutUrl("/logout")
        .invalidateHttpSession(true)
        .logoutSuccessHandler( handler );

    }


    private CorsConfigurationSource createCorsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        corsConfiguration.addExposedHeader("X-AUTH-TOKEN");
        corsConfiguration.addAllowedOrigin("http://localhost:8081");

        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", corsConfiguration);

        return corsSource;


    }

}
