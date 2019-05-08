package com.qushihan.work_submit_system.student.dto;

import lombok.Data;

/**
 * 学生注册请求
 */
@Data
public class RegisterStudentRequest {

    /**
     * 学号
     */
    private Long studentNumber;

    /**
     * 密码
     */
    private String studentPassword;

    /**
     * 学生姓名
     */
    private String studentName;
}
