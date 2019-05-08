package com.qushihan.work_submit_system.clazz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 学生数量增加状态枚举累
 */
@AllArgsConstructor
public enum  StudentCountIncreaseStatus {

    /**
     * 增加成功
     */
    INCREASE_SUCCESS(0, "增加成功");

    @Getter
    private Integer code;

    @Getter
    private String message;
}
