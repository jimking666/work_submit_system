package com.qushihan.work_submit_system.app.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/" ).setViewName( "forward:/index/login" );
        registry.addViewController("/register").setViewName("forward:/index/register");
        registry.addViewController("/workDetailPage").setViewName("forward:/index/workDetailPage");
        registry.addViewController("/browseClazzPage").setViewName("forward:/index/browseClazzPage");
        registry.addViewController("/browseClazzPageSearch").setViewName("forward:/index/browseClazzPageSearch");
        registry.addViewController("/studentDetailPage").setViewName("forward:/index/studentDetailPage");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers( registry );
    }
    // SpringBoot重写addResourceHandlers解决resources下面静态资源无法访问
    @Override // 对静态资源进行处理，否则boot是把所有静态资源进行拦截
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").resourceChain(true);
    }
}
