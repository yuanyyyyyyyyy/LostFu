import promptAction from '@ohos.promptAction'
import router from '@ohos.router'
import staticApi from '../../common/http/api'
import user from '../../common/http/user'
export default {
    data: {
        title: "",
        text:[{"name":"昵称","data":"请输入昵称"},
            {"name":"电话","data":"请输入电话"},
            {"name":"邮箱","data":"请输入邮箱"}
        ],
        username:"",
        phone:"",
        email:"",
        avatar:"",
        img:[
            {src:"http://123.249.1.184:888/img/avatar/1.jpg",check:false},
            {src:"http://123.249.1.184:888/img/avatar/2.jpg",check:false},
            {src:"http://123.249.1.184:888/img/avatar/3.jpg",check:false},
            {src:"http://123.249.1.184:888/img/avatar/4.png",check:false},
            {src:"http://123.249.1.184:888/img/avatar/5.jpg",check:false},
            {src:"http://123.249.1.184:888/img/avatar/6.jpg",check:false},
            {src:"http://123.249.1.184:888/img/avatar/7.jpg",check:false},
        ]

    },
    onInit(){
        staticApi.getInfo({"id":user.uid.toString()}).then(res=> {
            console.log("查询信息" + res);
            this.username=res.data.username;
            this.phone=res.data.phone;
            this.email=res.data.email;
            console.log("===="+this.username+this.phone+this.email);
        })
    },

    // 昵称输入框内容改变时的事件处理函数
    namechange(event) {
        var name = event.value; // 获取输入框中的昵称
        this.username=name;
        console.log("昵称已更改为: " + this.username);
    },

    // 电话输入框内容改变时的事件处理函数
    phonechange(event) {
        var phone = event.value; // 获取输入框中的电话号码
        this. phone=phone;
        console.log("电话已更改为: " + this.phone);

    },

    // 邮箱输入框内容改变时的事件处理函数
    emailchange(event) {
        var email = event.value; // 获取输入框中的邮箱地址
        this. email=email;
        console.log("邮箱已更改为: " + this.email);

    },
    infosave(){
        console.log("发起修改请求")
        //发起请求
        staticApi.changeinfo({"id":user.uid.toString(),"username":this.username,"phone":this.phone,"email":this.email,"avatar":this.avatar}).then(res=>{
            console.log("登录修改返回的信息"+res);
            if(res.data==='更新成功')
            {
                promptAction.showToast({
                    message: '修改成功',
                    duration: 2000,
                    bottom:300
                })
            }
            else{
                promptAction.showToast({
                    message: '修改失败',
                    duration: 2000,
                    bottom:300
                })
            }
        })
    },
    imageclick(){
        this.$element("log").show();
    },
    bt(){
        this.$element("log").close();
    },
    imgg(e){
        this.img[e].check=true;
        console.log(e);
        this.avatar=this.img[e].src;
        console.log("avatar:"+this.avatar)
    }
    ,
    toPage(){
        router.replaceUrl({
            url:'pages/tabBars/tabBars'
        })
    }

}