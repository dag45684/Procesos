package Cli_Hash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;

public class HashServ {

	public static void main(String[] args) {

		try {

			ServerSocket ss = new ServerSocket(9999);
			System.out.println("Accepting socket...");
			Socket s = ss.accept();

			PrintWriter out = new PrintWriter(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());

			MessageDigest md = MessageDigest.getInstance("MD5");
			System.out.println("Server ready.");

			while (true) {
				Object o = in.readObject();
				byte[] hash;
				try {
					hash = md.digest((byte[]) o);
				}catch (Exception e) {
					hash = deserialize(o);
				}
				StringBuilder result = new StringBuilder();
				for(byte b : hash) result.append(String.format("%02X", b));
				System.out.println(result.toString());
				out.println(result.toString());
				out.flush();
			}

		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private static byte[] deserialize(Object o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.flush();
			return baos.toByteArray();
		} catch (IOException e) {
			System.err.println("Object couldnt be deserialized");
		}
		return null;
	}
}
