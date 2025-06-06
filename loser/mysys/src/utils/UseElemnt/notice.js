import {
    Message,
    Notification

} from 'element-ui'
import "element-ui/lib/theme-chalk/index.css";

function notice(type, data) {
    Notification({
        type: type,
        message: data,
        duration: 4500
    })


}
function mess(data) {

    Message({
        message: data,
        type: 'success',
        duration: 2 * 1000
    })
}

export { notice, mess }
