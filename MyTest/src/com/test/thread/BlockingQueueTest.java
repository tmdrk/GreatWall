package com.test.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueTest {
	/**
	 * 1.BlockingQueue定义的常用方法如下: 
        1)add(anObject):把anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则报异常 
        2)offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false. 
        3)put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续. 
        4)poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null 
        5)take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到Blocking有新的对象被加入为止 
       2.BlockingQueue有四个具体的实现类,根据不同需求,选择不同的实现类 
        1)ArrayBlockingQueue:规定大小的BlockingQueue,其构造函数必须带一个int参数来指明其大小.其所含的对象是以FIFO(先入先出)顺序排序的. 
        2)LinkedBlockingQueue:大小不定的BlockingQueue,若其构造函数带一个规定大小的参数,生成的BlockingQueue有大小限制,若不带大小参数,所生成的BlockingQueue的大小由Integer.MAX_VALUE来决定.其所含的对象是以FIFO(先入先出)顺序排序的 
        3)PriorityBlockingQueue:类似于LinkedBlockQueue,但其所含对象的排序不是FIFO,而是依据对象的自然排序顺序或者是构造函数的Comparator决定的顺序. 
        4)SynchronousQueue:特殊的BlockingQueue,对其的操作必须是放和取交替完成的. 
       3.LinkedBlockingQueue和ArrayBlockingQueue比较起来,它们背后所用的数据结构不一样,导致LinkedBlockingQueue的数据吞吐量要大于ArrayBlockingQueue,但在线程数量很大时其性能的可预见性低于ArrayBlockingQueue.
	 * @param args
	 * @throws InterruptedException
	 * @author zhoujie
	 * @date 2017年6月2日 下午2:43:14
	 */
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(10);
//		BlockingQueue<String> msgQueue = new PriorityBlockingQueue<String>(10,new Comparator<String>() {
//			@Override
//			public int compare(String o1, String o2) {
//				return Integer.parseInt(o1)-Integer.parseInt(o2);
//			}
//		}); //双缓冲队列
		ExecutorService executor = Executors.newFixedThreadPool(10);
		Consumer Consumer = new Consumer(msgQueue);
		executor.execute(Consumer);
		for(int i=0;i<20;i++){
			System.out.println(i);
			msgQueue.put(i+"");
		}
		executor.shutdown();
		
		AtomicInteger ai = new AtomicInteger();
		ai.set(4);
		System.out.println(ai.get());
		System.out.println(ai.getAndDecrement());
		System.out.println(ai.addAndGet(3));
	}
	static class Consumer implements Runnable{
		BlockingQueue<String> msgQueue;
		
		public Consumer(BlockingQueue<String> msgQueue){
			this.msgQueue = msgQueue;
		}
		@Override
		public void run() {
			while(true){
				doWork(msgQueue);
			}
		}
		public void doWork(BlockingQueue<String> msgQueue){
			try {
				Thread.sleep(1000);
				System.out.println("消费"+msgQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
