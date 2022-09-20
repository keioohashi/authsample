<template>
  <div>
		<div class="inputItemDiv"><div style="color:red;">{{message}}</div></div>
		<div class="table" style="max-height:300px;width:900px;">
			<table>
				<thead>
					<tr>
						<th>アカウントID</th>
						<th>名前</th>
						<th>会社名</th>
						<th>部署</th>
					</tr>
				</thead>
				<tbody>
					<tr  v-for="(data) in accountList" :key="data.id">
						<td>{{ data.userid }}</td>
						<td>{{ data.name }}</td>
						<td>{{ data.companyname }}</td>
						<td>{{ data.department }}</td>

					</tr>
				</tbody>
			</table>
		</div>
		
	</div>

</template>

<script>
import { ref } from 'vue'
import accountRepository from '../composables/AccountRepository'

export default ({
  name: 'AccountList',
  setup(){
    //ユーザ情報リスト
    const accountList = ref([])

    //ユーザ取得結果メッセージ
    const message = ref(null)

    //accountRepositoryモジュールからgetAccountメソッドを取得
    const { getAccount } = accountRepository()
    

    
    //データ取得時のコールバック( AccountRepositoryのgetAccountメソッドに渡す)
    const callback = ( result, data )=>{
      //取得成功の場合はaccountListに取得データを設定
      if( result == "success"){
        message.value = "データ取得完了"
        accountList.value = data
      }else{
        message.value = result
      }
    }
    
    //ユーザ情報取得
    getAccount( callback )
    
    return{
      accountList,
      message
    }  
  }
})

</script>