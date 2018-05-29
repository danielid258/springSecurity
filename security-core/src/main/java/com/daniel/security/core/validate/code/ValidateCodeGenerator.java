package com.daniel.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Daniel on 2018/5/28.
 */
public interface ValidateCodeGenerator {

    /**
     * generate image validate code
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
