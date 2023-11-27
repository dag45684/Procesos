package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class multiechoserver {

	public static void main(String[] args) throws IOException {
		
		ServerSocket server = new ServerSocket(9999);
		ExecutorService pool = Executors.newFixedThreadPool(30);
		while(true) {
			pool.submit(new Task (server));
		}
	}
}

class Task implements Runnable{
	
	ServerSocket server;
	String msg;
	
	public Task (ServerSocket server){
		this.server = server;
	}
	
	public void run() {
		try {
			Socket s = server.accept();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()))){
				String line;
				while((line = br.readLine()) != null) {
					System.out.println(line);
					pw.println(line);
					pw.flush();
				}
			}
		}catch (Exception e) {}
	}
}
