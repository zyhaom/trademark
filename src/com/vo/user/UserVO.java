package com.vo.user;

import java.io.Serializable;

public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userID = null ;//用户ID
	private String userPSW = null ;//登录秘密
	private String userName = null ;//用户名称

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPSW() {
		return userPSW;
	}

	public void setUserPSW(String userPSW) {
		this.userPSW = userPSW;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserVO() {
		
	}

	public UserVO(String userID, String userPSW, String userName) {
		super();
		this.userID = userID;
		this.userPSW = userPSW;
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserVO [userID=" + userID + ", userPSW=" + userPSW + ", userName=" + userName + "]";
	}

}
