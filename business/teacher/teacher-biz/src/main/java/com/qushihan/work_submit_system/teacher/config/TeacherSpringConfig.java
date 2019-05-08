package com.qushihan.work_submit_system.teacher.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.qushihan.work_submit_system.inf.config.InfSpringConfig;
import com.qushihan.work_submit_system.teacher.api.impl.TeacherApiPkg;
import com.qushihan.work_submit_system.teacher.biz.service.TeacherServiceBizPkg;
import com.qushihan.work_submit_system.teacher.dao.TeacherDaoPkg;

@Configuration
@Import(value = { InfSpringConfig.class })
@ComponentScan(basePackageClasses = { TeacherApiPkg.class, TeacherServiceBizPkg.class, TeacherDaoPkg.class })
@MapperScan(basePackages = {"com.qushihan.work_submit_system.teacher.mapper"}, sqlSessionFactoryRef =
        "teacherSqlSessionFactoryBean")
public class TeacherSpringConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory teacherSqlSessionFactoryBean() throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage("com.qushihan.work_submit_system.teacher.model");
        return sessionFactoryBean.getObject();
    }
}