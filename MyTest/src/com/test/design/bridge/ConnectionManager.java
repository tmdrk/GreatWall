package com.test.design.bridge;

public class ConnectionManager {
	static Driver driver;
	public static Connection getConnection(){
		return driver.getConnection();
	}
	public static void register(Driver dri){
		driver = dri;
	}
}
