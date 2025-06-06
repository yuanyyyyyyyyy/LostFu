const superAdmin = '/superAdmin';//放在@Restcontroller("")注解中
const seeWant = superAdmin + '/seeWant';//查看申请管理员列表  分页？ 暂时就算了（如果不麻烦就用分页查询）  
const addAdmin = superAdmin + '/adAdmin'//添加管理员  ，get请求   传参  id，后端以 @RequestParam("id") 接受   返回数据中data:null就不用添加
const deleteAdmin = superAdmin + '/deleteAdmin'  //删除管理员  传参id  后端以 @RequestParam("id") 接受   返回数据中data:null就不用添加
const alladminInfo='/admin'+'/alladminInfo'
const against=superAdmin+'/against'//驳回管理员申请
const log = superAdmin + '/seelog';
export {
    superAdmin,
    seeWant,
    addAdmin,
    deleteAdmin,
    alladminInfo,
    log,
    against
}