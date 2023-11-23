package executors;

import java.util.concurrent.Semaphore;

public class Park {

	private final int totalppl;
	private final int maxseat;
	private final Semaphore BENCH;
	
	public Park(int totalppl, int maxseat) {
		this.totalppl = totalppl;
		this.maxseat = maxseat;
		BENCH = new Semaphore(maxseat);
	}
	
	public void start() throws InterruptedException {
		Thread[] ppl = new Thread[totalppl];
		for (int i=0; i<totalppl; i++) {
			(ppl[i] = new Person(i+1, this)).start();
		}
		for (int i=0; i<totalppl; i++) {
			ppl[i].join();
		} 
	}
	
	public boolean sitDown() {
		try {
			if(BENCH.tryAcquire()) {
				System.out.println(Thread.currentThread().getName() + " sits down.");
				Thread.sleep(500);
				return true;
			}else return false;
		}catch (Exception e) { return false; }
	}
	
	public void standUp() {
		BENCH.release();
		System.out.println(Thread.currentThread().getName() + " stands up.");
	}
	

	public static void main(String[] args) throws InterruptedException {
		Park park = new Park(20, 5);
		park.start();
	}

}
