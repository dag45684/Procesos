package Barberman;

import java.util.Random;

public class Client extends Thread{
	
	BarberShop bs;
	public String name;
	Random rnd = new Random();

	public Client(String n, BarberShop bs) {
		super(n);
		name = n;
		this.bs = bs;
	}
	
	public void run() {
		try {
			Thread.sleep(rnd.nextInt(30000));
		} catch (InterruptedException e) {}
		joinQueue();
	}
	
	public synchronized void joinQueue() {
		bs.joinWaitingRoom(this);
	}

}
