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
          <img style="width: 80%" src="@/assets/logo1.png" alt="">
          <div style="text-align: center">这是一段描述</div>
          <div style="color: red">售价 $298</div>
        </div>
      </el-col>

      <el-col :span="6">
        <div style="padding: 10px; border: 1px solid #ccc; text-align: center">
          <img style="width: 80%" src="@/assets/logo1.png" alt="">
          <div style="text-align: center">这是一段描述</div>
          <div style="color: red">售价 $99</div>
        </div>
      </el-col>

      <el-col :span="6">
        <div style="padding: 10px; border: 1px solid #ccc; text-align: center">
          <img style="width: 80%" src="@/assets/logo1.png" alt="">
          <div style="text-align: center">这是一段描述</div>
          <div style="color: red">售价 $198</div>
        </div>
      </el-col>

      <el-col :span="6">
        <div style="padding: 10px; border: 1px solid #ccc; text-align: center">
          <img style="width: 80%" src="@/assets/logo1.png" alt="">
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


<!--    下拉框功能-->
<!--    @是v-on的简写，绑定事件,当下拉框的值change时，会触发changeSelect方法-->
<!--    v-for循环中绑定的key是数组中的唯一标识符-->
    <el-row>
      <el-select v-model="select1" @:change="changeSelectFruit">
        <el-option v-for="item in fruits" :key="item.id" :value="item.name"></el-option>
      </el-select>
    </el-row>


    <el-row>
      <el-select v-model="select2" @:change="changeSelectUser">
        <el-option v-for="item in users" :key="item.card" :label="item.name" :value="item.card"></el-option>
      </el-select>
    </el-row>


<!--    el-radio-group是一组单选框-->
    <el-row>
      <el-radio-group v-model="radio" @change="selectRadio">
        <el-radio label="男"></el-radio>
        <el-radio label="女"></el-radio>
      </el-radio-group>
    </el-row>


<!--    el-date-picker是日期选择器，type="date"是日期选择器，type="datetime"是时间选择器，一定要设置value-format-->
    <el-date-picker v-model="date" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" @change="changeDate"></el-date-picker>
    <el-date-picker v-model="datetime" type="datetime" placeholder="选择日期时间" value-format="yyyy-MM-dd HH:mm:ss" @change="changeDateTime"></el-date-picker>


<!--    el-table是表格，数组有几个属性就有几个el-table-column,prop是数组中的属性-->
<!--    通过:header-cell-style来设置表头的样式-->
    <el-row style="margin: 20px 0">
      <el-table :data="tableData" border :header-cell-style="{ background: 'aliceblue', fontSize:'16px'}">
        <el-table-column label="序号" prop="id" align="center"></el-table-column>
        <el-table-column label="姓名" prop="name" align="center"></el-table-column>
        <el-table-column label="年龄" prop="age" align="center"></el-table-column>
        <el-table-column label="地址" prop="address" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template v-slot="scope">
            <el-button size="primary" @click="edit(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
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
      coffees: [//想要实现搜索建议，数组必须带value
        {value: '1星巴克咖啡'},
        {value: '2瑞幸咖啡'},
        {value: '3库迪咖啡'}
      ],
      select1: '',
      select2: '',
      fruits: [
        {name: '香蕉', id: 1},
        {name: '橘子', id: 2},
        {name: '菠萝', id: 3}
      ],
      users: [
        {name: '张三', card: '12312342312342123'},
        {name: '李四', card: '23232312232312312'},
        {name: '王五', card: '78761231232122313'},
      ],
      radio: '',
      date: '',
      datetime: '',
      tableData: [
        {id:'1',name:'张三',age:'17',address:'DX1560'},
        {id:'2',name:'李四',age:'18',address:'DN2100'},
        {id:'3',name:'王五',age:'19',address:'CE1230'},
      ],
    }
  },
  methods: {
    querySearch(queryString, cb) { //cb(call back)是一个回调函数，用来返回匹配的结果
      let result = queryString ? this.coffees.filter(coffee => coffee.value.includes(queryString)) : this.coffees
      cb(result)
    },
    changeSelectFruit(){
      console.log(this.select1)
    },
    changeSelectUser(){
      console.log(this.select2)
    },
    selectRadio(){
      console.log(this.radio)
    },
    changeDate(){
      console.log(this.date)
    },
    changeDateTime(){
      console.log(this.datetime)
    },
    edit(row){
      //有四种消息提示方式可选
      //第一种
      //alert(row.name)
      //第二种
      //this.$message.success(row.name)
      //第三种
      //this.$notify.success(row.name)
      //第四种
      this.$confirm('这是一段文本','提示',{
          type: 'warning'
      }).then(() => {
        this.$message.success('确定的消息')
      }).catch(() => {
        this.$message.warning('取消的消息')
      })
    },
  }
}
</script>






