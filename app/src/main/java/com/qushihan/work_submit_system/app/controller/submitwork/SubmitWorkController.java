package com.qushihan.work_submit_system.app.controller.submitwork;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qushihan.work_submit_system.app.util.PrintWriterUtil;
import com.qushihan.work_submit_system.inf.util.TransitionUtil;
import com.qushihan.work_submit_system.submitwork.api.SubmitWorkService;
import com.qushihan.work_submit_system.submitwork.dto.EditSubmitWorkRequest;
import com.qushihan.work_submit_system.submitwork.dto.QuerySubmitWorkContentRequest;

@RestController
@RequestMapping("submitWork")
public class SubmitWorkController {

    @Autowired
    private SubmitWorkService submitWorkService;

    /**
     * 修改提交作业
     *
     * @param editSubmitWorkRequest
     * @param response
     */
    @PostMapping("/edit")
    public void editSubmitWork(@RequestBody EditSubmitWorkRequest editSubmitWorkRequest, HttpServletResponse response) {
        Long workId = TransitionUtil.stringToLong(editSubmitWorkRequest.getWorkId());
        Long studentId = TransitionUtil.stringToLong(editSubmitWorkRequest.getStudentId());
        String submitWorkContent = editSubmitWorkRequest.getSubmitWorkContent();
        String editMessage = submitWorkService.editSubmitWork(workId, studentId, submitWorkContent);
        PrintWriterUtil.print(editMessage, response);
    }

    /**
     * 查询提交作业内容
     *
     * @param querySubmitWorkContentRequest
     * @param response
     */
    @PostMapping("/querySubmitWorkContent")
    public void querySubmitWorkContent(@RequestBody QuerySubmitWorkContentRequest querySubmitWorkContentRequest,
            HttpServletResponse response) {
        Long workId = TransitionUtil.stringToLong(querySubmitWorkContentRequest.getWorkId());
        Long studentId = TransitionUtil.stringToLong(querySubmitWorkContentRequest.getStudentId());
        String submitWorkContent = submitWorkService.querySubmitWorkContent(workId, studentId);
        PrintWriterUtil.print(submitWorkContent, response);
    }
}
