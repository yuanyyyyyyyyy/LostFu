import router from '@ohos.router'
export default {
    data: {
        title: "",
        list:[
            {name:"test2.docx", check:false, src:"C:\\Users\\史帅\\Desktop\\test\\word\\test2.docx"},
            {name:"test3.docx", check:false, src:"C:\\Users\\史帅\\Desktop\\test\\word\\test3.docx"},
            {name:"test3.pdf", check:false, src:"C:\\Users\\史帅\\Desktop\\test\\pdf\\test3.pdf"},
            {name:"test1.docx", check:false, src:"C:\\Users\\史帅\\Desktop\\test\\word\\test1.docx"},

        ],
        bsrc:"",

    },

    // selectall(){
    //     for (let i = 0; i < this.list.length; i++) {
    //         this.list[i].check= true;
    //         this.data[i]=this.list[i].src;
    //         console.log(this.data);
    //     }
    // },
    checkbox(e){
        console.log(e);

            this.list[e].check=true;
            this.bsrc=this.list[e].src;
        console.log(this.bsrc);

    },
    backpage(){
        console.log("跳转")
        router.pushUrl({
            url: 'pages/testpage1/testpage1',
            params: {
                data:this.bsrc
            }
        })
    }

}
