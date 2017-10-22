package practice.semaphoreDemo;

import java.util.concurrent.Semaphore;

public class Connection {
	//Singleton Class
	private static Connection instance = new Connection();
	
	//true in parameter means that the first thread that requires accepts the lock
	private Semaphore sem = new Semaphore(10, true);
	
	//Number of connection made at any given time
	private int connections = 0;
	
	private Connection(){};
	
	public static Connection getInstance(){
		return instance;
	}
	
	public void connect(){
		try{
			sem.acquire();
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
		
		try{
			//Any exception thrown in doConnect still ensures that the lock is released
			doConnect();
		}finally{
			sem.release();
		}
		
	}
	
	public void doConnect(){
		
		synchronized(this){
			//acquire connection, if execption is called in between then release may never be called , hence we have a doConnect method
			//sem.acquire();
			connections++;
			System.out.println("Current connections: " + connections);
		}
		
		//operation performed
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		synchronized(this){
			//release connection
			connections--;
		}
		//sem.release();
	}
}
