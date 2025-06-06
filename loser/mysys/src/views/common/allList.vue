<template>
  <div class="main">
    <el-row>
      <el-col :span="6">
        <el-input
          v-model="key"
          class="input-with-select"
          placeholder="请输入要搜索的内容"
        >
          <el-button icon="el-icon-search" slot="append" @click="searchM"></el-button>
          <!-- <el-button slot="append" icon="el-icon-search"></el-button> -->
        </el-input>
      </el-col>

      <!-- <el-col :span="6">
                  <el-input v-model="key" placeholder="请输入要搜索的内容"></el-input>
              </el-col>
              <el-col :span="4"> <el-button icon="el-icon-search" circle @click="searchM"></el-button></el-col> -->
    </el-row>
    <el-card class="box-card" v-for="(item, index) in listdata" :key="index">
      <div
        slot="header"
        class="clearfix"
        style="display: flex; justify-content: space-between; align-items: center"
      >
        <span>
          <i class="el-icon-user"></i>
          发布者：{{ item.username }}</span
        >
        <span @click="chat(item)">
          <i class="el-icon-chat-line-square">临时对话</i>
        </span>

        <!-- <el-button size="mini" type="primary" v-if="infoid == 3">修改</el-button> -->
        <div>
          <el-button
            size="mini"
            type="warning"
            icon="el-icon-delete"
            v-if="infoid == 3 || infoid == 4"
            @click="deletepo(item.id)"
            >删除</el-button
          >
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-document-delete"
            v-if="infoid == 3 || infoid == 4"
            @click="remove(item.username)"
            >扣除信誉分</el-button
          >
        </div>
      </div>
      <div class="content">
        <div>
          <i class="el-icon-tickets"></i>
          <!-- <i class="el-icon-tickets"></i> {{ item.describe }} -->
          <div v-html="brightenKeyword(item.describe)"></div>
        </div>
      </div>
      <el-divider></el-divider>
      <div class="common">
        <i class="el-icon-phone-outline"></i> 联系方式：{{ item.tel }}
      </div>
      <!-- <el-divider></el-divider> -->
      <div class="common"><i class="el-icon-date"></i> 发布时间：{{ item.date }}</div>
      <!-- <div>
       
      </div> -->
    </el-card>
    <el-dialog
      title="扣除信誉积分"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-input v-model="score" placeholder="请输入扣除的分数"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 弹幕抽屉 -->
    <el-drawer
      v-model="drawer"
      :with-header="true"
      :visible.sync="vs"
      :wrapper-closable="false"
      :modal="false"
    >
      <div class="Barrage">
        <h3>临时会话</h3>
        <el-scrollbar class="chat-content">
          <!-- recordContent 聊天记录数组-->
          <div v-for="(itemc, indexc) in recordContent" :key="indexc">
            <!-- 其他人发送的 -->
            <div class="word" v-if="!itemc.mineMsg">
              <img :src="itemc.headUrl" />
              <div class="info">
                <p class="time">{{ itemc.nickName }} {{ itemc.timestamp }}</p>
                <div class="info-content">{{ itemc.contactText }}</div>
              </div>
            </div>
            <!-- 我发送的 -->
            <div class="word-my" v-else>
              <div class="info">
                <p class="time">{{ itemc.nickName }} {{ itemc.timestamp }}</p>
                <div class="info-content">{{ itemc.contactText }}</div>
              </div>
              <img :src="itemc.headUrl" />
            </div>
          </div>
        </el-scrollbar>
        <!-- 发送弹幕的输入按钮 -->
        <br />
        <div class="speak">
          <el-input v-model="input" placeholder="发送弹幕" clearable />
          <el-button type="primary" @click="sendto">发送</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>
