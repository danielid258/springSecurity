package com.daniel.security.core.validate.filter;

import com.daniel.security.core.validate.exception.ValidateCodeException;
import com.daniel.security.core.validate.properties.SecurityProperties;
import com.daniel.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * on 5/28/2018.
 * 验证码校验Filter 需要验证码校验的URL可配置
 *
 * OncePerRequestFilter类型的Filter只会被调用一次
 * InitializingBean 其他properties数据都初始化完毕后执行afterPropertiesSet方法中的特定操作
 */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AuthenticationFailureHandler failureHandler;

    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        boolean action = false;
        //验证当前请求的URL 是否匹配配置文件中配置的需要验证码校验的URL
        for (String url : urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                action = true;
                break;
            }
        }
        //如果匹配上任意需要验证码校验的URL 校验验证码
        if (action) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                //捕获到验证码异常 交由认证失败处理器AuthenticationFailureHandler处理
                failureHandler.onAuthenticationFailure(request, response, e);

                //验证码校验失败 终止后续Filter的调用
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * 当配置文件初始化完成后
     *
     * 把配置中需要验证码校验的url添加到set全局变量中
     *
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urls.add("/authenticate/form");
        String[] configUrls = StringUtils.split(securityProperties.getCode().getImage().getUrl(), ",");
        if (configUrls == null)
            return;

        for (String configUrl : configUrls) {
            urls.add(configUrl);
        }
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

        //verify succed, remove code
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

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
