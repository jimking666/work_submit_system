package com.qushihan.work_submit_system.submitwork.dto;

import lombok.Data;

/**
 * 查询提交作业内容请求
 */
@Data
public class QuerySubmitWorkContentRequest {

    /**
     * 作业id
     */
    private String workId;

    /**
     * 学生id
     */
    private String studentId;
}
