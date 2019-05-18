package com.qushihan.work_submit_system.app.controller.clazz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qushihan.work_submit_system.app.util.PrintWriterUtil;
import com.qushihan.work_submit_system.clazz.dto.GetClazzBySearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

//    @RequestMapping("/clazzList")
//    public String clazzList(Map map) {
//        List<ClazzDto> clazzDtos = clazzService.queryAllClazz();
//        map.put("clazzDtos", clazzDtos);
//        return "mainpage";
//    }

    @RequestMapping("/getClazzBySearch")
    public void getClazzBySearch(@RequestBody GetClazzBySearchRequest getClazzBySearchRequest, HttpServletRequest request, HttpServletResponse response) {
        String searchClazzName = getClazzBySearchRequest.getSearchClazzName();
        List<ClazzDto> clazzDtos = clazzService.getBySearchClazzName(searchClazzName);
        request.getServletContext().setAttribute("clazzDtos", clazzDtos);
        PrintWriterUtil.print("查询成功", response);
    }
}
