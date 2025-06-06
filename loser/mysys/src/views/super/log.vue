<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column fixed prop="id" label="ID" width="150">
      </el-table-column>
      <el-table-column prop="uid" label="用户ID" width="120">
      </el-table-column>
      <el-table-column prop="date" label="日志时间" width="120">
      </el-table-column>
      <el-table-column prop="type" label="操作" width="120">
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { logf } from '../../utils/api/superApi'
export default {
  data() {
    return {
      tableData: []
    }
  },
  mounted() {
    logf().then(res => {
      this.tableData = res.data.data
    })
  },
  computed: {
  },
  methods: {
  }
}
</script>
<style scoped></style>
