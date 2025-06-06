<template>
    <div class="main">
        <!-- <el-table ref="multipleTable" :data="listdata" tooltip-effect="dark" style="width: 100%"
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
                    <el-button type="danger" size="small" @click="shutdown(scope.row)">禁言</el-button>
                </template>
            </el-table-column>
        </el-table> -->
        <el-table :data="[tableData]" border style="width: 100%">
            <el-table-column fixed prop="id" label="图片id">
            </el-table-column>

            <el-table-column prop="username" label="上传时间">
            </el-table-column>


        </el-table>
    </div>
</template>
<script>
import { allUserInfof, shutUserf } from '../../utils/api/adminApi'
export default {
    data() {
        return {
            tableData: [],
            multipleSelection: [],

        }
    },
    mounted() {
        allUserInfof({ infoid: localStorage.getItem("infoid") }).then(res => {
            console.log(res.data)
            this.tableData = res.data.data;
            console.log(this.tableData)
        })

    },
    computed: {
    },
    methods: {
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        shutdown(data) {
            shutUserf({ id: data.id, calmday: 2 })
        }
    }
}
</script>
<style scoped>
.main {
    height: 100%
}
</style>
