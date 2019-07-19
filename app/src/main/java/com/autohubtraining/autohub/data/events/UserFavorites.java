package com.autohubtraining.autohub.data.events;

public class UserFavorites {
    public UserFavorites(String userId, String isFavorite) {
        this.isFavorite = isFavorite;
        this.userId = userId;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String isFavorite;
    String userId;

}
