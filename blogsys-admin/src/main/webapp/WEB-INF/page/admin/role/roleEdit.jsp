<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>角色管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="${ctx}static/plugins/ueditor/lang/zh-cn/zh-cn.js" media="all" />
</head>
<style>
    .ueditorContainer{
        margin-left: 110px;
        width: 1200px;
        height: 800px;
    }
</style>

<body>
<div>
    <%--在一个容器中设定 class="layui-form" 来标识一个表单元素块--%>
    <form class="layui-form" style="margin-left: 20px;margin-top: 20px;">
        <div style="width:100%;overflow: auto;">
            <input type="hidden" id="id" name="id">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="roleName" class="layui-form-label">
                        角色名称
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <input type="text" id="roleName" name="roleName" required lay-verify="required" placeholder="" value="" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="roleCode" class="layui-form-label">
                        角色code
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <input type="text" id="roleCode" name="roleCode" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="roleDescribe" class="layui-form-label">
                        角色描述
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <textarea id="roleDescribe" name="roleDescribe" placeholder="" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="remarks" class="layui-form-label">
                        <span class="x-red"></span>备注
                    </label>
                    <div class="layui-input-block" style="width: 600px;">
                        <textarea id="remarks" name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
                        确定
                    </button>
                    <button class="layui-btn layui-btn-primary" id="close">
                        取消
                    </button>
                </div>
            </div>
        </div>
    </form>

</div>
<%@include file="../../../../common/jsModule.jsp" %>

<script>
    layui.use(['form', 'layer', 'upload'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;

        // 页面初始化操作
        $(function () {
            $("#id").val("${role.id}");
            $("#roleName").val("${role.roleName}");
            $("#roleCode").val("${role.roleCode}");
            $("#roleDescribe").val("${role.roleDescribe}");
            $("#remarks").val("${role.remarks}");
        });


        // 关闭按钮
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        // 监听提交
        form.on('submit(add)', function (data) {
            $.ajax({
                url: '/role/add',
                type: 'post',
                data: data.field,
                async: false, dataType: "json", traditional: true,
                success: function (msg) {
                    layer.msg("新增成功");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.layui.table.reload('roleList');
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
