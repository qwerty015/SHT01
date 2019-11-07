package com.autohubtraining.autohub.data;

import com.autohubtraining.autohub.data.model.User;
import com.autohubtraining.autohub.data.model.user.UserData;

public class DataHandler {
    private static DataHandler dataHandler;

    private static User user;
    private static UserData userData;

    private DataHandler() { }

    public static DataHandler getInstance() {
        if (dataHandler == null)
            dataHandler = new DataHandler();
        return dataHandler;
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
