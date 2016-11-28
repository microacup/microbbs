# MicroBBS

轻量化Java社区。

关键词：

- Spring、SpringMVC、Thymeleaf
- Bootstrap、AdminLTE
- Java社区

## 架构

* Spring Boot 全家桶

* Site 前后端分离, 前端使用React构建, 后端提供Restful API

* Admin 采用AdminLTE(去掉了google fonts), 模板引擎thymeleaf

 

## 特性

- markdown 编辑器

- 支持在线或上传图片

- @用户

- 支持微博、Github等社交账号登录（推荐）

- 消息提醒

- 强大的后台管理

  ​

## Stack

* (TODO) `React.js` 全家桶 
* `Spring Boot` 全家桶
* `Mysql`

## 快速起步

- clone 源码

- cd source目录，执行`gulp build`编译静态文件

- 创建数据库`microbbs`，数据库表将自动创建

- 如有需要，修改application.yml中的服务器名和端口号等个性化信息

- `gradle clean bootRun` 运行

  
## 开发指导

    > compass watch #　监听css
    > gulp minify-css # 避免重复处理其他资源


## TODO

- [ ] 去掉凌乱的Vue，改为React全家桶

- [ ] 数据为空的处理。返回引导页

- [ ] 优化findByTags 和 findByCategoryId

- [ ] profile页

- [ ] 编辑器内容localStorage

- [ ] 表单重复提交

- [ ] 图床

- [ ] 权限支持热加载

- [ ] 权限支持黑名单模式



## 开源协议

GPL协议。


## 商用授权

包括但不限于如下场景，请联系作者：xiangmain@gmail.com

- 公司使用
- 盈利性质