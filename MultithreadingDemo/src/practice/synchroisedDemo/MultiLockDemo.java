package practice.synchroisedDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Two processes can run simultaneously by having synchronized blocks and two different objects locked.
public class MultiLockDemo {
	
	//A random generator to make it realistic
	private Random random = new Random();
	
	//Intrinsic locks are acquired when the method is synchronized which prevents other threads from acquiring it at the same time. Hence takes longer time for execution.
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	//shared data for multiple threads
	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();
	
	//Some kind of processing where we can break it down into two stages
	//Imagine some operation is being performed elsewhere or some calculation
	public void stageOne(){
		//Now two threads can run the method at the same time, but next thread has to wait till the synchronized code block is released. 
		//But different objects are locked hence. they work faster.
		synchronized(lock1){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}

	
	//Some calculation is performed in this stage
	public void stageTwo(){
		//Now two threads can run the method at the same time, but next thread has to wait till the synchronized code block is released. 
		//But different objects are locked hence. they work faster.
		synchronized(lock2){
			try{
				Thread.sleep(1);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
	}
	
	
	public void process(){
		for(int i=0;i<1000;i++){
			stageOne();
			stageTwo();
		}
	}
	
	public static void main(String[] args) {
		MultiLockDemo md = new MultiLockDemo();
		System.out.println("Starting.....");
		long start = System.currentTimeMillis();
		
		Thread t1 = new Thread(new Runnable(){
			public void run() {
				md.process();				
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run() {
				md.process();				
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//md.process();
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: "+ (end-start));
		System.out.println("List 1: "+ md.list1.size() + ", List 2: "+ md.list2.size());
		
		
	}

}
