package semaphores;

import java.util.concurrent.Semaphore;

public class Exercise15 {

	private static int PEOPLE = 20;
	private static int SEATS = 5;
	public final static Semaphore BENCH = new Semaphore(SEATS);
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread[] people = new Thread[PEOPLE];
		for(int i=1; i<PEOPLE; i++) {
			(people[i] = new Person(i)).start();	
		}
		for(int i=1; i<PEOPLE; i++) {
			people[i].join();
		}
	}
	
	public void sitDown() {
//		BENCH.acquire();
		System.out.println();
		
	}

}

class Person extends Thread{
	
	public Person(int n) {
		super("Person " + n);
	}
	
	public void run() {
//		Parque =
		System.out.println();
//		Thread.sleep(1000);
		System.out.println();
		
		
	}
	
}

class Park{
	
}
