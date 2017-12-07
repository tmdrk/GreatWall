package com.test.entity;

import java.util.HashMap;

public class Test {
	public static void main(String[] args) {
		getMethod();
	}

	private static void getMethod() {
		new HashMap<String, Object>();
		double aa = 0;
		double TT = 3.1416;
		double e = 2.71828;
		double d = (1/Math.sqrt(2*TT))*Math.pow(e,-Math.pow(aa,2)/2);
		for(int i=-5;i<6;i++){
			System.out.println((1/Math.sqrt(2*TT))*Math.pow(e,-Math.pow(i,2)/2)*1000);
		}
		System.out.println(Math.pow(32, 36));
	}
}
