package com.qushihan.work_submit_system.work.biz.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qushihan.work_submit_system.work.dao.WorkDao;
import com.qushihan.work_submit_system.work.dto.WorkDto;
import com.qushihan.work_submit_system.work.model.auto.Work;

@Service
public class WorkBizService {

    @Autowired
    private WorkDao workDao;

    /**
     * 通过workId修改work记录
     * @param workDto
     * @return
     */
    public int editByWorkId(WorkDto workDto) {
        Work work = new Work();
        BeanUtils.copyProperties(workDto, work);
        return workDao.editByWorkId(work);
    }

    /**
     * 通过workId获取WorkDto列表
     * @param workId
     * @return
     */
    public List<WorkDto> getByWorkId(Long workId) {
        List<Work> works = workDao.getByWorkId(workId);
        return works.stream()
                .map(work -> {
                    WorkDto workDto =  new WorkDto();
                    BeanUtils.copyProperties(work, workDto);
                    return workDto;
                }).collect(Collectors.toList());
    }
}
