const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  // devServer: {
  //   proxy: {
  //     '/api': {
  //       target: 'http://localhost:8088', // 后端接口地址
  //       changeOrigin: true, // 是否允许跨域
  //       pathRewrite: {
  //         '^/api': '', // 将 /api 替换为空字符串
  //       },
  //     },
  //   },
  // },
})
