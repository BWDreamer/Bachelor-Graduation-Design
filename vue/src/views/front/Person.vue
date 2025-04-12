<template>
  <div>
    <!-- 顶部导航 -->
    <div class="nav-top">
      <div class="user-info">
        <div v-if="user.name">
          <el-dropdown>
            <div class="front-header-dropdown">
              <img :src="user.avatar" alt="">
              <div style="margin-left: 10px; color: #fff">
                <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div style="text-decoration: none" @click="logout">退出</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>

    <!-- 主导航 -->
    <div class="navs">
      <el-menu :default-active="$route.path" mode="horizontal" router class="el-menu-demo">
        <el-menu-item v-for="(item, index) in navItems" :key="index" :index="item.path">
          {{ item.name }}
        </el-menu-item>
      </el-menu>
    </div>

    <div class="main-content" style="width: 50%; margin-top: 40px">
      <el-tabs v-model="activeName" @tab-click="clickTab">
        <el-tab-pane label="个人资料" name="个人资料">
          <person-page @update:user="updateUser" />
        </el-tab-pane>
        <el-tab-pane label="我发表的文章" name="我发表的文章">
          <div class="card" style="padding: 5px"><el-button type="primary" @click="addBlog">发表新文章</el-button></div>
          <div style="margin-top: 10px">
            <blog-list type="user" :show-opt="true" />
          </div>
        </el-tab-pane>
        <el-tab-pane label="我报名的活动" name="我报名的活动">
          <activity-list type="user" :span="8" />
        </el-tab-pane>
        <el-tab-pane label="我的点赞" name="我的点赞">
          <div class="card" style="padding: 5px; display: flex">
            <div class="category-btn" :class="{'active' : likesCurrent==='文章'}" @click="likesCurrent='文章'">文章</div>
            <div class="category-btn" :class="{'active' : likesCurrent==='活动'}" @click="likesCurrent='活动'">活动</div>
          </div>
          <div style="margin-top: 10px">
            <blog-list v-if="likesCurrent==='文章'" type="like"></blog-list>
            <activity-list v-if="likesCurrent==='活动'" :span="8" type="like"></activity-list>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的收藏" name="我的收藏">
          <div class="card" style="padding: 5px; display: flex">
            <div class="category-btn" :class="{'active' : collectCurrent==='文章'}" @click="collectCurrent='文章'">文章</div>
            <div class="category-btn" :class="{'active' : collectCurrent==='活动'}" @click="collectCurrent='活动'">活动</div>
          </div>
          <div style="margin-top: 10px">
            <blog-list v-if="collectCurrent==='文章'" type="collect"></blog-list>
            <activity-list v-if="collectCurrent==='活动'" :span="8" type="collect"></activity-list>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的评论" name="我的评论">
          <div class="card" style="padding: 5px; display: flex">
            <div class="category-btn" :class="{'active' : commentCurrent==='文章'}" @click="commentCurrent='文章'">文章</div>
            <div class="category-btn" :class="{'active' : commentCurrent==='活动'}" @click="commentCurrent='活动'">活动</div>
          </div>
          <div style="margin-top: 10px">
            <blog-list v-if="commentCurrent==='文章'" type="comment"></blog-list>
            <activity-list v-if="commentCurrent==='活动'" :span="8" type="comment"></activity-list>
          </div>
        </el-tab-pane>
      </el-tabs>

      <Footer />
    </div>
  </div>

</template>

<script>
import Footer from "@/components/Footer";
import PersonPage from "@/components/PersonPage";
import BlogList from "@/components/BlogList";
import ActivityList from "@/components/ActivityList";

export default {
  components: {
    ActivityList,
    BlogList,
    Footer,
    PersonPage,
  },
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('确认密码错误'))
      } else {
        callback()
      }
    }
    return {
      user: JSON.parse(localStorage.getItem("web-user") || '{}'),
      dialogVisible: false,
      navItems: [
        { name: '首页', path: '/front/home' },
        { name: '游戏文章', path: '/front/blog' },
        { name: '游戏资讯', path: '/front/activity' },
        { name: '交流论坛', path: '/front/chat' },
        { name: '个人中心', path: '/front/person' },
        { name: '后台管理', path: '/home' }
      ],
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
      },
      activeName: '个人资料',
      likesCurrent: '文章',
      collectCurrent: '文章',
      commentCurrent: '文章',
    }
  },
  computed: {
    currentIndex() {
      return this.navItems.findIndex(item => this.$route.path === item.path)
    }
  },
  methods: {
    updateUser() {
      // 触发父级的数据更新
      this.$emit('update:user')
    },
    addBlog() {
      window.open('/front/newBlog')
    },
    clickTab(tab) {
      console.log(tab)
    },
    update() {
      // 保存当前的用户信息到数据库
      this.$request.put('/user/update', this.user).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('保存成功')
          // 更新浏览器缓存里的用户信息
          localStorage.setItem('web-user', JSON.stringify(this.user))

          // 触发父级的数据更新
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.user, 'avatar', response.data)
    },
    // 修改密码
    updatePassword() {
      this.dialogVisible = true
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.put('/user/update', this.user).then(res => {
            if (res.code === '200') {
              // 成功更新
              this.$message.success('修改密码成功')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    logout() {
      localStorage.removeItem('web-user');
      this.$router.push('/login');
    },
  }
}
</script>

<style scoped>
  @import "@/assets/css/home.css";

/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 50%;
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
.category-btn {
  width: fit-content;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}
.active{
  background-color: #2a60c9 !important;
  color: white !important;
}
</style>