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
		
		while(true) {
			try {
				ServerSocket server = new ServerSocket (9999);
				System.out.println("Open to connection........");
				Socket socket = server.accept();
				System.out.println("Connection stablished.");
				
				out = new PrintWriter(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				while(socket.isBound()) {
					String data = in.readLine();
					char symbol = data.charAt(data.length()-1);
					double value = Double.parseDouble(data.replaceAll("[^0-9,]", ""));
					String secondValue;
					switch (symbol) {
					case '+':
						secondValue = in.readLine(); 
						if (secondValue.equals("C")) {
							out.println(0);
							out.flush();
							break;
						}
						out.println(value + Double.parseDouble(secondValue)); 
						out.flush();
						break;
					case '-':
						secondValue = in.readLine();
						if (secondValue.equals("C")) {
							out.println(0);
							out.flush();
							break;
						}
						out.println(value - Double.parseDouble(secondValue));
						out.flush();
						break;
					case '\u00d7':
						secondValue = in.readLine();
						if (secondValue.equals("C")) {
							out.println(0);
							out.flush();
							break;
						}
						out.println(value * Double.parseDouble(secondValue));
						out.flush();
						break;
					case '\u00f7':
						secondValue = in.readLine();
						if (secondValue.equals("C")) {
							out.println(0);
							out.flush();
							break;
						}
						out.println(value / Double.parseDouble(secondValue));
						out.flush();
						break;
					case '\u221a':
						out.println(Math.sqrt(value));
						out.flush();
						break;
					case '\u00b1':
						out.println(value * -1);
						out.flush();
						break;
					case 'C':
						out.println(0);
						out.flush();
						break;
					default:
						out.println(data);
						out.flush();
					}
				}
			}catch (Exception e) { }
		System.out.println("Connection closed");
		}
	}
}
