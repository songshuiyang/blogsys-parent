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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<section class="container-module">
    <section class="search-module">
        角色：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="roleName" autocomplete="off">
        </div>
        角色code：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="roleCode" autocomplete="off">
        </div>
        <%--搜索按钮--%>
        <button class="layui-btn layui-btn-radius layui-btn-normal" id="search" data-type="select">
            <i class="layui-icon"></i>
        </button>
    </section>

    <section class="functional-module">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="add">
                <i class="layui-icon">&#xe608;</i>
                新增角色
            </button>

            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="batchDelete">
                <i class="layui-icon">&#xe640;</i>
                删除角色
            </button>

            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">ဂ</i>
            </button>
        </div>
    </section>


    <section class="table-module">
        <table id="roleList" lay-filter="roleListTool"></table>
    </section>
</section>

<%@include file="../../../../common/jsModule.jsp" %>
<%--列表操作模板--%>
<script type="text/html" id="toolTemplete">
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
    <a class="layui-btn layui-btn-radius layui-btn-xs" lay-event="assignPermission"><i class="layui-icon">&#xe609;</i>分配权限</a>
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-normal" lay-event="assignUser"><i class="layui-icon">&#xe60b;</i>分配用户</a>
</script>
<script>
    // 回车提交表单
    document.onkeydown = function (e) {
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $('#search').click();
        }
    }
    layui.use(['table','layer','form'], function(){
        var table = layui.table;
        var layer=layui.layer;
        var form =layui.form;
        var $=layui.jquery;
        // 执行表格渲染
        table.render({
            id: 'roleList',
            elem: '#roleList',
            request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            url: '/role/list',
            limits:[5,10,15,30,50],
            page: true,
            cols: [[ //表头
                 {type: 'checkbox'}
                ,{field: 'id', title: 'ID',width:"4%", sort: true}
                ,{field: 'roleName', title: '角色', sort: true, style: 'background-color: #00c0ef; color: #fff'}
                ,{field: 'roleCode', title: '角色code', sort: true}
                ,{field: 'roleDescribe', title: '描述', sort: true}
                ,{field: 'createdBy', title: '创建人',width:"8%", sort: true}
                ,{field: 'createdDate', title: '创建时间',width:"8%",sort: true}
                ,{field: 'remarks', title: '备注',width:"8%", sort: true}
                ,{title:'操作', align:'center',toolbar: '#toolTemplete'}
            ]]
        });
        // 监听工具条
        table.on('tool(roleListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'assignUser'){ // 分配用户
                layer.open({
                    title : "分配用户",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex,
                    maxmin:true,
                    skin:'layui-layer-lan',
                    shade: 0.4,
                    area:["60%","80%"],
                    content : 'loadAssignUserPage?id=' + data.id,
                    success : function(layero, index){
                    }
                });
            } else if(layEvent === 'assignPermission') { // 分配权限
                layer.open({
                    id: 'assign-permission',
                    type: 2,
                    area: ['650px', '600px'],
                    fix: false,
                    skin:"layui-layer-molv",
                    maxmin: true,
                    shadeClose: true,
                    shade: 0.4,
                    title: "分配权限",
                    content : 'loadAssignPermissionPage?id=' + data.id,
                    success : function(layero, index){
                    }
                });
            } else if(layEvent === 'delete'){ //删除
                layer.confirm('确定要删除该【 ' + data.roleName + ' 】角色?', {icon: 3, title:'删除角色'}, function(index){
                    //do something
                    $.ajax({
                        url: "/role/" + data.id,
                        type: "delete",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('roleList');
                        }
                    });
                    layer.close(index);
                });
            } else if(layEvent === 'edit'){ //编辑
                layer.open({
                    title : "修改角色",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex,
                    maxmin:true,
                    skin:'layui-layer-lan',
                    shade: 0.4,
                    area:["60%","80%"],
                    content : 'loadUpdatePage?id=' + data.id,
                    success : function(layero, index){
                    }
                });
            }
        });
        var active = {
            // 查询按钮
            select: function () {
                var roleName = $('#roleName').val();
                var roleCode = $('#roleCode').val();
                console.info(username);
                table.reload('roleList', {
                    where: {
                        roleName: roleName,
                        roleCode: roleCode
                    }
                });
            },
            // 刷新按钮
            reload: function () {
                var roleName = $('#roleName').val();
                var roleCode = $('#roleCode').val();
                table.reload('roleList', {
                    where: {
                        roleName: roleName,
                        roleCode: roleCode
                    }
                });
            },
            // 新增按钮
            add: function () {
                layer.open({
                    title : "添加角色",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex, //重点1
                    skin:'layui-layer-lan',
                    shade: 0.4,
                    area:["60%","80%"],
                    content : "loadAddPage",
                    success : function(layero, index){
                        layer.setTop(layero); //重点2
                        setTimeout(function () {
                            layer.tips('这里是关闭窗口', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500)
                    }
                });
            },
            // 批量删除按钮
            batchDelete: function () {
                var checkStatus = table.checkStatus('roleList')
                var data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg('至少要选择一条记录吧...');
                    return false;
                }
                var ids = "";
                for (var i = 0; i< data.length; i++) {
                    if (i == 0) {
                        ids = ids + data[i].id;
                    } else {
                        ids = ids + "," + data[i].id ;
                    }
                }
                layer.confirm('确定要删除这【'+ data.length +'】条角色吗?', {icon: 3, title:'批量删除角色'}, function(index){
                    $.ajax({
                        url: "/role/batchDelete",
                        type: "post",
                        dataType:"json",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('roleList');
                        },
                        error: function () {
                            layer.alert("系统异常",{icon: 5});
                        },
                        complete: function () {
                            layer.close('loading');
                        }
                    });
                    layer.close(index);
                });
            }

        };
        // 监控带有layui-btn类的元素, 并触发相应的方法
        $('.layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>

<style>


</style>
</html>
