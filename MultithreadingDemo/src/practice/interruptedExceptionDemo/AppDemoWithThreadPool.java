package practice.interruptedExceptionDemo;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AppDemoWithThreadPool {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Starting...");
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		//Future<?> used when we do not have any return type set
		Future<?> future = exec.submit(new Callable<Void>(){
			public Void call()throws Exception{
				
				Random rand = new Random();
				
				for(int i=0;i<1E8;i++){
					if(Thread.currentThread().isInterrupted()){
						System.out.println("Interrupted!");
						break;
					}
					
					Math.sin(rand.nextDouble());
				
				}
				return null;
			}
		});
		
		exec.shutdown();
		Thread.sleep(500);
		
		//Kills all running threads, meaning sets interrupted flag
		exec.shutdownNow();
		
		//true sets the interrupted flag
		//future.cancel(true);
		
		exec.awaitTermination(1, TimeUnit.DAYS);
		System.out.println("Finished!");
	}
}
