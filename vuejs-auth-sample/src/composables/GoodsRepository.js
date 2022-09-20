import store from '@/store/index';
import * as axios from 'axios'

export default function (){

  //商品情報取得
  const getGoods = async( callback ) => {
    try{
      //Spring Bootにaxiosで商品取得依頼
      var token = store.getters.getAuthToken
      console.log( token )
      var goodsList = await requestServerGetWidthToken ( "api/goods/list", token)

      //コールバックに結果を返す
      callback( "success", goodsList )
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
    getGoods
  }
}	