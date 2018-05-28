package com.daniel.security.core.validate.code;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * Daniel on 2018/5/28.
 */
public interface ValidateCodeGenerator {

    /**
     * generate image validate code
     * @param request
     * @return
     */
    ImageCode generate(HttpServletRequest request);
}
