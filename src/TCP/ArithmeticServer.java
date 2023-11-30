package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ArithmeticServer {

	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(9999);
		while (true) {
			Socket s = server.accept();
			//use DataOutput/InputStream
			try (DataInputStream dis = new DataInputStream(s.getInputStream());
					DataOutputStream dos = new DataOutputStream(s.getOutputStream())) {
				String line = dis.readUTF();
				double res=0;
				if(line.contains("+")) {
					 res = Integer.parseInt(line.split("+")[0])*1d + Integer.parseInt(line.split("+")[1]);
				}
				else if(line.contains("-")) {
					 res = Integer.parseInt(line.split("-")[0])*1d + Integer.parseInt(line.split("-")[1]);
				}
				else if(line.contains("*")) {
					 res = Integer.parseInt(line.split("*")[0])*1d + Integer.parseInt(line.split("*")[1]);
				}
				else if(line.contains("/")) {
					 res = Integer.parseInt(line.split("/")[0])*1d + Integer.parseInt(line.split("/")[1]);
				}
				else if(line.contains("sqrt")) {
					 res = Math.sqrt(Integer.parseInt(line.replaceAll("sqrt","")));
				}
				dos.writeUTF(line);
			} catch (Exception e) {}
		}
	}

}
