package eval1;

public class ThreadPractice2 {

	public static void main(String[] args) {
		
		Runnable task = new Task();
		for(int i=0; i<5; i++) {
			//Normal method
//			new Thread(task, "Thread " + i).start();
			//Lambda
			new Thread(() -> {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {}
			}, "lulz").start();
			//Also possible with reference to function
		}

	}

}

class Task implements Runnable{
	public void run() {
		for(int i=0; i<5; i++) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {}
			System.out.println(Thread.currentThread().getName() + " Mensaje " + i);
		}
	}
}
