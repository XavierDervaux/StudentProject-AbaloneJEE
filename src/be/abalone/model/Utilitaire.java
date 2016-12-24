package be.abalone.model;

import java.security.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utilitaire {
	public static String cryptPassword(String password){
		MessageDigest md;
		byte[] byteData;
		StringBuffer hexString = new StringBuffer();
		
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byteData = md.digest();

			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
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
	
    public static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur != null)
        	if(valeur.trim().length() == 0 )
        		valeur = null;
        return valeur;
    }
    
    public static boolean getBoolChamp( HttpServletRequest request, String nomChamp ) {
        boolean res = false;
    	String valeur = request.getParameter( nomChamp );
        
        if ( valeur.equals("true") ) {
            res = true;
        }
        return res;
    }
    
    public static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }

}
