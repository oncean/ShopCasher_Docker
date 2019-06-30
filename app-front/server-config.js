const isProd = process.env.NODE_ENV === 'production'
const localhost = 'http://127.0.0.1:31133/'
const baseUrl = process.env.VUE_APP_API_URL || localhost
const api = isProd ? baseUrl : localhost
export default {
  isProd,
  api
}