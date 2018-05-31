package com.daniel.security.code;

import com.daniel.security.core.validate.code.ValidateCodeGenerator;
import com.daniel.security.core.validate.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 Daniel on 2018/5/29.
 *
 * 客户端自定义的图片验证码生成器
 *
 */
//@Component("imageValidateCodeGenerator")    //此处强制需要名称=imageCodeGenerator,否则不能覆盖系统默认提供的图片验证码生成器
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("more advance image code generator");
        return null;
    }
}
