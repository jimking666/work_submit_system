package com.qushihan.work_submit_system.core.dto;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClazzStudentDto {

    /**
     * 班级学生id
     */
    private Long clazzStudentId;

    /**
     * 班级id
     */
    private Long clazzId;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 是否删除
     */
    private Boolean isdel;

    /**
     * 创建日期
     */
    private Date createTime;

}
