package com.qushihan.work_submit_system.submitwork.api.impl;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.qushihan.work_submit_system.inf.enums.FieldIsdelStatus;
import com.qushihan.work_submit_system.submitwork.api.SubmitWorkService;
import com.qushihan.work_submit_system.submitwork.biz.service.SubmitWorkBizService;
import com.qushihan.work_submit_system.submitwork.dao.SubmitWorkDao;
import com.qushihan.work_submit_system.submitwork.dto.SubmitWorkDto;
import com.qushihan.work_submit_system.submitwork.enums.EditSubmitWorkStatus;
import com.qushihan.work_submit_system.submitwork.model.auto.SubmitWork;
import com.qushihan.work_submit_system.submitwork.util.DuplicateCheckingUtil;
import com.qushihan.work_submit_system.submitwork.util.SubmitWorkUtil;
import com.qushihan.work_submit_system.work.api.WorkService;
import com.qushihan.work_submit_system.work.dto.WorkDto;

@Service
public class SubmitWorkServiceImpl implements SubmitWorkService {

    @Autowired
    private SubmitWorkDao submitWorkDao;

    @Autowired
    private SubmitWorkBizService submitWorkBizService;

    @Autowired
    private WorkService workService;

    @Override
    public List<SubmitWorkDto> querySubmitWorkDtoListByWorkId(Long workId) {
        return submitWorkBizService.getByWorkId(workId);
    }

    @Override
    public SubmitWorkDto querySubmitWorkDtoByWorkIdAndStudentId(Long workId, Long studentId) {
        List<SubmitWork> submitWorks = submitWorkDao.querySubmitWorkListByWorkIdAndStudentId(workId, studentId);
        return submitWorks.stream().findFirst().map(submitWork -> {
            SubmitWorkDto submitWorkDto = new SubmitWorkDto();
            BeanUtils.copyProperties(submitWork, submitWorkDto);
            return submitWorkDto;
        }).orElse(new SubmitWorkDto());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String editSubmitWork(Long workId, Long studentId, String submitWorkContent) {
        List<SubmitWork> submitWorks = submitWorkDao.querySubmitWorkListByWorkIdAndStudentId(workId, studentId);
        // 得到老师定义的重复率
        float repetitiveRate = workService.getByWorkId(workId).stream()
                .findFirst()
                .map(WorkDto::getRepetitiveRate)
                .orElse(0f);
        // 先检查重复率
        SubmitWork oldSubmitWork = submitWorks.stream().findFirst().orElse(new SubmitWork());
        Long oldSubmitWorkId = Optional.ofNullable(oldSubmitWork).map(SubmitWork::getSubmitWorkId).orElse(0L);
        float maxRepetitiveRate = maxDuplicateChecking(submitWorkContent, oldSubmitWorkId, workId);
        if (maxRepetitiveRate > repetitiveRate) {
            DecimalFormat df = new DecimalFormat("0.00%");
            return EditSubmitWorkStatus.REPETITION.getMessage() + "+_+" + df.format(maxRepetitiveRate);
        }
        if (!CollectionUtils.isEmpty(submitWorks)) {
            // 将老记录软删除
            oldSubmitWork.setIsdel(FieldIsdelStatus.ISDEL_TRUE.getIsdel());
            submitWorkDao.updateSubmitWork(oldSubmitWork);
        }
        // 增加新纪录
        Long submitWorkId = SubmitWorkUtil.getSubmitWorkId();
        SubmitWork newSubmitWork = new SubmitWork();
        newSubmitWork.setSubmitWorkId(submitWorkId);
        newSubmitWork.setSubmitWorkContent(submitWorkContent);
        newSubmitWork.setWorkId(workId);
        newSubmitWork.setStudentId(studentId);
        newSubmitWork.setHighRepetitiveRate(maxRepetitiveRate);
        submitWorkDao.addSubmitWork(newSubmitWork);
        // 将work表中的该workId作业总数
        List<SubmitWorkDto> submitWorkDtos = submitWorkBizService.getByWorkId(workId);
        List<WorkDto> workDtos = workService.getByWorkId(workId);
        WorkDto workDto = workDtos.stream()
                .findFirst()
                .orElse(new WorkDto());
        workDto.setSubmitWorkCount(submitWorkDtos.size());
        workService.editByWorkId(workDto);
        return EditSubmitWorkStatus.UPDATE_SUCCESS.getMessage();
    }

    /**
     * 检测最大重复率
     * @param newContent
     * @return
     */
    private Float maxDuplicateChecking(String newContent, Long submitWorkId, Long workId) {
        float highRepetitiveRate = 0.0f;
        DuplicateCheckingUtil duplicateCheckingUtil = new DuplicateCheckingUtil();
        List<String> oldContents = submitWorkDao.getByWorkId(workId).stream()
                .filter(submitWork -> !submitWork.getSubmitWorkId().equals(submitWorkId))
                .map(SubmitWork::getSubmitWorkContent)
                .collect(Collectors.toList());
        for (String oldContent : oldContents) {
            float repetitiveRate = duplicateCheckingUtil.getSimilarity(duplicateCheckingUtil.getPreProcessedCode(newContent),
                                                                       duplicateCheckingUtil.getPreProcessedCode(oldContent));
            if (highRepetitiveRate < repetitiveRate) {
                highRepetitiveRate = repetitiveRate;
            }
        }
        return  highRepetitiveRate;
    }

    @Override
    public String querySubmitWorkContent(Long workId, Long studentId) {
        List<SubmitWork> submitWorks = submitWorkDao.querySubmitWorkListByWorkIdAndStudentId(workId, studentId);
        String submitWorkContent = submitWorks.stream().findFirst().map(SubmitWork::getSubmitWorkContent).orElse("");
        return submitWorkContent;
    }
}
