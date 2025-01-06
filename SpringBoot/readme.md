# RFID-Archive-Management-System

## 项目介绍

RFID-Archive-Management-System 是一个基于 Spring Boot 的项目，用于管理 RFID 档案系统。

## 技术栈

- Java 17
- Spring Boot 3.0.4
- Maven
- MySQL
- Redis

## 项目结构

- `src/main/java/com/encounter` - Java 源代码
- `src/main/resources` - 资源文件
- `src/test/java/com/encounter` - 测试代码

## 配置文件

`application.yml`

```yaml
server:
  port: 8080
spring:
  application:
    name: RFID-Archive-Management-System
  datasource:
    username: Encounter
    password: 20020628
    url: jdbc:mysql://127.0.0.1:3306/rams?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.jdbc.Driver
  data:
    redis:
      host: 127.0.0.1
      timeout: 50000
      port: 6379
      jedis:
        pool:
          max-active: 3
          max-idle: 3
          min-idle: 1
          max-wait: -1
```

## 运行项目

- 克隆项目到本地：
  `git clone <repository-url>`
- 进入项目目录：
  `cd RFID-Archive-Management-System`
- 使用 Maven 构建项目：
  `./mvnw clean install`
- 运行 Spring Boot 应用：
  `./mvnw spring-boot:run`
- 访问项目
  本地访问地址: http://localhost:8080
  外部访问地址: http://<your-ip-address>:8080

## 参考文档

[官方 Apache Maven 文档](https://maven.apache.org/guides/index.html)
[Spring Boot Maven 插件参考指南](https://docs.spring.io/spring-boot/3.3/maven-plugin/)
[创建 OCI 镜像](https://docs.spring.io/spring-boot/3.3/maven-plugin/build-image.html)
[Spring Web](https://docs.spring.io/spring-boot/3.3/reference/web/servlet.html)

## 指南

[使用 MySQL 访问数据](https://spring.io/guides/gs/accessing-data-mysql)
[构建 RESTful Web 服务](https://spring.io/guides/gs/rest-service)
[使用 Spring MVC 提供 Web 内容](https://spring.io/guides/gs/serving-web-content)
[使用 Spring 构建 REST 服务](https://spring.io/guides/tutorials/rest)

## 许可证

此项目使用 MIT 许可证 - 有关更多详细信息，请参阅 LICENSE 文件