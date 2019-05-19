package com.qushihan.work_submit_system.work.api;

import java.util.List;

import com.qushihan.work_submit_system.work.dto.WorkDto;
import com.qushihan.work_submit_system.work.dto.WorkRelationDto;

public interface WorkService {

    /**
     * 通过班级id和学生id查询作业关联列表
     *
     * @param clazzId
     *
     * @return
     */
    List<WorkRelationDto> queryWorkRelationDtoByClazzId(Long clazzId, Long studentId);

    /**
     * 通过课程教师班级id查询作业Dto列表
     *
     * @param courseTeacherClazzId
     *
     * @return
     */
    List<WorkDto> queryWorkDtoListByCourseTeacherClazzId(Long courseTeacherClazzId);

    /**
     * 通过workId修改work记录
     * @param workDto
     * @return
     */
    int editByWorkId(WorkDto workDto);

    /**
     * 通过workId获取WorkDto列表
     * @param workId
     * @return
     */
    List<WorkDto> getByWorkId(Long workId);

    /**
     * 通过搜索作业题目获取作业
     *
     * @return
     */
    List<WorkDto> getBySearchWorkTitle(String searchWorkTitle);
}
