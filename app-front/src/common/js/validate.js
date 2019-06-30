/* eslint-disable */
let validate = {
  email: function (value) {
    var regEmail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/
    return regEmail.test(value)
  },
  userName: function (value) {
    //用户名正则，4到16位（字母，数字，下划线，减号）
    var uPattern1 = /^[a-zA-Z0-9_-]{4,16}$/;
    var uPattern2 = /[a-zA-Z]/i;
    return uPattern1.test(value) && uPattern2.test(value)
  }
}
export default validate
/* eslint-enable */
