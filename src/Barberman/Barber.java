package Barberman;

public class Barber extends Thread{

	BarberShop bs;
	
	public Barber(BarberShop bs) {
		super();
		this.bs = bs;
	}
	
	public void run() {
		while(true) {
			bs.cutHair();
		}
	}

}
