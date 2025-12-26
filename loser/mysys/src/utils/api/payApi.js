import request from '../api/request'
import { payCreateUrl } from '../url/pay'  // 变量名修改为 payCreateUrl

// 创建支付订单
export function payCreate(data) {
    return request.post(payCreateUrl, data)  // 此处使用修改后的变量名
}