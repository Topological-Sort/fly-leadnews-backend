## 仿今日头条项目V2.0：
（仅备注新增/改动内容，NoApp表示该模块为非启动模块）
### 1. heima-file-starter (NoApp)
### 2. heima-leadnews-common (NoApp)
### 3. heima-leadnews-feign-api (NoApp)
新增wemedia、user的跨服务调用，主要用于admin和behavior模块
### 4. heima-leadnews-gateway: 
网关层
  #### -| heima-leanews-admin-gateway: 
审核管理端网关
  #### -| heima-leanews-app-gateway: 
用户端网关
  #### -| heima-leadnews-wemedia: 
自媒体端网关
### 5. heima-leadnews-model (NoApp):
### 6. heima-leanews-service:
  #### -| heima-leanews-admin: 
内容审核管理端，包含用户管理、敏感词设置、人工审核文章等
  #### -| heima-leanews-article: 
新增文章用户行为（点赞、收藏等）回显；热度文章xxljob定时计算并缓存，同时修改文章推荐为优先获取缓存内容
  #### -| heima-leanews-behavior: 
用户行为记录模块，包括浏览、点赞、收藏等行为记录
  #### -| heima-leanews-schedule:
  #### -| heima-leanews-search:
  #### -| heima-leanews-user: 
新增用户关注功能（Redis/Mysql实现）
  #### -| heima-leanews-wemedia:
### 7. heima-leadnews-utils (NoApp):
### 8. heima-leadnews-test: 
新增xxljob测试

## 仿今日头条项目V1.0：
（NoApp表示非启动类）
### 1. heima-file-starter (NoApp): 
文件上传springboot starter
### 2. heima-leadnews-common (NoApp): 
公共内容，常量类，redis封装类，tess4j(用户图象提取文字)等
### 3. heima-leadnews-feign-api (NoApp): 
跨服务调用client和fallback
### 4. heima-leadnews-gateway: app网关层
  #### -| heima-leadnews-wemedia-gateway: 自媒体端网关层
### 5. heima-leadnews-model (NoApp): 
存放各微服务的dto、vo、entity类
### 6. heima-leanews-service: 存放各服务service层
  #### -| heima-leanews-user: 
app用户端service，主要涉及登录校验等功能
  #### -| heima-leanews-wemedia: 
自媒体端service，主要涉及文章提交、文章修改等功能
  #### -| heima-leanews-article: 
文章审核service，主要涉及文章内容审核，文章发布等功能
  #### -| heima-leanews-schedule: 
文章定时提交service，用于实现wemedia -> article的消息传输，用于实现文章定时提交功能
  #### -| heima-leanews-search: 
文章搜索service，主要涉及文章相关内容检索，关联词提示等功能
### 7. heima-leadnews-utils (NoApp): 
工具类，包括普通工具类(JWT、MD5等)和线程工具类(记录单次服务中的用户信息到线程空间)
### 8. heima-leadnews-test: 
单元测试内容
