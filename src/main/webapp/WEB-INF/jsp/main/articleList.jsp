<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <%--<t:base type="default"></t:base>--%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
</head>
<script src="./vue.min.js"></script>
<link rel="stylesheet" href="./element.css">

<!-- 引入组件库 -->
<script src="./element.js"></script>
<script src="./axios.min.js"></script>

<style>
    * {
        margin: 0;
        padding: 0;
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
        /* display: flex; */
        /* margin-left: 45px; */
        height: 28px;
        position: relative;
        top: 0;
        bottom: 0;
        margin: auto;
        margin-left: 45px;
        margin-right: 0;
        border: 1px solid #000;
    }


    .selectBtn {
        display: block;
        width: 100px;
        position: relative;
        top: 0;
        bottom: 0;
        margin: auto;
        margin-left: 0;
        height: 28px;
        text-align: center;
        font-size: 20px;
        color: #FFFFFF;
        background: #E60012;
        line-height: 28px;
        border: 1px solid #000;
    }

    .tabs {
        width: 100%;
        height: 40px;
        background: #347BB7;
    }

    .tabsBox {
        margin: 0 auto;
        display: flex;
        text-align: center;
        justify-content: center;
    }

    .tabs_item {
        width: 70px;
        height: 30px;
        margin-top: 10px;
        line-height: 30px;
        color: #FFFFFF;
        font-weight: 500;
        font-size: 16px;
        margin-left: 16px;

    }

    .tabs_item:hover {

        background: #296190;
    }

    .active {
        background: #FFFFFF;
        color: #347BB7;
    }

    .active:hover {
        background: #FFFFFF;
        color: #347BB7;
    }

    .tabTitle {
        /* width: 100%; */
        height: 60px;
        background: #F1F9FF;
        margin-top: 10px;
        line-height: 60px;
        padding-left: 130px;
    }

    .tabs1Center {
        height: 580px;
        border-bottom: 1px solid #CCCCCC;
        width: 70%;
        margin: 0 auto;
    }

    .tabs1List {
        position: relative;
        display: flex;
        height: 40px;
        border-bottom: 1px dashed #CCCCCC;
        line-height: 40px;
        justify-content: space-between;
        margin-top: 10px;
    }

    .tabs1List_dian {
        width: 6px;
        height: 6px;
        position: absolute;
        top: 0;
        bottom: 0;
        margin: auto 0;
        background: #808080;
        border-radius: 50%;
    }

    .tabs1List_name {
        margin-left: 26px;
        color: #4C4C4C;
        max-width: 300px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .tabs1List_name:hover {
        color: #D30404;
    }

    .tabs1List_right {
        margin-right: 10px;
        font-size: 16px;
        color: #000000;
    }

    .tabs1Page {
        width: 70%;
        margin: 0 auto;
        text-align: right;
        margin-top: 5px;
    }

    .friendBox {
        border-top: 1px solid #CCCCCC;
        margin-top: 40px;
        padding-left: 40px;
    }

    .friendTxt {
        font-size: 16px;
        color: #7F7F80;
        line-height: 60px;

    }

    .friendHerf {
        display: inline-block;
        height: 32px;
        color: #FFFFFF;
        background: #2F4050;
        margin-left: 30px;
        line-height: 32px;
        padding: 0 5px;
        text-decoration: none;
    }

    .nava {
        color: #2F4050;
        text-decoration: none;
    }
</style>

<body>
<div id="app">
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
    <div class="tabs">
        <div class="tabsBox">
            <div class="tabs_item" v-for="(item,index) in tabs" :key="index" :class="{'active': isActive == index}"
                 @click="tabsClick(item,index)">
                {{item.name}}
            </div>
        </div>
    </div>
    <div class="tab1">
        <%--<div class="tabTitle">
                <span class="tabPath">
                    您现在的位置：
                </span>
            <span class="tabPath">
                    首页
                </span>
            >
            <span class="tabPath">

                    {{loginName}}

                </span>

            <span class="tabPath" v-if="zhengwen">

                    > 正文

                </span>

        </div>--%>
        <div class="tabs1Center" v-if="!zhengwen">
            <div class="tabs1List" v-for="(item,index) in titleList" :key="index" @click="listClick(item)">
                <div class="tabs1List_dian">

                </div>
                <div class="tabs1List_name">
                    {{item.title}}
                </div>
                <div class="tabs1List_right">
                    {{item.publicTime}} 阅{{item.count}}次
                </div>
            </div>
        </div>
        <div class="tabs1Page" v-if="!zhengwen">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                           @prev-click="prevClick" @next-click="nextClick"
                           :current-page="currentPage4" :page-sizes="[20,40]" :page-size="pageSize"
                           layout="total, sizes, prev, pager, next" :total="totalSize">
            </el-pagination>
        </div>
    </div>
    <div class="tabs1Center" v-if="zhengwen">
        <div class="tab1Zhengwen" v-if="zhengwen">
            <p class="tab1_zwTitle" style="text-align: center;font-size: 30px;padding-bottom: 10px">
                {{tab1zwMsg.title}}
            </p>
            <p class="tab1_zwDate" style="text-align: center;padding-bottom: 10px">
                {{tab1zwMsg.publicTime}}
            </p>
            <p class="tab1_zwVal" style="font-size: 20px">
                {{tab1zwMsg.content}}
            </p>
        </div>
    </div>

    <div class="friendBox">
        <span class="friendTxt"> 链接</span>
        <a class="friendHerf" href="http://www.chy.egov.cn/"> 朝阳政务</a>
        <a class="friendHerf" href="http://www.bjchy.gov.cn/"> 朝阳区政务网</a>
        <a class="friendHerf" href="http://bgpc.beijing.gov.cn/"> 市政府采购中心</a>
        <a class="friendHerf" href="http://3d.bjchy.gov.cn"> 云享朝阳·感知互动体验中心</a>
        <a class="friendHerf" href="https://www.xuexi.cn/"> 学习强国</a>
        <a class="friendHerf" href="http://www.chy.egov.cn/qbm-5.html"> 区保密局提示</a>
    </div>
</div>
</body>
<script>
    var messageType = ${messageType};

    var id = '${getOne}';

    var massages = [];


    /* {
         name: "双井图库"
     },
 */
    var dom = new Vue({
        el: '#app',
        data() {
            return {
                input2: '',
                isActive: 1,
                loginName: '',
                tabs: [{
                    name: "公式文件",
                    messageType: 1,
                },
                    {
                        name: "通知公告",
                        messageType: 2,
                    },
                    {
                        name: "媒体聚焦",
                        messageType: 3,
                    },
                    {
                        name: "工作信息",
                        messageType: 4,
                    },
                    {
                        name: "制度范围",
                        messageType: 5,
                    }
                ],
                currentPage4: 1,
                pageSize: 20,
                pageIndex: 1,
                titleList: [],
                totalSize: 20,
                zhengwen: false,
                tab1zwMsg: {
                    title: "标题",
                    publicTime: "2020-08-08",
                    content: "念佛阿红i我那就理解的 赛哦就打算就 撒娇打击撒泼大数据所惧怕就得跑收到啦降低记得啦时间"
                }
            }
        },
        mounted() {
            this.getTitList();

            if (id == 0) {
                console.log(0)
            } else {
                console.log(1)
                this.loginForThis(id);
            }
        },
        methods: {
            getTitList() {
                axios.get('oa/login/messageList?messageType=' + messageType).then((msg) => {
                    console.log(msg)
                    console.log('getTitList')
                    this.loginName = this.tabs[messageType - 1].name;
                    this.isActive = messageType - 1;
                    this.titleList = msg.data.obj.records;
                    this.totalSize = msg.data.obj.total;
                })
            },
            tabsClick(item, index) {
                axios.get('oa/login/messageList?messageType=' + item.messageType + '&rows=' + this.pageSize).then((msg) => {
                    messageType = item.messageType;
                    this.isActive = index;
                    this.loginName = item.name;
                    this.titleList = msg.data.obj.records;
                    this.totalSize = msg.data.obj.total;
                    this.zhengwen = false;

                })
            },
            handleSizeChange(val) {
                axios.get('oa/login/messageList?messageType=' + messageType + '&rows=' + val).then((msg) => {
                    this.loginName = this.tabs[messageType - 1].name;
                    this.isActive = messageType - 1;
                    this.titleList = msg.data.obj.records;
                    this.pageSize = val;
                })
            },
            handleCurrentChange(val) {
                axios.get('oa/login/messageList?messageType=' + messageType + '&rows=' + this.pageSize + '&page=' + val).then((msg) => {
                    this.loginName = this.tabs[messageType - 1].name;
                    this.isActive = messageType - 1;
                    this.titleList = msg.data.obj.records;
                    this.pageIndex = val;
                })
            },
            prevClick(val) {
                console.log(val)
                axios.get('oa/login/messageList?messageType=' + messageType + '&rows=' + this.pageSize + '&page=' + val).then((msg) => {
                    this.loginName = this.tabs[messageType - 1].name;
                    this.isActive = messageType - 1;
                    this.titleList = msg.data.obj.records;
                    this.pageIndex = val;
                })
            },
            nextClick(val) {
                console.log(val)
                axios.get('oa/login/messageList?messageType=' + messageType + '&rows=' + this.pageSize + '&page=' + val).then((msg) => {
                    this.loginName = this.tabs[messageType - 1].name;
                    this.isActive = messageType - 1;
                    this.titleList = msg.data.obj.records;
                    this.pageIndex = val;
                })
            },
            listClick(item) {
                axios.get('oa//login/getArticle?id=' + item.id).then((msg) => {
                    this.zhengwen = true;
                    this.tab1zwMsg = msg.data.obj;
                })

            },
            loginForThis(item) {
                axios.get('oa/login/getArticle?id=' + item).then((msg) => {
                    console.log('loginForThis')
                    this.loginName = this.tabs[messageType - 1].name;
                    this.isActive = messageType - 1;
                    this.zhengwen = true;
                    this.tab1zwMsg = msg.data.obj;
                })

            },
            goBack() {
                this.zhengwen = false;
            }
        }
    })
</script>

</html>