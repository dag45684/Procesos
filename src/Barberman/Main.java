package Barberman;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
        JFrame frame = new JFrame("El barbero dormilon");
        Frame room = new Frame();
        frame.add(room);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
		BarberShop bs = new BarberShop(3, room);
		new Barber(bs).start();
		Thread.sleep(500);
		for (int i=0; i<10; i++) {
			new Client("Client "+i, bs).start();
		}

	}

}
