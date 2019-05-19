package com.qushihan.work_submit_system.work.dto;

import lombok.Data;

/**
 * 搜索作业请求
 */
@Data
public class GetWorkBySearchRequest {

    /**
     * 搜索作业题目
     */
    private String searchWorkTitle;
}
