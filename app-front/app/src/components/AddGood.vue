<style lang="scss" scoped>
  .total-div {
    display: flex;
    justify-content: space-between;
  }
</style>
<template>
  <div>
    <!--<el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/shop/' + shopId }">店铺</el-breadcrumb-item>
      <el-breadcrumb-item>录入商品</el-breadcrumb-item>
    </el-breadcrumb>-->
    <div class="total-div">
      <div class="wang-panel" style="width: 63%;">
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
            prop="stock"
            label="库存">
          </el-table-column>
          <el-table-column
            label="单价">
            <template slot-scope="scope">
              <span>{{scope.row.price | RMB}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="加入数量"
            width="160">
            <template slot-scope="scope">
              <el-input-number size="mini" v-model="scope.row.number" :min="1"></el-input-number>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin: 20px">
          <el-button type="primary" icon="el-icon-plus" :disabled="goods.length === 0" style="float: right;" @click="addToServer">确定录入</el-button>
        </div>
      </div>
      <div class="wang-panel" style="width: 35%">
        <ScanIcon :shopId="shopId"></ScanIcon>
        <transition enter-active-class="animated wobble" leave-active-class="animated wobble">
          <div v-show="showLastGood">
            <div slot="header" class="clearfix" style="line-height: 30px; padding: 20px 20px 0;">
              <span class="wang-large-title">最近录入的商品</span>
              <!--<el-button type="info" icon="el-icon-delete" style="float: right;" @click="">撤销</el-button>-->
            </div>
            <img :src="lastGood.picUrl | PICURL" class="image" style="width: 100%;max-height: 300px;">
            <div style="padding: 14px;">
              <el-row :gutter="20">
                <el-col :span="10" style="text-align: right">商品名称</el-col>
                <el-col :span="10">{{lastGood.name}}</el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="10" style="text-align: right">商品详情</el-col>
                <el-col :span="10">{{lastGood.description}}</el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="10" style="text-align: right">库存</el-col>
                <el-col :span="10">{{lastGood.stock}}</el-col>
              </el-row>
            </div>
          </div>
        </transition>
        <div v-show="!showLastGood" style="padding: 50px;text-align: center;">
          <div><i class="el-icon-wang-saoma" style="font-size: 100px"></i></div>
          <div class="wang-large-title">请扫码录入</div>
        </div>
      </div>
    </div>
    <el-dialog
      title="未找到该商品，请先录入商品信息"
      :visible.sync="dialogVisible"
      center
      width="500px">
      <GoodInfo :good="newGood"
                flag="add"
                @cancel="dialogVisible = false"
                @success="addGoodSuccess"></GoodInfo>
    </el-dialog>
  </div>
</template>

<script>
import scanGunService from '../common/service/scanGunService'
import shopService from '../common/service/shopService'

export default {
  data () {
    return {
      shopId: '',
      lastGood: {},
      goods: [],
      dialogVisible: false,
      showLastGood: false,
      newGood: {}
    }
  },
  created: function () {
    this.shopId = this.$route.params.shopId
    let component = this
    scanGunService.startGet(component.scanNewGood)
    this.watchScanEvent()
  },
  methods: {
    scanNewGood (code) {
      console.info('扫描到code = ' + code)
      shopService.getGoodByCode(this.shopId, code).then(data => {
        if (data.code === '0') {
          this.addGoodNum(data.result)
        } else if (data.code === '5000004') {
          this.newGood = {
            code: code,
            shopId: this.shopId
          }
          this.dialogVisible = true
        }
      })
    },
    indexMethod (index) {
      return index + 1
    },
    // 页面上的新增流程，再刚刚录入的地方显示，在表格中显示
    addGoodNum (good) {
      this.showLastGood = true
      this.lastGood = good
      this.addGoodToList(good)
    },
    addGoodToList (good) {
      // 通过id判断是否已存在列表中，不存在则更新
      for (var i in this.goods) {
        if (this.goods[i].id === good.id) {
          this.goods[i].number++
          return
        }
      }
      this.$set(good, 'number', 1)
      this.goods.push(good)
    },
    addGoodSuccess (goodInfo) {
      this.dialogVisible = false
      this.scanNewGood(goodInfo.code)
    },
    addToServer () {
      let req = {
        shopId: this.shopId,
        goods: []
      }
      for (var i in this.goods) {
        let good = {
          goodCode: this.goods[i].code,
          number: this.goods[i].number
        }
        req.goods.push(good)
      }
      this.$http.post('/good/addGoodNumBatch', req).then(data => {
        if (data.code === '0') {
          this.$message.success('录入商品成功')
          this.$router.go(-1)
        } else {
          this.$message.error('录入商品失败，请联系管理员')
        }
      })
    },
    watchScanEvent () { // 其他客户端扫码事件
      this.$bus.$on(this.busConstants.RECEIPT_SCAN_CODE, code => {
        this.scanNewGood(code)
      })
    }
  },
  beforeRouteLeave (to, from, next) {
    this.$bus.$off(this.busConstants.RECEIPT_SCAN_CODE)
    next()
  }
}
</script>
