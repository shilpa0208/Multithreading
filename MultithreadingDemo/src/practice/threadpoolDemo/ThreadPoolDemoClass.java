package practice.threadpoolDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable{
	
	private int id;
	
	public Processor(int id){
		this.id=id;
	}
	
	public void run() {
		System.out.println("Starting: " + id);
		
		//Some processing done 
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
		}
		System.out.println("Completed: " + id);
	}

}

public class ThreadPoolDemoClass{
	
	public static void main(String[] args) {
		//Thread pool has 2 workers in the factory here. We can avoid thread creation overhead and recycle the threads 
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		for(int i=0;i<5;i++){
			executor.submit(new Processor(i));
		}
		//Terminates the thread pool once all the threads finish executing
		executor.shutdown();
		
		System.out.println("All tasks submitted");
		
		try {
			//We can terminate after a given duration of time
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("All tasks completed");
	}
	
}
