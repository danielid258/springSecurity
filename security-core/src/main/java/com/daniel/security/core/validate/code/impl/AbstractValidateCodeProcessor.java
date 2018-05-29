package com.daniel.security.core.validate.code.impl;

import com.daniel.security.core.validate.code.ValidateCodeGenerator;
import com.daniel.security.core.validate.code.ValidateCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * Daniel on 2018/5/29.
 */
public abstract class AbstractValidateCodeProcessor<C> implements ValidateCodeProcessor{
    /**
     * Session 操作工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集spring容器中所有ValidateCodeGenerator实现类的bean,key:bean的name value:bean对象
     *
     * 根据不同的验证码类型调用不同的{@link validateCodeGenerators}
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 保存验证码到Session中
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request,C validateCode) {
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(), validateCode);
    }

    /**
     * 发送验证码,具体发送逻辑由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getProcessorType(request);

        //spring开发技巧:依赖搜索,从同一类型的多个实现类中搜索出对应的bean
        //从验证码生成器bean的集合中取出对应name 的生成器
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");

        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 根据请求的URL获取验证码类型
     *
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }
}
