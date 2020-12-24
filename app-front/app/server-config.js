const isProd = process.env.NODE_ENV === 'production'
const baseUrl = '/api'
const api = baseUrl
export default {
  isProd,
  api
}