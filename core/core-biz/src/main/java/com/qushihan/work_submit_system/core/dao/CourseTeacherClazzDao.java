package com.qushihan.work_submit_system.core.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.core.mapper.auto.CourseTeacherClazzMapper;
import com.qushihan.work_submit_system.core.model.auto.CourseTeacherClazz;
import com.qushihan.work_submit_system.core.model.auto.CourseTeacherClazzExample;
import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;

@Repository
public class CourseTeacherClazzDao {

    @Autowired
    private CourseTeacherClazzMapper courseTeacherClazzMapper;

    public List<CourseTeacherClazz> queryCourseTeacherClazzListByClazzId(Long clazzId) {
        if (!Optional.ofNullable(clazzId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        CourseTeacherClazzExample courseTeacherClazzExample = new CourseTeacherClazzExample();
        CourseTeacherClazzExample.Criteria criteria = courseTeacherClazzExample.createCriteria();
        criteria.andClazzIdEqualTo(clazzId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return courseTeacherClazzMapper.selectByExample(courseTeacherClazzExample);
    }
}
