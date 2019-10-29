package com.autohubtraining.autohub.data.model;

import java.io.Serializable;
import java.util.List;



public class User implements Serializable{
	private int type;
	private String firstName;
	private String lastName;
	private String email;



	private String countryCode;
	private String pictureUrl;
	private String bio;
	private List<String> bestImages;
	private List<String> userInterests;

	private String userId;
	private String phoneNo;
	private String signupStatus;

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}




	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setPictureUrl(String pictureUrl){
		this.pictureUrl = pictureUrl;
	}

	public String getPictureUrl(){
		return pictureUrl;
	}

	public void setBio(String bio){
		this.bio = bio;
	}

	public String getBio(){
		return bio;
	}

	public void setBestImages(List<String> bestImages){
		this.bestImages = bestImages;
	}

	public List<String> getBestImages(){
		return bestImages;
	}

	public void setUserInterests(List<String> userInterests){
		this.userInterests = userInterests;
	}

	public List<String> getUserInterests(){
		return userInterests;
	}



	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
	}

	public String getPhoneNo(){
		return phoneNo;
	}

	public void setSignupStatus(String signupStatus){
		this.signupStatus = signupStatus;
	}

	public String getSignupStatus(){
		return signupStatus;
	}
}