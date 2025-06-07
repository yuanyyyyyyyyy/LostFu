太棒了！你想进行 **Postman 黑盒测试**，并且希望从 **0 到 1 完整走一遍流程**。我会为你提供一个完整的测试流程，包括：

- 环境准备
- 登录接口调用（获取 Token）
- 带 Token 调用受保护的接口
- 测试管理员专属接口
- 测试异常情况（如 Token 失效、权限不足）

---

## 🧪 一、前提条件

### ✅ 已知信息（来自你的项目）：

| 项目       | 内容                            |
| ---------- | ------------------------------- |
| 后端地址   | `http://localhost:8088`         |
| 登录接口   | `/login`                        |
| Token 名称 | `satoken`                       |
| 用户名字段 | `username`                      |
| 密码字段   | `password`                      |
| 接口风格   | RESTful JSON 风格               |
| 使用框架   | Spring Boot + Sa-Token 权限系统 |

---

## 📦 二、工具准备

1. **安装 Postman**
   - 下载地址：[https://www.postman.com/downloads/](https://www.postman.com/downloads/)
2. **启动后端服务**
   - 运行你的 Spring Boot 项目，确保它监听在 `localhost:8088`
3. **准备好测试数据**
   - 至少有一个用户账号（如 `root` / `root`）

---

## 🔐 三、第一步：登录获取 Token

### 📍 接口说明：
- **URL**: `POST http://localhost:8088/login`
- **请求方式**: `POST`
- **请求头**: 不需要 Token
- **请求体**: JSON 格式用户名和密码

### ✅ 操作步骤：

1. 打开 Postman
2. 新建一个请求：
   - Method: `POST`
   - URL: `http://localhost:8088/login`
3. Body → raw → JSON:
```json
{
  "username": "root",
  "password": "root"
}
```

4. 点击 **Send**

### ✅ 成功响应示例：

```json
{
  "code": 2000,
  "message": "success",
  "data": [
    {
      "id": 114,
      "username": "root",
      "password": null,
      "tel": "",
      "crscore": 100,
      "auth": 1,
      "calmday": 0,
      "infoid": 3
    },
    {
      "tokenName": "satoken",
      "tokenValue": "31cb5547-5c50-41b3-ac49-9fc77c7f9c1d",
      "isLogin": true,
      "loginId": "114",
      ...
    }
  ]
}
```

> ⚠️ 请复制保存 `tokenValue` 字段值，后续接口会用到。

---

## 💾 四、第二步：使用环境变量保存 Token（推荐）

### 🎯 目标：让多个接口复用同一个 Token

#### 步骤：
1. 在 Postman 中点击右上角的 **Environment quick look**（眼睛图标）
2. 点击 **Edit** 创建一个新的环境（如 `Localhost Env`）
3. 添加变量：
   ```
   key: token
   value: 31cb5547-5c50-41b3-ac49-9fc77c7f9c1d
   ```

4. 点击 **Save**

#### 或者使用 Tests 自动提取 Token（进阶）：

在登录接口的 **Tests 标签页** 添加脚本自动提取并保存 Token：

```javascript
const jsonData = pm.response.json();
pm.environment.set("token", jsonData.data[1].tokenValue);
```

这样每次登录都会自动更新 Token。

---

## 📡 五、第三步：调用受保护的接口（带上 Token）

### 示例：获取当前用户信息
- **URL**: `GET http://localhost:8088/user/userInfo?id=114`
- **Headers**:
  ```
  satoken: {{token}}
  ```

> 使用 `{{token}}` 表示引用你刚才设置的环境变量

### ✅ 成功响应示例：

```json
{
  "code": 2000,
  "message": "success",
  "data": {
    "id": 114,
    "username": "root",
    "tel": "",
    "crscore": 100,
    "auth": 1,
    "calmday": 0,
    "infoid": 3
  }
}
```

---

## 👮 六、第四步：测试管理员专属接口

### 示例：查看所有普通用户列表
- **URL**: `GET http://localhost:8088/admin/allUserInfo`
- **Headers**:
  ```
  satoken: {{token}}
  ```

> 注意：只有 `auth >= 2` 的用户才能访问管理员接口，否则返回 `{"code": 4005, "message": "not login or token is null"}`

你可以先通过 `/user/wantAdmin?id=114` 提交申请成为管理员，然后由超级管理员审核。

---

## 🧹 七、第五步：测试 Token 失效或权限不足的情况

### 1. **Token 为空时访问受保护接口**
- 不带 Token 访问 `/admin/allUserInfo`，应该返回：
```json
{
  "code": 4005,
  "message": "not login or token is null"
}
```

### 2. **Token 错误或过期**
- 修改环境变量中的 Token，比如改成 `invalid-token`
- 再次访问 `/user/userInfo?id=114`，应该返回：
```json
{
  "code": 4005,
  "message": "not login or token is null"
}
```

### 3. **权限不足访问管理员接口**
- 使用 `auth == 1` 的用户访问 `/admin/allUserInfo`，应该返回：
```json
{
  "code": 401,
  "message": "无此权限"
}
```

---

## 📋 八、完整测试用例清单（建议你逐一测试）

| 接口             | 方法 | URL                               | 参数               | 是否需要登录 | 是否需要管理员权限 |
| ---------------- | ---- | --------------------------------- | ------------------ | ------------ | ------------------ |
| 登录             | POST | `/login`                          | username, password | ❌            | ❌                  |
| 获取用户信息     | GET  | `/user/userInfo?id=xxx`           | id                 | ✅            | ❌                  |
| 查看帖子列表     | GET  | `/user/seeList?infoid=1`          | infoid             | ❌            | ❌                  |
| 发布帖子         | POST | `/user/pullLose`                  | JSON body          | ✅            | ❌                  |
| 申请管理员       | GET  | `/user/wantAdmin?id=xxx`          | id                 | ✅            | ❌                  |
| 查看自己发的帖子 | GET  | `/user/selfPull?id=xxx`           | id                 | ✅            | ❌                  |
| 删除帖子         | GET  | `/user/deletePost?id=xxx`         | id                 | ✅            | ❌                  |
| 关键词搜索帖子   | GET  | `/user/keySearch?key=xxx`         | key                | ❌            | ❌                  |
| 查看所有用户     | GET  | `/admin/allUserInfo`              | 无                 | ✅            | ✅                  |
| 封禁用户         | GET  | `/admin/banuser?id=xxx&calmday=7` | id, calmday        | ✅            | ✅                  |
| 解除禁言         | GET  | `/admin/release?id=xxx`           | id                 | ✅            | ✅                  |
| 查看操作日志     | GET  | `/admin/seelog`                   | 无                 | ✅            | ❌                  |

---

## 🧪 九、Postman 测试流程图（推荐顺序）

1. `POST /login` → 获取 Token
2. 设置环境变量保存 Token
3. `GET /user/userInfo?id=114` → 查看用户信息
4. `GET /admin/allUserInfo` → 查看用户列表（需管理员权限）
5. `GET /user/seeList?infoid=1` → 查看帖子列表
6. `POST /user/pullLose` → 发布帖子
7. `GET /user/selfPull?id=114` → 查看自己发布的帖子
8. `GET /admin/banuser?id=100&calmday=7` → 封禁某用户（需管理员权限）
9. 清空 Token，再次访问 `/admin/allUserInfo` → 应返回未登录错误
10. 修改 Token，模拟 Token 失效 → 应返回未登录错误

---

## 🧩 十、Postman 测试技巧

### 1. **使用 Pre-request Script 设置动态参数**
例如在 `/user/deletePost` 请求中可以写：

```javascript
pm.request.url.addQueryParams([
    { key: 'id', value: pm.environment.get('postId') }
]);
```

### 2. **使用 Tests 自动校验结果**
在 `/user/userInfo` 请求中添加：

```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("User ID is correct", function () {
    pm.expect(pm.response.json().data.id).to.eql(114);
});
```

### 3. **创建 Collection 组织测试用例**
将上述接口整理成一个 Collection，方便批量运行、导出、分享。

---

## 📦 十一、前端开发建议（Vue / React）

你可以将上面的流程封装为 Axios 请求拦截器，实现自动附加 Token：

```javascript
import axios from 'axios';

// 创建实例
const api = axios.create({
    baseURL: 'http://localhost:8088',
    timeout: 5000,
});

// 请求拦截器
api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['satoken'] = token;
    }
    return config;
});

// 响应拦截器
api.interceptors.response.use(response => {
    return response.data;
}, error => {
    if (error.response?.status === 401) {
        alert('登录失效，请重新登录');
        window.location.href = '/login';
    }
    return Promise.reject(error);
});

export default api;
```

---

## 🧪 十二、常见问题及调试建议

| 问题                                                      | 原因                   | 解决方法                       |
| --------------------------------------------------------- | ---------------------- | ------------------------------ |
| `{"code": 4005, "message": "not login or token is null"}` | Token 未传或已失效     | 检查 Headers 和 Token 是否正确 |
| `404 Not Found`                                           | 接口路径错误           | 检查 URL 是否拼写错误          |
| `405 Method Not Allowed`                                  | 请求方式错误           | 改为 `GET` 或 `POST`           |
| `500 Internal Server Error`                               | 后端代码出错           | 查看后端日志排查               |
| Token 变量未生效                                          | Postman 环境变量未切换 | 检查是否选择了正确的环境       |

---

## 📝 十三、最终目标：生成自动化测试报告（可选）

如果你要生成接口测试报告，可以使用 Postman 的 **Runner** 功能：

1. 将所有接口加入一个 Collection
2. 点击 Runner
3. 选择环境、迭代次数等
4. Run 并查看测试结果

---

## 🏁 十四、总结

| 步骤     | 内容                             |
| -------- | -------------------------------- |
| ✅ 第一步 | 登录获取 Token                   |
| ✅ 第二步 | 使用 Postman 环境变量保存 Token  |
| ✅ 第三步 | 测试所有接口（带 Token）         |
| ✅ 第四步 | 测试 Token 失效、权限不足等情况  |
| ✅ 第五步 | 封装接口调用逻辑（前端开发）     |
| ✅ 第六步 | 使用 Runner 批量测试接口（高级） |

---

## 📬 最后，我需要你确认以下内容：

1. 你是否有 Swagger 或 Knife4j 接口文档？
2. 你使用的账号 `root` 是不是管理员？`auth == 2`？
3. 你是否愿意让我帮你：
   - 自动生成 Postman Collection 文件（JSON）
   - 生成 Vue / React 的统一请求封装
   - 实现 Token 刷新机制
   - 编写自动化测试脚本

只要你告诉我，我可以立刻为你生成对应文件或代码！

是否现在继续？✅