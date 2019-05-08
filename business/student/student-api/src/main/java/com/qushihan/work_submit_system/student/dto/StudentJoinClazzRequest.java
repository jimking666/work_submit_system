package com.qushihan.work_submit_system.student.dto;

import lombok.Data;

/**
 * 学生加入班级请求
 */
@Data
public class StudentJoinClazzRequest {

    /**
     * 学生id
     */
    private String studentId;

    /**
     * 班级id
     */
    private String clazzId;
}
