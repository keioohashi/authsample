import { createRouter, createWebHistory } from 'vue-router'

import Login from '../views/Login.vue'
import AccountList from '../views/AccountList.vue'
import GoodsList from '../views/GoodsList.vue'
import AuthorityError from '../views/AuthorityError.vue'



import store from '@/store/index';

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login
  },
  {
    path: '/accountList',
    name: 'accountList',
    component: AccountList
  },
  {
    path: '/goodsList',
    name: 'goodsList',
    component: GoodsList
  },
  {
    path: '/authorityError',
    name: 'authorityError',
    component: AuthorityError
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});


router.beforeEach((to, from, next) => {
 
 console.log(from )
 console.log( to )
 
 //if( from != null && from.name == null ) return

 console.log( store.getters.getIsLogin )
 console.log( store.getters.getRole )
 
 if( to.path == "/"){
   next()
   return
 }
 
 if( !store.getters.getIsLogin ){
   router.push("/")
 }else{
   var role = store.getters.getRole
   if( role == "guest" && ( to.path == "/accountList" || to.path == "/sendMail" || to.path == "/registNotice") ){
     router.push("/authorityError")
   }else{
     next()
   }
 }
})



export default router



