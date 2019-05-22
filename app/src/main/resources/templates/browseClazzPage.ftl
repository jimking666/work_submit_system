<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生端</title>
    <link rel="stylesheet" type="text/css" href="css/common.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <style>
        /*登录界面样式*/
        .form-container {
            width: 300px;
            margin: 0 auto;
        }

        .page-title {
            text-align: center;
        }
    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="js/jquery-3.3.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script>
        $(function () {
            $(".meun-item").click(function () {
                $(".meun-item").removeClass("meun-item-active");
                $(this).addClass("meun-item-active");
            })
            $("#llbj").click(function () {
                $(".gl").attr("style", "display:none");
                $(".liulanbanji").attr("style", "display:block");
            })
            // 点击退出登陆触发事件
            $("#logout").click(function () {
                $.ajax({
                    url: '/student/logout',
                    type: 'get',
                    statusCode: {
                        200: function (data) {
                            window.location = "/"
                        }
                    }
                })
            })
            // 点击确认退出班级触发事件
            $("#quitClazz").click(function () {
                $.ajax({
                    url: "/student/studentQuitClazz",
                    type: "post",
                    data: JSON.stringify({
                        "studentId": $("#studentId").val()
                    }),
                    contentType: "application/json;charset=utf-8",
                    statusCode: {
                        200: function (data) {
                            if (data.indexOf("退出成功") != -1) {
                                alert("退 出 成 功 !")
                                window.location = "/browseClazzPage"
                            } else if (data.indexOf("退出失败") != -1) {
                                alert("退 出 失 败 !")
                            }
                        }
                    }
                })
            })
            // 搜索班级
            $("#searchClazz").click(function () {
                $.ajax({
                    url: "/clazz/getClazzBySearch",
                    type: "post",
                    data: JSON.stringify({
                        "searchClazzName": $("#searchClazzName").val()
                    }),
                    contentType: "application/json;charset=utf-8",
                    statusCode: {
                        200: function (data) {
                            if (data.indexOf("查询成功") != -1) {
                                window.location = "/browseClazzPage"
                            }
                        }
                    }
                })
            })
        })
        // 点击加入班级触发事件
        function studentJoinClazz(studentId, clazzId) {
            $.ajax({
                url: "/student/studentJoinClazz",
                type: "post",
                data: JSON.stringify({
                    "studentId": studentId,
                    "clazzId": clazzId
                }),
                contentType: "application/json;charset=utf-8",
                statusCode: {
                    200: function (data) {
                        if (data.indexOf("加入成功") != -1) {
                            alert("加 入 成 功 !")
                            window.location = "/browseClazzPage"
                        } else if (data.indexOf("加入失败") != -1) {
                            alert("加 入 失 败 !")
                        }
                    }
                }
            })
        }
        // 点击退出班级触发事件
        function addStudentId(studentId) {
            $("#studentId").val(studentId)
        }
        // 点击学生详情触发事件
        function studentDetail(clazzId) {
            $.ajax({
                url: "/student/studentDetail",
                type: "post",
                data: JSON.stringify({
                    "clazzId": clazzId
                }),
                contentType: "application/json;charset=utf-8",
                statusCode: {
                    200: function (data) {
                        if (data.indexOf("学生详情查询成功") != -1) {
                            window.location = "/studentDetailPage"
                        }
                    }
                }
            })
        }
        // 点击做作业触发事件
        function doHomeWork(clazzId, studentId) {
            $.ajax({
                url: "/work/queryWorkRelationDto",
                type: "post",
                data: JSON.stringify({
                    "clazzId": clazzId,
                    "studentId": studentId
                }),
                contentType: "application/json;charset=utf-8",
                statusCode: {
                    200: function (data) {
                        if (data.indexOf("作业关联查询成功") != -1) {
                            window.location = "/workDetailPage"
                        }
                    }
                }
            })
        }
    </script>
