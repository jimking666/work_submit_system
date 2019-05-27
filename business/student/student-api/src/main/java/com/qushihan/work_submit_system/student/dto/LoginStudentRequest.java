package com.qushihan.work_submit_system.student.dto;

import lombok.Data;

/**
 * 学生登陆请求
 */
@Data
public class LoginStudentRequest {

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 密码
     */
    private String studentPassword;
}
