package com.daniel.security.core.social.qq.connect;

import com.daniel.security.core.social.qq.api.QQ;
import com.daniel.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * on 6/4/2018.
 */
public class QQAdapter implements ApiAdapter<QQ>{
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    /**
     * adapte QQServiceProvider user to spring social standard connection data
     *
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
