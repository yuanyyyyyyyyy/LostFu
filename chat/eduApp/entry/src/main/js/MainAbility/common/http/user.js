// User.js
class User {
        constructor(uid = -1, roleId = 0, username = '', satoken = '') {
                if (typeof User.instance === 'object') {
                        return User.instance;
                }
                this.uid = uid;
                this.roleId = roleId;
                this.username = username;
                this.satoken = satoken;
                User.instance = this;
        }

        // Getters
        getUid() {
                return this.uid;
        }

        getRoleId() {
                return this.roleId;
        }

        getUsername() {
                return this.username;
        }

        getSatoken() {
                return this.satoken;
        }

        // Setters
        setUid(uid) {
                this.uid = uid;
        }

        setRoleId(roleId) {
                this.roleId = roleId;
        }

        setUsername(username) {
                this.username = username;
        }

        setSatoken(satoken) {
                this.satoken = satoken;
        }

        // 一次性设置所有属性的方法
        set(data) {
                this.uid = data.uid;
                this.roleId = data.role;
                this.username = data.username;
                this.satoken = data.satoken;
        }
}

// 获取User单例
let user = new User(-1, 0, '', '');
export let file=[];
// 导出User单例
export default user;

