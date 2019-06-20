package com.qushihan.work_submit_system.clazz.api.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.qushihan.work_submit_system.clazz.api.ClazzService;
import com.qushihan.work_submit_system.clazz.dao.ClazzDao;
import com.qushihan.work_submit_system.clazz.dto.ClazzDto;
import com.qushihan.work_submit_system.clazz.model.auto.Clazz;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzDao clazzDao;

    @Override
    public List<ClazzDto> queryAllClazz() {
        List<Clazz> clazzes = clazzDao.queryAllClazz();
        if (CollectionUtils.isEmpty(clazzes)) {
            return Collections.emptyList();
        }
        List<ClazzDto> clazzDtos = clazzes.stream().map(clazz -> {
            ClazzDto clazzDto = new ClazzDto();
            BeanUtils.copyProperties(clazz, clazzDto);
            return clazzDto;
        }).collect(Collectors.toList());
        return clazzDtos;
    }

    @Override
    public ClazzDto getByClazzId(Long clazzId) {
        Clazz clazz = clazzDao.getByClazzId(clazzId);
        ClazzDto clazzDto = new ClazzDto();
        BeanUtils.copyProperties(clazz, clazzDto);
        return clazzDto;
    }

    @Override
    public List<ClazzDto> getBySearchClazzName(String searchClazzName) {
        List<Clazz> clazzes = clazzDao.getBySearchClazzName(searchClazzName);
        return clazzes.stream()
                .map(clazz -> {
                    ClazzDto clazzDto = new ClazzDto();
                    BeanUtils.copyProperties(clazz, clazzDto);
                    return clazzDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int updateByClazzId(ClazzDto clazzDto) {
        Clazz clazz = new Clazz();
        BeanUtils.copyProperties(clazzDto, clazz);
        return clazzDao.updateByClazzId(clazz);
    }
}
