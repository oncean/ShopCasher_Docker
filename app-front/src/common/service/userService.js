
let userService = {
  saveUserInfoAndToken: function (userInfo, token) {
    sessionStorage.setItem('user_token', token)
    sessionStorage.setItem('userInfo', userInfo)
  },
  ifLogined: function () {
    if (sessionStorage.getItem('user_token')) {
      return true
    }
    return false
  },
  getToken: function () {
    return sessionStorage.getItem('user_token')
  },
  loginOut: function () {
    sessionStorage.removeItem('user_token')
    sessionStorage.removeItem('userInfo')
  }
}

export default userService
