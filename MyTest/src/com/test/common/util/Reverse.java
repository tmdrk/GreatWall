package com.test.common.util;

import java.util.Random;

public class Reverse {
	public static void main(String[] args) {
		String a = "hello !";
		char[] cs = a.toCharArray();
		for(int i=cs.length-1;i>=0;i--){
			System.out.print(cs[i]);
		}
		Random r = new Random();
		for(int i=0;i<10;i++){
			System.out.println(r.nextInt(1));
		}
	}
}
