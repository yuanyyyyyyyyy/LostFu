import http from '@ohos.net.http';
import user from  './user'
// 封装网络请求函数
async function request(url, method = http.RequestMethod.GET, extraData = null, contentType = 'application/json') {
    console.log('请求URL: ' + url); // 打印请求的URL
    if (extraData) {
        console.log('请求数据: ' + JSON.stringify(extraData)); // 打印请求的数据
    }


    // 设置请求头和请求数据(token设置)
    let headers = {};
    let formattedData = extraData;
    if (contentType === 'application/json') {
        headers =
        { 'Content-Type': 'application/json' ,
            'satoken':user.getSatoken()
        };
    } else if (contentType === 'application/x-www-form-urlencoded') {
        headers = {
            'Content-Type': 'application/x-www-form-urlencoded' ,
            'satoken':user.getSatoken()
        };
        formattedData = Object.keys(extraData).map(key => `${encodeURIComponent(key)}=${encodeURIComponent(extraData[key])}`).join('&');
    }
    console.log("请求头携带的token"+user.getSatoken())
    return new Promise((resolve, reject) => {
        let httpRequest = http.createHttp();
        console.log("create")
        // 订阅HTTP响应头
        httpRequest.on('headersReceive', (header) => {
            console.info('header: ' + JSON.stringify(header));
        });

        // 发起HTTP请求
        httpRequest.request(
            url,
            {
                method: method,
                header: headers,
                extraData: formattedData,
                expectDataType: http.HttpDataType.STRING,
                usingCache: true,
                priority: 1,
                connectTimeout: 60000,
                readTimeout: 60000,
                usingProtocol: http.HttpProtocol.HTTP1_1,
            },
            (err, data) => {
                    console.log('响应数据: ' + data.result); // 打印响应数据
                    if (!err) {
                    httpRequest.off('headersReceive');
                    httpRequest.destroy();
                    resolve(JSON.parse(data.result)); // 返回请求结果
                } else {
                    console.error('请求错误: ' + JSON.stringify(err)); // 打印请求错误
                    httpRequest.off('headersReceive');
                    httpRequest.destroy();
                    reject(err); // 返回错误
                }
            }
        );
    });
}

// 导出函数
export default request;
