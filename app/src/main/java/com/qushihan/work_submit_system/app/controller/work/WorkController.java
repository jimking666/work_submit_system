package com.qushihan.work_submit_system.app.controller.work;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qushihan.work_submit_system.app.util.PrintWriterUtil;
import com.qushihan.work_submit_system.inf.util.TransitionUtil;
import com.qushihan.work_submit_system.work.api.WorkService;
import com.qushihan.work_submit_system.work.dto.QueryWorkRelationRequest;
import com.qushihan.work_submit_system.work.dto.WorkRelationDto;

@RestController
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    /**
     * 通过班级id对班级所有作业关联大查询
     *
     * @param queryWorkRelationRequest
     * @param request
     * @param response
     */
    @PostMapping("/queryWorkRelationDto")
    public void queryWorkRelation(@RequestBody QueryWorkRelationRequest queryWorkRelationRequest,
            HttpServletRequest request, HttpServletResponse response) {
        Long clazzId = TransitionUtil.stringToLong(queryWorkRelationRequest.getClazzId());
        Long studentId = TransitionUtil.stringToLong(queryWorkRelationRequest.getStudentId());
        List<WorkRelationDto> workRelationDtos = workService.queryWorkRelationDtoByClazzId(clazzId, studentId);
        request.getServletContext().setAttribute("workRelationDtos", workRelationDtos);
        request.getServletContext().setAttribute("clazzId", clazzId);
        PrintWriterUtil.print("作业关联查询成功", response);
    }
}
