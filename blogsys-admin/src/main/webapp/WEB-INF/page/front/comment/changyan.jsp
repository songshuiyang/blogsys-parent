<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<head>
    <title>博客畅言</title>
    <%@include file="../../../../common/front/meta.jsp" %>
    <%@include file="../../../../common/front/link.jsp" %>
    <link href="${ctx}static/front/css/detail.css?v=${version}" rel="stylesheet" />
</head>
<html>
<body style="background-color: #ecf0f5">
<div class="overflay"></div>
<%@include file="../../../../common/front/header.jsp" %>
<div class="blog-body">
    <div class="blog-container">
        <blockquote class="layui-elem-quote sitemap shadow">
            <a href="${ctx}" title="网站首页">网站首页 > </a>
            <a><cite>畅言</cite></a>
        </blockquote>
        <div class="blog-module shadow animated zoomIn" style="box-shadow: 0 1px 8px #a6a6a6;">
            <fieldset class="layui-elem-field layui-field-title" style="margin-bottom:0">
                <!-- 代码1：放在页面需要展示的位置  -->
                <!-- 如果您配置过sourceid，建议在div标签中配置sourceid、cid(分类id)，没有请忽略  -->
                <div id="cyEmoji" role="cylabs" data-use="emoji"></div>
                <!-- 代码2：用来读取评论框配置，此代码需放置在代码1之后。 -->
                <!-- 如果当前页面有评论框，代码2请勿放置在评论框代码之前。 -->
                <!-- 如果页面同时使用多个实验室项目，以下代码只需要引入一次，只配置上面的div标签即可 -->
                <script type="text/javascript" charset="utf-8" src="https://changyan.itc.cn/js/lib/jquery.js"></script>
                <script type="text/javascript" charset="utf-8" src="https://changyan.sohu.com/js/changyan.labs.https.js?appid=cytf5fPKF"></script>


                <!--PC和WAP自适应版-->
                <div id="SOHUCS" sid="请将此处替换为配置SourceID的语句" ></div>
                <script type="text/javascript">
                    (function(){
                        var appid = 'cytf5fPKF';
                        var conf = 'prod_ee3a22e2c13174e193691fbc93e3cbc8';
                        var width = window.innerWidth || document.documentElement.clientWidth;
                        if (width < 960) {
                            window.document.write('<script id="changyan_mobile_js" charset="utf-8" type="text/javascript" src="https://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=' + appid + '&conf=' + conf + '"><\/script>'); } else { var loadJs=function(d,a){var c=document.getElementsByTagName("head")[0]||document.head||document.documentElement;var b=document.createElement("script");b.setAttribute("type","text/javascript");b.setAttribute("charset","UTF-8");b.setAttribute("src",d);if(typeof a==="function"){if(window.attachEvent){b.onreadystatechange=function(){var e=b.readyState;if(e==="loaded"||e==="complete"){b.onreadystatechange=null;a()}}}else{b.onload=a}}c.appendChild(b)};loadJs("https://changyan.sohu.com/upload/changyan.js",function(){window.changyan.api.config({appid:appid,conf:conf})}); } })(); </script>
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
    var $;
    layui.use(['form','layer', 'upload','element'], function () {
        var form = layui.form;
        $ = layui.jquery;
        var layer = layui.layer;
        var upload = layui.upload;
        var element = layui.element;

        // 导航栏重新选中
        var li = $(".layui-nav").children("li");
        $.each(li,function (index,item) {
            $(this).removeClass("layui-this");
        });
        $('#lay-nav-5').addClass("layui-this");


        // 监听提交
        form.on('submit(comment)', function (data) {
            console.log("请求数据", data);
            $.ajax({
                url: '/commentArticles/front/add',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.msg("你的意见是站长不懈的动力, 感谢你的反馈! ");
                    location.reload();
                }, error: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
                }
            });
            return false;
        });
        form.render();

    });

    // 评论点赞
    function satisfactoryCommentClick (commentId) {
        $.ajax({
            url: '/commentArticles/front/thumbs/commmet/'+commentId+'/1',
            type: 'get',
            async: false, dataType: "json", traditional: true,
            success: function (msg) {
                layer.msg("你的意见是站长不懈的动力, 感谢你的反馈! ");
                location.reload();
            }, error: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
            }
        });
    }
    // 评论差评
    function dissatisfiedCommentClick (commentId) {
        $.ajax({
            url: '/commentArticles/front/thumbs/commmet/'+commentId+'/0',
            type: 'get',
            async: false, dataType: "json", traditional: true,
            success: function (msg) {
                layer.msg("你的意见是站长不懈的动力, 感谢你的反馈! ");
                location.reload();
            }, error: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                window.top.layer.msg('请求失败', {icon: 5,  area: ['120px', '80px'], anim: 2});
            }
        });
    }

</script>
</html>
