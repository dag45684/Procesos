package calculadora;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcServer {
	
	private static PrintWriter out;
	private static BufferedReader in;
	
	private static double memory;
	
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
					System.out.println(data);
					
					double[] nums = new double[2];
					char op = '\0';
					
					try {
						nums = Pattern.compile("(-?\\d*\\.?\\d+)")
								.matcher(data)
								.results()
								.map(MatchResult::group)
								.mapToDouble(Double::parseDouble)
								.toArray();
						op = data.substring(1).replaceAll("\\d*\\.?\\d+", "").charAt(0);
					} catch(Exception err) {
						if (data.matches("\\d*\\.?\\d+")) out.println(data);
						else out.println("ERR");
					}

					switch(op) {
					case '+': // sum
						out.println(nums[0] + nums[1]);
						out.flush();
						break;
					case '-': // rest
						out.println(nums[0] - nums[1]);
						out.flush();
						break;
					case '\u00d7': // multiply
						out.println(nums[0] * nums[1]);
						out.flush();
						break;
					case '\u00f7': // divide
						out.println(nums[0] / nums[1]);
						out.flush();
						break;
					case '\u221a': // Sqrt
						out.println(Math.sqrt(nums[0]));
						out.flush();
						break;
					default:
						out.println("SYN. ERROR");
					}
				}
			}catch (Exception e) { }
		}
	}
}
