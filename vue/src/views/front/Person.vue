<template>
  <div>
    <canvas ref="canvas" class="star-canvas"></canvas>
    <div class="main-content">
      <el-card style="width: 50%; margin: 30px auto">
        <div style="text-align: right; margin-bottom: 20px">
          <el-button type="primary" @click="dialogVisible = true">修改密码</el-button>
        </div>
        <el-form :model="user" label-width="80px" style="padding-right: 20px">
          <div style="margin: 15px; text-align: center">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:9999/file/upload"
                :headers="{ token: user.token }"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
              <img v-if="user.avatar" :src="user.avatar" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
          <el-form-item label="用户名" prop="username">
            <el-input v-model="user.username" placeholder="用户名" disabled></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="user.name" placeholder="姓名"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="user.phone" placeholder="电话"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="user.email" placeholder="邮箱"></el-input>
          </el-form-item>
          <div style="text-align: center; margin-bottom: 20px">
            <el-button type="primary" @click="update">保 存</el-button>
          </div>
        </el-form>
      </el-card>

      <el-dialog title="修改密码" :visible.sync="dialogVisible" width="30%" :close-on-click-modal="false">
        <el-form ref="formRef" :model="user" :rules="rules" label-width="80px" style="padding-right: 20px">
          <el-form-item label="原始密码" prop="password">
            <el-input show-password v-model="user.password" placeholder="原始密码"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input show-password v-model="user.newPassword" placeholder="新密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input show-password v-model="user.confirmPassword" placeholder="确认密码"></el-input>
          </el-form-item>
          <div style="text-align: center; margin-bottom: 20px">
            <el-button type="primary" @click="update">确认修改</el-button>
          </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "Person",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('两次密码不一致'))
      } else {
        callback()
      }
    }
    return {
      user: JSON.parse(localStorage.getItem('web-user') || '{}'),
      dialogVisible: false,
      //动画相关变量
      scale: 1,
      width: 0,
      height: 0,
      stars: [],
      pointerX: null,
      pointerY: null,
      velocity: { x: 0, y: 0, tx: 0, ty: 0, z: 0.0009 },
      touchInput: false,
      animationFrame: null,
      rules: {
        password: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      }
    }
  },
  beforeDestroy() {
    // 清理事件监听
    window.removeEventListener('resize', this.handleResize);
    const canvas = this.$refs.canvas;
    canvas.removeEventListener('mousemove', this.onMouseMove);
    canvas.removeEventListener('touchmove', this.onTouchMove);
    canvas.removeEventListener('touchend', this.onMouseLeave);
    document.removeEventListener('mouseleave', this.onMouseLeave);
    cancelAnimationFrame(this.animationFrame);
  },
  mounted() {
    this.initStarCanvas()
    // 添加事件监听
    const canvas = this.$refs.canvas;
    window.addEventListener('resize', this.handleResize);
    canvas.addEventListener('mousemove', this.onMouseMove);
    canvas.addEventListener('touchmove', this.onTouchMove, { passive: false });
    canvas.addEventListener('touchend', this.onMouseLeave);
    document.addEventListener('mouseleave', this.onMouseLeave);
  },
  methods: {
    update() {
      // 保存当前的用户信息到数据库
      this.$request.put('/user/update', this.user).then(res => {
        if (res.code === '200') {
          this.$message.success('保存成功')
          localStorage.setItem('web-user', JSON.stringify(this.user))
          // 触发父级的数据更新
          this.$emit('update:user', this.user)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    save() {  // 添加新的保存方法
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          const params = {
            password: this.user.password,
            newPassword: this.user.newPassword
          }
          this.$request.put('/password', params).then(res => {
            if (res.code === '200') {
              this.$message.success('密码修改成功')
              this.dialogVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.user.avatar = response.data
    },
    // 星空动画方法
    initStarCanvas() {
      const canvas = this.$refs.canvas;
      const context = canvas.getContext('2d');
      this.generateStars();
      this.handleResize();
      this.step(context);
    },
    generateStars() {
      const STAR_COUNT = (window.innerWidth + window.innerHeight) / 8;
      this.stars = [];
      for (let i = 0; i < STAR_COUNT; i++) {
        this.stars.push({
          x: 0,
          y: 0,
          z: 0.2 + Math.random() * 0.8
        });
      }
      this.stars.forEach(star => this.placeStar(star));
    },
    placeStar(star) {
      star.x = Math.random() * this.width;
      star.y = Math.random() * this.height;
    },
    handleResize() {
      this.scale = window.devicePixelRatio || 1;
      this.width = window.innerWidth * this.scale;
      this.height = window.innerHeight * this.scale;
      const canvas = this.$refs.canvas;
      canvas.width = this.width;
      canvas.height = this.height;
      this.stars.forEach(star => this.placeStar(star));
    },
    step(context) {
      context.clearRect(0, 0, this.width, this.height);
      this.updateStars();
      this.renderStars(context);
      this.animationFrame = requestAnimationFrame(() => this.step(context));
    },
    updateStars() {
      this.velocity.tx *= 0.96;
      this.velocity.ty *= 0.96;
      this.velocity.x += (this.velocity.tx - this.velocity.x) * 0.8;
      this.velocity.y += (this.velocity.ty - this.velocity.y) * 0.8;

      this.stars.forEach(star => {
        star.x += this.velocity.x * star.z;
        star.y += this.velocity.y * star.z;
        star.x += (star.x - this.width/2) * this.velocity.z * star.z;
        star.y += (star.y - this.height/2) * this.velocity.z * star.z;
        star.z += this.velocity.z;

        if (star.x < -50 || star.x > this.width + 50 ||
            star.y < -50 || star.y > this.height + 50) {
          this.recycleStar(star);
        }
      });
    },
    recycleStar(star) {
      let direction = 'z';
      const vx = Math.abs(this.velocity.x);
      const vy = Math.abs(this.velocity.y);

      if (vx > 1 || vy > 1) {
        let axis;
        if (vx > vy) {
          axis = Math.random() < vx / (vx + vy) ? 'h' : 'v';
        } else {
          axis = Math.random() < vy / (vx + vy) ? 'v' : 'h';
        }

        if (axis === 'h') {
          direction = this.velocity.x > 0 ? 'l' : 'r';
        } else {
          direction = this.velocity.y > 0 ? 't' : 'b';
        }
      }

      star.z = 0.2 + Math.random() * 0.8;

      switch(direction) {
        case 'l':
          star.x = -50;
          star.y = this.height * Math.random();
          break;
        case 'r':
          star.x = this.width + 50;
          star.y = this.height * Math.random();
          break;
        case 't':
          star.x = this.width * Math.random();
          star.y = -50;
          break;
        case 'b':
          star.x = this.width * Math.random();
          star.y = this.height + 50;
          break;
        default:
          star.z = 0.1;
          star.x = Math.random() * this.width;
          star.y = Math.random() * this.height;
      }
    },
    renderStars(context) {
      context.clearRect(0, 0, this.width, this.height);
      this.stars.forEach(star => {
        context.beginPath();
        context.lineCap = 'round';
        context.lineWidth = 3 * star.z * this.scale;
        context.globalAlpha = 0.5 + 0.5 * Math.random();
        context.strokeStyle = '#fff';

        const tailX = this.velocity.x * 2;
        const tailY = this.velocity.y * 2;
        context.moveTo(star.x, star.y);
        context.lineTo(star.x + tailX, star.y + tailY);
        context.stroke();
      });
    },
    movePointer(x, y) {
      if (this.pointerX !== null && this.pointerY !== null) {
        const ox = x - this.pointerX;
        const oy = y - this.pointerY;
        this.velocity.tx += (ox / 8) * this.scale * (this.touchInput ? 1 : -1);
        this.velocity.ty += (oy / 8) * this.scale * (this.touchInput ? 1 : -1);
      }
      this.pointerX = x;
      this.pointerY = y;
    },
    onMouseMove(e) {
      this.touchInput = false;
      this.movePointer(e.clientX, e.clientY);
    },
    onTouchMove(e) {
      this.touchInput = true;
      this.movePointer(e.touches[0].clientX, e.touches[0].clientY);
      e.preventDefault();
    },
    onMouseLeave() {
      this.pointerX = null;
      this.pointerY = null;
    }
  }
}
</script>

<style scoped>
/* 星空画布样式 */
.star-canvas {
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
  width: 100%;
  height: 100%;
  background-image: linear-gradient(-225deg, #231557 0%, #43107a 29%, #FF1361 100%);
}

.main-content {
  display: flex;
  min-height: 100vh;
  justify-content: center;
  align-items: flex-start;
}

:deep(.el-card) {
  width: 55% !important;
  margin: 60px auto 0;
  background: rgba(255, 255, 255, 0.85);
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.12);
}

/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}

/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
</style>