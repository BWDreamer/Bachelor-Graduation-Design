/*
* 本文件是项目的配置文件，通过node中的一个模块操作，向外暴露了一个配置对象
* */
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port: 9090
  },
  chainWebpack: config =>{
    config.plugin('html')
        .tap(args => {
          args[0].title = "游戏分享网站";
          return args;
        })
  }
})
