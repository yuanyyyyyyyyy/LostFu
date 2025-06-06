import router from '@ohos.router'
import staticApi from '../../common/http/api'
import promptAction from '@ohos.promptAction'
import user from '../../common/http/user'
export default {
    data: {
        title: "",
        file:"",
        list:[],
    },
    onReady() {
        this.selectlist();
        const params = router.getParams();
        if (params && params.data) {
            this.file = params.data;
            console.log("拿到bsrc"+this.file)
        } else {
            console.error('Unable to get data from params');
            // 可以设置默认值或者进行其他处理
            this.file = 'default_value';
        }


    },
    selectlist(){
        staticApi.getformname({"uid":user.uid.toString()}).then(res=>{
            console.log("返回的信息"+res.data);
            this.list=res.data;

        })
    },

    clickroot(){
        console.log("跳转")
        router.pushUrl({
            url: 'pages/testpage2/testpage2',
            params: {
                data2: {

                }
            }
        })
    },
    wordtpdf(){
        console.log("发起转换请求")
        //发起请求
        staticApi.wordtopdf({"file":this.file,"uid":user.uid.toString()}).then(res=>{
            console.log("转换返回的信息"+res);
            if(res.data==="转换成功")
            {
                promptAction.showToast({
                    message: '转换成功',
                    duration: 2000,
                    bottom:300
                })
            }
            else{
                promptAction.showToast({
                    message: '转换失败',
                    duration: 2000,
                    bottom:300
                })
            }
        })
    },
    pdftword(){
        console.log("发起转换请求")
        //发起请求
        staticApi.pdftoword({"file":this.file,"uid":user.uid.toString()}).then(res=>{
            console.log("转换返回的信息"+res);
            if(res.data==="转换成功")
            {
                promptAction.showToast({
                    message: '转换成功',
                    duration: 2000,
                    bottom:300
                })
            }
            else{
                promptAction.showToast({
                    message: '转换失败',
                    duration: 2000,
                    bottom:300
                })
            }
        })
    },
    imagetword(){
        console.log("发起转换请求")
        //发起请求
        staticApi.imgtoword({"file":this.file,"uid":user.uid.toString()}).then(res=>{
            console.log("转换返回的信息"+res);
            if(res.data==="转换成功")
            {
                promptAction.showToast({
                    message: '转换成功',
                    duration: 2000,
                    bottom:300
                })
            }
            else{
                promptAction.showToast({
                    message: '转换失败',
                    duration: 2000,
                    bottom:300
                })
            }
        })
    },
    imagetpdf(){
        console.log("发起转换请求")
        //发起请求
        staticApi.imgtopdf({"file":this.file,"uid":user.uid.toString()}).then(res=>{
            console.log("转换返回的信息"+res);
            if(res.data==="转换成功")
            {
                promptAction.showToast({
                    message: '转换成功',
                    duration: 2000,
                    bottom:300
                })
            }
            else{
                promptAction.showToast({
                    message: '转换失败',
                    duration: 2000,
                    bottom:300
                })
            }
        })
    }
}
