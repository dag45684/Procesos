package eval1;

public class Ex13T2 {

	public static void main(String[] args) {

	}

}

class Consumer {
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
		if (stored == products.length) // almacén lleno
			try {
				wait();
			} catch (InterruptedException e) {
			}
		products[stored++] = product;
		notify();
	}

	public synchronized String retrieve() {
		if (stored == 0) // almacén vacío
			try {
				wait();
			} catch (InterruptedException e) {
			}
		String producto = products[--stored];
		notify();
		return producto;
	}
}
