<style lang="scss" scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
    border: 1px solid;
    margin-bottom: 20px;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .avatar-uploader{
    text-align: center;
  }
</style>
<template>
  <div>
    <div style="text-align: center;margin-bottom: 22px;" v-if="false">
      <div v-if="imageUrl">
        <img :src="imageUrl | PICURL" height="150px" class="image">
      </div>
      <div v-else><i class="el-icon-picture" style="font-size: 40px;padding: 20px"></i></div>
      <el-button type="primary">
        <vue-core-image-upload
          crop="local"
          class="btn btn-primary"
          @imageuploaded="handleAvatarSuccess"
          :max-file-size="5242880"
          extensions="png,jpg"
          :text="imgUploadText"
          :maxWidth="300"
          :maxHeight="300"
          cropRatio="1:1"
          inputAccept="image/*"
          url="/api/filesUpload">
        </vue-core-image-upload>
      </el-button>
    </div>
    <div>
      <el-upload
              class="avatar-uploader"
              action="/api/ftp/fileUpload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
        <img v-if="imageUrl" :src="imageUrl | FTP_FILE" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </div>
    <el-form ref="form" label-width="80px">
      <el-form-item label="商品编号">
        <el-input
          placeholder="请输入商品编码"
          v-model="tempGood.code"
          clearable v-if="flag === 'add'">
        </el-input>
        <span v-else>{{tempGood.code}}</span>
      </el-form-item>
      <el-form-item label="商品名称">
        <el-input
          placeholder="请输入商品名称"
          v-model="tempGood.name"
          clearable>
        </el-input>
      </el-form-item>
      <el-form-item label="单价">
        <el-input
          placeholder="请输入单价"
          v-model="tempGood.price"
          clearable>
        </el-input>
      </el-form-item>
      <el-form-item label="进价">
        <el-input
          placeholder="请输入进价"
          v-model="tempGood.bid"
          clearable>
        </el-input>
      </el-form-item>
      <div style="margin-bottom: 18px;">
        <el-row :gutter="23">
          <el-col :span="3" style="text-align: right">规格</el-col>
          <el-col :span="6">
            <el-select v-model="selectSpec" placeholder="请选择">
              <el-option
                v-for="item in specList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="5" style="text-align: right">商品类别</el-col>
          <el-col :span="10">
            <el-cascader
              :options="categoryOptions"
              v-model="selectedCategoryOptions">
            </el-cascader>
          </el-col>
        </el-row>
      </div>
      <el-form-item label="详细信息">
        <el-input
          type="textarea"
          autosize
          placeholder="请输入详细信息"
          v-model="tempGood.description"
          clearable>
        </el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
    </div>
  </div>
</template>

<script>
import shopService from '../common/service/shopService'
import commService from '../common/service/commService'
import 'vue-croppa/dist/vue-croppa.css'

export default {
  data () {
    return {
      imageUrl: '',
      specList: ['包', '个', '袋', '条', '箱', '瓶', '提', '打'],
      categoryOptions: [],
      selectedCategoryOptions: [],
      selectSpec: '',
      tempGood: {},
      myCroppa: {},
      imgUploadText: '上传商品图片'
    }
  },
  created: function () {
    this.initCategoryOptions()
    if (this.good.picUrl) {
      this.imageUrl = this.good.picUrl
    }
    this.selectSpec = this.good.spec
    this.tempGood = commService.copyObject(this.good)
  },
  props: {
    good: {
      type: Object,
      // 对象或数组且一定会从一个工厂函数返回默认值
      default: function () {
        return {}
      }
    },
    flag: {
      type: [String, Number],
      validator: function (value) {
        // 这个值必须匹配下列字符串中的一个
        return ['add', 'edit'].indexOf(value) !== -1
      }
    }
  },
  methods: {
    submit () {
      if (this.flag === 'add') {
        this.createGood()
      } else if (this.flag === 'edit') {
        this.saveGood()
      }
    },
    handleAvatarSuccess (res, file) {
      this.imageUrl = res.result
      this.tempGood.picUrl = res.result
    },
    beforeAvatarUpload (file) {
      const isIMG = ['image/jpeg', 'image/png'].indexOf(file.type) !== -1
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isIMG) {
        this.$message.error('上传头像图片只能是 JPG、PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isIMG && isLt2M
    },
    initCategoryOptions () {
      this.categoryOptions = shopService.buildCatogeryForCascader()
      if (this.good.category) {
        let catogery = shopService.getCatogeryById(this.good.category)
        this.selectedCategoryOptions = [catogery.parentId, catogery.id]
      }
    },
    // 去后台创建新商品的流程
    createGood () {
      this.tempGood.spec = this.selectSpec
      this.tempGood.category = this.selectedCategoryOptions[1]
      this.$http.post('/good/create', this.tempGood).then(data => {
        this.$message.success('新建商品成功')
        this.$emit('success', this.tempGood)
      })
    },
    saveGood () {
      this.tempGood.spec = this.selectSpec
      this.tempGood.category = this.selectedCategoryOptions[1]
      this.$http.post('/good/edit', this.tempGood).then(data => {
        this.$message.success('修改商品成功')
        this.$emit('success', this.tempGood)
      })
    },
    cancel () {
      this.$emit('cancel')
    }
  },
  watch: {
    imageUrl: function () {
      if (this.imageUrl) {
        this.imgUploadText = '修改商品图片'
      }
    }
  }
}
</script>
