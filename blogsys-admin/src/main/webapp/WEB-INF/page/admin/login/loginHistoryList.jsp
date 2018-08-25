<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录历史记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<style>
    .table-module .layui-icon {
        margin-top: 6px;
    }
</style>
<section class="container-module">
    <section class="layui-form search-module" class="layui-form">
        用户名：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="username" autocomplete="off">
        </div>
        <%--设备类型：--%>
        <%--<div class="layui-inline">--%>
            <%--<select id="deviceType" name="deviceType" lay-filter="selFilter" lay-search>--%>
                <%--<option value=""></option>--%>
                <%--<option value="Com">电脑</option>--%>
                <%--<option value="Mob">手机</option>--%>
            <%--</select>--%>
        <%--</div>--%>
        <%--搜索按钮--%>
        <button class="layui-btn layui-btn-radius layui-btn-normal" id="search" data-type="select">
            <i class="layui-icon"></i>
        </button>



    </section>

    <section class="functional-module">
        <div class="layui-btn-container">
            <%--列表刷新按钮--%>
            <button class="layui-btn layui-btn-radius layui-btn-normal" data-type="reload"  id="refresh" style="float: right;">
                <i class="layui-icon">ဂ</i>
            </button>
        </div>
    </section>
    <section class="table-module">
        <table id="loginHistoryList" lay-filter="loginHistoryListTool"></table>
    </section>
</section>

<%@include file="../../../../common/jsModule.jsp" %>

<script type="text/html" id="statusTemplate">
    {{#  if(d.status == 0){ }}
    <span>登录成功</span>
    {{#  } else { }}
    <span>登录失败</span>
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
            id: 'loginHistoryList',
            elem: '#loginHistoryList',
            request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            url: '/loginHistory/list',
            limits:[5,10,15,30,50],
            page: true,
            cols: [[ //表头
                {field: 'username',title: '用户名',width:"6%",sort: true,style: 'background-color: #00c0ef; color: #fff'}
                ,{field: 'createdDate', title: '登录时间',width:"15%", sort: true, }
                ,{field: 'status', title: '状态', width:"6%",sort: true, templet:'#statusTemplate'}
                ,{field: 'ip', title: 'IP',width:"10%", sort: true, }
                ,{field: 'ipLocation', title: 'IP地址', width:"10%",sort: true}
                ,{field: 'deviceType', title: '设备类型',width:"6%", sort: true}
                ,{field: 'deviceSystem', title: '设备系统', width:"10%",sort: true}
                ,{field: 'browser', title: '浏览器', width:"10%",sort: true}
                ,{field: 'useragent', title: '代理', width:"19%",sort: true}
                ,{field: 'remarks', title: '备注', width:"8%",sort: true}
            ]]
        });
        // 监听工具条
        table.on('tool(loginHistoryListTool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象

            if(layEvent === 'detail'){ //查看
                layer.open({
                    title : "登录详情",
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
            }
        });
        var active = {
            // 查询按钮
            select: function () {
                var username = $('#username').val();
                var deviceType = $('#deviceType').val();
                console.info(username);
                table.reload('loginHistoryList', {
                    where: {
                        username: username,
                        deviceType: deviceType
                    }
                });
            },
            // 刷新按钮
            reload: function () {
                var username = $('#username').val();
                var nickname = $('#nickname').val();
                table.reload('loginHistoryList', {
                    where: {
                        username: username,
                        nickname: nickname
                    }
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
