package practice.blockingQueueDemo;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//Using blocking queue prevents us from using synchronized keywords with java.util.concurrent package
public class BlockingQueueDemo {
	
	private static BlockingQueue queue = new ArrayBlockingQueue(10);
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				try{
					producer();
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run(){
				try{
					consumer();
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}
	
	private static void producer() throws InterruptedException{
		//Random Generator to produce random data
		Random random = new Random();
		
		while(true){
			queue.put(random.nextInt(100));
		}
	}
	
	private static void consumer() throws InterruptedException{
		//Used to consume items randomly
		Random random = new Random();  
		
		while(true){
			Thread.sleep(100);
			
			//Happens just 1/10 times in average
			if(random.nextInt(10)==0){
				Integer val = (Integer) queue.take();
				System.out.println("Taken value: " + val + "; Queue size is: " + queue.size());
			}
		}
	}

}
 