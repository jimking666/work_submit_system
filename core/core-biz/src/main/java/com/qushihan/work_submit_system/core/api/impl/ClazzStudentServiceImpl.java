package com.qushihan.work_submit_system.core.api.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.qushihan.work_submit_system.core.api.ClazzStudentService;
import com.qushihan.work_submit_system.core.dao.ClazzStudentDao;
import com.qushihan.work_submit_system.core.dto.ClazzStudentDto;
import com.qushihan.work_submit_system.core.enums.ClazzStudentStatus;
import com.qushihan.work_submit_system.core.model.auto.ClazzStudent;
import com.qushihan.work_submit_system.core.util.ClazzStudentUtil;
import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;

@Service
public class ClazzStudentServiceImpl implements ClazzStudentService {

    @Autowired
    private ClazzStudentDao clazzStudentDao;

    @Override
    public String increaseRecord(Long clazzId, Long studentId) {
        Long clazzStudentId = ClazzStudentUtil.getClazzStudentId(clazzId, studentId);
        ClazzStudent clazzStudent = new ClazzStudent();
        clazzStudent.setClazzStudentId(clazzStudentId);
        clazzStudent.setClazzId(clazzId);
        clazzStudent.setStudentId(studentId);
        clazzStudentDao.increaseRecord(clazzStudent);
        return ClazzStudentStatus.INSERT_SUCCESS.getMessage();
    }

    @Override
    public ClazzStudentDto getByStudentId(Long studentId) {
        List<ClazzStudent> clazzStudents = clazzStudentDao.getByStudentId(studentId);
        if (CollectionUtils.isEmpty(clazzStudents)) {
            return new ClazzStudentDto();
        }
        ClazzStudent clazzStudent = clazzStudents.stream()
                .findFirst()
                .orElse(new ClazzStudent());
        ClazzStudentDto clazzStudentDto = new ClazzStudentDto();
        BeanUtils.copyProperties(clazzStudent, clazzStudentDto);
        return clazzStudentDto;
    }

    @Override
    public int updateByClazzStudentId(ClazzStudentDto clazzStudentDto, Long clazzStudentId) {
        ClazzStudent clazzStudent = new ClazzStudent();
        BeanUtils.copyProperties(clazzStudentDto, clazzStudent);
        return clazzStudentDao.updateByClazzStudentId(clazzStudent, clazzStudentId);
    }
}
