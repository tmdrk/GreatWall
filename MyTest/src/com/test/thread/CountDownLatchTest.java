package com.test.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {
	final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	public static void main(String[] args) throws InterruptedException {
		ExecutorService  es = Executors.newFixedThreadPool(3);
		CountDownLatch latch=new CountDownLatch(2);//两个工人的协作  
		Worker worker1=new Worker("zhang san", 4000, latch);  
	    Worker worker2=new Worker("li si", 4000, latch); 
	    Boss subject=new Boss("dog king", 2000, latch); 
	    es.execute(worker1);
	    es.execute(worker2);
	    es.execute(subject);
        latch.await();//等待所有工人完成工作   
        System.out.println("all work done at "+sdf.format(new Date()));  
	}
	
    static class Worker implements Runnable{  
        String workerName;
        int workTime;
        CountDownLatch latch;
        public Worker(String workerName ,int workTime ,CountDownLatch latch){
             this.workerName=workerName;
             this.workTime=workTime;
             this.latch=latch;
        }
        public void run(){
            System.out.println("Worker "+workerName+" do work begin at "+sdf.format(new Date()));
            doWork();//工作了
            System.out.println("Worker "+workerName+" do work complete at "+sdf.format(new Date()));
            latch.countDown();//工人完成工作，计数器减一
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println(workerName+"工人继续干活");
        }  
          
        private void doWork(){  
            try {  
                Thread.sleep(workTime);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    } 
    
    static class Boss implements Runnable{  
        String workerName;   
        int workTime;  
        CountDownLatch latch;  
        public Boss(String workerName ,int workTime ,CountDownLatch latch){  
             this.workerName=workerName;  
             this.workTime=workTime;  
             this.latch=latch;  
        }  
        public void run(){  
        	try {
        		System.out.println("等待所有工人完成工作 ...");
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            System.out.println("Boss "+workerName+" do work begin at "+sdf.format(new Date()));  
            doWork();//工作了  
            System.out.println("Boss "+workerName+" do work complete at "+sdf.format(new Date()));  
  
        }  
          
        private void doWork(){  
            try {  
                Thread.sleep(workTime);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
    } 
}
