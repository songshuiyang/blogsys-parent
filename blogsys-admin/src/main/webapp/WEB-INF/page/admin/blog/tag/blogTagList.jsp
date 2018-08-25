<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib.jsp" %>
<%@include file="../../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>博客标签管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<section class="container-module">
    <section class="search-module">
        标签：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="tag" autocomplete="off">
        </div>
        <%--搜索按钮--%>
        <button class="layui-btn layui-btn-radius layui-btn-normal" id="search" data-type="select">
            <i class="layui-icon"></i>
        </button>



    </section>

    <section class="functional-module">
        <div class="layui-btn-container">

            <shiro:hasPermission name="blog:blogTag:add">
                <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="add">
                    <i class="layui-icon">&#xe608;</i>
                    新增标签
                </button>

            </shiro:hasPermission>
            <shiro:hasPermission name="blog:blogTag:BatchDelete">
                <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="batchDelete">
                    <i class="layui-icon">&#xe640;</i>
                    批量删除标签
                </button>
            </shiro:hasPermission>

            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">&#x1002;</i>
            </button>
        </div>
        
    </section>

    <section class="table-module">
        <table id="blogTagList" lay-filter="blogTagListTool"></table>
    </section>
</section>

<%@include file="../../../../../common/jsModule.jsp" %>

<%--列表操作模板--%>
<script type="text/html" id="toolTemplete">
    <shiro:hasPermission name="blog:blogTag:edit">
        <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
    </shiro:hasPermission>
    {{#  if(d.status == 0){ }}
    <shiro:hasPermission name="blog:blogTag:edit">
        <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-radius" lay-event="publish"><i class="layui-icon">&#xe609;</i>发布</a>
    </shiro:hasPermission>
    {{#  } }}
    <%--<a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-normal" lay-event="detail"><i class="layui-icon">&#xe60b;</i>详情</a>--%>
    {{#  if(d.enable == 1){ }}
    <shiro:hasPermission name="blog:blogTag:delete">
        <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger " lay-event="delete"><i class="layui-icon">&#xe640;</i>删除</a>
    </shiro:hasPermission>
    {{#  } }}
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
            id: 'blogTagList',
            elem: '#blogTagList',
            request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            url: '/blogTag/list',
            limits:[5,10,15,30,50],
            page: true,
            cols: [[ //表头
                 {type: 'checkbox'}
                ,{field: 'id', title: 'ID', sort: true}
                ,{field: 'name', title: '标签',sort: true,style: 'background-color: #00c0ef; color: #fff'}
                ,{field: 'createdBy', title: '创建人',sort: true}
                ,{field: 'lastModifiedDate', title: '最后修改时间',sort: true}
                ,{field: 'statusAlias', title: '状态',sort: true}
                ,{title:'操作', align:'center', toolbar: '#toolTemplete'}
            ]]
        });
        // 监听工具条
        table.on('tool(blogTagListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                layer.open({
                    title : "标签详情",
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex,
                    maxmin:true,
                    area:["100%","100%"],
                    content : 'loadDetailPage?id=' + data.id,
                    success : function(layero, index){
                        layer.setTop(layero);
                        setTimeout(function () {
                            layer.tips('这里是关闭窗口', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500)
                    }
                });
            } else if(layEvent === 'delete'){ //删除
                layer.confirm('确定要删除该【 ' + data.name + ' 】标签?', {icon: 3, title:'删除标签'}, function(index){
                    //do something
                    $.ajax({
                        url: "/blogTag/" + data.id,
                        type: "delete",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogTagList');
                        }
                    });
                    layer.close(index);
                });
            } else if(layEvent === 'edit'){ //编辑
                layer.prompt({
                    formType: 0, //输入框类型，支持0（文本）默认1（密码）2（多行文本）
                    value: data.name,
                    maxlength: 10, //可输入文本的最大长度，默认500
                    title: '请输入新的标签名，并按确认'
                }, function(value, index, elem){
                    $.ajax({
                        url: "/blogTag/add",
                        type: "post",
                        dataType:"json",
                        data: {id:data.id,name: value},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogTagList');
                        },
                        error: function () {
                            layer.alert("系统异常",{icon: 5});
                        },
                        complete: function () {
                            layer.close('loading');
                        }
                    })
                    layer.close(index);
                });
            }
        });
        var active = {
            // 查询按钮
            select: function () {
                var username = $('#username').val();
                var nickname = $('#nickname').val();
                console.info(username);
                table.reload('blogTagList', {
                    where: {
                        username: username,
                        nickname: nickname
                    }
                });
            },
            // 刷新按钮
            reload: function () {
                var username = $('#username').val();
                var nickname = $('#nickname').val();
                table.reload('blogTagList', {
                    where: {
                        username: username,
                        nickname: nickname
                    }
                });
            },
            // 新增按钮
            add: function () {
                //例子2
                layer.prompt({
                    formType: 0, //输入框类型，支持0（文本）默认1（密码）2（多行文本）
                    maxlength: 10, //可输入文本的最大长度，默认500
                    title: '请输入新的标签名，并按确认'
                }, function(value, index, elem){
                    $.ajax({
                        url: "/blogTag/add",
                        type: "post",
                        dataType:"json",
                        data: {name: value},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogTagList');
                        },
                        error: function () {
                            layer.alert("系统异常",{icon: 5});
                        },
                        complete: function () {
                            layer.close('loading');
                        }
                    })
                    layer.close(index);
                });
            },
            // 批量删除按钮
            batchDelete: function () {
                var checkStatus = table.checkStatus('blogTagList')
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
                layer.confirm('确定要删除这【'+ data.length +'】条标签吗?', {icon: 3, title:'批量删除标签'}, function(index){
                    $.ajax({
                        url: "/blogTag/batchDelete",
                        type: "post",
                        dataType:"json",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('blogTagList');
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
