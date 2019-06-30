import axios from 'axios'
import userService from '../../common/service/userService'
import { Loading, Message } from 'element-ui'
var loadingInstance

import serverConfig from '../../../server-config'

// 当前请求的条数
var requestNum = 0

let defaultLoadingDivId = 'loadingMainPanel'
let axiosInstance = getInstance(defaultLoadingDivId)
axiosInstance.loadInDiv = function (divId) {
  return getInstance(divId)
}

// 获取新的实例
function getInstance (divId) {
  if (!divId) {
    divId = null
  }
  let $http = axios.create({
    baseURL: serverConfig.api +'/ShopCasher'
  })
  // http request 拦截器
  $http.interceptors.request.use(function (config) {
    if (divId !== null && divId !== '') {
      if (divId === defaultLoadingDivId) {
        if (requestNum === 0) {
          loadingInstance = Loading.service({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(255, 255, 255, 0.7)',
            target: document.querySelector('#' + divId)
          })
        }
        requestNum++
      } else {
        loadingInstance = Loading.service({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(255, 255, 255, 0.7)',
          target: document.querySelector('#' + divId)
        })
      }
    }
    config.headers['Authorization'] = userService.getToken()// 将接口返回的token信息配置到接口请求中
    return config
  }, function (error) {
    return Promise.reject(error)
  })
  // http response 拦截器
  $http.interceptors.response.use(
    response => {
      if (divId !== null && divId !== '') {
        if (divId === defaultLoadingDivId) {
          requestNum--
        }
        if (requestNum === 0) {
          loadingInstance.close()
        }
      }
      switch (response.data.code) {
        case '5000003':
          sessionStorage.setItem('user_token', '')
          sessionStorage.setItem('userInfo', '{}')
          Message.error('登录超时，请重新登录')
          loadingInstance.close()
          window.location.href = '/#/login'// 需求方要求一旦出错立即跳转登录，所以采取这种侵入式的手段。
          break
        default:
          return response.data
      }
    },
    error => {
      if (loadingInstance) {
        loadingInstance.close()
      }
      Message.error('系统异常，请联系管理员')
      return Promise.reject(error)
    }
  )
  return $http
}

export default axiosInstance
