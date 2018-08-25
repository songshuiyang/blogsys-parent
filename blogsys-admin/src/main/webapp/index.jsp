<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/taglib.jsp" %>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>宋水阳个人博客系统</title>
    <%@include file="common/front/meta.jsp" %>
    <%@include file="common/front/link.jsp" %>
    <link href="${ctx}static/front/css/home.css?v=${version}" rel="stylesheet" />
    <link href="${ctx}static/admin/css/main.css?v=${version}" rel="stylesheet" />
</head>
<body style="background-color: #ecf0f5">
<!--贼罩层和皮肤-->
<div class="overflay"></div>
<!-- 导航 -->
<%@include file="common/front/header.jsp" %>
<!-- 主体（一般只改变这里的内容） -->
<div class="blog-body">

    <!-- 这个一般才是真正的主体内容 -->
    <div class="blog-container">
        <div class="blog-main">
            <%--<!-- 轮播 -->--%>
            <div id="flash" class="animated bounceInLeft">
                <div class="layui-carousel" id="carousel">
                    <div carousel-item>
                        <div>
                            <a target="_blank" href="https://spring.io/"><img src="http://111.230.226.158/uploadfile/banner/banner-spring.png"></a>
                        </div>
                        <div>
                            <a target="_blank" href="http://www.songshuiyang.site/"><img src="http://111.230.226.158/uploadfile/banner/banner-black.png"/></a>
                        </div>
                        <div>
                            <a target="_blank" href="http://www.ecit.edu.cn/"><img src="http://111.230.226.158/uploadfile/banner/banner-school.png"/></a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 网站公告提示 -->
            <div class="home-tips shadow animated bounceInUp">
                <i style="float: left; line-height: 17px; color: red" class="iconfont icon-iconset0272"></i>
                <div class="home-tips-container">
                    <span style="color: #009688">博客前台正在开发中</span>
                    <span style="color: red">毕业设计编码中</span>
                </div>
            </div>
            <!--左边文章列表-->
            <div class="blog-main-left" id="blog-main-left">
                <!-- 文章主体内容流加载 -->

            </div>
            <!--右边小栏目-->
            <div class="blog-main-right">
                <div class="blogerinfo blog-module shadow animated lightSpeedIn">

                    <div class="blog-module-title">博主简介</div>

                    <img class="hvr-grow-shadow" style="width: 100px;height: 100px;border-radius:10px !important;-webkit-border-radius: 10px !important;-moz-border-radius: 10px;" src="http://111.230.226.158/uploadfile/20180606/005040-未标题-1.png"/>

                    <p class="blogerinfo-nickname">JAVA开发</p>
                    <p class="blogerinfo-introduce">将来的你，一定会感谢现在拼命努力的自己</p>
                    <p class="blogerinfo-content hvr-grow-shadow">
                        <i class="fa fa-location-arrow"></i>&nbsp;江西 - 南昌
                    </p>
                    <p class="blogerinfo-content hvr-grow-shadow">
                        <i class="iconfont icon-xingbie"></i>&nbsp;男
                    </p>
                    <p class="blogerinfo-content hvr-grow-shadow">
                        <i class="iconfont icon-xuexiao"></i>&nbsp;东华理工大学
                    </p>
                    <p class="blogerinfo-content hvr-grow-shadow">
                        <i class="iconfont icon-icon1 "></i>&nbsp;软件工程
                    </p>
                    <hr />
                    <div class="blogerinfo-contact">
                        <a target="_blank" title="Q我" id="qq" href="javascript:layer.tips('QQ: 1459074711','#qq', {tips: [4, '#78BA32'],time: 4000});">
                            <i class="fa fa-qq fa-2x hvr-grow-shadow"></i>
                        </a>
                        <a target="_blank" title="给我写信" id="email" href="javascript:layer.tips('邮箱: songshuiyang@foxmail.com','#email', {tips: [4, '#78BA32'],time: 4000});">
                            <i class="fa fa-envelope fa-2x hvr-grow-shadow"></i>
                        </a>
                        <a target="_blank" title="新浪微博" href="https://weibo.com/u/1810548617">
                            <i class="fa fa-weibo fa-2x hvr-grow-shadow"></i>
                        </a>
                        <a target="_blank" title="Github" href="https://github.com/songshuiyang/blogsys-parent">
                            <i class="fa fa-git fa-2x hvr-grow-shadow"></i>
                        </a>
                    </div>
                </div>
                <div></div>
                <!-- 分类导航 -->
                <div class="article-category shadow animated lightSpeedIn" id="blogtype">
                    <div class="article-category-title">分类导航</div>
                    <div class="clear"></div>
                </div>
                <!-- 文章标签 -->
                <div class="blog-module shadow animated lightSpeedIn" id="blogtag">
                    <div class="blog-module-title">文章标签</div>
                </div>
                <!--占位-->
                <div class="blog-module shadow animated lightSpeedIn">
                    <div class="blog-module-title">热文排行</div>
                    <ul class="fa-ul blog-module-ul animated rollIn" id="blogsrank">

                    </ul>
                </div>

                <div class="blog-module shadow animated lightSpeedIn">
                    <div class="blog-module-title">友情链接</div>
                    <ul class="blogroll">
                        <li><a target="_blank" href="http://www.songshuiyang.site/"
                               title="博客">博客</a></li>
                        <li><a target="_blank" href="http://www.layui.com/"
                               title="Layui">Layui</a></li>
                        <li><a target="_blank" href="http://www.iconfont.cn/"
                               title="Iconfont">Iconfont</a></li>
                        <li><a target="_blank" href="https://daneden.github.io/animate.css/"
                               title="Animate.css">Animate.css</a></li>
                        <li><a target="_blank" href="http://fontawesome.dashgame.com/"
                               title="fontawesome">fontawesome</a></li>
                        <li><a target="_blank" href="http://ianlunn.github.io/Hover/"
                               title="Hover">Hover</a></li>
                    </ul>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<!-- 底部 -->
<%@include file="common/front/footer.jsp" %>
<script>
    var user = "${user}";
</script>
<!-- layui.js -->
<script src="${ctx}static/layui-module/layui/layui.js"></script>
<!-- 全局脚本 -->
<script src="${ctx}static/front/js/global.js?v=${version}"></script>
<!-- 本页脚本 -->
<script src="${ctx}static/front/js/blog.js?v=${version}"></script>

<script>
    // 百度分享
    window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"1","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"slide":{"type":"slide","bdImg":"6","bdPos":"right","bdTop":"250"}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)]
</script>>
</body>
</html>