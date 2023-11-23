package TCP;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class curl {

	public static void main(String[] args) {
		
		URL url = null;
		URLConnection uc = null;
		
		try {
			Scanner s = new Scanner(System.in);
			url = new URL(s.nextLine());
			uc = url.openConnection();
			
			InputStream is = uc.getInputStream();
			s = new Scanner(is);
			
			Pattern pattern = Pattern.compile("(:?img *?src)|(:?IMG :?SRC)=\"(.*)\"");
			String tag;
			while (s.hasNextLine()) {
				if ((tag = s.findWithinHorizon(pattern, 0)) != null) {
					System.out.println(tag);
				}
				s.nextLine();
			}
			s.close();
		}catch (Exception e) {}

	}

}
