<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<style>
    html body {
        overflow: auto;
    }

    .nava {
        color: #2F4050;
        text-decoration: none;
    }

    .warning-msg {
        display: block;
        bottom: 0px;
        right: 0px;
        position: fixed;
    }

    * html .warning-msg {
        position: absolute;
        right: 18px
    }

    .notification {
        position: fixed;
        z-index: 99999;
        right: 0;
        bottom: 0;
        font-family: Digital, 'Microsoft YaHei', STFangsong;
        display: flex;
        margin: 0 auto;
        width: 600px;
        min-height: 150px;
    }

    .notification .info {
        flex: 1;
        position: fixed;
        right: 0;
        bottom: 0;
        padding: 10px 10px 0 10px;
        background: #FFFFFF;
        border-radius: 3px 0 0 3px;
        border-bottom: 3px solid #c0cdd1;
    }

    .notification .info span {
        margin: 0;
        padding: 0;
        font-size: 16px;
        color: #000;
    }

    .notification .info p {
        margin: 0;
        margin-top: 20px;
        padding: 5px 0;
        font-size: 14px;
        color: #c5bebe;
    }

    .notification .info .button {
        display: inline-block;
        margin: 30px 3px 5px 0;
        padding: 3px 7px;
        border-radius: 2px;
        border-bottom: 1px solid;
        font-size: 12px;
        font-weight: bold;
        text-decoration: none;
        color: #ecf0f1;
    }

    .notification .info .button.gray {
        background: #95a5a6;
        border-bottom-color: #798d8f;
    }

    .notification .info .button {
        background: #435d8a;
        border-bottom-color: #435d8a;
    }

    .gbspan {
        position: absolute;
        right: 0;
        margin: 0;
        padding: 0;
        font-size: 16px;
        color: #000;
        height: 36px;
    }

    .tzspan {
        margin: 0;
        padding: 0;
        font-size: 16px;
        color: #000;
        height: 36px;
    }

    .ui-jqgrid .ui-jqgrid-titlebar {
        position: relative;
        border-left: 0 solid;
        border-right: 0 solid;
        border-top: 0 solid;
        background: #FFFFFF;
    }

    .ui-jqgrid .table-bordered th.ui-th-ltr {
        border-left: 0px none !important;
        background: #FFF;
    }
</style>
<head>
    <t:base type="default,jqgrid"></t:base>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="./vue.min.js"></script>
    <script src="./axios.min.js"></script>
    <script src="./element.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <!-- 引入组件库 -->
    <%--<script src="https://unpkg.com/element-ui/lib/index.js"></script>--%>
    <%--<script src="https://unpkg.com/axios/dist/axios.min.js"></script>--%>
    <link rel="stylesheet" href="./index.css">
    <link rel="stylesheet" href="./element.css">
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
                    <span></span>
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
<div id="rbbox" hidden="true">
    <div class="notification" style="width: 600px">
        <div class="info">
            <div class="gbspan" style="font-weight: bolder " ; onclick="closes()"><span>关闭</span></div>
            <div class="tzspan" style="font-weight: bolder" onclick="jieshou()">接收通知</div>
            <div id="jqGrid_wrapper" class="jqGrid_wrapper" style="width: 600px">
            </div>
        </div>
    </div>
</div>
</div>
<div id="rbboxs" hidden="true">
    <div class="notification" style="width: 600px">
        <div class="info">
            <div class="gbspan" style="font-weight: bolder " ; onclick="closess()"><span>关闭</span></div>
            <div class="tzspan" style="font-weight: bolder" onclick="jieshou()">接收通知</div>
            <div id="jqGrid_wrappers" class="jqGrid_wrappers" style="width: 600px">
            </div>
        </div>
    </div>
</div>
</div>
<!-- 脚本部分 -->
<t:datagrid actionUrl="notificationform/tableAll" tableContentId="jqGrid_wrapper"
            caption="议题会议通知" multiSelect="true" name="table_list_1" height="200" pageSize="5" rownumbers="false">
    <t:dgCol name="id" label="编号" hidden="true" key="true"></t:dgCol>
    <t:dgCol name="huiyidate" label="会议日期" width="300" query="false"></t:dgCol>
    <t:dgCol name="huiyihome" label="会议室" width="300" query="false"></t:dgCol>
</t:datagrid>
<!-- 脚本部分 -->
<t:datagrid actionUrl="notificationform/tablegroupbygr" tableContentId="jqGrid_wrappers"
            caption="议题会议通知" multiSelect="true" name="table_list_2" height="200" pageSize="5" rownumbers="false">
    <t:dgCol name="id" label="编号" hidden="true" key="true"></t:dgCol>
    <t:dgCol name="huiyidate" label="会议日期" width="300" query="false"></t:dgCol>
    <t:dgCol name="huiyihome" label="会议室" width="300" query="false"></t:dgCol>
</t:datagrid>


<script type="text/javascript">
    function closes() {
        $("#rbbox").hide();
    }

    function closess() {
        $("#rbboxs").hide();
        $.post("notificationform/updatetx0", function (data) {
            if (data.success) {
                //操作结束，刷新表格
                reloadTable('table_list_2');
            } else {
                qhTipWarning(data.msg);
            }
        });
    }

    function jieshou() {
        var rowIds = $("#table_list_1").jqGrid('getGridParam', 'selarrrow');
        if (rowIds == "" || rowIds == null) {
            qhAlert('请选择要接受的通知');
            return;
        }
        //是
        $.post("notificationform/jieshou", {ids: rowIds.toString()}, function (data) {
            if (data.success) {
                qhTipSuccess(data.msg);
                //操作结束，刷新表格
                reloadTable('table_list_1');
                reloadTable('table_list_2');
            } else {
                qhTipWarning(data.msg);
            }
        });
    }

    function getAll() {
        //是
        $.post("notificationform/tableAlls", function (data) {
            if (data.obj.length == 0) {
                $("#rbbox").hide();
                $("#rbboxs").hide();
            } else {
                $("#rbbox").show();
                $("#rbboxs").hide();
                reloadTable('table_list_1');
            }
        });
    }

    function getgr() {
        //是
        $.post("notificationform/tablegroupbygrs", function (data) {
            if (data.obj.length == 0) {
                $("#rbboxs").hide();
                $.post("notificationform/tableAlls", function (data) {
                    if (data.obj.length == 0) {
                        $("#rbbox").hide();
                    } else {
                        $("#rbbox").show();
                        reloadTable('table_list_1');
                    }
                });
            } else {
                $("#rbbox").hide();
                reloadTable('table_list_1');
                $("#rbboxs").show();
                reloadTable('table_list_2');
            }
        });
    }

    function getgrS() {
        //是
        $.post("notificationform/tablegroupbygrs", function (data) {
            if (data.obj.length == 0) {
                $("#rbboxs").hide();
                $.post("notificationform/tableAlls", function (data) {
                    if (data.obj.length == 0) {
                        $("#rbbox").hide();
                    } else {
                        $("#rbbox").hide();
                        reloadTable('table_list_1');
                    }
                });
            } else {
                $("#rbbox").hide();
                reloadTable('table_list_1');
                $("#rbboxs").show();
                reloadTable('table_list_2');
            }
        });
    }

    setInterval(getAll, 7200000);
    setInterval(getgrS, 9000);
    window.onload = function () {    //加载
        getAll();
        getgr();
    }
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
    document.getElementById("app").style.webkitTransform = scale; // Chrome, Opera, Safari
    document.getElementById("app").style.msTransform = scale; // IE 9
    document.getElementById("app").style.transform = scale; // General

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