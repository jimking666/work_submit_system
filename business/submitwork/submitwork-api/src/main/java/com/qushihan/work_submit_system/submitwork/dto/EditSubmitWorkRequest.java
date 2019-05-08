package com.qushihan.work_submit_system.submitwork.dto;

import lombok.Data;

/**
 * 修改提交作业请求
 */
@Data
public class EditSubmitWorkRequest {

    /**
     * 提交作业内容
     */
    private String submitWorkContent;

    /**
     * 作业id
     */
    private String workId;

    /**
     * 学生id
     */
    private String studentId;
}
