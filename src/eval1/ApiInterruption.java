package eval1;

public class ApiInterruption extends Thread {
	
	public void run() {
		while(!interrupted()) {
			System.out.println("Running");
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("Thread interrupted while sleep");
			}
			interrupt();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ApiInterruption ai = new ApiInterruption();
		ai.start();
		Thread.sleep(2000);
		ai.interrupt();
		ai.join();
		System.out.println("thread finished");
	}

}
