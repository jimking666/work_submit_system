package com.qushihan.work_submit_system.submitwork.api;

import java.util.List;

import com.qushihan.work_submit_system.submitwork.dto.SubmitWorkDto;

public interface SubmitWorkService {

    /**
     * 通过作业id查询提交作业Dto列表
     *
     * @param workId
     *
     * @return
     */
    List<SubmitWorkDto> querySubmitWorkDtoListByWorkId(Long workId);

    /**
     * 通过作业id和学生id查询提交作业Dto
     *
     * @param workId
     * @param studentId
     *
     * @return
     */
    SubmitWorkDto querySubmitWorkDtoByWorkIdAndStudentId(Long workId, Long studentId);

    /**
     * 修改提交作业
     *
     * @param workId
     * @param studentId
     * @param submitWorkContent
     *
     * @return
     */
    String editSubmitWork(Long workId, Long studentId, String submitWorkContent);

    /**
     * 查询提交作业内容
     *
     * @param workId
     * @param studentId
     *
     * @return
     */
    String querySubmitWorkContent(Long workId, Long studentId);
}
