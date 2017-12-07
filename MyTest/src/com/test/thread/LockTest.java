package com.test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	public static void main(String[] args) {
		Conditions cons = new Conditions();
		MyThread1 m1 = new MyThread1(cons);
		MyThread2 m2 = new MyThread2(cons);
		m1.start();
		m2.start();
	}
}

class MyThread1 extends Thread{
	Conditions cons;
	public MyThread1(Conditions cons){
		this.cons = cons;
	}
	public void run(){
		cons.dubble();
	}
}
class MyThread2 extends Thread{
	Conditions cons;
	public MyThread2(Conditions cons){
		this.cons = cons;
	}
	public void run(){
		cons.single();
	}
}

class Conditions {
	private boolean bool = false;
	int a = 50;
	Lock lock = new ReentrantLock();
	Condition con = lock.newCondition();
	public void dubble(){
		lock.lock();
		try {
			while(a>0){
				while(bool){
					con.await();
				}
				System.out.println(Thread.currentThread().getName()+":dubble:"+(a--));
				bool = true;
				con.signal();
			}
		} catch (InterruptedException e) {
			lock.unlock();
		}
	}
	public void single(){
		lock.lock();
		try {
			while(a>0){
				while(!bool){
					con.await();
				}
				System.out.println(Thread.currentThread().getName()+":single:"+(a--));
				bool = false;
				con.signal();
			}
		} catch (InterruptedException e) {
			lock.unlock();
		}
	}
}