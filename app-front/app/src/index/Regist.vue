<style lang="scss" scoped>
  .SignCard {
    width: 432px;
    margin: 0 auto;
    text-align: center;
    border-radius: 2px;
    box-shadow: 0 1px 3px rgba(26, 26, 26, .1);
    box-sizing: border-box;
    background: #fff;
  }

  .SignFlowHeader {
    padding-top: 30px;
    padding-bottom: 5px;
    color: #0084ff;
  }

  .SignFlowHeader-logo {
    font-size: 40px;
  }

  .SignFlowHeader-slogen {
    margin-top: 15px;
    font-size: 22px;
  }

  .Input-wrapper {
    width: 100%;
    height: 48px;
    padding: 0 20px;
    color: #8590a6;
    border: none;
    border-bottom: 1px solid #ebebeb;
    border-radius: 0;
  }

  .Input-wrapper > input {
    font: 400 13.3333px Arial;
    height: 48px;
    line-height: 24px;
    flex: 1;
    padding: 0;
    overflow: hidden;
    font-family: inherit;
    font-size: inherit;
    font-weight: inherit;
    background: transparent;
    border: none;
    outline: none;
    resize: none;
  }

  .Input-wrapper > span {
    line-height: 48px;
    float: right
  }

  .SignFlowBody {
    padding: 0 40px 36px;
    font-size: 14px;
    text-align: left;
  }

  .SignContainer-switch {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    width: 100%;
    background-color: #f6f6f6;
    height: 58px;
    border-top: 1px solid #ebebeb;
    font-size: 16px;
  }

  .SignContainer-switch span {
    color: #175199;
    cursor: pointer;
  }

  .SignContainer-big-button {
    width: 100%;
    margin: auto;
    padding-bottom: 20px;
    button{
      width: 80%;
    }
  }
</style>
<template>
  <div v-bind:style="{ backgroundImage: backgroundImg ,height: '100%'}">
    <div>&nbsp;</div>
    <div class="SignCard" id="loadingMainPanel" style="margin-top: 10%;">
      <div v-if="showLoginView">
        <div>
          <div class="SignFlowHeader">
            <div class="SignFlowHeader-logo">WangSheng</div>
          </div>
          <div class="SignFlowBody">
            <div class="Input-wrapper">
              <input placeholder="手机号" v-model="registData.phone"/>
            </div>
            <div class="Input-wrapper">
              <input placeholder="请输入验证码" v-model="registData.code"/>
              <span>获取短信验证码</span>
            </div>
          </div>
          <div class="SignContainer-big-button">
            <el-button type="primary" @click="registStart()">注册</el-button>
          </div>
        </div>
        <div class="SignContainer-switch" @click="showLoginView = !showLoginView">
          已有账号？
          <span>登录</span>
        </div>
      </div>
      <div v-else>
        <div>
          <div class="SignFlowHeader">
            <div class="SignFlowHeader-logo">WangSheng</div>
          </div>
          <div class="SignFlowBody">
            <div class="Input-wrapper">
              <input placeholder="手机号" v-model="loginData.phone"/>
            </div>
            <div class="Input-wrapper">
              <input placeholder="密码" v-model="loginData.password"/>
            </div>
          </div>
          <div class="SignContainer-big-button">
            <el-button type="primary" @click="login()">登录</el-button>
          </div>
        </div>
        <div class="SignContainer-switch" @click="showLoginView = !showLoginView">
          没有账号？
          <span>注册</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import userService from '../common/service/userService'

export default {
  data () {
    return {
      showLoginView: false,
      backgroundImg: 'url(' + require('../assets/bg.8ca8122d44fc9a0f7b04.png') + ')',
      registData: {
        phone: '',
        code: ''
      },
      loginData: {
        phone: '',
        password: ''
      }
    }
  },
  methods: {
    login () {
      this.openLogin = false
      let page = this
      this.$http.post('/login/auth',
        {
          username: this.loginData.phone,
          password: this.loginData.password
        }).then(data => {
        if (data.code === '0') {
          userService.saveUserInfoAndToken(data.result, data.result.token)
          let redirect = decodeURIComponent(page.$route.query.redirect || '/home')
          this.$router.push({// 你需要接受路由的参数再跳转
            path: redirect
          })
        }
      })
    },
    registStart () {
      this.$http.post('/regist/sendCodeToEmail',
        {
          address: this.registData.phone,
          userName: this.registData.code
        }).then(data => {
        if (data.code === '0') {
          // TODO
        }
      })
    }
  }
}
</script>
