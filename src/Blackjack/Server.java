package blackjack;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) {
		
		ExecutorService es = Executors.newCachedThreadPool();
		try {
			ServerSocket ss = new ServerSocket(9999);
			es.submit(null);
			System.out.println("New player has joined.");
			
		}catch (Exception e) { System.err.println(e);}

	}

}
