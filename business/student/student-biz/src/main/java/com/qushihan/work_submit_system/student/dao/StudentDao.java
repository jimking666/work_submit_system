package com.qushihan.work_submit_system.student.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;
import com.qushihan.work_submit_system.student.mapper.auto.StudentMapper;
import com.qushihan.work_submit_system.student.mapper.biz.StudentBizMapper;
import com.qushihan.work_submit_system.student.model.auto.Student;
import com.qushihan.work_submit_system.student.model.auto.StudentExample;


@Repository
public class StudentDao {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentBizMapper studentBizMapper;

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
     * @param studentId
     * @param student
     *
     * @return
     */
    public Integer updateStudentInfo(Long studentId, Student student) {
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        return studentMapper.updateByExampleSelective(student, example);
    }

    /**
     * 通过学生id查询学生列表
     *
     * @param studentId
     *
     * @return
     */
    public List<Student> queryStudentListByStudentId(Long studentId) {
        if (!Optional.ofNullable(studentId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return studentMapper.selectByExample(example);
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
     * 通过学生id将Student表clazzId字段置为NULL
     *
     * @param studentId
     *
     * @return
     */
    public int setClazzIdNullByStudentId(Long studentId) {
        if (!Optional.ofNullable(studentId).isPresent()) {
            return 0;
        }
        return studentBizMapper.setClazzIdNullByStudentId(studentId);
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
