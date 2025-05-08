# 📱 RFID档案管理系统 - 前端部分

<div align="center">

![Vue](https://img.shields.io/badge/Vue-2.x-41b883)
![Element UI](https://img.shields.io/badge/Element_UI-2.x-409EFF)
![Vuex](https://img.shields.io/badge/Vuex-3.x-3eaf7c)
![Vue Router](https://img.shields.io/badge/Vue_Router-3.x-41b883)
![Axios](https://img.shields.io/badge/Axios-1.x-5A29E4)
![ECharts](https://img.shields.io/badge/ECharts-5.x-c23531)

<img src="https://user-images.githubusercontent.com/74038190/212257454-16e3712e-945a-4ca2-b238-408ad0bf87e6.gif" width="100">

</div>

## 📋 目录

- [项目概述](#-项目概述)
- [功能模块](#-功能模块)
- [技术栈](#-技术栈)
- [目录结构](#-目录结构)
- [环境要求](#-环境要求)
- [安装部署](#-安装部署)
- [环境变量配置](#-环境变量配置)
- [开发指南](#-开发指南)
- [与后端联调](#-与后端联调)
- [代码规范](#-代码规范)
- [常见问题](#-常见问题)
- [浏览器兼容性](#-浏览器兼容性)
- [联系方式](#-联系方式)
- [许可证](#-许可证)

## 🔍 项目概述

<details open>
<summary><b>系统简介</b></summary>

RFID档案管理系统前端基于Vue.js框架开发，采用了Element UI组件库，实现了一套完整的档案管理、借阅、统计的管理系统界面。本系统前端采用响应式设计，确保在不同设备上都能获得良好的用户体验。
</details>

## ✨ 功能模块

<details open>
<summary><b>核心功能</b></summary>

系统主要包含以下功能模块：

### 1. 用户管理
- 用户登录/登出
- 用户注册
- 用户信息管理
- 权限控制

### 2. 档案管理
- 档案信息录入
- 档案查询与搜索
- 档案分类管理
- 档案状态查看
- 档案位置显示

### 3. RFID设备交互
- 设备状态监控
- 读卡器数据接收
- 设备配置管理

### 4. 借阅管理
- 借阅申请
- 借阅审批
- 借阅历史查询
- 借阅到期提醒

### 5. 统计分析
- 档案使用频率统计
- 借阅趋势分析
- 数据可视化展示

### 6. 系统管理
- 系统参数配置
- 操作日志查看
- 数据备份与恢复
</details>

## 🚀 技术栈

<details open>
<summary><b>核心技术</b></summary>

- **前端框架**：Vue.js 2.x
- **UI组件库**：Element UI
- **状态管理**：Vuex
- **路由管理**：Vue Router
- **HTTP请求**：Axios
- **图表库**：ECharts
- **CSS预处理器**：Sass/SCSS
- **代码规范**：ESLint
- **构建工具**：Webpack
</details>

## 📁 目录结构

<details open>
<summary><b>项目结构</b></summary>

```
src/
├── api/                # API接口封装
│   ├── book.js         # 档案相关接口
│   ├── device.js       # 设备管理接口
│   ├── file_sharing.js # 文件共享接口
│   ├── sms_reminder.js # 提醒服务接口
│   └── user.js         # 用户相关接口
├── assets/             # 静态资源
├── components/         # 公共组件
├── icons/              # 图标资源
├── layout/             # 布局组件
├── router/             # 路由配置
├── store/              # Vuex状态管理
├── styles/             # 全局样式
├── utils/              # 工具函数
└── views/              # 页面视图
    ├── book/           # 档案管理
    ├── dashboard/      # 首页仪表盘
    ├── file_sharing/   # 文件共享
    ├── login/          # 登录页面
    ├── mi_history/     # 历史记录管理
    └── sms_reminder/   # 短信提醒服务
```
</details>

## 💻 环境要求

<details>
<summary><b>开发环境</b></summary>

- Node.js 12.x 或更高版本
- npm 6.x 或更高版本
- 现代浏览器 (Chrome, Firefox, Safari, Edge)
</details>

## 🔧 安装部署

<details>
<summary><b>开发环境</b></summary>

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

成功启动后，访问 http://localhost:9528
</details>

<details>
<summary><b>生产环境</b></summary>

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```

构建完成后，将生成的 `dist` 目录部署到Web服务器。
</details>

## ⚙️ 环境变量配置

<details>
<summary><b>配置文件</b></summary>

项目包含三个环境配置文件：

- `.env.development` - 开发环境配置
- `.env.staging` - 测试环境配置
- `.env.production` - 生产环境配置

根据需要修改这些文件中的配置参数，例如API基础URL等。
</details>

## 📝 开发指南

<details>
<summary><b>添加新页面</b></summary>

1. 在 `src/views` 目录下创建新的组件目录及文件
2. 在 `src/router` 中添加对应路由配置
3. 在左侧菜单中添加入口（如需要）
</details>

<details>
<summary><b>添加新API接口</b></summary>

1. 在 `src/api` 目录下创建或修改对应服务的文件
2. 使用 `request` 工具函数封装接口
3. 在组件中导入并使用
</details>

## 🔄 与后端联调

<details>
<summary><b>接口配置</b></summary>

前端默认通过环境变量中配置的 `VUE_APP_BASE_API` 与后端进行通信。确保在开发时后端服务已启动，且API路径配置正确。
</details>

## 📏 代码规范

<details>
<summary><b>代码检查</b></summary>

项目使用ESLint进行代码规范检查，遵循以下命令进行代码检查和修复：

```bash
# 代码规范检查
npm run lint

# 自动修复
npm run lint -- --fix
```
</details>

## ❓ 常见问题

<details>
<summary><b>常见错误及解决方案</b></summary>

1. **启动失败或依赖安装问题**
   - 尝试删除 `node_modules` 目录和 `package-lock.json` 文件，重新执行 `npm install`

2. **接口请求失败**
   - 检查网络连接
   - 确认后端服务是否正常运行
   - 验证API基础URL配置是否正确

3. **页面空白或报错**
   - 查看浏览器控制台错误信息
   - 检查对应组件的代码
</details>

## 🌐 浏览器兼容性

<details>
<summary><b>支持的浏览器</b></summary>

系统支持所有现代浏览器和IE11以上版本：

- Chrome
- Firefox
- Safari
- Edge
- IE 11+
</details>

## 📞 联系方式

<details>
<summary><b>联系信息</b></summary>

如有问题或建议，请联系：Encounter
</details>

## 📄 许可证

<details>
<summary><b>许可协议</b></summary>

[Apache-2.0 license](./LICENSE)
</details>

<div align="center">
<img src="https://user-images.githubusercontent.com/74038190/212284115-f47cd8ff-2ffb-4b04-b5bf-4d1c14c0247f.gif" width="100%">

**RFID档案管理系统 - 前端部分 | © 2025 Encounter. 保留所有权利**
</div>
