package com.qushihan.work_submit_system.submitwork.util;

import java.util.Random;

public class SubmitWorkUtil {

    /**
     * 随机生成三组 xxxx(四位数字)最后拼接起来 生成 submitWorkId
     *
     * @return
     */
    public static Long getSubmitWorkId() {
        int randomNumber1 = new Random().nextInt(9000) + 1000;
        int randomNumber2 = new Random().nextInt(9000) + 1000;
        int randomNumber3 = new Random().nextInt(9000) + 1000;
        String submitWorkId = String.valueOf(randomNumber1) + String.valueOf(randomNumber2) + String.valueOf(randomNumber3);
        return Long.parseLong(submitWorkId);
    }
}
