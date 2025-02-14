<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      你好，{{user.name}}，欢迎使用 游戏分享网站！
    </div>
    <div style="display: flex">
      <el-card style="width: 100%;">
        <div slot="header" class="clearfix">
          <span>国产游戏《黑神话：悟空》走向世界！</span>
        </div>
        <div>
          以《黑神话：悟空》为代表的国产3A游戏，通过对中国传统文化的创新演绎和高质量的呈现，不仅在全球范围内获得广泛认可，还成功将中国文化带入国际视野。
          <div style="margin-top: 20px">
            <div style="margin: 10px 0"><strong>主题色</strong></div>
            <el-button type="primary">按钮</el-button>
            <el-button type="success">按钮</el-button>
            <el-button type="warning">按钮</el-button>
            <el-button type="danger">按钮</el-button>
            <el-button type="info">按钮</el-button>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      user: JSON.parse(localStorage.getItem('web-user') || '{}'),
    }
  },
  created(){

  },
  methods:{}
}
</script>

<style scoped>

</style>