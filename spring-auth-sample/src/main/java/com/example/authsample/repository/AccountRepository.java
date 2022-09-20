package com.example.authsample.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.authsample.entity.Account;

import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveCrudRepository<Account, Integer> {
    @Query("select * from account where userid = :userId")
    Mono<Account> findByUserId(String userId);
}
