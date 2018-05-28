package com.daniel.security.core.properties;

/**
 * on 5/28/2018.
 *
 */
public class ImageCodeProperties {
    private int width = 100;
    private int height = 40;
    private int length = 4;
    private int expireInt = 60;

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
}
