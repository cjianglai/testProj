package com.bwton.app.config;



import com.bwton.filter.ApiFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bwton.app.interceptor.QrcodeInterceptor;

import javax.servlet.Filter;

@Configuration

public class MyWebAppConfigurer 
        extends WebMvcConfigurerAdapter {

	@Autowired 
	QrcodeInterceptor qrcodeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(qrcodeInterceptor).addPathPatterns("/**");

//        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
//        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    public FilterRegistrationBean ApiStatFilter() throws Exception {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean((OncePerRequestFilter)createApiFilterFactory().getObject());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/app/qrcode/get");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
        return filterRegistrationBean;
    }
//    @Bean
//    public Filter AuthFilter() throws Exception {
//        return (OncePerRequestFilter)new ApiFilterFactoryBean().getObject();
//    }

	@Bean
    public ApiFilterFactoryBean createApiFilterFactory(){
	    return new ApiFilterFactoryBean();
    }
//	@Bean("apiFilter")
//    public Object createApiFilter() throws Exception {
//	    return createApiFilterFactory().getObject();
//    }
	


}