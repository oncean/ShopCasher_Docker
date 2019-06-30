<style lang="scss" scoped>
  .el-header{
    background-color: #fff;
    padding: 0 10%;
  }

</style>
<template>
  <el-container style="height: 100%">
    <el-header>
      <Head></Head>
    </el-header>
    <el-container>
      <!--<el-aside width="300px"><LeftMenu></LeftMenu></el-aside>-->
      <router-view style="width: 95%;margin: 20px auto 0;"></router-view>
    </el-container>
  </el-container>
</template>

<script>
import Head from './Head.vue'
import LeftMenu from './LeftMenu.vue'
import shopService from '../common/service/shopService'
import socket from '../common/js/socket'

export default {
  components: {'Head': Head, 'LeftMenu': LeftMenu},
  data () {
    return {
    }
  },
  created: function () {
    shopService.initCatogery()
    this.initMyShops()
    // 接受商店切换事件，重新订阅扫码事件
    this.$bus.$on(this.busConstants.CHANGE_SHOP, shop => {
      let shopId = shop.shopInfo.shopId
      this.$http.get('/scan/getShopConnectCode/' + shopId).then(data => {
        this.subscribe(data.result)
      })
    })
  },
  beforeRouteLeave (to, from, next) {
    this.$bus.$off(this.busConstants.CHANGE_SHOP)
    next()
  },
  methods: {
    initMyShops () {
      this.$http.get('/shop/getMyShops').then(data => {
        this.shops = []
        if (data.total > 0) {
          for (var i in data.result) {
            var relationNames = ''
            for (var j in data.result[i].relations) {
              relationNames += this.Constants.JobTitle[data.result[i].relations[j].relationType]
              if (j < data.result[i].relations.length - 1) {
                relationNames += ','
              }
            }
            data.result[i].relationNames = relationNames
          }
          this.shops = data.result
          if (this.shops.length > 0) {
            // 如果传递了商店ID，则显示商店主页，否则显示商店列表
            if (this.$route.params.shopId) {
              this.current = this.$lodash.find(this.shops, shop => {
                return shop.shopInfo.shopId === this.$route.params.shopId
              })
              this.$bus.$emit(this.busConstants.CHANGE_SHOP, this.current)
            }
          }
        }
      })
    },
    subscribe (connectCode) {
      socket.subcribe(connectCode, code => {
        this.$bus.$emit(this.busConstants.RECEIPT_SCAN_CODE, code) // 广播客户端扫码事件
      })
    }
  }
}
</script>
