package com.test.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	ConcurrentHashMap chm = new ConcurrentHashMap();
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		Semaphore semp = new Semaphore(5);
		for(int i=0;i<20;i++){
			Runner runner = new Runner(semp,"线程"+i);
			executor.execute(runner);
		}
		executor.shutdown();
	}
	static class Runner implements Runnable {  
	    private Semaphore semp;  
	  
	    private String name;  
	  
	    public Runner(Semaphore semp, String name) {  
	        super();  
	        this.semp = semp;
	        this.name = name;  
	    }  
	  
	    @Override  
	    public void run() {  
	        try {  
	        	semp.acquire();
//	            Thread.sleep(1000 * (new Random()).nextInt(4));  
	            System.out.println(Thread.currentThread()+name + " 进来了...");  
	            Thread.sleep(1000 * 2);
	            semp.release();
	            System.out.println(name + " 起跑！");  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }
	    }  
	}
}
