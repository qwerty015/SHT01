package com.autohubtraining.autohub.data.model;

import java.io.Serializable;
import java.util.List;



public class User implements Serializable{


	private String firstName;


	private String lastName;


	private String countryCode;


	private String pictureUrl;


	private String bio;


	private List<String> bestImages;


	private List<String> userInterests;


	private int type;


	private String userId;


	private String phoneNo;


	private String signupStatus;

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

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
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

	@Override
 	public String toString(){
		return 
			"User{" + 
			"firstName = '" + firstName + '\'' + 
			",lastName = '" + lastName + '\'' + 
			",countryCode = '" + countryCode + '\'' + 
			",pictureUrl = '" + pictureUrl + '\'' + 
			",bio = '" + bio + '\'' + 
			",bestImages = '" + bestImages + '\'' + 
			",userInterests = '" + userInterests + '\'' + 
			",type = '" + type + '\'' + 
			",userId = '" + userId + '\'' + 
			",phoneNo = '" + phoneNo + '\'' + 
			",signupStatus = '" + signupStatus + '\'' + 
			"}";
		}
}