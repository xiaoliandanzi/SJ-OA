<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<style>
    * {
        margin: 0;
        padding: 0;
    }

    html,
    body,
    #home {
        height: 100%;
        width: 100%;
    }

    #home {
        display: flex;
        flex-direction: column
    }

    .navbarBox {
        display: flex;
        position: relative;
    }

    .logoBox {
        margin-left: 20px;
    }

    .title {
        font-size: 30px;
        color: #347BB7;
        line-height: 90px;
        font-weight: bold;
        margin-left: 18px;
    }

    .btnBox {
        height: 90px;
        position: relative;
        margin-left: 20px;
        line-height: 90px;

    }

    .btnImg {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 20px;
        height: 20px;
        margin: auto;
    }

    .btnTxt {
        font-size: 16px;
        font-weight: 400;
        color: #2F4050;
        margin-left: 20px;
    }

    .btnTxt:hover {
        color: #D30404;
    }

    .selectBox {
        width: 300px;
        /* margin-top: 26px; */
        display: flex;
        /* margin-left: 45px; */
        height: 28px;
        position: relative;
        top: 0;
        bottom: 0;
        margin: auto;
        margin-left: 45px;
        margin-right: 0;
        /* border: 1px solid #000; */
    }

    .selectBtn {
        display: block;
        width: 100px;
        position: relative;
        top: 0;
        bottom: 0;
        margin: auto;
        margin-left: 0;
        height: 26px;
        text-align: center;
        font-size: 20px;
        color: #FFFFFF;
        background: #E60012;
        line-height: 28px;
        border: 1px solid #2F4050;
        border-radius: 4px;
        left: -4px;
    }

    .MsgBox {
        display: flex;
        height: 100%;
        /* background-color: greenyellow; */
    }

    .shuBox {
        flex: 1;
        display: flex;
        flex-direction: column;
        margin-left: 10px;
    }

    .itemBox {
        flex: 1;
        border: 1px solid #ccc;
        overflow: hidden;
        margin-bottom: 10px;
    }

    .item_title {
        display: flex;

    }

    .title_left {
        flex: 2;
        border-bottom: 5px solid #347BB7;
        margin-right: 20px;
        margin-left: 10px;
        color: #347BB7;
        font-size: 15px;
        line-height: 30px;
        cursor: pointer;
    }

    .title_right {
        flex: 4;
        border-bottom: 5px solid #2F4050;
        text-align: right;
        padding-right: 10px;
        margin-right: 10px;
        color: #D30404;
        font-size: 12px;
        line-height: 30px;
        cursor: pointer;
    }

    .jia {
        color: #D62B2B;

    }

    .item1Img {
        width: 80%;
        height: 60%;
        margin: 0 auto;
        /* background: url('https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg') no-repeat; */
        background-size: 100% 100%;
        margin-top: 20px;
    }

    .item1Img img {
        width: 100%;
        height: 100%;
    }

    .listBox {
        padding-left: 10px;
    }

    .listItem {
        font-size: 14px;
        color: #000000;
        cursor: pointer;

    }

    .listItem_name {
        display: inline-block;
        width: 60%;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        font-size: 13px;
    }

    .listItem_date {
        font-size: 13px;
    }

    .gongsiwenjianBox {
        display: flex;
        justify-content: space-around;
        position: absolute;
        width: 100%;
        height: 120px;
        top: 0;
        bottom: 0;
        margin: auto;
    }

    .yuanBox {
        width: 98px;
        height: 98px;
        border: 1px solid #E6E6E6;
        border-radius: 50%;
        line-height: 100px;
        text-align: center;
        font-size: 20px;


    }

    .nava {
        color: #2F4050;

    }

    .gengduoa {
        color: #676a6c;
    }

    .i1, .i2 {
        height: 20px;
        background-color: #2F4050;
        font-size: 10px;
        color: white;
        line-height: 0;
        margin: 10px;
    }

    #footer {
        height: 100px;
        width: 1200px;
        margin: 10px;
    }

    .el-input__inner {
        border: 1px solid #2F4050;
    }
