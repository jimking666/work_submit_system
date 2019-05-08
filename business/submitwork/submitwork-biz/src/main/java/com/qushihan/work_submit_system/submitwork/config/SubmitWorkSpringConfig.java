package com.qushihan.work_submit_system.submitwork.config;

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
import com.qushihan.work_submit_system.submitwork.api.impl.SubmitWorkApiPkg;
import com.qushihan.work_submit_system.submitwork.biz.service.SubmitWorkServiceBizPkg;
import com.qushihan.work_submit_system.submitwork.dao.SubmitWorkDaoPkg;

@Configuration
@Import(value = { InfSpringConfig.class })
@ComponentScan(basePackageClasses = { SubmitWorkApiPkg.class, SubmitWorkServiceBizPkg.class, SubmitWorkDaoPkg.class })
@MapperScan(basePackages = { "com.qushihan.work_submit_system.submitwork.mapper" }, sqlSessionFactoryRef = "submitworkSqlSessionFactoryBean")
public class SubmitWorkSpringConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory submitworkSqlSessionFactoryBean() throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage("com.qushihan.work_submit_system.submitwork.model");
        return sessionFactoryBean.getObject();
    }
}
