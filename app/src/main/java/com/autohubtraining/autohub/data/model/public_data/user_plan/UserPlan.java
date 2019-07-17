package com.autohubtraining.autohub.data.model.public_data.user_plan;

import java.io.Serializable;
import java.util.ArrayList;

public class UserPlan implements Serializable {


    private String amount;

    private String editingIncluded;

    private String numberOfPictures="";

    private String planName;

    private String shootType;

    private String planId;

    private ArrayList<UserPlan> alPlans;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "ClassPojo [amount = " + amount + ", editingIncluded = " + editingIncluded + ", numberOfPictures = " + numberOfPictures + ", planName = " + planName + ", shootType = " + shootType + ", planId = " + planId + "]";
    }

}
