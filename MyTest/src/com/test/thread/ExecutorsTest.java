package com.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsTest {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		ExecutorService executors = Executors.newSingleThreadExecutor();
		Thread t1 = new Thread(new Runnable() {  
			@Override  
			public void run() {  
				for(int i=0;i<100;i++){
					try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(i==50){
						System.out.println("t1 "+Thread.currentThread().getName()+" 循环次数："+i);
						throw new RuntimeException("失败了");
					}
					System.out.println("t1 "+Thread.currentThread().getName()+" 循环次数："+i);
				}
			}  
		});
		Thread t2 = new Thread(new Runnable() {  
			@Override  
			public void run() {  
				for(int i=0;i<100;i++){
					try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("t2 "+Thread.currentThread().getName()+" 循环次数："+i);
				}
			}  
		});
//		t1.start();
//		t2.start();
		executors.execute(t1);
		executors.execute(t2);
		System.out.println("------------------------");
		executors.shutdown();
	}
}
