package com.qushihan.work_submit_system.app.controller.clazz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qushihan.work_submit_system.clazz.api.ClazzService;
import com.qushihan.work_submit_system.clazz.dto.ClazzDto;

@RestController
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

//    @RequestMapping("/clazzList")
//    public void clazzList(HttpServletRequest request) {
//        List<ClazzDto> clazzDtos = clazzService.queryAllClazz();
//        request.getServletContext().setAttribute("clazzDtos", clazzDtos);
//    }

    @RequestMapping("/clazzList")
    public String clazzList(Map map) {
        List<ClazzDto> clazzDtos = clazzService.queryAllClazz();
        map.put("clazzDtos", clazzDtos);
        return "mainpage";
    }
}
