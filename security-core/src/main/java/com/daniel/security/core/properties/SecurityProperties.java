package com.daniel.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Daniel on 2018/5/27.
 */
@ConfigurationProperties(prefix = "daniel.security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
