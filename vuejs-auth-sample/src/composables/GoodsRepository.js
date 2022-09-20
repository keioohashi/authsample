import store from '@/store/index';
import * as axios from 'axios'

export default function (){

  //���i���擾
  const getGoods = async( callback ) => {
    try{
      //Spring Boot��axios�ŏ��i�擾�˗�
      var token = store.getters.getAuthToken
      console.log( token )
      var goodsList = await requestServerGetWidthToken ( "api/goods/list", token)

      //�R�[���o�b�N�Ɍ��ʂ�Ԃ�
      callback( "success", goodsList )
    }catch(e){
      callback(e)
    }
  }

  //axios��Get���\�b�h�Ńg�[�N�����w�b�_�[�ɃZ�b�g��Spring Boot���փ��N�G�X�g���|����
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