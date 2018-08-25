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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<section class="container-module">
    <section class="search-module">
        权限：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="permissionName" autocomplete="off">
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
                新增权限
            </button>

            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">ဂ</i>
            </button>
        </div>
    </section>


    <section class="table-module">
        <%--<table id="permissionList" lay-filter="permissionListTool"></table>--%>
            <div id="permissionList"></div>
    </section>
</section>

<%@include file="../../../../common/jsModule.jsp" %>
<script>
    // 回车提交表单
    document.onkeydown = function (e) {
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $('#search').click();
        }
    }
    var layout = [
        { name: '菜单名称', treeNodes: true, headerClass: 'value_col',align:'center', colClass: 'value_col', style: 'width: 10%'
        }, { name: '类型',headerClass: 'value_col',align:'center',  colClass: 'value_col', style: 'width: 10%',
            render: function(row) {
                if(row.permissionType==0){
                    return '<div class="layui-table-cell laytable-cell-1-username"  style="color:#393D49">菜单</div>'; //列渲染
                }else if(row.permissionType==1){
                    return '<div class="layui-table-cell laytable-cell-1-username"  style="color:#009688">按钮</div>'; //列渲染
                }else if(row.permissionType==2){
                    return '<div class="layui-table-cell laytable-cell-1-username"  style="color:#FF5722">权限</div>'; //列渲染
                }

            }
        }, { name: '权限代码',align:'center', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%',
            render: function(row) {
                return '<div class="layui-table-cell layui-btn-radius laytable-cell-1-username"><span class="layui-badge layui-bg-green">'+(row.permissionCode==""?'--':row.permissionCode)+'</span></div>'; //列渲染
            }
        },{ name: '权限级别',align:'center', headerClass: 'value_col', colClass: 'value_col', style: 'width: 10%',
            render: function(row) {
                if (row.level == 1) {
                    return '<div class="layui-table-cell layui-btn-radius laytable-cell-1-username"><span class="layui-badge layui-bg-cyan">'+ '一级菜单' +'</span></div>'; //列渲染
                } else if (row.level == 2) {
                    return '<div class="layui-table-cell layui-btn-radius laytable-cell-1-username"><span class="layui-badge layui-bg-cyan">'+ '二级菜单' +'</span></div>'; //列渲染
                } else {
                    return '<div class="layui-table-cell layui-btn-radius laytable-cell-1-username"><span class="layui-badge layui-bg-cyan">'+ '资源' +'</span></div>'; //列渲染
                }
            }
        },{ name: '排序号',align:'center', headerClass: 'value_col', colClass: 'value_col', style: 'width: 5%',
            render: function(row) {
                if(row.orderNum==null){
                    return "";
                }else{
                    return row.orderNum;
                }

            }
        },
        {
            name: '操作',
            headerClass: 'value_col',
            colClass: 'value_col',
            style: 'width: 20%',
            render: function(row) {
                var chil_len=row.children.length;
                var str='<a class="layui-btn layui-btn-radius layui-btn-xs" onclick="updateRow(\'' + row.id + '\')"><i class="layui-icon">&#xe642;</i>编辑</a>'; //列渲染
                if(chil_len==0){
                    str+='<a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger" onclick="deleteRow(\'' + row.id + '\')"><i class="fa fa-remove""></i> 删除</a>';
                }
                return str;
            }
        },
    ];
    var layer;
    var $;
    var flushPermissionTable;
    layui.use(['layer','table','form','tree','jquery'], function(){
        var table = layui.table;
        var form = layui.form;
        layer = layui.layer;
        $ = layui.jquery;
        var nodes;

        flushPermissionTable = function () {
            layui.jquery.ajax({
                url:"/permission/getPermissionTreeGrid",
                type:"get",
                success:function(res){
                    nodes = res;
                    layui.treeGird({
                        elem: '#permissionList',
                        nodes: nodes,
                        layout: layout
                    });
                }
            });
        };
        flushPermissionTable();

        // 监听工具条
        table.on('tool(permissionListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'edit'){ //编辑
                layer.open({
                    title : "修改权限",
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
                location.reload();
            },
            // 刷新按钮
            reload: function () {
                location.reload();
            },
            // 新增按钮
            add: function () {
                layer.open({
                    title : "添加权限",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex, //重点1
                    maxmin:true,
                    skin:'layui-layer-lan',
                    shade: 0.4,
                    area:["60%","80%"],
                    content : "loadAddPage",
                    success : function(layero, index){
                    }
                });
            },
            // 批量删除按钮
            batchDelete: function () {
                var checkStatus = table.checkStatus('permissionList')
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
                layer.confirm('确定要删除这【'+ data.length +'】条权限吗?', {icon: 3, title:'批量删除权限'}, function(index){
                    $.ajax({
                        url: "/permission/batchDelete",
                        type: "post",
                        dataType:"json",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            location.reload();
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

    function updateRow(id){
        layer.open({
            title : "修改权限",
            type : 2,
            shade: false,
            zIndex: layer.zIndex,
            maxmin:true,
            skin:'layui-layer-lan',
            shade: 0.4,
            area:["60%","80%"],
            content : 'loadUpdatePage?id=' + id,
            success : function(layero, index){
            }
        });
    }
    function deleteRow(id){
        layer.confirm("确定要删除吗",{btn:['是的','取消'],title:"提示信息",icon:2},function(){
            layer.msg('删除中', {
                icon: 16
                ,shade: 0.5
            });
            setTimeout(function(){
                $.ajax({
                    url:"/permission/"+id,
                    type:"delete",
                    success:function(res){
                        layer.closeAll("loading");
                        if(res){
                            layer.msg("删除成功")
                            location.reload();
                        }else{
                            layer.msg("删除失败")
                        }
                    },
                    error:function( XMLHttpRequest, textStatus, errorThrown) {
                        layer.closeAll("loading");
                        commonAjax.error(XMLHttpRequest,layer);
                    },
                })
            },500);

        });
    }
</script>

<style>


</style>
</html>
