package Barberman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel {
	
	Timer t = new Timer(1000, (ActionListener) new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    });

	public Queue<Color> clients = new LinkedList<>();
	
	Color barberColor = Color.gray;
	Color clientColor = Color.orange;
	Color bgColor = Color.gray;
	Color waitingColor = Color.white;
	Color doorColor = Color.blue;
	Color deskColor = Color.orange;

	boolean clientAtDoor = false;
	boolean clientAtDesk = false;
	
	public void minusClientWait() {
		clients.poll();
		clientAtDesk = true;
	}
	
	public void removeFromDesk() {
		clientAtDesk = false;
	}

	public void plusClientWait() {
		clients.offer(clientColor);
	}
	
	public void activeBarber (boolean b) {
		barberColor = b ? Color.red : Color.gray;
	}
	
	public void clientLookForSeat (boolean b) {
		clientAtDoor = b;
	}
	
	public void takeSeatFromDoor (boolean b) {
		if (b) {
			try {
// This creates a bit of a delay in the animation for joining the waiting room but i dont know how to workaround it
				Thread.sleep(500); 
			} catch (InterruptedException e) {}
		}
		plusClientWait();
		clientAtDoor = false;
	}
	
	public Frame () {
		t.start();
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Background
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Waiting Room
        g.setColor(waitingColor);
        int roomWidth = 300;
        int roomHeight = 200;
        int roomX = 80;
        int roomY = 50;
        g.fillRect(roomX, roomY, roomWidth, roomHeight);
        g.setColor(Color.black);
        g.drawRect(roomX, roomY, roomWidth, roomHeight);
        
        // Door
        g.setColor(doorColor);
        int doorWidth = 10;
        int doorHeight = roomHeight / 4;
        int doorX = (roomX + roomWidth) - doorWidth/2;
        int doorY = roomY + 10;
        g.fillRect(doorX, doorY, doorWidth, doorHeight);
        
        // Desk
        g.setColor(deskColor);
        int deskSize = 50;
        int deskX = roomX;
        int deskY = roomY + (roomHeight - deskSize) / 2;
        g.fillRect(deskX, deskY, deskSize, deskSize);
        g.setColor(Color.black);
        g.drawRect(deskX, deskY, deskSize, deskSize);
        
        // Barber
        g.setColor(barberColor);
        int circleSize = 20;
        int barberX = deskX + 55;
        int barberY = deskY + 15;
        g.fillOval(barberX, barberY, circleSize, circleSize);
        
        // Clients
        int circleSpacing = 5;
        int circlesX = roomX + roomWidth - (circleSize + circleSpacing);
        int circlesY = roomY + roomHeight - (circleSize + circleSpacing);
        for (int i=0; i<clients.size(); i++) {
            g.setColor(clientColor);
            g.fillOval(circlesX, circlesY, circleSize, circleSize);
            circlesX -= circleSize + circleSpacing;
        }
        
        // Waiter
        if(clientAtDoor) {
        	g.setColor(clientColor);
        	int waiterX = doorX - 25;
        	int waiterY = doorY + 15;
            g.fillOval(waiterX, waiterY, circleSize, circleSize);
        }
        
        // Actual client
        if(clientAtDesk){
        	g.setColor(Color.magenta);
        	int waiterX = deskX + 15;
        	int waiterY = deskY + 15;
            g.fillOval(waiterX, waiterY, circleSize, circleSize);
        	
        }
    }
    
//    public static void main(String[] args) throws InterruptedException {
//        JFrame frame = new JFrame("Waiting Room Drawing");
//        Frame room = new Frame(10);
//        frame.add(room);
//        frame.setSize(500, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
}