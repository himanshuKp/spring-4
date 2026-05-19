package com.himanshu.springpractice.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
class WebConfiguration {

//    @Bean
//    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter(){
//        FilterRegistrationBean<ForwardedHeaderFilter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(new ForwardedHeaderFilter());
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }

//    @Bean
//    public FilterRegistrationBean<ShallowEtagHeaderFilter> etagHeaderFilterFilterRegistrationBean(){
//        FilterRegistrationBean<ShallowEtagHeaderFilter> filterFilterRegistrationBean =
//                new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());
//        filterFilterRegistrationBean.addUrlPatterns("/api/*");
//        return filterFilterRegistrationBean;
//    }
}
