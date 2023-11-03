package Barberman;

import java.util.LinkedList;
import java.util.Queue;

public class BarberShop {
	
	Queue <Client> l;
	boolean onDuty;
	int free;
	
	public BarberShop(int n) {
		free = n;
		l = new LinkedList<>();
	}
	
	public void cutHair() {
		Client c = waitClients();
		try {
			System.out.println(c.name + " is now cutting his hair.");
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
			System.out.println(c.name + " is now leaving");
	}
	
	private synchronized Client waitClients() {
		if (l.isEmpty()) {
			onDuty = false;
			System.out.println("Barber goes to sleep");
			try {
				wait();
			}catch (Exception e) {}
		}
		onDuty = true;
		return l.poll();
	}
	
	private boolean arrangeClients() {
		if (l.size() == free) {
			System.out.println(Thread.currentThread().getName() + " leaves due to overcrowd");
			return false;
		}
		return true;
	}
	
	public synchronized void joinWaitingRoom(Client c) {
			System.out.println(Thread.currentThread().getName() + " is joining the waiting room");
			if (arrangeClients()) {
				l.offer(c);
			}
			
			if(l.size() == 1 && !onDuty) {
				System.out.println(Thread.currentThread().getName() + " awakes the barber.");
				notify();
			}

	}
	

}
