package com.daniel.security.core.validate.code.sms;

import com.daniel.security.core.properties.SecurityProperties;
import com.daniel.security.core.util.VerifyCodeUtil;
import com.daniel.security.core.validate.code.ValidateCode;
import com.daniel.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Daniel on 2018/5/29.
 *
 * sms verification code generator
 */
@Component("smsCodeGenerator")      //同一个接口有多个实现类需要注入 需要指定name 获取时变量名称和此处的name匹配
public class SmsCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {

        String verifyCode = VerifyCodeUtil.generateVerifyCode(securityProperties.getCode().getSms().getLength());

        return new ValidateCode(verifyCode, securityProperties.getCode().getSms().getExpireInt());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
