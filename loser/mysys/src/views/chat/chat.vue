<template>
  <div style="height: 100%; width: 100%; background-color: #ededed">
    <div class="wrap">
      <!-- 头部 -->
      <div class="titleBox">
        <img
          src="https://img-qn-2.51miz.com/preview/element/00/01/27/97/E-1279706-3813EBF9.jpg!/quality/90/unsharp/true/compress/true/format/jpg/fh/320"
          alt=""
          class="head_portrait"
          style="margin-left: 20px; margin-right: 20px"
        />
        <span style="color: #fff">官方客服</span>
        <!-- 在线状态弹框 -->
        <el-popover placement="bottom" trigger="click">
          <div class="stateBox2" @click="uploadState(1)">
            <span class="state1"></span>
            <span class="stateText">在线</span>
          </div>
          <div class="stateBox2" @click="uploadState(2)">
            <span class="state2"></span>
            <span class="stateText">离线</span>
          </div>
          <div class="stateBox2" @click="uploadState(3)">
            <span class="state3"></span>
            <span class="stateText">忙碌</span>
          </div>
          <div class="stateBox2" @click="uploadState(4)">
            <span class="state4"></span>
            <span class="stateText">退出</span>
          </div>
          <div class="stateBox" slot="reference" v-if="state == 1">
            <span class="state1"></span>
            <span class="stateText">在线</span>
          </div>
          <div class="stateBox" slot="reference" v-if="state == 2">
            <span class="state2"></span>
            <span class="stateText">离线</span>
          </div>
          <div class="stateBox" slot="reference" v-if="state == 3">
            <span class="state3"></span>
            <span class="stateText">忙碌</span>
          </div>
        </el-popover>
      </div>
      <!-- 底部 -->
      <div class="infoBox">
        <!-- 左边用户列表 -->
        <div class="userList">
          <div class="searchBox">
            <el-input
              placeholder="请输入内容"
              v-model="search"
              class="input-with-select"
              size="mini"
              @input="inquire"
            >
              <i
                class="el-icon-search el-input__icon"
                slot="suffix"
                @click="handleIconClick"
              >
              </i>
            </el-input>
            <el-button
              icon="el-icon-plus"
              size="mini"
              type="primary"
              @click="dialogVisible = true"
            ></el-button>
          </div>
          <div class="userListBox">
            <div
              v-for="(item, index) in userListData"
              :key="index"
              @click="getAct(item, index)"
              :class="item.id == act ? 'userFlexAct' : 'userFlex'"
            >
              <div>
                <img
                  :src="item.url"
                  alt="头像"
                  class="head_portrait2"
                  style="margin-left: 20px"
                />
              </div>
              <div style="margin-right: 40px">
                <el-tooltip :content="item.username" placement="bottom" effect="light">
                  <div style="color: #565656" class="userName">
                    {{ item.username }}
                  </div>
                </el-tooltip>
                <div class="userInfo">{{ item.info }}</div>
              </div>
              <div style="margin-right: 10px; font-size: 14px; color: #ccc">
                {{ item.timer }}
              </div>
            </div>
          </div>
        </div>
        <!-- 右边输入框和信息展示 -->
        <div class="infoList">
          <!-- 信息 -->
          <div class="infoTop" ref="scrollBox" id="box">
            <div
              :class="item.position == 'left' ? 'chatInfoLeft' : 'chatInfoRight'"
              v-for="(item, index) in userInfoList"
              :key="index"
            >
              <img :src="item.url" alt="头像" class="head_portrait2" />
              <div :class="item.position == 'left' ? 'chatLeft' : 'chatRight'">
                <div class="text" v-html="item.info"></div>
              </div>
            </div>
          </div>
          <!-- 输入框 -->
          <div class="infoBottom">
            <div class="infoIcon">
              <i @click="extend('照片上传')" class="el-icon-picture-outline-round"></i>
              <i @click="extend('发送商品')" class="el-icon-sell"></i>
              <i @click="extend('设置')" class="el-icon-setting"></i>
              <i @click="extend('聊天记录')" class="el-icon-chat-dot-round"></i>
              <i @click="extend('更多选项')" class="el-icon-more-outline"></i>
            </div>
            <textarea
              type="textarea"
              class="infoInput"
              v-model="textarea"
              @keydown.enter.exact="handlePushKeyword($event)"
              @keyup.ctrl.enter="lineFeed"
              :disabled="isshow == 1 ? false : true"
            />
            <div class="fasong" @click="setUp" v-show="isshow == 1 ? true : false">
              发送
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 搜索框边 + 号弹框 -->
    <el-dialog
      title="选择需要添加的联系人"
      :visible.sync="dialogVisible"
      width="30%"
      :modal="false"
    >
      <span>自定义页面，还没想好写什么功能</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //websocket部分
      path: "ws://localhost:8080/webSocket/ss", //后台的websocket地址，找后端要
      ws: null, //建立的连接
      lockReconnect: false, //是否真正建立连接
      timeout: 10 * 1000, //30秒一次心跳
      timeoutObj: null, //心跳心跳倒计时
      serverTimeoutObj: null, //心跳倒计时
      timeoutnum: null, //断开 重连倒计时

      // 在线状态
      state: 1,
      //搜索用户
      search: "",
      //用户列表渲染数据
      userListData: [
        {
          id: 0,
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info: "在吗",
          timer: "2022/8/9",
        },
        {
          id: 1,
          url:
            "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
          username: "超人不换内裤",
          info: "在吗在吗",
          timer: "2022/8/1",
        },
        {
          id: 2,
          url:
            "https://img1.baidu.com/it/u=2029513305,2137933177&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=472",
          username: "王子变蛤蟆",
          info: "你好，在吗",
          timer: "2022/8/9",
        },
        {
          id: 3,
          url:
            "https://img1.baidu.com/it/u=1960292808,1761809160&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "皇太子",
          info: "给我看一下这个情况呢",
          timer: "2022/8/2",
        },
        {
          id: 4,
          url:
            "https://img2.baidu.com/it/u=3684117954,695988885&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400",
          username: "飞天小女警",
          info: "模拟数据发送一下呢",
          timer: "2022/8/4",
        },
        {
          id: 5,
          url:
            "https://img2.baidu.com/it/u=4122738859,2522601053&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "罗大大大",
          info: "在吗",
          timer: "2022/8/5",
        },
        {
          id: 6,
          url:
            "https://img0.baidu.com/it/u=661161858,172661768&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "王子花",
          info: "好的，我知道了",
          timer: "2022/8/9",
        },
        {
          id: 7,
          url:
            "https://img2.baidu.com/it/u=835899845,548435859&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "开盖有奖",
          info: "试试看吧，再发一次",
          timer: "2022/8/9",
        },
        {
          id: 8,
          url:
            "https://img0.baidu.com/it/u=4065107391,2142799144&fm=253&fmt=auto&app=138&f=JPEG?w=527&h=500",
          username: "日本大爆炸",
          info: "在吗",
          timer: "2022/8/5",
        },
        {
          id: 9,
          url:
            "https://img2.baidu.com/it/u=2860188096,638334621&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "秋天的第一杯奶茶",
          info: "好的，我知道了",
          timer: "2022/8/9",
        },
        {
          id: 10,
          url:
            "https://img0.baidu.com/it/u=1694074520,2517635995&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "潮汐",
          info: "试试看吧，再发一次",
          timer: "2022/8/9",
        },
      ],
      //用户列表筛选数据
      userListDatas: [
        {
          id: 0,
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info: "在吗",
          timer: "2022/8/9",
        },
        {
          id: 1,
          url:
            "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
          username: "超人不换内裤",
          info: "在吗在吗",
          timer: "2022/8/1",
        },
        {
          id: 2,
          url:
            "https://img1.baidu.com/it/u=2029513305,2137933177&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=472",
          username: "王子变蛤蟆",
          info: "你好，在吗",
          timer: "2022/8/9",
        },
        {
          id: 3,
          url:
            "https://img1.baidu.com/it/u=1960292808,1761809160&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "皇太子",
          info: "给我看一下这个情况呢",
          timer: "2022/8/2",
        },
        {
          id: 4,
          url:
            "https://img2.baidu.com/it/u=3684117954,695988885&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400",
          username: "飞天小女警",
          info: "模拟数据发送一下呢",
          timer: "2022/8/4",
        },
        {
          id: 5,
          url:
            "https://img2.baidu.com/it/u=4122738859,2522601053&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "罗大大大",
          info: "在吗",
          timer: "2022/8/5",
        },
        {
          id: 6,
          url:
            "https://img0.baidu.com/it/u=661161858,172661768&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "王子花",
          info: "好的，我知道了",
          timer: "2022/8/9",
        },
        {
          id: 7,
          url:
            "https://img2.baidu.com/it/u=835899845,548435859&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "开盖有奖",
          info: "试试看吧，再发一次",
          timer: "2022/8/9",
        },
        {
          id: 8,
          url:
            "https://img0.baidu.com/it/u=4065107391,2142799144&fm=253&fmt=auto&app=138&f=JPEG?w=527&h=500",
          username: "日本大爆炸",
          info: "在吗",
          timer: "2022/8/5",
        },
        {
          id: 9,
          url:
            "https://img2.baidu.com/it/u=2860188096,638334621&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "秋天的第一杯奶茶",
          info: "好的，我知道了",
          timer: "2022/8/9",
        },
        {
          id: 10,
          url:
            "https://img0.baidu.com/it/u=1694074520,2517635995&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "潮汐",
          info: "试试看吧，再发一次",
          timer: "2022/8/9",
        },
      ],
      //用户点击选中变色
      act: Number,
      // 加号弹框
      dialogVisible: false,
      //模拟花间一壶酒用户的历史信息
      userInfoList2: [
        {
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info: "在吗",
          timer: "2022/8/9",
          position: "left",
        },
        {
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info: "在吗",
          timer: "2022/8/9",
          position: "left",
        },
        {
          url:
            "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
          username: "超人不换内裤",
          info: "在吗在吗",
          timer: "2022/8/1",
          position: "right",
        },
        {
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info: "在吗",
          timer: "2022/8/9",
          position: "left",
        },
        {
          url:
            "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
          username: "超人不换内裤",
          info: "在吗在吗",
          timer: "2022/8/1",
          position: "right",
        },
        {
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info: "在吗在吗在吗在吗在吗在吗在吗",
          timer: "2022/8/9",
          position: "left",
        },
        {
          url:
            "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
          username: "超人不换内裤",
          info: "在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗",
          timer: "2022/8/1",
          position: "right",
        },
        {
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info:
            "在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗在吗",
          timer: "2022/8/9",
          position: "left",
        },
        {
          url:
            "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
          username: "超人不换内裤",
          info: "在吗在吗在吗在吗在吗在吗",
          timer: "2022/8/1",
          position: "right",
        },
      ],
      //模拟超人不换内裤用户的历史信息
      userInfoList3: [
        {
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info: "测试测试",
          timer: "2022/8/9",
          position: "left",
        },
        {
          url:
            "https://img1.baidu.com/it/u=592570905,1313515675&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          username: "花间一壶酒",
          info: "测试",
          timer: "2022/8/9",
          position: "left",
        },
        {
          url:
            "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
          username: "超人不换内裤",
          info: "测试测试测试测试",
          timer: "2022/8/1",
          position: "right",
        },
      ],
      //历史信息
      userInfoList: [],
      //输入框
      textarea: "",
      //滚动条距离顶部距离
      scrollTop: 0,
      //发送和输入显隐
      isshow: 0,
    };
  },
  created() {
    this.initWebpack();
  },
  beforeDestroy() {
    // 离开页面后关闭连接
    this.ws.close();
    // 清除时间
    clearTimeout(this.timeoutObj);
    clearTimeout(this.serverTimeoutObj);
  },
  methods: {
    //切换客服状态
    uploadState(state) {
      if (state !== 4) {
        this.state = state;
      } else {
        this.$confirm("是否退出登录?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$message({
              type: "success",
              message: "退出成功!",
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消退出",
            });
          });
      }
    },
    //搜索icon
    handleIconClick() {
      console.log(1);
    },
    //点击用户
    getAct(val, index) {
      this.isshow = 1;
      // 点击用户切换数据时先清除监听滚动事件，防止出现没有历史数据的用户，滚动条为0，会触发滚动事件
      this.$refs.scrollBox.removeEventListener("scroll", this.srTop);
      //点击变色
      this.act = val.id;
      //清空消息数组
      this.userInfoList = [];
      // 模拟一下点击用户出现历史记录的样子，实际开发中是axios请求后数组赋值然后调用setPageScrollTo
      if (val.username == "花间一壶酒") {
        this.userInfoList = this.userInfoList2;
        // 直接调用不生效：因为你历史数据刚给，渲染的时候盒子高度还没有成型，所以直接调用拿不到，用个定时器让他在下一轮循环中调用，盒子就已经生成了
        this.$nextTick(() => {
          // 一定要用nextTick
          this.setPageScrollTo();
          //页面滚动条距离顶部高度等于这个盒子的高度
          this.$refs.scrollBox.scrollTop = this.$refs.scrollBox.scrollHeight;
        });
      } else if (val.username == "超人不换内裤") {
        this.userInfoList = this.userInfoList3;
        // 直接调用不生效：因为你历史数据刚给，渲染的时候盒子高度还没有成型，所以直接调用拿不到，用个定时器让他在下一轮循环中调用，盒子就已经生成了
        this.$nextTick(() => {
          // 一定要用nextTick
          this.setPageScrollTo();
          //页面滚动条距离顶部高度等于这个盒子的高度
          this.$refs.scrollBox.scrollTop = this.$refs.scrollBox.scrollHeight;
        });
      } else if (val.username == "王子变蛤蟆") {
        this.$nextTick(() => {
          // 一定要用nextTick
          this.setPageScrollTo();
          //页面滚动条距离顶部高度等于这个盒子的高度
          this.$refs.scrollBox.scrollTop = this.$refs.scrollBox.scrollHeight;
        });
      }
    },
    // 模糊搜索用户
    inquire() {
      let fuzzy = this.search;
      if (fuzzy) {
        this.userListData = this.userListDatas.filter((item) => {
          return item.username.includes(fuzzy);
        });
      } else {
        this.userListData = this.userListDatas;
      }
    },
    //发送
    setUp() {
      console.log("发送内容:", this.textarea);
      this.userInfoList2.push({
        url:
          "https://img2.baidu.com/it/u=2859542338,3761174075&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500",
        username: "超人不换内裤",
        info: this.textarea,
        timer: "2022/8/1",
        position: "right",
      });
      this.textarea = "";
      // 页面滚动到底部
      this.$nextTick(() => {
        // 一定要用nextTick
        this.setPageScrollTo();
        //页面滚动条距离顶部高度等于这个盒子的高度
        this.$refs.scrollBox.scrollTop = this.$refs.scrollBox.scrollHeight;
      });
    },
    // 监听键盘回车阻止换行并发送
    handlePushKeyword(event) {
      console.log(event);
      if (event.keyCode === 13) {
        event.preventDefault(); // 阻止浏览器默认换行操作
        this.setUp(); //发送文本
        return false;
      }
    },
    // 监听按的是ctrl + 回车，就换行
    lineFeed() {
      console.log("换行");
      this.textarea = this.textarea + "\n";
    },
    //点击icon
    extend(val) {
      alert("你点击了：" + val);
    },
    //滚动条默认滚动到最底部
    setPageScrollTo(s, c) {
      //获取中间内容盒子的可见区域高度
      this.scrollTop = document.querySelector("#box").offsetHeight;
      setTimeout((res) => {
        //加个定时器，防止上面高度没获取到，再获取一遍。
        if (this.scrollTop != this.$refs.scrollBox.offsetHeight) {
          this.scrollTop = document.querySelector("#box").offsetHeight;
        }
      }, 100);
      //scrollTop：滚动条距离顶部的距离。
      //把上面获取到的高度座位距离，把滚动条顶到最底部
      this.$refs.scrollBox.scrollTop = this.scrollTop;
      //判断是否有滚动条,有滚动条就创建一个监听滚动事件，滚动到顶部触发srTop方法
      if (this.$refs.scrollBox.scrollTop > 0) {
        this.$refs.scrollBox.addEventListener("scroll", this.srTop);
      }
    },
    //滚动条到达顶部
    srTop() {
      //判断：当滚动条距离顶部为0时代表滚动到顶部了
      if (this.$refs.scrollBox.scrollTop == 0) {
        //逻辑简介：
        //到顶部后请求后端的方法，获取第二页的聊天记录，然后插入到现在的聊天数据前面。
        //如何插入前面：可以先把获取的数据保存在 A 变量内，然后 this.userInfoList=A.concat(this.userInfoList)把数组合并进来就可以了

        //拿聊天记录逻辑:
        //第一次调用一个请求拉历史聊天记录，发请求时参数带上页数 1 传过去，拿到的就是第一页的聊天记录，比如一次拿20条。你显示出来
        //然后向上滚动到顶部时，触发新的请求，在请求中把分页数先 +1 然后再请求，这就拿到了第二页数据，然后通过concat合并数组插入进前面，依次类推，功能完成！
        alert("已经到顶部了");
      }
    },

    //-----------------------以下是websocket部分方法

    // 初始化websocket链接
    initWebpack() {
      if (typeof WebSocket === "undefined") {
        alert("您的浏览器不支持socket");
      } else {
        this.ws = new WebSocket(this.path); //实例
        this.ws.onopen = this.onopen; //监听链接成功
        this.ws.onmessage = this.onmessage; //监听后台返回消息
        this.ws.onclose = this.onclose; //监听链接关闭
        this.ws.onerror = this.onerror; //监听链接异常
      }
    },
    //重新连接
    reconnect() {
      var that = this;
      if (that.lockReconnect) {
        return;
      }
      that.lockReconnect = true;
      //没连接上会一直重连，设置延迟避免请求过多
      that.timeoutnum && clearTimeout(that.timeoutnum);
      that.timeoutnum = setTimeout(function () {
        that.initWebpack(); //新连接
        that.lockReconnect = false;
      }, 5000);
    },
    //重置心跳
    reset() {
      var that = this;
      clearTimeout(that.timeoutObj); //清除心跳倒计时
      clearTimeout(that.serverTimeoutObj); //清除超时关闭倒计时
      that.start(); //重启心跳
    },
    //开启心跳
    start() {
      var self = this;
      self.timeoutObj && clearTimeout(self.timeoutObj); //心跳倒计时如果有值就清除掉，防止重复
      self.serverTimeoutObj && clearTimeout(self.serverTimeoutObj); //超时关闭倒计时如果有值就清除掉，防止重复
      //然后从新开一个定时器
      self.timeoutObj = setTimeout(function () {
        //这里通过readyState判断链接状态，有四个值，0：正在连接，1：已连接，2：正在断开，3：已经断开或者链接不成功
        if (self.ws.readyState == 1) {
          //如果连接正常，给后天发送一个值，可以自定义，然后后台返回我们一个信息，我们接收到后会触发onmessage方法回调
          self.ws.send("ping");
        } else {
          //如果检测readyState不等于1那也就代表不处在链接状态，那就是不正常的，那就调用重连方法
          self.reconnect();
        }
        //从新赋值一个超时计时器，这个定时器的作用：当你触发心跳的时候可能会出现一个情况，后台崩了，前台发了个心跳，没有回应，就不会触发onmessage方法
        //所以我们需要在这个心跳发送出去了后，再开一个定时器，用于监控心跳返回的时间，比如10秒，那么10秒内如果后台回我了，触发onmessage方法，自然就会把心跳时间和超时倒计时一起清空掉
        //也就不会触发这个关闭连接，但是如果10秒后还是没有收到回应，那么就会触发关闭连接，而关闭连接方法内又会触发重连方法，循环就走起来了。
        self.serverTimeoutObj = setTimeout(function () {
          //如果超时了就关闭连接
          self.ws.close();
        }, self.timeout);
      }, self.timeout);
    },
    //连接成功
    onopen() {
      console.log("连接成功");
      if (this.ws.readyState == 1) {
        //如果连接正常，给后天发送一个值，可以自定义，然后后台返回我们一个信息，我们接收到后会触发onmessage方法回调
        this.ws.send("链接上啦！！！");
      }
      this.start(); //链接成功后开启心跳
    },
    //接受后台信息回调
    onmessage(e) {
      /**这里写自己的业务逻辑代码**/
      console.log("收到后台信息:", e.data);
      this.reset(); //收到服务器信息，心跳重置
    },
    //关闭连接回调
    onclose(e) {
      console.log("连接关闭");
      this.reconnect(); //重连
    },
    //连接异常回调
    onerror(e) {
      console.log("出现错误");
      this.reconnect(); //重连
    },
  },
};
</script>

