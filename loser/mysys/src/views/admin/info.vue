<template>
    <div class="main">
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
                    <el-button type="danger" size="small" @click="shutdown(scope.row)">禁言</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog title="禁言" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
            <el-input v-model="calmday" placeholder="请输入禁言天数"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirm">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import { allUserInfof, shutUserf } from '../../utils/api/adminApi'
export default {
    data() {
        return {
            tableData: [],
            multipleSelection: [],
            user: {},
            calmday: '',
            dialogVisible: false
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
            this.user = data;
            this.dialogVisible = true;

        },
        confirm() {
            shutUserf({ id: this.user.id, calmday: this.calmday }).then(res => {
                if (res.data.message === 'success') {
                    this.dialogVisible = false;
                    this.$notify({
                        title: '提示',
                        message: '操作成功',
                        duration: 1600
                    });
                    this.$router.go(0);
                } else {
                    this.$notify({
                        title: '提示',
                        message: '操作失败',
                        duration: 1600
                    });
                }
            })
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        }
    }
}
</script>
<style scoped>
.main {
    height: 100%
}
</style>
