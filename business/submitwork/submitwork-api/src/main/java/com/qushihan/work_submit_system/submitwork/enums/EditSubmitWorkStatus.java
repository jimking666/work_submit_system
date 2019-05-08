package com.qushihan.work_submit_system.submitwork.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 修改作业状态枚举类
 */
@AllArgsConstructor
public enum EditSubmitWorkStatus {

    /**
     * 修改成功
     */
    UPDATE_SUCCESS(0, "修改成功"),

    /**
     * 重复率偏大
     */
    REPETITION(1, "重复率偏大");

    @Getter
    private Integer code;

    @Getter
    private String message;
}
