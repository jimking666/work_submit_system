package com.qushihan.work_submit_system.student.api.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.qushihan.work_submit_system.clazz.api.ClazzService;
import com.qushihan.work_submit_system.clazz.dto.ClazzDto;
import com.qushihan.work_submit_system.core.api.ClazzStudentService;
import com.qushihan.work_submit_system.core.dto.ClazzStudentDto;
import com.qushihan.work_submit_system.inf.util.TransitionUtil;
import com.qushihan.work_submit_system.student.api.StudentService;
import com.qushihan.work_submit_system.student.dao.StudentDao;
import com.qushihan.work_submit_system.student.dto.StudentDto;
import com.qushihan.work_submit_system.student.dto.StudentJoinClazzRequest;
import com.qushihan.work_submit_system.student.enums.JudgeRegisterStatus;
import com.qushihan.work_submit_system.student.enums.StudentJoinClazzStatus;
import com.qushihan.work_submit_system.student.enums.StudentQuitClazzStatus;
import com.qushihan.work_submit_system.student.model.auto.Student;
import com.qushihan.work_submit_system.student.util.StudentUtil;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ClazzStudentService clazzStudentService;

    @Autowired
    private ClazzService clazzService;

    @Override
    public String registerStudent(Long studentNumber, String studentPassword, String studentName) {
        // 检查是否重复注册
        List<Student> students = studentDao.judgeRepeatRegister(studentNumber);
        if (!CollectionUtils.isEmpty(students)) {
            return JudgeRegisterStatus.REPEAT_EXIST.getMessge();
        }
        // 注册成功
        Long studentId = StudentUtil.getStudentId(studentNumber);
        Student student = new Student();
        student.setStudentId(studentId);
        student.setStudentNumber(studentNumber);
        student.setStudentPassword(studentPassword);
        student.setStudentName(studentName);
        studentDao.registerStudent(student);
        return JudgeRegisterStatus.REGISTER_SUCCESS.getMessge();
    }

    @Override
    public List<StudentDto> loginStudent(Long studentNumber, String studentPassword) {
        List<Student> students = studentDao.queryStudentByStudentNumberAndStudentPassword(
                studentNumber, studentPassword);
        if (CollectionUtils.isEmpty(students)) {
            return Collections.emptyList();
        }
        return students.stream()
                .map(student -> {
                    StudentDto studentDto = new StudentDto();
                    BeanUtils.copyProperties(student, studentDto);
                    ClazzDto clazzDto = clazzService.getByClazzId(student.getClazzId());
                    studentDto.setClazzName(clazzDto.getClazzName());
                    return studentDto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String studentJoinClazz(StudentJoinClazzRequest studentJoinClazzRequest) {
        Long studentId = TransitionUtil.stringToLong(studentJoinClazzRequest.getStudentId());
        Long clazzId = TransitionUtil.stringToLong(studentJoinClazzRequest.getClazzId());
        // 学生表班级字段更改
        Student student = studentDao.getByStudentId(studentId);
        student.setClazzId(clazzId);
        studentDao.updateByStudentId(student);
        // 班级学生关联表增加记录
        clazzStudentService.insertClazzStudent(clazzId, studentId);
        // 班级表学生个数增加一
        List<ClazzStudentDto> clazzStudentDtos = clazzStudentService.getByClazzId(clazzId);
        ClazzDto clazzDto = clazzService.getByClazzId(clazzId);
        clazzDto.setStudentCount((long) clazzStudentDtos.size());
        clazzService.updateByClazzId(clazzDto);
        return StudentJoinClazzStatus.JOIN_SUCCESS.getMessge();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String studentQuitClazz(Long studentId) {
        // 学生表班级id字段更改为空
        Student student = studentDao.getByStudentId(studentId);
        student.setClazzId(null);
        studentDao.updateByStudentId(student);
        // 将clazzStudent表中记录进行软删除
        ClazzStudentDto clazzStudentDto = clazzStudentService.getByStudentId(studentId);
        clazzStudentDto.setIsdel(FieldIsdelStatus.ISDEL_TRUE.getIsdel());
        clazzStudentService.updateByClazzStudentId(clazzStudentDto);
        // 通过clazzId将学生数量减去1
        Long clazzId = clazzStudentDto.getClazzId();
        List<ClazzStudentDto> clazzStudentDtos = clazzStudentService.getByClazzId(clazzId);
        ClazzDto clazzDto = clazzService.getByClazzId(clazzId);
        clazzDto.setStudentCount((long) clazzStudentDtos.size());
        clazzService.updateByClazzId(clazzDto);
        return StudentQuitClazzStatus.QUIT_SUCCESS.getMessge();
    }

    @Override
    public StudentDto getByStudentId(Long studentId) {
        Student student = studentDao.getByStudentId(studentId);
        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student, studentDto);
        ClazzDto clazzDto = clazzService.getByClazzId(student.getClazzId());
        studentDto.setClazzName(clazzDto.getClazzName());
        return studentDto;
    }

    @Override
    public List<StudentDto> queryStudentDtoListByClazzId(Long clazzId) {
        List<Student> students = studentDao.queryStudentListByClazzId(clazzId);
        List<StudentDto> studentDtos = students.stream().map(student -> {
            StudentDto studentDto = new StudentDto();
            ClazzDto clazzDto = clazzService.getByClazzId(student.getClazzId());
            studentDto.setClazzName(clazzDto.getClazzName());
            BeanUtils.copyProperties(student, studentDto);
            return studentDto;
        }).collect(Collectors.toList());
        return studentDtos;
    }

    @Override
    public List<StudentDto> getBySearchStudentName(String searchStudentName) {
        List<Student> students = studentDao.getBySearchStudentName(searchStudentName);
        return students.stream()
                .map(student -> {
                    StudentDto studentDto = new StudentDto();
                    BeanUtils.copyProperties(student, studentDto);
                    return studentDto;
                })
                .collect(Collectors.toList());
    }
}
