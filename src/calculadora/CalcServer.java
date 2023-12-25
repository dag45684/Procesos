package calculadora;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalcServer {
	
	private static PrintWriter out;
	private static BufferedReader in;
	
	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket (9999);
			System.out.println("Open to connection........");
			Socket socket = server.accept();
			System.out.println("Connection stablished.");
			
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				String data = in.readLine();
				char symbol = data.charAt(data.length()-1);
				double value = Double.parseDouble(data.replaceAll("[^0-9,]", ""));
				switch (symbol) {
				case '+':
					out.println(value + Double.parseDouble(in.readLine()));
					break;
				case '-':
					out.println(value - Double.parseDouble(in.readLine()));
					break;
				case '\u00d7':
					out.println(value * Double.parseDouble(in.readLine()));
					break;
				case '\u00f7':
					out.println(value / Double.parseDouble(in.readLine()));
					break;
				case '\u221a':
					out.println(Math.sqrt(value));
					break;
				case '\u00b1':
					out.println(value * -1);
					break;
				default:
					out.println(data);
				}
			}
		}catch (Exception e) { }
	}
}
