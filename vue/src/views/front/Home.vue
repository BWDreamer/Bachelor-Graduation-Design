<template>
  <div>
    <StarBackground />

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

    <!-- 轮播图 -->
    <el-carousel v-if="carouselItems.length" height="250px" class="carousel-container">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <a :href="item.link" target="_blank">
          <img :src="item.value" alt="轮播图" class="carousel-image">
        </a>
      </el-carousel-item>
    </el-carousel>
  </div>


</template>

<script>
import StarBackground from "@/components/StarBackground.vue";

export default {
  name: "Home",
  components: {
    StarBackground,
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
      carouselItems: [],
      user: JSON.parse(localStorage.getItem("web-user") || '{}')
    }
  },
  mounted() {
    this.loadCarouselData();
  },
  methods: {
    loadCarouselData() {
      this.$request.get('/carousel/selectAll').then(res => {
        this.carouselItems = (res.data || []).map(item => ({
          ...item,
          imageUrl: item.cover && `${this.$baseUrl}${item.cover}`,
          link: item.link || '#'
        }))
      });
    },
    logout() {
      localStorage.removeItem('web-user');
      this.$router.push('/login');
    },
  },
  computed: {
    currentIndex() {
      return this.navItems.findIndex(item => this.$route.path === item.path)
    }
  }
}
</script>

<style scoped>
  @import "@/assets/css/home.css";
</style>