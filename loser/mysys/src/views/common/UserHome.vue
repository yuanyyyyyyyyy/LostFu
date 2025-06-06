<template>
    <div class="main">

        <el-header style="display: flex;justify-content: right; align-items: center;">
            <div style="display: flex; align-items: center;margin-right: 7px;">
                <div style="margin-left: 10px;">
                    <span>用户名：{{ username }}</span>
                </div>
            </div>
            <el-popover placement="right" width="230" trigger="hover">
                <el-avatar class="avatar" src="https://tupian.qqw21.com/article/UploadPic/2019-1/201912319273090791.jpg"
                    slot="reference"></el-avatar>
                <el-avatar class="avatar"
                    src="https://tupian.qqw21.com/article/UploadPic/2019-1/201912319273090791.jpg"></el-avatar>

                <div>
                    <i class="el-icon-user"></i>
                    用户名 : {{ form.username }}
                </div>
                <div>
                    <i class="el-icon-phone-outline"></i>
                    联系方式： {{ form.tel }}
                </div>
                <div>
                    <i class="el-icon-warning-outline"></i>
                    禁言天数: {{ form.calmday }}
                </div>
                <el-divider></el-divider>
                <el-button size="mini" type="success" icon="el-icon-switch-button" plain @click="logout">退出</el-button>
            </el-popover>
        </el-header>
        <el-container>
            <el-aside :width="isCollapse ? '64px' : '200px'">
                <div class="toggle-button" @click="toggleCollapse">|||</div>
                <el-menu class="el-menu-vertical-demo" :collapse="isCollapse" :default-active="defaultActive"
                    @open="handleOpen" @close="handleClose" router>
                    <!-- 使用 v-for 渲染菜单项 -->
                    <template v-for="(menu, index) in menus">
                        <el-menu-item v-if="menu.type === 'item'" :index="menu.index">
                            <i :class="menu.icon"></i>
                            <span slot="title">{{ menu.title }}</span>
                        </el-menu-item>
                        <el-submenu v-else :index="menu.index">
                            <template slot="title">
                                <i :class="menu.icon"></i>
                                <span slot="title">{{ menu.title }}</span>
                            </template>
                            <!-- 使用 v-for 渲染子菜单项 -->
                            <el-menu-item-group v-for="(group, groupIndex) in menu.items" :key="groupIndex"
                                :title="group.groupTitle">
                                <el-menu-item v-for="(item, itemIndex) in group.items" :key="itemIndex" :index="item.index">
                                    {{ item.text }}
                                </el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                    </template>
                </el-menu>
            </el-aside>
            <el-main class="main-container"> <router-view></router-view></el-main>
        </el-container>
    </div>
</template>
<script>
import { menus, allMenus } from "../../utils/Menu.js";
import { userInfof } from '../../utils/api/userApi'
export default {
    data() {
        return {
            username: localStorage.getItem("username"),
            menus: [],
            isCollapse: false,
            defaultActive: '/UserHome',
            form: {}
        }
    },
    mounted() {
        this.select()
        userInfof({ id: localStorage.getItem("id") }).then(res => {
            this.form = res.data.data
        })

    },
    computed: {
    },
    methods: {
        select() {
            const infoid = localStorage.getItem("infoid");
            if (infoid === "1" || infoid === "2") {
                // 普通用户
                this.menus = allMenus.filter(menu => menu.index !== 'info' && menu.index !== 'log' && menu.index !== 'wantAd' && menu.index !== 'adminInfo');
            } else if (infoid === "3") {
                // 管理员
                this.menus = allMenus.filter(menu => menu.index !== 'log' && menu.index !== 'wantAd' && menu.index != 'searchMe' && menu.index !== 'adminInfo');
            } else if (infoid === "4") {
                // 超级管理员
                this.menus = allMenus.filter(menu => menu.index != 'searchMe');
                // this.menus = allMenus;
            }
        },
        toggleCollapse() {
            this.isCollapse = !this.isCollapse;
        },
        Come() {

        },
        handleOpen() {

        }, handleClose() {

        },
        logout() {
            localStorage.clear();
            this.$router.replace('/');
        }
    }
}
</script>
<style scoped>
.main {
    height: 100vh;
    width: 100%;
}

.main-container {
    /* background-color: #C0C4CC; */
    background-color: #f2f2f2;
    height: calc(100vh - 60px);
    /* 如果内容溢出，显示滚动条 */
}

.toggle-button {
    background-color: #ffffff;
    font-size: 10px;
    line-height: 24px;
    color: #4a5064;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
    border: 1px solid gainsboro;
}

.el-dropdown-link {
    cursor: pointer;
    margin-left: 10px;
    margin-top: 5px;
}

.avatar {
    /* margin-top: 5px; */
}
</style>
