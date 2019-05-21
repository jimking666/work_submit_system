package com.qushihan.work_submit_system.app.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    /**
     * 登陆页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String forwardLogin() {
        return "login";
    }

    /**
     * 作业详情页面
     *
     * @return
     */
    @RequestMapping("/workDetailPage")
    public String forwardWorkDetailPage() {
        return "workDetailPage";
    }

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping("/register")
    public String forwordRegister() {
        return "register";
    }

    /**
     * 班级详情页面
     *
     * @return
     */
    @RequestMapping("/browseClazzPage")
    public String forwordBrowseClazzPage() {
        return "/browseClazzPage";
    }


    /**
     * 学生详情页面
     *
     * @return
     */
    @RequestMapping("/studentDetailPage")
    public String forwardClazzStudentDetail() {
        return "studentDetailPage";
    }
}
