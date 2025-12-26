<template>
  <div class="success-container">
    <!-- 使用 Element UI 内置的图标 -->
    <i class="el-icon-check-circle" style="font-size: 60px; color: #4CAF50;"></i>
    <h2>支付成功！</h2>
    <p>您的悬赏金已支付，我们将通知失主联系您</p>
    <el-button type="primary" @click="toHome">返回首页</el-button>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'  // Vue 2 中应使用 this.$router，而非 useRouter

export default {
  methods: {
    toHome() {
      this.$router.push('/')  // 修正路由跳转方式
    }
  }
}
</script>

<style scoped>
.success-container {
  text-align: center;
  padding: 50px;
}
.el-icon-check-circle {
  margin-bottom: 20px;
}
h2 {
  color: #333;
  margin-bottom: 15px;
}
p {
  color: #666;
  margin-bottom: 30px;
}
</style>