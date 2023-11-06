package Barberman;

import java.util.LinkedList;
import java.util.Queue;

public class BarberShop {
	
	Frame frame;
	Queue <Client> l;
	boolean onDuty;
	int free;
	
	public BarberShop(int n, Frame frame) {
		free = n;
		l = new LinkedList<>();
		this.frame = frame;
	}
	
	public void cutHair() {
		Client c = waitClients();
		
		try {
			System.out.println(c.name + " is now cutting his hair.");
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
			System.out.println(c.name + " is now leaving");
			frame.removeFromDesk();
	}
	
	private synchronized Client waitClients() {
		if (l.isEmpty()) {
			onDuty = false;
			System.out.println("Barber goes to sleep");
			frame.activeBarber(false);
			try {
				wait();
			}catch (Exception e) {}
		}
		onDuty = true;
		frame.activeBarber(true);
		frame.minusClientWait();
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
			frame.clientLookForSeat(true);
			if (arrangeClients()) {
				frame.takeSeatFromDoor(true);
				l.offer(c);
			}
			
			if(l.size() == 1 && !onDuty) {
				System.out.println(Thread.currentThread().getName() + " awakes the barber.");
				frame.minusClientWait();
				frame.activeBarber(true);
				notify();
			}

	}
	

}
