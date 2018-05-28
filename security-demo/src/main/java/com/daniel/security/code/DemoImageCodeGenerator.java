package com.daniel.security.code;

import com.daniel.security.core.validate.code.ImageCode;
import com.daniel.security.core.validate.code.ValidateCodeGenerator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 Daniel on 2018/5/29.
 *
 * 客户端自定义的图片验证码生成器
 *
 */
//@Component("imageCodeGenerator")    //此处强制需要名称=imageCodeGenerator,否则不能覆盖系统默认提供的图片验证码生成器
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(HttpServletRequest request) {
        System.out.println("more advance image code generator");
        return null;
    }
}
