const isProd = process.env.NODE_ENV === 'production'
const localhost = 'http://127.0.0.1:31133/'
const serverHost = process.env.VUE_APP_SERVER_URL
const baseUrl = serverHost || localhost
const api = baseUrl
console.info(api)
export default {
  isProd,
  api
}