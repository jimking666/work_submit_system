package com.qushihan.work_submit_system.clazz.api.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.qushihan.work_submit_system.clazz.api.ClazzService;
import com.qushihan.work_submit_system.clazz.dao.ClazzDao;
import com.qushihan.work_submit_system.clazz.dto.ClazzDto;
import com.qushihan.work_submit_system.clazz.enums.ClazzErrorCode;
import com.qushihan.work_submit_system.clazz.enums.StudentCountIncreaseStatus;
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
    public String studentCountIncrease(Long clazzId) {
        List<Clazz> clazzes = clazzDao.queryClazzListByClazzId(clazzId);
        if (CollectionUtils.isEmpty(clazzes)) {
            return ClazzErrorCode.NO_SUCH_CLAZZ.getMessage();
        }
        Clazz clazz = clazzes.stream().findFirst().orElse(null);
        Long count = clazz.getStudentCount();
        clazz.setStudentCount(count + 1);
        clazzDao.studentCountIncrease(clazz, clazzId);
        return StudentCountIncreaseStatus.INCREASE_SUCCESS.getMessage();
    }

    @Override
    public Integer studentCountSubtract(Long clazzId) {
        List<Clazz> clazzes = clazzDao.queryClazzListByClazzId(clazzId);
        Clazz clazz = clazzes.stream().findFirst().orElse(null);
        Long count = clazz.getStudentCount();
        clazz.setStudentCount(count - 1);
        return clazzDao.studentCountIncrease(clazz, clazzId);
    }

    @Override
    public ClazzDto queryClazzDtoByClazzId(Long clazzId) {
        List<Clazz> clazzes = clazzDao.queryClazzListByClazzId(clazzId);
        Clazz clazz = clazzes.stream().findFirst().orElse(null);
        if (Optional.ofNullable(clazz).isPresent()) {
            ClazzDto clazzDto = new ClazzDto();
            BeanUtils.copyProperties(clazz, clazzDto);
            return clazzDto;
        }
        return new ClazzDto();
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
}