<script>
import { seeListf, deltePullf, keySearchf } from "../../utils/api/userApi";
import { minScoref } from "../../utils/api/adminApi";
import { notice, mess } from "../../utils/UseElemnt/notice";
import { socket } from "../../utils/websocket.js";
export default {
  data() {
    return {
      listdata: [],
      infoid: "",
      dialogVisible: false,
      vs: false,
      score: "",
      username: "",
      key: "",
      key1: "",
      drawer: "",
      input: "",
      receive: "",
      recordContent: [],

      showClo: true,
    };
  },
  mounted() {
    this.infoid = localStorage.getItem("infoid");

    //根据infoid不同返回不同数据，infoid :1 拾取者，2丢失者 ，3 管理员：返回全部数据
    seeListf({ infoid: localStorage.getItem("infoid") }).then((res) => {
      this.listdata = res.data.data;
    });
  },
  computed: {},

  methods: {
    //删除帖子
    deletepo(tid) {
      this.$confirm("此操作将永久删除记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          deltePullf({ id: tid });
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.$router.go(0);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //打开扣除分数弹窗
    remove(username) {
      this.dialogVisible = true;
      this.username = username;
    },
    //确定提交扣除分数
    confirm() {
      this.dialogVisible = false;
      minScoref({ username: this.username, score: this.score }).then((res) => {
        if (res.data.message === "success") {
          // notice('success', '操作成功')
          mess("操作成功");
        } else {
          notice("error", "操作失败请稍后再试");
        }
      });
    },
    //关闭弹窗
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    searchM() {
      this.key1 = this.key;
      console.log(this.key);
      keySearchf({ key: this.key }).then((res) => {
        this.listdata = res.data.data;
      });
    },
    //高亮显示
    //搜索高亮
    brightenKeyword(val) {
      let keyword = this.key1;
      if (keyword.length > 0) {
        let keywordArr = keyword.split("");
        val = val + "";
        keywordArr.forEach((item) => {
          if (val.indexOf(item) !== -1 && item !== "") {
            val = val.replace(
              new RegExp(item, "g"),
              '<font color="#f75353">' + item + "</font>"
            );
          }
        });
        return val;
      } else {
        return val;
      }
    },
    //点击临时对话
    chat(item) {
      this.vs = true;
      socket.init();
      let that = this;
      socket.receive = function (e) {
        console.log("eeeeeee:" + e);
        that.handleReceive(e);
        //socket.receive
      };
      //this.initWebSocket();
      this.receive = item.username;
    },
    handleReceive(message) {
      console.log("接收到的消22息:", message);
      var parsedObject = JSON.parse(message);
      console.log("senderId:", parsedObject.chatMsg.msg);
      let oneRecord = {
        mineMsg: false,
        headUrl:
          "https://tupian.qqw21.com/article/UploadPic/2019-1/201912319273090791.jpg",
        nickName: parsedObject.chatMsg.senderId,
        timestamp: "",
        contactText: parsedObject.chatMsg.msg,
      };
      this.recordContent.push(oneRecord);
    },
    sendto() {
      let oneRecord = {
        mineMsg: true,
        headUrl:
          "https://tupian.qqw21.com/article/UploadPic/2019-1/201912319273090791.jpg",
        nickName: localStorage.getItem("username"),
        timestamp: "",
        contactText: this.input,
      };
      this.recordContent.push(oneRecord);
      let data = {
        action: 2,
        chatMsg: {
          senderId: localStorage.getItem("username"),
          receiverId: this.receive,
          msg: this.input,
          msgId: null,
        },
        extend: null,
      };
      socket.websock.send(JSON.stringify(data));
    },
    // closescoket() {
    //   socket.close();
    // },
  },
};
</script>
<style scoped>
.box-card {
  margin-top: 5px;
}

.content {
  font-weight: bolder;
  font-size: 20px;
}

.search-bar {
  position: sticky;
  top: 0;
  background-color: #fff;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.highlight {
  color: red;
  /* 设置高亮颜色为红色 */
  font-weight: bold;
  /* 设置高亮文字为粗体 */
}

/* .input-with-select .el-input-group__prepend {
      background-color: #fff;
      width: 130px;
  } */

.speak {
  display: flex;
  gap: 10px;
  height: 30px;
  width: 90%;
  margin: 0 auto;
  padding: 20px;
  border-radius: 5px;
  background-color: #a4adbc;
}

.Barrage {
  display: flex;
  flex-direction: column;
}

.chat-content {
  width: 90%;
  margin: 0 auto;
  padding: 20px;
  border-radius: 5px;
  height: 500px;
  background-color: #e2e3e4;
}

.chat-content .word {
  display: flex;
  margin-bottom: 20px;
}

.chat-content .word img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.chat-content .word .info {
  margin-left: 10px;
}

.chat-content .word .info .time {
  font-size: 12px;
  color: rgba(51, 51, 51, 0.8);
  margin: 0;
  height: 20px;
  line-height: 20px;
  margin-top: -5px;
}

.chat-content .word .info .info-content {
  padding: 10px;
  font-size: 14px;
  background: #fff;
  position: relative;
  margin-top: 8px;
}

.chat-content .word .info .info-content::before {
  position: absolute;
  left: -8px;
  top: 8px;
  content: "";
  border-right: 10px solid #fff;
  border-top: 8px solid transparent;
  border-bottom: 8px solid transparent;
}

.chat-content .word-my {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.chat-content .word-my img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.chat-content .word-my .info {
  width: 90%;
  margin-left: 10px;
  text-align: right;
}

.chat-content .word-my .info .time {
  font-size: 12px;
  color: rgba(51, 51, 51, 0.8);
  margin: 0;
  height: 20px;
  line-height: 20px;
  margin-top: -5px;
  margin-right: 10px;
}

.chat-content .word-my .info .info-content {
  max-width: 70%;
  padding: 10px;
  font-size: 14px;
  float: right;
  margin-right: 10px;
  position: relative;
  margin-top: 8px;
  background: #a3c3f6;
  text-align: left;
}

.chat-content .word-my .info .info-content::after {
  position: absolute;
  right: -8px;
  top: 8px;
  content: "";
  border-left: 10px solid #a3c3f6;
  border-top: 8px solid transparent;
  border-bottom: 8px solid transparent;
}
</style>
