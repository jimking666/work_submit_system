package com.qushihan.work_submit_system.teacher.api;

import com.qushihan.work_submit_system.teacher.dto.TeacherDto;

public interface TeacherService {

    /**
     * 通过教师id查询教师dto
     *
     * @param teacherId
     *
     * @return
     */
    TeacherDto queryTeacherDtoByTeacherId(Long teacherId);
}
