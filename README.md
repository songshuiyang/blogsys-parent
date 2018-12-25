## 一：项目介绍
* 项目名称: 基于JavaWeb的个人博客系统

* 演示地址: www.songshuiyang.site 免密码登录

* 如有问题联系邮箱：songshuiyang@foxmail.com



### 1.1 开发背景
博客（Blog），是Weblog的简称，博客作为一种新的网络交流方式得到快速发展， 市面上有很多成熟的博客系统，比如说新浪博客、网易博客、CSDN博客、博客园，但作为一个软件开发的开发人员来说，独立开发一个个人博客系统是一个很好的锻炼机会，既可以学习各种软件技术，也可以作为一个信息管理平台，记录一些个人的想法和心得及学习笔记。

查看市面上主流的博客系统，发现这些主流博客系统不能很好的高度个性化定制，每个平台都有或多或少的缺点，所以参照市面上主流的博客系统的优点并结合自己的个人想法，设计及实现一个个人博客。

### 1.2 实现目标
本次开发的主要目标主要是设计及实现一个博客系统，博客系统分为俩个子模块，一个是前台展示模块，另一个是后台管理模块。前台展示模块主要是对博客文章内容信息进行展示，后台管理模块主要是对这些博客内容进行新增编辑这些操作。
> 系统功能模块图:

