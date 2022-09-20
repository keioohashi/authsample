<template>
	<div>
		<div class="inputItemDiv"><div style="color:red;">{{message}}</div></div>
		<div class="inputItemDiv"><div class="inputTitleDiv">ID</div><input class="input" type="text" v-model="loginForm.userid"></div>

		<div class="inputItemDiv"><div class="inputTitleDiv">パスワード</div><input class="input" type="password" v-model="loginForm.password"></div>
	
		<div class="inputItemDiv" style="text-align:right">
			<button  type="button" class="button" @click='cancel'>ｷｬﾝｾﾙ</button>
			<button type="button" class="button" @click='login'>ログイン</button>
		</div>
	</div>

</template>

<script>
//アカウント情報をサーバと通信して取得するモジュール
import accountRepository from '../composables/AccountRepository'
//ルート情報を保持するモジュール
import { useRouter  } from 'vue-router'

import {ref} from 'vue';


export default ({
  name: 'Login',
  setup(){
    //画面のuserid、passwordとバインドさせる変数
    const loginForm = ref( {userid:'', password:''} )
   
    //ログイン結果のメッセージ表示用
    const message = ref(null)
    
    //AccountRepositoryモジュールからの変数、メソッドを宣言
    const { loginAccount } = accountRepository()


    //ルータ使用変数宣言
    const router = useRouter()
    
    //ログインメソッド、ログインボタン押下時に呼ばれる
    const login = () =>{
      //loginAccountメソッドを呼ぶ、loginAccountは非同期メソッドなので、
      //処理終了時に呼ばれるコールバックメソッドを渡す
      loginAccount( loginForm.value, callback )
    }
    
    //キャンセルメソッド、キャンセルボタン押下時に呼ばれる
    const cancel = () =>{
      loginForm.value.userid = ''
      loginForm.value.password = '' 
    }
    
    //データ取得時のコールバック( AccountRepositoryのloginAccount メソッドに渡す)
    const callback = ( result ) =>{
      //処理が成功しならユーザリスト画面に遷移
      if( result == "Success"){
        router.push("/goodsList")
      }else{
         var data = result.response.data
        //ステータス401の場合は下記のエラーを出力し終了
        if( data.status == 401 ){
          message.value = "ユーザID又はパスワードが間違っています。"
        }else{
          //それ以外の場合は、エラーを出力し終了
          message.value = data.error
        }
      }
    }
    
    //template上で使用する変数を宣言
    return{
      loginForm,
      login,
      cancel,
      message
    }
  }

})
</script>