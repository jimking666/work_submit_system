package com.qushihan.work_submit_system.student.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 学生退出班级状态枚举类
 */
@AllArgsConstructor
public enum StudentQuitClazzStatus {

    /**
     * 退出成功
     */
    QUIT_SUCCESS(0, "退出成功"),

    /**
     * 退出失败
     */
    QUIT_FAIL(1, "退出失败");

    @Getter
    private Integer code;

    @Getter
    private String messge;

}