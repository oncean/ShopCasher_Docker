<style lang="scss" scoped>
  .el-menu{
    border-bottom: none;
    display: block;
  }
  .el-menu /deep/ a{
    text-decoration: none;
  }
  .logo-font{
    font-weight: 700;
    color: $mainColor;
  }
  .left_menu{
    background-color: $mainColor;
    height: 100%;
    .shop_info{
      padding: 50px 0;
      text-align: center;
      background-color: #ffffff;
    }
    .menu_items{
      width: 100%;
      text-align: center;
      font-weight: 700;
      .el-menu-item{
        text-align: left;
        i{
          color: #ffffff;
          padding-left: 30px;
        }
      }
      .el-submenu{
        text-align: left;
        .el-menu-item{
          text-align: center;
        }
        i{
          color: #ffffff;
          padding-left: 30px;
        }
      }
    }
  }
</style>
<template>
  <div class="left_menu">
    <div class="shop_info">
      <div><img src="../assets/shop.png"/></div>
      <div class="logo-font"> {{shop.shopInfo.shopName}}</div>
    </div>
    <div class="menu_items">
      <el-menu
      default-active="1"
      background-color="#10ba71"
      text-color="#fff"
      active-text-color="#ffd04b">
        <el-menu-item index="1" @click="openPage('home')">
          <i class="el-icon-menu"></i>
          <span slot="title">店铺主页</span>
        </el-menu-item>
        <el-menu-item index="2" @click="openPage('checkout')">
          <i class="el-icon-menu"></i>
          <span slot="title">收银台</span>
        </el-menu-item>
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>商品管理</span>
          </template>
          <el-menu-item index="3-1" @click="openPage('goodManage')">商品列表</el-menu-item>
          <el-menu-item index="3-2" @click="openPage('addGood')">录入商品</el-menu-item>
        </el-submenu>
        <el-menu-item index="4" @click="openPage('orderManage')">
          <i class="el-icon-menu"></i>
          <span slot="title">订单管理</span>
        </el-menu-item>
        <el-menu-item index="5" @click="openPage('settlement')">
          <i class="el-icon-menu"></i>
          <span slot="title">收支明细</span>
        </el-menu-item>
    </el-menu>
    </div>
  </div>
</template>
<script>
import userService from '../common/service/userService'
export default {
  data () {
    return {
      activeIndex: '1',
      shop: {
        shopInfo: {
          shopId: null,
          shopName: '创建店铺吧'
        }
      },
      showHomeSecond: true // 是否显示主页的二级菜单
    }
  },
  created: function () {
    this.watchShopChange()
  },
  beforeRouteLeave (to, from, next) {
    this.$bus.$off(this.busConstants.CHANGE_SHOP)
    next()
  },
  watch: {
    $route: { // 监听路由
      handler: function (val, oldVal) {
        // 获取路由参数
        let path = this.$route.path
        if (this.$lodash.startsWith(path, '/home')) {
          this.showHomeSecond = false
        } else {
          this.showHomeSecond = true
        }
      },
      // 深度观察监听
      deep: true
    }
  },
  methods: {
    watchShopChange () { // 商店变更事件
      this.$bus.$on(this.busConstants.CHANGE_SHOP, shop => {
        this.shop = shop
      })
    },
    openPage (name) {
      let shopId = this.shop.shopInfo.shopId
      if (!shopId) {
        return
      }
      switch (name) {
        case 'home':
          this.$router.push({ path: '/home/' + shopId })
          break
        case 'checkout':
          this.$router.push({ path: '/checkout/' + shopId })
          break
        case 'addGood':
          this.$router.push({ path: '/addGood/' + shopId })
          break
        case 'goodManage':
          this.$router.push({ path: '/goodManage/' + shopId })
          break
        case 'orderManage':
          this.$router.push({ path: '/orderManage/' + shopId })
          break
        case 'settlement':
          this.$router.push({ path: '/settlement/' + shopId })
          break
      }
    },
    quit () {
      userService.loginOut()
      this.$router.push({// 你需要接受路由的参数再跳转
        path: '/login'
      })
    }
  }
}
</script>