</style>
<head>
    <t:base type="default"></t:base>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">

    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <!-- 引入组件库 -->
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <%--<link rel="stylesheet" href="./home.css">--%>
</head>
<body>
<div id="home">
    <div class="navbarBox">
        <div class="logoBox">
            <img src="./img/a.png" alt="">
        </div>
        <div class="title">
            双井街道智慧办公系统
        </div>
        <div class="btnBox">
            <img class="btnImg" src="./img/d.png" alt="">
            <span class="btnTxt">
                    <a href="/oa/" class="nava">门户首页</a>
                </span>
        </div>
        <div class="btnBox">
            <img class="btnImg" src="./img/b.png" alt="">
            <span class="btnTxt">
                   <a href="/oa/index" class="nava">个人办公</a>
                </span>
        </div>
        <div class="btnBox">
            <img class="btnImg" src="./img/c.png" alt="">
            <span class="btnTxt">
                    <a href="#" class="nava">财务系统</a>
                </span>
        </div>
        <div class="selectBox">
            <el-input size="mini" placeholder="请输入关键字" prefix-icon="el-icon-search" v-model="input2">
            </el-input>
        </div>
        <div class="selectBtn">
            搜索
        </div>
    </div>
    <div class="MsgBox">
        <div class="shuBox" style="margin-left: 10px;">
            <div class="itemBox">
                <div class="item_title">
                    <div class="title_left">
                        双井图库
                    </div>
                    <div class="title_right">
                        更多
                        <span class="jia">+</span>
                    </div>
                </div>
                <div class="item1Img"
                     :style="{backgroundImage:'url(https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg)'}">
                </div>
            </div>
            <div class="itemBox">
                <div class="item_title">
                    <div class="title_left">
                        公示文件
                    </div>
                    <div class="title_right">
                        <a href="/oa/oa/index/viewArticleList?messageType=1" class="gengduoa">更多<span
                                class="jia">+</span></a>
                    </div>
                </div>
                <div class="listBox">
                    <div class="listItem" v-for="(item,index) in oneList" :key="index" @click="listClick(item)">
                            <span class="listItem_name">
                               {{item.title}}
                            </span>
                        <span class="listItem_date">
                                 {{item.publicTime}} 阅{{item.count}}次
                            </span>
                    </div>
                </div>
            </div>
            <div class="itemBox">
                <div class="item_title">
                    <div class="title_left">
                        通知公告
                    </div>
                    <div class="title_right">
                        <a href="/oa/oa/index/viewArticleList?messageType=2" class="gengduoa">更多<span
                                class="jia">+</span></a>
                    </div>
                </div>
                <div class="listBox">
                    <div class="listItem" v-for="(item,index) in twoList" :key="index" @click="listClick(item)">
                            <span class="listItem_name">
                               {{item.title}}
                            </span>
                        <span class="listItem_date">
                                 {{item.publicTime}} 阅{{item.count}}次
                            </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="shuBox">
            <div class="itemBox">
                <div class="item_title">
                    <div class="title_left">
                        工作信息
                    </div>
                    <div class="title_right">
                        <a href="/oa/oa/index/viewArticleList?messageType=4" class="gengduoa">更多<span
                                class="jia">+</span></a>
                    </div>
                </div>
                <div class="listBox">
                    <div class="listItem" v-for="(item,index) in fourList" :key="index" @click="listClick(item)">
                            <span class="listItem_name">
                               {{item.title}}
                            </span>
                        <span class="listItem_date">
                                 {{item.publicTime}} 阅{{item.count}}次
                            </span>
                    </div>
                </div>
            </div>
            <div class="itemBox">
                <div class="item_title">
                    <div class="title_left">
                        媒体聚焦
                    </div>
                    <div class="title_right">
                        <a href="/oa/oa/index/viewArticleList?messageType=3" class="gengduoa">更多<span
                                class="jia">+</span></a>
                    </div>
                </div>
                <div class="listBox">
                    <div class="listItem" v-for="(item,index) in threeList" :key="index" @click="listClick(item)">
                            <span class="listItem_name">
                               {{item.title}}
                            </span>
                        <span class="listItem_date">
                                 {{item.publicTime}} 阅{{item.count}}次
                            </span>
                    </div>
                </div>
            </div>
            <div class="itemBox">
                <div class="item_title">
                    <div class="title_left">
                        制度范围
                    </div>
                    <div class="title_right">
                        <a href="/oa/oa/index/viewArticleList?messageType=5" class="gengduoa">更多<span
                                class="jia">+</span></a>
                    </div>
                </div>
                <div class="listBox">
                    <div class="listItem" v-for="(item,index) in fiveList" :key="index" @click="listClick(item)">
                            <span class="listItem_name">
                               {{item.title}}
                            </span>
                        <span class="listItem_date">
                                 {{item.publicTime}} 阅{{item.count}}次
                            </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="shuBox" style="margin-right: 10px;">
            <div class="itemBox" style="position: relative;">
                <div class="item_title">
                    <div class="title_left">
                        待办事项
                    </div>
                    <div class="title_right">

                    </div>
                </div>
                <div class="gongsiwenjianBox">

                    <div class="yunItem">
                        <div class="yuanBox">2</div>
                        <p style="text-align: center; margin-top: 10px; color: #7F7F80;">待审核申请</p>
                    </div>
                    <div class="yunItem">
                        <div class="yuanBox">3</div>
                        <p style="text-align: center; margin-top: 10px; color: #7F7F80;">待督办事项</p>
                    </div>
                    <div class="yunItem">
                        <div class="yuanBox">4</div>
                        <p style="text-align: center; margin-top: 10px; color: #7F7F80;">被驳回事项</p>
                    </div>

                </div>
            </div>
            <div class="itemBox">
                <div class="item_title">
                    <div class="title_left">
                        12345案件统计
                    </div>
                    <div class="title_right">

                    </div>
                </div>
                <div>
                    <div id="main-number" style="width: 150px;height:150px;float: right;z-index: 9999999"></div>
                    <div id="main-dept" style="width: 150px;height:150px; float: left;z-index: 9999999"></div>
                </div>
            </div>
            <div class="itemBox">
                <div class="item_title">
                    <div class="title_left">
                        事件通知
                    </div>
                    <div class="title_right">
                        更多
                        <span class="jia">+</span>
                    </div>
                </div>
                <div class="listBox">
                    <div class="listItem">
                            <span class="listItem_date">

                                1、您好，xx于x年x月x日x时x分取消了x年x月x日的发文申请。取消原因x日的发
                                文申请。取消原因:xXXX。:xXXX。

                            </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="footer">
        <div>
            <ul id="btn">
                <li>链接
                    <input type="button" value="朝阳政务" class="i1" onclick="window.open('http://www.chy.egov.cn/')"/>
                    <input type="button" value="朝阳区服务网" class="i2"
                           onclick="window.open('http://www.bjchy.gov.cn/')"/>
                    <input type="button" value="市政府采购中心" class="i2"
                           onclick="window.open('http://bgpc.beijing.gov.cn/')"/>
                    <input type="button" value="云享朝阳·感知互动体验中心" class="i2"
                           onclick="window.open('http://3d.bjchy.gov.cn')"/>
                    <input type="button" value="学习强国" class="i2" onclick="window.open('https://www.xuexi.cn/')"/>
                    <input type="button" value="区保密局提示" class="i2"
                           onclick="window.open('http://www.chy.egov.cn/qbm-5.html')"/>
                </li>

            </ul>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">

    var home = new Vue({
        el: '#home',
        data() {
            return {
                input2: '',
                oneList: [],
                twoList: [],
                threeList: [],
                fourList: [],
                fiveList: [],
            }
        },
        mounted() {
            this.oneMounted(),
                this.twoMounted(),
                this.threeMounted(),
                this.fourMounted(),
                this.fiveMounted()
        },
        methods: {
            oneMounted() {
                axios.get('oa/login/messageList?messageType=' + 1 + '&rows=' + 8).then((msg) => {
                    this.oneList = msg.data.obj.records
                })
            },
            twoMounted() {
                axios.get('oa/login/messageList?messageType=' + 2 + '&rows=' + 8).then((msg) => {
                    this.twoList = msg.data.obj.records
                })
            },
            threeMounted() {
                axios.get('oa/login/messageList?messageType=' + 3 + '&rows=' + 8).then((msg) => {
                    this.threeList = msg.data.obj.records
                })
            },
            fourMounted() {
                axios.get('oa/login/messageList?messageType=' + 4 + '&rows=' + 8).then((msg) => {
                    this.fourList = msg.data.obj.records
                })
            },
            fiveMounted() {
                axios.get('oa/login/messageList?messageType=' + 5 + '&rows=' + 8).then((msg) => {
                    this.fiveList = msg.data.obj.records
                })
            },
            listClick(item) {
                //   /oa/oa/index/viewArticleList?messageType=5
                //this.$router.push("/oa/oa/index/viewArticleList?messageType=" + item.messageType + "&id=" + item.id)
                window.location.href = "/oa/oa/index/viewArticleList?messageType=" + item.messageType + "&id=" + item.id;
            },
        },


    })


    /*gongshi(1);
    gongshi(2);
    gongshi(3);
    gongshi(4);
    gongshi(5);*/
    /*    function gongshi(messageType) {
            $.ajax({
                type: "get",
                url: 'oa/login/messageList?messageType=' + messageType + "&rows=" + 8,//目标地址
                success: function (data) {
                    disposeList(data.obj, messageType);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    disposeList([], messageType);
                }
            })
        }

        function disposeList(list, messageType) {
            var lili = '';
            if (messageType == 1) {
                lili = '<li id="cor1"><b style="font-size: 12px">公示文件</b></li>\n' +
                    '            <li class="cor2"><a href="/oa/oa/index/viewArticleList?messageType=1" id="more" style="font-size: 10px">更多+</a></li>';
            } else if (messageType == 2) {
                lili = '<li id="cor1"><b style="font-size: 12px">通知公告</b></li>\n' +
                    '            <li class="cor2"><a href="/oa/oa/index/viewArticleList?messageType=2" id="more" style="font-size: 10px">更多+</a></li>';
            } else if (messageType == 3) {
                lili = '<li id="cor1"><b style="font-size: 12px">媒体聚焦</b></li>\n' +
                    '            <li class="cor2"><a href="/oa/oa/index/viewArticleList?messageType=3" id="more" style="font-size: 10px">更多+</a></li>';
            } else if (messageType == 4) {
                lili = '<li id="cor1"><b style="font-size: 12px">工作信息</b></li>\n' +
                    '            <li class="cor2"><a href="/oa/oa/index/viewArticleList?messageType=4" id="more" style="font-size: 10px">更多+</a></li>';
            } else if (messageType == 5) {
                lili = ' <li id="cor1"><b style="font-size: 12px">制度范围</b></li>\n' +
                    '            <li class="cor2"><a href="/oa/oa/index/viewArticleList?messageType=5" id="more" style="font-size: 10px">更多+</a></li>';
            }
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    var li = '<li onclick="getArticle(this)" class="font1" style="line-height: 20px" id="' + list[i].id + '">' + list[i].title + '(' + list[i].publicTime + '阅' + list[i].count + '次)</li>';
                    lili += li;
                }
            }
            if (messageType == 1) {
                document.getElementById('gongshiwenjian').innerHTML = lili;
            } else if (messageType == 2) {
                document.getElementById('tongzhigonggao').innerHTML = lili;
            } else if (messageType == 3) {
                document.getElementById('meitijujiao').innerHTML = lili;
            } else if (messageType == 4) {
                document.getElementById('gongzuoxinxi').innerHTML = lili;
            } else if (messageType == 5) {
                document.getElementById('zhidufanwei').innerHTML = lili;
            }
        }

        var getArticle = function (data) {
            console.log($(data).attr("id"));

        }*/


    /**
     *
     */
    var myChart = echarts.init(document.getElementById('main-number'));
    var myChart_two = echarts.init(document.getElementById('main-dept'));
    var bingtu = '';
    search();
    getNum();

    function search() {
        $.ajax({
            type: "post",
            url: 'oa/123456/getAll',//目标地址
            success: function (data) {
                bingtu = JSON.parse(data.obj);
                eac(bingtu);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(textStatus)
            }
        })
    }

    function getNum() {
        $.ajax({
            type: "post",
            url: 'oa/123456/getNum',//目标地址
            success: function (data) {
                bingtu = JSON.parse(data.obj);
                numeac(bingtu);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(textStatus)
            }
        })
    }

    function numeac(bingtu) {
        var titleNumber = bingtu.data.total;
        var number = bingtu.data.chart;
        var numberBL = bingtu.data.point;
        var option_two = {
            title: {
                text: '今日办结率:' + numberBL,
                subtext: numberBL,
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                confine: true,
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 10,
                data: []
            },
            series: [
                {
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        show: false,
                        position: 'center',
                        normal: {
                            position: 'inner',
                            show: false
                        }
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: number
                }
            ],
        };
        myChart_two.setOption(option_two);
    }


    function eac(bingtu) {
        var titleNumber = bingtu.data.total;
        var number = bingtu.data.chart;
        var numberSL = bingtu.data.total;
        var option = {
            title: {
                text: '今日案件数量:' + numberSL,
                subtext: numberSL,
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                confine: true,
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 10,
                data: []
            },
            series: [
                {
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        show: false,
                        position: 'center',
                        normal: {
                            position: 'inner',
                            show: false
                        }
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: number
                }
            ],
        };
        myChart.setOption(option);
    }


</script>
</html>



