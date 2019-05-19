package com.qushihan.work_submit_system.work.api.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.qushihan.work_submit_system.core.api.CourseTeacherClazzService;
import com.qushihan.work_submit_system.core.dto.CourseTeacherClazzDto;
import com.qushihan.work_submit_system.submitwork.api.SubmitWorkService;
import com.qushihan.work_submit_system.submitwork.dto.SubmitWorkDto;
import com.qushihan.work_submit_system.work.api.WorkService;
import com.qushihan.work_submit_system.work.biz.service.WorkBizService;
import com.qushihan.work_submit_system.work.dao.WorkDao;
import com.qushihan.work_submit_system.work.dto.WorkDto;
import com.qushihan.work_submit_system.work.dto.WorkRelationDto;
import com.qushihan.work_submit_system.work.model.auto.Work;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private CourseTeacherClazzService courseTeacherClazzService;

    @Autowired
    private SubmitWorkService submitWorkService;

    @Autowired
    private WorkDao workDao;

    @Autowired
    private WorkBizService workBizService;

    @Override
    public List<WorkRelationDto> queryWorkRelationDtoByClazzId(Long clazzId, Long studentId) {
        List<CourseTeacherClazzDto> courseTeacherClazzDtos = courseTeacherClazzService.queryCourseTeacherClazzDtoListByClazzId(
                clazzId);
        if (CollectionUtils.isEmpty(courseTeacherClazzDtos)) {
            return Collections.EMPTY_LIST;
        }
        List<WorkRelationDto> workRelationDtos = Lists.newArrayList();
        for (CourseTeacherClazzDto courseTeacherClazzDto : courseTeacherClazzDtos) {
            Long courseTeacherClazzId = courseTeacherClazzDto.getCourseTeacherClazzId();
            List<Work> works = workDao.queryWorkListByCourseTeacherClazzId(courseTeacherClazzId);
            if (!CollectionUtils.isEmpty(works)) {
                List<WorkRelationDto> workRelationDtoList = works.stream().map(work -> {
                    SubmitWorkDto submitWorkDto = submitWorkService.querySubmitWorkDtoByWorkIdAndStudentId(
                            work.getWorkId(), studentId);
                    WorkRelationDto workRelationDto = new WorkRelationDto();
                    workRelationDto.setWorkId(work.getWorkId())
                            .setWorkTitle(work.getWorkTitle())
                            .setWorkContent(work.getWorkContent())
                            .setCourseId(courseTeacherClazzDto.getCourseId())
                            .setCourseName(courseTeacherClazzDto.getCourseName())
                            .setTeacherId(courseTeacherClazzDto.getTeacherId())
                            .setTeacherName(courseTeacherClazzDto.getTeacherName())
                            .setCreateTime(work.getCreateTime())
                            .setSubmitWorkId(Optional.ofNullable(submitWorkDto.getSubmitWorkId()).orElse(null))
                            .setScore(Optional.ofNullable(submitWorkDto.getScore()).orElse(null));
                    return workRelationDto;
                }).collect(Collectors.toList());
                workRelationDtos.addAll(workRelationDtoList);
            }
        }
        return workRelationDtos;
    }

    @Override
    public List<WorkDto> queryWorkDtoListByCourseTeacherClazzId(Long courseTeacherClazzId) {
        List<Work> works = workDao.queryWorkListByCourseTeacherClazzId(courseTeacherClazzId);
        if (CollectionUtils.isEmpty(works)) {
            return Collections.EMPTY_LIST;
        }
        List<WorkDto> workDtos = works.stream().map(work -> {
            WorkDto workDto = new WorkDto();
            BeanUtils.copyProperties(work, workDto);
            return workDto;
        }).collect(Collectors.toList());
        return workDtos;
    }

    @Override
    public int editByWorkId(WorkDto workDto) {
        return workBizService.editByWorkId(workDto);
    }

    @Override
    public List<WorkDto> getByWorkId(Long workId) {
        return workBizService.getByWorkId(workId);
    }

    @Override
    public List<WorkDto> getBySearchWorkTitle(String searchWorkTitle) {
        List<Work> works = workDao.getBySearchWorkTitle(searchWorkTitle);
        return works.stream()
                .map(work -> {
                    WorkDto workDto = new WorkDto();
                    BeanUtils.copyProperties(work, workDto);
                    return workDto;
                })
                .collect(Collectors.toList());
    }
}
