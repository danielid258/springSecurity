package com.daniel.security.core.validate.code.sms;

/**
 * on 5/29/2018.
 *
 * 短信发送接口 具体的短信发送服务实现SmsCodeSender即可
 */
public interface SmsCodeSender {

    /**
     * 给指定的手机号码发送短信验证码
     *
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
