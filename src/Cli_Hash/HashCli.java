package Cli_Hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.file.Files;

public class HashCli {

	public static void main(String[] args) {
		
		try {
			Socket s = new Socket("localhost", 9999);
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			//Strings
			String str = "Por que tendre yo que pringar haciendo esto porque mis companeros sean unos putos caraduras";
			out.writeObject(str.getBytes());
			out.flush();
			System.out.println("Hash of the String you've sent: " + in.readLine());
			Thread.sleep(500);
			
			//Bin file
			File f = new File(".\\things\\randomData.dat"); //cualquier fichero binario
			byte[] fileData = Files.readAllBytes(f.toPath());
			out.writeObject(fileData);
			out.flush();
			System.out.println("Hash of the Bin File you've sent: " + in.readLine());
			Thread.sleep(500);
			
			//Objects
			TestObj test = new TestObj("Que conste que no has explicado serializacion", 39); //Cualquier objeto serializable
			if (test instanceof Serializable) {
				out.writeObject(test);
				out.flush();
				System.out.println("Hash of the Object you've sent: " + in.readLine());
			}else {
				System.err.println("Object sent to the server must be Serializable to hash it");
			}
			
		}catch (Exception e) { 
			System.err.println(e);
		}
	}
}

class TestObj implements Serializable{
	
	String statement;
	int painLevel;
	
	public TestObj (String s, int n) {
		statement = s;
		painLevel = n;
	}
}
