import store from '@/store/index';
import * as axios from 'axios'

export default function (){
  //ログインメソッド
  const loginAccount = async( loginForm, callback ) => {
    try{
      //Spring Bootにaxiosでログイン依頼
      var result = await requestServerPost ( "api/login", loginForm )
      var results = result .split('@')

      //ログイン成功の場合はトークン、ログイン情報、権限をvuexに格納
      if( results[0] == "Success"){
        store.commit("setIsLogin", true)
        store.commit("setRole", results[1])
        callback( "Success" )
        return
      }

      //コールバックに結果を返す
      callback( result )
    }catch(e){
      callback(e)
    }
  }


  const logoutAccount  = async( callback ) => {
    try{ 
      await requestServerPost("logout")
      
      store.commit("setIsLogin", false)
      store.commit("setRole", "")
      store.commit("setAuthToken", "")
      
      callback("success")
    }catch(e){
      callback(e)
    }
  
  }


  //axiosのポストメソッドでSpring Boot側へリクエストを掛ける
  const requestServerPost = ( url, formData ) =>{
    return new Promise((resolve, reject) => {
      axios
        .post('http://localhost:8080/' + url, formData)
        .then(response => {
           if( response.headers['x-auth-token'] != null ){
             store.commit("setAuthToken", response.headers['x-auth-token'])
           }
           resolve(response.data)
         }).catch(error => {
            reject(error)
         })
    }).catch((e) => {
      throw e
    })
  }

  //ユーザ情報取得
  const getAccount = async( callback ) => {
    try{
      //Spring Bootにaxiosでユーザ取得依頼
      var token = store.getters.getAuthToken
      console.log( token )
      var accountList = await requestServerGetWidthToken ( "api/account/list", token)

      //コールバックに結果を返す
      callback( "success", accountList )
    }catch(e){
      callback(e)
    }
  }

  //axiosのGetメソッドでトークンをヘッダーにセットしSpring Boot側へリクエストを掛ける
  const requestServerGetWidthToken = ( url, token ) =>{
    return new Promise((resolve, reject) => {
      axios
        .get('http://localhost:8080/' + url
          ,{
              headers: {
                "X-AUTH-TOKEN" : token
              }
          } 
         )
        .then(response => {
           resolve(response.data)
         }).catch(error => {
            reject(error)
         })
    }).catch((e) => {
      throw e
    })
  }

  return{
    loginAccount,
    getAccount,
    logoutAccount
  }
}	