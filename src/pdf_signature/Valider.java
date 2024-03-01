package pdf_signature;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;

public class Valider {

	public static void main(String[] args) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException, UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(new FileInputStream("./certs_clase/"), "admin".toCharArray()); //Path - Passwd .p12
		PublicKey pk = ks.getCertificate("alias").getPublicKey();
		try (
			BufferedInputStream bis_ogn = new BufferedInputStream (new FileInputStream("./certs_clase/pdf_original.pdf")); //Original PDF
			BufferedInputStream bis_sig = new BufferedInputStream (new FileInputStream("./certs_clase/pdf_firmado.pdf")); //Signed PDF
		){
			Signature sign = Signature.getInstance("SHA512withRSA");
			sign.initVerify(pk);
			
			byte[] buf = new byte[1024];
			int n;
			while ((n = bis_ogn.read(buf)) > 0) {
				sign.update(buf, 0, n);
			}
			
			System.out.println(sign.verify(bis_sig.readAllBytes()) ? "Valid signature" : "Invalid signature");

		}catch (Exception e) { } 

	}

}
