package com.daniel.security.core.validate.code.sms;

/**
 * on 5/29/2018.
 *
 * 默认的短信发送服务
 */
public class DefaultSmsCodeSender implements SmsCodeSender{
    @Override
    public void send(String mobile, String code) {
        System.out.println("send " + code + " to " + mobile + " successful!");
    }
}
