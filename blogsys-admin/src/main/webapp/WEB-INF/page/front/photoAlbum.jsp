<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib.jsp" %>
<head>
    <title>相册</title>
    <%@include file="../../../common/front/meta.jsp" %>
    <%@include file="../../../common/front/link.jsp" %>
    <link href="${ctx}static/front/css/detail.css?v=${version}" rel="stylesheet" />
    <link rel='stylesheet' href='${ctx}static/plugins/swipebox/css/justifiedgallery.min.css' type='text/css' media='all' />
    <link rel='stylesheet' href='${ctx}static/plugins/swipebox/css/swipebox.css' type='text/css' media='screen' />
</head>
<html>
<body style="background-color: #ecf0f5">
<div class="overflay"></div>
<%@include file="../../../common/front/header.jsp" %>
<div class="blog-body">
    <div class="blog-container">
        <blockquote class="layui-elem-quote sitemap shadow">
            <a href="${ctx}" title="网站首页">网站首页 > </a>
            <a><cite>相册</cite></a>
        </blockquote>
        <div class="blog-module shadow animated zoomIn" style="box-shadow: 0 1px 8px #a6a6a6;">
            <fieldset class="layui-elem-field layui-field-title" style="margin-bottom:0">
                <div class="photo-category">示例图片</div>
                <div class="layui-field-box">
                    <div class="swipeboxEx">
                        <a href="${ctx}static/plugins/swipebox/images/8083451788_552becfbc7_b.jpg" title="你的目的地在哪, 远方?">
                            <img alt="你的目的地在哪, 远方?" src="${ctx}static/plugins/swipebox/images/8083451788_552becfbc7_m.jpg"/>
                        </a>
                        <a href="${ctx}static/plugins/swipebox/images/7222046648_5bf70e893a_b.jpg" title="舞动">
                            <img alt="舞动" src="${ctx}static/plugins/swipebox/images/7222046648_5bf70e893a_m.jpg" />
                        </a>
                        <a href="${ctx}static/plugins/swipebox/images/7062575651_b23918b11a_b.jpg" title="桃花">
                            <img alt="桃花" src="${ctx}static/plugins/swipebox/images/7062575651_b23918b11a_m.jpg" />
                        </a>
                        <a href="${ctx}static/plugins/swipebox/images/6841267340_855273fd7e_b.jpg" title="岁月不等人">
                            <img alt="岁月不等人" src="${ctx}static/plugins/swipebox/images/6841267340_855273fd7e_m.jpg" />
                        </a>
                        <a href="${ctx}static/plugins/swipebox/images/6958456697_e56a37bb5f_b.jpg" title="曾经的曾经, 未来的未来">
                            <img alt="曾经的曾经, 未来的未来' Wall and the Old Rain" src="${ctx}static/plugins/swipebox/images/6958456697_e56a37bb5f_m.jpg" />
                        </a>
                        <a href="${ctx}static/plugins/swipebox/images/6791628438_affaa19e10_b.jpg" title="望着蔚然的天空">
                            <img alt="望着蔚然的天空" src="${ctx}static/plugins/swipebox/images/6791628438_affaa19e10_m.jpg" />
                        </a>
                        <a href="${ctx}static/plugins/swipebox/images/6840627709_92ed52fb41_b.jpg" title="The painter in Florence">
                            <img alt="The painter in Florence" src="${ctx}static/plugins/swipebox/images/6840627709_92ed52fb41_m.jpg" />
                        </a>
                        <a href="${ctx}static/plugins/swipebox/images/6812090617_5fd5bbdda0_b.jpg" title="Me and My Belover">
                            <img alt="Me and My Belover" src="${ctx}static/plugins/swipebox/images/6812090617_5fd5bbdda0_m.jpg" />
                        </a>
                        <a href="${ctx}static/plugins/swipebox/images/6806687375_07d2b7a1f9_b.jpg" title="Fiocco">
                            <img alt="Fiocco" src="${ctx}static/plugins/swipebox/images/6806687375_07d2b7a1f9_m.jpg" />
                        </a>
                    </div>
                </div>
                <c:forEach items="${photoAlbumTreeList}" var="photoAlbumTree" varStatus="i">
                    <c:if test="${not empty photoAlbumTree.photoAlbums}">
                        <div class="photo-category">${photoAlbumTree.category}</div>
                    </c:if>
                    <div class="layui-field-box">
                        <div class="swipeboxEx">
                            <c:forEach items="${photoAlbumTree.photoAlbums}" var="photoAlbums" varStatus="j">
                                <a href="${photoAlbums.url}" title="${photoAlbums.title}">
                                    <img src="${photoAlbums.url}" height="120px" width="220px;"/>
                                </a>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>

            </fieldset>
        </div>
    </div>
</div>
</body>
<!-- layui.js -->
<script src="${ctx}static/layui-module/layui/layui.js"></script>
<!-- 全局脚本 -->
<script src="${ctx}static/front/js/global.js?v=${version}"></script>
<script src='${ctx}static/plugins/swipebox/js/jquery.min.js'></script>
<script src='${ctx}static/plugins/swipebox/js/justifiedgallery.js'></script>
<script src='${ctx}static/plugins/swipebox/js/jquery.swipebox.min.js'></script>
<script>
    var $;
    layui.use(['form','layer', 'upload'], function () {
        $ = layui.jquery;
        var layer = layui.layer;


        // 导航栏重新选中
        var li = $(".layui-nav").children("li");
        $.each(li,function (index,item) {
            $(this).removeClass("layui-this");
        });
        $('#lay-nav-4').addClass("layui-this");


    });


    $(document).ready(function () {
        $('.swipeboxEx').each(function (i, el) {
            $(el).justifiedGallery({rel: 'gal' + i}).on('jg.complete', function () {
                if (i == 1) $('.swipeboxEx a').swipebox(); //如果想调用1个相册对应i值为0，同理3个相册，i值为2。
            });
        });
    });
</script>
</html>
