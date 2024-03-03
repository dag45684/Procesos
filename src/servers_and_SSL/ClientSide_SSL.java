package servers_and_SSL;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.cert.X509Certificate;

public class ClientSide_SSL {

	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		//Loading a key/cert and sending
		loadSendExpect();
		//Receiving a key/cert and storing
		receiveAndStore();
		//Encode file as Base64
		encodeB64();
		//Decode file from Base64
		decodeB64();
		//Cipher a file
		ciCert();
	}
	
	public static void ciCert() throws NoSuchAlgorithmException, NoSuchPaddingException, KeyStoreException, UnrecoverableKeyException, CertificateException, FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(new FileInputStream("./certs/my_keystore.p12"), "password".toCharArray());
		PublicKey pk = (PublicKey) ks.getKey("alias", "password".toCharArray()); 
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, pk);

		try(FileInputStream is = new FileInputStream("./certs/my_cert.crt")){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n;
			
			while((n = is.read(buf)) > 0) {
				baos.write(buf, 0, n);
			}
			
			byte[] byteCert = baos.toByteArray();
			byte[] encriptedCert = cipher.doFinal(byteCert);
			String secret = new String(encriptedCert);
		}
	}
	
	public static void deciCert() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, CertificateException, FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException {
		String secret = "";
		byte[] encriptedCert = secret.getBytes();
		
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(new FileInputStream("./certs/my_keystore.p12"), "password".toCharArray());
		PrivateKey pk = (PrivateKey) ks.getKey("alias", "password".toCharArray()); 
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, pk);
		
		byte[] decriptedCert = cipher.doFinal(encriptedCert);
		String finalCert = new String(decriptedCert);
	}
	
	public static void encodeB64() throws IOException {
		try(FileInputStream is = new FileInputStream("./certs/my_cert.crt")){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n;
			
			while((n = is.read(buf)) > 0) {
				baos.write(buf, 0, n);
			}
			
			byte[] byteCert = baos.toByteArray();
			Encoder encoder = Base64.getEncoder();
			byte[] encodedCert = encoder.encode(byteCert);
			String B64Cert = new String(encodedCert);
			
		}catch (Exception e) { }
	}
	
	public static void decodeB64 () {
		String B64Cert = "";
		byte[] decodedCert = Base64.getDecoder().decode(B64Cert);
		String cert = new String(decodedCert);
	}
	
	public static void loadSendExpect() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(new FileInputStream("./certs/my_keystore.p12"), "password".toCharArray());
		//Case public key
		PublicKey pk = (PublicKey) ks.getKey("alias", "password".toCharArray()); 
		//Case certificate
		Certificate cert = ks.getCertificate("alias");
		try (
			Socket s = new Socket("localhost", 9999);
			ObjectOutputStream out = new ObjectOutputStream (s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream (new FileInputStream(""));
		){
			//Sending the key
			//Case public key
			out.writeObject(pk);
			out.flush();
			//Case certificate
			out.writeObject(cert);
			out.flush();
			//Expect answer
			Object response = in.readObject();
			System.out.printf("Response from server: %s ==> %s ", String.valueOf(response.getClass()), response.toString());

		}catch (Exception e) { }
	}
	
	public static void receiveAndStore() {
		try (
			Socket s = new Socket ("localhost", 9999);
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			FileInputStream fis = new FileInputStream("./certs/keystore_from_server.p12");
			FileOutputStream fos = new FileOutputStream("./certs/keystore_from_server.p12");
		){
			Object o = in.readObject();
			//Case certificate
			Certificate crt = (Certificate) o;
			PublicKey puk = crt.getPublicKey();
			KeyStore kstore = KeyStore.getInstance("P12");
			kstore.load(fis, "passwd".toCharArray());
			kstore.setCertificateEntry("alias_recieved_cert", crt);
			kstore.store(fos, "passwd".toCharArray());
			
			//Case key
			PublicKey pubk = (PublicKey) o;
			KeyStore keystr = KeyStore.getInstance("P12");
			keystr.load(fis, "passwd".toCharArray());
			keystr.setKeyEntry("alias", pubk, null, null);
			keystr.store(fos, "passwd".toCharArray());
			
		}catch (Exception e) { }
	}
}
