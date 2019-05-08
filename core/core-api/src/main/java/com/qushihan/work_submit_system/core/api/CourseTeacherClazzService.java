package com.qushihan.work_submit_system.core.api;

import java.util.List;

import com.qushihan.work_submit_system.core.dto.CourseTeacherClazzDto;

public interface CourseTeacherClazzService {

    /**
     * 通过班级id查询课程教师班级
     *
     * @param clazzId
     *
     * @return
     */
    List<CourseTeacherClazzDto> queryCourseTeacherClazzDtoListByClazzId(Long clazzId);
}
