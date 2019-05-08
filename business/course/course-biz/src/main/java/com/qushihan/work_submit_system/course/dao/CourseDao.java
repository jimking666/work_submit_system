package com.qushihan.work_submit_system.course.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.course.mapper.auto.CourseMapper;
import com.qushihan.work_submit_system.course.model.auto.Course;
import com.qushihan.work_submit_system.course.model.auto.CourseExample;
import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;

@Repository
public class CourseDao {

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 根据课程id查询课程列表
     *
     * @param courseId
     *
     * @return
     */
    public List<Course> queryCourseListByCourseId(Long courseId) {
        if (!Optional.ofNullable(courseId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return courseMapper.selectByExample(courseExample);
    }
}
