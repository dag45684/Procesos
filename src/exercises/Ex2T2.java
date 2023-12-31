package exercises;

public class Ex2T2 {

	public static void main(String[] args) {
		
		Runnable task = new Task();
		for(int i=0; i<5; i++) {
			//Normal method
			Thread t = new Thread(task, "Thread " + i);
			if(i%2==0) {
				t.setPriority(i+i+1);
			}else {
				t.setPriority(i);
			}
			t.start();
			//Lambda
//			new Thread(() -> {
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {}
//			}, "lulz").start();
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
