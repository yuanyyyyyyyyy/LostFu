# Loser - 失物招领网站

## 项目介绍
Loser是一个基于SpringBoot和Vue.js开发的现代化失物招领网站系统。该系统旨在为用户提供一个便捷的平台，用于发布和查找失物招领信息。

## 技术架构
### 后端技术栈
- **基础框架**: Spring Boot 2.7.15
- **数据库**: MySQL
- **ORM框架**: MyBatis-Plus 2.1.9
- **数据库连接池**: Druid 1.1.9
- **权限认证**: Sa-Token 1.35.0.RC
- **JSON处理**: FastJSON 1.2.54, Gson 2.8.5
- **工具库**: Apache Commons Lang3
- **WebSocket**: Netty
- **其他**: Lombok, Spring AOP

### 前端技术栈
- Vue.js
- Element UI
- Axios
- WebSocket

## 功能特性
1. 用户管理
   - 用户注册/登录
   - 个人信息管理
   - 权限控制

2. 失物招领管理
   - 失物信息发布
   - 招领信息发布
   - 信息检索与浏览
   - 物品分类管理

3. 实时通讯
   - 基于WebSocket的即时消息通知
   - 用户在线交流

## 快速开始

### 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Node.js 12+

### 后端部署
1. 克隆项目到本地
```bash
git clone [项目地址]
```

2. 导入数据库
```bash
mysql -u username -p database_name < 11.sql
```

3. 修改数据库配置
修改 `application.properties` 或 `application.yml` 中的数据库连接信息

4. 启动项目
```bash
cd loser/springboot
mvn spring-boot:run
```

### 前端部署
1. 安装依赖
```bash
cd loser/mysys
npm install
```

2. 启动开发服务器
```bash
npm run serve
```
http://localhost:40001/
## 项目结构
```
loser/
├── springboot/          # 后端项目目录
│   ├── src/            # 源代码
│   ├── pom.xml        # Maven配置文件
│   └── target/        # 编译输出目录
├── mysys/              # 前端项目目录
├── chat/              # 即时通讯模块
└── 11.sql             # 数据库脚本
```

## 开发指南
1. 代码规范
   - 遵循阿里巴巴Java开发规范
   - 使用统一的代码格式化工具
   - 确保代码有适当的注释

2. 分支管理
   - master: 主分支，用于发布
   - develop: 开发分支
   - feature/*: 功能分支
   - hotfix/*: 紧急修复分支

## 参与贡献
1. Fork 本仓库
2. 新建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request


