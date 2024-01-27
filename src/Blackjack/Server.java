package Blackjack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws IOException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		ServerSocket ss = new ServerSocket(9999);
		
		while(true) {
			try {
				Socket s = ss.accept();
				System.out.println("New player has joined.");
				es.submit(new Game(s));
				
			}catch (Exception e) { System.err.println(e);}
		}
		

	}

}
