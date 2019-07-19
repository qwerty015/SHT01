package com.autohubtraining.autohub.util;

import android.util.Log;

import com.autohubtraining.autohub.data.model.User;

import java.io.Serializable;

/**
 * Created by binod on 25/6/19.
 */

public class UserHelper implements Serializable {

    private static volatile UserHelper sSoleInstance;
    private static User user;

    public static UserHelper getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (UserHelper.class) {
                if (sSoleInstance == null) {
                    sSoleInstance = new UserHelper();
                }
            }
        }

        return sSoleInstance;
    }

//    public void setUser(User adminUser) {
//        Log.e("adminnnn" , adminUser.getFirstName());
//        this.user = adminUser;
//    }
//
//    public User getUser() {
//        return user;
//    }

}
