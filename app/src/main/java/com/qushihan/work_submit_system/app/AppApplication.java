package com.qushihan.work_submit_system.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.qushihan.work_submit_system.app.controller.config.WebMvcConfig;
import com.qushihan.work_submit_system.clazz.config.ClazzSpringConfig;
import com.qushihan.work_submit_system.core.config.CoreSpringConfig;
import com.qushihan.work_submit_system.course.config.CourseSpringConfig;
import com.qushihan.work_submit_system.inf.config.InfSpringConfig;
import com.qushihan.work_submit_system.student.config.StudentSpringConfig;
import com.qushihan.work_submit_system.submitwork.config.SubmitWorkSpringConfig;
import com.qushihan.work_submit_system.teacher.config.TeacherSpringConfig;
import com.qushihan.work_submit_system.work.config.WorkSpringConfig;


@SpringBootApplication
@Import(value = { CoreSpringConfig.class, WebMvcConfig.class, StudentSpringConfig.class, ClazzSpringConfig.class, InfSpringConfig.class, CourseSpringConfig.class, TeacherSpringConfig.class, WorkSpringConfig.class, SubmitWorkSpringConfig.class })
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}

