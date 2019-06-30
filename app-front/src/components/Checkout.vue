<style lang="scss" scoped>
  .total-div {
    display: flex;
    justify-content: space-between;
  }

  .left-panel {
    width: 60%;
  }
  .right-panel /deep/ .vue-keyboard-key{
    min-width: 30%;
  }
  .right-panel {
    width: 38%;
    font-size: 20px;
    text-align: center;
    .el-row {
      margin-bottom: 20px;
      &:last-child {
        margin-bottom: 0;
      }
    }
  }
  .receipts_input{
    font-size: 20px;
    line-height: 20px;
    height: 28px;
    text-align: center;
    border-width: 0px;
    border-bottom-width: 1px;
  }
  .digit-button-group{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    margin-top: 20px;
  }
  .digit-button{
    width: 87px;
    height: 35px;
    line-height: 35px;
    padding: 0px;
    margin: 10px 0;
  }
  .el-autocomplete{
    width: 100%;
  }
  .my-autocomplete{
    li {line-height: 40px;
    }
  }
  .innerInpuBox{
    display: flex;
    padding: 5px;
    border-bottom: 1px solid #eee;
    img{
      width: 30px;
    }
  }
  .innerInpuBox_nothing{
    text-align: center;
    color: #797878;
  }
  .totalNumClass{
    padding: 10px;
    text-align: right;
    .big{
      font-size: 20px;
    }
  }
</style>
<template>
  <div>
    <!--<el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/shop/' + shopId }">店铺</el-breadcrumb-item>
      <el-breadcrumb-item>收银台</el-breadcrumb-item>
    </el-breadcrumb>-->
    <div class="total-div">
      <div class="left-panel">
        <!--<div class="wang-panel code-panel wang-h1">
            <i class="icon icon-keyboard"></i>
            <span style="margin-right: 10px;">收银台连接码</span>
            <span style="font-weight: 900;">{{connectCode}}</span>
        </div>-->
        <!--<div style="width: 800px;height: 500px;">
          <ScanView></ScanView>
        </div>-->
        <el-card shadow="never" body-style="{padding:'0px'}">
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="searchBox.value"
            clearable
            size="large"
            :trigger-on-focus="false"
            :fetch-suggestions="querySearchSuggestions"
            placeholder="输入商品条码、名称搜索"
            @select="selectSuggestion">
            <ScanCamera slot="prepend"></ScanCamera>
            <ScanIcon :shopId="shopId" slot="prepend" style="margin-left: 20px;"></ScanIcon>
            <i
              class="el-icon-close el-input__icon"
              slot="suffix"
              @click="removeSearchBox">
            </i>
            <template slot-scope="{ item }">
              <div v-if="item.showNothing" class="innerInpuBox_nothing">
                <div> 未查找到结果 </div>
              </div>
              <div class="innerInpuBox" v-else>
                <div>
                  <img :src="item.picUrl | PICURL"/>
                </div>
                <div class="name">
                  <div>{{item.name}}</div>
                </div>
              </div>
            </template>
          </el-autocomplete>
        </el-card>
        <el-card shadow="never" style="margin-top: 10px;">

          <el-table
            :data="goods"
            style="width: 100%;min-height: 300px;">
            <el-table-column
              type="index"
              :index="indexMethod">
            </el-table-column>
            <el-table-column
              prop="name"
              label="商品名称">
            </el-table-column>
            <el-table-column
              width="150"
              label="数量">
              <template slot-scope="scope">
                <el-input-number size="mini" v-model="scope.row.number" :min="1"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column
              label="单价">
              <template slot-scope="scope">
                <span>{{scope.row.price | RMB}}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="good.price | RMB"
              label="金额">
              <template slot-scope="scope">
                <span>{{scope.row.number * scope.row.price | RMB}}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作">
              <template slot-scope="scope">
                <i class="el-icon-delete operateDiv" @click="deleteGood(scope.row.id)"></i>
              </template>
            </el-table-column>
          </el-table>
          <div class="totalNumClass">
            <span>数量: <span class="big">{{carList.totalNum}}</span></span>
            <span style="margin: 0 25px;">合计: <span class="big">{{carList.totalPrice | RMB}}</span></span>
          </div>
        </el-card>
      </div>
      <el-card class="right-panel wang-panel" shadow="never">
        <div class="wang-h1">付款方式</div>
        <el-row style="margin-top: 10px;">
          <el-col :span="8">
            <el-radio v-model="payMethod" label="1" border>现金</el-radio>
          </el-col>
          <el-col :span="8">
            <el-radio v-model="payMethod" label="2" border>微信</el-radio>
          </el-col>
          <el-col :span="8">
            <el-radio v-model="payMethod" label="3" border>支付宝</el-radio>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <div>
              <div>应收金额</div>
              <div style="color: inherit;">{{carList.totalPrice | RMB}}</div>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <div>实收金额</div>
              <div>
                <cleave v-model="receipts"
                        :options="receiptsInputOptions"
                        class="el-input__inner receipts_input"
                        disabled="true"></cleave>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <div>找零</div>
              <div>{{carList.giveChange | RMB}}</div>
            </div>
          </el-col>
        </el-row>
        <div>
          <keyboard v-model="receipts" layouts="123|456|789|0.{x:backspace}"></keyboard>
        </div>
        <div class="digit-button-group">
          <el-button @click="receipts = '0'" style="width: 120px;">清零</el-button>
          <el-button @click="pay()" :disabled="ifSettlementButton" type="primary" style="width: 120px;">结算</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import scanGunService from '../common/service/scanGunService'
