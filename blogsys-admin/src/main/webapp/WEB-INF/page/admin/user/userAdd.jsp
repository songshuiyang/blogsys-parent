<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
</head>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <input id="id" name="id" type="hidden">
        <input id="headPortrait" name="headPortrait" type="hidden">

        <div style="width:100%;overflow: auto;">
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">头像上传</legend>
                </fieldset>
                <div class="layui-input-inline">
                    <div class="layui-upload-drag" style="margin-left:10%;" id="uploadImage">
                        <i style="font-size:30px;" class="layui-icon"></i>
                        <p style="font-size: 10px">点击上传，或将文件拖拽到此处</p>
                    </div>
                </div>
                <div class="layui-input-inline">

                    <div id="headPortraitDIV" style="margin-top: 20px;margin-left: 50px">
                        <img src="${ctx}static/layui-module/x-admin/images/bg.png" width="100px" height="100px"
                             class="layui-upload-img layui-circle">
                    </div>

                </div>
            </div>
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="username" class="layui-form-label">
                        <span class="x-red">*</span>用户名
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="username" name="username" lay-verify="username"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label for="username" class="layui-form-label">
                        <span class="x-red">*</span>密码
                    </label>
                    <div class="layui-input-inline">
                        <input type="password" id="password" name="password" lay-verify="required"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="nickname" class="layui-form-label">
                        <span class="x-red">*</span>昵称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="nickname" name="nickname" lay-verify="nickname" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label for="age" class="layui-form-label">
                        <span class="x-red">*</span>年龄
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="age" name="age" lay-verify="number"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-block">
                    <input type="radio" id="sex1" name="sex" value="1" title="男">
                    <input type="radio" id="sex2" name="sex" value="0" title="女">
                </div>
            </div>
            <div class="layui-form-item">
                <div>
                    <label for="email" class="layui-form-label">
                        <span class="x-red"></span>邮箱
                    </label>
                    <div class="layui-input-block">
                        <input type="email" id="email" style="width: 93%" name="email" lay-verify="email"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div>
                    <label for="phone" class="layui-form-label">
                        <span class="x-red"></span>手机号
                    </label>
                    <div class="layui-input-block">
                        <input  id="phone" style="width: 93%" name="phone"
                                autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div>
                    <label for="address" class="layui-form-label">
                        <span class="x-red"></span>地址
                    </label>
                    <div class="layui-input-block">
                        <input id="address" style="width: 93%" name="address"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div>
                    <label for="remarks" class="layui-form-label">
                        <span class="x-red"></span>备注
                    </label>
                    <div class="layui-input-block">
                        <input  id="remarks" style="width: 93%" name="remarks"
                                autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div style="height: 60px"></div>
        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

                <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
                    确定
                </button>
                <button class="layui-btn layui-btn-primary" id="close">
                    取消
                </button>
            </div>
        </div>
    </form>
</div>
<%@include file="../../../../common/jsModule.jsp" %>
<script>
    layui.use(['form', 'layer', 'upload'], function () {
        $ = layui.jquery;
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;

        var flag, msg;
        $(function () {
            if ("${isUpdate}" === "true") {
                $.ajax({
                    url: '/user/${id}',
                    async: false,
                    type: 'get',
                    success: function (data) {
                        $('#id').val(data.id)
                        $('#address').val(data.address)
                        $('#age').val(data.age)
                        $('#email').val(data.email)
                        $('#username').val(data.username)
                        $('#nickname').val(data.nickname)
                        //$('#password').val(data.password)
                        $('#l_repass').val(data.password)
                        $('#phone').val(data.phone)
                        $('#sex').val(data.sex)
                        $('#remarks').val(data.remarks)
                        $('#headPortrait').val(data.headPortrait)
                        $('#headPortraitDIV').find('img').remove();
                        $('#headPortraitDIV').append('<img src="' + data.headPortrait + '"  width="100px" height="100px" class="layui-upload-img layui-circle">');

                        if (data.sex == 1) {
                            $('#sex1').prop("checked", true);
                        } else {
                            $('#sex2').prop("checked", true);
                        }
                        form.render();
                    }
                });
            }
            /**
             * 校验姓名
             */
            $('#username').on("blur", function () {
                // 修改操作不校验
                if ("${isUpdate}" === "true") {
                    flag = true;
                    return;
                }
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
                            $('#ms').find('span').remove();
                            if (data.code != 1) {
                                msg = data.content;
                                $('#ms').append("<span style='color: red;'>" + data.content + "</span>");
                                // layer.msg(msg,{icon: 5,anim: 6});
                            } else {
                                flag = true;
                                $('#ms').append("<span style='color: green;'>用户名可用</span>");
                            }
                        },
                        beforeSend: function () {
                            $('#ms').find('span').remove();
                            $('#ms').append("<span>验证ing</span>");
                        }
                    });
                }
            });

        });

        // 头像上传
        upload.render({
            elem: '#uploadImage'
            , url: '/file/uploadLocal'
            , method: 'post'
            , field: 'upfile'
            , before: function (obj) {

            }, done: function (res) {
                console.log(res)
                if (res.state != 'SUCCESS') {
                    layer.msg(res.url, {icon: 5, anim: 6});
                } else {
                    console.log("执行")
                    $('#headPortrait').val(res.url)
                    $('#headPortraitDIV').find('img').remove();
                    $('#headPortraitDIV').append('<img src="' + res.url + '"  width="100px" height="100px" class="layui-upload-img layui-circle">');

                }
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
                if (!flag) {
                    return msg;
                }
            }
            , repass: function (value) {
                if ($('#password').val() != $('#l_repass').val()) {
                    return '两次密码不一致';
                }
            }

        });

        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        //监听提交
        form.on('submit(add)', function (data) {
            console.log("请求数据", data)
            $.ajax({
                url: '/user/addUser',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.layui.table.reload('userList');
                    window.top.layer.msg(msg, {icon: 6,  area: ['120px', '80px'], anim: 2});
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
</script>
</body>

</html>
