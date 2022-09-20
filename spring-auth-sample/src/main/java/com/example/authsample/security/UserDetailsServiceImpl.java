package com.example.authsample.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.example.authsample.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	Logger logger = org.slf4j.LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try{
        	System.out.println( "loadUserByUsername" );

            return accountRepository.findByUserId(userId).
            		map( a ->
            			new User( a.getUserid(),
            				PasswordEncoderFactories.createDelegatingPasswordEncoder().encode( a.getPassword() ),
            				Collections.singletonList( new SimpleGrantedAuthority( a.getRole()  ) )
            			)
            		).block();

        }catch (Exception e) {
        	e.printStackTrace();
            throw new UsernameNotFoundException("ユーザーが見つかりません");
        }
    }

}


