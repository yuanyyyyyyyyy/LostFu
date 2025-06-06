import{login,register} from '../url/common'
import request from '../api/request'
function loginf(data){
    return request.post(login,data)
}
function registerf(data){
    return request.post(register,data)
}
export{loginf,registerf}