package com.qushihan.work_submit_system.app.controller.index;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qushihan.work_submit_system.clazz.api.ClazzService;
import com.qushihan.work_submit_system.clazz.dto.ClazzDto;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private ClazzService clazzService;

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
     * 浏览班级页面
     *
     * @param request
     *
     * @return
     */
    @RequestMapping("/browseClazzPage")
    public String forwordBrowseClazzPage(HttpServletRequest request) {
        List<ClazzDto> clazzDtos = clazzService.queryAllClazz();
        request.getServletContext().setAttribute("clazzDtos", clazzDtos);
        return "/browseClazzPage";
    }

    @RequestMapping("/browseClazzPageSearch")
    public String forwordBrowseClazzPageSearch() {
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
