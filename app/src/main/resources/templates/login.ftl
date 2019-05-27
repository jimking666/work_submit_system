<!DOCTYPE HTML>
<html>
<head>
    <title>登陆</title>
    <!-- Custom Theme files -->
    <link href="css/login.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- Custom Theme files -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords"
          content="Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design"/>
    <script type="text/javascript" src="../js/jquery-3.3.1.js"></script>
    <script>
        $(function () {
            $("#login").click(function () {
                // 若学号为空或学生密码为空
                if ($("#studentNumber").val() == "" || $("#studentPassword").val() == "") {
                    // 若学号为空
                    if ($("#studentNumber").val() == "") {
                        $("#studentNumberError").html("&nbsp&nbsp&nbsp&nbsp学 号 不 能 为 空 !")
                    } else {
                        $("#studentNumberError").html("&nbsp&nbsp&nbsp&nbsp*")
                    }
                    // 若学生密码为空
                    if ($("#studentPassword").val() == "") {
                        $("#studentPasswordError").html("&nbsp&nbsp&nbsp&nbsp密 码 不 能 为 空 !")
                    } else {
                        $("#studentPasswordError").html("&nbsp&nbsp&nbsp&nbsp*")
                    }
                    return
                } else {
                    // 若都不为空则提交表单
                    $("#studentNumberError").html("&nbsp&nbsp&nbsp&nbsp*")
                    $("#studentPasswordError").html("&nbsp&nbsp&nbsp&nbsp*")
                    $.ajax({
                        url: "/student/login",
                        type: "post",
                        data: JSON.stringify({
                            "studentNumber": $("#studentNumber").val(),
                            "studentPassword": $("#studentPassword").val()
                        }),
                        contentType: "application/json;charset=utf-8",
                        statusCode: {
                            200: function (data) {
                                if (data.indexOf("登陆成功") != -1) {
                                    window.location = "/browseClazzPage"
                                } else if (data.indexOf("格式错误") != -1) {
                                    $("#studentNumber").val("")
                                    $("#studentNumberError").html("&nbsp&nbsp&nbsp&nbsp学 号 只 能 是 数 字 !")
                                    $("#studentPassword").val("")
                                } else if (data.indexOf("账号或密码错误") != -1) {
                                    $("#studentNumber").val("")
                                    $("#studentPassword").val("")
                                    $("#studentPasswordError").html("&nbsp&nbsp&nbsp&nbsp学 号 或 密 码 错 误 !")
                                }
                            }
                        }
                    })
                }
            })
        })
    </script>
</head>
<body>
<div class="login">
    <h2>欢迎进入学生端</h2>
    <div class="login-top">
        <h1>登 陆</h1>
        <form>
            <input type="text" id="studentNumber" name="studentNumber" value="" placeholder="学 号">
            <span id="studentNumberError" style="color: red">&nbsp&nbsp&nbsp&nbsp*</span>
            <input type="password" id="studentPassword" name="studentPassword" value="" placeholder="密 码">
            <span id="studentPasswordError" style="color: red">&nbsp&nbsp&nbsp&nbsp*</span>
            <div class="forgot">
                <input type="button" id="login" value="登陆">
            </div>
        </form>
    </div>
    <div class="login-bottom">
        <h3>新用户？ &nbsp;<a href="/register">注册</a>&nbsp 这里</h3>
    </div>
</div>
</body>
</html>