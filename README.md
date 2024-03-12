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
