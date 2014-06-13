package com.paypal.imagegrid.data;

import java.util.ArrayList;

public class InstagramData {
	
	private static InstagramData mInstagramData = null;
	
	public static InstagramData getInstance() {
		
		if(mInstagramData == null) {
			mInstagramData = new InstagramData();
		}
		return mInstagramData;
	}
	private String userId = null;
	private String accessToken = null;
	private String userName = null;
	private String token = null;
	private ArrayList<String> imageList = null;
	
	public ArrayList<String> getThumbnailList() {
		return imageList;
	}
	public void setThumbnailList(ArrayList<String> thumbnailList) {
		this.imageList = thumbnailList;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
