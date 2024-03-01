package servers_and_SSL;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;

public class ServerSide_SSL {

	public static void main(String[] args) {

		//Sign a received document and return it
		signReturn();
		
	}
	
	public static void signReturn(){
		
		try(
			ServerSocket ss = new ServerSocket(9999);
			Socket s = ss.accept();
			InputStream is = s.getInputStream();
			BufferedOutputStream bos = new BufferedOutputStream (s.getOutputStream()); //Path to pdf we want to dump
		){
			//Guardamos el documento que nos llegue
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n;
			while ((n = is.read(buf)) > 0) {
				baos.write(buf, 0, n);
			}
			byte[] doc = baos.toByteArray();

			//Cargamos nuestra clave privada
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(new FileInputStream("./certs/kstore.p12"),"passwd".toCharArray());
			PrivateKey pk = (PrivateKey) ks.getKey("alias", "passwd".toCharArray());
			Signature sign = Signature.getInstance("SHA512withRSA");
			sign.initSign(pk);
			
			//Firmamos
			byte[] buff = new byte[1024];
			int m;
			InputStream bais = new ByteArrayInputStream(doc); //Path to pdf we want to sign
			while ((m = bais.read(buff)) > 0) {
				sign.update(buff, 0, m);
			}
			
			//Enviamos
			byte[] signature = sign.sign();
			bos.write(signature);

		}catch (Exception e) { }
	}
}
