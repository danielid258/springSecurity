package com.daniel.security.core.controller;

import com.daniel.security.core.properties.SecurityConstants;
import com.daniel.security.core.properties.SecurityProperties;
import com.daniel.security.core.validate.code.ValidateCode;
import com.daniel.security.core.validate.code.ValidateCodeGenerator;
import com.daniel.security.core.validate.code.ValidateCodeProcessorHolder;
import com.daniel.security.core.validate.code.image.ImageCode;
import com.daniel.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Daniel on 2018/5/29.
 */
@RestController
@RequestMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX)
public class ValidateCodeController {
    //@Autowired
    //Map<String, ValidateCodeProcessor> validateCodeProcessors;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 创建验证码
     *
     * 根据不同的验证码类型调用不同的{@link validateCodeProcessors}实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping("/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }




    //==================================================================
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;
    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;
    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * generate sms verification code
     *
     * @param request
     * @param response
     * @throws IOException
     */
    //@GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request,response));

        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);

        //call sms server api send verification code to user
        smsCodeSender.send(ServletRequestUtils.getRequiredStringParameter(request, "mobile"), smsCode.getCode());
    }

    /**
     * generate image verification code
     *
     * @param request
     * @param response
     * @throws IOException
     */
    //@GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request, response));

        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);

        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
