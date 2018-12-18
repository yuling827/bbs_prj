package com.zx.bbsprj.config;

import org.springframework.context.annotation.Configuration;

/**
 * 服务相关的配置
 */
@Configuration
public class MyServerConfig {

    //注册Servlet的组件-->Filter
//    @Bean
//    public FilterRegistrationBean myFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new NoCacheFilter());
//        //设置要拦截的请求--系统登录、退出时的两个跳转页禁止使用本地缓存
////        filterRegistrationBean.setUrlPatterns(Arrays.asList("/toLogin","/logout"));
//        filterRegistrationBean.setUrlPatterns(Arrays.asList("/loginSkip","/logoutSkip"));
//        return filterRegistrationBean;
//    }

}
