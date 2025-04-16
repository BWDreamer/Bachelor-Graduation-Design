<template>
  <div>
    <StarBackground/>

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
    <el-carousel class="carousel-container" v-if="carouselItems.length" height="250px">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <a :href="item.link" target="_blank">
          <img :src="item.value" alt="轮播图" class="carousel-image">
        </a>
      </el-carousel-item>
    </el-carousel>

    <div class="main-content" style="margin-top: 40px">
      <activity-list :span="6" />
      <Footer />
    </div>

  </div>
</template>

<script>
import Footer from "@/components/Footer";
import ActivityList from "@/components/ActivityList";
import StarBackground from "@/components/StarBackground.vue";

export default {
  name: "Activity",
  components: {
    ActivityList,
    Footer,
    StarBackground
  },

  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 8,  // 每页显示的个数
      total: 0,
      name: null,
      navItems: [
        { name: '首页', path: '/front/home' },
        { name: '游戏文章', path: '/front/blog' },
        { name: '游戏资讯', path: '/front/activity' },
        { name: '交流论坛', path: '/front/chat' },
        { name: '个人中心', path: '/front/person' },
        { name: '后台管理', path: '/home' }
      ],
      user: {
        username: localStorage.getItem('web-user') ? JSON.parse(localStorage.getItem('web-user')).username : ''
      },
      carouselItems: [],
    }
  },
  mounted() {
    this.loadCarouselData()
  },
  created() {
    this.load(1)
  },
  computed: {
    currentIndex() {
      return this.navItems.findIndex(item =>
          this.$route.path.startsWith(item.path.split('/')[1])
      )
    }
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
    goDetail(id) {
      window.open('/front/activityDetail?activityId=' + id)
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/activity/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
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
</style>