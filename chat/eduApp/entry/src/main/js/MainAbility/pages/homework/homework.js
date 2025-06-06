import picker from '@ohos.file.picker';
import featureAbility from '@ohos.ability.featureAbility';
import Want from '@ohos.application.Want';
// 创建一个PhotoViewPicker实例
import fetch from '@system.fetch';
import file from '@system.file';
import device from '@system.device';
import staticApi from '../../common/http/api';
import promptAction from '@ohos.promptAction';

export default {
    data: {
        title: "Hello World",
        photoWay: [
            {
                name: "作业批改",
                iconSrc: "/common/static/check.png"
            },
            {
                name: "文字识别",
                iconSrc: "/common/static/textcopy.png"
            },
        ],
        imageData: [
            { src: 'http://123.249.1.184:888/img/555.jpg', checked: false },
            { src: 'http://123.249.1.184:888/img/a.jpg', checked: false },
            { src: 'http://123.249.1.184:888/img/333.jpg', checked: false }
        ],
        chosePic:0,
        backUrl:"",
        result:"",
        funid:0
    },
    onInit() {
        // 初始化操作
    },
    clickDia1(){
        this.funid=0;
        this.$element("dia1").show();
    },
    clickDia2(){
        this.funid=1;
        this.$element("dia1").show();
    },
    handRadioClick(index){
        console.log(index)
        this.imageData[index].checked=!this.imageData[index].checked;
        this.chosePic=index;
    },
    handleSubmit(){
        if(this.funid==0){
        staticApi.photoReco({"url":this.imageData[this.chosePic].src}).then(res=>{
            console.log("返回的信息"+res.data);
                this.backUrl=res.data;
                console.log(this.backUrl)
        })

        }
        else{
            staticApi.recognize({"url":this.imageData[this.chosePic].src}).then(res=>{
                console.log("返回的信息"+res.data);
                this.result=res.data
                console.log(this.result)
            })

        }
        this.$element("dia1").close();
    },

    }

