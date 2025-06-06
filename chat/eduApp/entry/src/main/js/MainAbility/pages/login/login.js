import promptAction from '@ohos.promptAction'
import router from '@ohos.router'
import staticApi from '../../common/http/api'
import user from '../../common/http/user'

export default {
    data: {

        username:"",
        password:"",
        UsePwd:true,
        code:"",//验证码,
        email:''
    },
    onDestroy(){

    },
    //渲染登录
    Back(){ this.isLogin=true},
    //登录/注册请求
    Login(){
        console.log("发起登录请求")
         if(this.UsePwd){

                //发起请求
                staticApi.login({"username":this.username,"password":this.password}).then(res=>{
                    console.log("登录返回的信息"+res.data.msg);
                    if(res.data.msg==='登录成功')
                    {
                        promptAction.showToast({
                            message: '登录成功',
                            duration: 2000,
                            bottom:300
                        })

                       this.storeUserData(res.data);
                        console.log("用户存储数据"+user.username);
                        //跳转到主页面
                        console.log("调转页面")
                        this.ToPage()
                    }
                    else{
                        promptAction.showToast({
                            message: '用户名或密码错误',
                            duration: 2000,
                            bottom:300
                        })
                    }
                })
         }

        else{
            staticApi.UseCode({"email":this.email,"code":this.code}).then(res=>{
                console.log("登录返回的信息"+res);
                if(res.data.msg==='登录成功')
                {
                    promptAction.showToast({
                        message: '登录成功',
                        duration: 2000,
                        bottom:300
                    })
                    this.storeUserData(res.data)
                    //跳转到主页面
                    this.ToPage()
                }
                else{
                    promptAction.showToast({
                        message: '用户名或密码错误',
                        duration: 2000,
                        bottom:300
                    })
                }
            })

        }

    },
    //存储用户数据
    storeUserData(data){
        user.set(data)
    },
    //跳转页面
    ToPage(){

        router.pushUrl({
            url:'pages/tabBars/tabBars'

        }, (err) => {
            if (err) {
                console.error(`replaceUrl failed, code is ${err.code}, message is ${err.message}`);
                return;
            }
            console.info('replaceUrl success');
        });

    },
    //判断是否合法输入
    check(){
            if(this.password==""||this.username==""){
                try {
                    promptAction.showToast({
                        message: '信息未填写完整',
                        duration: 2000,
                        bottom:300

                    });
                } catch (error) {
                    console.error(`showToast args error code is ${error.code}, message is ${error.message}`);
                };
                return false;
            }
        return true;
    },
    userInput(e){
        console.log("用户名"+e.value)
        this.username=e.value
    },
    pwdInput(e){
        console.log("密码"+e.value)
        this.password=e.value
    },
    ToMail(){
        console.log("邮箱登录模式")
        this.UsePwd=!this.UsePwd;

    },
    CodeInput(e){
        this.code=e.value;
    },
    GetCode(){
        staticApi.sendCode({"email":this.email})
    },
    emailInput(e){
            this.email=e.value;
    },
    //前往注册界面
    ToRegister(){
        router.pushUrl({
            url:'pages/register/register'
        })
    },


}
