<style lang="scss">
  .el-menu{
    border-bottom: none;
    display: inline-block;
  }
  .el-menu /deep/ a{
    text-decoration: none;
  }
  .header-hole{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
  .header-hole /deep/ .el-menu-item-withouthover{
    background-color: transparent;
  }
  .logo-font{
    font-weight: 700;
    color: $mainColor;
  }
  .el-submenu .el-menu{
    width: 100%;
  }
</style>
<template>
  <div class="header-hole">
    <div @click="openPage('home')" class="el-menu-item el-menu-item-withouthover logo-font">云收银</div>
    <div style="width: 80%;text-align: center">
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" v-if="shop.shopInfo.shopId">
        <el-menu-item index="1" @click="openPage('home')">店铺主页</el-menu-item>
        <el-menu-item index="2" @click="openPage('checkout')">收银台</el-menu-item>
        <el-submenu index="3">
          <template slot="title">商品管理</template>
          <el-menu-item index="3-1" @click="openPage('goodManage')">商品列表</el-menu-item>
          <el-menu-item index="3-2" @click="openPage('addGood')">录入商品</el-menu-item>
        </el-submenu>
        <el-menu-item index="4" @click="openPage('orderManage')">订单管理</el-menu-item>
        <el-menu-item index="5" @click="openPage('settlement')">收支明细</el-menu-item>
      </el-menu>
    </div>
    <div>
      <el-menu class="el-menu-demo" mode="horizontal">
        <el-submenu index="1">
          <template slot="title">我</template>
          <el-menu-item index="1-1" @click="openPage('my')">我的信息</el-menu-item>
          <el-menu-item index="1-2" @click="quit()">退出登录</el-menu-item>
        </el-submenu>
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
