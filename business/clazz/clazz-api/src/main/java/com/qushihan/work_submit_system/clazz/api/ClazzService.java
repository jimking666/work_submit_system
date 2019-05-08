package com.qushihan.work_submit_system.clazz.api;

import java.util.List;

import com.qushihan.work_submit_system.clazz.dto.ClazzDto;

public interface ClazzService {

    /**
     * 查询所有班级
     *
     * @return
     */
    List<ClazzDto> queryAllClazz();

    /**
     * 班级学生个数增加1
     *
     * @param clazzId
     *
     * @return
     */
    String studentCountIncrease(Long clazzId);

    /**
     * 班级学生个数减去1
     *
     * @param clazzId
     *
     * @return
     */
    Integer studentCountSubtract(Long clazzId);

    /**
     * 通过班级id查询班级dto
     *
     * @param clazzId
     *
     * @return
     */
    ClazzDto queryClazzDtoByClazzId(Long clazzId);
}
