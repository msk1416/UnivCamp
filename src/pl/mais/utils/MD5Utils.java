package pl.mais.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	public static String getMD5HashAsString (String passToHash) {
		StringBuffer hexString = new StringBuffer();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		
			byte[] bytes = md.digest(passToHash.getBytes());
			for (int i = 0; i < bytes.length; i++) {
			    if ((0xff & bytes[i]) < 0x10) {
			        hexString.append("0"
			                + Integer.toHexString((0xFF & bytes[i])));
			    } else {
			        hexString.append(Integer.toHexString(0xFF & bytes[i]));
			    }
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hexString.toString();
	}
}
