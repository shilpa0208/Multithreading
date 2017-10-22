package practice.synchroisedDemo;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

//Demonstrated the usage of synchronized with intrinsic locks
//Synchronized is needed although we have join to ensure proper working when large tasks are present.
public class SynchronisedDemo {
	
	private int count = 0;
	
	public static void main(String[] args) {
		SynchronisedDemo demo = new SynchronisedDemo();
		demo.doWork();
	}
	
	//intrinsic lock is obtained by each thread
	public synchronized void increment(){
		count++;
	}
	
	public void doWork(){
		
		Thread t1 = new Thread(new Runnable(){
			
			public void run() {
				
				System.out.println("In thread 1");
				for(int i=0;i<10000;i++){
					increment();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			
			public void run(){
				
				System.out.println("In thread 2");
				for(int i=0;i<10000;i++){
					increment();
				}
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
		
		
		System.out.println("Count is: "+count);
	}
	
}
