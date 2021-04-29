<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<script src="./polyfill.min.js"></script>
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
    .eventIndex {
        max-height: 300px;
        overflow: hidden;
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
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts@4.5.0/dist/echarts.min.js"></script>
    <!-- 引入组件库 -->
    <%--<script src="https://unpkg.com/element-ui/lib/index.js"></script>--%>
    <%--<script src="https://unpkg.com/axios/dist/axios.min.js"></script>--%>
    <link rel="stylesheet" href="./index.css">
    <link rel="stylesheet" href="./element.css">
</head>
<body style = "background-color:#f7f7f7">
<div id="app" style="background-color: #f7f7f7">
    <div class="topNavber" style="background-color: #2076be !important;color: white;">
        <div class="logo">
            <img class="logoImg" src="./img/a.png" alt="">
        </div>
        <div class="webName" style="color: white;">
            双井街道智慧办公系统
        </div>
        <a href="#" οnclick="javascript:Run('file://C:/Users/云天红移/Desktop/sscom5.13.1.exe','File')">gggggggggggggggggggggg</a>
        <div class="navberItem">
            <img class="navIcon" src="./img/d.png" alt="">
            <span class="navItemName"><a href="/oa/" class="nava" style="color: white;">门户首页</a></span>
        </div>
        <div class="navberItem">
            <img class="navIcon" src="./img/b.png" alt="">
            <%--<a href="/oa/index" class="nava">个人办公</a>--%>
            <%--span class="navItemName">--%><a href="/oa/index" class="navItemName nava" style="color: white;">个人办公</a><%--</span>--%>
        </div>
        <div class="navberItem">
            <img class="navIcon" src="./img/c.png" alt="">
            <a class="navItemName" href="vs:" style="color: white;" >财务系统</a>
        </div>
        <%--
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
        </div>--%>
        <div class="userBox" id="user-box" style="margin-right: 136px !important;">
            <img src="./img/user.png" class="navIcon" style="margin-top: 0px;">&nbsp
                    <span class="navItemName nava" style="font-size: 30px;color: white;">{{relName}}</span>
                    <span  class="navItemName nava" style="font-size: 28px;color: white;" href="javascript:;" onclick="exit();">退出</span>
        </div>
    </div>
    <div class="listBox">
        <div style="margin-top: 18px;padding: 0 20px;display: flex;">
            <div class="nowCenter" style="width: 1230px;">
                <div class="one_a boxShadow" style="width: 1230px !important;height: 703px !important;margin-bottom: 20px;">
                    <div class="borderTitle">
                        <div class="border_left" style="border-left: 5px solid #347BB7">双井风采</div>
                        <div class="border_right">
                            <div>
                                <span class="jia" style="display: block;position: relative;top:24px;"><img
                                        src="./img/more.png"></span>
                                <span class="gengduo"><a href="/oa/oa/index/returnAllImgPath">更多</a></span>
                            </div>
                        </div>
                    </div>
                    <div>
                        <img style="float: left; width: 26px;height: 44px;margin-right: 20px;margin-left: 10px; margin-top:295px; "
                             src="./img/zuo-.png" alt="" id="left">
<%--                        <img class="BorderImgBox" style=" float: left; width: 1084px;height: 610px !important;"--%>
<%--                             src="./img/borderImg.png" alt="">--%>

                        <div class="container">
                            <div class="list" id="listImg" style="left:0px;">
                            </div>
                            <div class="pointer">
                                <span index="1" class="on"></span>
                                <span index="2"></span>
                                <span index="3"></span>
                                <span index="4"></span>
                                <span index="5"></span>
                            </div>

<%--                            <a href="#" rel="external nofollow" rel="external nofollow" class="arrow left">&gt;</a>--%>
<%--                            <a href="#" rel="external nofollow" rel="external nofollow" class="arrow right">&lt;</a>--%>
                        </div>
                        <img style="float: right;width: 26px;height: 44px;margin-left: 25px; margin-top:295px;"
                             src="./img/you.png" alt="" id="right">
                    </div>


                </div>

                <div class="one_a boxShadow">
                    <div class="borderTitle">
                        <div class="border_left" style="border-left: 5px solid #347BB7">通知公告</div>
                        <div class="border_right">
                            <div>
                                <%--                        <span class="jia">+</span>--%>
                                <span class="jia" style="display: block;position: relative;top:25px;"><img src="./img/more.png"></span>
                                <span class="gengduo"><a href="/oa/oa/index/viewArticleList?messageType=2"
                                                         class="gengduo">更多</a></span>
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

                <div class="one_a boxShadow" style="margin-left: 40px;">
                    <div class="borderTitle">
                        <div class="border_left" style="border-left: 5px solid #347BB7">工作信息</div>
                        <div class="border_right">
                            <div>
                                <%--                        <span class="jia">+</span>--%>
                                <span class="jia" style="display: block;position: relative;top:25px;"><img src="./img/more.png"></span>
                                <span class="gengduo"><a href="/oa/oa/index/viewArticleList?messageType=4"
                                                         class="gengduo">更多</a></span>
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
            </div>
            <div class="newLeft" style="width: 600px;">
                <div class="oneLine">
                    <div class="one_a boxShadow">
                        <div class="borderTitle">
                            <div class="border_left" style="border-left: 5px solid #347BB7">待办事项</div>
                            <div class="border_right">
                                <div>
                                    <%--                                    <span class="jia">+</span>--%>
                                    <span class="jia" style="display: block;position: relative;top:25px;"><img
                                            src="./img/more.png"></span>
                                    <span class="gengduo"><a href="sys/message/list" class="gengduo">更多</a></span>
                                </div>
                            </div>
                        </div>
                        <div class="circularBigBox">
                            <div class="circularBox">
                                <div class="circular">
                                    <div style="font-size: 22px;font-weight: bold">{{shenhe}}</div>
                                </div>
                                <div class="circularTitle">
                                    待审核申请
                                </div>
                            </div>
                            <div class="circularBox">
                                <div class="circular">
                                    <div style="font-size: 22px;font-weight: bold">{{duban}}</div>
                                </div>
                                <div class="circularTitle">
                                    待督办事项
                                </div>
                            </div>
                            <div class="circularBox">
                                <div class="circular">
                                    <div style="font-size: 22px;font-weight: bold">{{bohui}}</div>
                                </div>
                                <div class="circularTitle">
                                    被驳回事项
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="oneLine">
                    <div class="one_a boxShadow">
                        <div class="borderTitle">
                            <div class="border_left" style="border-left: 5px solid #347BB7">12345案件统计</div>
                            <div class="border_right">
                                <div>
                                    <%--                                    <span class="jia">+</span>--%>
                                    <span class="jia" style="display: block;position: relative;top:25px;"><img
                                            src="./img/more.png"></span>
                                    <span class="gengduo" onclick="goto12345()">更多</span>
                                </div>
                            </div>
                        </div>
                        <div>
                            <div id="main-number"
                                 style="width: 200px;height:200px;float: right;z-index: 9999999;top: 20px;right: 40px"></div>
                            <div id="main-dept"
                                 style="width: 200px;height:200px; float: left;z-index: 9999999;top: 20px;left: 40px"></div>
                        </div>
                    </div>
                </div>
                <%--href="index"--%>
                <div class="oneLine" style="height: 369px;">
                    <div class="one_a boxShadow" style="height: 369px;">
                        <div class="borderTitle">
                            <div class="border_left" style="border-left: 5px solid #347BB7">事项通知</div>
                            <div class="border_right">
                                <div>
                                    <%--                        <span class="jia">+</span>--%>
                                    <span class="jia" style="display: block;position: relative;top:25px;"><img
                                            src="./img/more.png"></span>
                                    <span class="gengduo"><a class="gengduo" onclick="doTaskList()">更多</a></span>
                                </div>
                            </div>
                        </div>
                        <div class="eventIndex">
                            <div class="articleItem" v-for="(item,index) in shijian" :key="index" onclick="doTaskList()">
                                <div class="">{{index + 1}}、{{item.content}}</div>
                                <!-- <div class="eventTitle"></div>
                                <div class="eventMsg"></div> -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 第三行 -->
    <div class="oneLine">


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
            <div class="gbspan" style="font-weight: bolder " ; onclick="closes()"><span><button>关闭</button></span></div>
            <div class="tzspan" style="font-weight: bolder" onclick="jieshou()">
                <button>接收通知</button>
            </div>
            <div id="jqGrid_wrapper" class="jqGrid_wrapper" style="width: 600px">
            </div>
        </div>
    </div>
</div>
</div>
<div id="rbboxs" hidden="true">
    <div class="notification" style="width: 600px">
        <div class="info">
            <div class="gbspan" style="font-weight: bolder " ; onclick="closess()"><span><button>关闭</button></span>
            </div>
            <div class="tzspan" style="font-weight: bolder" onclick="jieshou()">
                <button>接收通知</button>
            </div>
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

    $.post("oa/index/getIndexImg", function (data) {
        for(var i=0;i<data.length;i++){
            var ui="<img src="+data[i]+" alt="+(i+1)+"/>";
            $("#listImg").append(ui);
        }
    });


    function doTaskList(){
        debugger
        setTimeout(function(){
            window.open('flow/biz/task/list');
        }, 1000);
        window.location.href= "index";
    }
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
        var imgCount = 5;
        var index = 1;
        var intervalId;
        var buttonSpan = $('.pointer')[0].children;//htmlCollection 集合
        //自动轮播功能 使用定时器
        autoNextPage();
        function autoNextPage(){
            intervalId = setInterval(function(){
                nextPage(true);
            },6000);
        }
        //当鼠标移入 停止轮播
        $('.container').mouseover(function(){
            console.log('hah');
            clearInterval(intervalId);
        });
        // 当鼠标移出，开始轮播
        $('.container').mouseout(function(){
            autoNextPage();
        });
        //点击下一页 上一页的功能
        $('#left').click(function(){
            debugger
            nextPage(false);
        });
        $('#right').click(function(){
            debugger
            nextPage(true);
        });

        //小圆点的相应功能 事件委托
        clickButtons();
        function clickButtons(){
            var length = buttonSpan.length;
            for(var i=0;i<length;i++){
                buttonSpan[i].onclick = function(){
                    $(buttonSpan[index-1]).removeClass('on');
                    if($(this).attr('index')==1){
                        index = 5;
                    }else{
                        index = $(this).attr('index')-1;
                    }
                    nextPage(true);

                };
            }
        }
        function nextPage(next){
            var targetLeft = 0;
            //当前的圆点去掉on样式
            $(buttonSpan[index-1]).removeClass('on');
            if(next){//往后走
                if(index == 5){//到最后一张，直接跳到第一张
                    targetLeft = 0;
                    index = 1;
                }else{
                    index++;
                    targetLeft = -1084*(index-1);
                }

            }else{//往前走
                if(index == 1){//在第一张，直接跳到第五张
                    index = 5;
                    targetLeft = -1084*(imgCount-1);
                }else{
                    index--;
                    targetLeft = -1084*(index-1);
                }

            }
            $('.list').animate({left:targetLeft+'px'});
            //更新后的圆点加上样式
            $(buttonSpan[index-1]).addClass('on');
        }
        getAll();
        getgr();
    }
    var dom = new Vue({
        el: "#app",
        data() {
            return {
                oneList: [],
                twoList: [],
                threeList: [],
                fourList: [],
                fiveList: [],
                selectMsg: '',
                selectHold: true,
                relName: '',
                shijian: [],
                count: [],
                duban: 0,
                shenhe: 0,
                bohui: 0,
            }
        },
        mounted() {
                this.oneMounted(),
                this.twoMounted(),
                this.threeMounted(),
                this.fourMounted(),
                this.fiveMounted(),
                this.shijianMounted(),
                this.getUser(),
                this.getCounts()
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
                // axios.get('oa/login/messageList?messageType=' + 4 + '&rows=' + 6).then((msg) => {
                //     this.fiveList = msg.data.obj.records
                // })
                axios.get('oa/login/messageList?messageType=' + 5 + '&rows=' + 6).then((msg) => {
                    this.fiveList = msg.data.obj.records
                })
            },
            getCounts() {
                axios.get('oa/index/workCount').then((msg) => {
                    console.log(msg)
                    console.log(msg.data.obj)
                    this.duban = msg.data.obj[0]
                    this.shenhe = msg.data.obj[1]
                    this.bohui = msg.data.obj[2]
                })
            },
            shijianMounted() {
                axios.get('oa/index/msgList?rows=' + 6).then((msg) => {
                    this.shijian = msg.data.obj.records
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
    function goto12345() {
        $.ajax({
            type: "post",
            url: 'oa/goto12345',//目标地址
            success: function (data) {
                if (data.success) {
                    // window.location.href = data.obj;
                    window.open(data.obj);
                } else {
                    qhTipWarning(data.msg);
                }
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
<style>
    *{
        padding:0;
        margin:0;
    }
    .container{
        width:1084px;
        height:610px;
        overflow: hidden;
        position:relative;
        margin-top:15px;
        margin-left: 5px;
        float: left;
    }
    .list{
        /*width:3000px;*/
        height:610px;
        position:absolute;

    }
    .list>img{
        float:left;
        width:1084px;
        height:610px;
    }
    .pointer{
        position:absolute;
        width:150px;
        bottom:20px;
        left:520px;
    }
    .pointer>span{
        cursor:pointer;
        display:inline-block;
        width:20px;
        height:20px;
        background: #7b7d80;
        border-radius:50%;
        border:1px solid #fff;
    }
    .pointer .on{
        background: #28a4c9;
    }
    .arrow{
        position:absolute;
        text-decoration:none;
        width:40px;
        height:40px;
        background: #727d8f;
        color:#fff;
        font-weight: bold;
        line-height:40px;
        text-align:center;
        top:180px;
        display:none;
    }
    .arrow:hover{
        background: #0f0f0f;
    }
    .left{
        left:0;
    }
    .right{
        right:0;
    }
    .container:hover .arrow{
        display:block;
    }
</style>

</html>