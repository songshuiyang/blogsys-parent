<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib.jsp" %>
<%@include file="../../../common/cssModule.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>展示面板</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <style>
        .flexstyle {
            display: flex;
            justify-content: space-between;
        }
    </style>
</head>
<section class="container-module">
    <section class="flexstyle">
        <div style="width: 50%;">
            <%--文章分类--%>
            <div id="blog-articles-type-pie" style="height: 500px; width: 100%; "></div>

        </div>
        <div style="width: 50%;">
            <%--文章类别--%>
            <div id="blog-articles-category-pie" style="height: 500px; width: 100%; "></div>

        </div>
    </section>

    <section class="flexstyle">
        <div style="width: 100%;">
            <%--文章日迹表--%>
            <div id="blog-articles-calendar" style="height: 500px;width: 100%;"></div>
        </div>
    </section>

    <section class="flexstyle">
        <div style="width: 100%;">
            <%--每月发文章数--%>
            <div id="blog-articles-monthlyNum-bar" style="height: 500px;width: 100%;"></div>
        </div>
    </section>

    <section>
        <div style="width: 100%;height: 750px;">
            <!-- 用户登录地地域分布图 -->
            <div id="address-map-chart" style="height: 700px; width: 100%; "></div>
        </div>
    </section>
</section>

