import request from './request.js'
import { seeWant, addAdmin, deleteAdmin, log, alladminInfo, against } from '../url/superadmin'
function seeWantf() {
    return request.get(seeWant);
}
function addAdminf(data) {
    return request.get(addAdmin, { params: { id: data.id } })
}
function deleteAdminf(data) {
    return request.get(deleteAdmin, { params: { id: data.id } })
}
function logf() {
    return request.get(log);
}
function alladminInfof() {
    return request.get(alladminInfo);
}
function againstf(data) {
    return request.get(against, { params: { username: data.username } })
}

export {
    seeWantf, addAdminf, deleteAdminf, logf, alladminInfof, againstf
}