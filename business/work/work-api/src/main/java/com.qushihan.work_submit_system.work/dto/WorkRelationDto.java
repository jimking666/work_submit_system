package com.qushihan.work_submit_system.work.dto;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WorkRelationDto {

    /**
     * 作业id
     */
    private Long workId;

    /**
     * 作业题目
     */
    private String workTitle;

    /**
     * 作业内容
     */
    private String workContent;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 提交作业id
     */
    private Long submitWorkId;

    /**
     * 得分
     */
    private Integer score;
}
