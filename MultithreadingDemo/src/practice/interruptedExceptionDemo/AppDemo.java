package practice.interruptedExceptionDemo;

import java.util.Random;

public class AppDemo {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Starting...");
		
		Thread t = new Thread(new Runnable(){
			public void run(){
				Random rand = new Random();
				//1E6 is scientific notation of 1 million
				for(int i=0;i<1E8;i++){
					if(Thread.currentThread().isInterrupted()){
						System.out.println("Interrupted!");
						break;
					}
					Math.sin(rand.nextDouble());
				}
			}
		});
		
		t.start();
		t.sleep(500);
		t.interrupt();
		t.join();
		
		System.out.println("Finished!");
	}
}
