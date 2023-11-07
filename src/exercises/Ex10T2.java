package exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ex10T2 extends Thread{
	
	static boolean suspended = false;
	static boolean dead = false;
	
	public synchronized void setSuspension() {
		suspended = true;
	}
	public synchronized void kill() {
		dead = true;
	}
	public synchronized void delSuspension() {
		suspended = false;
		notify();
	}
	
	public void run() {
		while(true) {
			try {
				synchronized(this) {
					if(dead) break;
					if(suspended) {
						System.out.println("Suspended");
						wait();
					}
				}
				System.out.println("Running...");
				Thread.sleep(1000);
			}catch (Exception e) {}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Ex10T2 thread = new Ex10T2();
		thread.start();
		boolean alive = true;
		do {
			String opt = br.readLine();
			System.out.println("S --> Stop | R --> Restart | K --> Kill");
			switch(opt.toUpperCase()) {
			case "S":
				thread.setSuspension();
				break;
			case "R":
				thread.delSuspension();
				break;
			case "K":
				thread.kill();
				alive=false;
				break;
			}
		}while(alive);
		System.out.println("Program terminated.");
	}

}
