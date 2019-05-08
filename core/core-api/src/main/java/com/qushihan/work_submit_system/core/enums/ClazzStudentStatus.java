package com.qushihan.work_submit_system.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 班级学生状态枚举类
 */
@AllArgsConstructor
public enum ClazzStudentStatus {

    /**
     * 插入成功
     */
    INSERT_SUCCESS(0, "插入成功");

    @Getter
    private Integer code;

    @Getter
    private String message;
}
