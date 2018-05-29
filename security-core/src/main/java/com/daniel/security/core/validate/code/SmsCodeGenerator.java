package com.daniel.security.core.validate.code;

import com.daniel.security.core.properties.SecurityProperties;
import com.daniel.security.core.util.VerifyCodeUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Daniel on 2018/5/29.
 *
 * sms verification code generator
 */
@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(HttpServletRequest request) {

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
