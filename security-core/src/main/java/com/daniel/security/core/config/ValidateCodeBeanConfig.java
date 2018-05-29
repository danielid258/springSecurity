package com.daniel.security.core.config;

import com.daniel.security.core.properties.SecurityProperties;
import com.daniel.security.core.validate.code.ImageCodeGenerator;
import com.daniel.security.core.validate.code.ValidateCodeGenerator;
import com.daniel.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.daniel.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Daniel on 2018/5/29.
 */
@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    //根据名称判断 此处不能使用类型判断: ValidateCodeGenerator还有其他的实现类SmsCodeGenerator,如果用类型判断 下面的注入不会执行
    @ConditionalOnMissingBean(name = "imageCodeGenerator")  //当spring容器中不存在 name = "imageCodeGenerator" 的bean时才执行此处的的注入; 否则使用已有的bean
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    //根据类型判断
    @ConditionalOnMissingBean(SmsCodeSender.class)  //当spring容器中找不到SmsCodeSender的实现类时 才执行此处的注入; 否则使用已有的bean
    public SmsCodeSender smsCodeSender() {
        SmsCodeSender sender = new DefaultSmsCodeSender();
        return sender;
    }
}