</head>
<body>
<div id="wrap">
    <div class="leftMeun show" id="leftMeun">
        <div id="logoDiv">学生端</div>
        <div id="personInfor">
            <p>
                <#if studentDto??>
                    学生: ${studentDto.studentName}
                <#else>
                    <a class="btn btn-default" href="/">请您先去登录!</a>
                </#if>
            </p>
            <p>
                <#if studentDto??>
                    <#if studentDto.clazzName??>
                        班级: ${studentDto.clazzName}
                    </#if>
                </#if>
            </p>
        </div>
        <div class="meun-title">当前页面</div>
        <div class="meun-item meun-item-active" id="llbj">浏览班级</div>
        <div class="meun-title">学生操作</div>
        <div class="meun-title">
            <#if studentDto??>
                <button class="btn btn-danger" id="logout">退出登录</button>
            </#if>
        </div>
        <div class="meun-title">
            <#if studentDto??>
                <#if studentDto.clazzName??>
                    <button class="btn btn-danger" data-toggle="modal" data-target="#tuichubanji"
                            onclick="addStudentId('${studentDto.studentId}')">
                        退出班级
                    </button>
                <#else>
                    <button class="btn btn-primary" data-toggle="modal" data-target="#jiarubanji">
                        选择班级
                    </button>
                </#if>
            </#if>
        </div>
        <div class="meun-title">
            <#if studentDto??>
                <#if studentDto.clazzName??>
            <button class="btn btn-primary" onclick="doHomeWork('${studentDto.clazzId}','${studentDto.studentId}')">
                &nbsp&nbsp做作业
            </button>
                </#if>
            </#if>
        </div>
    </div>

    <div id="rightContent">
        <div class="gl liulanbanji">
            <div class="cx">
                <div class="input-group">
                    <input placeholder="输入班级名查询" class="form-control right-ss" id="searchClazzName"/>
                    <span class="input-group-btn"><button class="btn btn-default right-ss" id="searchClazz">查询</button></span>
                </div>
            </div>
            <div class="biao">
                <table class="table table-striped table-hover">
                    <colgroup>
                        <col style="width: 20%">
                        <col style="width: 20%">
                        <col style="width: 20%">
                        <col style="width: 20%">
                        <col style="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>班级id</th>
                        <th>班级名称</th>
                        <th>学生数量</th>
                        <th>创建日期</th>
                        <#if studentDto??>
                            <th>操作</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#if clazzDtos??>
                        <#list clazzDtos as clazzDto>
                        <tr>
                            <td>${clazzDto.clazzId}</td>
                            <td>${clazzDto.clazzName}</td>
                            <td>${clazzDto.studentCount}</td>
                            <td>${clazzDto.createTime?string("yyyy-MM-dd")}</td>
                            <#if studentDto??>
                                <td>
                                    <button class="btn btn-default"
                                            onclick="studentDetail('${clazzDto.clazzId}')">学生详情
                                    </button>
                                </td>
                            </#if>
                        </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>

    <#--离开班级-->
        <div class="modal fade" id="tuichubanji" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">警告</h4>
                    </div>
                    <div class="modal-body">
                        <p>确 定 退 出 此 班 级 ？</p>
                    </div>
                    <form>
                        <input type="hidden" name="studentId" id="studentId">
                    </form>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            取消
                        </button>
                        <button type="button" class="btn btn-danger" id="quitClazz">
                            确定退出
                        </button>
                    </div>
                </div>
            </div>
        </div>

    <#--加入班级-->
        <div class="modal fade" id="jiarubanji" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">加入班级</h4>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped">
                            <colgroup>
                                <col style="width: 30%">
                                <col style="width: 30%">
                                <col style="width: 30%">
                                <col style="">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>班级id</th>
                                <th>班级名称</th>
                                <th>创建日期</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                    <#if clazzDtos??>
                                        <#list clazzDtos as clazzDto>
                                            <tr>
                                                <td>${clazzDto.clazzId}</td>
                                                <td>${clazzDto.clazzName}</td>
                                                <td>${clazzDto.createTime?string("yyyy-MM-dd")}</td>
                                                <td>
                                                    <#if studentDto??>
                                                        <button class="btn btn-primary" data-toggle="modal"
                                                                onclick="studentJoinClazz('${studentDto.studentId}','${clazzDto.clazzId}')">
                                                            加入
                                                        </button>
                                                    </#if>
                                                </td>
                                            </tr>
                                        </#list>
                                    </#if>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            关闭
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <#--<div class="dao">-->
            <#--<nav aria-label="Page navigation">-->
                <#--<ul class="pagination">-->
                    <#--<li>-->
                        <#--<a href="#" aria-label="Previous">-->
                            <#--<span aria-hidden="true">&laquo;</span>-->
                        <#--</a>-->
                    <#--</li>-->
                    <#--<li><a href="#">1</a></li>-->
                    <#--<li><a href="#">2</a></li>-->
                    <#--<li><a href="#">3</a></li>-->
                    <#--<li><a href="#">4</a></li>-->
                    <#--<li><a href="#">5</a></li>-->
                    <#--<li>-->
                        <#--<a href="#" aria-label="Next">-->
                            <#--<span aria-hidden="true">&raquo;</span>-->
                        <#--</a>-->
                    <#--</li>-->
                <#--</ul>-->
            <#--</nav>-->
        <#--</div>-->
    </div>
</div>
</body>
</html>
