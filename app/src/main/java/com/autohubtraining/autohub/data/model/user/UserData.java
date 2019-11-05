package com.autohubtraining.autohub.data.model.user;

import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable {
    private String userId;
    private int type;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;
    private String bio = "";
    private List<String> userInterests;
    private List<String> bestImages;
    private UserCameraResponse userCamera;
    private ArrayList<UserPlan> alUserPlans;

    private boolean isFavourite=false;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        if (firstName == null) {
            return "";
        }

        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        if (lastName == null) {
            return "";
        }

        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        if (avatarUrl == null) {
            return "";
        }

        return avatarUrl;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        if (bio == null) {
            return "";
        }
        return bio;
    }

    public void setUserInterests(List<String> userInterests) {
        this.userInterests = userInterests;
    }

    public List<String> getUserInterests() {
        return userInterests;
    }

    public void setBestImages(List<String> bestImages) {
        this.bestImages = bestImages;
    }

    public List<String> getBestImages() {
        return bestImages;
    }

    public void setUserCamera(UserCameraResponse userCamera) {
        this.userCamera = userCamera;
    }

    public UserCameraResponse getUserCamera() {
        return userCamera;
    }

    public void setAlUserPlans(ArrayList<UserPlan> alUserPlans) {
        this.alUserPlans = alUserPlans;
    }

    public ArrayList<UserPlan> getAlUserPlans() {
        return alUserPlans;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
