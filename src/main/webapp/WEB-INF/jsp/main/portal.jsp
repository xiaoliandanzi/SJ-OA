<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<style>
    .nava {
        color: #2F4050;
        text-decoration: none;
    }
</style>
<head>
    <t:base type="default"></t:base>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="./vue.min.js"></script>
    <script src="./axios.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <link rel="stylesheet" href="./index.css">
</head>

<body>
<div id="app">
    <div class="topNavber">
        <div class="logo">
            <img class="logoImg" src="./img/a.png" alt="">
        </div>
        <div class="webName">
            双井街道智慧办公系统
        </div>
        <div class="navberItem">
            <img class="navIcon" src="./img/d.png" alt="">
            <span class="navItemName"><a href="/oa/" class="nava">门户首页</a></span>
        </div>
        <div class="navberItem">
            <img class="navIcon" src="./img/b.png" alt="">
            <%--<a href="/oa/index" class="nava">个人办公</a>--%>
            <%--span class="navItemName">--%><a href="/oa/index" class="navItemName nava">个人办公</a><%--</span>--%>
        </div>
        <div class="navberItem">
            <img class="navIcon" src="./img/c.png" alt="">
            <span class="navItemName">财务系统</span>
        </div>
        <div class="selectBox">
            <div class="inpBox">
                <input class="selectInp" v-model="selectMsg" type="text" @input="getSelectHold"/>
                <div class="inpBack" v-if="selectHold">
                    <img class="selectIcon" src="./img/select.png" alt="">
                    <span>123132</span>
                </div>
            </div>
            <div class="selectBtn">
                搜索
            </div>
        </div>
        <div class="userBox" id="user-box"><img src="./img/user.jpg" class="navIcon" style="margin-top: 0px;">&nbsp
            {{relName}}
        </div>
    </div>
    <div class="listBox">
        <!-- 第一个 -->
        <div class="oneLine">
            <div class="one_a">
                <div class="borderTitle">
                    <div class="border_left">双井图库</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo">更多</span>
                        </div>
                    </div>
                </div>
                <div>
                    <img class="BorderImgBox" src="./img/borderImg.png" alt="">
                </div>
            </div>
            <!-- 第二个 -->
            <div class="one_a borderCenter">
                <div class="borderTitle">
                    <div class="border_left">工作信息</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo"><a href="/oa/oa/index/viewArticleList?messageType=4" class="gengduo">更多</a></span>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="articleItem" v-for="(item,index) in fourList" :key="index" @click="listClick(item)">
                        <div class="articleTitle">{{item.title}}</div>
                        <div class="articleDate">({{item.publicTime}}</div>
                        <div class="articleNums">阅{{item.count}}次)</div>
                    </div>
                </div>
            </div>
            <!-- 第三个 -->
            <div class="one_a">
                <div class="borderTitle">
                    <div class="border_left">代办事项</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo">更多</span>
                        </div>
                    </div>
                </div>
                <div class="circularBigBox">
                    <div class="circularBox">
                        <div class="circular">
                            <div style="font-size: 22px;font-weight: bold">3</div>
                        </div>
                        <div class="circularTitle">
                            待审核申请
                        </div>
                    </div>
                    <div class="circularBox">
                        <div class="circular">
                            <div style="font-size: 22px;font-weight: bold">3</div>
                        </div>
                        <div class="circularTitle">
                            待督办事项
                        </div>
                    </div>
                    <div class="circularBox">
                        <div class="circular">
                            <div style="font-size: 22px;font-weight: bold">8</div>
                        </div>
                        <div class="circularTitle">
                            被驳回事项
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 第二行 -->
        <div class="oneLine twoLine">
            <div class="one_a">
                <div class="borderTitle">
                    <div class="border_left">公式文件</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo"><a href="/oa/oa/index/viewArticleList?messageType=1" class="gengduo">更多</a></span>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="articleItem" v-for="(item,index) in oneList" :key="index" @click="listClick(item)">
                        <div class="articleTitle">{{item.title}}</div>
                        <div class="articleDate">({{item.publicTime}}</div>
                        <div class="articleNums">阅{{item.count}}次)</div>
                    </div>
                </div>
            </div>
            <div class="one_a borderCenter">
                <div class="borderTitle">
                    <div class="border_left">媒体聚焦</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo"><a href="/oa/oa/index/viewArticleList?messageType=3" class="gengduo">更多</a></span>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="articleItem" v-for="(item,index) in threeList" :key="index" @click="listClick(item)">
                        <div class="articleTitle">{{item.title}}</div>
                        <div class="articleDate">({{item.publicTime}}</div>
                        <div class="articleNums">阅{{item.count}}次)</div>
                    </div>
                </div>
            </div>
            <div class="one_a">
                <div class="borderTitle">
                    <div class="border_left">12345案件统计</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo">更多</span>
                        </div>
                    </div>
                </div>
                <div>
                    <%--<div class="articleItem" v-for="(item,index) in list" :key="index">
                        <div class="articleTitle">{{item.title}}</div>
                        <div class="articleDate">({{item.date}}</div>
                        <div class="articleNums">阅{{item.nums}}次)</div>
                    </div>--%>
                    <div id="main-number"
                         style="width: 200px;height:200px;float: right;z-index: 9999999;top: 20px;right: 40px"></div>
                    <div id="main-dept"
                         style="width: 200px;height:200px; float: left;z-index: 9999999;top: 20px;left: 40px"></div>
                </div>
            </div>

        </div>
        <!-- 第三行 -->
        <div class="oneLine twoLine">
            <div class="one_a">
                <div class="borderTitle">
                    <div class="border_left">通知公告</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo"><a href="/oa/oa/index/viewArticleList?messageType=2" class="gengduo">更多</a></span>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="articleItem" v-for="(item,index) in twoList" :key="index" @click="listClick(item)">
                        <div class="articleTitle">{{item.title}}</div>
                        <div class="articleDate">({{item.publicTime}}</div>
                        <div class="articleNums">阅{{item.count}}次)</div>
                    </div>
                </div>
            </div>
            <div class="one_a borderCenter">
                <div class="borderTitle">
                    <div class="border_left">制度范围</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo"><a href="/oa/oa/index/viewArticleList?messageType=5" class="gengduo">更多</a></span>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="articleItem" v-for="(item,index) in fiveList" :key="index" @click="listClick(item)">
                        <div class="articleTitle">{{item.title}}</div>
                        <div class="articleDate">({{item.publicTime}}</div>
                        <div class="articleNums">阅{{item.count}}次)</div>
                    </div>
                </div>
            </div>
            <div class="one_a">
                <div class="borderTitle">
                    <div class="border_left">事件通知</div>
                    <div class="border_right">
                        <div>
                            <span class="jia">+</span>
                            <span class="gengduo">更多</span>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="articleItem" v-for="(item,index) in shijian" :key="index">
                        <div class="eventIndex">{{index+1}}、{{item.title}}取消原因：{{item.msg}}</div>
                        <!-- <div class="eventTitle"></div>
                        <div class="eventMsg"></div> -->
                    </div>
                </div>
            </div>

        </div>
        <div class="herfBox">
            <div class="lianjie">链接</div>
            <a href="http://www.chy.egov.cn/" target="_blank">
                <div class="hrefVal">
                    朝阳政务
                </div>
            </a>
            <a href="http://www.bjchy.gov.cn/" target="_blank">
                <div class="hrefVal">
                    朝阳区政府
                </div>
            </a>
            <a href="http://bgpc.beijing.gov.cn/" target="_blank">
                <div class="hrefVal">
                    市政府采购中心
                </div>
            </a>
            <a href="http://3d.bjchy.gov.cn" target="_blank">
                <div class="hrefVal">
                    云享朝阳·感知互动体验中心
                </div>
            </a>
            <a href="https://www.xuexi.cn/" target="_blank">
                <div class="hrefVal">
                    学习强国
                </div>
            </a>
            <a href="http://www.chy.egov.cn/qbm-5.html" target="_blank">
                <div class="hrefVal">
                    区保密局提示
                </div>
            </a>

        </div>
    </div>
