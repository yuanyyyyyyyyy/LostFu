<template>
    <div class="main">
        <div>
            <el-button type="success" icon="el-icon-edit" circle @click="AddPost"></el-button>
            <el-button type="warning" plain icon="el-icon-user" @click="want">申请管理员</el-button>
        </div>
        <el-card class="box-card" v-for="(item, index) in listdata" :key="index">
            <div slot="header" class="clearfix" style="display: flex; justify-content: space-between; align-items: center;">
                <span>发布者：{{ item.username }}</span>
                <div>
                    <el-button size="mini" type="danger" @click="deletep(item.id)">删除</el-button>
                    <el-button size="mini" type="primary" @click="updatep(item)">修改</el-button>
                </div>
            </div>
            <div>
                <div>内容：{{ item.describe }}</div>
            </div>
            <div>
                <el-image
                        style="width: 50px; height: 50px"
                        :src="item.picaddr"
                        fit="fill"></el-image>
            </div>
            <div>
                发布时间：{{ item.date }}
            </div>
        </el-card>
        <el-dialog :title="isAdd ? '增加' : '修改'" :visible.sync="isOccur" width="30%" :before-close="handleClose">
            <el-form ref="form" :model="form" label-width="80px">

                <el-form-item label="发帖内容">
                    <el-input type="textarea" v-model="form.describe"></el-input>
                </el-form-item>
                <el-form-item label="上传文件:" prop="excel">
                    <el-upload
                            class="upload-demo"
                            ref="upload"
                    :http-request="httpRequest"
                    :before-upload="beforeUpload"
                    :on-exceed="handleExceed"
                    :limit="1">
                    <el-button slot="trigger" size="small" type="primary">选取图片</el-button>
                    <div slot="tip" class="el-upload__tip">不超过5M</div>
                    </el-upload>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="isOccur = false">取 消</el-button>
                <el-button type="primary" @click="confirm()">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import { selfPullf, deltePullf, updatePullf, wantAdminf, pullLosef } from '../../utils/api/userApi'
import {ImgUrl} from '../../utils/url/img'
export default {
    data() {
        return {
            listdata: [],
            isOccur: false,
            isAdd: true,//判断是发表帖子还是修改帖子
            form: {
                id: '',
                username: localStorage.getItem("username"),
                tel: localStorage.getItem("tel"),
                describe: '',
                Picaddr: '',
                type: localStorage.getItem("infoid"),
                date: ''
            },
            img:ImgUrl,
            //存放上传文件
            fileList: []
        }
    },
    mounted() {
        selfPullf({ id: localStorage.getItem("id") }).then(res => {
            this.listdata = res.data.data
        })
    },
    computed: {
    },
    methods: {
        deletep(id) {

            this.$confirm('此操作将永久删除该帖子, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {

                deltePullf({ id: id });
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                this.$router.go(0);
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        //点击修改按钮
        updatep(item) {
            this.isAdd = false
            this.isOccur = true;
            this.form = item;
        },
        //点击发帖按钮
        AddPost() {
            this.isAdd = true;
            this.isOccur = true;
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        }
        ,
        //提交表单
        confirm() {
            this.isOccur = false;
            if (this.isAdd) {//发帖表单
                //
                const params = new FormData()
                // 将上传文件数组依次添加到参数paramsData中
                this.fileList.forEach((x) => {
                    params.append('file', x.file)
                });
                // 将输入表单数据添加到params表单中
                params.append('username', this.form.username);
                params.append('tel', this.form.tel);
                params.append('type', this.form.type);
                params.append("describe", this.form.describe);
                pullLosef(params).then(res => {
                    if (res.data.message === 'success') {
                        this.$notify({
                            title: '成功',
                            message: '操作成功',
                            duration: 0,
                            type: 'success',

                        });
                        this.$router.go(0);
                    } else if (res.data.message === 'lose auth') {
                        this.$notify({
                            title: '警告',
                            message: '您正在处于禁言中，请稍后再试',
                            duration: 0
                        });
                    }
                    else {
                        this.$notify({
                            title: '错误',
                            message: '系统繁忙，请稍后再试',
                            duration: 0,
                            type: 'warning'
                        });
                    }
                })

            } else {//修改表单
                this.isOccur = false;
                updatePullf(this.form).then(res => {
                    if (res.data.message === 'success') {
                        this.$notify({
                            title: '成功',
                            message: '操作成功',
                            duration: 0,
                            type: 'success'
                        });
                        this.form = {
                            id: '',
                            username: localStorage.getItem("username"),
                            tel: localStorage.getItem("tel"),
                            describe: '',
                            Picaddr: '',
                            type: localStorage.getItem("Infoid"),
                            date: ''
                        }
                    }
                    else {
                        this.$notify({
                            title: '错误',
                            message: '操作失败，请稍后再试',
                            duration: 0,
                            type: 'warning'
                        });
                    }
                })
            }
        },
        //申请管理员
        want() {
            wantAdminf({ id: localStorage.getItem("id") }).then(res => {
                if (res.data.message === 'success') {
                    this.$notify({
                        title: '成功',
                        message: '申请已发送',
                        duration: 0,
                        type: 'success'
                    });
                } else {
                    this.$notify({
                        title: '错误',
                        message: '申请失败，请稍后再试',
                        duration: 0,
                        type: 'warning'
                    });
                }
            })
        }
        ,
        //图片上传
        httpRequest(option){
            this.fileList.push(option)
        },
        // 上传前处理
        beforeUpload(file) {
            let fileSize = file.size
            const FIVE_M = 5 * 1024 * 1024;
            //大于5M，不允许上传
            if (fileSize > FIVE_M) {
                this.$message.error("最大上传5M")
                return false
            }
            return true
        }
        ,  // 文件数量过多时提醒
        handleExceed() {
            this.$message({ type: 'error', message: '最多支持1个图片上传' })
        },
    }
}
</script>
<style >
.box-card {
    margin-top: 5px;
}


</style>
