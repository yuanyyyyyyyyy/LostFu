import promptAction from '@ohos.promptAction'
import staticApi from '../../common/http/api'
import router from '@ohos.router'
export default {
    data: {
        username:"",
        password:"",
        checkPassword:"",
        email:""
    },
    onInit() {

    },

    userInput(e){
        console.log("用户名"+e.value)
        this.username=e.value
    },
    pwdInput(e){
        console.log("密码"+e.value)
        this.password=e.value
    },

    checkPwdInput(e){
        console.log("检查的密码"+e.value)
        this.checkPassword=e.value
    },

    emailInput(e){
        console.log("检查的密码"+e.value)
        this.email=e.value
    },
    register(){
        if(this.password==""||this.username==""){
            try {
                promptAction.showToast({
                    message: '用户名和密码不能为空',
                    duration: 2000,
                    bottom:300
                });
            } catch (error) {
                console.error(`showToast args error code is ${error.code}, message is ${error.message}`);
            };
        }else if (this.password!=this.checkPassword){
            try {
                promptAction.showToast({
                    message: '两次密码不相同',
                    duration: 2000,
                    bottom:300
                });
            } catch (error) {
                console.error(`showToast args error code is ${error.code}, message is ${error.message}`);
            };
        }else{
            console.log("发起注册请求")
            //发起请求
            staticApi.register({"username":this.username,"password":this.password,"email":this.email}).then(res=>{
                console.log("登录返回的信息"+res);
                if(res.data==='注册成功')
                {
                    promptAction.showToast({
                        message: '注册成功',
                        duration: 2000,
                        bottom:300
                    })
                    //this.storeUserData(res.data)
                    //跳转到登录页面
                    this.ToLogin()
                }
                else{
                    promptAction.showToast({
                        message: '用户名已被占用',
                        duration: 2000,
                        bottom:300
                    })
                }
            })

        }

    },
    ToLogin(){

        router.pushUrl({
            url:'pages/login/login'

        }, (err) => {
            if (err) {
                console.error(`replaceUrl failed, code is ${err.code}, message is ${err.message}`);
                return;
            }
            console.info('replaceUrl success');
        });

    },
}
