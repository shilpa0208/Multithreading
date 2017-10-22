package practice.deadlockDemo;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*Solution:
 * 1. Always lock the locks in teh same order in all threads.
 * 2. Use tryLock() to check if the lock is acquired or not in a loop till both are acuired as shown below */
public class DeadlockDemo {
	
	//We can solve deadlocks using tryLock() method of reentrant lock.
	private Account acc1 = new Account();
	private Account acc2 = new Account();
	
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	
	private void acquireLock(Lock firstLock, Lock secondLock) throws InterruptedException{
		//we loop till bot locks are acquired
		while(true){
			//AcquireLocks
			boolean gotFirstLock = false;
			boolean gotSecondLock = false;
			
			try{
				//tryLock() returns true if it has got the lock else it returns false
				gotFirstLock = firstLock.tryLock();
				gotSecondLock = secondLock.tryLock();
			}finally{
				if(gotFirstLock && gotSecondLock){
					return;
				}
				if(gotFirstLock){
					firstLock.unlock();
				}
				if(gotSecondLock){
					secondLock.unlock();
				}
			}
			//Locks not acquired
			Thread.sleep(1);
		}
	}
	
	public void firstThread() throws InterruptedException{
		
		Random random = new Random();
		
		for(int i=0;i<10000;i++){
			
			acquireLock(lock1, lock2);
			/*Use above to prevent deadlock
			 * lock1.lock();
			   lock2.lock();*/
			
			try{
				//We do not always know from which account we  are transferring to which account 
				Account.transfer(acc1, acc2, random.nextInt(100));
			}finally{
				lock1.unlock();
				lock2.unlock();
			}
		}
	}
	
	public void secondThread() throws InterruptedException{
		
		Random random = new Random();
		
		for(int i=0;i<10000;i++){
			
			acquireLock(lock2, lock1);
			//Deadlock condition when locks are obtained in different order to thread 1
			/*lock2.lock();
			  lock1.lock();*/
			
			try{
				Account.transfer(acc2, acc1, random.nextInt(100));
			}finally{
				lock2.unlock();
				lock1.unlock();
			}
		}
	}
	
	public void finished(){
		System.out.println("Account 1 balance: " + acc1.getBalance());
		System.out.println("Account 2 balance: " + acc2.getBalance());
		System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
	}
	
	public static void main(String[] args) throws Exception {
		
		DeadlockDemo dObj = new DeadlockDemo();
		
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				try{
					dObj.firstThread();
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run(){
				try{
					dObj.secondThread();
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		dObj.finished();
	}

}
