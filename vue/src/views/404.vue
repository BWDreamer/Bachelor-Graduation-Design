<template>
  <div class="not-found">
    <h1>404 - 页面未找到</h1>
    <p>很抱歉，您访问的页面不存在。</p>
    <router-link to="/front/home">返回首页</router-link>
  </div>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped>
.not-found {
  text-align: center;
  padding: 50px;
}

h1 {
  font-size: 3em;
  color: #333;
}

p {
  font-size: 1.2em;
  color: #666;
}

router-link {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #007BFF;
  color: #fff;
  text-decoration: none;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

router-link:hover {
  background-color: #0056b3;
}
</style>
