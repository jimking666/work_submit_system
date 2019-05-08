package com.qushihan.work_submit_system.student.util;

import java.util.Random;

public class StudentUtil {

    /**
     * 使用studentNumber + xxxx(四位数字)生成studentId
     * @param studentNumber
     * @return
     */
    public static Long getStudentId(Long studentNumber) {
        int randomNumber = new Random().nextInt(9000) + 1000;
        String studentId = String.valueOf(studentNumber) + String.valueOf(randomNumber);
        return Long.parseLong(studentId);
    }
}
