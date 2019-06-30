/* eslint-disable */
import Constants from './Constants'
let sock = null
let stompClient = null
let stompConnect = null
let isConnected = false
let socket = {
  subcribe: function (connectCode,callback) {
    if (sock === null) {
      sock = new SockJS('/api/my-websocket')
      stompClient = Stomp.over(sock)
    }
    if (!isConnected) {
      stompClient.connect({}, function (frame) {
        isConnected = true
        stompConnect = stompClient.subscribe('/topic/sendScanCode/' + connectCode, function (r) {
          callback(JSON.parse(r.body))
        })
      }, function (error) {
        console.info(error)
      })
    }else{
      stompConnect.unsubscribe()
      stompConnect = stompClient.subscribe('/topic/sendScanCode/' + connectCode, function (r) {
        callback(JSON.parse(r.body))
      })
    }
  }
}
export default socket
/* eslint-enable */
