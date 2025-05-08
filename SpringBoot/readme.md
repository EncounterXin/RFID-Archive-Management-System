# ⚙️ RFID档案管理系统 - 后端服务

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange)
![SpringBoot](https://img.shields.io/badge/SpringBoot-3.0.4-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1)
![Redis](https://img.shields.io/badge/Redis-6.x-DC382D)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.0.x-6DB33F)
![MyBatis](https://img.shields.io/badge/MyBatis-3.0.x-blue)

<img src="https://user-images.githubusercontent.com/74038190/212747903-e9bdf048-2dc8-41f9-b973-0e72ff07bfba.gif" width="120">

</div>

## 📋 目录

- [项目概述](#-项目概述)
- [系统架构](#-系统架构)
- [核心功能](#-核心功能)
- [技术栈详解](#-技术栈详解)
- [项目结构](#-项目结构)
- [API接口文档](#-api接口文档)
- [数据库设计](#-数据库设计)
- [配置说明](#-配置说明)
- [部署指南](#-部署指南)
- [开发规范](#-开发规范)
- [测试指南](#-测试指南)
- [版本历史](#-版本历史)
- [常见问题](#-常见问题)
- [参考文档](#-参考文档)
- [许可证](#-许可证)

## 📌 项目概述

<details open>
<summary><b>业务背景</b></summary>

RFID-Archive-Management-System 是一个基于 Spring Boot
的项目，专为档案管理系统设计，集成了RFID技术实现档案的智能化管理。本系统旨在提高档案管理效率，减少人为错误，提供实时监控和全面的数据分析功能。

### 业务背景

传统档案管理系统面临以下挑战：

- 人工查找效率低下
- 档案借阅归还流程复杂
- 档案状态难以实时追踪
- 统计分析能力有限
- 安全管控措施不足

本系统通过RFID技术，结合现代化的软件架构，实现了档案的全生命周期管理，从入库、上架、借阅、归还到盘点全过程实现自动化和智能化。
</details>

<details>
<summary><b>系统目标</b></summary>

- 📍 实现档案的精确定位和快速查找
- 🔄 优化档案借阅归还流程
- 📊 提供实时的档案状态监控
- 📈 强化统计分析能力
- 🔒 提升系统安全性和可靠性
- 👥 支持多用户、多角色的权限管理

</details>

## 系统架构

### 整体架构

本系统采用前后端分离的三层架构设计：

1. **表现层**：基于Vue.js的前端界面
2. **业务逻辑层**：Spring Boot后端服务
3. **数据持久层**：MySQL数据库 + Redis缓存

同时，系统还集成了Nacos用于服务注册与配置管理，实现微服务架构的基础。

### 技术架构图

```
+-------------------+
|    客户端层       |
|  (Browser/App)    |
+--------+----------+
         |
         v
+-------------------+
|     前端层        |
|     (Vue.js)      |
+--------+----------+
         |
         v
+-------------------+       +-------------------+
|   Spring Cloud    |       |   Nacos           |
|   Gateway         +------>+   服务注册/配置   |
+--------+----------+       +-------------------+
         |
         v
+-------------------+
|   业务服务层      |
|  (Spring Boot)    |
+--------+----------+
         |
         v
+-------------------+       +-------------------+
|   数据访问层      |       |     Redis         |
|   (MyBatis)       +------>+     缓存          |
+--------+----------+       +-------------------+
         |
         v
+-------------------+
|   数据存储层      |
|    (MySQL)        |
+-------------------+
```

### 核心模块

- **用户管理模块**：负责用户认证、授权和权限管理
- **档案管理模块**：管理档案的基本信息、位置和状态
- **RFID交互模块**：处理与RFID硬件的通信
- **借阅管理模块**：处理档案的借出、归还和预约
- **统计分析模块**：提供数据分析和报表功能
- **系统管理模块**：负责系统配置、日志和监控

### 数据流转过程

1. RFID读取器识别档案标签信息
2. STM32处理器接收原始数据并进行初步处理
3. 后端服务接收处理后的数据
4. 后端服务进行业务逻辑处理
5. 数据持久化到MySQL数据库
6. 关键数据缓存到Redis提高访问速度
7. 前端展示处理结果

## 核心功能

### 用户管理

- **用户认证**：基于JWT的安全认证机制
- **角色管理**：支持多角色（管理员、档案管理员、普通用户等）
- **权限控制**：细粒度的功能和数据权限控制
- **操作日志**：记录用户关键操作，支持审计追踪

### 档案管理

- **档案入库**：支持批量导入档案信息，自动生成档案编号
- **档案编目**：支持自定义档案类别、属性和标签
- **档案定位**：实时显示档案位置信息
- **档案状态**：显示档案当前状态（在库、借出、预约等）
- **档案搜索**：支持多维度、模糊查询和高级搜索

### RFID交互

- **设备管理**：管理RFID读取器设备信息和状态
- **数据采集**：实时接收RFID标签数据
- **数据解析**：解析RFID原始数据，提取有效信息
- **异常处理**：处理通信中断、数据异常等情况

### 借阅管理

- **借阅申请**：在线提交借阅申请
- **借阅审批**：多级审批流程，支持自动化审批
- **借阅记录**：记录借阅历史，支持查询和统计
- **到期提醒**：自动提醒即将到期的借阅记录
- **归还确认**：RFID自动识别归还档案

### 统计分析

- **实时统计**：档案总数、借出数量、预约数量等
- **趋势分析**：档案借阅趋势、热门档案分析
- **用户分析**：用户活跃度、借阅偏好分析
- **报表导出**：支持多种格式的报表导出（PDF、Excel等）

## 技术栈详解

### 核心框架

- **Java 17**：利用最新Java特性提升开发效率和性能
- **Spring Boot 3.0.4**：简化应用配置和部署
- **Spring MVC**：提供RESTful API支持
- **Spring Security**：实现安全认证和授权
- **Spring Data Redis**：集成Redis缓存提高性能
- **Spring Cloud**：微服务架构支持
- **Nacos**：服务注册与配置中心

### 数据层

- **MySQL 8.x**：可靠的关系型数据库
- **MyBatis**：灵活的ORM框架
- **Druid**：高效的数据库连接池
- **PageHelper**：简化分页查询
- **Redis**：高性能缓存，支持会话共享

### 工具库

- **Lombok**：减少样板代码
- **Fastjson**：高效的JSON处理
- **Commons-lang3**：常用工具类
- **Aliyun OSS**：云存储服务
- **Quartz**：任务调度

## 项目结构

### 目录结构

```
src/main/java/com/encounter/
├── config/              # 配置类
│   ├── CorsConfig.java  # 跨域配置
│   ├── RedisConfig.java # Redis配置
│   └── SecurityConfig.java # 安全配置
├── controller/          # 控制器
│   ├── UserController.java     # 用户管理
│   ├── ArchiveController.java  # 档案管理
│   ├── RfidController.java     # RFID管理
│   └── BorrowController.java   # 借阅管理
├── service/             # 服务层
│   ├── impl/            # 服务实现
│   ├── UserService.java      # 用户服务接口
│   ├── ArchiveService.java   # 档案服务接口
│   ├── RfidService.java      # RFID服务接口
│   └── BorrowService.java    # 借阅服务接口
├── mapper/              # MyBatis映射器
├── model/               # 数据模型
│   ├── entity/          # 实体类
│   ├── dto/             # 数据传输对象
│   ├── vo/              # 视图对象
│   └── param/           # 请求参数对象
├── utils/               # 工具类
│   ├── JwtUtil.java     # JWT工具
│   ├── RedisUtil.java   # Redis工具
│   └── RfidUtil.java    # RFID工具
├── exception/           # 异常处理
│   ├── GlobalExceptionHandler.java # 全局异常处理
│   └── BusinessException.java      # 业务异常
├── interceptor/         # 拦截器
├── task/                # 定时任务
└── RAMSApplication.java # 启动类

src/main/resources/
├── application.yml      # 应用配置
├── application-dev.yml  # 开发环境配置
├── application-prod.yml # 生产环境配置
├── bootstrap.yml        # 引导配置
├── logback.xml          # 日志配置
└── mapper/              # MyBatis XML映射文件
```

### 关键类说明

- **RAMSApplication.java**：项目入口类，包含主方法和全局配置
- **CorsConfig.java**：跨域配置，允许前端正常访问API
- **SecurityConfig.java**：安全配置，管理认证和授权
- **JwtUtil.java**：处理JWT生成、验证和解析
- **GlobalExceptionHandler.java**：全局异常处理，统一API响应格式

## API接口文档

### 用户管理API

| 接口名称   | URL                        | 方法   | 说明         |
|--------|----------------------------|------|------------|
| 用户登录   | `/api/user/login`          | POST | 用户登录认证     |
| 用户注册   | `/api/user/register`       | POST | 新用户注册      |
| 获取用户信息 | `/api/user/info`           | GET  | 获取当前用户信息   |
| 修改用户信息 | `/api/user/update`         | PUT  | 更新用户信息     |
| 用户列表   | `/api/user/list`           | GET  | 获取用户列表(分页) |
| 重置密码   | `/api/user/reset-password` | POST | 重置用户密码     |

### 档案管理API

| 接口名称 | URL                          | 方法     | 说明         |
|------|------------------------------|--------|------------|
| 档案列表 | `/api/archive/list`          | GET    | 获取档案列表(分页) |
| 档案详情 | `/api/archive/{id}`          | GET    | 获取档案详情     |
| 添加档案 | `/api/archive/add`           | POST   | 添加新档案      |
| 更新档案 | `/api/archive/update`        | PUT    | 更新档案信息     |
| 删除档案 | `/api/archive/delete/{id}`   | DELETE | 删除档案       |
| 档案搜索 | `/api/archive/search`        | POST   | 多条件搜索档案    |
| 档案位置 | `/api/archive/location/{id}` | GET    | 获取档案位置     |

### RFID管理API

| 接口名称 | URL                            | 方法   | 说明          |
|------|--------------------------------|------|-------------|
| 设备列表 | `/api/rfid/devices`            | GET  | 获取RFID设备列表  |
| 设备状态 | `/api/rfid/device/{id}/status` | GET  | 获取设备状态      |
| 数据接收 | `/api/rfid/data`               | POST | 接收RFID数据    |
| 标签绑定 | `/api/rfid/bind`               | POST | 绑定RFID标签与档案 |
| 标签解绑 | `/api/rfid/unbind/{id}`        | POST | 解绑RFID标签    |

### 借阅管理API

| 接口名称 | URL                        | 方法   | 说明         |
|------|----------------------------|------|------------|
| 借阅申请 | `/api/borrow/apply`        | POST | 提交借阅申请     |
| 借阅审批 | `/api/borrow/approve/{id}` | POST | 审批借阅申请     |
| 借阅拒绝 | `/api/borrow/reject/{id}`  | POST | 拒绝借阅申请     |
| 借阅归还 | `/api/borrow/return/{id}`  | POST | 归还借阅档案     |
| 借阅记录 | `/api/borrow/records`      | GET  | 获取借阅记录(分页) |
| 借阅详情 | `/api/borrow/{id}`         | GET  | 获取借阅详情     |

### 统计分析API

| 接口名称  | URL                               | 方法   | 说明        |
|-------|-----------------------------------|------|-----------|
| 基础统计  | `/api/stats/basic`                | GET  | 获取基础统计数据  |
| 借阅趋势  | `/api/stats/borrow-trend`         | GET  | 获取借阅趋势数据  |
| 档案分布  | `/api/stats/archive-distribution` | GET  | 获取档案分布数据  |
| 用户活跃度 | `/api/stats/user-activity`        | GET  | 获取用户活跃度数据 |
| 导出报表  | `/api/stats/export`               | POST | 导出统计报表    |

### 通用响应格式

```json
{
  "code": 200,
  // 状态码：200成功，非200失败
  "message": "success",
  // 响应消息
  "data": {}
  // 响应数据
}
```

### 错误码说明

| 错误码 | 说明       |
|-----|----------|
| 200 | 成功       |
| 400 | 请求参数错误   |
| 401 | 未授权或登录过期 |
| 403 | 权限不足     |
| 404 | 资源不存在    |
| 500 | 服务器内部错误  |

## 数据库设计

### 数据库架构

本系统使用MySQL数据库，主要包含以下核心表：

### 核心表结构

**用户表(user)**

```sql
CREATE TABLE `user`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`        varchar(64)  NOT NULL COMMENT '用户名',
    `password`        varchar(128) NOT NULL COMMENT '密码(加密)',
    `real_name`       varchar(64)           DEFAULT NULL COMMENT '真实姓名',
    `phone`           varchar(20)           DEFAULT NULL COMMENT '手机号',
    `email`           varchar(64)           DEFAULT NULL COMMENT '邮箱',
    `avatar`          varchar(255)          DEFAULT NULL COMMENT '头像URL',
    `role_id`         bigint                DEFAULT NULL COMMENT '角色ID',
    `department`      varchar(64)           DEFAULT NULL COMMENT '部门',
    `status`          tinyint      NOT NULL DEFAULT '1' COMMENT '状态(0禁用,1启用)',
    `last_login_time` datetime              DEFAULT NULL COMMENT '最后登录时间',
    `create_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';
```

**角色表(role)**

```sql
CREATE TABLE `role`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`        varchar(64) NOT NULL COMMENT '角色名称',
    `description` varchar(128)         DEFAULT NULL COMMENT '角色描述',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';
```

**档案表(archive)**

```sql
CREATE TABLE `archive`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '档案ID',
    `archive_no`     varchar(32)  NOT NULL COMMENT '档案编号',
    `title`          varchar(128) NOT NULL COMMENT '档案标题',
    `category_id`    bigint       NOT NULL COMMENT '类别ID',
    `description`    text COMMENT '描述',
    `location`       varchar(64)           DEFAULT NULL COMMENT '档案位置',
    `status`         tinyint      NOT NULL DEFAULT '1' COMMENT '状态(1在库,2借出,3丢失)',
    `rfid_tag`       varchar(64)           DEFAULT NULL COMMENT 'RFID标签',
    `create_user_id` bigint                DEFAULT NULL COMMENT '创建用户ID',
    `create_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_archive_no` (`archive_no`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_rfid_tag` (`rfid_tag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='档案表';
```

**借阅记录表(borrow_record)**

```sql
CREATE TABLE `borrow_record`
(
    `id`                 bigint   NOT NULL AUTO_INCREMENT COMMENT '借阅ID',
    `user_id`            bigint   NOT NULL COMMENT '借阅用户ID',
    `archive_id`         bigint   NOT NULL COMMENT '档案ID',
    `borrow_time`        datetime NOT NULL COMMENT '借阅时间',
    `plan_return_time`   datetime NOT NULL COMMENT '计划归还时间',
    `actual_return_time` datetime          DEFAULT NULL COMMENT '实际归还时间',
    `status`             tinyint  NOT NULL DEFAULT '1' COMMENT '状态(1申请中,2已借出,3已归还,4已拒绝)',
    `approver_id`        bigint            DEFAULT NULL COMMENT '审批人ID',
    `approve_time`       datetime          DEFAULT NULL COMMENT '审批时间',
    `remark`             varchar(255)      DEFAULT NULL COMMENT '备注',
    `create_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_archive_id` (`archive_id`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='借阅记录表';
```

**RFID设备表(rfid_device)**

```sql
CREATE TABLE `rfid_device`
(
    `id`               bigint      NOT NULL AUTO_INCREMENT COMMENT '设备ID',
    `device_no`        varchar(64) NOT NULL COMMENT '设备编号',
    `device_name`      varchar(64) NOT NULL COMMENT '设备名称',
    `device_type`      tinyint     NOT NULL COMMENT '设备类型',
    `location`         varchar(64)          DEFAULT NULL COMMENT '设备位置',
    `ip_address`       varchar(32)          DEFAULT NULL COMMENT 'IP地址',
    `port`             int                  DEFAULT NULL COMMENT '端口号',
    `status`           tinyint     NOT NULL DEFAULT '1' COMMENT '状态(0离线,1在线)',
    `last_active_time` datetime             DEFAULT NULL COMMENT '最后活跃时间',
    `create_time`      datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_device_no` (`device_no`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='RFID设备表';
```

### ER图

```
┌─────────┐      ┌─────────┐      ┌──────────┐
│  User   │      │  Role   │      │ Archive  │
├─────────┤      ├─────────┤      ├──────────┤
│ id      │──┐   │ id      │      │ id       │
│ username│  │   │ name    │      │ title    │
│ password│  │   │ desc    │      │ category │
│ ...     │  │   └─────────┘      │ rfid_tag │◄────┐
└─────────┘  │                     └──────────┘     │
             │                          ▲           │
             │                          │           │
             │   ┌──────────────┐       │           │
             └──►│ BorrowRecord │───────┘           │
                 ├──────────────┤                   │
                 │ id           │                   │
                 │ user_id      │                   │
                 │ archive_id   │                   │
                 │ status       │                   │
                 └──────────────┘                   │
                                                    │
                 ┌──────────────┐                   │
                 │ RfidDevice   │                   │
                 ├──────────────┤                   │
                 │ id           │                   │
                 │ device_no    │                   │
                 │ type         │                   │
                 │ status       │───────────────────┘
                 └──────────────┘
```

## 配置说明

### 主配置文件 (application.yml)

```yaml
server:
  port: 9999 # 服务端口
  servlet:
    context-path: /api # API前缀

spring:
  application:
    name: RFID-Archive-Management-System
  profiles:
    active: dev # 默认使用开发环境
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/rams?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: Encounter
    password: 20020628
    druid:
      initial-size: 5
      min-idle: 5
      max-active: 20
      max-wait: 60000
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      validation-query: SELECT 1
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: true
      max-pool-prepared-statement-per-connection-size: 20
      filters: stat,wall
  data:
    redis:
      host: 127.0.0.1
      port: 6379
      timeout: 50000
      jedis:
        pool:
          max-active: 3
          max-idle: 3
          min-idle: 1
          max-wait: -1

mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  type-aliases-package: com.encounter.model.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

pagehelper:
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
  params: count=countSql

# JWT配置
jwt:
  secret: your-secret-key-encounter-rfid-archive-system
  expiration: 86400000 # 24小时
  header: Authorization

# 日志配置
logging:
  level:
    com.encounter: debug
    org.springframework: warn
  file:
    name: logs/rams.log
    max-size: 10MB
    max-history: 30

# RFID设备配置
rfid:
  connect-timeout: 5000
  read-timeout: 10000
  heartbeat-interval: 60000
```

### 多环境配置

**开发环境 (application-dev.yml)**

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/rams_dev?useSSL=false&useUnicode=true&characterEncoding=UTF-8
  redis:
    host: localhost
```

**生产环境 (application-prod.yml)**

```yaml
spring:
  datasource:
    url: jdbc:mysql://prod-db-server:3306/rams_prod?useSSL=false&useUnicode=true&characterEncoding=UTF-8
  redis:
    host: prod-redis-server
```

## 部署指南

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Nacos 2.0+ (可选)

### 构建项目

```bash
# 克隆项目
git clone https://github.com/EncounterXin/RFID-Archive-Management-System.git

# 进入SpringBoot目录
cd RFID-Archive-Management-System/SpringBoot

# Maven打包
mvn clean package -DskipTests
```

### 运行方式

#### 方式一：直接运行

```bash
java -jar target/RFID-Archive-Management-System-0.0.1-SNAPSHOT.jar
```

#### 方式二：使用脚本启动

**Windows (start.bat)**

```batch
@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
java -Xms512m -Xmx1024m -jar RFID-Archive-Management-System-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

**Linux (start.sh)**

```bash
#!/bin/bash
export JAVA_HOME=/usr/local/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
nohup java -Xms512m -Xmx1024m -jar RFID-Archive-Management-System-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > /dev/null 2>&1 &
echo $! > pid.file
```

#### 方式三：使用Docker部署

**Dockerfile**

```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
ADD target/RFID-Archive-Management-System-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```

**构建并运行**

```bash
# 构建镜像
docker build -t rams:latest .

# 运行容器
docker run -d -p 9999:9999 --name rams rams:latest
```

## 开发规范

### 代码规范

- **命名规范**：
    - 类名：大驼峰命名
    - 方法名、变量名：小驼峰命名
    - 常量：全大写，下划线分隔
    - 包名：全小写

- **注释规范**：
    - 类注释：说明类的功能、作者和版本
    - 方法注释：说明方法的功能、参数和返回值
    - 关键代码注释：说明复杂逻辑的实现

- **异常处理**：
    - 使用自定义异常类表达业务异常
    - 全局异常处理统一响应格式
    - 记录异常日志，包含足够上下文信息

### 数据库规范

- **命名规范**：
    - 表名：小写，下划线分隔
    - 字段名：小写，下划线分隔
    - 主键：id
    - 外键：关联表名_id

- **字段规范**：
    - 每张表必须包含id, create_time, update_time
    - 使用tinyint表示状态字段
    - 字符编码统一使用utf8mb4

## 测试指南

### 单元测试

使用JUnit5和Mockito进行单元测试，重点测试服务层和工具类。

```java

@SpringBootTest
public class UserServiceTest
    {
        @Autowired
        private UserService userService;
        
        @Test
        public void testLogin()
            {
                // 测试代码
            }
    }
```

### 接口测试

使用Postman或Swagger进行API接口测试。

**Swagger访问地址**：http://localhost:9999/api/swagger-ui/index.html

## 版本历史

- **v0.1.0** (2023-05-01)
    - 初始版本，基本功能实现

- **v0.2.0** (2023-06-15)
    - 添加RFID交互模块
    - 优化档案管理流程

- **v0.3.0** (2023-08-10)
    - 添加统计分析功能
    - 增强安全性，加入JWT认证

## 常见问题

1. **如何修改数据库连接配置？**
    - 修改application.yml或对应环境的配置文件

2. **如何调整JVM内存参数？**
    - 在启动命令中添加-Xms和-Xmx参数

3. **如何增加新的API接口？**
    - 在相应的Controller中添加新方法
    - 实现Service层逻辑
    - 更新API文档

4. **如何处理跨域问题？**
    - 已在CorsConfig中配置，可根据需要修改

5. **如何查看应用日志？**
    - 开发环境：控制台输出
    - 生产环境：查看logs/rams.log文件

## 参考文档

- [Spring Boot官方文档](https://docs.spring.io/spring-boot/docs/3.0.4/reference/html/)
- [MyBatis官方文档](https://mybatis.org/mybatis-3/)
- [Spring Security官方文档](https://docs.spring.io/spring-security/reference/index.html)
- [Redis官方文档](https://redis.io/docs/)
- [JWT介绍](https://jwt.io/introduction)
- [Nacos官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)

## 许可证

此项目使用 [Apache-2.0 license](https://github.com/EncounterXin/RFID-Archive-Management-System?tab=Apache-2.0-1-ov-file#)
许可证。
有关详细信息，请参阅LICENSE文件。

---

© 2025 Encounter. 保留所有权利。