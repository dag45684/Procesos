package exercises;

public class Ex7T2_2 extends Thread{

	//Volatile prevents from cache copying the variable for the Thread so modifying it out of it does affect the behavior of the flag for the thread
	static boolean end = false;
	
	@Override
	public void run() {
		while(!end);
		System.out.println("Thread finished");
	}
	
	public static void main(String[] args) throws InterruptedException {
		Ex7T2_2 fi = new Ex7T2_2();
		fi.start();
		Thread.sleep(2000);
		end = true;
		System.out.println("End of main.");

	}

}

