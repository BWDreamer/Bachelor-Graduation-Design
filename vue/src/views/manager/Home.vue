<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px" v-if="user.role==='用户'">
      你好，{{user.name}}，欢迎使用 游戏分享网站！
    </div>

    <div style="display: flex" v-if="user.role==='用户'">
      <el-card style="width: 100%;">
        <div slot="header" class="clearfix">
          <span>国产游戏《黑神话：悟空》走向世界！</span>
        </div>
        <div>
          以《黑神话：悟空》为代表的国产3A游戏，通过对中国传统文化的创新演绎和高质量的呈现，不仅在全球范围内获得广泛认可，还成功将中国文化带入国际视野。
        </div>
      </el-card>
    </div>

    <div v-if="user.role==='管理员'">
      <div style="display: flex; grid-gap: 10px">
        <div class="card" style="height: 400px; width: 50%" id="pie"></div>
        <div class="card" style="height: 400px; width: 50%" id="bar"></div>
      </div>
      <div class="card" style="height: 400px; width: 100%; margin-top: 10px" id="line"></div>
    </div>

  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('web-user') || '{}'),
      pieOptions: {
        title: {
          text: '不同分类下用户发布游戏文章的数量',
          subtext: '统计维度：游戏类型',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '数量占比',
            type: 'pie',
            radius: '50%',
            center: ['50%', '60%'],
            data: [
              { value: 1048, name: '开放世界游戏' },
              { value: 735, name: '射击游戏' },
              { value: 580, name: '魂类游戏' },
              { value: 484, name: '肉鸽游戏' },
              { value: 300, name: '生存建造类游戏' },
              { value: 200, name: '即时战略游戏' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      barOptions: {
        title: {
          text: '不同用户发布游戏文章数量Top5',
          subtext: '统计维度：用户名',
          left: 'center'
        },
        gird: {
          top: '20%',
          bottom: '20%'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        xAxis: {
          type: 'category',
          data: ['王柄皓', '张三', '李四', '王五', '赵六'],
          name: '用户名',
          axisLabel: {
            show: true,
            interval: 0,
            rotate: -60,
            inside: false,
            margin: 6,
          },
        },
        yAxis: {
          type: 'value',
          name: '用户名',
        },
        tooltip: {
          trigger: 'item',
        },
        series: [
          {
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
            itemStyle: {
              color: function (){
                return '#' + Math.floor(Math.random() * (256 * 256 * 256 -1 )).toString(16);
              }
            },
          },
        ]
      },
      lineOptions: {
        title: {
          text: '最近一周每天平台用户发布的文章数量',
          subtext: '统计维度：最近一周',
          left: 'center'
        },
        gird: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        legend: {
          data: [],
          template:""
        },
        xAxis: {
          name: '日期',
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        },
        yAxis: {
          name: '文章数量',
          type: 'value',
        },
        tooltip: {
          trigger: 'item',
        },
        series: [
          {
            name: '文章数量',
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'line',
            smooth: true,
            markLine: {
              data: [{ type: 'average', name: '最近一周文章发布数量平均值' }]
            },
            markPoint:{
              data:[
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ]
            },
          },
        ]
      },
    }
  },
  mounted() {
    if (this.user.role === '管理员') {
      this.loadPie();
      this.loadBar();
      this.loadLine();
    }
  },
  methods:{
    loadPie() {
      this.$request.get('/echarts/pie').then(res => {
        if (res.code === '200'){
          let chartDom = document.getElementById('pie');
          let myChart = echarts.init(chartDom);
          this.pieOptions.series[0].data = res.data;
          myChart.setOption(this.pieOptions);
        }
      })
    },
    loadBar() {
      this.$request.get('/echarts/bar').then(res => {
        if (res.code === '200'){
          let chartDom = document.getElementById('bar');
          let myChart = echarts.init(chartDom);
          this.barOptions.xAxis.data = res.data.xAxis;
          this.barOptions.series[0].data = res.data.yAxis;
          myChart.setOption(this.barOptions);
        }
      })
    },
    loadLine() {
      this.$request.get('/echarts/line').then(res => {
        if (res.code === '200'){
          let chartDom = document.getElementById('line');
          let myChart = echarts.init(chartDom);
          this.lineOptions.xAxis.data = res.data.xAxis;
          this.lineOptions.series[0].data = res.data.yAxis;
          myChart.setOption(this.lineOptions);
        }
      })
    }
  }
}

</script>

<style scoped>

</style>