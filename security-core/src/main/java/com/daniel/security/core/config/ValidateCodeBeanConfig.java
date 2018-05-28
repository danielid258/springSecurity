package com.daniel.security.core.config;

import com.daniel.security.core.properties.SecurityProperties;
import com.daniel.security.core.validate.code.ImageCodeGenerator;
import com.daniel.security.core.validate.code.ValidateCodeGenerator;
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
    @ConditionalOnMissingBean(name = "imageCodeGenerator")      //当spring容器中不存在 name = "imageCodeGenerator" 的bean时才执行此处的初始化; 否则不初始化并使用已有的bean
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }
}
