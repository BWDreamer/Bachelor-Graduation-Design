<template>
  <div class="page-container">
    <div class="fixed-box">
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
    </div>

    <!-- 视差滚动区块 -->
    <div class="shell">
      <div class="image"
           :style="{ backgroundImage: `url(${require('@/assets/imgs/BlackMyth.jpg')})` }">
      </div>
      <div class="heading">
        <h1>游戏文章</h1>
      </div>
      <div class="text">
        <h1>更多内容尽在游戏文章</h1>
      </div>
      <div class="image"
           :style="{ backgroundImage: `url(${require('@/assets/imgs/CivilizationVI.jpg')})` }">
      </div>
      <div class="heading">
        <h1>游戏资讯</h1>
      </div>
      <div class="text">
        <h1>更多内容尽在游戏资讯</h1>
      </div>
      <div class="image"
           :style="{ backgroundImage: `url(${require('@/assets/imgs/EldenRing.jpg')})` }">
      </div>
      <div class="heading">
        <h1>交流论坛</h1>
      </div>
      <div class="text">
        <h1>更多内容尽在交流论坛</h1>
      </div>
      <div class="image"
           :style="{ backgroundImage: `url(${require('@/assets/imgs/CyberPunk2077.jpg')})` }">
      </div>
      <div class="heading">
        <h1>个人中心</h1>
      </div>
      <div class="text">
        <h1>更多内容尽在个人中心</h1>
      </div>
    </div>


  </div>


</template>

<script>

export default {
  name: "Home",
  components: {
  },
  data() {
    return {
      activeNav: 0,
      navItems: [
        { name: '首页', path: '/front/home' },
        { name: '游戏文章', path: '/front/blog' },
        { name: '游戏资讯', path: '/front/activity' },
        { name: '交流论坛', path: '/front/chat' },
        { name: '个人中心', path: '/front/person' },
        { name: '后台管理', path: '/home' }
      ],
      user: JSON.parse(localStorage.getItem("web-user") || '{}')
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('web-user');
      this.$router.push('/login');
    },
  },
}
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
}
.fixed-box {
  box-sizing: border-box;
  padding-top: 110px;
  height: 110px;
}
.shell {
  flex: 1;
  overflow-x: hidden;
  perspective: 3px;
}

.shell div {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  font-style: 30px;
  letter-spacing: 2px;
}

.image {
  transform: translateZ(-1px) scale(1.6);
  background-size: cover;
  height: 100%;
  z-index: -1;
}

.text {
  height: 50vh;
  background-color: #fff;
}

.text h1 {
  color: #000;
}

.heading {
  z-index: -1;
  transform: translateY(-30vh) translateZ(1px);
  color: #fff;
  font-size: 30px;
}

@import "@/assets/css/home.css";
</style>