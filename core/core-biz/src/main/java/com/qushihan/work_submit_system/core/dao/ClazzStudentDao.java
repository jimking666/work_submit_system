package com.qushihan.work_submit_system.core.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.core.mapper.auto.ClazzStudentMapper;
import com.qushihan.work_submit_system.core.model.auto.ClazzStudent;
import com.qushihan.work_submit_system.core.model.auto.ClazzStudentExample;
import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;

@Repository
public class ClazzStudentDao {

    @Autowired
    ClazzStudentMapper clazzStudentMapper;

    /**
     * 新增班级学生记录
     *
     * @param clazzStudent
     *
     * @return
     */
    public int insertClazzStudent(ClazzStudent clazzStudent) {
        if (!Optional.ofNullable(clazzStudent).isPresent()) {
            return 0;
        }
        return clazzStudentMapper.insertSelective(clazzStudent);
    }

    /**
     * 根据clazzStudentId更改ClazzStudent记录
     *
     * @param clazzStudent
     *
     * @return
     */
    public int updateByClazzStudentId(ClazzStudent clazzStudent) {
        if (!Optional.ofNullable(clazzStudent).isPresent()) {
            return 0;
        }
        ClazzStudentExample clazzStudentExample = new ClazzStudentExample();
        ClazzStudentExample.Criteria criteria = clazzStudentExample.createCriteria();
        criteria.andClazzStudentIdEqualTo(clazzStudent.getClazzStudentId());
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return clazzStudentMapper.updateByExampleSelective(clazzStudent, clazzStudentExample);
    }

    /**
     * 通过学生id查询班级学生关联表记录
     *
     * @param studentId
     *
     * @return
     */
    public List<ClazzStudent> getByStudentId(Long studentId) {
        if (!Optional.ofNullable(studentId).isPresent()) {
            return Collections.emptyList();
        }
        ClazzStudentExample clazzStudentExample = new ClazzStudentExample();
        ClazzStudentExample.Criteria criteria = clazzStudentExample.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return clazzStudentMapper.selectByExample(clazzStudentExample);
    }

    /**
     * 通过班级id获取班级学生记录
     *
     * @param clazzId
     * @return
     */
    public List<ClazzStudent> getByClazzId(Long clazzId) {
        if (!Optional.ofNullable(clazzId).isPresent()) {
            return Collections.emptyList();
        }
        ClazzStudentExample clazzStudentExample = new ClazzStudentExample();
        ClazzStudentExample.Criteria criteria = clazzStudentExample.createCriteria();
        criteria.andClazzIdEqualTo(clazzId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return clazzStudentMapper.selectByExample(clazzStudentExample);
    }
}
