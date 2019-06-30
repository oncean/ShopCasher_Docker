<style lang="scss" scoped>
  .operateDiv{
    font-size: 18px;
    span{
      margin: 3px;
      cursor: pointer;
    }
  }
</style>
<template>
  <div>
    <!--<el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/shop/' + shopId }">店铺</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
    </el-breadcrumb>-->
    <div  class="wang-panel" style="padding: 30px">
      <div>
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="商品名称">
            <el-input v-model="table.req.goodName" placeholder="请输入商品名称"></el-input>
          </el-form-item>
          <el-form-item label="商品编码">
            <el-input v-model="table.req.goodCode" placeholder="请输入商品编码"></el-input>
          </el-form-item>
          <el-form-item label="商品类别">
            <el-cascader
              change-on-select
              :options="categoryOptions"
              v-model="selectedCategoryOptions">
            </el-cascader>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="queryGoods">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div>
        <el-table
          :data="table.data"
          style="width: 100%">
          <el-table-column
            label="商品图片">
            <template slot-scope="scope">
              <img :src="scope.row.picUrl | FTP_FILE" class="image" style="max-height: 50px;">
            </template>
          </el-table-column>
          <el-table-column
            prop="name"
            sortable
            label="商品名称">
          </el-table-column>
          <el-table-column
            prop="description"
            label="商品描述">
          </el-table-column>
          <el-table-column
            prop="stock"
            sortable
            label="库存">
            <template slot-scope="scope">
              <span>{{scope.row.stock + " " + scope.row.spec }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="单价">
            <template slot-scope="scope">
              <span>{{scope.row.price | RMB}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="进价">
            <template slot-scope="scope">
              <span>{{scope.row.bid | RMB}}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="商品类别">
            <template slot-scope="scope">
              <span>{{scope.row.category | CAT}}</span>
            </template>
          </el-table-column>
          <el-table-column
            width="200px"
            label="操作">
            <template slot-scope="scope">
              <div class="operateDiv">
                <span @click="editGoodInfo(scope.row)">
                  <el-button type="primary">修改</el-button>
                </span>
                <span @click="deleteGood(scope.row)">
                  <el-button type="danger">删除</el-button>
                </span>
                <span>
                  <!--<el-tooltip class="item" effect="dark" content="查看销售记录" placement="top">-->
                    <!--<i class="el-icon-tickets"></i>-->
                  <!--</el-tooltip>-->
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
    <el-dialog
      title="修改商品"
      :visible.sync="dialogVisible"
      center
      width="500px" v-if="dialogVisible">
      <GoodInfo :good="editGood"
                flag="edit"
                @cancel="dialogVisible = false"
                @success="editGoodSuccess"></GoodInfo>
    </el-dialog>
  </div>
</template>

<script>
import shopService from '../common/service/shopService'
export default {
  data () {
    return {
      categoryOptions: [],
      selectedCategoryOptions: [],
      shopId: '',
      dialogVisible: false,
      table: {
        page: {
          pageSize: 10,
          pageNo: 0
        },
        req: {
          goodCode: '',
          goodName: ''
        },
        totalNum: 0,
        data: []
      },
      editGood: {}
    }
  },
  created: function () {
    this.shopId = this.$route.params.shopId
    this.initCategoryOptions()
    this.queryGoods()
  },
  methods: {
    initCategoryOptions () {
      this.categoryOptions = shopService.buildCatogeryForCascader()
    },
    queryGoods () {
      let req = {
        page: this.$commService.formatPage(this.table.page),
        shopId: this.shopId,
        goodCode: this.table.req.goodCode,
        goodName: this.table.req.goodName
      }
      if (this.selectedCategoryOptions.length === 1) {
        req.catogeryType = 1
        req.catogeryId = this.selectedCategoryOptions[0]
      } else if (this.selectedCategoryOptions.length === 2) {
        req.catogeryType = 2
        req.catogeryId = this.selectedCategoryOptions[1]
      }
      this.$http.post('/good/query', req).then(data => {
        this.table.data = data.result
        this.table.totalNum = data.total
      })
    },
    handleSizeChange (val) {
      this.table.page.pageSize = val
      this.queryGoods()
    },
    handleCurrentChange (val) {
      this.table.page.pageNo = val
      this.queryGoods()
    },
    editGoodInfo (goodInfo) {
      this.editGood = goodInfo
      this.dialogVisible = true
    },
    deleteGood (goodInfo) {
      this.$confirm('是否确定删除该商品?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        shopService.deleteGoodById(this.shopId, goodInfo.id).then(data => {
          if (data.code === '0') {
            this.$message.success('删除商品成功')
            this.queryGoods()
          } else if (data.code === '5000004') {
            this.$message.success('未找到该商品')
          }
        })
      }).catch(() => {})
    },
    editGoodSuccess () {
      this.dialogVisible = false
      this.queryGoods()
    }
  }
}
</script>
