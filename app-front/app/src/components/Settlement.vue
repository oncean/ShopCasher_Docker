<style lang="scss" scoped>
  .chart-div{
    height: 300px;
    margin-top: 40px;
    .el-col{
      height: 100%;
      .echarts{
        width: 100%;
        height: 100%;
      }
    }
  }
</style>
<template>
  <div>
    <!--<el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/shop/' + shopId }">店铺</el-breadcrumb-item>
      <el-breadcrumb-item>收支明细</el-breadcrumb-item>
    </el-breadcrumb>-->
    <div class="wang-panel" style="padding: 30px">
      <!--图表视图-->
      <div>
        <el-date-picker
          v-model="settlementTrend.dateRange"
          type="daterange"
          size="mini"
          value-format="yyyy-MM-dd"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="settlementTrend.pickerDataOptions"
          @change="changeSettlementTrendDate()">
        </el-date-picker>
        <el-row class="chart-div">
          <el-col :span="12">
            <ECharts :options="settlementTrend.chartOption" auto-resize></ECharts>
          </el-col>
          <el-col :span="12">
            <ECharts :options="catogeryPie.chartOption" auto-resize></ECharts>
          </el-col>
        </el-row>
      </div>
    </div>
    <div class="wang-panel" style="padding: 30px;margin-top: 10px;">
      <!--表格视图-->
      <div>
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="选择日期">
            <el-date-picker
              type="dates"
              value-format="yyyy-MM-dd"
              v-model="req.dates"
              placeholder="选择一个或多个日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="querySettlement">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <el-table
          :data="table.data"
          style="width: 100%">
          <el-table-column
            label="时间">
            <template slot-scope="scope">
              <span>{{scope.row.settlementDate}}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="orderNum"
            label="成交(笔)">
          </el-table-column>
          <el-table-column
            label="营业额">
            <template slot-scope="scope">
              <span>{{scope.row.sales | RMB}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="纯利润">
            <template slot-scope="scope">
              <span>{{scope.row.profit | RMB}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="最后一笔">
            <template slot-scope="scope">
              <span>{{scope.row.updateTime | DATE}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <div class="operateDiv">
                <span @click="editGoodInfo(scope.row)">
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
          </el-table-column>
        </el-table>
        <el-pagination
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
import shopService from '../common/service/shopService'
let moment = require('moment')

export default {
  data () {
    return {
      shopId: '',
      table: {
        page: {
          pageSize: 10,
          pageNo: 1
        },
        totalNum: 0,
        data: []
      },
      req: {
        dates: []
      },
      settlementTrend: {
        pickerDataOptions: this.$commService.commonPickDateOptions,
        dateRange: [],
        chartOption: {
          title: {
            text: '销售利润趋势图',
            left: 'center',
            top: 0,
            textStyle: {
              color: '#333'
            }
          },
          legend: {
            data: ['营业额', '纯利润'],
            top: 20,
            left: 'right'
          },
          xAxis: {
            data: [],
            axisLabel: {
              interval: 0,
              rotate: -30
            }
          },
          tooltip: {
            trigger: 'axis'
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            name: '营业额',
            data: [],
            type: 'line',
            smooth: true
          }, {
            name: '纯利润',
            data: [],
            type: 'line',
            smooth: true
          }]
        }
      },
      catogeryPie: {
        chartOption: {
          title: {
            text: '商品类别销售图',
            left: 'center',
            top: 0,
            textStyle: {
              color: '#333'
            }
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          series: [
            {
              name: '商品类别',
              type: 'pie',
              radius: '55%',
              center: ['50%', '50%'],
              data: [],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
      }
    }
  },
  created: function () {
    this.shopId = this.$route.params.shopId
    this.querySettlement()
    this.initSettlementTrendDate()
  },
  methods: {
    querySettlement () {
      let req = {
        page: this.$commService.formatPage(this.table.page),
        shopId: this.shopId,
        settlementDates: this.req.dates
      }
      shopService.querySettlement(req).then(data => {
        this.table.data = data.result.settlements
        this.table.totalNum = data.result.totalNum
      })
    },
    handleSizeChange (val) {
      this.table.page.pageSize = val
      this.querySettlement()
    },
    handleCurrentChange (val) {
      this.table.page.pageNo = val
      this.querySettlement()
    },
    initSettlementTrendDate () {
      this.settlementTrend.dateRange = [
        moment().subtract('days', 30).format('YYYY-MM-DD'),
        moment().format('YYYY-MM-DD')
      ]
      this.changeSettlementTrendDate()
    },
    changeSettlementTrendDate () {
      let req = {
        shopId: this.shopId
      }
      if (this.settlementTrend.dateRange == null || this.settlementTrend.dateRange.length < 2) {
        this.settlementTrend.dateRange = [
          moment().subtract('days', 30).format('YYYY-MM-DD'),
          moment().format('YYYY-MM-DD')
        ]
      }
      req.startDay = this.settlementTrend.dateRange[0]
      req.endDay = this.settlementTrend.dateRange[1]
      this.buildSettlementTrend(req)
      this.buildPie(req)
    },
    buildSettlementTrend (req) {
      // 结算趋势图标选择时间范围后的操作
      shopService.querySettlement(req).then(data => {
        let settlements = data.result.settlements
        let xAlias = []
        let yAliasSales = [] // 营业额数组
        let yAliasProfit = [] // 纯利润数组
        for (let i in settlements) {
          xAlias.push(settlements[i].settlementDate)
          yAliasSales.push(settlements[i].sales)
          yAliasProfit.push(settlements[i].profit)
        }
        let chartOption = this.$commService.copyObject(this.settlementTrend.chartOption)
        chartOption.xAxis.data = xAlias
        chartOption.series[0].data = yAliasSales
        chartOption.series[1].data = yAliasProfit
        this.settlementTrend.chartOption = chartOption
      })
    },
    buildPie (req) { // 设置饼图的操作
      this.$http.post('/settlement/settlementByCatogery', req).then(data => {
        let settlements = data.result
        let chartDatas = [] // 纯利润数组
        if (data.result === null || data.result.length < 1) {
          let chartData = {
            name: '暂无数据',
            value: 0
          }
          chartDatas.push(chartData)
        } else {
          for (let i in settlements) {
            let chartData = {
              name: settlements[i].catogeryName,
              value: settlements[i].number
            }
            chartDatas.push(chartData)
          }
        }
        this.catogeryPie.chartOption.series[0].data = chartDatas
      })
    }
  }
}
</script>
