import store from '@/store/index';
import * as axios from 'axios'

export default function (){
  //���O�C�����\�b�h
  const loginAccount = async( loginForm, callback ) => {
    try{
      //Spring Boot��axios�Ń��O�C���˗�
      var result = await requestServerPost ( "api/login", loginForm )
      var results = result .split('@')

      //���O�C�������̏ꍇ�̓g�[�N���A���O�C�����A������vuex�Ɋi�[
      if( results[0] == "Success"){
        store.commit("setIsLogin", true)
        store.commit("setRole", results[1])
        callback( "Success" )
        return
      }

      //�R�[���o�b�N�Ɍ��ʂ�Ԃ�
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


  //axios�̃|�X�g���\�b�h��Spring Boot���փ��N�G�X�g���|����
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

  //���[�U���擾
  const getAccount = async( callback ) => {
    try{
      //Spring Boot��axios�Ń��[�U�擾�˗�
      var token = store.getters.getAuthToken
      console.log( token )
      var accountList = await requestServerGetWidthToken ( "api/account/list", token)

      //�R�[���o�b�N�Ɍ��ʂ�Ԃ�
      callback( "success", accountList )
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
    loginAccount,
    getAccount,
    logoutAccount
  }
}	