![系统功能模块图](https://images.gitee.com/uploads/images/2018/0825/003420_ca33056a_1502968.png "iframe.png")


### 1.3 开发意义
开发个人博客系统，一方面，可以督促自己学习新的知识，并将学习到的技术点转为实践，纸上得来终觉浅，绝知此事要躬行，任何理论知识如果没有转化成实际结果，那么学习这些知识点就没有意义，理论与实践的结合可以使自己的技术水平得到大幅提高，以便为以后工作打好坚实的技能基础。

对于软件开发人员来说，在工作中会遇到各种新的技术或者问题，通过编写博客将这些记录下来，一方面可以加深对技术点的理解，另一方面也可以作为笔记一样记录下来，等哪天在遇到同样的问题,我们可以翻阅他，而不至于遇到相同问题又要重新思考当时的解决方法。

编写博客可以提高自己的思维能力。也能提高自己的写作水平，博客写多了，可以很好的锻炼我们的逻辑思维能力，可以时刻保持思考的习惯,不至于思想比较僵化，可以提高解决问题的思维角度,有利于我们作为程序员的长远发展。
## 二：系统设计及实现
### 2.1 系统功能需求
博客系统分为俩个子模块，一个是前台展示模块，另一个是后台管理模块。

#### 2.1.1 前台展示模块：
用户进入系统首先看到的是前台展示模块，该模块是数据信息展示区，用户在这里可以看到录用的一些博客文章，文章详情，留言评论，相册图片等信息，具体功能需求如下: 

1. 用户注册/登录：本系统用户角色分为游客，普通用户，admin三种角色，没有在本系统中注册过的用户则为游客角色，只能查看前台模块的数据，游客如果想录用博客文章需要通过注册功能成为本系统中的普通用户，注册完成之后可以通过登录功能登录到后台管理系统。
2. 博客文章展示功能：用户录用的文章可以在前台模块中进行展示，包括文章标题，文章头图，文章摘要，文章内容，创建时间，创建人，博客所属分类，博客标签，点赞数，浏览量，文章评论；
3. 根据博客分类检索文章功能，采用博客分类对博客文章进行一个大范围的归档，一篇博客对应一个分类，一个分类可以对应多篇博客文章，可通过分类查找对应的博客，博客文章信息也可以展示本博客是属于那种分类。
4. 标签是对博客文章进行一个小范围的归档，一篇博客可以多个标签，一个标签可以对应多篇博客文章，可以通过博客标签查找对应的博客，博客文章信息也可以展示本博客所标记的标签；
5. 热门文章排行，本功能是对质量高的博客文章进行推荐展示，划分标准是采用文章点赞数从高到底选取前十的博客文章，用户可以通过点击文章标题进入到相应的博客文章中。
6. 展示相册图片功能：提供图片展示功能，每张图片应该有对应的相册，可以对图片归类，每张图片可以显示对应的文字摘要信息，提供幻灯片展示图片的功能。
7. 留言功能：用户浏览完博客之后可能会有一些感受建议，用户可以通过该功能进行留言评论，帮助博主更好的改进本系统。
8. ... 未完待续

#### 2.1.2 后台管理模块
本系统用户角色分为游客，普通用户，admin三种角色，游客需要通过前台注册功能成为普通用户之后才能登录系统，后台模块提供如下功能：
1. 博客文章管理：提供添加博客，修改博客，删除博客，查看博客详情，发布博客的功能。
2. 博客分类：提供新增,修改博客的时候选择博客分类数据。
3. 博客标签管理：提供新增,修改博客的时候可供选择的标签。
4. 时间线功能：提供了日迹功能，随时记录美好时刻
5. 相册图片管理：提供添加图片的功能，添加图片的时候可以选择相册，添加完成之后可以设置图片是否发布，如果是选择的是【可见】图片会展示在前台页面，如果选择的是【不可见】则新增的图片展示在前台页面。
6. 随机头像管理：随机头像是为前台留言评论服务，用户一进入到前台页面，系统会返回一个随机头像作为评论人的头像。
7. 评论留言管理：可以对用户的评论留言进行删除、修改操作。
8. 用户管理：用于维护后台模块系统用户，可以新增用户，修改用户信息，删除用户，查看用户详情，分配角色功能。
9. 角色管理：用于维护后台模块系统角色，可以新增角色，修改角色信息，删除角色，查看角色详情，分配用户，分配权限功能 。
10. 权限管理：用于维护后台模块系统权限，可以新增权限，修改权限，删除权限 。
11. 菜单管理：用户维护后台模块系统左侧菜单项，可以新增菜单，修改菜单限，删除菜单，自定义菜单 。
12. 登录历史：用于记录用户登录信息，包括登录地，所在设备，记录访问Cookie
13. 系统监控：Ruid Monitor监控JavaWeb运行数据
14. Swagger API接口：用于管理后台接口
15. ... 未完待续

## 三: 系统开发工具及使用技术

本系统基于JavaWeb进行开发，整合了Spring, Spring Mvc, Mybatis, Apache shiro等常用流行框架，由于采用的技术都是行业上广泛应用的，都是行业里比较成熟的产品，所以对项目安全性，稳定性，扩展性提供了可靠的保障。

### 3.1 开发工具
技术 | 名称 | 官网
----|------|----
IntelliJ IDEA | 集成开发环境 |[https://www.jetbrains.com/idea/](https://www.jetbrains.com/) 
Navicat | 数据库可视化工具  |[https://www.navicat.com.cn/](https://www.navicat.com.cn/)
Git | 代码版本控制  |[https://git-scm.com/ ](https://git-scm.com/ )
Git bash | Git操作工具  |[https://gitforwindows.org/](https://gitforwindows.org/)

### 3.2 后端技术
技术 | 名称 | 官网
----|------|----
Spring Framework | 核心基础框架  | [http://projects.spring.io/spring-framework/](http://projects.spring.io/spring-framework/)
SpringMVC | MVC框架  | [http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc)
MyBatis | ORM框架  | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
Apache Shiro | 安全框架  | [http://shiro.apache.org/](http://shiro.apache.org/)
Mysql 5.7 | 数据库 |[https://www.mysql.com/](https://www.mysql.com/) 
PageHelper | MyBatis分页插件  | [https://github.com/pagehelper/Mybatis-PageHelper.git](https://github.com/pagehelper/Mybatis-PageHelper.git)
Druid | 数据库连接池  | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)
Log4J | 日志组件  | [http://logging.apache.org/log4j/1.2/](http://logging.apache.org/log4j/1.2/)
MyBatis Generator | 代码生成  | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)
AliOSS | 云存储  | [https://www.aliyun.com/product/oss/](https://www.aliyun.com/product/oss/)
Ehcache | 纯Java的进程内缓存框架  |[www.ehcache.org/](www.ehcache.org/)
Redis | 高性能的key-value数据库  |[https://redis.io/](https://redis.io/)
Freemarker | 模板引擎  |[https://freemarker.apache.org/](https://freemarker.apache.org/)
Ftp文件上传 | 文件上传 |[http://www.songshuiyang.site](http://www.songshuiyang.site) 
Lombok | 简化代码工具  |[https://www.projectlombok.org/](https://www.projectlombok.org/)
Swagger | REST APIs文档工具  |[https://swagger.io](https://swagger.io)

### 3.3 前端技术
技术 | 名称 | 官网
----|------|----
Layui | 前端基础框架 |[http://www.layui.com/](http://www.layui.com/) 
Layer | 弹层组件 |[http://layer.layui.com/hello.html](http://layer.layui.com/hello.html) 
JQuery | 函式库  | [http://jquery.com/](http://jquery.com/)
Bootstrap | 前端框架  | [http://getbootstrap.com/](http://getbootstrap.com/)
Waves | 点击效果插件  | [https://github.com/fians/Waves](https://github.com/fians/Waves)
Animate.css | css动画效果 |[https://daneden.github.io/animate.css/](https://daneden.github.io/animate.css/)
Hover.css | css动画效果  | [http://ianlunn.github.io/Hover/](http://ianlunn.github.io/Hover/)
Iconfont | 阿里巴巴矢量图标 |[http://www.iconfont.cn/](http://www.iconfont.cn/)
Font-awesome| 字体图标  | [http://fontawesome.io/](http://fontawesome.io/)
ECharts| 图表插件  | [echarts.baidu.com/ ](echarts.baidu.com/ )
UEditor | 在线HTML编辑器  | [ueditor.baidu.com/](ueditor.baidu.com/)

### 四：运行项目

#### 4.1 硬件环境
由于本系统是属于小型项目，业务需求并不复杂，所以对硬件配置的要求并不高，部署服务只需一台小型服务器即可，现在市面上的小型服务器比较便宜，比如阿里云服务器，腾讯云服务器

|  配置 | 参数    | 
| :-----   | :-----   | 
| CPU     | Intel Xeon E5-2682 v4 1核   |
| 内存     | 2G 最新一代DDR4 内存        |
| 带宽    | 1M带宽 VPC专有网络, I/O 优化 |
| 系统盘   | 40G系统盘高效云盘           |
#### 4.2 软件环境

|  软件 | 版本    | 
| :-----   | :-----   | 
| 服务器系统   |   Centos 7 |
| JDK     |     JDK 1.8    |
| Web服务器    |  Tomcat 8.0   |
| 数据库服务器   |    Mysql 5.7   |
| 反向代理 |    Nginx 1.12.2   |
| 文件服务器 | Ftp服务器, 使用nginx做HTTP映射| 
| 持续集成 | Jenkins| 

#### 4.3 项目运行
* 1、搭建运行环境，如需使用文件上传功能需要自己搭建ftp文件服务器，本人的不开放哟，搭建好之后修改`project.properties` ftp 参数
* 2、创建数据库 `blogsys`, `执行sql文件 `doc\sql\blogsys.sql` 文件`
* 3、执行Maven 打包命令得到炸包 `blogsys-parent.war` 放到 Tomcat 服务器中


### 五：其他
#### 5.1 代码自动生成
##### 5.1.1 传统 mybatis-generator 生成方式
执行步骤：
```
打开 generatorConfig.xml 配置文件并进行相应的配置 (blogsys-parent\blogsys-admin\src\main\resources\generatorConfig.xml)
1. 检查数据库驱动	使用绝对路径classPathEntry location 属性（如果本地没有可以在项目根目录lib文件夹下copy)
2. 配置数据库链接URL，用户名、密码 ，及生成的表名
3. IDEA 执行 maven plugins:  mybatis-generator:generate
```

##### 5.1.2 利用freemarker 自定义生成方式
执行步骤：
```
详见步骤：blogsys-parent\blogsys-admin\src\main\resources\templates\README.md
 
1. templates 目录下为模板文件，可以自定义修改
2. 执行`com.songsy.core.generate.CodeGenerateUtils`, 填入表名及实体类名即可
       public static void main(String[] args) throws Exception{
           CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
           // 表名 + 实体类名 + 实体类中文名
           codeGenerateUtils.generate("sys_account","Account","用戶");
       }
```

### 六：项目效果图
* 前台展示

![输入图片说明](https://images.gitee.com/uploads/images/2018/0825/030604_353c9bf5_1502968.png "front.png")

* 前台文章
![输入图片说明](https://images.gitee.com/uploads/images/2018/0825/030631_b6bbfe89_1502968.png "font1.png")

* 文章详情界面
![输入图片说明](https://images.gitee.com/uploads/images/2018/0825/030701_8fa4c909_1502968.png "font2.png")

* 注册界面
![输入图片说明](https://images.gitee.com/uploads/images/2018/0825/030834_152f74d6_1502968.png "register.png")

* 登录界面
![输入图片说明](https://images.gitee.com/uploads/images/2018/0825/030643_8387f0ee_1502968.png "login.png")

* 后台管理界面
![输入图片说明](https://images.gitee.com/uploads/images/2018/0825/030744_cdcc7922_1502968.png "admin.png")

* 后台列表页面
![输入图片说明](https://images.gitee.com/uploads/images/2018/0825/030801_fb657aaa_1502968.png "admin1.png")




### 七：总结
该系统整合了Spring, Spring Mvc, Mybatis, Apache shiro等常用流行框架，实现了当前主流博客系统的基本功能，博客文章录用方便，使用富文本框编辑器可以很方便的对文字格式、图片、文件、视频进行编辑，同时还具有代码高亮的功能。使用Layui前端UI框架只要通过少量代码就可以达到优秀的显示效果，这对于我们后端方向的开发人员是极大的帮助，这样我们可以把重点转到后台功能实现上，而不是前端显示上，所以根据项目实际需要使用第三方框架可以帮助我们解决许多问题。

由于本系统从原型设计、需求分析、功能实现、数据库设计都是由本人独立完成，所以从中收获了好多，在这个过程中我明白了实践和理论二者不可分割的关系，写程序就不是去盲目的去套用代码，而是理清此程序的架构以及思路。程序该从什么时候开始，什么时候结束。也感受到一个功能实现并不是最终目的，还需考虑这个功能安全性，性能要求，扩展性等等。同时独立完成一个项目也发现了自己存在许多问题，知道了自己在哪些方面存在欠缺需要改正，这样可以不断的鞭策自己，努力向着更好的方向发展。
