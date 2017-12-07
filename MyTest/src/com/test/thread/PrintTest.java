package com.test.thread;

/**
 * 两个线程交替打印12345 ... 49 50
 * @ClassName: PrintTest 
 * @author zhoujie
 * @date 2017年11月1日 下午3:18:12
 */
public class PrintTest {
	static String lock1 = "";
	static String lock2 = "";
	static String lock3 = "";
	static int num = 50;
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				while(num>0){
					synchronized (lock3) {
						synchronized (lock1) {
						System.out.println(Thread.currentThread().getName()+":"+(num--));
						try {
							lock2.notifyAll();
							lock1.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						}
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while(num>0){
					synchronized (lock1) {
						synchronized (lock2) {
						System.out.println(Thread.currentThread().getName()+":"+(num--));
						try {
							lock3.notifyAll();
							lock2.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						}
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while(num>0){
					synchronized (lock2) {
						synchronized (lock3) {
						System.out.println(Thread.currentThread().getName()+":"+(num--));
						try {
							lock1.notifyAll();
							lock3.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						}
					}
				}
			}
		}).start();
	}
}

class Print{
	int num = 50;
	public synchronized void printSiggle(){
		System.out.println(num--);
	}
	public synchronized void printDubble(){
		System.out.println(num--);
	}
}
class SiggleThread extends Thread{
	Print p;
	public SiggleThread(Print p){
		this.p = p;
	}
	public void run() {
		while(p.num>0){
			p.printSiggle();
		}
	}
}
class DubbleThread extends Thread{
	Print p;
	public DubbleThread(Print p){
		this.p = p;
	}
	public void run() {
		while(p.num>0){
			p.printDubble();
		}
	}
}