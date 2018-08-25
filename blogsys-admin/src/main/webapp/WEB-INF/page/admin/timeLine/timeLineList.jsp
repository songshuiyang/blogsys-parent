<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib.jsp" %>
<%@include file="../../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>时间线管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<section class="container-module">
    <section class="search-module">
        内容：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="value" autocomplete="off">
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
                新增记事
            </button>

            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="batchDelete">
                <i class="layui-icon">&#xe640;</i>
                批量删除记事
            </button>
            
            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">&#x1002;</i>
            </button>
        </div>
        
    </section>

    <section class="table-module">
        <table id="timeLineList" lay-filter="timeLineListTool"></table>
    </section>
</section>

<%@include file="../../../../common/jsModule.jsp" %>

<%--列表操作模板--%>
<script type="text/html" id="toolTemplete">
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm" lay-event="edit"><i class="layui-icon">&#xe642;</i>编辑</a>
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-warm" lay-event="editDate"><i class="layui-icon">&#xe642;</i>修改时间点</a>
    <%--<a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-normal" lay-event="detail"><i class="layui-icon">&#xe60b;</i>详情</a>--%>
    {{#  if(d.enable == 1){ }}
    <a class="layui-btn layui-btn-radius layui-btn-xs layui-btn-danger " lay-event="delete"><i class="layui-icon">&#xe640;</i>删除</a>
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
            id: 'timeLineList',
            elem: '#timeLineList',
            request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            url: '/timeLine/list',
            limits:[5,10,15,30,50],
            page: true,
            cols: [[ //表头
                 {type: 'checkbox'}
                ,{field: 'id', title: 'ID', sort: true}
                ,{field: 'value', title: '记事',sort: true,style: 'background-color: #00c0ef; color: #fff'}
                ,{field: 'value1', title: '时间',sort: true}
                ,{field: 'createdBy', title: '创建人',sort: true}
                ,{field: 'lastModifiedDate', title: '最后修改时间',sort: true}
                ,{title:'操作', align:'center', toolbar: '#toolTemplete'}
            ]]
        });
        // 监听工具条
        table.on('tool(timeLineListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'delete'){ //删除
                layer.confirm('确定要删除该【 ' + data.value + ' 】记事?', {icon: 3, title:'删除记事'}, function(index){
                    //do something
                    $.ajax({
                        url: "/timeLine/" + data.id,
                        type: "delete",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('timeLineList');
                        }
                    });
                    layer.close(index);
                });
            } else if(layEvent === 'edit'){ //编辑
                layer.prompt({
                    formType: 2, //输入框类型，支持0（文本）默认1（密码）2（多行文本）
                    value: data.value,
                    maxlength: 100000, //可输入文本的最大长度，默认500
                    title: '请输入新的记事名，并按确认',
                    area: ['800px', '500px'] //自定义文本域宽高
                }, function(value, index, elem){
                    $.ajax({
                        url: "/timeLine/add",
                        type: "post",
                        dataType:"json",
                        data: {id:data.id,value: value},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('timeLineList');
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
            } else if (layEvent === 'editDate'){
                layer.prompt({
                    formType: 0, //输入框类型，支持0（文本）默认1（密码）2（多行文本）
                    value: data.value1,
                    title: '请输入新的记事名，并按确认',
                }, function(value, index, elem){
                    $.ajax({
                        url: "/timeLine/add",
                        type: "post",
                        dataType:"json",
                        data: {id:data.id,value1: value},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('timeLineList');
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
        });
        var active = {
            // 查询按钮
            select: function () {
                var value = $('#value').val();
                console.info(username);
                table.reload('timeLineList', {
                    where: {
                        value: value
                    }
                });
            },
            // 刷新按钮
            reload: function () {
                var value = $('#value').val();
                console.info(username);
                table.reload('timeLineList', {
                    where: {
                        value: value
                    }
                });
            },
            // 新增按钮
            add: function () {
                //例子2
                layer.prompt({
                    formType: 2, //输入框类型，支持0（文本）默认1（密码）2（多行文本）
                    maxlength: 1000000, //可输入文本的最大长度，默认500
                    title: '请输入新的记事名，并按确认',
                    area: ['800px', '500px'] //自定义文本域宽高
                }, function(value, index, elem){
                    $.ajax({
                        url: "/timeLine/add",
                        type: "post",
                        dataType:"json",
                        data: {value: value},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('timeLineList');
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
                var checkStatus = table.checkStatus('timeLineList')
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
                layer.confirm('确定要删除这【'+ data.length +'】条记事吗?', {icon: 3, title:'批量删除记事'}, function(index){
                    $.ajax({
                        url: "/timeLine/batchDelete",
                        type: "post",
                        dataType:"json",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('timeLineList');
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
