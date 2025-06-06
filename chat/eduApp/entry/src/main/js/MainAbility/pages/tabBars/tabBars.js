import  staticApi from '../../common/http/api'
import user from '../../common/http/user'
export default {
    data: {
        tabList:[],//后端返回的菜单栏的数据
        toPage:0,//跳转的页面index

    },
    //手动点击，跳转页面
    NavigateToPage(index){
        //this.toPage=index;
        console.log("前往"+index+"页")
        this.$element('swiper').swipeTo({index: index});//原生事件
        for (let i = 0; i < this.tabList.length; i++) {
            this.tabList[i].selected=false;
        }
        this.tabList[index].selected=true;

    },
    //左右滑动主要是改变样式
    swiperChange(e){
        let index=e.index
        console.log(e.index)
        for (let i = 0; i < this.tabList.length; i++) {
            this.tabList[i].selected=false;
        }
        this.tabList[index].selected=true;

    },
    onReady(){
        //根据实际的用户权限ID来请求用户可查看的菜单栏
        console.log("用户的权限"+user.roleId)
        staticApi.getMenu({ roleId: user.roleId.toString() }).then(res => {
            if (res.msg === '请求成功') {
                var data = res.data; // 假设res.data已经是正确解析的数组
                console.log("处理数据" + JSON.stringify(data));
                this.tabList = data.map((item, i) => {
                    return {
                        selected: i === 0, // 第一个默认选中
                        index: i,
                        name: item.name,
                        img: item.icon,
                        IMG: item.ico
                    };
                });

                console.log("表单赋值" + JSON.stringify(this.tabList));
            }
        }).catch(error => {
            console.error("获取菜单信息失败", error);
        });
    }


}
