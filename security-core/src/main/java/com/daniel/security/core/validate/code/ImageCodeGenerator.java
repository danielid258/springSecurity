package com.daniel.security.core.validate.code;

import com.daniel.security.core.properties.SecurityProperties;
import com.daniel.security.core.util.VerifyCodeUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import java.awt.image.BufferedImage;

/**
 * Daniel on 2018/5/29.
 *
 * image verification code generator
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ImageCode generate(HttpServletRequest request) {
        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImage().getWidth());

        int height = ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImage().getHeight());

        String verifyCode = VerifyCodeUtil.generateVerifyCode(securityProperties.getCode().getImage().getLength());

        BufferedImage image = VerifyCodeUtil.createImage(width, height, verifyCode);

        return new ImageCode(image, verifyCode, securityProperties.getCode().getImage().getExpireInt());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
