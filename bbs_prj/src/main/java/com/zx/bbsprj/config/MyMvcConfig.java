package com.zx.bbsprj.config;

import com.zx.bbsprj.component.LoginHandlerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义配置文件，用于扩展Spring Boot对Spring MVC的默认配置
 * 无需全面接管Spring MVC
 */
@Configuration
@PropertySource(value = {"classpath:ckeditor.properties"})
public class MyMvcConfig implements WebMvcConfigurer {

    @Value("${ckeditor.storage.image.path}")
    private String ckeditorStorageImagePath;

    //配置视图的mapping映射
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            //添加视图映射
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
            }

            //扩展Spring MVC的拦截功能
            //不能拦截的请求有如下几个
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/","/index.html","/login","/logout","/showArticle/**","/qrySiteArticle",
                                "/register","/qryAllArticle","/webjars/**","/asserts/**","/user/**","/ckeditor/**",
                                "/image/**","/criCommit","/files/upload/image","/checkLegal","/successRegist");
            }

            //添加一个静态资源文件夹：D:/picture/
            //所有上传到该路径的静态文件都可通过/image/图片名的方式来访问
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/image/**").addResourceLocations("file:"+ckeditorStorageImagePath);
            }
        };
        return webMvcConfigurer;
    }

}
