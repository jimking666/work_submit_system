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
     * 通过班级id查询班级dto
     *
     * @param clazzId
     *
     * @return
     */
    ClazzDto getByClazzId(Long clazzId);

    /**
     * 通过搜索班级名称获取班级
     *
     * @return
     */
    List<ClazzDto> getBySearchClazzName(String searchClazzName);

    /**
     * 通过班级id修改班级记录
     *
     * @param clazzDto
     * @return
     */
    int updateByClazzId(ClazzDto clazzDto);
}
