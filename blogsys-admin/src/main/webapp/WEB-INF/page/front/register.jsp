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
            <a><cite>注册</cite></a>
        </blockquote>
        <div class="blog-module shadow animated zoomIn" style="box-shadow: 0 1px 8px #a6a6a6;">
            <fieldset class="layui-elem-field layui-field-title" style="margin-bottom:0">
                <legend>注册页面</legend>
                <div class="layui-field-box" style="margin-top: 20px;">
                    <form class="layui-form">
                        <input type="hidden" id="headPortrait" name="headPortrait" value="${randomHeadPortrait}">
                        <input type="hidden" id="type" name="type" value="1">

                        <div class="layui-container">
                            <div class="layui-row">
                                <div class="layui-form-item">
                                    <label for="headPortrait" class="layui-form-label">
                                        头像:
                                    </label>

                                    <div class="layui-col-md1">
                                        <div class="layui-inline">
                                            <div id="uploadImageDiv" style="margin-top: -10px;">
                                                <img src="${randomHeadPortrait}"
                                                     style="border-radius:30px;" width="60px" height="60px"
                                                     class="layui-upload-img layui-show">
                                            </div>

                                        </div>
                                    </div>

                                    <div class="layui-col-md3">
                                        <label for="uploadImageDiv" class="layui-form-label" style="color: red;width: 120px;">
                                            刷新页面更换头像
                                        </label>
                                    </div>
                                    <%--<div class="layui-col-md3">--%>
                                        <%--<button type="button" class="layui-btn layui-btn-radius layui-btn-normal"--%>
                                                <%--id="uploadImage">--%>
                                            <%--<i class="layui-icon">&#xe67c;</i>上传图片--%>
                                        <%--</button>--%>
                                    <%--</div>--%>


                                    <%--// 封面上传--%>
                                    <%--upload.render({--%>
                                    <%--elem: '#uploadImage'--%>
                                    <%--, url: '/file/uploadLocal'--%>
                                    <%--, method: 'post'--%>
                                    <%--, field: 'upfile'--%>
                                    <%--, before: function (obj) {--%>

                                    <%--}, done: function (res) {--%>
                                    <%--if (res.state != 'SUCCESS') {--%>
                                    <%--layer.msg(res.content, {icon: 5, anim: 6});--%>
                                    <%--} else {--%>
                                    <%--$('#headPortrait').val(res.url)--%>
                                    <%--$('#uploadImageDiv').find('img').remove();--%>
                                    <%--$('#uploadImageDiv').append('<img src="' + res.url + '"  style="border-radius:30px;" width="60px" height="60px" class="layui-upload-img layui-show">');--%>

                                    <%--}--%>
                                    <%--}--%>
                                    <%--});--%>
                                </div>

                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label for="username" class="layui-form-label">
                                                用户名：
                                            </label>
                                            <div class="layui-input-block  input-div">
                                                <input class="layui-input" type="text" id="username" name="username" lay-verify="username" placeholder="将会成为您唯一的登录名">
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <div id="usernameVerify" class="layui-form-mid layui-word-aux">
                                                <span class="x-red">*</span><span id="ums">将会成为您唯一的登录名</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label for="nickname" class="layui-form-label">
                                                昵称：
                                            </label>
                                            <div class="layui-input-block  input-div">
                                                <input class="layui-input" type="text" id="nickname" name="nickname" lay-verify="required" placeholder="">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label for="phone" class="layui-form-label">
                                                手机号：
                                            </label>
                                            <div class="layui-input-block input-div">
                                                <input class="layui-input" type="text" id="phone" name="phone" lay-verify="phone" placeholder="">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label for="password" class="layui-form-label">
                                                密码：
                                            </label>
                                            <div class="layui-input-block input-div">
                                                <input class="layui-input" type="password" id="password" name="password" lay-verify="required" placeholder="">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label for="repassword" class="layui-form-label">
                                                确认密码：
                                            </label>
                                            <div class="layui-input-block input-div">
                                                <input class="layui-input" type="password" id="repassword" name="repassword" lay-verify="repassword" placeholder="">
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label for="age" class="layui-form-label">
                                                年龄：
                                            </label>
                                            <div class="layui-input-block input-div">
                                                <input class="layui-input" type="text" id="age" name="age" lay-verify="number" placeholder="">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label  class="layui-form-label">
                                                性别：
                                            </label>
                                            <div class="layui-input-block input-div">
                                                <input type="radio" name="sex" value="1" title="男" checked="">
                                                <input type="radio" name="sex" value="0" title="女">
                                            </div>
                                        </div>
                                    </div>

                                </div>

                                <div class="layui-col-md5">
                                    <div class="layui-form-item">
                                        <div class="layui-inline">
                                            <label for="email" class="layui-form-label">
                                                邮箱：
                                            </label>
                                            <div class="layui-input-block input-div">
                                                <input class="layui-input" type="text" id="email" name="email" lay-verify="email" placeholder="">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>

                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <label for="remarks" class="layui-form-label">
                                        备注：
                                    </label>
                                    <div class="layui-input-block input-div-textarea">
                                        <textarea class="layui-textarea" name="remarks" id="remarks" placeholder="暂时分配普通用户角色, 只能使用部分功能, 如需使用全部功能, 邮件联系博主1459074711@qq.com"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <button class="layui-btn layui-btn-radius layui-btn-normal" style="margin-left:60px;" lay-filter="comment" lay-submit="">
                                <i class="layui-icon">&#xe618;</i>提交
                            </button>
                        </div>
                    </form>
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
        var flag = false; // 用户名校验flag


        /**
         * 校验姓名
         */
        $('#username').on("blur", function () {
            var username = $('#username').val();
            if (username.match(/[\u4e00-\u9fa5]/)) {
                return;
            }
            if (!/(.+){3,12}$/.test(username)) {
                return;
            }
            if (username != '') {
                $.ajax({
                    url: '/user/checkUser?username=' + username,
                    async: false,
                    type: 'get',
                    success: function (data) {
                        $('#usernameVerify').find('span').remove();
                        if (data.code != 1) {
                            flag = false
                            msg = data.content;
                            $('#usernameVerify').append("<span style='color: red;'>" + data.content + "</span>");
                            //
                        } else {
                            flag = true;
                            $('#usernameVerify').append("<span style='color: green;'>用户名可用</span>");
                        }
                    },
                    beforeSend: function () {
                        $('#usernameVerify').find('span').remove();
                        $('#usernameVerify').append("<span>验证ing</span>");
                    }
                });
            }
        });


        // 自定义验证规则
        form.verify({
            username: function (value) {
                if (value.trim() == "") {
                    return "用户名不能为空";
                }
                if (value.match(/[\u4e00-\u9fa5]/)) {
                    return "用户名不能为中文";
                }
                if (!/(.+){3,12}$/.test(value)) {
                    return "用户名必须3到12位";
                }
            }
            , repassword: function (value) {
                if ($('#password').val() != $('#repassword').val()) {
                    return '两次密码不一致';
                }
            }

        });

        // 监听提交
        form.on('submit(comment)', function (data) {
            if (!flag) {
                layer.msg("用户名不可用",{icon: 5,anim: 6});
                return false;
            }
            $.ajax({
                url: '/system/front/register',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.confirm('注册成功, 是否进入后台登录界面', {icon: 1, title:'注册成功'}, function(index){
                        window.open("${ctx}login.jsp");
                    });
                    $('#username').focus()


                }, error: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.top.layer.msg('请求失败', {icon: 5, area: ['120px', '80px'], anim: 2});
                }
            });
            return false;
        });
        form.render();
    });

</script>
</html>
