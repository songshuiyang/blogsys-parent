<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<nav class="blog-nav layui-header animated bounceInDown" style="position: fixed;">

    <div class="blog-container">

                <a href="${ctx}system/front/loadRegisterPage" class="blog-register">
                    <i class="iconfont icon-zhuce">&nbsp;注册</i>
                </a>

                <a href="${ctx}login.jsp" class="blog-user">
                    <i class="iconfont icon-denglu1">&nbsp;登录</i>
                </a>


        <!-- 导航菜单 -->
        <ul class="layui-nav" lay-filter="nav" style="border-color: #00c0ef;">
            <li class="layui-nav-item layui-this" id="lay-nav-1">
                <a href="${ctx}">
                    <i class="fa fa-home fa-fw"></i>&nbsp;博客首页
                </a>
            </li>
            <li class="layui-nav-item" id="lay-nav-3">
                <a href="${ctx}timeLine/front/loadListPage">
                    <i class="fa fa-clock-o fa-fw"></i>&nbsp;时间线
                </a>
            </li>
            <li class="layui-nav-item" id="lay-nav-4">
                <a href="${ctx}photoAlbum/front/loadListPage">
                    <i class="fa fa-photo fa-fw"></i>&nbsp;相册
                </a>
            </li>
            <li class="layui-nav-item" id="lay-nav-2">
                <a href="${ctx}commentArticles/front/leavingMessage">
                    <i class="fa fa-comment fa-fw"></i>&nbsp;给我留言
                </a>
            </li>
            <li class="layui-nav-item" id="lay-nav-5">
                <a href="${ctx}commentArticles/front/changyan">
                    <i class="fa fa-comments fa-fw"></i>&nbsp;畅言
                </a>
            </li>
            <li class="layui-nav-item" id="lay-nav-6">
                <a href="https://github.com/songshuiyang/blogsys-parent" target="_blank">
                    <i class="fa fa-file-code-o fa-fw"></i>&nbsp;获取源码
                </a>
            </li>
        </ul>
        <!-- 手机和平板的导航开关 -->
        <a class="blog-navicon" href="javascript:;"> <i
                class="fa fa-navicon"></i>
        </a>
    </div>
</nav>
<!--侧边导航 手机尺寸-->
<ul class="layui-nav layui-nav-tree layui-nav-side blog-nav-left layui-hide" lay-filter="nav" style="background-color: #00c0ef;">
    <li class="layui-nav-item layui-this" id="lay-nav-1">
        <a href="${ctx}">
            <i class="fa fa-home fa-fw"></i>&nbsp;博客首页
        </a>
    </li>
    <li class="layui-nav-item" id="lay-nav-3">
        <a href="${ctx}timeLine/front/loadListPage">
            <i class="fa fa-clock-o fa-fw"></i>&nbsp;时间线
        </a>
    </li>
    <li class="layui-nav-item" id="lay-nav-4">
        <a href="${ctx}photoAlbum/front/loadListPage">
            <i class="fa fa-photo fa-fw"></i>&nbsp;相册
        </a>
    </li>
    <li class="layui-nav-item" id="lay-nav-2">
        <a href="${ctx}commentArticles/front/leavingMessage">
            <i class="fa fa-comment fa-fw"></i>&nbsp;给我留言
        </a>
    </li>
    <li class="layui-nav-item" id="lay-nav-5">
        <a href="${ctx}commentArticles/front/changyan">
            <i class="fa fa-comments fa-fw"></i>&nbsp;畅言
        </a>
    </li>
    <li class="layui-nav-item" id="lay-nav-6">
        <a href="https://github.com/songshuiyang/blogsys-parent" target="_blank">
            <i class="fa fa-file-code-o fa-fw"></i>&nbsp;获取源码
        </a>
    </li>
</ul>
<%--链接提交工具是网站主动向百度搜索推送数据的工具，本工具可缩短爬虫发现网站链接时间，网站时效性内容建议使用链接提交工具，实时向搜索推送数据。本工具可加快爬虫抓取速度，无法解决网站内容是否收录问题--%>
<script>
    (function(){
        var bp = document.createElement('script');
        var curProtocol = window.location.protocol.split(':')[0];
        if (curProtocol === 'https') {
            bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
        }
        else {
            bp.src = 'http://push.zhanzhang.baidu.com/push.js';
        }
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(bp, s);
    })();
</script>
<%--百度统计--%>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?ac93934d9b847216e10a2f133187e535";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
