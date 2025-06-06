<template>
  <div class="main">
    <div class="title-container">
      <h1 class="title">失物招领系统</h1>
    </div>
    <div class="center-container">
      <el-form :model="ruleForm" status-icon label-width="100px" class="form">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model.number="ruleForm.username"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="tel" v-if="register">
          <el-input
            type="password"
            v-model="ruleForm.tel"
            autocomplete="off"
            prefix-icon="el-icon-phone"
          ></el-input>
        </el-form-item>
        <el-form-item label="身份" v-if="!register">
          <el-radio-group v-model="ruleForm.infoid">
            <el-radio label="拾取者"></el-radio>
            <el-radio label="丢失者"></el-radio>
            <el-radio label="管理员"></el-radio>
            <el-radio label="超级管理员"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input
            type="password"
            v-model="ruleForm.password"
            autocomplete="off"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass" v-if="register">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" class="login-btn"
            >登录</el-button
          >
          <el-button @click="ReG" class="register-btn">注册</el-button>
          <el-button v-if="register" @click="back" class="cancel-btn">取消</el-button>
          <el-button v-if="register" @click="confirmRe" type="primary" class="confirm-btn"
            >确认</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { loginf, registerf } from "../../utils/api/comapi";

import { mess, notice } from "@/utils/UseElemnt/notice";
export default {
  data() {
    return {
      register: false,
      ruleForm: {
        password: "",
        checkPass: "",
        username: "",
        infoid: "",
        tel: "",
      },
      user: {
        //用于注册时使用
        id: "",
        Infoid: "",
        username: "",
        password: "",
        tel: "",
        crscore: "",
        auth: "",
        calmday: "",
      },
    };
  },
  methods: {
    ReG() {
      this.register = !this.register;
    },
    submitForm(formName) {
      let name = this.ruleForm.username;
      let pwd = this.ruleForm.password;
      let info;
      if (this.ruleForm.infoid === "拾取者") {
        info = 1;
      }
      if (this.ruleForm.infoid === "丢失者") {
        info = 2;
      }
      if (this.ruleForm.infoid === "管理员") {
        info = 3;
      }
      if (this.ruleForm.infoid === "超级管理员") {
        info = 4;
      }
      loginf({ username: name, password: pwd, infoid: info }).then((res) => {
        if (res.data.message === "success") {
          let mydata = res.data.data;
          console.log(mydata);
          let data = mydata[0];
          let token = mydata[1];
          console.log(data);
          localStorage.setItem("id", data.id);
          localStorage.setItem("infoid", info);
          localStorage.setItem("username", data.username);
          localStorage.setItem("tel", data.tel);
          localStorage.setItem("satoken", token.tokenValue);
          this.$notify({
            title: "提示",
            message: "登录成功",
            duration: 1600,
          });
          setTimeout(() => {
            // const currentRoute = this.$router.currentRoute;
            // if (currentRoute.path !== '/UserHome') {
            this.$router.push("/UserHome");
            // }
          }, 1000); // 2000毫秒（2秒）延迟
        } else {
          this.$notify({
            title: "提示",
            message: "用户名或密码错误",
            duration: 3600,
          });
        }
      });
    },

    confirmRe() {
      if (
        this.ruleForm.username != "" ||
        this.ruleForm.tel != "" ||
        this.ruleForm.password != ""
      ) {
        if (this.ruleForm.password != this.ruleForm.checkPass) {
          notice("error", "俩次输入密码不一致，请重新输入");
        } else {
          this.user.username = this.ruleForm.username;
          this.user.tel = this.ruleForm.tel;
          this.user.password = this.ruleForm.password;
          registerf(this.user).then((res) => {
            if (res.data.message === "success") {
              this.register = false;
              mess("注册成功，请登录");
            }
          });
        }
      } else {
        notice("error", "有信息未填请在检查一次");
      }
    },
    //返回登录主界面
    back() {
      this.register = false;
    },
  },
};
</script>

<style scoped>
.main {
  width: 100%;
  height: 100vh;
  background-image: url("@/assets/login.png");
  background-size: cover;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.center-container {
  margin-top: 2%;
  width: 30%;
  background-color: rgb(254, 254, 254);
  border-radius: 5px;
  box-shadow: 2px 2px 5px #888888;
}

.title-container {
  margin-bottom: 20px;
  margin-top: 7%;
}

.title {
  text-align: center;
  font-size: 50px;
  font-weight: bold;
  letter-spacing: 5px;
  color: white; /* 使用绿色作为标题颜色 */
}

.form {
  margin-top: 13px;
  width: 85%;
  height: 80%;
}

.el-button {
  margin-top: 10px;
  margin-bottom: 10px;
}

.login-btn {
  background: linear-gradient(
    to right,
    #4caf50,
    #45a049
  ); /* 使用渐变绿色作为登录按钮背景颜色 */
  border: none;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  padding: 10px 20px;
  transition: background 0.3s ease;
}

.login-btn:hover {
  background: linear-gradient(to right, #45a049, #4caf50); /* 鼠标悬停时的渐变效果 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); /* 添加阴影效果 */
}

.register-btn {
  background: linear-gradient(
    to right,
    #409eff,
    #007bff
  ); /* 使用渐变蓝色作为注册按钮背景颜色 */
  border: none;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  padding: 10px 20px;
  transition: background 0.3s ease;
}

.register-btn:hover {
  background: linear-gradient(to right, #007bff, #409eff); /* 鼠标悬停时的渐变效果 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); /* 添加阴影效果 */
}

.cancel-btn {
  background-color: #909399; /* 使用灰色作为取消按钮背景颜色 */
}

.confirm-btn {
  background-color: #4caf50; /* 使用绿色作为确认按钮背景颜色 */
}

.el-input {
  border-radius: 3px;
}
</style>
