package com.qushihan.work_submit_system.teacher.api.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qushihan.work_submit_system.teacher.api.TeacherService;
import com.qushihan.work_submit_system.teacher.dao.TeacherDao;
import com.qushihan.work_submit_system.teacher.dto.TeacherDto;
import com.qushihan.work_submit_system.teacher.model.auto.Teacher;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public TeacherDto queryTeacherDtoByTeacherId(Long teacherId) {
        List<Teacher> teachers = teacherDao.queryTeacherListByTeacherId(teacherId);
        Teacher teacher = teachers.stream().findFirst().orElse(null);
        if (Optional.ofNullable(teacher).isPresent()) {
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher, teacherDto);
            return teacherDto;
        }
        return new TeacherDto();
    }
}
