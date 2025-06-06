<template>
    <div>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="id" label="ID" width="120">
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="120">
            </el-table-column>
            <el-table-column prop="tel" label="电话">
            </el-table-column>
            <el-table-column prop="crscore" label="信誉分">
            </el-table-column>
            <el-table-column prop="calmday" label="禁言天数">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <el-button type="danger" size="small" @click="deleteAd(scope.row)">删除管理员</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
import { deleteAdminf } from '../../utils/api/superApi'
import { alladminInfof } from '../../utils/api/superApi'
export default {
    data() {
        return {
            tableData: []
        }
    },
    mounted() {
        alladminInfof().then(res => {
            this.tableData = res.data.data
        })
    },
    computed: {
    },
    methods: {
        deleteAd(data) {
            deleteAdminf({ id: data.id })
        },
        handleSelectionChange() {

        }
    }
}
</script>
<style scoped></style>
