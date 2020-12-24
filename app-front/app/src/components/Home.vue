<style lang="scss">
  .box-card {
    cursor: pointer;
    margin: 5px;
    width: 200px;
    height: 250px;
  }
  .el-row {
    margin-bottom: 5px;
    &:last-child {
      margin-bottom: 0;
    }
    .el-col {
      &:first-child {
        color:#80848f;
      }
    }
  }
  .home-div{
    text-align: center;
    display: flex;
    height: 100%;
    flex-direction: column;
    justify-content: space-around;
    background-color: #ffffff;
    padding: 20px;
    .settlemng-div{
      background-color: $mainColor;
      color: #fff;
      padding: 32px;
      margin: auto;
      width: 70%;
      border-radius: 5px;
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
  }
  .create_shop_div{
    padding-top: 20px;text-align: center;font-size: 15px;
  }
</style>
<template>
  <div>
    <div v-if="shops.length > 0">
      <transition enter-active-class="animated pulse">
        <div class="home-div" v-if="showCurrent && current.shopInfo" style="height: 400px">
          <div class="wang-large-title">
            <span>{{current.shopInfo.shopName}}</span>
          </div>
          <div style="padding: 2px;color: #909399;font-size: 12px;">
            <span>{{current.shopInfo.location}}</span>
          </div>
          <div class="settlemng-div">
            <div>今日营业额</div>
            <div style="font-size:1.5rem;font-weight:900;">{{current.today.sales | RMB}}</div>
            <div>{{current.today.orderNum}}笔</div>
          </div>
        </div>
      </transition>
      <transition enter-active-class="animated pulse">
        <div style="padding-top: 20px;" v-if="!showCurrent">
          <el-carousel type="card" height="400px"
                       trigger="click"
                       :autoplay="autoplay"
                       @change="changeShop">
            <el-carousel-item v-for="item in shops" :key="item.shopId">
              <div class="home-div">
                <div class="wang-large-title">
                  <span>{{item.shopInfo.shopName}}</span>
                </div>
                <div style="padding: 2px;color: #909399;font-size: 12px;">
                  <span>{{item.shopInfo.location}}</span>
                </div>
                <div class="settlemng-div">
                  <div>今日营业额</div>
                  <div style="font-size:1.5rem;font-weight:900;">{{item.today.sales | RMB}}</div>
                  <div>{{item.today.orderNum}}笔</div>
                </div>
              </div>
            </el-carousel-item>
          </el-carousel>
        </div>
      </transition>
      <div>
        <div class="create_shop_div">
          <el-button @click="dialogVisible = true" size="large" plain round>创建店铺</el-button>
          <el-button @click="showCurrent = false" size="large" plain round v-if="showCurrent">切换店铺</el-button>
          <el-button @click="showCurrent = true" size="large" plain round v-if="!showCurrent">进入店铺</el-button>
        </div>
      </div>
    </div>
    <div v-else>
      <el-button @click="dialogVisible = true" size="large" plain round>创建店铺</el-button>
    </div>
    <el-dialog
      title="创建"
      :visible.sync="dialogVisible"
      center
      width="500px">
      <el-form ref="form" label-width="80px">
        <el-form-item label="店铺名称">
          <el-input
            placeholder="请输入店铺名称"
            v-model="shopName"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input
            placeholder="请输入店铺地址"
            v-model="location"
            clearable>
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createShop()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data () {
    return {
      shops: [],
      shopName: '',
      location: '',
      areaSelected: [],
      dialogVisible: false,
      autoplay: false,
      current: {},
      showCurrent: true
    }
  },
  created: function () {
    this.init()
  },
  methods: {
    init () {
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
              this.showCurrent = true
              this.current = this.$lodash.find(this.shops, shop => {
                return shop.shopInfo.shopId === this.$route.params.shopId
              })
            } else {
              this.showCurrent = false
            }
          }
        }
      })
    },
    chooseShop (item) { // 选择一个商店
      this.current = this.shops[item]
      this.$route.params.shopId = this.shops[item].shopInfo.shopId
      this.$bus.$emit(this.busConstants.CHANGE_SHOP, this.shops[item])
    },
    createShop () {
      this.$http.post('/shop/create', {
        shopName: this.shopName,
        location: this.location
      }).then(data => {
        if (data.code === '0') {
          this.$message.success('店铺创建成功')
          this.dialogVisible = false
          this.init()
        }
      }).catch(exception => {
        console.info(exception)
      })
    },
    gotoShopHome (shopInfo, today) {
      this.$router.push({ path: '/shop/' + shopInfo.shopId })
    },
    changeShop (item) {
      this.chooseShop(item)
    }
  },
  beforeRouteLeave (to, from, next) {
    this.$bus.$off(this.busConstants.CHANGE_SHOP)
    next()
  }
}
</script>
