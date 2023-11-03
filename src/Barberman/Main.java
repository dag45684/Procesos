package Barberman;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		BarberShop bs = new BarberShop(3);
		new Barber("Nigger", bs).start();
		Thread.sleep(1000);
		for (int i=0; i<10; i++) {
			new Client("Client "+i, bs).start();
		}

	}

}
