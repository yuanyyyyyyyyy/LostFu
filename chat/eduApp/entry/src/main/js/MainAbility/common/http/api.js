// staticApi.js

import request from './request';
import http from '@ohos.net.http';
import {BASEURL} from '../http/url'

const staticApi = {
    // 获取本地新闻
    getNews() {
        // 确保返回request()的结果
        return request(BASEURL+"text/resource.json", http.RequestMethod.GET, {});
    },
    //获取云端新闻
    getYunNews(data){
        console.log("传输的数据:"+data)
        return request("http://v.juhe.cn/toutiao/index", http.RequestMethod.POST, data,'application/x-www-form-urlencoded');
    },
    //登录
    login(data){
        return request(BASEURL+"user/login",http.RequestMethod.POST,data)
    },
    //获取邮箱验证码
    sendCode(data){
      return request(BASEURL+"user/sendCode",http.RequestMethod.GET,data)
    },
    //使用验证码登录
    UseCode(data){
        return request(BASEURL+'user/verifyCode',http.RequestMethod.GET,data)
    },
    //获取历史数据
    getHistory(data){
        return  request(BASEURL+'history/HistoryList',http.RequestMethod.GET,data)
    },
    //获取某次详细的聊天记录
    getDetail(data){
        return  request(BASEURL+'history/HistoryDetail',http.RequestMethod.GET,data)
    }
    ,
    //发送问题
    sendMsg(data){
        return  request(BASEURL+'chat'+'/sendQuestion',http.RequestMethod.GET,data)
    },
    //在历史记录中发送问题
    sendHisMsg(data){
        return  request(BASEURL+'chat'+'/hisQuestion',http.RequestMethod.GET,data)
    },
    //获取用户菜单栏
    getMenu(data){
        return  request(BASEURL+'menu'+'/MenuList',http.RequestMethod.GET,data)
    },
    //停止聊天
    stopChat(data){
        return  request(BASEURL+'chat'+'/stopChat',http.RequestMethod.GET,data)
    },
    //注册
    register(data){
        return request(BASEURL+"user/register",http.RequestMethod.POST,data)
    },
    //修改信息
    changeinfo(data){
        return request(BASEURL+"user/updateuser",http.RequestMethod.POST,data)
    },
    //作业批改
    photoReco(data){
        return request(BASEURL+"photoReco",http.RequestMethod.GET,data)
    },
    //文字识别
    recognize(data){
        return request(BASEURL+"recognize",http.RequestMethod.GET,data)
    },
    //图片转文档
    imgtoword(data){
        return request(BASEURL+"image-to-word",http.RequestMethod.GET,data)
    },
    //图片转pdf
    imgtopdf(data){
        return request(BASEURL+"image-to-pdf",http.RequestMethod.GET,data)
    },
    // word转pdf
    wordtopdf(data){
        return request(BASEURL+"word-to-pdf",http.RequestMethod.GET,data)
    },
    //pdf转word
    pdftoword(data){
        return request(BASEURL+"pdf-to-word",http.RequestMethod.GET,data)
    },
    //获取个人信息
    getInfo(data){
        return request(BASEURL+"user/userInfo",http.RequestMethod.GET,data)
    }
,
    getformname(data){
        return request(BASEURL+"getformnames",http.RequestMethod.GET,data)
    }


};
export default staticApi;
