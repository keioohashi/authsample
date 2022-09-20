package com.example.authsample.ac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.authsample.entity.Account;
import com.example.authsample.repository.AccountRepository;

import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	/**
	* ユーザリストを取得
	* @return
	*/
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Flux<Account> getUserList() throws Exception{

		//権限がadmin以外からの受付を拒否する処理
		//セッションから権限情報を取得
		String role = ( String )SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
		//ダブルクォート削除処理
		role = role.replaceAll("\"", "");

    //	権限がadminでない場合は例外を発生
		if( !role.equals("admin")) {
			throw new RoleAuthorityException("アクセス権限がありません。");
		}
		return this.accountRepository.findAll();
	}
}
