<template>
  <div class="register-container">
    <!-- 背景视频 -->
    <video id="videofilm" poster="@/assets/SkyStar/img/skystar2.jpg" autoplay muted loop>
      <source src="@/assets/SkyStar/video/skystar.mp4" type="video/mp4">
    </video>

    <!-- 注册框 -->
    <div class="register-box">
      <div style="display: flex; background-color: white; width: 80%; border-radius: 5px; overflow: hidden; height: 57vh">
        <div style="flex: 1; height: 100%; position: relative">
          <img src="@/assets/register.png" alt="" style="width: 100%; height: 100%; object-fit: cover">
        </div>
        <div style="flex: 1; display: flex; align-items: center; justify-content: center">
          <el-form :model="user" style="width: 60%" :rules="rules" ref="registerRef">
            <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px">欢迎注册游戏分享网站</div>
            <el-form-item prop="username">
              <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.username"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPass">
              <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请确认密码" v-model="user.confirmPass"></el-input>
            </el-form-item>
            <el-form-item prop="role">
              <el-radio-group v-model="user.role" style="display: none">
                <el-radio label="用户" checked></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="info" style="width: 100%" @click="register">注 册</el-button>
            </el-form-item>
            <div style="display: flex">
              <div style="flex: 1">已经有账号了？请<span style="color: #6E77F2; cursor: pointer" @click="$router.push('/login')">登录</span></div>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>


<script>

export default {
  name: "Register",
  data() {
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if(confirmPass !== this.user.password){
        callback(new Error('两次输入的密码不一致'))
      }else {
        callback();
      }
    }
    return {
      user:{
        username:'',
        password:'',
        confirmPass:'',
        role: '用户'  // 设置默认角色
      },
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'blur' }
        ],
      }
    }
  },
  created() {

  },
  methods: {
    register() {
      this.$refs['registerRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/register', this.user).then(res => {
            if (res.code === '200') {
              this.$router.push('/login')
              this.$message.success('注册成功')
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
/* 新增与登录页一致的样式 */
.register-container {
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

.register-box {
  position: relative;
  z-index: 2;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 表单样式优化 */
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