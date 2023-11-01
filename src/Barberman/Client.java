package Barberman;

import java.util.Random;

public class Client extends Thread{
	
	BarberShop bs;
	Random rnd = new Random();

	public Client(String n, BarberShop bs) {
		super(n);
		this.bs = bs;
	}
	
	public void run() {
		try {
			Thread.sleep(rnd.nextInt(2000));
		} catch (InterruptedException e) {}
		boolean temp = bs.takeSeat();
		if(temp) bs.trimHair();
	}

}
