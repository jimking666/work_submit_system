package com.qushihan.work_submit_system.student.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 判断注册状态枚举类
 */
@AllArgsConstructor
public enum JudgeRegisterStatus {

    /**
     * 注册成功
     */
    REGISTER_SUCCESS(0, "注册成功"),

    /**
     * 重复注册
     */
    REPEAT_EXIST(1, "重复注册"),

    /**
     * 格式错误
     */
    FORMAT_ERROR(3, "格式错误"),
    ;

    @Getter
    private Integer code;

    @Getter
    private String messge;

}

