package com.qushihan.work_submit_system.clazz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 班级错误枚举类
 */
@AllArgsConstructor
public enum ClazzErrorCode {

    /**
     * 没有查询到班级
     */
    NO_SUCH_CLAZZ(1, "没有查询到班级");

    @Getter
    private Integer code;

    @Getter
    private String message;
}
