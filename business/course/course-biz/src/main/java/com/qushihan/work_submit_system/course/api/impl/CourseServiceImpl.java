package com.qushihan.work_submit_system.course.api.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qushihan.work_submit_system.course.api.CourseService;
import com.qushihan.work_submit_system.course.dao.CourseDao;
import com.qushihan.work_submit_system.course.dto.CourseDto;
import com.qushihan.work_submit_system.course.model.auto.Course;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public CourseDto queryCourseDtoByCourseId(Long courseId) {
        List<Course> courses = courseDao.queryCourseListByCourseId(courseId);
        Course course = courses.stream().findFirst().orElse(null);
        if (Optional.ofNullable(course).isPresent()) {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto);
            return courseDto;
        }
        return new CourseDto();
    }
}
