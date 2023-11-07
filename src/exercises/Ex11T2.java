package exercises;

public class Ex11T2 {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(Process::run);
		Thread t2 = new Thread(Process::run);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println("Sync.:" + Cnt.getSyn());
		System.out.println("Non Sync.:" + Cnt.get());
	}
}

class Process{
	private static Cnt cnt = new Cnt(100);

	public static void run() {
		for(int i =0; i<100; i++) {
			cnt.incSyn();
			cnt.inc();
		}
		try {
			Thread.sleep(10);
		}catch (Exception e) {}
	}
}

class Cnt {
	private static int n;
	private static int m;
	
	public Cnt (int n) {
		this.n = n;
		this.m = n;
	}
	public void inc() {
		m++;
	}

	public synchronized void incSyn() {
//		synchronized (this) {
			n++;
//		}
	}
	public static int getSyn() {
		return n;
	}
	public static int get() {
		return m;
	}
}
