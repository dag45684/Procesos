package Barberman;

public class BarberShop {
	

	private final int MAX;
	int freeSeats;
	boolean onDuty;
	
	public BarberShop(int MAX) {
		this.MAX = MAX;
		this.freeSeats = MAX;
		this.onDuty = false;
	}
	
	public boolean isWorkLeft() {
		return freeSeats != MAX;
	}
	
	//why the seven fucking depths of jesus christus hells is this not working synchronized
	public boolean takeSeat() {
		System.out.println(Thread.currentThread().getName() + " enters the barber shop.");
		freeSeats--;
		System.out.println("There are " + freeSeats + " seats available.");
		if (freeSeats < 0) {
			System.out.println(Thread.currentThread().getName() + " leaves due to overcrowd.");
			freeSeats++;
			return false;
		}
		System.out.println(Thread.currentThread().getName() + " takes seat.");
		return true;
	}
	
	public synchronized void attend() {
		onDuty = true;
	}
	
	public synchronized void becomesCustomer () {
		if (onDuty) {
			System.out.println(Thread.currentThread().getName() + " joins the waiting room");
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		trimHair();
	}
	
	public synchronized void trimHair() {
		freeSeats++;
		System.out.println("Trimming hair of " + Thread.currentThread().getName());
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {}
		System.out.println(Thread.currentThread().getName() + " leaves the barber shop.");
		onDuty = false;
		notify();
	}
	

}
