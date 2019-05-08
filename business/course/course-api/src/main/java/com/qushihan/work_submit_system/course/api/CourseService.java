package com.qushihan.work_submit_system.course.api;

import com.qushihan.work_submit_system.course.dto.CourseDto;

public interface CourseService {

    /**
     * 通过课程id查询课程dto
     *
     * @param courseId
     *
     * @return
     */
    CourseDto queryCourseDtoByCourseId(Long courseId);
}
