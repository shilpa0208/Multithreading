package practice.lowLevelConstructDemo;

import java.util.LinkedList;
import java.util.Random;

public class LowLevelConstructsDemo {
	
	//Like a dataStore
	private LinkedList<Integer> list = new LinkedList<Integer>();
	private final int LIMIT = 10;
	//explicit locking used
	private Object lock = new Object();
	
	public void produce() throws InterruptedException{
		int value = 0;
		
		while(true){
			
			synchronized(lock){
				
				while(list.size()==LIMIT){
					//need to call wait on the object used
					lock.wait();
				}
				list.add(value++);
				//wakes up the other thread
				lock.notify();
			}
			
		}
	}
	
	public void consume() throws InterruptedException{
		
		Random random = new Random();
		
		while(true){
			
			synchronized(lock){
				
				while(list.size()==0){
					lock.wait();
				}
				System.out.print("List size is: "+ list.size());
				//Creating a FIFO list structure 
				int value = list.removeFirst();
				System.out.println("; value is: "+ value);
				lock.notify();
			}
			Thread.sleep(random.nextInt(3000));
		}
	}
	
	public static void main(String[] args) {
		
		LowLevelConstructsDemo lobj = new LowLevelConstructsDemo();
		
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				try {
					lobj.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run(){
				try{
					lobj.consume();
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
	}
}
