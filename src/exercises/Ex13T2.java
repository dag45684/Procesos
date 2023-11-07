package exercises;

public class Ex13T2 {

	public static void main(String[] args) {
		Storage stg = new Storage(20);
		for(int i=0; i<40; i++) {
			new Producer(stg, 100).start();
			new Consumer(stg, 1000).start();
		}
		System.out.println(stg.getStored());

	}

}

class Consumer extends Thread{
	private long retard;
	private Storage storage;

	public Consumer (Storage storage, long retard) {
		this.retard = retard;
		this.storage = storage;
	}

	public void run() {
		while (true) {
			String producto = storage.retrieve();
			System.out.println("producto " + producto + " retirado");
			try {
				Thread.sleep(retard);
			} catch (InterruptedException e) {
			}
		}
	}

}

class Producer extends Thread{
	private long retard, cnt = 0;
	private Storage storage;

	public Producer (Storage storage, long retard) {
		super("producer");
		this.retard = retard;
		this.storage = storage;
	 }

	public void run() {
		while (true) {
			String product = String.format("%d", ++cnt);
			storage.store(product);
			System.out.println("product " + product + " stored");
			try {
				Thread.sleep(retard);
			} catch (InterruptedException e) {
			}
		}
	}
}

class Storage {
	private int stored = 0;
	private String[] products;

	public Storage (int capacity) {
	 products = new String[capacity];
	 }

	public synchronized void store(String product) {
		while (stored == products.length) // Storage full
			try {
				wait(); // Put the thread on wait until its notified the action can be performed
			} catch (InterruptedException e) {
			}
		products[stored++] = product;
		notify(); // inform one of the threads on wait they can compete again for the resources of the system and continue its funcions
		// notifyAll(); // same but informing ALL threads on wait
	}

	public synchronized String retrieve() {
		while (stored == 0) //  Storage empty
			try {
				wait();
			} catch (InterruptedException e) {
			}
		String producto = products[--stored];
		notify();
		return producto;
	}
	
	public int getStored() {
		return stored;
	}
}
