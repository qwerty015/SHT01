package com.autohubtraining.autohub.data.model.user;

import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;
import com.autohubtraining.autohub.data.model.user_cameras.UserCameraResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable {
    private int type;
    private String firstName;
    private String lastName;
    private String email;




    boolean isFavourite=false;
    private UserCameraResponse userCamera;
    private ArrayList<UserPlan> alUserPlans;



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





    public UserCameraResponse getUserCamera() {
        return userCamera;
    }

    public void setUserCamera(UserCameraResponse userCamera) {
        this.userCamera = userCamera;
    }

    public ArrayList<UserPlan> getAlUserPlans() {
        return alUserPlans;
    }

    public void setAlUserPlans(ArrayList<UserPlan> alUserPlans) {
        this.alUserPlans = alUserPlans;
    }




    private String countryCode;


    private String pictureUrl;


    private String bio = "";


    private List<String> bestImages;

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    private List<String> userInterests;





    private String userId;


    private String phoneNo;


    private String signupStatus;



    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        if (pictureUrl != null)
            return pictureUrl;
        else
            return "";
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

    public void setBestImages(List<String> bestImages) {
        this.bestImages = bestImages;
    }

    public List<String> getBestImages() {
        return bestImages;
    }

    public void setUserInterests(List<String> userInterests) {
        this.userInterests = userInterests;
    }

    public List<String> getUserInterests() {
        return userInterests;
    }



    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setSignupStatus(String signupStatus) {
        this.signupStatus = signupStatus;
    }

    public String getSignupStatus() {
        return signupStatus;
    }
}
