package com.qushihan.work_submit_system.core.dao;

import java.util.List;

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
     * 新增一条关联记录
     *
     * @param clazzStudent
     *
     * @return
     */
    public Integer increaseRecord(ClazzStudent clazzStudent) {
        return clazzStudentMapper.insertSelective(clazzStudent);
    }

    /**
     * 根据studentId将ClazzStudent记录删除
     *
     * @param studentId
     *
     * @return
     */
    public Integer deleteClazzStudentByStudentId(Long studentId) {
        ClazzStudentExample clazzStudentExample = new ClazzStudentExample();
        ClazzStudentExample.Criteria criteria = clazzStudentExample.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        return clazzStudentMapper.deleteByExample(clazzStudentExample);
    }

    /**
     * 通过学生id查询班级学生关联表记录
     *
     * @param studentId
     *
     * @return
     */
    public List<ClazzStudent> queryClazzStudentListByStudentId(Long studentId) {
        ClazzStudentExample clazzStudentExample = new ClazzStudentExample();
        ClazzStudentExample.Criteria criteria = clazzStudentExample.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return clazzStudentMapper.selectByExample(clazzStudentExample);
    }
}
