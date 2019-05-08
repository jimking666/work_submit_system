package com.qushihan.work_submit_system.core.util;

import java.util.Random;

public class ClazzStudentUtil {

    /**
     * 使用clazzId最后4位 + studentId最后4位 + 4位随机生成数 生成clazzStudentId
     *
     * @param clazzId
     * @param studentId
     *
     * @return
     */
    public static Long getClazzStudentId(Long clazzId, Long studentId) {
        String number1 = String.valueOf(clazzId);
        number1 = number1.substring(number1.length() - 4);
        String number2 = String.valueOf(studentId);
        number2 = number2.substring(number2.length() - 4);
        int randomNumber = new Random().nextInt(9000) + 1000;
        String clazzStudentId = number1 + number2 + String.valueOf(randomNumber);
        return Long.parseLong(clazzStudentId);
    }
}
