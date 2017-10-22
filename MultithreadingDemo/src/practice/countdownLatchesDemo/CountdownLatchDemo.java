package practice.countdownLatchesDemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//prevents the usage of notify and synchronized 
public class CountdownLatchDemo {
	
	public static void main(String[] args) {
		
		CountDownLatch latch = new CountDownLatch(3);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i=0;i<3; i++){
			executor.submit(new Processor(latch));
		}
		
		try {
			//Waits for the current thread until the latch has become zero
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Completed");
	}

}

class Processor implements Runnable{
	
	private CountDownLatch latch;
	
	public Processor(CountDownLatch latch){
		this.latch = latch;
	}
	
	public void run(){
				
		try{
			System.out.println("Started.");
			Thread.sleep(3000);
		}catch(InterruptedException exp){
			exp.printStackTrace();
		}
		//Every time its called the value is reduced by one
		latch.countDown();
	}
}