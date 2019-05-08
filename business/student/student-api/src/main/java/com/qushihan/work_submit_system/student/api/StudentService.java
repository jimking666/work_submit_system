package com.qushihan.work_submit_system.student.api;


import java.util.List;

import com.qushihan.work_submit_system.student.dto.LoginStudentRequest;
import com.qushihan.work_submit_system.student.dto.RegisterStudentRequest;
import com.qushihan.work_submit_system.student.dto.StudentDto;
import com.qushihan.work_submit_system.student.dto.StudentJoinClazzRequest;
import com.qushihan.work_submit_system.student.dto.StudentQuitClazzRequest;

public interface StudentService {

    /**
     * 学生注册
     *
     * @param registerStudentRequest
     *
     * @return
     */
    String registerStudent(RegisterStudentRequest registerStudentRequest);

    /**
     * 学生登陆
     *
     * @param loginStudentRequest
     */
    StudentDto loginStudent(LoginStudentRequest loginStudentRequest);

    /**
     * 学生加入班级
     *
     * @param studentJoinClazzRequest
     *
     * @return
     */
    String studentJoinClazz(StudentJoinClazzRequest studentJoinClazzRequest);

    /**
     * 学生退出班级
     *
     * @param studentId
     *
     * @return
     */
    String studentQuitClazz(Long studentId);

    /**
     * 通过学生id查询学生
     *
     * @param studentId
     *
     * @return
     */
    StudentDto queryStudentByStudentId(Long studentId);

    /**
     * 通过班级Id查询班中学生Dto列表
     *
     * @param clazzId
     * @return
     */
    List<StudentDto> queryStudentDtoListByClazzId(Long clazzId);
}
