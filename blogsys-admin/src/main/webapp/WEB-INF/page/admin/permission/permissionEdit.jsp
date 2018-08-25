<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>权限管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <style>
        .downpanel .layui-select-title span{
            line-height: 38px;
        }
        .downpanel dl dd:hover{background-color: inherit;}
    </style>
</head>
<body>
<div>
    <%--在一个容器中设定 class="layui-form" 来标识一个表单元素块--%>
    <form class="layui-form" style="margin-left: 20px;margin-top: 20px;">
        <div style="width:100%;overflow: auto;">
            <input type="hidden" id="id" name="id">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="permissionName" class="layui-form-label">
                        权限名称
                    </label>
                    <div class="layui-input-block" style="width: 100%;">
                        <input type="text" id="permissionName" name="permissionName" lay-verify="required" placeholder="" value="" class="layui-input">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">父级菜单:</label>
                <div class="layui-input-block" style="position: relative;width: 293px">
                    <div class="layui-unselect layui-form-select downpanel">
                        <div class="layui-select-title">
                            <span class="layui-input layui-unselect" id="treeclass">选择栏目</span>
                            <input type="hidden" id="parentId" name="parentId">
                            <i class="layui-edge"></i>
                        </div>
                        <dl class="layui-anim layui-anim-upbit">
                            <dd>
                                <ul id="treeGrid"></ul>
                            </dd>
                        </dl>
                    </div>

                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">
                        权限类型
                    </label>
                    <div class="layui-input-block" style="width: 290px;">
                        <input type="radio" id="permissionType-menu" name="permissionType" lay-filter="type" value="0"  title="菜单" >
                        <input type="radio" id="permissionType-btn" name="permissionType" lay-filter="type" value="1"  title="按钮" >
                        <input type="radio" id="permissionType-perm" name="permissionType" lay-filter="type"  value="2" title="权限" >
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="permissionCode" class="layui-form-label">
                        权限代码
                    </label>
                    <div class="layui-input-block" style="width: 100%;">
                        <input type="text" id="permissionCode" name="permissionCode" placeholder=""  class="layui-input">
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
    layui.use(['form', 'layer', 'tree','upload'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var layer = layui.layer;
        var upload = layui.upload;

        // 修改页面页面初始化操作
        $(function () {
            if ("${isUpdatePage}" === "true") {
                $("#id").val("${permission.id}");
                $("#permissionCode").val("${permission.permissionCode}");
                $("#permissionName").val("${permission.permissionName}");
                $("#parentId").val("${permission.parentId}");
                $("#level").val("${permission.level}");
                $("#orderNum").val("${permission.orderNum}");
                $("#description").val("${permission.description}");

                var permissionType = '${permission.permissionType}';
                if (permissionType == 0) {
                    $("#permissionType-menu").prop("checked", true);
                } else if(permissionType == 1) {
                    $("#permissionType-btn").prop("checked", true);
                } else {
                    $("#permissionType-perm").prop("checked", true);
                }
                form.render();
            }
        });

        $.ajax({
            url:"/permission/getPermissionTreeGrid",
            type:"get",
            success:function(res){
                nodes = res;
                layui.tree({
                    elem: '#treeGrid',
                    nodes:nodes,
                    click: function(node){
                        console.log(node);
                        if(node.type == 3){
                            layer.msg("您选择的是权限操作,请重新选择");
                            return;
                        }else if(node.type == 1){
                            layer.msg("您选择的是按钮,不可作为父级");
                            return;
                        }
                        var $select=$($(this)[0].elem).parents(".layui-form-select");
                        $select.removeClass("layui-form-selected").find(".layui-select-title span").html(node.name).end().find("input:hidden[name='parentId']").val(node.id);

                    }
                });
            }
        });
        $(".downpanel").on("click",".layui-select-title",function(e) {
            $(".layui-form-select").not($(this).parents(".layui-form-select")).removeClass("layui-form-selected");
            $(this).parents(".downpanel").toggleClass("layui-form-selected");
            layui.stope(e);
        }).on("click","dl i",function(e) {
            layui.stope(e);
        });

        $(document).on("click",function(e) {
            $(".layui-form-select").removeClass("layui-form-selected");
        });

        $("#orderNum").focus(function(){
            layer.tips("用户菜单排序",this);
        });
        form.verify({
            orderNum: [/^[0-9]*[1-9][0-9]*$/, '请填写序号(正整数)']
        });

        form.on('radio(type)', function(data){
            if(data.value == 0){
                $("#permissionCode").val("--");
                $("#orderNum").val("");
                $("#orderNum").attr("disabled",false);
                $("#permissionCode").attr("disabled",true);
            }else{
                $("#orderNum").val("99999");
                $("#orderNum").attr("disabled",true);
                $("#permissionCode").attr("disabled",false);
            }
        });

        // 关闭按钮
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        // 监听提交
        form.on('submit(add)', function (data) {
            $.ajax({
                url: '/permission/',
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
                    window.top.layer.msg('请求失败', {icon: 5, area: ['120px', '80px'], anim: 2});
                }
            });
            return false;
        });
        form.render();
    });
</script>
</body>

</html>