</div>
</body>

<script type="text/javascript">

    var dom = new Vue({
        el: "#app",
        data() {
            return {
                selectMsg: '',
                selectHold: true,
                oneList: [],
                twoList: [],
                threeList: [],
                fourList: [],
                fiveList: [],
                relName: '',
                shijian: [{
                    title: "您好，xx于x年x月x日x时x分取消了x年x月x日的发文申请。",
                    msg: "这是原因"
                },
                    {
                        title: "您好，xx于x年x月x日x时x分取消了x年x月x日的发文申请。",
                        msg: "这是原因"
                    }, {
                        title: "您好，xx于x年x月x日x时x分取消了x年x月x日的发文申请。",
                        msg: "这是原因"
                    }, /*{
                        title: "您好，xx于x年x月x日x时x分取消了x年x月x日的发文申请。",
                        msg: "这是原因"
                    }*/
                ]
            }
        },
        mounted() {
            this.oneMounted(),
                this.twoMounted(),
                this.threeMounted(),
                this.fourMounted(),
                this.fiveMounted(),
                this.getUser()
        },
        methods: {
            oneMounted() {
                axios.get('oa/login/messageList?messageType=' + 1 + '&rows=' + 6).then((msg) => {
                    this.oneList = msg.data.obj.records
                })
            },
            twoMounted() {
                axios.get('oa/login/messageList?messageType=' + 2 + '&rows=' + 6).then((msg) => {
                    this.twoList = msg.data.obj.records
                })
            },
            threeMounted() {
                axios.get('oa/login/messageList?messageType=' + 3 + '&rows=' + 6).then((msg) => {
                    this.threeList = msg.data.obj.records
                })
            },
            fourMounted() {
                axios.get('oa/login/messageList?messageType=' + 4 + '&rows=' + 6).then((msg) => {
                    this.fourList = msg.data.obj.records
                })
            },
            fiveMounted() {
                axios.get('oa/login/messageList?messageType=' + 5 + '&rows=' + 6).then((msg) => {
                    this.fiveList = msg.data.obj.records
                })
            },
            getSelectHold() {
                if (this.selectMsg != '') {
                    this.selectHold = false

                } else {
                    this.selectHold = true

                }
            },
            listClick(item) {
                //   /oa/oa/index/viewArticleList?messageType=5
                //this.$router.push("/oa/oa/index/viewArticleList?messageType=" + item.messageType + "&id=" + item.id)
                window.location.href = "/oa/oa/index/viewArticleList?messageType=" + item.messageType + "&id=" + item.id;
            },
            getUser() {
                axios.get('oa/portal/user').then((msg) => {
                    console.log(msg.data.obj.realName);
                    this.relName = msg.data.obj.realName;
                    console.log(this.relName)
                })
            }
        }
    })
    var scale = 'scale(0.8)';
    document.body.style.webkitTransform = scale; // Chrome, Opera, Safari
    document.body.style.msTransform = scale; // IE 9
    document.body.style.transform = scale; // General

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