<style scoped>
.wrap {
  height: 80%;
  width: 55%;
  background-color: #f2f2f2;
  margin: auto;
  transform: translateY(10%);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 10px;
}
.titleBox {
  height: 10%;
  width: 100%;
  background-image: linear-gradient(to right, #1e76bc, #69a3d5);
  display: flex;
  align-items: center;
  border-top-right-radius: 10px;
  border-top-left-radius: 10px;
}
.infoBottom {
  height: 30%;
  display: flex;
  flex-direction: column;
}
/* 输入框 */
.infoInput {
  height: 58%;
  width: 100%;
  border: none;
  resize: none;
  padding: 10px;
  box-sizing: border-box;
  background-color: #f2f2f2;
  color: #434343;
}
.fasong {
  height: 30px;
  width: 80px;
  background-color: #e8e8e8;
  text-align: center;
  line-height: 30px;
  border-radius: 4px;
  color: #58df4d;
  margin-top: 1%;
  align-self: flex-end;
  margin-right: 20px;
  cursor: pointer;
}
.infoIcon {
  height: 40px;
  width: 100%;
  display: flex;
  align-items: center;
}
.infoIcon i {
  font-size: 24px;
  color: #676767;
  margin-left: 15px;
  cursor: pointer;
}
/* 头像 */
.head_portrait {
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.head_portrait2 {
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.stateBox {
  margin-left: 20px;
  padding: 1px 8px;
  background-color: #fff;
  border-radius: 10px;
  text-align: center;
  cursor: pointer;
}
.stateBox2 {
  margin-left: 20px;
  padding: 1px 8px;
  background-color: #fff;
  border-radius: 10px;
  text-align: center;
  cursor: pointer;
}
.stateBox2:hover {
  background-color: #dcdcdc;
}
/* 在线 */
.state1 {
  display: inline-block;
  height: 10px;
  width: 10px;
  border-radius: 50%;
  background-color: #8ee80e;
}
/* 离线 */
.state2 {
  display: inline-block;
  height: 10px;
  width: 10px;
  border-radius: 50%;
  background-color: #cacaca;
}
/* 忙碌 */
.state3 {
  display: inline-block;
  height: 10px;
  width: 10px;
  border-radius: 50%;
  background-color: #ff8c1e;
}
/* 退出登录 */
.state4 {
  display: inline-block;
  height: 10px;
  width: 10px;
  border-radius: 50%;
  background-color: #7e7e7e;
}
.stateText {
  font-size: 14px;
  margin-left: 5px;
}
/* 列表和信息 */
.infoBox {
  height: 90%;
  width: 100%;
  display: flex;
}
/* 用户列表大盒子 */
.userList {
  height: 100%;
  width: 300px;
  border-right: 1px solid #ccc;
  display: flex;
  flex-direction: column;
}
/* 用户列表 */
.userListBox {
  flex: 1;
  width: 100%;
  overflow: auto;
}
/* 信息外层盒子 */
.infoList {
  height: 100%;
  width: 72%;
}
/* 信息列表 */
.infoTop {
  height: 70%;
  width: 100%;
  border-bottom: 1px solid #ccc;
  padding: 10px;
  box-sizing: border-box;
  overflow: auto;
}
/* 对方发的信息样式 */
.chatInfoLeft {
  min-height: 70px;
  margin-left: 10px;
  margin-top: 10px;
  display: flex;
}
.chatLeft {
  margin-left: 15px;
  flex: 1;
}
.chatLeft .text {
  color: #434343;
  margin-top: 8px;
  background-color: #e3e3e3;
  display: inline-block;
  padding: 6px 10px;
  border-radius: 10px;
  max-width: 50%;
  /* 忽略多余的空白，只保留一个空白 */
  white-space: normal;
  /* 换行显示全部字符 */
  word-break: break-all;
  white-space: pre-wrap;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

/* 自己发的信息样式 */
.chatInfoRight {
  height: 70px;
  margin-left: 10px;
  margin-top: 10px;
  display: flex;
  flex-direction: row-reverse;
}
.chatRight {
  margin-right: 15px;
  flex: 1;
  /* 用align-items把元素靠右对齐 */
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
.chatRight .text {
  color: #434343;
  margin-top: 8px;
  background-color: #95ec69;
  display: inline-block;
  padding: 6px 10px;
  border-radius: 10px;
  max-width: 50%;
  /* 忽略多余的空白，只保留一个空白 */
  white-space: normal;
  /* 换行显示全部字符 */
  word-break: break-all;
  white-space: pre-wrap;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.searchBox {
  padding: 4px 2px;
  border-bottom: 1px solid #ededed;
}
.input-with-select {
  width: 80%;
  margin-right: 2%;
}
/* 点击用户变色 */
.userFlexAct {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  border-bottom: 1px solid #e8e8e8;
  cursor: pointer;
  background-color: #e8e8e8;
}
/* 用户默认颜色 */
.userFlex {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  border-bottom: 1px solid #e8e8e8;
  cursor: pointer;
}
/* 用户名 */
.userName {
  width: 100px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
/* 简略信息 */
.userInfo {
  width: 100px;
  font-size: 14px;
  color: #ccc;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-top: 3px;
}

/* 滚动条样式 */
::-webkit-scrollbar {
  width: 5px;
  height: 10px;
}
::-webkit-scrollbar-thumb {
  background-color: #dbd9d9;
  border-radius: 3px;
}
</style>
