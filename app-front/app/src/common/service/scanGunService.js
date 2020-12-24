let scanClient = {
  startGet: function (callback) {
    var code = ''
    var lastTime = new Date().getTime()
    document.onkeypress = function (e) {
      var newTime = new Date().getTime()
      if (newTime - lastTime < 30) {
        if (e.keyCode === 13 && code.length > 8) {
          callback(code)
        }
        if (e.keyCode >= 48) {
          let tempCode = e.keyCode - 48
          code = code + tempCode
        }
      } else {
        code = ''
      }
      lastTime = newTime
    }
  }
}
export default scanClient
