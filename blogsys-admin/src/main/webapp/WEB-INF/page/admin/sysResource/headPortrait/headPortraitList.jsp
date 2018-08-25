<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../../common/taglib.jsp" %>
<%@include file="../../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>随机头像管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<section class="container-module">
    <section class="search-module">
        
    </section>

    <section class="functional-module">
        <div class="layui-btn-container">
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="add">
                <i class="layui-icon">&#xe608;</i>
                新增头像
            </button>

            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="batchDelete">
                <i class="layui-icon">&#xe640;</i>
                批量删除头像
            </button>

            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">&#x1002;</i>
            </button>
        </div>

    </section>

    <section class="table-module">
        <table id="headPortraitList" lay-filter="headPortraitListTool"></table>
    </section>
</section>

<%@include file="../../../../../common/jsModule.jsp" %>
<script type="text/html" id="imageTemplate">
    {{#  if(d.value != null){ }}
    <div>
        <a href="#"><img src="{{d.value}}" style="width:35px;height:35px;border-radius:35px" ></a>
    </div>
    {{#  } else { }}
    <span></span>
    {{#  } }}
</script>
<%--列表操作模板--%>
<script type="text/html" id="toolTemplete">
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
            id: 'headPortraitList',
            elem: '#headPortraitList',
            request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            url: '/headPortrait/list',
            limits:[5,10,15,30,50],
            page: true,
            cols: [[ //表头
                {type: 'checkbox'}
                ,{field: 'id', title: 'ID', sort: true}
                ,{field: 'value', title: '头像',sort: true ,event: 'imageEvent',templet:'#imageTemplate'}
                ,{field: 'value1', title: '文件名',sort: true}
                ,{field: 'value2', title: '链接',sort: true}
                ,{field: 'createdBy', title: '创建人',sort: true}
                ,{field: 'lastModifiedDate', title: '最后修改时间',sort: true}
                ,{field: 'enableAlias', title: '状态',sort: true}
                ,{title:'操作', align:'center', toolbar: '#toolTemplete'}
            ]]
        });
        // 监听工具条
        table.on('tool(headPortraitListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'delete'){ //删除
                layer.confirm('确定要删除该【 ' + data.value + ' 】头像?', {icon: 3, title:'删除头像'}, function(index){
                    //do something
                    $.ajax({
                        url: "/sysResouce/" + data.id,
                        type: "delete",
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('headPortraitList');
                        }
                    });
                    layer.close(index);
                });
            } else if(obj.event === 'imageEvent'){ // 图片展示
                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    skin: 'layui-layer-nobg', //没有背景色
                    area:["40%","65%"],
                    shadeClose: true,
                    content: '<div style="height: 600px;width: 600px;"><img src="' + data.value + '"><div>'
                });
            }
        });
        var active = {
            // 查询按钮
            select: function () {
                var username = $('#username').val();
                var nickname = $('#nickname').val();
                console.info(username);
                table.reload('headPortraitList', {
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
                table.reload('headPortraitList', {
                    where: {
                        username: username,
                        nickname: nickname
                    }
                });
            },
            // 新增按钮
            add: function () {
                layer.open({
                    title : "新增头像",
                    id: 'headPortrait-add',
                    type : 2,
                    shade: false,
                    zIndex: layer.zIndex,
                    maxmin:true,
                    area:["65%","80%"],
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
                var checkStatus = table.checkStatus('headPortraitList')
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
                layer.confirm('确定要删除这【'+ data.length +'】条头像吗?', {icon: 3, title:'批量删除头像'}, function(index){
                    $.ajax({
                        url: "/sysResouce/batchDelete",
                        type: "post",
                        dataType:"json",
                        data: {ids: ids},
                        success: function (msg) {
                            layer.msg('操作成功', {icon: 6});
                            layui.table.reload('headPortraitList');
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