<%@include file="../../../common/jsModule.jsp" %>
<script src="${ctx}static/plugins/echarts/echarts.min.js"></script>
<script src="${ctx}static/plugins/echarts/area.js"></script>
<script>
    initAddressMapChartInfo();
    initBlogArticlesTypePieInfo();
    initBlogArticlesCategoryPieInfo();
    initBlogArticlesMonthlyNumBarInfo();
    initBlogArticlesCalendarInfo();

    var blogArticlesTypeList = [];
    // 初始化博客分类
    function initBlogArticlesTypePieInfo () {
        $.ajax({
            url: "/displayPanel/getBlogArticlesType",
            type: "get",
            success: function (res) {
                blogArticlesTypeList = res
                initBlogArticlesTypePie();
            }
        });
    }
    function initBlogArticlesTypePie() {
        var blogArticlesTypePie = echarts.init(document.getElementById('blog-articles-type-pie'));
        blogArticlesTypePie.setOption({
            backgroundColor: '#fff',

            title: {
                text: '博客分类',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#000'
                }
            },
            tooltip : {
                trigger: 'item',
                formatter: "{b} : {c} 篇({d}%)"
            },
            series : [
                {
                    name:'博客分类',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data: blogArticlesTypeList.sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'radius',
                    label: {
                        normal: {
                            textStyle: {
                                color: '#00'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#c23531',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        });
    }

    var blogArticlesCategoryList = [];
    var blogArticlesCategoryLegendList = [];
    // 初始化博客归档
    function initBlogArticlesCategoryPieInfo () {
        $.ajax({
            url: "/displayPanel/getBlogArticlesCategory",
            type: "get",
            success: function (res) {
                blogArticlesCategoryList = res;
                for (var i =0; i< blogArticlesCategoryList.length; i++) {
                    blogArticlesCategoryLegendList.push(blogArticlesCategoryList[i].name);
                }
                initBlogArticlesCategoryPie();
            }
        });
    }
    function initBlogArticlesCategoryPie() {
        var blogArticlesCategoryPie = echarts.init(document.getElementById('blog-articles-category-pie'));
        blogArticlesCategoryPie.setOption({
            title: {
                text: '博客归档',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#000'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} 篇 ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data:blogArticlesCategoryLegendList
            },
            series: [
                {
                    name:'博客归档',
                    type:'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    label: {
                        normal: {
                            textStyle: {
                                color: '#000'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: true
                        }
                    },
                    data:blogArticlesCategoryList
                }
            ]
        });
    }

    var blogArticlesMonthList = [];
    var blogArticlesMonthlyNumList =[];
    // 文章月均数统计
    function initBlogArticlesMonthlyNumBarInfo () {
        $.ajax({
            url: "/displayPanel/getBlogArticlesMonthlyNum?yearNum=2018",
            type: "get",
            success: function (res) {
                for (var i= 0;i<res.length;i++) {
                    blogArticlesMonthList.push(res[i].name);
                    blogArticlesMonthlyNumList.push(res[i].value);
                }
                initBlogArticlesMonthlyNumBar();
            }
        });
    }
    function initBlogArticlesMonthlyNumBar() {
        var blogArticlesMonthlyNumBar = echarts.init(document.getElementById('blog-articles-monthlyNum-bar'));
        blogArticlesMonthlyNumBar.setOption({
            title: {
                text: '博客月均数统计',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#000'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} 篇"
            },
            legend: {
                data:['数量']
            },
            itemStyle:{
                normal:{
                    color:'#00c0ef'
                }
            },
            barMaxWidth: '100',
            xAxis: {
                data: blogArticlesMonthList
            },
            yAxis: {},
            series: [{
                name: '文章数',
                type: 'bar',
                data: blogArticlesMonthlyNumList
            }]
        });

    }


    var calendarData = [];
    // 文章日迹表统计
    function initBlogArticlesCalendarInfo () {
        $.ajax({
            url: "/displayPanel/getblogArticlesCalendar",
            type: "get",
            success: function (res) {
                calendarData = res;
                // 初始化地图
                initBlogArticlesCalendar();
            }
        });
    }
    function initBlogArticlesCalendar() {

        var blogArticlesCalendar = echarts.init(document.getElementById('blog-articles-calendar'));
        blogArticlesCalendar.setOption({
            title: {
                text: '博客日迹',
                left: 'left',
                top: 20,
                textStyle: {
                    color: '#000'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{b}: {c} 篇"
            },
            visualMap: {
                min: 0,
                max: 5,
                calculable: true,
                orient: 'horizontal',
                left: 'center',
                top: 'top'
            },
            calendar: [{
                    range: '2018',
                    top: 'middle',
                    cellSize: ['auto', 20],
                    dayLabel: {
                        firstDay: 1, // 从周一开始
                        nameMap: 'cn'
                    },
                    monthLabel: {
                        nameMap: 'cn'
                    },
                    itemStyle: {
                        color: {
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0, color: 'red' // 0% 处的颜色
                            }, {
                                offset: 1, color: 'blue' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        }
                    }
            },],
            series: [{
                type: 'heatmap',
                coordinateSystem: 'calendar',
                calendarIndex: 0,
                data: calendarData
            }]
        });

    }


    // 获取买家地域数据 (中国分布图)
    var addressMap =  [];
    var chinaMapJson = {};
    function initAddressMapChartInfo () {
        $.ajax({
            url: "/displayPanel/getChinaMapJson",
            type: "get",
            success: function (res) {
                chinaMapJson = res.json;
                $.ajax({
                    url: "/displayPanel/getAddressMap",
                    type: "get",
                    success: function (res) {
                        addressMap = res.data;
                        // 初始化地图
                        initAddressMapChart();
                    }
                });

            }
        });
    }
    function initAddressMapChart() {
        // 注册地图
        echarts.registerMap('china', chinaMapJson);
        // ECharts
        var addresssMapChart = echarts.init(document.getElementById('address-map-chart'));
        // 地图经纬度数据
        var geoCoordMap = areamap;
        // 业务数据
        var data = this.addressMap;
        var convertData = function (data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var geoCoord = geoCoordMap[data[i].name];
                if (geoCoord) {
                    res.push({
                        name: data[i].name,
                        value: geoCoord.concat(data[i].value)
                    })
                }
            }
            return res;
        };
        var convertedData = [
            convertData(data),
            convertData(data.sort(function (a, b) {
                return b.value - a.value // b-a输出从大到小排序。a,b表示数组中的任意两个元素，若return > 0 b前a后；reutrn < 0 a前b后
            }).slice(0, 10))
        ];
        // 柱状图的数据由小到大，但柱状y轴显示是反向显示
        data.sort(function (a, b) {
            return a.value - b.value;
        });
        // var selectedItems = []
        var categoryDataDesc = []; // 柱状图y轴数据
        var categoryValueDesc = [];// 柱状图x轴数据

        var index;
        if (data.length < 10) {
            index = 0;
        } else {
            index = data.length - 10
        }
        // 筛选个数前十的城市
        for (var j = index; j < data.length; j++) {
            categoryDataDesc.push(data[j].name);
            categoryValueDesc.push(data[j]);
        }
        addresssMapChart.setOption({
            backgroundColor: '#fff',
            animation: true,
            animationDuration: 1000,
            animationEasing: 'cubicInOut',
            animationDurationUpdate: 1000,
            animationEasingUpdate: 'cubicInOut',
            title: [{
                show: true,
                text: '用户登录地地域分布图',
                subtext: '',
                left: 'center',
                textStyle: {
                    color: '#000'
                }
            }, {
                id: 'statistic',
                //text: count ? '城市个数:' + count + '   平均数: ' + parseInt((sum / count).toFixed(4)) : '',
                right: 120,
                top: 40,
                width: 100,
                textStyle: {
                    color: '#fff',
                    fontSize: 16
                }
            }],
            toolbox: {
                iconStyle: {
                    normal: {
                        borderColor: '#fff'
                    },
                    emphasis: {
                        borderColor: '#b1e4ff'
                    }
                },
                feature: {
                    brush: {
                        type: ['rect', 'polygon', 'clear']
                    },
                    saveAsImage: {
                        show: true
                    }
                }
            },
            brush: {
                outOfBrush: {
                    color: '#abc'
                },
                brushStyle: {
                    borderWidth: 2,
                    color: 'rgba(0,0,0,0.2)',
                    borderColor: 'rgba(0,0,0,0.5)'
                },
                seriesIndex: [0, 1],
                throttleType: 'debounce',
                throttleDelay: 300,
                geoIndex: 0
            },
            geo: {
                map: 'china',
                top: '80',
                left: 390,
                // right: '55%',
                // center: [117.98561551896913, 31.205000490896193],
                zoom: 1.2,
                label: {
                    emphasis: {
                        show: false
                    }
                },
                roam: false,
                itemStyle: {
                    normal: {
                        // areaColor: '#323c48',
                        // areaColor: '#97CBFF',
                        // #84C1FF
                        areaColor: '#84C1FF',
                        borderColor: '#111'
                    },
                    emphasis: {
                        areaColor: '#46A3FF'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: function (params) {
                        var count = params.value[2];
                        var name = params.name;
                        var str = name + '</br>';
                        str = str + '用户登录次数: ' + count
                        return str
                    }
                }
            },
            tooltip: {
                trigger: 'item'
            },
            grid: {
                right: 30,
                top: 200,
                bottom: 40,
                height: 500,
                width: '20%'
            },
            xAxis: {
                type: 'value',
                scale: true,
                position: 'top',
                boundaryGap: false,
                splitLine: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    margin: 2,
                    textStyle: {
                        color: '#aaa'
                    }
                }
            },
            yAxis: {
                type: 'category',
                name: 'TOP 10',
                nameGap: 16,
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: '#000'
                    }
                },
                axisTick: {
                    show: false,
                    lineStyle: {
                        color: '#000'
                    }
                },
                axisLabel: {
                    interval: 0,
                    textStyle: {
                        color: '#000'
                    }
                },
                data: categoryDataDesc
            },
            series: [{
                type: 'scatter',
                coordinateSystem: 'geo',
                data: convertedData[0],
                symbolSize: function (val) {
                    return Math.max(val[2] / 5, 8)
                },
                label: {
                    normal: {
                        formatter: '{b}',
                        position: 'right',
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                itemStyle: {
                    normal: {
                        // color: '#FF5809', 深红色
                        // color: '#4A4AFF', 备选
                        color: '#4A4AFF',
                        position: 'right',
                        show: true
                    }
                }
            }, {
                type: 'effectScatter',
                coordinateSystem: 'geo',
                data: convertedData[1],
                symbolSize: function (val) {
                    // return Math.max(val[2] / 10, 8)
                    return (val[2]/1)
                },
                showEffectOn: 'render',
                rippleEffect: {
                    brushType: 'stroke'
                },
                hoverAnimation: true,
                label: {
                    normal: {
                        formatter: '{b}',
                        position: 'right',
                        show: true
                    }
                },
                itemStyle: {
                    normal: {
                        // color: '#FF5809' 深红色
                        color: '#4A4AFF',
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },
                zlevel: 1
                // tooltip: {
                //   trigger: 'item',
                //   formatter: function (params) {
                //     // console.log(params)
                //     return '23'
                //   }
                // }
            }, {
                id: 'bar',
                zlevel: 2,
                barWidth: 25,
                type: 'bar',
                symbol: 'none',
                itemStyle: {
                    normal: {
                        color: '#46A3FF'
                        // color: '#0DCDC0'
                    }
                },
                // 具体个数
                data: categoryValueDesc
            }]
        })
    }

</script>

<style>


</style>
</html>
