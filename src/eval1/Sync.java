package eval1;

public class Sync {

	public static void main(String[] args) throws InterruptedException {
		
		Thread t = new Thread("countdown") {
			@Override
			public void run() {
				for (int i=5; i>=0; i--) {
					System.out.println(i);
					try {
						Thread.sleep(10000);
					}catch (Exception e) {}
				}
				System.out.println("Thread finished");
			}
		};
		t.start();
		t.join();
		System.out.println("Main finished");
	}
}