import shopService from '../common/service/shopService'
import keyboard from 'vue-keyboard'
import ScanCamera from './ScanCamera'
import ScanView from './ScanView'

export default {
  components: {'keyboard': keyboard,'ScanCamera':ScanCamera,'ScanView':ScanView},
  data () {
    return {
      carList: {
        totalPrice: 0,
        totalNum: 0,
        giveChange: 0
      },
      searchBox: {
        value: ''
      },
      goods: [],
      receipts: '0',
      payMethod: '1',
      shopId: '',
      receiptsInputOptions: {
        numeral: true
      },
      ifSettlementButton: true
    }
  },
  created: function () {
    let component = this
    this.shopId = this.$route.params.shopId
    scanGunService.startGet(component.scanNewGood)
    this.$bus.$on(this.busConstants.RECEIPT_SCAN_CODE, code => {
      this.scanNewGood(code)
    })
  },
  beforeRouteLeave (to, from, next) {
    this.$bus.$off(this.busConstants.RECEIPT_SCAN_CODE)
    next()
  },
  watch: {
    'goods': {
      handler: function (val, oldVal) {
        let totalNum = 0
        let totalPrice = 0
        for (var i in this.goods) {
          let good = this.goods[i]
          totalNum += good.number
          totalPrice += good.number * good.price
        }
        this.carList.totalNum = totalNum
        this.carList.totalPrice = totalPrice
        if (totalPrice > 0) {
          this.ifSettlementButton = false
        } else {
          this.ifSettlementButton = true
        }
      },
      deep: true
    },
    'receipts': function () {
      this.carList.giveChange = parseFloat(this.receipts ? this.receipts : 0) - this.carList.totalPrice
    }
  },
  methods: {
    refresh () {
      this.carList = {
        totalPrice: 0,
        totalNum: 0,
        giveChange: 0
      }
      this.searchBox = {
        value: ''
      }
      this.goods = []
      this.receipts = '0'
      this.payMethod = '1'
      this.receiptsInputOptions = {
        numeral: true
      }
    },
    scanNewGood (code) {
      shopService.getGoodByCode(this.shopId, code).then(data => {
        if (data.code === '0') {
          this.addGood(data.result)
        } else if (data.code === '5000004') {
          this.$message.warning('未找到该商品')
        } else {
          this.$message.error('系统错误，请联系管理员')
        }
      })
    },
    addGood (goodItem) {
      // 通过id判断是否已存在列表中，不存在则更新
      for (var i in this.goods) {
        if (this.goods[i].id === goodItem.id) {
          this.goods[i].number++
          return
        }
      }
      this.$set(goodItem, 'number', 1)
      this.goods.push(goodItem)
    },
    deleteGood (goodId) {
      for (var i in this.goods) {
        if (this.goods[i].id === goodId) {
          this.goods.splice(i, 1)
          return
        }
      }
    },
    selectSuggestion (item) {
      this.addGood(item)
    },
    indexMethod (index) {
      return index + 1
    },
    querySearchSuggestions (queryString, cb) {
      if (this.searchBox.value === null || this.searchBox.value === '') {
        cb()
      }
      let req = {
        totalNum: 10,
        value: this.searchBox.value,
        shopId: this.shopId
      }
      this.$http.loadInDiv().post('/good/querySuggestion', req).then(data => {
        if (data.result === null || data.result.length < 1) {
          data.result = [
            {
              showNothing: true
            }
          ]
        }
        cb(data.result)
      })
    },
    pay () {
      var orderItems = []
      for (var i in this.goods) {
        var good = this.goods[i]
        var orderItem = {
          totalPrice: good.totalPrice,
          number: good.number,
          goodId: good.id
        }
        orderItems.push(orderItem)
      }
      var req = {
        order: {
          totalPrice: this.carList.totalPrice,
          receipts: this.receipts,
          giveChange: this.carList.giveChange,
          totalNum: this.carList.totalNum,
          payMethod: this.payMethod,
          shopId: this.shopId
        },
        orderItems: orderItems
      }
      this.$http.post('/order/add', req).then(data => {
        if (data.code === '0') {
          this.$message.success('订单结算成功')
          this.refresh()
        }
      })
    },
    removeSearchBox () {
      this.searchBox.value = ''
    }
  }
}
</script>
