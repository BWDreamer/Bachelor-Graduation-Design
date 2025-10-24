<template>
  <div class="forbidden">
    <h1>403 - 禁止访问</h1>
    <p>很抱歉，您没有权限访问此页面。</p>
    <router-link to="/">返回首页</router-link>
  </div>
</template>

<script>
export default {
  name: 'Auth'
}
</script>

<style scoped>
.forbidden {
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
