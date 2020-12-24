<style lang="scss" scoped>
  .operateDiv{
    font-size: 18px;
    span{
      margin: 3px;
      cursor: pointer;
    }
  }
  .expand_table_style  /deep/ .el-table__expanded-cell{
    background-color: #eee;
    &:hover{
      background-color: #eee;
    }
  }
</style>
<template>
  <div>
    <!--<el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/shop/' + shopId }">店铺</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
    </el-breadcrumb>-->
    <div  class="wang-panel" style="padding: 30px">
      <div>
        <el-form :inline="true" class="demo-form-inline" size="mini">
          <el-form-item label="时间筛选">
            <el-date-picker
              v-model="req.dateRange.value"
              type="datetimerange"
              size="mini"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="req.dateRange.options"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="订单号">
            <el-input v-model="req.orderId" placeholder="请输入订单号"></el-input>
          </el-form-item>
          <el-form-item label="支付方式">
            <el-select v-model="req.payMethod.value" placeholder="请选择" style="width: 100px;">
              <el-option
                v-for="item in req.payMethod.options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="queryOrders">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="expand_table_style">
        <el-table
          :data="table.data"
          @expand-change="openOrderItem"
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
                <el-table
                  :data="props.row.orderItems">
                  <el-table-column
                    prop="goodCode"
                    label="商品编码"></el-table-column>
                  <el-table-column
                    prop="goodName"
                    label="商品名称"></el-table-column>
                  <el-table-column
                    prop="number"
                    label="商品数量"></el-table-column>
                  <el-table-column
                    label="总计">
                    <template slot-scope="scope">
                      <span>{{scope.row.totalPrice | RMB }}</span>
                    </template>
                  </el-table-column>
                </el-table>
            </template>
          </el-table-column>
          <el-table-column
            prop="orderId"
            label="订单号">
          </el-table-column>
          <el-table-column
            label="支付类型">
            <template slot-scope="scope">
              <span>{{scope.row.payMethod | PAY_MATHOD }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="订单金额">
            <template slot-scope="scope">
              <span>{{scope.row.totalPrice | RMB }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="收款">
            <template slot-scope="scope">
              <span>{{scope.row.receipts | RMB }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="找零">
            <template slot-scope="scope">
              <span>{{scope.row.giveChange | RMB }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="订单日期">
            <template slot-scope="scope">
              <span>{{scope.row.settlementTime | DATE }}</span>
            </template>
          </el-table-column>
          <!--<el-table-column
            label="操作">
            <template slot-scope="scope">
              <div class="operateDiv">
                <span>
                  <el-tooltip class="item" effect="dark" content="修改商品信息" placement="top">
                    <i class="el-icon-edit"></i>
                  </el-tooltip>
                </span>
                <span>
                  <el-tooltip class="item" effect="dark" content="查看销售记录" placement="top">
                    <i class="el-icon-tickets"></i>
                  </el-tooltip>
                </span>
              </div>
            </template>
          </el-table-column>-->
        </el-table>
        <el-pagination
          v-if="table.page.pageSize < table.totalNum"
          style="margin-top: 20px;"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="table.page.pageNo"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="table.page.pageSize"
          layout="total, prev, pager, next, sizes"
          :total="table.totalNum">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      req: {
        dateRange: {
          options: {
            shortcuts: [{
              text: '今天',
              onClick (picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24)
                picker.$emit('pick', [start, end])
              }
            }, {
              text: '昨天',
              onClick (picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 2)
                end.setTime(start.getTime() - 3600 * 1000 * 24)
                picker.$emit('pick', [start, end])
              }
            }, {
              text: '最近三天',
              onClick (picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 3)
                picker.$emit('pick', [start, end])
              }
            }, {
              text: '最近一周',
              onClick (picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
                picker.$emit('pick', [start, end])
              }
            }, {
              text: '最近一个月',
              onClick (picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
                picker.$emit('pick', [start, end])
              }
            }]
          },
          value: ''
        },
        orderId: '',
        payMethod: {
          value: '',
          options: []
        }
      },
      shopId: '',
      table: {
        page: {
          pageSize: 10,
          pageNo: 0
        },
        totalNum: 0,
        data: []
      }
    }
  },
  created: function () {
    this.shopId = this.$route.params.shopId
    this.queryOrders()
    this.req.payMethod.options = this.$commService.initPaymethodOptions()
  },
  methods: {
    queryOrders () {
      let req = {
        page: this.$commService.formatPage(this.table.page),
        shopId: this.shopId,
        orderId: this.req.orderId,
        payMethod: this.req.payMethod.value
      }
      if (this.req.dateRange.value != null) {
        req.startTime = this.req.dateRange.value[0]
        req.endTime = this.req.dateRange.value[1]
      }
      this.$http.post('/order/query', req).then(data => {
        this.table.data = data.result
        this.table.totalNum = data.total
      })
    },
    handleSizeChange (val) {
      this.table.page.pageSize = val
      this.queryOrders()
    },
    handleCurrentChange (val) {
      this.table.page.pageNo = val
      this.queryOrders()
    },
    openOrderItem (row, expandedRows) {
      if (row.isOpenItems) {
        row.isOpenItems = false
      } else {
        this.$http.get('/order/getOrderItems/' + row.orderId).then(data => {
          this.$set(row, 'orderItems', data.result)
          this.$set(row, 'isOpenItems', true)
        })
      }
    }
  }
}
</script>
