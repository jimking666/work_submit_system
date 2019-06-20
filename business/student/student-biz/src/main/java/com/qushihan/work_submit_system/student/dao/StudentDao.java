package com.qushihan.work_submit_system.student.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;
import com.qushihan.work_submit_system.student.mapper.auto.StudentMapper;
import com.qushihan.work_submit_system.student.model.auto.Student;
import com.qushihan.work_submit_system.student.model.auto.StudentExample;


@Repository
public class StudentDao {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 学生注册
     *
     * @param student
     *
     * @return
     */
    public Integer registerStudent(Student student) {
        return studentMapper.insertSelective(student);
    }

    /**
     * 通过账号密码查询学生列表
     *
     * @param studentNumber
     * @param studentPassword
     *
     * @return
     */
    public List<Student> queryStudentByStudentNumberAndStudentPassword(Long studentNumber, String studentPassword) {
        if (!Optional.ofNullable(studentNumber).isPresent()) {
            return Collections.emptyList();
        }
        if (StringUtils.isEmpty(studentPassword)) {
            return Collections.emptyList();
        }
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andStudentNumberEqualTo(studentNumber);
        criteria.andStudentPasswordEqualTo(studentPassword);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return studentMapper.selectByExample(example);
    }

    /**
     * 检查是否重复注册
     *
     * @param studentNumber
     *
     * @return
     */
    public List<Student> judgeRepeatRegister(Long studentNumber) {
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andStudentNumberEqualTo(studentNumber);
        return studentMapper.selectByExample(example);
    }

    /**
     * 通过学生id更改学生信息
     *
     * @param student
     * @param student
     *
     * @return
     */
    public int updateByStudentId(Student student) {
        if (!Optional.ofNullable(student).isPresent()) {
            return 0;
        }
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andStudentIdEqualTo(student.getStudentId());
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return studentMapper.updateByExample(student, studentExample);
    }

    /**
     * 通过学生id查询学生
     *
     * @param studentId
     *
     * @return
     */
    public Student getByStudentId(Long studentId) {
        if (!Optional.ofNullable(studentId).isPresent()) {
            return new Student();
        }
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return studentMapper.selectByExample(example).stream()
                .findFirst()
                .orElse(new Student());
    }

    /**
     * 通过班级id查询学生
     *
     * @param clazzId
     *
     * @return
     */
    public List<Student> queryStudentListByClazzId(Long clazzId) {
        if (!Optional.ofNullable(clazzId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andClazzIdEqualTo(clazzId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return studentMapper.selectByExample(studentExample);
    }

    /**
     * 通过学生名称搜索学生
     *
     * @param searchStudentName
     * @return
     */
    public List<Student> getBySearchStudentName(String searchStudentName) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        if (StringUtils.isNotEmpty(searchStudentName)) {
            criteria.andStudentNameLike(searchStudentName + "%");
        }
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return studentMapper.selectByExample(studentExample);
    }
}
