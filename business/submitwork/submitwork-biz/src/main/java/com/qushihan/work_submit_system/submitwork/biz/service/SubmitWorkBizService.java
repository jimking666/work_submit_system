package com.qushihan.work_submit_system.submitwork.biz.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.qushihan.work_submit_system.submitwork.dao.SubmitWorkDao;
import com.qushihan.work_submit_system.submitwork.dto.SubmitWorkDto;
import com.qushihan.work_submit_system.submitwork.model.auto.SubmitWork;

@Service
public class SubmitWorkBizService {

    @Autowired
    private SubmitWorkDao submitWorkDao;

    /**
     * 通过workId获取SubmitWorkDto列表
     * @param workId
     * @return
     */
    public List<SubmitWorkDto> getByWorkId(Long workId) {
        List<SubmitWork> submitWorks = submitWorkDao.getByWorkId(workId);
        if (CollectionUtils.isEmpty(submitWorks)) {
            return Collections.EMPTY_LIST;
        }
        return submitWorks.stream()
                .map(submitWork -> {
                    SubmitWorkDto submitWorkDto = new SubmitWorkDto();
                    BeanUtils.copyProperties(submitWork, submitWorkDto);
                    return submitWorkDto;
                }).collect(Collectors.toList());
    }

}
