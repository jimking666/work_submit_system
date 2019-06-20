package com.qushihan.work_submit_system.clazz.dao;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qushihan.work_submit_system.clazz.mapper.auto.ClazzMapper;
import com.qushihan.work_submit_system.clazz.model.auto.Clazz;
import com.qushihan.work_submit_system.clazz.model.auto.ClazzExample;
import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;

@Repository
public class ClazzDao {

    @Autowired
    private ClazzMapper clazzMapper;

    /**
     * 查询所有班级
     *
     * @return
     */
    public List<Clazz> queryAllClazz() {
        ClazzExample clazzExample = new ClazzExample();
        ClazzExample.Criteria criteria = clazzExample.createCriteria();
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return clazzMapper.selectByExample(clazzExample);
    }

    /**
     * 通过班级id查询班级
     *
     * @return
     */
    public Clazz getByClazzId(Long clazzId) {
        if (!Optional.ofNullable(clazzId).isPresent()) {
            return new Clazz();
        }
        ClazzExample clazzExample = new ClazzExample();
        ClazzExample.Criteria criteria = clazzExample.createCriteria();
        criteria.andClazzIdEqualTo(clazzId);
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return clazzMapper.selectByExample(clazzExample).stream()
                .findFirst()
                .orElse(new Clazz());
    }

    /**
     * 通过班级名称搜索班级
     *
     * @param searchClazzName
     * @return
     */
    public List<Clazz> getBySearchClazzName(String searchClazzName) {
        ClazzExample clazzExample = new ClazzExample();
        ClazzExample.Criteria criteria = clazzExample.createCriteria();
        if (StringUtils.isNotEmpty(searchClazzName)) {
            criteria.andClazzNameLike(searchClazzName + "%");
        }
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return clazzMapper.selectByExample(clazzExample);
    }

    /**
     * 通过班级id修改班级记录
     *
     * @param clazz
     * @return
     */
    public int updateByClazzId(Clazz clazz) {
        if (!Optional.ofNullable(clazz).isPresent()) {
            return 0;
        }
        ClazzExample clazzExample = new ClazzExample();
        ClazzExample.Criteria criteria = clazzExample.createCriteria();
        criteria.andClazzIdEqualTo(clazz.getClazzId());
        criteria.andIsdelEqualTo(FieldIsdelStatus.ISDEL_FALSE.getIsdel());
        return clazzMapper.updateByExampleSelective(clazz, clazzExample);
    }
}
