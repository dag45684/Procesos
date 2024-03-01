package pdf_signature;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class Signer {

	public static void main(String[] args) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(new FileInputStream("./certs_clase/"), "admin".toCharArray()); //Path - Passwd
		PrivateKey pk = (PrivateKey) ks.getKey("alias", "admin".toCharArray()); //Alias - Passwd
		try (
			BufferedInputStream bis = new BufferedInputStream (new FileInputStream("./certs_clase/pdf_original.pdf")); //Path to pdf we want to sign
			BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream("./certs_clase/pdf_firmado.pdf")); //Path to pdf we want to dump
		){
			Signature sign = Signature.getInstance("SHA512withRSA");
			sign.initSign(pk);
			
			byte[] buf = new byte[1024];
			int n;
			while((n = bis.read(buf)) > 0) {
				sign.update(buf, 0, n);
			}
			
			byte[] signature = sign.sign();
			bos.write(signature);
			
		} catch (Exception e) { }
	}

}
