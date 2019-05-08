<!DOCTYPE HTML>
<html>
<head>
    <title>注册</title>
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
            $("#register").click(function () {
                // 判断学号、学生密码、学生姓名是否有为空
                if ($("#studentNumber").val() == "" || $("#studentPassword").val() == "" || $("#studentName").val() == "") {
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
                    // 若学生姓名为空
                    if ($("#studentName").val() == "") {
                        $("#studentNameError").html("&nbsp&nbsp&nbsp&nbsp学 生 姓 名 不 能 为 空 !")
                    } else {
                        $("#studentNameError").html("&nbsp&nbsp&nbsp&nbsp*")
                    }
                    return
                } else {
                    $("#studentNumberError").html("&nbsp&nbsp&nbsp&nbsp*")
                    $("#studentPasswordError").html("&nbsp&nbsp&nbsp&nbsp*")
                    $("#studentNameError").html("&nbsp&nbsp&nbsp&nbsp*")
                    $.ajax({
                        url: "/student/register",
                        type: "post",
                        data: JSON.stringify({
                            "studentNumber": $("#studentNumber").val(),
                            "studentPassword": $("#studentPassword").val(),
                            "studentName": $("#studentName").val()
                        }),
                        contentType: "application/json;charset=utf-8",
                        statusCode: {
                            200: function (data) {
                                if (data.indexOf("注册成功") != -1) {
                                    alert(data)
                                    window.location = "/"
                                } else if (data.indexOf("重复注册") != -1) {
                                    $("#studentNumberError").html("&nbsp&nbsp&nbsp&nbsp此 学 号 已 被 注 册 !")
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
        <h1>注 册</h1>
        <form>
            <input type="text" id="studentNumber" name="studentNumber" value="" placeholder="学 号"/>
            <span id="studentNumberError" style="color: red">&nbsp&nbsp&nbsp&nbsp*</span>
            <input type="password" id="studentPassword" name="studentPassword" value="" placeholder="密 码"/>
            <span id="studentPasswordError" style="color: red">&nbsp&nbsp&nbsp&nbsp*</span>
            <input type="text" id="studentName" name="studentName" value="" placeholder="真 实 姓 名"/>
            <span id="studentNameError" style="color: red">&nbsp&nbsp&nbsp&nbsp*</span>
        </form>
        <div class="forgot">
            <input type="button" id="register" value="注册">
        </div>
    </div>
    <div class="login-bottom">
        <h3>有账号？ &nbsp;<a href="/">登陆</a>&nbsp 这里</h3>
    </div>
</div>
</body>
</html>