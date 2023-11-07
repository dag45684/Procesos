package exercises;

import java.util.Scanner;

public class Ex9T2 extends Thread {
	
	static int cnt=0;
	
	public void run() {
		while(!interrupted()) {
			cnt++;
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("Thread interrupted while sleep");
				interrupt();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Ex9T2 ai = new Ex9T2();
		ai.start();
		Scanner s = new Scanner(System.in);
		System.out.println("Write 'end' to stop.");
		String str = s.nextLine();
		while (!str.equals("end")) {
			str = s.nextLine();
		}
		ai.interrupt();
		ai.join();
		System.out.println(cnt);
	}

}
