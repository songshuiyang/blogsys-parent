<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib.jsp" %>
<head>
    <title>留言</title>
    <%@include file="../../../common/front/meta.jsp" %>
    <%@include file="../../../common/front/link.jsp" %>
    <link href="${ctx}static/front/css/detail.css?v=${version}" rel="stylesheet" />
</head>
<html>
<body style="background-color: #ecf0f5">
<div class="overflay"></div>
<%@include file="../../../common/front/header.jsp" %>
<div class="blog-body">
    <div class="blog-container">
        <blockquote class="layui-elem-quote sitemap shadow">
            <a href="${ctx}" title="网站首页">网站首页 > </a>
            <a><cite>时间线</cite></a>
        </blockquote>
        <div class="blog-module shadow animated zoomIn" style="box-shadow: 0 1px 8px #a6a6a6;">
            <fieldset class="layui-elem-field layui-field-title" style="margin-bottom:0">
                <legend>时间线</legend>
                <div class="layui-field-box" style="margin-top: 20px;">
                    <ul class="layui-timeline">
                        <c:forEach items="${timeLine}" var="item" varStatus="i">
                            <li class="layui-timeline-item">
                                <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                                <div class="layui-timeline-content layui-text">
                                    <h3 class="layui-timeline-title">${item.value1}</h3>
                                    ${item.value}
                                </div>
                            </li>
                        </c:forEach>
                        <li class="layui-timeline-item">
                            <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                            <div class="layui-timeline-content layui-text">
                                <div class="layui-timeline-title">过去</div>
                            </div>
                        </li>
                    </ul>
                </div>
            </fieldset>
        </div>
    </div>
</div>
</body>
<!-- layui.js -->
<script src="${ctx}static/layui-module/layui/layui.js"></script>
<!-- 全局脚本 -->
<script src="${ctx}static/front/js/global.js?v=${version}"></script>
<script>
    layui.use(['form','layer', 'upload'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        var layer = layui.layer;
        var upload = layui.upload;


        // 导航栏重新选中
        var li = $(".layui-nav").children("li");
        $.each(li,function (index,item) {
            $(this).removeClass("layui-this");
        });
        $('#lay-nav-3').addClass("layui-this");

    });
</script>
</html>
