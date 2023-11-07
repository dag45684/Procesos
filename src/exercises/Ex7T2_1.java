package exercises;

public class Ex7T2_1 extends Thread{

	//Volatile prevents from cache copying the variable for the Thread so modifying it out of it does affect the behavior of the flag for the thread
	private volatile boolean end = false;
	
	@Override
	public void run() {
		while(!end) {
			System.out.println("Running");
			try {
				Thread.sleep(100);
			} catch (Exception e) {}
		}
		System.out.println("Thread finished");
	}
	
	public void putToEnd() { end = true; }

	public static void main(String[] args) throws InterruptedException {
		Ex7T2_1 fi = new Ex7T2_1();
		fi.start();
		Thread.sleep(2000);
		fi.putToEnd();

	}

}

