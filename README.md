# MicroBBS

轻量化Java社区

## 架构

* Site 前后端分离, 前端使用React构建, 后端提供Restful API
* Admin 采用AdminLTE, 模板引擎thymeleaf

## Stack

* (TODO) `React.js` 全家桶
* `Spring Boot` 全家桶
* `Mysql`

##　运行

    > gulp build
    > gradle clean bootRun

## 开发指导

    > compass watch #　监听css
    > gulp minify-css # 避免重复处理其他资源
    
## TODO

* [ ] TODO 数据为空的处理。返回引导页
* [ ] 各种badge数据计算
* [ ] 优化findByTags 和 findByCategoryId
* [ ] 分页
* [ ] posts 根据status过滤