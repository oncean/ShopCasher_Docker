// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import axios from './common/js/axios'
import ElementUI from 'element-ui'
import App from './App'
import router from './common/js/routers'
import filter from './common/js/filter'
import VueAreaLinkage from 'vue-area-linkage'
import './assets/icons/iconfont.css'
import './common/css/animate.css'
import './common/css/wang.css'
import './common/css/common.scss'
import Cleave from 'vue-cleave-component'
import Constants from './common/js/Constants'
import bus from './common/js/bus'
import busConstants from './common/js/busConstants'
import commService from './common/service/commService'
import GoodInfo from './components/GoodInfo'
import ScanIcon from './components/ScanIcon'
import VueCoreImageUpload from 'vue-core-image-upload'
import Croppa from 'vue-croppa'
import ECharts from 'vue-echarts'
import lodash from 'lodash'
import VueQrcode from '@xkeshi/vue-qrcode'

// 加入过滤器
for (let key in filter) {
  Vue.filter(key, filter[key])
}
Vue.use(ElementUI, { size: 'small' })
Vue.use(VueAreaLinkage) // 地区选择器
Vue.use(Croppa) // 图像裁剪器
Vue.prototype.$http = axios
Vue.prototype.Constants = Constants
Vue.prototype.$commService = commService
Vue.prototype.busConstants = busConstants // 事件常量类
Vue.prototype.$bus = bus // 事件总线
Vue.prototype.$lodash = lodash // 计算函数
Vue.use(Cleave) // 输入框
Vue.component('GoodInfo', GoodInfo)
Vue.component('ScanIcon', ScanIcon) // 手机扫描显示图标
Vue.component('vue-core-image-upload', VueCoreImageUpload)
Vue.component('ECharts', ECharts) // 图表
Vue.component('VueQrcode', VueQrcode) // 二维码显示

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  ...App
})
