layui.use(['element', 'layer', 'util', 'form','carousel','flow'], function () {
    var carousel = layui.carousel;
    var layer = layui.layer;
    var $ = layui.jquery;
    var flow = layui.flow;
    var count=0;
    // 轮播图
    carousel.render({
        elem: '#carousel'
        ,width: '100%' //设置容器宽度
        ,arrow: 'always' //始终显示箭头
        //,anim: 'updown' //切换动画方式
    });

    var grid={
        /**
         * 加载文章数据
         * @param type  0:不带参数 1:文章分类 2: 文章标签
         * @param value 参数值
         * @param limit 页数
         * @param pageNumber 第几页
         */
        getBlog:function(type,value,limit,pageNumber){
            $("#blog-main-left").empty();
            flow.load({
                elem: '#blog-main-left', //指定列表容器
                isAuto: true,
                end: "<a href='#'>别滑了,木有了<i class='layui-icon' style='font-size:15px;'>&#xe60c;</i></a>",
                done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                    setTimeout(function () {
                        //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                        var url="blogArticles/front/list?";
                        if (type == 0) {
                            url+="pageSize="+limit+"&pageIndex="+page;
                        } else if (type == 1) {
                            url+="pageSize="+limit+"&pageIndex="+page+"&category="+value;
                        } else if (type == 2) {
                            url+="pageSize="+limit+"&pageIndex="+page+"&tag="+value;
                        }
                        $.get(url, function (res) {
                            //假设你的列表返回在data集合中
                            var lis = [];
                            var blog = "";
                            var pageCount = res.pageCount // 总页数
                            var pageNum = res.pageNum     // 当前页数
                            layui.each(res.data, function (index, item) {
                                if (index == 0) {
                                    count = res.count;
                                }
                                var tagList = "";       // 文章标签list
                                var articlesType = "";  // 文章分类
                                if (item.count != 0) {
                                    // 筛选标签
                                    layui.each(item.blogTagList, function (index, tagItem) {
                                        tagList = tagList + tagItem.name + " "
                                    });
                                    // 筛选文章类型
                                    if (item.typeAlias == "精贴") {
                                        articlesType = '<span style="color: red;font-weight:bold">' +
                                                            '<i class="iconfont icon-jingpin"></i>&nbsp;' + item.typeAlias +
                                                        '</span>'
                                    }
                                    if (item.typeAlias == "原创") {
                                        articlesType = '<span style="color: blue;font-weight:bold">' +
                                                            '<i class="iconfont icon-yuanchuang"></i>&nbsp;' + item.typeAlias +
                                                        '</span>'
                                    }
                                    if (item.typeAlias == "转载") {
                                        articlesType = '<span style="color: darkmagenta;font-weight:bold">' +
                                                            '<i class="iconfont icon-zhuanzhai"></i>&nbsp;' + item.typeAlias +
                                                        '</span>'
                                    }
                                    // 博客内容div
                                    blog =
                                        '<div class="article shadow animated zoomIn">' +
                                            '<div class="article-left animated rotateInDownLeft">' +
                                                '<img src="'+ item.coverImage +'"  alt="' + item.title + '"/>' +
                                            '</div>' +
                                            '<div class="article-right animated bounceInLeft">' +
                                                '<div class="article-title">' +
                                                    '<a class="hvr-float-shadow" href="blogArticles/front/' + item.id + '">' + item.title + '</a>&nbsp;&nbsp;&nbsp;' +  articlesType +
                                                '</div>' +
                                                '<div class="article-abstract">'
                                                    + item.outline +
                                                '</div>' +
                                            '</div>' +
                                            '<div class="clear">' +
                                            '</div>' +
                                            '<div class="article-footer">' +
                                                '<span style="color: blue">' +
                                                    '<i class="fa fa-clock-o"></i>&nbsp;' +
                                                    '<a href="#" style="color: blue" title="创建时间"> ' + item.createdDate + '</a>' +
                                                '</span>'+
                                                '<span class="article-author" style="color: blue">' +
                                                    '<i class="fa fa-user"></i>&nbsp;' +
                                                    '<a href="#" style="color: blue" title="作者"> ' + item.createdBy + '</a>' +
                                                '</span>' +

                                                '<span style="color: #dd483f">' +
                                                    '<i class="iconfont icon-kinds"></i>&nbsp;' +
                                                    '<a href="#" style="color: #dd483f" title="文章分类"> ' + item.category + '</a>' +
                                                '</span>' +
                                                '<span style="color: green">' +
                                                    '<i class="iconfont icon-biaoqian"></i>&nbsp;' +
                                                    '<a href="#" style="color: green"  title="文章标签">' + tagList + '</a>' +
                                                '</span>' +

                                                '<span class="article-viewinfo" style="color: blue">' +
                                                    '<i class="fa fa-thumbs-o-up"></i>&nbsp;' +
                                                    '<a href="#" style="color: blue" title="点赞数"> ' + item.satisfactoryNum + '</a>' +
                                                '</span>' +
                                                '<span class="article-viewinfo" style="color: blue">' +
                                                    '<i class="fa fa-eye"></i>&nbsp;' +
                                                    '<a href="#" style="color: blue" title="阅读数"> ' + item.hitsNum + '</a>' +
                                                '</span>' +
                                                '<span class="article-viewinfo" style="color: blue">' +
                                                    '<i class="fa fa-commenting"></i>&nbsp;'+
                                                    '<a href="#" style="color: blue" title="评论数"> ' + item.commentNum + '</a>' +
                                                '</span>' +
                                            '</div>' +
                                        '</div>';
                                    lis.push('<li>' + blog + '</li>');
                                }
                            });
                            next(lis.join(''), pageNum < pageCount);
                        });
                    }, 1);

                }
            });

         }
    }

    $(function () {
        // 加载文章(默认加载前四篇)
        grid.getBlog(0,null,4,1);
        // 播放公告
        playAnnouncement(3000);
    });

    // 加载热文排行
    $.get('blogArticles/front/hotArticles?pageSize=10' , function (res) {
        layui.each(res.data, function (index, item) {
            if (index <= 10) {
                $("#blogsrank").append("<li><i class=\"iconfont icon-remen\" style='color: red;margin-left: -28px'></i><a class='animated lightSpeedIn hvr-bob' href=\"blogArticles/front/" + item.id + "\" target='_blank'>" + item.title + "</a></li>");
            }
        });
    });
    // 加载栏目类型
    $.get('blogCategory/front/categoryList', function (res) {
        layui.each(res.data, function (index, item) {
            $("#blogtype").append("<div typeid=" + item.value + " class=\"set_8_button4 animated lightSpeedIn outline\" href=\"#\"><i class=\"iconfont icon-kinds\" style=\"color:#dd483f\"></i>&nbsp;" + item.value + "<span class=\"lines\"></span></div>");
        });
        $("#blogtype .set_8_button4").each(function(k,v){
            $(v).on("click",function(){
                var tid=$(this).attr("typeid");
                grid.getBlog(1,tid,4,1);
            });
        });
    });

    // 加载标签
    $.get('blogTag/front/tagList', function (res) {
        layui.each(res.data, function (index, item) {
            $("#blogtag").append('<a tagid="'+ item.id  + '"class="layui-btn layui-btn-radius layui-btn-xs layui-btn-primary tag hvr-float-shadow" style="color: green"><i class="iconfont icon-biaoqian"></i>'+ item.name +'</a>');
        });
        $("#blogtag .tag").each(function(k,v){
            $(v).on("click",function(){
                var tagid=$(this).attr("tagid");
                grid.getBlog(2,tagid,4,1);
            });
        });
    });

    // 播放公告
    function playAnnouncement(interval) {
        var index = 0;
        var $announcement = $('.home-tips-container>span');
        //自动轮换
        setInterval(function () {
            index++;    //下标更新
            if (index >= $announcement.length) {
                index = 0;
            }
            $announcement.eq(index).stop(true, true).fadeIn().siblings('span').fadeOut();  //下标对应的图片显示，同辈元素隐藏
        }, interval);
    }
});