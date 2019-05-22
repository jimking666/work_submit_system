package com.qushihan.work_submit_system.student.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.qushihan.work_submit_system.clazz.api.ClazzService;
import com.qushihan.work_submit_system.clazz.dto.ClazzDto;
import com.qushihan.work_submit_system.clazz.enums.StudentCountIncreaseStatus;
import com.qushihan.work_submit_system.core.api.ClazzStudentService;
import com.qushihan.work_submit_system.core.dto.ClazzStudentDto;
import com.qushihan.work_submit_system.core.enums.ClazzStudentStatus;
import com.qushihan.work_submit_system.inf.util.TransitionUtil;
import com.qushihan.work_submit_system.student.api.StudentService;
import com.qushihan.work_submit_system.student.dao.StudentDao;
import com.qushihan.work_submit_system.student.dto.LoginStudentRequest;
import com.qushihan.work_submit_system.student.dto.RegisterStudentRequest;
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
    public String registerStudent(RegisterStudentRequest registerStudentRequest) {
        Long studentNumber = registerStudentRequest.getStudentNumber();
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
        student.setStudentPassword(registerStudentRequest.getStudentPassword());
        student.setStudentName(registerStudentRequest.getStudentName());
        studentDao.registerStudent(student);
        return JudgeRegisterStatus.REGISTER_SUCCESS.getMessge();
    }

    @Override
    public StudentDto loginStudent(LoginStudentRequest loginStudentRequest) {
        List<Student> students = studentDao.queryStudentByStudentNumberAndStudentPassword(
                loginStudentRequest.getStudentNumber(), loginStudentRequest.getStudentPassword());
        Student student = students.stream().findFirst().orElse(null);
        if (Optional.ofNullable(student).isPresent()) {
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto);
            ClazzDto clazzDto = clazzService.queryClazzDtoByClazzId(student.getClazzId());
            studentDto.setClazzName(clazzDto.getClazzName());
            return studentDto;
        }
        return new StudentDto();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String studentJoinClazz(StudentJoinClazzRequest studentJoinClazzRequest) {
        Long studentId = TransitionUtil.stringToLong(studentJoinClazzRequest.getStudentId());
        Long clazzId = TransitionUtil.stringToLong(studentJoinClazzRequest.getClazzId());
        // 班级学生关联表增加记录
        if (!clazzStudentService.increaseRecord(clazzId, studentId).equals(
                ClazzStudentStatus.INSERT_SUCCESS.getMessage())) {
            return StudentJoinClazzStatus.JOIN_FAIL.getMessge();
        }
        // 班级表学生个数增加一
        if (!clazzService.studentCountIncrease(clazzId).equals(
                StudentCountIncreaseStatus.INCREASE_SUCCESS.getMessage())) {
            return StudentJoinClazzStatus.JOIN_FAIL.getMessge();
        }
        // 学生表班级字段更改
        Student student = new Student();
        student.setClazzId(clazzId);
        studentDao.updateStudentInfo(studentId, student);
        return StudentJoinClazzStatus.JOIN_SUCCESS.getMessge();
    }

    @Override
    public String studentQuitClazz(Long studentId) {
        // 学生表班级id字段更改为空
        studentDao.setClazzIdNullByStudentId(studentId);
        // 先将clazzStudent表中记录查询出来得到clazzId
        ClazzStudentDto clazzStudentDto = clazzStudentService.queryClazzStudentByStudentId(studentId);
        Long clazzId = clazzStudentDto.getClazzId();
        // 将clazzStudent表中记录进行物理删除
        clazzStudentService.deleteClazzStudentByStudentId(studentId);
        // 通过clazzId将学生数量减去1
        clazzService.studentCountSubtract(clazzId);
        return StudentQuitClazzStatus.QUIT_SUCCESS.getMessge();
    }

    @Override
    public StudentDto queryStudentByStudentId(Long studentId) {
        List<Student> students = studentDao.queryStudentListByStudentId(studentId);
        if (CollectionUtils.isEmpty(students)) {
            return new StudentDto();
        }
        Student student = students.stream().findFirst().orElse(null);
        if (Optional.ofNullable(student).isPresent()) {
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto);
            ClazzDto clazzDto = clazzService.queryClazzDtoByClazzId(student.getClazzId());
            studentDto.setClazzName(clazzDto.getClazzName());
            return studentDto;
        }
        return null;
    }

    @Override
    public List<StudentDto> queryStudentDtoListByClazzId(Long clazzId) {
        List<Student> students = studentDao.queryStudentListByClazzId(clazzId);
        List<StudentDto> studentDtos = students.stream().map(student -> {
            StudentDto studentDto = new StudentDto();
            ClazzDto clazzDto = clazzService.queryClazzDtoByClazzId(student.getClazzId());
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
