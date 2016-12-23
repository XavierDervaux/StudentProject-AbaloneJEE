package be.abalone.model;

import java.security.*;

public class Utilitaire {
	
	public static String cryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		byte byteData[] = md.digest();
		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static int boolToInt(boolean b) {
		int res = 0;
		
		if(b == true){
			res = 1;
		}
		return res;
	}
	
	public static boolean intToBool(int i) {
		return i>0;
	}
}
