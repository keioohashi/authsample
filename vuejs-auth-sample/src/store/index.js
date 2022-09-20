import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'


export default createStore({
  state: {
    isLogin:false,
    authToken:'',
    role:''
  },
  mutations: {
    setAuthToken(state, value){
      state.authToken = value
    },
    setIsLogin(state, value){
      state.isLogin = value
    },
    setRole(state, value){
      state.role = value
    }
  },
  actions: {
    setAuthToken: function(commit, value) {
      commit('setAuthToken', value)
    },
    setIsLogin: function(commit, value) {
      commit('setIsLogin', value)
    },
    setRole: function(commit, value) {
      commit('setRole', value)
    }
  },
  getters:{
    getAuthToken(state){
      return state.authToken
    },
    getIsLogin(state){
      return state.isLogin
    },
    getRole(state){
      return state.role
    }
  },
  
  plugins: [createPersistedState(
    { 
      storage: window.sessionStorage
    }
  )]
  
})
