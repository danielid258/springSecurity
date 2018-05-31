package com.daniel.security.core.filter;

import com.daniel.security.core.exception.ValidateCodeException;
import com.daniel.security.core.properties.SecurityConstants;
import com.daniel.security.core.properties.SecurityProperties;
import com.daniel.security.core.validate.code.ValidateCodeProcessorHolder;
import com.daniel.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * OncePerRequestFilter 一个请求只拦截一次 进入时拦截离开时不拦截
 *
 * InitializingBean spring容器初始化完毕后执行afterPropertiesSet方法
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证码处理器holder
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 所有需要验证码校验的url 及验证码的类型
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * 判断URL是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 需要验证码的校验URL 根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("validate the verification code in :(" + request.getRequestURI() + "),and type is:　" + type);
            try {
                //从validateCodeProcessorHolder中 通过依赖查找 取出对应类型的processor执行校验逻辑
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                logger.info("validate verification code　success");
            } catch (ValidateCodeException exception) {
                logger.info("validate verification code　failure");
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);

                //校验失败 终止调用后续的过滤器
                return;
            }
        }

        chain.doFilter(request, response);

    }

    /**
     * 获取校验码的类型,如果当前请求不需要校验,则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
