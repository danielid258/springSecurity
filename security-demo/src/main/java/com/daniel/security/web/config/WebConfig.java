package com.daniel.security.web.config;

import com.daniel.security.web.filter.ThirdTimeFilter;
import com.daniel.security.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * on 5/25/2018.
 */
//@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private TimeInterceptor timeInterceptor;
    /**
     * 注册Interceptor
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    /**
     * 把没有@Component注解的Filter加入到过滤器链中
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean timeFilterThird() {
        FilterRegistrationBean bean = new FilterRegistrationBean();

        //加入到过滤器链中
        ThirdTimeFilter thirdTimeFilter = new ThirdTimeFilter();
        bean.setFilter(thirdTimeFilter);

        //指定要拦截的url
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        bean.setUrlPatterns(urls);

        return bean;
    }
}
