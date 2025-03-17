<template>
  <div class="login-container">
    <!-- 背景视频 -->
    <video id="videofilm" poster="@/assets/SkyStar/img/skystar2.jpg" autoplay muted loop>
      <source src="@/assets/SkyStar/video/skystar.mp4" type="video/mp4">
    </video>

    <!-- 登录框 -->
    <div class="login-box">
      <div style="display: flex; background-color: white; width: 80%; border-radius: 5px; overflow: hidden; height: 50vh">
        <div style="flex: 1; height: 100%; position: relative">
          <img src="@/assets/login.png" alt="" style="width: 100%; height: 100%; object-fit: cover">
        </div>
        <div style="flex: 1; display: flex; align-items: center; justify-content: center">
          <el-form :model="user" style="width: 60%" :rules="rules" ref="loginRef">
            <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px">欢迎登录游戏分享网站</div>
            <el-form-item prop="username">
              <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.username"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
            </el-form-item>
            <el-form-item prop="code">
              <div style="display: flex">
                <el-input placeholder="请输入验证码" prefix-icon="el-icon-circle-check" size="medium" style="flex: 1" v-model="user.code"></el-input>
                <div style="flex: 1; height: 36px">
                  <valid-code @update:value="getCode" />
                </div>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" style="width: 100%" @click="login">登 录</el-button>
            </el-form-item>
            <div style="display: flex">
              <div style="flex: 1">还没有账号？请<span style="color: #0f9876; cursor: pointer" @click="$router.push('/register')">注册</span></div>
              <div style="flex: 1; text-align: right"><span style="color: #0f9876; cursor: pointer" @click="handleForgetPass">忘记密码</span></div>
            </div>
          </el-form>
        </div>
      </div>

      <!-- 忘记密码弹窗 -->
      <el-dialog title="忘记密码" :visible.sync="forgetPassDialogVis" width="30%" :modal="false">
        <el-form :model="forgetUserForm" label-width="80px" style="padding-right: 20px">
          <el-form-item label="用户名">
            <el-input v-model="forgetUserForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="forgetUserForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="forgetPassDialogVis = false">取 消</el-button>
          <el-button type="primary" @click="resetPassword">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>


<script>
import ValidCode from "@/components/ValidCode.vue";

export default {
  name: "Login",
  components: {
    ValidCode
  },
  data() {

    //验证码校验
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else if(value.toLowerCase() !== this.code){
        callback(new Error('验证码错误'))
      }else {
        callback();
      }
    }

    return {
      forgetUserForm: {},   // 忘记密码的表单数据
      forgetPassDialogVis: false, // 忘记密码的弹窗
      code:'',  // 验证码组件传递过来的code
      user:{
        code:'',  // 表单里用户输入的code验证码
        username:'',
        password:''
      },
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ],
      },
    }
  },
  mounted() {
    this.initStars();
    this.startTextAnimation();
  },
  beforeDestroy() {
    clearTimeout(this.textTimer1);
    clearTimeout(this.textTimer2);
  },
  created() {},
  methods: {
    getCode(code) {
      this.code=code.toLowerCase()
    },
    login() {
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/login', this.user).then(res => {
            if (res.code === '200') {
              this.$router.push('/')
              this.$message.success('登陆成功')
              localStorage.setItem("web-user", JSON.stringify(res.data))  // 存储用户数据
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    handleForgetPass() {   //  初始化表单的数据
      this.forgetUserForm = {}
      this.forgetPassDialogVis = true
    },
    resetPassword() {
      this.$request.put('/password', this.forgetUserForm).then(res => {
        if (res.code === '200') {
          this.$message.success('重置成功，密码重置为123')
          this.forgetPassDialogVis = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
  }
}
</script>

<style scoped>
.login-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
  background-color: rgba(15, 152, 118, 0.2);
}

#videofilm {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  min-width: 100%;
  min-height: 100%;
  z-index: 0;
  filter: brightness(0.8);
}

.login-box {
  position: relative;
  z-index: 2;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 原有登录框样式优化 */
::v-deep .el-form {
  background: rgba(255, 255, 255, 0.95);
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.2);
}

/* 响应式适配 */
@media (max-width: 768px) {
  .login-box {
    width: 95% !important;
  }
}
</style>
