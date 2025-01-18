<template>
<!--  <div>-->
<!--&lt;!&ndash;    gutter是间隔的意思，gutter=20即每一个元素之间间隔20px&ndash;&gt;-->
<!--    <el-row gutter="20">-->
<!--&lt;!&ndash;      一个页面一共有24等分，span=12即占用一半&ndash;&gt;-->
<!--      <el-col :span="6">-->
<!--      <div style="width: 100%; height: 300px; background: dodgerblue"></div>-->
<!--      </el-col>-->
<!--      <el-col :span="6">-->
<!--        <div style="width: 100%; height: 300px; background: red"></div>-->
<!--      </el-col>-->
<!--    </el-row>-->
<!--  </div>-->


  <div>
    <el-row>

      <el-col :span="6">
        <div style="padding: 10px; border: 1px solid #ccc; text-align: center">
          <img style="width: 80%" src="@/assets/logo.png" alt="">
          <div style="text-align: center">这是一段描述</div>
          <div style="color: red">售价 $298</div>
        </div>
      </el-col>

      <el-col :span="6">
        <div style="padding: 10px; border: 1px solid #ccc; text-align: center">
          <img style="width: 80%" src="@/assets/logo.png" alt="">
          <div style="text-align: center">这是一段描述</div>
          <div style="color: red">售价 $99</div>
        </div>
      </el-col>

      <el-col :span="6">
        <div style="padding: 10px; border: 1px solid #ccc; text-align: center">
          <img style="width: 80%" src="@/assets/logo.png" alt="">
          <div style="text-align: center">这是一段描述</div>
          <div style="color: red">售价 $198</div>
        </div>
      </el-col>

      <el-col :span="6">
        <div style="padding: 10px; border: 1px solid #ccc; text-align: center">
          <img style="width: 80%" src="@/assets/logo.png" alt="">
          <div style="text-align: center">这是一段描述</div>
          <div style="color: red">售价 $68</div>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-button>默认按钮</el-button>
      <el-button type="primary">主要按钮</el-button>
      <el-button type="success">成功按钮</el-button>
      <el-button type="info">信息按钮</el-button>
      <el-button type="warning">警告按钮</el-button>
      <el-button type="danger">危险按钮</el-button>
    </el-row>

    <el-row>
      <el-button plain>朴素按钮</el-button>
      <el-button type="primary" plain>主要按钮</el-button>
      <el-button type="success" plain>成功按钮</el-button>
      <el-button type="info" plain>信息按钮</el-button>
      <el-button type="warning" plain>警告按钮</el-button>
      <el-button type="danger" plain>危险按钮</el-button>
    </el-row>

    <el-row>
      <el-button round>圆角按钮</el-button>
      <el-button type="primary" round>主要按钮</el-button>
      <el-button type="success" round>成功按钮</el-button>
      <el-button type="info" round>信息按钮</el-button>
      <el-button type="warning" round>警告按钮</el-button>
      <el-button type="danger" round>危险按钮</el-button>
    </el-row>

    <el-row>
      <el-button icon="el-icon-search" circle></el-button>
      <el-button type="primary" icon="el-icon-edit" circle></el-button>
      <el-button type="success" icon="el-icon-check" circle></el-button>
      <el-button type="info" icon="el-icon-message" circle></el-button>
      <el-button type="warning" icon="el-icon-star-off" circle></el-button>
      <el-button type="danger" icon="el-icon-delete" circle></el-button>
    </el-row>


<!--    element的搜索框需要设置一个v-model属性，用来绑定输入框的值。-->
    <el-row style="margin-top: 20px">
      <el-col>
        <el-input style="width: 200px" v-model="value1" placeholder="请输入账号"></el-input>
        <el-input show-password style="width: 200px" v-model="password" placeholder="请输入密码"></el-input>
        <el-input type="textarea" style="width: 200px" v-model="value2" placeholder="请在文本域中输入内容"></el-input>
        <el-input style="width: 200px" v-model="value3" placeholder="请输入内容" prefix-icon="el-icon-search"></el-input>
        <el-input clearable style="width: 200px" v-model="value4" placeholder="请输入用户名" suffix-icon="el-icon-user"></el-input>
        <el-button icon="el-icon-search">搜索</el-button>
      </el-col>
    </el-row>


<!--    :是v-bind的简写，:a="b"表示将b的值绑定到a属性上。同时又将a绑定到el-autocomplete-->
<!--    el-autocomplete模糊搜索，用来实现匹配搜索的关键字，实现搜索建议-->
    <el-row style="margin: 20px 0">
      <el-autocomplete style="width: 200px" v-model="value5" placeholder="请输入内容" :fetch-suggestions="querySearch" :trigger-on-focus="false"></el-autocomplete>
    </el-row>



  </div>
</template>




<script>
export default {
  data() {
    return {
      value1: '',
      value2: '',
      value3: '',
      value4: '',
      value5: '',
      password: '',
      coffees: [{value: '1星巴克咖啡'},{value: '2瑞幸咖啡'},{value: '3库迪咖啡'}] //想要实现搜索建议，数组必须带value
    }
  },
  methods: {
    querySearch(queryString, cb) {//cb(call back)是一个回调函数，用来返回匹配的结果
      let result = queryString ? this.coffees.filter(coffee => coffee.value.includes(queryString)) : this.coffees
      cb(result)
    }
  }
}
</script>






