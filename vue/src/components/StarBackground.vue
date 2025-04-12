<template>
  <canvas ref="canvas" class="star-canvas"></canvas>
</template>

<script>
export default {
  data() {
    return {
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
    this.initStarCanvas();
  },
  beforeDestroy() {
    // 清理事件监听
    window.removeEventListener('resize', this.handleResize);
    const canvas = this.$refs.canvas;
    canvas.removeEventListener('touchmove', this.onTouchMove);
    document.removeEventListener('mousemove', this.onMouseMove);
    document.removeEventListener('mouseleave', this.onMouseLeave);
    cancelAnimationFrame(this.animationFrame);
  },

  methods: {
    initStarCanvas() {
      const canvas = this.$refs.canvas;
      const context = canvas.getContext('2d');
      this.generateStars();
      this.handleResize();

      window.addEventListener('resize', this.handleResize);
      document.addEventListener('mousemove', this.onMouseMove);
      canvas.addEventListener('touchmove', this.onTouchMove);
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
        this.velocity.tx += (ox / 20) * this.scale * (this.touchInput ? 1 : -1);
        this.velocity.ty += (oy / 20) * this.scale * (this.touchInput ? 1 : -1);
      }
      this.pointerX = x;
      this.pointerY = y;
    },
    onMouseMove(e) {
      this.touchInput = false;
      // 添加边界判断，确保在任意位置都可以触发
      if (e.clientX >= 0 && e.clientY >= 0) {
        this.movePointer(e.clientX, e.clientY);
      }
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
  },
}
</script>

<style scoped>
.star-canvas {
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
  width: 100%;
  height: 100%;
  background-image: linear-gradient(-225deg, #231557 0%, #43107a 29%, #FF1361 100%);
}
</style>