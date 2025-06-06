
const User = '/user'//这个是放在controller注解里
//下面的 例如/updateInfo,是放在@POSTMapping("/updateInfo")中的
const userInfo = User + "/userInfo"  //get请求，传参id，返回用户信息，除了密码
const updateINfo = User + '/updateInfo';//修改用户个人信息（post）   传参为  User实体类  后端用@RequestBody接受
const seeList = User + '/seeList'//返回帖子(get),需不需要分页查找？  前端传参 infoid根据infoid来判断返回帖子类型，拾取者就返回丢失物品帖子，管理员就是全都返回  后端用 @RequestParam("infoid")来接受
const pullLose = User + '/upload';//发表帖子 (post)      传参 为帖子实体类  后端用@RequestBody    接受
const wantAdmin = User + '/wantAdmin';//申请成为管理员 （post）  传参为  申请实体类   后端用@RequestBody 接受  
const selfPull = User + '/selfPull' //get请求，传参id 返回用户自己发布的帖子
const uploadurl = User + '/uploadurl'//上传图片地址  
const updatePull = User + '/updatePull';//更新帖子
const deltePull = User + '/deletePost';
const keySearch = User + '/keySearch'
//返回数据  使用 response类 
//  int  code//2000正确，4001错误请求
// String  message //成功就是success  , 失败就是   failure
//  Object   data  //比如get请求 返回数据就放在这，有些更新数据也要返回数据
export { userInfo, User, updateINfo, seeList, pullLose, wantAdmin, selfPull, uploadurl, updatePull, deltePull, keySearch }
