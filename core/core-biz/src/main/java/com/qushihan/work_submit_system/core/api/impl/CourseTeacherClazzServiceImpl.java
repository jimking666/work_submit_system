package com.qushihan.work_submit_system.core.api.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.qushihan.work_submit_system.clazz.api.ClazzService;
import com.qushihan.work_submit_system.clazz.dto.ClazzDto;
import com.qushihan.work_submit_system.core.api.CourseTeacherClazzService;
import com.qushihan.work_submit_system.core.dao.CourseTeacherClazzDao;
import com.qushihan.work_submit_system.core.dto.CourseTeacherClazzDto;
import com.qushihan.work_submit_system.core.model.auto.CourseTeacherClazz;
import com.qushihan.work_submit_system.course.api.CourseService;
import com.qushihan.work_submit_system.course.dto.CourseDto;
import com.qushihan.work_submit_system.teacher.api.TeacherService;
import com.qushihan.work_submit_system.teacher.dto.TeacherDto;

@Service
public class CourseTeacherClazzServiceImpl implements CourseTeacherClazzService {

    @Autowired
    private CourseTeacherClazzDao courseTeacherClazzDao;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ClazzService clazzService;

    @Override
    public List<CourseTeacherClazzDto> queryCourseTeacherClazzDtoListByClazzId(Long clazzId) {
        List<CourseTeacherClazz> courseTeacherClazzes = courseTeacherClazzDao.queryCourseTeacherClazzListByClazzId(clazzId);
        if (CollectionUtils.isEmpty(courseTeacherClazzes)) {
            return Collections.EMPTY_LIST;
        }
        List<CourseTeacherClazzDto> courseTeacherClazzDtos = courseTeacherClazzes.stream().map(courseTeacherClazz -> {
            CourseTeacherClazzDto courseTeacherClazzDto = new CourseTeacherClazzDto();
            BeanUtils.copyProperties(courseTeacherClazz, courseTeacherClazzDto);
            CourseDto courseDto = courseService.queryCourseDtoByCourseId(courseTeacherClazz.getCourseId());
            TeacherDto teacherDto = teacherService.queryTeacherDtoByTeacherId(courseTeacherClazz.getTeacherId());
            ClazzDto clazzDto = clazzService.getByClazzId(courseTeacherClazz.getClazzId());
            courseTeacherClazzDto.setCourseName(courseDto.getCourseName());
            courseTeacherClazzDto.setTeacherName(teacherDto.getTeacherName());
            courseTeacherClazzDto.setClazzName(clazzDto.getClazzName());
            return courseTeacherClazzDto;
        }).collect(Collectors.toList());
        return courseTeacherClazzDtos;
    }
}
