package com.autohubtraining.autohub.data;

public class DataHandler {
    private static DataHandler dataHandler;
    private int userType;
    private DataHandler(){

    }

    public static DataHandler getInstance(){
        if(dataHandler==null)
            dataHandler = new DataHandler();
        return dataHandler;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
