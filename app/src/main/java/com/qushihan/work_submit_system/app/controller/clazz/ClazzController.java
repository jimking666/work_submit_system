package com.qushihan.work_submit_system.app.controller.clazz;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 通过班级名称查询班级
     *
     * @param getClazzBySearchRequest
     * @param request
     * @param response
     */
    @RequestMapping("/getClazzBySearch")
    public void getClazzBySearch(@RequestBody GetClazzBySearchRequest getClazzBySearchRequest, HttpServletRequest request, HttpServletResponse response) {
        String searchClazzName = getClazzBySearchRequest.getSearchClazzName();
        List<ClazzDto> searchClazzDtos = clazzService.getBySearchClazzName(searchClazzName);
        List<Long> clazzIds = searchClazzDtos.stream()
                .map(ClazzDto::getClazzId)
                .collect(Collectors.toList());
        List<ClazzDto> clazzDtos = clazzService.queryAllClazz();
        clazzDtos = clazzDtos.stream()
                .filter(clazzDto -> clazzIds.contains(clazzDto.getClazzId()))
                .collect(Collectors.toList());
        request.getServletContext().setAttribute("clazzDtos", clazzDtos);
        PrintWriterUtil.print("查询成功", response);
    }
}
