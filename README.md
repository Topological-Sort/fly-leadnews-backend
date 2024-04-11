## 各微服务关系图
![飞翔头条](https://github.com/Topological-Sort/fly-leadnews-backend/assets/115573611/674d40a8-334c-41e7-9815-f386e1943357)

## 各微服务主要功能说明：(NoApp表示非启动模块)
#### file-starter (NoApp): 【MinIO对象存储工具启动类】
#### common (NoApp): 【存放常量类、工具类、swagger配置等】
#### feign-api (NoApp): 【定义feign跨服务调用接口】
#### model (NoApp): 【存放dto、entity、vo类】
#### utils (NoApp): 【存放各种工具类】
#### test (NoApp): 【各类型测试类】 <br> 
### gateway: 【三端微服务网关】
#### -| app-gateway: 【用户端网关】
#### -| wemedia-gateway: 【自媒体端网关】
#### -| admin-gateway: 【后台管理端网关】
三个网关的作用基本相同，只是每个网关对应不同端 <br> 
  1. **请求转发**：将前端统一端口的请求转发到对应的不同端口的微服务 <br> 
  2. **GlobalFilter**： <br> 
    (1) 校验token，每个前端请求都必须携带token头部，token校验通过后才能转发到对应微服务接口，该token经过特定字符串和加密算法加密保障安全性 <br> 
    (2) 添加用户/自媒体人/管理员信息到请求头部，再转发请求到相应微服务 <br> 
### service: 【微服务进程】
### （以下为用户端）
### -| user: 【用户管理】
  0. **拦截器**：拦截从网关转发来的请求，从该请求头获取到用户信息（主要是用户id） <br> 
  1. **用户登录**：登录后，签发加密 token 到前端 localStorage，与网关层 GlobalFilter 进行协同工作 <br> 
  2. **用户关注**：使用 Redis-ZSET 实现，可以方便地按关注时间降序排列，获取用户共同关注 <br> 
  3. **用户管理**（用户管理写成了feign，由后台管理端调用。但其实可以直接写在该模块，由前端在**后台管理端**调用） <br> 
### -| article: 【文章管理】
  1. **文章加载**：分页加载、推荐页热门文章加载 <br> 
  2. **文章上下架(Kafka: wemedia --> article)**：在**自媒体(wemedia)微服务**，自媒体人点击文章下架后，发送消息通知article微服务修改该文章状态为已下线 <br> 
  3. **文章存储(feign: wemedia --> article)**：在**自媒体(wemedia)微服务**，新闻审核通过后，存储成文章 --> 通过Freemarker生成页面 --> 存在MinIO中供用户浏览 （同时使用 Kafka 发送消息到**文章搜索(search)微服务**，使 ES 生成索引供文章搜索） <br> 
### -| search: 【文章搜索】
  1. **文章搜索**：使用 ES ，基于文章标题、内容中的关键词索引搜索文章 <br> 
  2. **搜索联想词**：使用 MongoDB 存储搜索联想词，用户搜索时模糊匹配查找联想词 <br> 
  3. **搜索历史记录**：使用 MongoDB 存储、删除用户搜索历史记录 <br> 
### -| behavior: 【用户行为】
  1. **记录用户行为**：浏览、点赞、不喜欢、收藏等（使用Redis） <br> 
  2. **文章热度实时计算(Kafka: behavior --> article)**：每当有用户行为，对该篇文章得分进行重新计算，并刷新Redis中的热门文章 <br> 
### （以下为自媒体端）
### -| wemedia: 【自媒体新闻管理】
  0. **拦截器**：拦截从网关转发来的请求，从该请求头获取到自媒体人信息（主要是自媒体人id） <br> 
  1. **自媒体人登录**：登录后，签发加密 token 到前端 localStorage，与网关层 GlobalFilter 进行协同工作 <br> 
  2. **频道管理**：自媒体端只能搜索频道，**后台管理端**可以增删改查频道 <br> 
  3. **素材管理**：自媒体人增删改查自己的图片素材 <br> 
  4. **新闻查询**：查询自己已提交的新闻情况 <br> 
  5. **文章上下架(Kafka: wemedia --> article)**：自媒体人上架/下架自己已经审核通过的文章 <br> 
  6. **新闻提交审核(feign: wemedia --> article)**：提交新闻供后台审核，审核通过后上架为文章 <br> 
  7. **新闻延迟审核(Redis: wemedia --> schedule --> article)**： <br> 
    (1) **存储延迟审核任务到mysql**，再由**schedule微服务**进行定时刷新 <br> 
    (2) **从 redis 队列中取需要执行的任务**，进行新闻审核，审核通过后通过 feign 远程调用**article微服务**上架文章 <br> 
### （以下为后台管理端）
### -| admin: 【后台管理端】
  0. **拦截器**：拦截从网关转发来的请求，从该请求头获取到管理员信息（主要是管理员id） <br> 
  1. **管理员登录**：登录后，签发加密 token 到前端 localStorage，与网关层 GlobalFilter 进行协同工作 <br> 
  2. **用户管理**（实际位于**用户管理(user)微服务**） <br> 
  3. **频道管理**（实际位于**新闻管理(wemedia)微服务**） <br> 
  4. **审核敏感词管理** <br> 
  5. **文章管理** <br> 
### （以下为定时任务）
### -| schedule: 【定时任务】
  1. **新闻延迟审核**：定时刷新任务：从 Mysql(>5分钟) --> Redis-ZSET(<=5分钟) --> Redis-LIST(<=当前时间) <br> 
  2. **热门文章定时刷新**：每小时重新计算一天内的文章得分，刷新热门文章到Redis <br> 

 <br> 

## 仿今日头条项目V2.0：
（仅备注新增/改动内容，NoApp表示该模块为非启动模块）
### 1. heima-file-starter (NoApp)
### 2. heima-leadnews-common (NoApp)
### 3. heima-leadnews-feign-api (NoApp)
新增wemedia、user的跨服务调用，主要用于admin和behavior模块
### 4. heima-leadnews-gateway: 
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
