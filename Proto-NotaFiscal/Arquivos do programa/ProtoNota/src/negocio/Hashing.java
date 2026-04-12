package negocio;

import java.security.MessageDigest;

public class Hashing {
	public static String Hash(String texto) throws Exception {
		//SHA
		MessageDigest objHash = MessageDigest.getInstance("SHA-256");
		byte[] resumo = objHash.digest(texto.getBytes("UTF-8"));
		String hexadecimal = "";
		
		//HEXADECIMAL
		for (int i = 0; i < resumo.length; i++) {
			String temp = Integer.toHexString(0xFF & resumo[i]);
			if (temp.length() == 1) temp = ("0" + temp);
			hexadecimal += temp;
		}
		
		return hexadecimal;
	}
}
