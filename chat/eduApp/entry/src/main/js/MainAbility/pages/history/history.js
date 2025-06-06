import staticApi from '../../common/http/api'
import router from '@ohos.router'
import user from '../../common/http/user'
export default {
    data: {
        message:[
        //     {
        //         "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //         "date": "2024-06-10",
        //         "title": "小学英语应该怎么学"
        //     },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },
        //     {
        //         "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //         "date": "2024-06-10",
        //         "title": "小学英语应该怎么学"
        //     },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },{
        //     "sessionId": "244aac8a-3e82-45d0-a65f-58657b61800b",
        //     "date": "2024-06-10",
        //     "title": "小学英语应该怎么学"
        // },

        ]
    },
    onInit() {
        //TODO 需要修改为从缓存取用户的uid
        staticApi.getHistory({"uid":user.uid.toString()}).then(res=>{
            this.message=res.data
        })
    },
    ToPage(sessionId){
       router.pushUrl({
            url:'pages/hisChat/hisChat',
           params:{
               sessionId:sessionId
           }
       })
    }
}
