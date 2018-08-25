<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../common/taglib.jsp" %>
<%@include file="../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <title>博客系统后台</title>
    <%@include file="../../common/front/meta.jsp" %>
    <link rel="stylesheet" href="${ctx}static/layui-module/plugins/font-awesome/css/font-awesome.min.css" media="all" />
    <link rel="stylesheet" href="${ctx}static/layui-module/build/css/app.css?v=${version}" media="all" />
    <link rel="stylesheet" href="${ctx}static/layui-module/build/css/themes/blue.1.css" media="all" id="skin" kit-skin />
</head>

<body class="kit-theme">
<div class="layui-layout layui-layout-admin kit-layout-admin">
    <div class="layui-header  animated fadeInDown">
        <div class="layui-logo">博客系统后台</div>
        <div class="layui-logo kit-logo-mobile"></div>
        <ul class="layui-nav layui-layout-left kit-nav">
            <li class="layui-nav-item"><a href="${ctx}">博客前台</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;">还没想好</a></dd>
                    <dd><a href="javascript:;">......</a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right kit-nav">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <i class="layui-icon">&#xe63f;</i> 皮肤</a>
                </a>
                <dl class="layui-nav-child skin">
                    <dd><a href="javascript:;" data-skin="blue.1" style="color:#393D49;"><i class="layui-icon">&#xe658;</i> 天空蓝</a></dd>
                    <dd><a href="javascript:;" data-skin="orange" style="color:#ff6700;"><i class="layui-icon">&#xe658;</i> 橘子橙</a></dd>
                    <dd><a href="javascript:;" data-skin="green" style="color:#00a65a;"><i class="layui-icon">&#xe658;</i> 春天绿</a></dd>
                    <dd><a href="javascript:;" data-skin="pink" style="color:#FA6086;"><i class="layui-icon">&#xe658;</i> 少女粉</a></dd>
                    <dd><a href="javascript:;" data-skin="default" style="color:#00c0ef;"><i class="layui-icon">&#xe658;</i> 黑色</a></dd>
                    <dd><a href="javascript:;" data-skin="red" style="color:#dd4b39;"><i class="layui-icon">&#xe658;</i> 枫叶红</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">
                    欢迎你 ${user.username}
                </a>
                <dl class="layui-nav-child">
                    <%--<dd><a href="javascript:;" kit-target data-options="{url:'basic.html',icon:'&#xe658;',title:'基本资料',id:'966'}"><span>基本资料</span></a></dd>--%>
                    <dd><a href="javascript:;">基本资料</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="/system/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
        </ul>
    </div>


    <div class="layui-side layui-nav-tree layui-bg-black kit-side animated fadeInLeft">
        <div class="layui-side-scroll">
            <div class="kit-side-fold">
                <i id="navigationIcon" class="fa iconfont icon-zhankaikuaitui" style="margin-top: 10px;" aria-hidden="true"></i>
            </div>
            <div class="headPortrait animated bounce">
                <div class="headPortraitImg">
                    <a title="我的头像">
                        <img style="width: 80px;height: 80px;border-radius:10px !important;-webkit-border-radius: 10px !important;-moz-border-radius: 10px;" src="${user.headPortrait}"/>
                    </a>
                </div>
                <div class="headPortraitTip">
                    你好! <span class="userName">${user.username}</span>
                </div>
            </div>
            <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
            <c:forEach items="${menuBars}" var="firstMenu" varStatus="k">
                <li class="layui-nav-item">
                    <a class="" href="javascript:;">
                        <i aria-hidden="true" class="${firstMenu.icon}"></i>
                        <span>${firstMenu.firstMenu}</span>
                    </a>
                <c:forEach items="${firstMenu.secondMenuList}" var="secondMenu" varStatus="j">
                    <dl class="layui-nav-child">
                        <a href="javascript:;" kit-target
                           data-options="{url:'${secondMenu.url}',icon:'${secondMenu.icon}',title:'${secondMenu.secondMenu}',id:'${secondMenu.id}'}">
                            <i class="${secondMenu.icon}"></i>
                            <span>${secondMenu.secondMenu}</span>
                        </a>
                    </dl>

                </c:forEach>
                </li>
            </c:forEach>
            </ul>
        </div>
    </div>
<div class="layui-body animated fadeInRight" id="container">
    <!-- 内容主体区域 -->
    <div style="padding: 15px;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63e;</i> 请稍等...</div>
</div>

<div class="layui-footer animated fadeInUp">
    <!-- 底部固定区域 -->
    2018 &copy; ecut songsy
</div>
</div>
<script src="${ctx}static/layui-module/layui/layui.js"></script>
<script>
    var message;
    layui.config({
        base: '${ctx}static/layui-module/build/js/',
        version: '1.0.1'
    }).use(['app', 'message'], function() {
        var app = layui.app,
            $ = layui.jquery,
            layer = layui.layer;
        //将message设置为全局以便子页面调用
        message = layui.message;
        //主入口
        app.set({
            type: 'iframe'
        }).init();
        $('dl.skin > dd').on('click', function() {
            var $that = $(this);
            var skin = $that.children('a').data('skin');
            switchSkin(skin);
        });
        var setSkin = function(value) {
                layui.data('kit_skin', {
                    key: 'skin',
                    value: value
                });
            },
            getSkinName = function() {
                return layui.data('kit_skin').skin;
            },
            switchSkin = function(value) {
                var _target = $('link[kit-skin]')[0];
                _target.href = _target.href.substring(0, _target.href.lastIndexOf('/') + 1) + value + _target.href.substring(_target.href.lastIndexOf('.'));
                setSkin(value);
            },
            initSkin = function() {
                var skin = getSkinName();
                switchSkin(skin === undefined ? 'blue.1' : skin);
            }();
    });
</script>
</body>
</html>