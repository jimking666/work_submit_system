package com.qushihan.work_submit_system.submitwork.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;
import com.qushihan.work_submit_system.submitwork.mapper.auto.SubmitWorkMapper;
import com.qushihan.work_submit_system.submitwork.model.auto.SubmitWork;
import com.qushihan.work_submit_system.submitwork.model.auto.SubmitWorkExample;

@Repository
public class SubmitWorkDao {

    @Autowired
    private SubmitWorkMapper submitWorkMapper;

    /**
     * 通过作业id查询提交作业列表
     *
     * @param workId
     *
     * @return
     */
    public List<SubmitWork> getByWorkId(Long workId) {
        if (!Optional.ofNullable(workId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        SubmitWorkExample submitWorkExample = new SubmitWorkExample();
        SubmitWorkExample.Criteria criteria = submitWorkExample.createCriteria();
        criteria.andWorkIdEqualTo(workId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return submitWorkMapper.selectByExample(submitWorkExample);
    }

    /**
     * 通过作业id和学生id查询提交作业列表
     *
     * @param workId
     * @param studentId
     *
     * @return
     */
    public List<SubmitWork> querySubmitWorkListByWorkIdAndStudentId(Long workId, Long studentId) {
        if (!Optional.ofNullable(workId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        if (!Optional.ofNullable(studentId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        SubmitWorkExample submitWorkExample = new SubmitWorkExample();
        SubmitWorkExample.Criteria criteria = submitWorkExample.createCriteria();
        criteria.andWorkIdEqualTo(workId);
        criteria.andStudentIdEqualTo(studentId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return submitWorkMapper.selectByExample(submitWorkExample);
    }

    /**
     * 新增提交作业
     *
     * @param submitWork
     *
     * @return
     */
    public int addSubmitWork(SubmitWork submitWork) {
        return submitWorkMapper.insertSelective(submitWork);
    }

    /**
     * 根据作业id与学生id更新提交作业
     *
     * @param submitWork
     *
     * @return
     */
    public int updateSubmitWork(SubmitWork submitWork) {
        Long workId = submitWork.getWorkId();
        Long studentId = submitWork.getStudentId();
        if (!Optional.ofNullable(workId).isPresent()) {
            return 0;
        }
        if (!Optional.ofNullable(studentId).isPresent()) {
            return 0;
        }
        SubmitWorkExample submitWorkExample = new SubmitWorkExample();
        SubmitWorkExample.Criteria criteria = submitWorkExample.createCriteria();
        criteria.andWorkIdEqualTo(workId);
        criteria.andStudentIdEqualTo(studentId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return submitWorkMapper.updateByExampleSelective(submitWork, submitWorkExample);
    }
}
