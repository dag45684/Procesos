package Barberman;

public class Barber extends Thread{

	BarberShop bs;
	
	public Barber(String n, BarberShop bs) {
		super(n);
		this.bs = bs;
	}
	
	public void run() {
		while(true) {
			bs.cutHair();
		}
	}

}
