package com.qushihan.work_submit_system.student.mapper.biz;

import org.apache.ibatis.annotations.Param;

public interface StudentBizMapper {

    /**
     * 通过学生id将Student表clazzId字段置为NULL
     *
     * @param studentId
     *
     * @return
     */
    int setClazzIdNullByStudentId(@Param("studentId") Long studentId);
}
