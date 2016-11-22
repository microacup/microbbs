# MicroBBS

轻量化Java社区

## 架构

* Site 前后端分离, 前端使用React构建, 后端提供Restful API
* Admin 采用AdminLTE(去掉了google fonts), 模板引擎thymeleaf

## Stack

* (TODO) `React.js` 全家桶 
* `Spring Boot` 全家桶
* `Mysql`

## 运行

    > gulp build
    > gradle clean bootRun

## 开发指导

    > compass watch #　监听css
    > gulp minify-css # 避免重复处理其他资源
    
        
## TODO

* [ ] 去掉凌乱的Vue，改为React全家桶
* [ ] 数据为空的处理。返回引导页
* [ ] 优化findByTags 和 findByCategoryId
* [ ] profile页
* [ ] 编辑器内容localStorage
* [ ] 表单重复提交
* [ ] 图床
