package com.test.java8.thread;

public class MyThread {
	public static void main(String[] args) {
		Thread threadLam = new Thread(()->{
			System.out.println("use Lambda method:");
		});
		threadLam.start();
	}
}
