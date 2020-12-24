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

  .Input-wrapper /deep/ input {
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
    width:100%;
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
  .bottom-version-div{
    position: absolute;
    margin-top: 20px;
    bottom: 20px;
    text-align: center;
    width: 100%;
    font-size: 12px;
    color: grey;
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
            <el-form :model="registForm" :rules="registFormRules" ref="registForm" label-width="0px" class="demo-dynamic">
              <el-form-item
                prop="email">
                <div class="Input-wrapper">
                  <el-input placeholder="邮箱" v-model="registForm.email"></el-input>
                </div>
              </el-form-item>
              <el-form-item
                prop="code">
                <div class="Input-wrapper">
                  <el-row>
                    <el-col :span="16">
                      <el-input placeholder="请输入验证码" v-model="registForm.code"/>
                    </el-col>
                    <el-col :span="8">
                      <div style="line-height: 47px;cursor: pointer;" @click="sendCode()" v-if="sendCodeTimer === null" id="sendCodeLoading">获取验证码</div>
                      <div style="line-height: 47px;cursor: pointer;" @click="sendCode()" v-else>{{sendCodeTime}}秒后再发送</div>
                    </el-col>
                  </el-row>
                </div>
              </el-form-item>
              <el-form-item
                prop="userName">
                <div class="Input-wrapper">
                  <el-input placeholder="用户名,请输入字母，数字，下划线，减号的组合" v-model="registForm.userName"/>
                </div>
              </el-form-item>
              <el-form-item
                prop="password">
                <div class="Input-wrapper">
                  <el-input placeholder="密码" type="password" v-model="registForm.password"/>
                </div>
              </el-form-item>
            </el-form>
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
              <input placeholder="用户名/邮箱/手机号" v-model="loginData.phone"/>
            </div>
            <div class="Input-wrapper">
              <input placeholder="密码" type="password" v-model="loginData.password"/>
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
    <div class="bottom-version-div">v1.0.1</div>
  </div>
</template>

<script>
import userService from '../common/service/userService'
import validate from '../common/js/validate'
const TIME_COUNT = 60

export default {
  data () {
    var validateEmail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入邮箱地址'))
      }
      if (!validate.email(value)) {
        return callback(new Error('请输入正确的邮箱'))
      }
      this.$http.loadInDiv().get('/regist/checkEmailExsit', {params: {email: value}}).then(
        data => {
          if (data.result) {
            this.cansendCode = false
            return callback(new Error('邮箱已存在'))
          }
          this.cansendCode = true
          callback()
        }
      )
    }
    var validateUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入用户名'))
      }
      if (!validate.userName(value)) {
        return callback(new Error('请输入字母，数字，下划线，减号的组合'))
      }
      this.$http.loadInDiv().get('/regist/checkUserNameExsit', {params: {userName: value}}).then(
        data => {
          if (data.result) {
            return callback(new Error('用户名已存在'))
          }
          callback()
        }
      )
    }
    return {
      showLoginView: false,
      backgroundImg: 'url(' + require('../assets/bg.8ca8122d44fc9a0f7b04.png') + ')',
      sendCodeTimer: null,
      sendCodeTime: 0,
      loginData: {
        phone: '',
        password: ''
      },
      registForm: {
        email: '',
        code: '',
        userName: '',
        password: ''
      },
      cansendCode: false,
      registFormRules: {
        email: [
          {validator: validateEmail, trigger: ['blur', 'change']}
        ],
        userName: [
          {validator: validateUserName, trigger: ['blur', 'change']}
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
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
          if (decodeURIComponent(page.$route.query.redirect) === '/') {
            page.$route.query.redirect = '/home'
          }
          let redirect = decodeURIComponent(page.$route.query.redirect || '/home')
          this.$router.push({// 你需要接受路由的参数再跳转
            path: redirect
          })
        }else{
          this.$message.error(data.desc)
        }
      })
    },
    registStart () {
      this.$refs['registForm'].validate((valid) => {
        if (valid) {
          this.$http.post('/regist',
            {
              address: this.registForm.email,
              userName: this.registForm.userName,
              password: this.registForm.password,
              code: this.registForm.code
            }).then(data => {
            if (data.code === '0') { // 注册成功
              this.$message.success('注册成功')
              this.loginData = {
                phone: this.registForm.email,
                password: this.registForm.password
              }
              this.login()
            } else if (data.code === '5000011') {
              this.$message.error('验证码已过期')
            } else if (data.code === '5000012') {
              this.$message.error('用户名已存在')
            } else if (data.code === '5000013') {
              this.$message.error('验证码错误')
            } else if (data.code === '5000014') {
              this.$message.error('邮箱已存在')
            }
          })
        } else {
          return false
        }
      })
    },
    sendCode () {
      if (this.cansendCode) {
        this.$http.loadInDiv('sendCodeLoading').post('/regist/sendCodeToEmail',
          {
            address: this.registForm.email
          }).then(data => {
          if (data.result) {
            this.$message.success('验证码发送成功')
            this.sendCodeTime = TIME_COUNT
            this.sendCodeTimer = setInterval(() => {
              if (this.sendCodeTime > 0 && this.sendCodeTime <= TIME_COUNT) {
                this.sendCodeTime--
              } else {
                clearInterval(this.sendCodeTimer)
                this.sendCodeTimer = null
              }
            }, 1000)
          } else {
            this.$message.error('邮件发送失败，请检查邮箱是否正确')
          }
        })
      }
    }
  }
}
</script>
