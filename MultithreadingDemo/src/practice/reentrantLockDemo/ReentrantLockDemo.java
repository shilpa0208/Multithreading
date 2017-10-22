package practice.reentrantLockDemo;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Important point is to unlock after signal is called because the other thread can't acquire the lock if the lock is not given up
//tryLock() of reentrant lock is the biggest ad. to test if the lock can be acquired or not
public class ReentrantLockDemo {
	
	private int count = 0;
	//Reentrant lock implements Lock interface - used instead of synchronized
	//Reentrant locks mean that we can lock it any number of times and then unlock the same amount of times 
	private Lock lock = new ReentrantLock();
	//used to demo notifyAll
	private Condition cond = lock.newCondition();
	
	private void increment(){
		for(int i=0;i<100000;i++){
			count++;
		}
	}
	
	public void firstThread() throws InterruptedException{
		//Only one thread can lock and unlock a given thread at a time
		//signal and await can be called only after getting the lock
		lock.lock();
		
		System.out.println("Waiting...");
		//hands over the lock to other thread and waits till signal is called by the other thread
		cond.await();
		System.out.println("Woken up");
		
		try{
			increment();
		}finally{
			lock.unlock();
		}
		
	}
	
	public void secondThread() throws InterruptedException{
		
		Thread.sleep(1000);
		lock.lock();
		
		System.out.println("Press the return key!");
		new Scanner(System.in).nextLine();
		System.out.println("Got return key!");
		
		cond.signal();
		
		try{
			increment();
		}finally{
			lock.unlock();
		}
		
	}
	
	public void finished(){
		System.out.println("Count is: "+count);
	}
	
	public static void main(String[] args) throws Exception {
		
		final ReentrantLockDemo rObj = new ReentrantLockDemo();
		
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				try{
					rObj.firstThread();
					
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run(){
				try{
					rObj.secondThread();
					
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		}); 
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		rObj.finished();
	}
}


