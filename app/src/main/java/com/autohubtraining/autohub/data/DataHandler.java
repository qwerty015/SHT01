package com.autohubtraining.autohub.data;

import com.autohubtraining.autohub.data.model.User;

public class DataHandler {
    private static DataHandler dataHandler;

    private static User user;

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

}
