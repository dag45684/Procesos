package keystore;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Arrays;

public class Keystore_example {

	public static void main(String[] args) {
		
		try {
			KeyStore ks = KeyStore.getInstance("pkcs12");
			char[] pwd = "admin".toCharArray();
			ks.load(new FileInputStream("C:\\cygwin64\\home\\Tarde\\certs\\keystore.p12"), pwd);
			Key k = ks.getKey("first_key_carlos", "admin".toCharArray());

			System.out.println();
			System.out.println("----------------------------------");
			System.out.println("PRIVATE KEY");
			System.out.println(k.getAlgorithm());
			System.out.println(k.getFormat());
			System.out.println(Arrays.toString(k.getEncoded()));
			System.out.println(k.getClass());
			System.out.println("----------------------------------");

			System.out.println();
			System.out.println("----------------------------------");
			System.out.println("CERTIFICATE");
			
			Certificate cert = ks.getCertificate("first_key_carlos");
			System.out.println(cert.getType());
			System.out.println(Arrays.toString(cert.getEncoded()));
			System.out.println("----------------------------------");
			
			System.out.println();
			System.out.println("----------------------------------");
			System.out.println("PUBLIC KEY FROM CERTIFICATE SINCE IT IS ASSOCIATED ONE ANOTHER THROUGH THE ALIAS");

			Key pub_k = cert.getPublicKey();
			System.out.println(pub_k.getAlgorithm());
			System.out.println(pub_k.getFormat());
			System.out.println(Arrays.toString(pub_k.getEncoded()));
			System.out.println(pub_k.getClass());
			System.out.println("----------------------------------");
			
		}catch (Exception e) { }

	}

}
