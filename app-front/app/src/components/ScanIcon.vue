<style lang="scss" scoped>
</style>
<template>
  <div style="display: inline-block">
    <el-popover
      placement="top-start"
      trigger="manual"
      v-model="visible">
      <VueQrcode :value="connectCode" :options="{ size: 200 }" id="vueQrcodeLoad"></VueQrcode>
      <i class="el-icon-mobile-phone" slot="reference" style="font-size: 30px;" @click="visible = !visible"></i>
    </el-popover>
  </div>
</template>

<script>
export default {
  data () {
    return {
      connectCode: '',
      visible: false
    }
  },
  mounted () {
    this.$http.loadInDiv('vueQrcodeLoad').get('/scan/getShopConnectCode/' + this.shopId).then(data => {
      this.connectCode = '/api/scanClient/scan.html?connectCode=' + data.result + '&shopId=' + this.shopId
    })
  },
  props: {
    shopId: {
      type: String,
      validator: function (value) {
        // 这个值必须匹配下列字符串中的一个
        return value !== ''
      }
    }
  }
}
</script>
