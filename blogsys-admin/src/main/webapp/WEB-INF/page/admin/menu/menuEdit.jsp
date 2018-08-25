<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>菜单管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
</head>
<body>
<div>
    <%--在一个容器中设定 class="layui-form" 来标识一个表单元素块--%>
    <form class="layui-form" style="margin-left: 20px;margin-top: 20px;">
        <div style="width:100%;overflow: auto;">
            <input type="hidden" id="id" name="id">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="firstMenu" class="layui-form-label">
                        一级菜单
                    </label>
                    <div class="layui-input-block" style="width: 100%;">
                        <input type="text" id="firstMenu" name="firstMenu" lay-verify="required" placeholder="" value="" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="secondMenu" class="layui-form-label">
                        二级菜单
                    </label>
                    <div class="layui-input-block" style="width: 100%;">
                        <input type="text" id="secondMenu" name="secondMenu"  placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="type" class="layui-form-label">
                        菜单类型
                    </label>
                    <div class="layui-input-block" style="width: 290px;">
                        <select id="type" name="type" lay-filter="selFilter" lay-verify="required" lay-search>
                            <option value=""></option>
                            <option value="0">一级菜单</option>
                            <option value="1">二级菜单</option>
                        </select>

                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="icon" class="layui-form-label">
                        菜单图标
                    </label>
                    <div class="layui-input-block" style="width: 100%;">
                        <input type="text" id="icon" name="icon" lay-verify="required" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="url" class="layui-form-label">
                        链接
                    </label>
                    <div class="layui-input-block" style="width: 100%;">
                        <input type="text" id="url" name="url" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="orderNum" class="layui-form-label">
                        排序号
                    </label>
                    <div class="layui-input-block" style="width: 100%;">
                        <input type="text" id="orderNum" name="orderNum" lay-verify="required" placeholder=""  class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="remarks" class="layui-form-label">
                        <span class="x-red"></span>备注
                    </label>
                    <div class="layui-input-block" style="width: 100%;">
                        <textarea id="remarks" name="remarks" placeholder="请输入内容" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>

            <div class="layui-form-item" style="margin-left:40%;">
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

        // 修改页面页面初始化操作
        $(function () {
            $("#id").val("${menu.id}");
            $("#firstMenu").val("${menu.firstMenu}");
            $("#secondMenu").val("${menu.secondMenu}");
            $("#icon").val("${menu.icon}");
            $("#url").val("${menu.url}");
            $("#orderNum").val("${menu.orderNum}");
            $("#remarks").val("${menu.remarks}");

            // 下拉框赋值
            $("select[name=type]").val("${menu.type}");
            form.render();
        });


        // 关闭按钮
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        // 监听提交
        form.on('submit(add)', function (data) {
            console.log(data.field);
            if (data.field.type == 0) {
                if (data.field.secondMenu !='') {
                    layer.msg('类型为一级菜单不能填写二级菜单名', {icon: 5});
                    return false;
                }
            }
            $.ajax({
                url: '/menu/',
                type: 'post',
                data: data.field,
                async: false,
                dataType: "json",
                traditional: true,
                success: function (msg) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.location.reload();
                    window.top.layer.msg("操作成功", {icon: 6, area: ['120px', '80px'], anim: 2});
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
