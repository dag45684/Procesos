package eval1;

public class SyncExample {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(Main::run);
		Thread t2 = new Thread(Main::run);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(Cnt.get());
	}
}

class Main{
	private static Cnt cnt = new Cnt(100);

	public static void run() {
		for(int i =0; i<100; i++) {
			cnt.inc();
		}
		try {
			Thread.sleep(10);
		}catch (Exception e) {}
	}
}

class Cnt {
	private static int n;
	
	public Cnt (int n) {
		this.n = n;
	}
	
	public synchronized void inc() {
//		synchronized (this) {
			n++;
//		}
	}
	public static int get() {
		return n;
	}
}
