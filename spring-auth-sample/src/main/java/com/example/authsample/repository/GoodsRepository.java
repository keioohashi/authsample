package com.example.authsample.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.authsample.entity.Goods;

public interface GoodsRepository extends ReactiveCrudRepository<Goods, Integer> {

}
