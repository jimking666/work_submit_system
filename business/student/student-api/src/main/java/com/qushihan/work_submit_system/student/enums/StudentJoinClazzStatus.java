package com.qushihan.work_submit_system.student.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 学生加入班级状态枚举类
 */
@AllArgsConstructor
public enum StudentJoinClazzStatus {

    /**
     * 加入成功
     */
    JOIN_SUCCESS(0, "加入成功"),

    /**
     * 加入失败
     */
    JOIN_FAIL(1, "加入失败");

    @Getter
    private Integer code;

    @Getter
    private String messge;

}
