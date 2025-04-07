<template>
  <div>
    <canvas ref="canvas" class="star-canvas"></canvas>
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

import Footer from "@/components/Footer";
import BlogList from "@/components/BlogList.vue";

export default {
  components: {
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
      //动画相关变量
      scale: 1,
      width: 0,
      height: 0,
      stars: [],
      pointerX: null,
      pointerY: null,
      velocity: { x: 0, y: 0, tx: 0, ty: 0, z: 0.0009 },
      touchInput: false,
      animationFrame: null
    }
  },
  mounted() {
    this.load()
    this.refreshTop()
    this.loadTopActivity()
    this.initStarCanvas()
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
  methods: {
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
    // 星空动画方法
    initStarCanvas() {
      const canvas = this.$refs.canvas;
      const context = canvas.getContext('2d');
      this.generateStars();
      this.handleResize();

      // 添加事件监听
      window.addEventListener('resize', this.handleResize);
      canvas.addEventListener('mousemove', this.onMouseMove);
      canvas.addEventListener('touchmove', this.onTouchMove);
      canvas.addEventListener('touchend', this.onMouseLeave);
      document.addEventListener('mouseleave', this.onMouseLeave);

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

<style>
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