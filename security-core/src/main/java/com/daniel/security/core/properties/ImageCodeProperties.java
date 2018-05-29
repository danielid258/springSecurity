package com.daniel.security.core.properties;

/**
 * on 5/28/2018.
 *
 */
public class ImageCodeProperties extends SmsCodeProperties{
    private int width = 100;
    private int height = 40;

    //重新构造图片验证码字符串的长度 覆盖短信验证码中的默认length=6
    public ImageCodeProperties() {
        setLength(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
