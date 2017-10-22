package practice.waitAndNotifyDemo;
import java.util.Scanner;

public class WaitAndNotifyDemo {
	
	public void produce() throws InterruptedException{
		//Intrinsic lock of this class
		synchronized(this){
			System.out.println("Producer thread running...");
			//relinquishes lock
			wait();
			System.out.println("Resumed");
		}
	}
	
	public void consume() throws InterruptedException{
		
		Scanner sc = new Scanner(System.in);
		Thread.sleep(2000);
		//locked on same object as above
		synchronized(this){
			System.out.println("Waiting for return key.");
			sc.nextLine();
			System.out.println("Return key pressed");
			//Relinquishes lock after completing execution
			notify();
			
		}
	}
	
public static void main(String[] args) throws InterruptedException {
		
		WaitAndNotifyDemo obj = new WaitAndNotifyDemo();
	
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				try{
					obj.produce();
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run(){
				try{
					obj.consume();
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
	}
}
