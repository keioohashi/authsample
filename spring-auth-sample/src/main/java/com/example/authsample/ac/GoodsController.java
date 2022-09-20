package com.example.authsample.ac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.authsample.entity.Goods;
import com.example.authsample.repository.GoodsRepository;

import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/goods")
@CrossOrigin
public class GoodsController {


	@Autowired
	private GoodsRepository goodsRepository;

	/**
	* 商品リストを取得
	* @return
	*/
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Flux<Goods> getUserList() throws Exception{

		return this.goodsRepository.findAll();

	}
}
