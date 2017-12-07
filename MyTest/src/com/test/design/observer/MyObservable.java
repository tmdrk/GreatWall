package com.test.design.observer;

import java.util.Observable;
import java.util.Observer;

public class MyObservable extends Observable{
	public void setHomework(){
		System.out.println("被观察者修改！");
		setChanged();
		notifyObservers("观察者看到了！");
	}
	public static void main(String[] args) {
		MyObservable o = new MyObservable();
		Observer ose1 = new MyObserver("李友", o);
		Observer ose2 = new MyObserver("王帅", o);
		Observer ose3 = new MyObserver("胡杨波", o);
		o.setHomework();
	}
}
