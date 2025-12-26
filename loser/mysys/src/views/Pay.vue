<template>
  <div class="pay-container">
    <h3>支付悬赏金</h3>
    <el-form :model="payForm" ref="payForm" label-width="100px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input v-model="payForm.orderNo" readonly></el-input>
      </el-form-item>
      <el-form-item label="金额(元)" prop="amount">
        <el-input v-model="payForm.amount" type="number" step="0.01"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="subject">
        <el-input v-model="payForm.subject"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitPay">提交支付</el-button>
      </el-form-item>
    </el-form>
    <!-- 支付表单容器（用于渲染支付宝返回的HTML） -->
    <div v-html="payHtml" v-if="payHtml"></div>
  </div>
</template>

<script>
import { payCreate } from '@/utils/api/payApi'

export default {
  data() {
    return {
      payForm: {
        orderNo: '', // 订单号（可前端生成或后端返回）
        amount: 0,
        subject: '失物招领悬赏金'
      },
      payHtml: '' // 支付宝支付表单HTML
    }
  },
  created() {
    // 生成唯一订单号（示例：时间戳+随机数）
    this.payForm.orderNo = Date.now() + Math.floor(Math.random() * 1000).toString()
  },
  methods: {
    submitPay() {
      payCreate(this.payForm).then(res => {
        // 渲染支付宝支付表单
        this.payHtml = res.data
      }).catch(err => {
        this.$message.error('创建支付订单失败')
      })
    }
  }
}
</script>