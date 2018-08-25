<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<footer class="blog-footer">
    <p>
        <span>Copyright</span><span>&copy;</span><span>2018</span><a
            href="${ctx}index.jsp">Graduation Project</a><span>Design By songshuiyang</span>
    </p>
    <p>
        <a href="http://www.miibeian.gov.cn/" target="_blank">赣ICP备18001541号</a>
    </p>
</footer>
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
<!--遮罩-->
<div class="blog-mask animated layui-hide"></div>
