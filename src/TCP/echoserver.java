package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class echoserver {

	public static void main(String[] args) throws IOException {
		
		ServerSocket server = new ServerSocket(9999);
		while(true) {
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
		}
	}
}
