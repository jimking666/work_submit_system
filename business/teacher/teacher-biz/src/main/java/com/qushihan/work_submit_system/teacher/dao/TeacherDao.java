package com.qushihan.work_submit_system.teacher.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;
import com.qushihan.work_submit_system.teacher.mapper.auto.TeacherMapper;
import com.qushihan.work_submit_system.teacher.model.auto.Teacher;
import com.qushihan.work_submit_system.teacher.model.auto.TeacherExample;

@Repository
public class TeacherDao {

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 根据教师id查询教师列表
     *
     * @param teacherId
     *
     * @return
     */
    public List<Teacher> queryTeacherListByTeacherId(Long teacherId) {
        if (!Optional.ofNullable(teacherId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        TeacherExample teacherExample = new TeacherExample();
        TeacherExample.Criteria criteria = teacherExample.createCriteria();
        criteria.andTeacherIdEqualTo(teacherId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return teacherMapper.selectByExample(teacherExample);
    }
}
