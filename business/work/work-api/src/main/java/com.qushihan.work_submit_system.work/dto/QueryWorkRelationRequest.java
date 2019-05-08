package com.qushihan.work_submit_system.work.dto;

import lombok.Data;

/**
 * 查询作业关联请求
 */
@Data
public class QueryWorkRelationRequest {

    /**
     * 班级id
     */
    private String clazzId;

    /**
     * 学生id
     */
    private String studentId;
}
