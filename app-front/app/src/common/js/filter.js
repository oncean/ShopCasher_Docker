import commService from '../service/commService'
import shopService from '../service/shopService'
import Constants from '../js/Constants'
let moment = require('moment')

const filter = {
  RMB: value => { // 金额过滤器
    if (value || value === 0) {
      var str = value.toFixed(2) + ''
      var intSum = str.substring(0, str.indexOf('.')).replace(/\B(?=(?:\d{3})+$)/g, ',')// 取到整数部分
      var dot = str.substring(str.length, str.indexOf('.'))// 取到小数部分搜索
      var ret = intSum + dot
      return '￥' + ret
    }
  },
  PICURL: value => { // 图片url过滤器
    if (value) {
      return commService.getPicUrl(value)
    }
  },
  CAT: value => { // 商品类型过滤器
    if (value) {
      return shopService.getCatogeryName(value)
    }
  },
  PAY_MATHOD: value => { // 支付类型
    if (value) {
      return Constants.PayMethods[value]
    }
  },
  DATE: value => {
    if (value) {
      return moment(value).format('YYYY-MM-DD HH:mm:ss')
    }
  },
  FTP_FILE: value => {
    if (value) {
      return '/api/ftp/download/' + value
    }
  }
}
export default filter
