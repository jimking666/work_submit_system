package com.qushihan.work_submit_system.work.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;
import com.qushihan.work_submit_system.work.dto.WorkDto;
import com.qushihan.work_submit_system.work.mapper.auto.WorkMapper;
import com.qushihan.work_submit_system.work.model.auto.Work;
import com.qushihan.work_submit_system.work.model.auto.WorkExample;

@Repository
public class WorkDao {

    @Autowired
    private WorkMapper workMapper;

    /**
     * 通过课程教师班级id查询作业列表
     *
     * @param courseTeacherclazzId
     *
     * @return
     */
    public List<Work> queryWorkListByCourseTeacherClazzId(Long courseTeacherclazzId) {
        if (!Optional.ofNullable(courseTeacherclazzId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        WorkExample workExample = new WorkExample();
        WorkExample.Criteria criteria = workExample.createCriteria();
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        criteria.andCourseTeacherClazzIdEqualTo(courseTeacherclazzId);
        return workMapper.selectByExample(workExample);
    }

    /**
     * 通过workId修改work记录
     * @param work
     * @return
     */
    public int editByWorkId(Work work) {
        if (!Optional.ofNullable(work).map(Work::getWorkId).isPresent()) {
            return 0;
        }
        WorkExample workExample = new WorkExample();
        WorkExample.Criteria criteria = workExample.createCriteria();
        criteria.andWorkIdEqualTo(work.getWorkId());
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return workMapper.updateByExampleSelective(work, workExample);
    }

    /**
     * 通过workId获取work列表
     * @param workId
     * @return
     */
    public List<Work> getByWorkId(Long workId) {
        if (!Optional.ofNullable(workId).isPresent()) {
            return Collections.EMPTY_LIST;
        }
        WorkExample workExample = new WorkExample();
        WorkExample.Criteria criteria = workExample.createCriteria();
        criteria.andWorkIdEqualTo(workId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return workMapper.selectByExample(workExample);
    }

    /**
     * 通过作业题目搜索作业
     *
     * @param searchWorkTitle
     * @return
     */
    public List<Work> getBySearchWorkTitle(String searchWorkTitle) {
        WorkExample clazzExample = new WorkExample();
        WorkExample.Criteria criteria = clazzExample.createCriteria();
        if (StringUtils.isNotEmpty(searchWorkTitle)) {
            criteria.andWorkTitleLike(searchWorkTitle + "%");
        }
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return workMapper.selectByExample(clazzExample);
    }
}
