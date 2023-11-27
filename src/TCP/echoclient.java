package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class echoclient {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
		String line;
		while((line = br.readLine()) != null) {
			Socket s = new Socket("localhost", 9999);
			try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()))){
				pw.println(line);
				pw.flush();
				System.out.println(in.readLine());
			}
		}
	}
		

}
