<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../../common/taglib.jsp" %>
<%@include file="../../../../common/cssModule.jsp" %>
<section class="table-module" style="margin: 10px;">
    <table id="roleDetailList"></table>

    <button class="layui-btn layui-btn-radius layui-btn-normal" style="margin-left:60px;" id="roleDetailCommit">
        <i class="layui-icon">&#xe618;</i>提交
    </button>
</section>
<%@include file="../../../../common/jsModule.jsp" %>
<script >
    layui.use(['table','layer','form'], function(){
        var table = layui.table;
        var layer=layui.layer;
        var $=layui.jquery;
        var loadIndex  = layer.load(0, {shade: [0.1,'#000']}); // 加载效果

        table.render({
            elem: '#roleDetailList'
            ,request: {
                pageName: 'pageIndex', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            ,url: '/role/getUsersByRoleId?roleId='+${roleId} //数据接口
            ,cols: [[ //表头
                 {type: 'checkbox',width:"10%",}
                ,{type: 'numbers',width:"10%",}
                ,{field: 'userName', title:'用户名',}
                ,{field: 'nickName', title: '昵称',}
            ]],
            page:false,
            done: function(res, curr, count){
                layer.close(loadIndex);
            }
        });
        $('#roleDetailCommit').click(function () {
            var checkStatus = table.checkStatus('roleDetailList');
            var data = checkStatus.data;
            var userIds = "";
            $.each(data,function(k,v){
                userIds += v.id+ ",";
            });
            // 去最后的,
            userIds=userIds.substring(0,userIds.lastIndexOf(","));
            $.ajax({
                url:'/role/updateUserRoleRelationship',
                data:{"roleId":"${roleId}","userIds":userIds},
                dataType:"json",
                type:"post",
                success:function(res){
                    if(res.code == 1 ){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        window.parent.layui.table.reload('roleList');
                        window.top.layer.msg("角色分配成功", {icon: 6,area: ['120px', '80px'], anim: 2});
//                        layer.alert("角色分配成功");
//
//                        setTimeout(function () {
//
//                        },1500);
                    }else{
                        layer.alert("角色分配失败");
                    }
                },
                error:function( XMLHttpRequest, textStatus, errorThrown) {
                    layer.alert("系统异常",{icon: 5});
                },
            })
        });

    });
</script>