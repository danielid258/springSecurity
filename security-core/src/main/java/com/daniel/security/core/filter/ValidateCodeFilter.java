package com.daniel.security.core.filter;

import com.daniel.security.core.exception.ValidateCodeException;
import com.daniel.security.core.validate.code.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * on 5/28/2018.
 * <p>
 * OncePerRequestFilter类型的Filter只会被调用一次
 */
public class ValidateCodeFilter extends OncePerRequestFilter {
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //只对登录请求做验证码校验
        if (StringUtils.equals("/authenticate/form", request.getRequestURI()) && StringUtils.equalsIgnoreCase("POST", request.getMethod()))
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                //捕获到验证码异常 交由认证失败处理器AuthenticationFailureHandler处理
                failureHandler.onAuthenticationFailure(request, response, e);

                //验证码校验失败 终止后续Filter的调用
                return;
            }

        chain.doFilter(request, response);
    }

    /**
     * 校验验证码
     *
     * @param request
     * @throws ValidateCodeException
     */
    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(request, SESSION_KEY);
        if (imageCode == null)
            throw new ValidateCodeException("verification code does not exist!");

        String requestCode = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(requestCode))
            throw new ValidateCodeException("verification code can not be empty!");

        if (imageCode.isExpired()) {
            sessionStrategy.removeAttribute(request, SESSION_KEY);
            throw new ValidateCodeException("verification code has expired!");
        }

        if (!StringUtils.equalsIgnoreCase(requestCode, imageCode.getCode()))
            throw new ValidateCodeException("verification code does not match!");

        //verify succed remove code
        sessionStrategy.removeAttribute(request, SESSION_KEY);
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public AuthenticationFailureHandler getFailureHandler() {
        return failureHandler;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }
}
