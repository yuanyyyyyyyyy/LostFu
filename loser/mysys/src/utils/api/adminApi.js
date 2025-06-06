import request from './request.js'
import { deleteBad, minScore, adminUpdateInfo, shutUser, allUserInfo, release } from '../url/admin'

function deleteBadf(data) { return request.get(deleteBad, data.id); }
function minScoref(data) { return request.get(minScore, { params: { username: data.username, score: data.score } }) }
function adminUpdateInfof(data) { return request.post(adminUpdateInfo, { data: { data } }) }
function shutUserf(data) { return request.get(shutUser, { params: { id: data.id, calmday: data.calmday } }) }
// function deleteAdminf(data){return request.get(deleteAdmin,{params:{id:data.id}})}
function allUserInfof() { return request.get(allUserInfo) }
function releasef(data) { return request.get(release, { params: { id: data.id } }) }
export { deleteBadf, minScoref, adminUpdateInfof, shutUserf, allUserInfof, releasef }