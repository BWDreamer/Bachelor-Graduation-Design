<template>
  <div>
    <canvas ref="canvas" class="star-canvas"></canvas>
    <div class="main-content">
      <div style="display: flex; grid-gap: 10px">

        <div style="flex: 1; width: 0">
          <div class="card" style="padding: 30px; margin-bottom: 10px">
            <div style="font-weight: bold; font-size: 24px; margin-bottom: 20px">{{ blog.title }}</div>
            <div style="color: #666; margin-bottom: 20px">
              <span style="margin-right: 20px"><i class="el-icon-user"></i> {{ blog.userName }}</span>
              <span style="margin-right: 20px"><i class="el-icon-date"></i> {{ blog.date }}</span>
              <span style="margin-right: 20px"><i class="el-icon-eye"></i> {{ blog.readCount }}</span>
              <span>
              <el-tag v-for="item in tagsArr" :key="item" type="primary" style="margin-right:5px">{{ item }}</el-tag>
            </span>
            </div>

            <div class="w-e-text">
              <div v-html="blog.content"></div>
            </div>

          </div>

          <!--    点赞和收藏数据    -->
          <div class="card" style="text-align: center; font-size: 20px; color: #666; margin-bottom: 10px">
            <span style="margin-right: 20px; cursor: pointer;" @click="setLikes" :class="{ 'active' : blog.userLike }"><i class="el-icon-like"></i> {{ blog.likesCount }}</span>
            <span style=" cursor: pointer"  @click="setCollect" :class="{ 'active' : blog.userCollect }"><i class="el-icon-star-off"></i> {{ blog.collectCount }}</span>
          </div>

          <!--评论-->
          <Comment :fid="blogId" module="游戏文章" />

        </div>

        <div style="width: 260px">
          <div class="card" style="margin-bottom: 10px">
            <div style="display: flex; align-items: center; grid-gap: 10px; margin-bottom: 10px">
              <img :src="blog.user?.avatar" alt="" style="width: 50px; height: 50px; border-radius: 50%">
              <div style="flex: 1;">
                <div style="font-weight: bold; margin-bottom: 5px">{{ blog.user?.name }}</div>
              </div>
            </div>

            <div style="display: flex">
              <div style="flex: 1; text-align: center">
                <div style="margin-bottom: 5px">文章</div>
                <div style="color: #888">{{ blog.user?.blogCount }}</div>
              </div>
              <div style="flex: 1; text-align: center">
                <div style="margin-bottom: 5px">点赞</div>
                <div style="color: #888">{{ blog.user?.likesCount }}</div>
              </div>
              <div style="flex: 1; text-align: center">
                <div style="margin-bottom: 5px">收藏</div>
                <div style="color: #888">{{ blog.user?.collectCount }}</div>
              </div>
            </div>
          </div>

          <div class="card" style="margin-bottom: 10px">
            <div style="font-weight: bold; font-size: 20px; padding-bottom: 10px; border-bottom: 1px solid #ddd; margin-bottom: 10px">相关推荐</div>

            <div>
              <div style="margin-bottom: 15px" v-for="item in recommendList" :key="item.id">
                <a :href="'/front/blogDetail?blogId=' + item.id" target="_blank"><div class="recommend-title line2">{{ item.title }}</div></a>
                <div style="color: #888">
                  <span>阅读</span> <span>{{ item.readCount }}</span>
                  <span style="margin-left: 10px">点赞</span> <span>{{ item.likesCount }}</span>
                </div>
              </div>
            </div>
          </div>

          <div class="card">
            <div style="display: flex; grid-gap: 10px; ">
              <div style="flex: 1; line-height: 25px">
                推动中国游戏走出国门！
                助力中华文化走向世界！
              </div>
              <img src="@/assets/logo1.png" alt="" style="width: 50px; height: 50px; border-radius: 5px">
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  </div>
</template>


<script>
import Footer from "@/components/Footer";
import Comment from "@/components/Comment";

export default {
  name: "BlogDetail",
  components: {
    Comment,
    Footer
  },
  data() {
    return {
      blogId: this.$route.query.blogId,
      blog: {},
      tagsArr: [],
      recommendList: [],
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
    }
  },
  created() {
    this.load()
    this.$request.put('/blog/updateReadCount/' + this.blogId)
  },
  mounted() {
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
    setLikes() {
      this.$request.post('/likes/set', {  fid: this.blogId, module: '游戏文章' }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')

          this.load()  // 重新加载数据
        }
      })
    },
    setCollect() {
      this.$request.post('/collect/set', {  fid: this.blogId, module: '游戏文章' }).then(res => {
        if (res.code === '200') {
          this.$message.success('操作成功')

          this.load()  // 重新加载数据
        }
      })
    },
    load() {
      this.$request.get('/blog/selectById/' + this.blogId).then(res => {
        this.blog = res.data || {}

        this.tagsArr = JSON.parse(this.blog.tags || '[]')
      })

      this.$request.get('/blog/selectRecommend/' + this.blogId).then(res => {
        this.recommendList = res.data || []
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

/* blockquote 样式 */
blockquote {
  display: block;
  border-left: 8px solid #d0e5f2;
  padding: 20px 10px;
  margin: 10px 0;
  line-height: 1.4;
  font-size: 100%;
  background-color: #f1f1f1;
}

/* code 样式 */
code {
  display: inline-block;
  *display: inline;
  *zoom: 1;
  background-color: #f1f1f1;
  border-radius: 3px;
  padding: 3px 5px;
  margin: 0 3px;
}
pre code {
  display: block;
}
p {
  line-height: 30px
}
.active{
  color: orange !important;
}
.recommend-title {
  margin-bottom: 5px;
}
.recommend-title:hover {
  color: #2a60c9;
}
.comment-active{
  color: #2a60c9;
}
pre {
  white-space: pre-wrap; /*css-3*/
  white-space: -moz-pre-wrap; /*Mozilla,since1999*/
  white-space: pre-wrap; /*Opera4-6*/
  white-space: -o-pre-wrap; /*Opera7*/
  word-wrap: break-word; /*InternetExplorer5.5+*/
}
</style>