<template>
  <div>
    <StarBackground />

    <!-- 轮播图 -->
    <el-carousel class="carousel-container" v-if="carouselItems.length" height="250px">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <a :href="item.link" target="_blank">
          <img :src="item.value" alt="轮播图" class="carousel-image">
        </a>
      </el-carousel-item>
    </el-carousel>

    <div class="main-content">
      <div style="display: flex; align-items: flex-start; grid-gap: 10px; padding: 20px; max-width: 1200px; margin: 0 auto">

        <div style="width: 150px" class="card">
          <div class="category-item" :class="{ 'category-item-active': item.name === current }"
               v-for="item in categoryList" :key="item.id" @click="selectCategory(item.name)">{{ item.name }}</div>
        </div>

        <div style="flex: 1;">
          <BlogList :categoryName="current" ref="blogListRef"/>
          <Footer />
        </div>

        <div style="width: 260px">
          <div class="card" style="margin-bottom: 10px">
            <div style="font-size: 20px; font-weight: bold; margin-bottom: 10px">欢迎您！😊</div>
            <a href="/front/person"><div style="color: #666">写文章来支持国产游戏走向世界</div></a>
          </div>

          <div class="card" style="margin-bottom: 10px">
            <div style="display: flex; align-items: center; padding-bottom: 10px; border-bottom: 1px solid #ddd">
              <div style="font-size: 20px; flex: 1">热门游戏榜单</div>
              <div style="font-size: 12px; color: #666; cursor: pointer;" @click="refreshTop"><i class="el-icon-refresh"></i> 换一换</div>
            </div>
            <div>
              <div v-for="item in showList" :key="item.id" style="margin: 15px 0" class="line1">
                <a :href="'/front/blogDetail?blogId=' + item.id" target="_blank">
                  <span style="width: 18px; display: inline-block; text-align: right; margin-right: 10px">
                    <span style="color: orangered" v-if="item.index === 1">{{ item.index }}</span>
                    <span style="color: goldenrod" v-else-if="item.index === 2">{{ item.index }}</span>
                    <span style="color: dodgerblue" v-else-if="item.index === 3">{{ item.index }}</span>
                    <span style="color: #666" v-else>{{ item.index }}</span>
                  </span>
                  <span style="color: #666;">{{ item.title }}</span>
                </a>
              </div>
            </div>
          </div>

          <div style="margin-bottom: 10px">
            <div v-for="item in topActivityList" :key="item.id" style="margin-bottom: 10px">
              <a :href="'/front/activityDetail?activityId=' + item.id" target="_blank"><img :src="item.cover" alt="" style="width: 100%; border-radius: 5px"></a>
            </div>
          </div>

          <div style="line-height: 30px; color: #333; padding: 0 10px">
            <div>举报邮箱： 1262276637@qq.com</div>
            <div> 座机电话： (+86) 13103187479</div>
            <div> 京ICP备888888©2025 CQUPT</div>
          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<script>
import StarBackground from "@/components/StarBackground.vue";
import Footer from "@/components/Footer";
import BlogList from "@/components/BlogList.vue";

export default {
  name: "Blog",
  components: {
    StarBackground,
    BlogList,
    Footer
  },
  data() {
    return {
      current: '全部游戏类型',  //当前选中的分类名称
      categoryList: [],
      topList: [],
      showList: [],
      lastIndex: 0,
      topActivityList: [],
      carouselItems: [],
    }
  },
  mounted() {
    this.load()
    this.refreshTop()
    this.loadTopActivity()
    this.loadCarouselData()
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
    loadTopActivity() {
      this.$request.get('/activity/selectTop').then(res => {
        this.topActivityList = res.data || []
      })
    },
    refreshTop() {
      this.$request.get('/blog/selectTop', {
        params: {
          status: '通过'
        }
      }).then(res => {
        this.topList = res.data || []
        let i = 1
        this.topList.forEach(item => item.index = i++)

        // 0  5  0
        if (this.lastIndex === 10) {
          this.lastIndex = 0
        }
        this.showList = this.topList.slice(this.lastIndex, this.lastIndex+5)  // 0-5   5- 10   0-5
        this.lastIndex += 5  // 5  10  5
      })
    },
    selectCategory(categoryName) {
      this.current = categoryName
    },
    load() {
      // 请求分类的数据
      this.$request.get('/category/selectAll').then(res => {
        this.categoryList = res.data || []
        this.categoryList.unshift({ name: '全部游戏类型' })
      })
    },
  }
}
</script>

<style>

.main-content {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  padding: 20px 0;
}

.card {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 15px;
}

.category-item {
  text-align: center;
  padding: 10px 0;
  font-size: 16px;
  cursor: pointer;
}

.category-item-active {
  background-color: #1890ff;
  color: #fff;
  border-radius: 5px;
}

</style>