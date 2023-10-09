package eval1;

public class ApiNonStaticInterruption implements Runnable {

	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			System.out.println("Running");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println("Interrumpted while sleeping");
				Thread.currentThread().interrupt();
			}
		}
	}

}
