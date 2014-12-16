/**
 * 
 */
package com.tca.utils;

import android.content.Context;
import android.content.Intent;

import com.tca.model.Profile;
import com.tca.ui.DashboardActivity1;

/**
 * @author rahumani
 *
 */
public class LoginUtil {
    private static LoginUtil instance;
    
    private Profile profile;
    private String sessionInfo;
    private Context context;
    
    public static LoginUtil getInstance() {
        if (instance == null) {
            instance = new LoginUtil();
        }
        return instance;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(String sessionInfo) {
        this.sessionInfo = sessionInfo;
    }
    
    public void onLoginSuccess() {
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClass(context, DashboardActivity1.class);
        context.startActivity(i);
    }
    
    public void login(Profile profile, Context context) {
        this.context = context;
        this.profile = profile;
        onLoginSuccess();
    }
}
