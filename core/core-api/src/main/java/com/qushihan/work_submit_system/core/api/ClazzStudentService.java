package com.qushihan.work_submit_system.core.api;

import com.qushihan.work_submit_system.core.dto.ClazzStudentDto;

import java.util.List;

public interface ClazzStudentService {

    /**
     * 新增班级学生关联记录
     *
     * @param clazzId
     * @param studentId
     *
     * @return
     */
    int insertClazzStudent(Long clazzId, Long studentId);

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
     * @param clazzStudentDto
     *
     * @return
     */
    int updateByClazzStudentId(ClazzStudentDto clazzStudentDto);

    /**
     * 通过班级id查询班级学生关联表记录
     *
     * @param clazzId
     * @return
     */
    List<ClazzStudentDto> getByClazzId(Long clazzId);
}
