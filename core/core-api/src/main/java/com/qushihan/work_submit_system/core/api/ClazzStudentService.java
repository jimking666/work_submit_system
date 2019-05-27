package com.qushihan.work_submit_system.core.api;

import com.qushihan.work_submit_system.core.dto.ClazzStudentDto;

public interface ClazzStudentService {

    /**
     * 班级学生关联记录插入
     *
     * @param clazzId
     * @param studentId
     *
     * @return
     */
    String increaseRecord(Long clazzId, Long studentId);

    /**
     * 通过学生id查询班级学生关联表记录
     *
     * @param studentId
     *
     * @return
     */
    ClazzStudentDto getByStudentId(Long studentId);

    /**
     * 根据clazzStudentId将ClazzStudent记录更改
     *
     * @param clazzStudentId
     *
     * @return
     */
    int updateByClazzStudentId(ClazzStudentDto clazzStudentDto, Long clazzStudentId);
}
