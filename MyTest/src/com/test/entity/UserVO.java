package com.test.entity;

import java.io.Serializable;

public class UserVO implements Serializable{
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = -550495895623955294L;
	
	private String userType1;
	private String sex;

	public String getUserType1() {
		return userType1;
	}
	public void setUserType1(String userType1) {
		this.userType1 = userType1;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	} 
	
}
