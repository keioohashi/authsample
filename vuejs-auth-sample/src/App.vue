<template>
  <div>
    <header class="header">
     <div class="headerNav">
      <nav>
        <!--ログイン画面の場合はメニューを表示しない -->
        <ul v-show="$route.path !== '/'" class="b">
           
          <!-- Vuexのロール情報を取得し、adminの場合のみユーザ一覧へのリンクを表示 -->
          <li v-show="store.getters.getRole=='admin'"><a href="#" @click.prevent.stop="trasPage('accountList')">ユーザ一覧</a></li>
          
          <!-- 商品一覧とログアウトは全てのユーザが閲覧可能 -->
          <li><a href="#" @click.prevent.stop="trasPage('goodsList')">商品一覧</a></li>
          <li><a href="#" @click.prevent.stop="logout">ログアウト</a></li>
        </ul>
      </nav>
      </div>
    </header>
   
    <!--Vue Router用のタグ、URLに対応した画面を表示 -->
    <router-view/>
    
  </div>
</template>

<script>

import { useStore } from 'vuex'
import { useRouter  } from 'vue-router'
import accountRepository from './composables/AccountRepository'

export default ({
  name: 'App',
  setup(){
  
     const router = useRouter()
     const store = useStore()
     const { logoutAccount } = accountRepository()
     
     //メニューのリンクが押下された時に呼ばれる
     //router.pushメソッドで画面遷移依頼
     const trasPage = ( page ) => {
      if( page == "goodsList"){
        router.push("/goodsList")
      }else if( page == "accountList"){
        router.push("/accountList")
      }
    }
    
    const callback = ()=>{
      router.push("/")
    }
    
    //ログアウト押下時の処理
    const logout = ()=>{
      logoutAccount( callback )
    }
  
    return{
      trasPage,
      store,
      logout
    }
  }
})
</script>

<style>

.header{
  display: flex;
  width: 100%;
  height:90px;
  padding: 0%;
  justify-content: space-between;
  background-color:#ff69b4;
  maring: 0px 0%;
}

.b{
  display: flex;
  font-size: 18px;
  text-transform: uppercase;
  margin-top: 30px;
  margin-right: 30px;
  list-style: none;
}
.b li{
  margin-left: 30px;
}
.b li a{
  color: #0000ff;
  text-decoration: none;
}

.headerNav{
  display: flex;
  width: 100%;
  height:90px;
  padding: 0%;
  justify-content: space-between;
   maring:0%;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>