package com.daniel.security.core.controller;

import com.daniel.security.core.properties.SecurityProperties;
import com.daniel.security.core.util.VerifyCodeUtil;
import com.daniel.security.core.validate.code.ImageCode;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * on 5/28/2018.
 */
@RestController
public class ValidateCodeController {
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode(request);

        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);

        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    private ImageCode createImageCode(HttpServletRequest request) {
        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImage().getWidth());

        int height = ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImage().getHeight());

        String verifyCode = VerifyCodeUtil.generateVerifyCode(securityProperties.getCode().getImage().getLength());

        BufferedImage image = VerifyCodeUtil.createImage(width, height, verifyCode);

        return new ImageCode(image, verifyCode, securityProperties.getCode().getImage().getExpireInt());
    }

}
