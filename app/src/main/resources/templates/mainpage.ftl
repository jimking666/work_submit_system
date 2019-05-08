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
            $("#yhgl").click(function () {
                $(".gl").attr("style", "display:none");
                $(".yonghugl").attr("style", "display:block");
            })
            $("#spgl").click(function () {
                $(".gl").attr("style", "display:none");
                $(".shangpingl").attr("style", "display:block");
            })
            $("#sptpgl").click(function () {
                $(".gl").attr("style", "display:none");
                $(".shangpingtupiangl").attr("style", "display:block");
            })
            $("#spflgl").click(function () {
                $(".gl").attr("style", "display:none");
                $(".shangpinfenleigl").attr("style", "display:block");
            })
            $("#ddgl").click(function () {
                $(".gl").attr("style", "display:none");
                $(".dingdangl").attr("style", "display:block");
            })
            $("#yhqqgl").click(function () {
                $(".gl").attr("style", "display:none");
                $(".yonghuqingqiugl").attr("style", "display:block");
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
                                window.location = "/mainpage"
                            } else if (data.indexOf("退出失败") != -1) {
                                alert("退 出 失 败 !")
                            }
                        }
                    }
                })
            })
        })

        // 点击加入班级触发事件
        function studentJoinClazz(studentId ,clazzId, clazzName) {
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
                            window.location = "/mainpage"
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
                        <button class="btn btn-danger" data-toggle="modal" data-target="#tuichubanji"
                                onclick="addStudentId('${studentDto.studentId}')">
                            &nbsp&nbsp退出班级
                        </button>
                    <#else>
                        <button class="btn btn-primary" data-toggle="modal" data-target="#jiarubanji">
                            选择班级
                        </button>
                    </#if>
                </#if>
            </p>

        </div>
        <div class="meun-title">学生操作</div>
        <div class="meun-item meun-item-active" id="yhgl">用户管理</div>
        <div class="meun-item" id="spgl">商品管理</div>
        <div class="meun-item" id="sptpgl">商品图片管理</div>
        <div class="meun-item" id="spflgl">商品分类管理</div>
        <div class="meun-item" id="ddgl">订单管理</div>
        <div class="meun-item" id="yhqqgl">用户请求管理</div>
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

    <div id="rightContent">
        <!--用户管理-->
        <div class="gl yonghugl">
            <div class="cx">
                <div class="input-group">
                    <input placeholder="输入用户名查询" class="form-control right-ss"/>
                    <span class="input-group-btn"><button class="btn btn-default right-ss">查询</button></span>
                </div>
            </div>
            <div class="biao">
                <table class="table table-striped table-hover">
                    <colgroup>
                        <col style="width: 16.6%">
                        <col style="width: 16.6%">
                        <col style="width: 16.6%">
                        <col style="width: 16.6%">
                        <col style="width: 16.6%">
                        <col style="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>idNumber</th>
                        <th>用户名</th>
                        <th>真实姓名</th>
                        <th>联系电话</th>
                        <th>地址</th>
                        <th>状态</th>
                        <#if admin??>
                            <th>操作</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#if users??>
                        <#list users as user>
                            <tr>
                                <td>${user.uid}</td>
                                <td>${user.username}</td>
                                <td>${user.realname}</td>
                                <td>${user.phone}</td>
                                <td>${user.useraddress}</td>
                                <td>
                                    <#if user.ustatus == "禁用">
                                        <span style="color: red">禁用</span>
                                    <#else>
                                        <span style="color: green">正常</span>
                                    </#if>
                                </td>
                                <#if admin??>
                                    <td>
                                        <button class="btn btn-default" data-toggle="modal" data-target="#yonghuxg"
                                                onclick="adduid(${user.uid})">修改
                                        </button>
                                        <button class="btn btn-danger" onclick="adduserid(${user.uid})" data-toggle="modal" data-target="#shanchu">
                                            删除
                                        </button>
                                    </td>
                                </#if>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
            <div class="dao">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">6</a></li>
                        <li>
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="modal fade" id="yonghuxg" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">用户修改</h4>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">

                                <form class="form-horizontal" id="updateUserInfo_Form">
                                    <input type="hidden" name="uid" id="uid">
                                    <div class="form-group ">
                                        <label for="sName" class="col-xs-3 control-label">用户名：</label>
                                        <div class="col-xs-8 ">
                                            <input type="text" class="form-control input-sm duiqi" name="username"
                                                   id="username"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sLink" class="col-xs-3 control-label">真实姓名：</label>
                                        <div class="col-xs-8 ">
                                            <input type="text" class="form-control input-sm duiqi" name="realname"
                                                   id="realname"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">电话：</label>
                                        <div class="col-xs-8">
                                            <input type="text" class="form-control input-sm duiqi" name="phone"
                                                   id="phone"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group ">
                                        <label for="sName" class="col-xs-3 control-label">地址：</label>
                                        <div class="col-xs-8">
                                            <input type="text" class="form-control input-sm duiqi" name="useraddress"
                                                   id="useraddress"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="situation" class="col-xs-3 control-label">状态：</label>

                                        <div class="col-xs-8">
                                            <label class="control-label" for="anniu">
                                                <input type="radio" name="ustatus" id="usernormal" value="正常">正常</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="ustatus" id="userforbid" value="禁用">
                                                禁用</label>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="updateUser">
                                保存
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--商品管理-->
        <div class="gl shangpingl" style="display:none">
            <div class="cx">
                <div class="input-group">
                    <input placeholder="输入商品名查询" class="form-control right-ss"/>
                    <span class="input-group-btn"><button class="btn btn-default right-ss">查询</button></span>
                </div>
            </div>
            <div class="biao">
                <table class="table table-striped table-hover">
                    <colgroup>
                        <col style="width: 16.6%">
                        <col style="width: 16.6%">
                        <col style="width: 16.6%">
                        <col style="width: 16.6%">
                        <col style="width: 16.6%">
                        <col style="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>商品名</th>
                        <th>商品介绍</th>
                        <th>价格</th>
                        <th>库存</th>
                        <th>状态</th>
                        <#if admin??>
                            <th>操作</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#if products??>
                        <#list products as product>
                            <tr>
                                <td>${product.pname}</td>
                                <td>${product.description}</td>
                                <td>${product.price}</td>
                                <td>${product.pquantity}</td>
                                <td>
                                    <#if product.pstatus == "缺货">
                                        <span style="color: red">缺货</span>
                                    <#else>
                                        <span style="color: green">正常</span>
                                    </#if>
                                </td>
                                <#if admin??>
                                    <td>
                                        <button class="btn btn-default" data-toggle="modal" data-target="#shangpinxg"
                                                onclick="addProductInfo('${product.pname}','${product.description}','${product.price}','${product.pquantity}','${product.pstatus}')">
                                            修改
                                        </button>
                                        <button class="btn btn-danger" onclick="addProductName(${product.pname})" data-toggle="modal" data-target="#shanchu">
                                            删除
                                        </button>
                                    </td>
                                </#if>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
            <div class="dao">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">6</a></li>
                        <li>
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="modal fade" id="shangpinxg" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">商品修改</h4>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">

                                <form class="form-horizontal" id="updateProductInfo_Form">
                                    <div class="form-group ">
                                        <label for="sName" class="col-xs-3 control-label">商品名：</label>
                                        <div class="col-xs-8 ">
                                            <input type="text" class="form-control input-sm duiqi" id="pname" name="pname" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sLink" class="col-xs-3 control-label">商品介绍：</label>
                                        <div class="col-xs-8 ">
                                            <input type="text" class="form-control input-sm duiqi" id="description" name="description" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">价格：</label>
                                        <div class="col-xs-8">
                                            <input type="text" class="form-control input-sm duiqi" id="price" name="price" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">库存：</label>
                                        <div class="col-xs-8">
                                            <input type="text" class="form-control input-sm duiqi" id="pquantity" name="pquantity" placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="situation" class="col-xs-3 control-label">状态：</label>
                                        <div class="col-xs-8">
                                            <label class="control-label" for="anniu">
                                                <input type="radio" name="pstatus" id="productnormal" value="正常">正常</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="pstatus" id="productforbid" value="缺货">缺货</label>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="updateProduct">
                                保存
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="shangpinxg" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">订单详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <form class="form-horizontal">
                                    <div class="form-group ">
                                        <label for="sName" class="col-xs-3 control-label">商品名：</label>
                                        <div class="col-xs-8 ">
                                            <input type="" class="form-control input-sm duiqi" id="sName"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sLink" class="col-xs-3 control-label">商品介绍：</label>
                                        <div class="col-xs-8 ">
                                            <input type="" class="form-control input-sm duiqi" id="sLink"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">价格：</label>
                                        <div class="col-xs-8">
                                            <input type="" class="form-control input-sm duiqi" id="sKnot"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">库存：</label>
                                        <div class="col-xs-8">
                                            <input type="" class="form-control input-sm duiqi" id="sKnot"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="situation" class="col-xs-3 control-label">状态：</label>
                                        <div class="col-xs-8">
                                            <label class="control-label" for="anniu">
                                                <input type="radio" name="situation" id="normal">正常</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="situation" id="forbid">缺货</label>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                关闭
                            </button>
                            <button type="button" class="btn btn-primary">
                                保存
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--商品图片管理-->
        <div class="gl shangpingtupiangl" style="display:none">
            <div class="cx">
                <div class="input-group">
                    <input placeholder="输入商品名查询" class="form-control right-ss"/>
                    <span class="input-group-btn"><button class="btn btn-default right-ss">查询</button></span>
                </div>
            </div>
            <div class="biao">
                <table class="table table-striped table-hover">
                    <colgroup>
                        <col style="width: 25%">
                        <col style="width: 25%">
                        <col style="width: 25%">
                        <col style="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>商品名</th>
                        <th>列表</th>
                        <th>图片数量</th>
                        <#if admin??>
                            <th>操作</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#shangpintupianxg">修改
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#shangpintupianxg">修改
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#shangpintupianxg">修改
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#shangpintupianxg">修改
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="dao">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">6</a></li>
                        <li>
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="modal fade" id="shangpintupianxg" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">商品图片修改</h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-striped">
                                <tbody>
                                <tr>
                                    <td style="text-align: center;">
                                        <div class="dropdown" style="text-align: center;">
                                            <button type="button" class="btn btn-default dropdown-toggle"
                                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择
                                                <span class="caret"></span></button>
                                            <ul class="dropdown-menu" style="margin-left: 36%;">
                                                <li><a href="#">新增</a></li>
                                                <li role="separator" class="divider"></li>
                                                <li><a href="#">图1</a></li>
                                                <li><a href="#">图2</a></li>
                                                <li><a href="#">图3</a></li>
                                            </ul>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: center;"><img src="img/13.jpg" class="img-responsive"/></td>
                                </tr>
                                <tr>
                                    <td style="text-align: center;"><input type="file" style="margin: 0 auto;"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                关闭
                            </button>
                            <button type="button" class="btn btn-primary">
                                保存
                            </button>
                            <button type="button" class="btn btn-danger">
                                删除
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="shangpinxg" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">订单详情</h4>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <form class="form-horizontal">
                                    <div class="form-group ">
                                        <label for="sName" class="col-xs-3 control-label">商品名：</label>
                                        <div class="col-xs-8 ">
                                            <input type="" class="form-control input-sm duiqi" id="sName"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sLink" class="col-xs-3 control-label">商品介绍：</label>
                                        <div class="col-xs-8 ">
                                            <input type="" class="form-control input-sm duiqi" id="sLink"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">价格：</label>
                                        <div class="col-xs-8">
                                            <input type="" class="form-control input-sm duiqi" id="sKnot"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">库存：</label>
                                        <div class="col-xs-8">
                                            <input type="" class="form-control input-sm duiqi" id="sKnot"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="situation" class="col-xs-3 control-label">状态：</label>
                                        <div class="col-xs-8">
                                            <label class="control-label" for="anniu">
                                                <input type="radio" name="situation" id="normal">正常</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="situation" id="forbid">缺货</label>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                关闭
                            </button>
                            <button type="button" class="btn btn-primary">
                                保存
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--商品分类管理-->
        <div class="gl shangpinfenleigl"></div>
        <!--订单管理-->
        <div class="gl dingdangl" style="display:none">
            <div class="cx">
                <div class="input-group">
                    <input placeholder="输入用户名查询" class="form-control right-ss"/>
                    <span class="input-group-btn"><button class="btn btn-default right-ss">查询</button></span>
                </div>
            </div>
            <div class="biao">
                <table class="table table-striped table-hover">
                    <colgroup>
                        <col style="width: 12.5%">
                        <col style="width: 12.5%">
                        <col style="width: 12.5%">
                        <col style="width: 12.5%">
                        <col style="width: 12.5%">
                        <col style="width: 12.5%">
                        <col style="width: 12.5%">
                        <col style="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>idDd</th>
                        <th>idNumber</th>
                        <th>商品名</th>
                        <th>创建日期</th>
                        <th>总价</th>
                        <th>收货地址</th>
                        <th>状态</th>
                        <#if admin??>
                            <th>操作</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>333</td>
                        <td>3333</td>
                        <td>3333</td>
                        <td>3333</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#dingdanxq">详情</button>
                            <button class="btn btn-default" data-toggle="modal" data-target="#dingdanxg">修改</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#shanchu">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>333</td>
                        <td>3333</td>
                        <td>3333</td>
                        <td>3333</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#dingdanxq">详情</button>
                            <button class="btn btn-default" data-toggle="modal" data-target="#dingdanxg">修改</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#shanchu">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>333</td>
                        <td>3333</td>
                        <td>3333</td>
                        <td>3333</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#dingdanxq">详情</button>
                            <button class="btn btn-default" data-toggle="modal" data-target="#dingdanxg">修改</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#shanchu">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>333</td>
                        <td>3333</td>
                        <td>3333</td>
                        <td>3333</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#dingdanxq">详情</button>
                            <button class="btn btn-default" data-toggle="modal" data-target="#dingdanxg">修改</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#shanchu">删除</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="dao">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">6</a></li>
                        <li>
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="modal fade" id="dingdanxq" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">订单详情</h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-striped">
                                <colgroup>
                                    <col style="width: 25%">
                                    <col style="width: 25%">
                                    <col style="width: 25%">
                                    <col style="">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>商品名</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>总价</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>121</td>
                                    <td>1212</td>
                                    <td>2222</td>
                                    <td>333</td>
                                </tr>
                                <tr>
                                    <td>121</td>
                                    <td>1212</td>
                                    <td>2222</td>
                                    <td>333</td>
                                </tr>
                                <tr>
                                    <td>121</td>
                                    <td>1212</td>
                                    <td>2222</td>
                                    <td>333</td>
                                </tr>
                                <tr>
                                    <td>121</td>
                                    <td>1212</td>
                                    <td>2222</td>
                                    <td>333</td>
                                </tr>
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
            <div class="modal fade" id="dingdanxg" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">订单修改</h4>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">收货地址：</label>
                                        <div class="col-xs-8">
                                            <input type="" class="form-control input-sm duiqi" id="sKnot"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="sKnot" class="col-xs-3 control-label">总价：</label>
                                        <div class="col-xs-8">
                                            <input type="" class="form-control input-sm duiqi" id="sKnot"
                                                   placeholder="">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="situation" class="col-xs-3 control-label">状态：</label>
                                        <div class="col-xs-8">
                                            <label class="control-label" for="anniu">
                                                <input type="radio" name="situation" id="normal">待接单</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="situation" id="forbid">已接单</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="situation" id="forbid">联系快递中</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="situation" id="forbid">送货中</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="situation" id="forbid">已收货</label>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <label class="control-label" for="meun">
                                                <input type="radio" name="situation" id="forbid">被取消订单</label>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                关闭
                            </button>
                            <button type="button" class="btn btn-primary">
                                保存
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--用户请求管理-->
        <div class="gl yonghuqingqiugl" style="display:none">
            <div class="cx">
                <div class="input-group">
                    <input placeholder="输入用户名查询" class="form-control right-ss"/>
                    <span class="input-group-btn"><button class="btn btn-default right-ss">查询</button></span>
                </div>
            </div>
            <div class="biao">
                <table class="table table-striped table-hover">
                    <colgroup>
                        <col style="width: 25%">
                        <col style="width: 25%">
                        <col style="width: 25%">
                        <col style="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>请求创建日期</th>
                        <th>状态</th>
                        <#if admin??>
                            <th>操作</th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#qingqiuxq">详情</button>
                            <button class="btn btn-default" data-toggle="modal" data-target="#chulixg">修改及留言</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#shanchu">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#qingqiuxq">详情</button>
                            <button class="btn btn-default" data-toggle="modal" data-target="#chulixg">修改及留言</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#shanchu">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#qingqiuxq">详情</button>
                            <button class="btn btn-default" data-toggle="modal" data-target="#chulixg">修改及留言</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#shanchu">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td>121</td>
                        <td>1212</td>
                        <td>2222</td>
                        <td>
                            <button class="btn btn-default" data-toggle="modal" data-target="#qingqiuxq">详情</button>
                            <button class="btn btn-default" data-toggle="modal" data-target="#chulixg">修改及留言</button>
                            <button class="btn btn-danger" data-toggle="modal" data-target="#shanchu">删除</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="dao">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">6</a></li>
                        <li>
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="modal fade" id="chulixg" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">状态修改</h4>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <textarea class="form-control" rows="6" placeholder="回复信息"
                                              style="resize: none;"></textarea>
                                    <label for="situation" class="col-xs-3 control-label">状态：</label>
                                    <div class="col-xs-8">
                                        <label class="control-label" for="anniu">
                                            <input type="radio" name="situation" id="normal">待查看</label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <label class="control-label" for="meun">
                                            <input type="radio" name="situation" id="forbid">待处理</label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <label class="control-label" for="meun">
                                            <input type="radio" name="situation" id="forbid">处理中</label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <label class="control-label" for="meun">
                                            <input type="radio" name="situation" id="forbid">处理完成</label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <label class="control-label" for="meun">
                                            <input type="radio" name="situation" id="forbid">处理失败</label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <label class="control-label" for="meun">
                                            <input type="radio" name="situation" id="forbid">拒接处理</label>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            关闭
                        </button>
                        <button type="button" class="btn btn-primary">
                            保存
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="qingqiuxq" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">请求详情</h4>
                </div>
                <div class="modal-body">
                    <p>asdasdasdasdasdsadsadsadasdsadasddadasdsdasd</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        关闭
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="shanchu" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">警告</h4>
            </div>
            <div class="modal-body">
                <p>确定删除？</p>
            </div>
            <form id="delete_Form">
                <input type="hidden" name="id" id="id">
                <input type="hidden" name="action" id="action">
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
                <button type="button" class="btn btn-danger" id="deleteAll">
                    删除
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
