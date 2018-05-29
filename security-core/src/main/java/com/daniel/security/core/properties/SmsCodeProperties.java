package com.daniel.security.core.properties;

/**
 * on 5/28/2018.
 *
 */
public class SmsCodeProperties {
    private int length = 6;
    private int expireInt = 60;
    private String url;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireInt() {
        return expireInt;
    }

    public void setExpireInt(int expireInt) {
        this.expireInt = expireInt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
