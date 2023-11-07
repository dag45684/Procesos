package exercises;

public class Ex4T2 {

	public static void main(String[] args) throws InterruptedException {
		
		Thread t = new Thread("Countdown") {
			public void run() {
				for(int i = 5; i >= 0; i--) {
					System.out.println(i);
					try {
						Thread.sleep(i);
					}catch (Exception e) {}
				}
				System.out.println("End of thread");
			}
		};
		t.start();
		t.join();
		System.out.println("End of main.");
		
		

	}

}
