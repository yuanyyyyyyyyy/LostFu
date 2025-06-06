import router from '@ohos.router';
import staticApi from '../../common/http/api';
import  user  from '../../common/http/user';
export default {
    data: {
        title: "",
        selfInfo:
            {
                name:"",
                headImg:"",
                phoneNum:""
            },
        funcInfo:[
            {
               fName:"修改个人信息", fImg:"http://123.249.1.184:888/img/update.png",toPage:"pages/infochange/infochange"
            },
            {
                fName:"历史问答记录", fImg:"http://123.249.1.184:888/img/history.png",toPage:"pages/history/history"
            },
            {
                fName:"收藏", fImg:"http://123.249.1.184:888/img/collect.png",toPage:""
            },
            {
                fName:"帮助", fImg:"http://123.249.1.184:888/img/help.png",toPage:""
            },
        ]

    },
    onReady() {
        console.log("获取个人信息")
        staticApi.getInfo({"id":user.uid.toString()}).then(res=> {
            console.log("查询信息" + res);
            this.selfInfo.name=res.data.username;
            this.selfInfo.headImg=res.data.avatar;
            this.selfInfo.phoneNum=res.data.phone;
            console.log("===="+this.selfInfo.headImg);
        })
    },
    route(data){
        router.pushUrl({
            url:data

        }, (err) => {
            if (err) {
                console.error(`replaceUrl failed, code is ${err.code}, message is ${err.message}`);
                return;
            }
            console.info('replaceUrl success');
        });

    },
}