<!DOCTYPE html>
<html lang="en">
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
            $("#zyxq").click(function () {
                $(".gl").attr("style", "display:none");
                $(".zuoyexiangqing").attr("style", "display:block");
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
                                window.location = "/workDetailPage"
                            } else if (data.indexOf("退出失败") != -1) {
                                alert("退 出 失 败 !")
                            }
                        }
                    }
                })
            })

            // 点击写作业窗口关闭触发事件
            $("#closeSaveSubmitWorkWindow").click(function () {
                $("#submitWorkContentError").html("&nbsp&nbsp*")
                $("#submitWorkContent").val("")
            })

            // 回到班级管理页面
            $("#goBackBrowseClazzPage").click(function () {
                window.location = "/browseClazzPage"
            })
        })

        // 点击加入班级触发事件
        function studentJoinClazz(studentId, clazzId, clazzName) {
            $.ajax({
                url: "/student/studentJoinClazz",
                type: "post",
                data: JSON.stringify({
                    "studentId": studentId,
                    "clazzId": clazzId,
                    "clazzName": clazzName
                }),
                contentType: "application/json;charset=utf-8",
                statusCode: {
                    200: function (data) {
                        if (data.indexOf("加入成功") != -1) {
                            alert("加 入 成 功 !")
                            window.location = "/workDetailPage"
                        } else if (data.indexOf("加入失败") != -1) {
                            alert("加 入 失 败 !")
                        }
                    }
                }
            })
        }

        // 点击写作业触发事件
        function addWorkRelation(workId, workTitle, workContent, studentId) {
            $.ajax({
                url: "/submitWork/querySubmitWorkContent",
                type: "post",
                data: JSON.stringify({
                    "workId": workId,
                    "studentId": studentId
                }),
                contentType: "application/json;charset=utf-8",
                statusCode: {
                    200: function (data) {
                        $("#workId").val(workId)
                        $("#workTitle").val(workTitle)
                        $("#workContent").val(workContent)
                        $("#submitWorkContent").val(data)
                    }
                }
            })
        }

        // 点击保存触发事件
        function saveSubmitWork(studentId, clazzId) {
            if ($("#submitWorkContent").val() == "") {
                $("#submitWorkContentError").html("&nbsp&nbsp提 交 作 业 内 容 不 能 为 空 !")
                return
            } else {
                $.ajax({
                    url: "/submitWork/edit",
                    type: "post",
                    data: JSON.stringify({
                        "submitWorkContent": $("#submitWorkContent").val(),
                        "workId": $("#workId").val(),
                        "studentId": studentId
                    }),
                    contentType: "application/json;charset=utf-8",
                    statusCode: {
                        200: function (data) {
                            if (data.indexOf("修改成功") != -1) {
                                alert("保 存 成 功 !")
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
                            } else if (data.indexOf("重复率偏大") != -1) {
                                var datas = data.split("+_+")
                                alert("您的重复率为" + datas[1] + "超出了老师给定的重复率。请再次修改")
                            }
                        }
                    }
                })
            }
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
                    <a class="btn btn-default" href="/">请您先去登陆!</a>
                </#if>
            </p>
            <p>
                <#if studentDto??>
                    <button class="btn btn-danger" id="logout">退出登陆</button>
                </#if>
            </p>
            <p>
                <#if studentDto??>
                    <#if studentDto.clazzName??>
                        班级: ${studentDto.clazzName}
                    <#else>
                        无班级
                    </#if>
                </#if>
            </p>
        </div>
        <div class="meun-title">学生操作</div>
        <div class="meun-item meun-item-active" id="zyxq">作业详情</div>
    </div>


    <div id="rightContent">
        <!--发布作业详情-->
        <div class="gl zuoyexiangqing">
            <div class="cx">
                <div class="input-group">
                    <input placeholder="输入作业题目查询" class="form-control right-ss"/>
                    <span class="input-group-btn"><button class="btn btn-default right-ss">查询</button></span>
                </div>
            </div>
            <div class="biao">
                <table class="table table-striped">
                    <colgroup>
                        <col style="width: 14%">
                        <col style="width: 14%">
                        <col style="width: 14%">
                        <col style="width: 14%">
                        <col style="width: 14%">
                        <col style="width: 14%">
                        <col style="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>作业id</th>
                        <th>作业题目</th>
                        <th>课程名称</th>
                        <th>教师名称</th>
                        <th>创建日期</th>
                        <th>得分</th>
                        <#if studentDto??>
                            <th>操作</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                        <#if workRelationDtos??>
                            <#list workRelationDtos as workRelationDto>
                                <tr>
                                    <td>${workRelationDto.workId}</td>
                                    <td>${workRelationDto.workTitle}</td>
                                    <td>${workRelationDto.courseName}</td>
                                    <td>${workRelationDto.teacherName}</td>
                                    <td>${workRelationDto.createTime?string("yyyy-MM-dd")}</td>
                                    <td>
                                        <#if workRelationDto.score??>
                                            ${workRelationDto.score}
                                        <#else>
                                            --
                                        </#if>
                                    </td>
                                    <#if studentDto??>
                                        <td>
                                            <button class="btn btn-primary"
                                                    data-toggle="modal"
                                                    onclick="addWorkRelation('${workRelationDto.workId}','${workRelationDto.workTitle}','${workRelationDto.workContent}','${studentDto.studentId}')"
                                                    data-target="#tijiaozuoye">
                                                写作业
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
                                                            onclick="studentJoinClazz(
                                                                    '${studentDto.studentId}',
                                                                    '${clazzDto.clazzId}',
                                                                    '${clazzDto.clazzName}')">
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
    <#--提交作业-->
        <div class="modal fade" id="tijiaozuoye" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="main">
                        <h3 class="page-title">提交作业</h3>
                    <#--提交作业表单-->
                        <form>
                            <div class="form-container">
                                <div class="form-group">
                                    作业题目：
                                    <input type="text" class="form-control" id="workTitle" disabled/>
                                </div>
                                <div class="form-group">
                                    作业内容：
                                    <textarea disabled rows="5" cols="30" class="form-control" id="workContent"></textarea>
                                </div>
                                <div class="form-group">
                                    提交作业内容:
                                    <textarea rows="10" cols="30" class="form-control" id="submitWorkContent"></textarea>
                                    <span id="submitWorkContentError" style="color: red">&nbsp&nbsp*</span>
                                </div>
                                <input type="hidden" name="workId" id="workId"/>
                            </div>
                        </form>
                    <#--将保存按钮放在表单外，若不这样不能通过js阻塞表单提交-->
                        <div class="form-container">
                            <div class="form-group">
                                <#if studentDto?? && clazzId??>
                                    <button class="btn btn-primary btn-block form-container" onclick="saveSubmitWork('${studentDto.studentId}','${clazzId}')">
                                        保存
                                    </button>
                                </#if>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"
                                    id="closeSaveSubmitWorkWindow">
                                关闭
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="dao">
            <nav aria-label="Page navigation">
                <button class="btn btn-primary" id="goBackBrowseClazzPage">
                    主页面
                </button>
            </nav>
        </div>
    </div>
</div>
</body>
</html>