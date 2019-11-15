package com.autohubtraining.autohub.data.model;

import com.autohubtraining.autohub.data.model.public_data.user_plan.UserPlan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
	private String userId;
	private int type;
	private String firstName;
	private String lastName;
	private String email;
	private String avatarUrl;
	private String bio;
	private List<String> userInterests;
	private List<String> bestImages;
	private String cameraBrand;
	private String cameraModel;
	private List<String> cameraAccessories;
	private ArrayList<UserPlan> arrayPlan;
	private List<String> followings;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

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

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setBio(String bio){
		this.bio = bio;
	}

	public String getBio(){
		return bio;
	}

	public void setUserInterests(List<String> userInterests){
		this.userInterests = userInterests;
	}

	public List<String> getUserInterests(){
		return userInterests;
	}

	public void setBestImages(List<String> bestImages){
		this.bestImages = bestImages;
	}

	public List<String> getBestImages(){
		return bestImages;
	}

	public void setCameraBrand(String cameraBrand) {
		this.cameraBrand = cameraBrand;
	}

	public String getCameraBrand() {
		return cameraBrand;
	}

	public void setCameraModel(String cameraModel) {
		this.cameraModel = cameraModel;
	}

	public String getCameraModel() {
		return cameraModel;
	}

	public void setCameraAccessories(List<String> cameraAccessories){
		this.cameraAccessories = cameraAccessories;
	}

	public List<String> getCameraAccessories(){
		return cameraAccessories;
	}

	public void setArrayPlan(ArrayList<UserPlan> arrayPlan) {
		this.arrayPlan = arrayPlan;
	}

	public ArrayList<UserPlan> getArrayPlan() {
		return arrayPlan;
	}

	public void setFollowings(List<String> followings) {
		this.followings = followings;
	}

	public List<String> getFollowings() {
		return followings;
	}
}