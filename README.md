# BookSystem - 网上书店管理系统

## 项目概述

BookSystem 是一个基于 SpringBoot 框架开发的网上书店管理系统，实现了图书管理、用户管理、购物车和订单处理等功能。系统采用分层架构设计，包含数据持久层、业务逻辑层、控制层和视图层。

## 技术栈

- **后端框架**: SpringBoot
- **数据库**: MySQL
- **持久层**: JPA
- **前端技术**: HTML/CSS/JavaScript

## 系统功能

### 用户功能
- 用户注册与登录
- 个人信息查看与修改
- 图书浏览与搜索
- 购物车管理
- 订单管理

### 管理员功能
- 用户管理（添加、查看、删除）
- 图书管理（添加、编辑、删除）
- 查看所有购买记录

## 项目结构

```
booksystem/
├── pom.xml                     # Maven项目配置文件，包含项目依赖和构建配置
├── src                         # 源代码目录
│   ├── main                    # 主代码目录
│   │   ├── java                # Java源代码目录
│   │   │   └── com             # 基础包路径
│   │   │       └── book        # 项目主包
│   │   │           └── manager # 业务模块包
│   │   │               ├── config/      # Spring配置类(如安全配置、Web配置等)
│   │   │               ├── controller/  # Spring MVC控制器(处理HTTP请求)
│   │   │               ├── entity/      # 数据库实体类(与表对应的POJO)
│   │   │               ├── repository/  # 数据访问层(DAO接口)
│   │   │               ├── service/     # 业务逻辑接口定义
│   │   │               ├── serviceImpl/ # 业务逻辑实现类
│   │   │               └── BookManagerApplication.java # SpringBoot启动类
│   │   └── resources           # 资源文件目录
│   │       ├── static/         # 静态资源(JS/CSS/图片等)
│   │       ├── templates/      # 视图模板(Thymeleaf/FreeMarker等)
│   │       ├── application.yml # 应用配置文件(替代application.properties)
│   │       ├── mapper/         # MyBatis映射文件(XML格式)
│   │       └── upload/         # 文件上传存储目录(如用户头像)
│   └── test                    # 测试代码目录
│       ├── java                # 测试Java代码(单元测试/集成测试)
│       └── resources           # 测试资源文件(如测试数据库配置)
└── target                      # 构建输出目录(Maven自动生成)
    ├── classes                 # 编译后的类文件
    │   ├── META-INF/           # 元信息目录(包含MANIFEST.MF等)
    │   ├── application.yml     # 打包后的配置文件
    │   ├── com/                # 编译后的Java类文件
    │   ├── images/             # 打包后的图片资源
    │   ├── mapper/             # 打包后的MyBatis映射文件
    │   ├── static/             # 打包后的静态资源
    │   ├── templates/          # 打包后的视图模板
    │   └── upload/             # 打包后的上传目录(空目录)
    └── test-classes            # 编译后的测试类文件
```

## 数据库设计

系统使用三张主要表：

1. **users表** - 存储用户信息
2. **book表** - 存储图书信息
3. **buy表** - 记录购买行为(购物车和订单)

详细表结构请参考项目文档。

## 特色功能

1. **创新的buy表设计**：通过ret字段区分购物车和订单状态，减少表数量
2. **图书软删除**：通过size=-1标记删除图书，保留历史记录
3. **购物车超时机制**：15分钟未完成购买自动释放库存

