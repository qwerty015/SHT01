package com.autohubtraining.autohub.data;

import android.util.Log;

import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;

public class DataHandler {
    private static DataHandler dataHandler;
    private static User user;
    public static UserData userData;
    private int userType;

    private DataHandler() {

    }

    public static DataHandler getInstance() {
        if (dataHandler == null)
            dataHandler = new DataHandler();
        return dataHandler;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setUser(User adminUser) {
        this.user = adminUser;
    }

    public User getUser() {
        return user;
    }

    public void setUserData(UserData adminUser) {
        this.userData = adminUser;
    }

    public UserData getUserData() {
        return userData;
    }

}
