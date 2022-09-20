<template>
  <div>
		<div class="inputItemDiv"><div style="color:red;">{{message}}</div></div>
		<div class="table" style="max-height:300px;width:900px;">
			<table>
				<thead>
					<tr>
						<th>コード</th>
						<th>名前</th>
						<th>カテゴリ</th>
						<th>価格</th>
					</tr>
				</thead>
				<tbody>
					<tr  v-for="(data) in goodsList" :key="data.id">
						<td>{{ data.code }}</td>
						<td>{{ data.name }}</td>
						<td>{{ data.category }}</td>
						<td>{{ data.price }}</td>
					</tr>
				</tbody>
			</table>
		</div>
		
	</div>

</template>

<script>
import { ref } from 'vue'
import goodsRepository from '../composables/GoodsRepository'

export default ({
  name: 'GoodsList',
  setup(){
    //商品情報リスト
    const goodsList = ref([])

    //商品取得結果メッセージ
    const message = ref(null)

    //goodsRepositoryモジュールからgetGoodsメソッドを取得
    const { getGoods } = goodsRepository()
    
    //データ取得時のコールバック( GoodsRepositoryのgetGoodsメソッドに渡す)
    const callback = ( result, data )=>{
      //取得成功の場合はgoodsListに取得データを設定
      if( result == "success"){
        message.value = "データ取得完了"
        goodsList.value = data
      }else{
        message.value = result
      }
    }
    
    //ユーザ情報取得
    getGoods( callback )
    

    
    return{
      goodsList,
      message
    }  
  }
})

</script>