package com.test.design.observer;

import java.util.Observable;
import java.util.Observer;

public class MyObserver implements Observer{
	private String name; 
	public MyObserver(String name,Observable o){
		this.name = name;
		o.addObserver(this);
	}
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(name+"--"+arg);
	}

}
