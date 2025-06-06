const admin = '/admin';
const deleteBad = admin + '/deleteBad';//删除不良信息帖子'
const minScore = admin + '/minScore';//扣除信誉分
const adminUpdateInfo = admin + '/adminup';//更新用户信息
const shutUser = admin + '/banuser'//封禁用户
const allUserInfo = admin + '/allUserInfo' //返回所有普通用户/管理员的数据,传参Infoid根据他来判断返回数据
const release=admin+'/release'
export { admin, deleteBad, minScore, adminUpdateInfo, shutUser, allUserInfo ,release}