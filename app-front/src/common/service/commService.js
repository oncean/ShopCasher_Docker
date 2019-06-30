import Constants from '../js/Constants'

let commService = {
  getPicUrl: function (url) {
    return '/api' + url
  },
  initPaymethodOptions: function () {
    let payMethods = Constants.PayMethods
    let options = []
    for (var i in payMethods) {
      let option = {
        label: payMethods[i],
        value: i
      }
      options.push(option)
    }
    return options
  },
  copyObject: function (value) {
    return JSON.parse(JSON.stringify(value))
  },
  commonPickDateOptions: {
    shortcuts: [{
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
    }, {
      text: '最近三个月',
      onClick (picker) {
        const end = new Date()
        const start = new Date()
        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
        picker.$emit('pick', [start, end])
      }
    }]
  },
  formatPage: function (page) {
    return {
      pageSize: page.pageSize,
      pageNo: page.pageNo > 0 ? page.pageNo - 1 : 0
    }
  }
}

export default commService
