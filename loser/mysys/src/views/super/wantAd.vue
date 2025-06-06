<template>
    <div>
        <el-table :data="tableData" style="width: 100%">
            <el-table-column fixed prop="id" label="ID" width="150">
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="120">
            </el-table-column>
            <el-table-column prop="date" label="申请时间" width="120">
            </el-table-column>
            <el-table-column prop="crscore" label="信誉分" width="300">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="success" size="small">同意</el-button>

                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
import { seeWantf, addAdminf } from '../../utils/api/superApi'
import { notice } from '@/utils/UseElemnt/notice'
export default {
    data() {
        return {
            tableData: []
        }
    },
    mounted() {
        seeWantf().then(res => {
            this.tableData = res.data.data
        })
    },
    computed: {
    },
    methods: {
        handleClick(data) {
            addAdminf({ id: data.id }).then(res => {
                if (res.data.message === 'success') {
                    notice('success', '操作成功')
                }
            })

        }
    }
}
</script>
<style scoped></style>
