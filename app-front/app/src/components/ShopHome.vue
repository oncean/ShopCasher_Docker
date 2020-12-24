<style lang="scss" scoped>
  .el-row {
    margin-bottom: 5px;
    &:last-child {
      margin-bottom: 0;
    }
    .el-col {
      &:first-child {
        color: #80848f;
      }
    }
  }
  .bottom-div{
    display: flex;
    justify-content: center;
    i{
      font-size: 30px;
    }
    .icon-div{
      text-align: center;
      margin-left: 30px;
      cursor: pointer;
      :last-child{
        margin-top: 5px;
      }
    }
    :first-child{
      margin-left: 0;
    }
  }
  .main-div{
    text-align: center;
    .el-card{
      height: 400px;
      width: 50%;
      margin: auto;
    }
  }
  .settlemng-div{
    background-color: $mainColor;
    color: #fff;
    padding: 32px;
    margin: auto;
    width: 70%;
    border-radius: 5px;
  }
  .home-div{
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }
</style>
<template>
  <div>
    <!--<el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>店铺</el-breadcrumb-item>
    </el-breadcrumb>-->
    <div class="main-div">
      <el-card id="loadingMainPanel" :body-style="{height: '100%'}">
        <div class="home-div">
          <div class="wang-large-title">
            <span>{{shopInfo.shopName}}</span>
          </div>
          <div class="settlemng-div">
            <div>今日营业额</div>
            <div style="font-size:1.5rem;font-weight:900;">{{today.sales | RMB}}</div>
            <div>{{today.orderNum}}笔</div>
          </div>
          <div class="bottom-div">
            <div class="icon-div" @click="openPage('checkout')">
              <div><i class="el-icon-wang-iconset0315"></i></div>
              <div>收银台</div>
            </div>
            <div class="icon-div" @click="openPage('addGood')">
              <div><i class="el-icon-wang-chuangjianketang"></i></div>
              <div>录入商品</div>
            </div>
            <div class="icon-div" @click="openPage('goodManage')">
              <div><i class="el-icon-wang-shangpinguanli"></i></div>
              <div>商品管理</div>
            </div>
            <div class="icon-div" @click="openPage('orderManage')">
              <div><i class="el-icon-wang-74wodedingdan"></i></div>
              <div>订单管理</div>
            </div>
            <div class="icon-div" @click="openPage('settlement')">
              <div><i class="el-icon-wang-chaxun"></i></div>
              <div>收支明细</div>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      shopId: '',
      shopName: '',
      shopInfo: {},
      dialogVisible: false,
      today: {}
    }
  },
  created: function () {
    this.init()
  },
  methods: {
    init () {
      this.shopId = this.$route.params.shopId
      this.$http.get('/shop/get/' + this.shopId).then(data => {
        this.shopInfo = data.result
      })
      this.$http.get('/settlement/getToday/' + this.shopId).then(data => {
        this.today = data.result
      })
    },
    openCreateShop () {
      // this.dialogVisible = true
    },
    openPage (name) {
      switch (name) {
        case 'checkout':
          this.$router.push({ path: '/checkout/' + this.shopId })
          break
        case 'addGood':
          this.$router.push({ path: '/addGood/' + this.shopId })
          break
        case 'goodManage':
          this.$router.push({ path: '/goodManage/' + this.shopId })
          break
        case 'orderManage':
          this.$router.push({ path: '/orderManage/' + this.shopId })
          break
        case 'settlement':
          this.$router.push({ path: '/settlement/' + this.shopId })
          break
      }
    }
  }
}
</script>
