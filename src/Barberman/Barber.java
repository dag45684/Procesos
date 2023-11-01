package Barberman;

public class Barber extends Thread{

	boolean sleeping;
	BarberShop bs;
	
	public Barber(String n, BarberShop bs) {
		super(n);
		this.bs = bs;
		sleeping = true;
	}
	
	public void run() {
		while (true) {
			if (!bs.isWorkLeft() && !sleeping) {
				System.out.println("Barber goes to sleep again");
				sleeping = true;
			}else if (bs.isWorkLeft() && !sleeping){
				awake();
				bs.attend();
			}
		}
	}
	
	public void awake() {
		System.out.println("Barber starts working");
		sleeping = false;
		notify();
	}

}
