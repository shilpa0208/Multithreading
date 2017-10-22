package practice.callableAndFutureDemo;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Allows to get returnable from threads and throw exceptions
public class CallableAndFutureDemo {

	public static void main(String[] args) {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		//Returns an integer value with future 
		Future<Integer> future = exec.submit(new Callable<Integer>(){

			public Integer call() throws Exception {
				Random rand = new Random();
				int duration = rand.nextInt(4000);
				
				if(duration>2000){
					//Throw an execution of our own and check how it is thrown
					throw new IOException("Sleeping for too long!");
				}
				
				System.out.println("Starting...");
				
				try{
					Thread.sleep(duration);
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
				
				System.out.println("Finished!");
				
				return duration;
			}
			
		});
		
		exec.shutdown();
		
		try {
			//used to obtain the result from the callable value using future
			System.out.println("Future result: " + future.get());
		} catch (InterruptedException e){
			e.printStackTrace();
		}catch(ExecutionException e){
			IOException ex =  (IOException) e.getCause();
			System.out.println(ex.getMessage());
			//System.out.println(ex);
			//System.out.println(ex.getMessage());
		}
	}
	
	
}
