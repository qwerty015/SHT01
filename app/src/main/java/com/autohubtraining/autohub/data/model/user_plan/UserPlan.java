package com.autohubtraining.autohub.data.model.user_plan;

import java.io.Serializable;

public class UserPlan implements Serializable {
    private String planName;
    private String shootType;
    private String price;
    private String editingIncluded;
    private String numberOfPictures;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getShootType() {
        return shootType;
    }

    public void setShootType(String shootType) {
        this.shootType = shootType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEditingIncluded() {
        return editingIncluded;
    }

    public void setEditingIncluded(String editingIncluded) {
        this.editingIncluded = editingIncluded;
    }

    public String getNumberOfPictures() {
        return numberOfPictures;
    }

    public void setNumberOfPictures(String numberOfPictures) {
        this.numberOfPictures = numberOfPictures;
    }
}
