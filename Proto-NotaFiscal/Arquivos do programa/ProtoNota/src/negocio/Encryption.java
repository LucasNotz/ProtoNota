package negocio;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	private static final String programKey = "1234123412341234";
	//User encrypt
	public static String UserEncrypt(String texto, String chave) throws Exception {
		
		//inicializar  cifra
		Cipher objCifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//especificar a chave
		SecretKey objChave = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
		
		//especificacao do vetor de inicializacao
		IvParameterSpec objIv = new IvParameterSpec("0123456789123456".getBytes());
		
		//inicializar a cifra
		objCifra.init(Cipher.ENCRYPT_MODE, objChave, objIv);
		
		//encriptar
		byte[] criptograma = objCifra.doFinal(texto.getBytes("UTF-8"));
		
		//devolver na base 64
		return Base64.getEncoder().encodeToString(criptograma);
	}
	
	//Program encrypt (fixed key)
	public static String ProgramEncrypt(String texto) throws Exception {
		String chave = programKey;
		//inicializar  cifra
		Cipher objCifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//especificar a chave
		SecretKey objChave = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
		
		//especificacao do vetor de inicializacao
		IvParameterSpec objIv = new IvParameterSpec("0123456789123456".getBytes());
		
		//inicializar a cifra
		objCifra.init(Cipher.ENCRYPT_MODE, objChave, objIv);
		
		//encriptar
		byte[] criptograma = objCifra.doFinal(texto.getBytes("UTF-8"));
		
		//devolver na base 64
		return Base64.getEncoder().encodeToString(criptograma);
	}
	
	//User decrypt
	public static String UserDecrypt(String criptograma, String chave) throws Exception {
		
		//inicializar  cifra
		Cipher objCifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//especificar a chave
		SecretKey objChave = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
		
		//especificacao do vetor de inicializacao
		IvParameterSpec objIv = new IvParameterSpec("0123456789123456".getBytes());
		
		//inicializar a cifra
		objCifra.init(Cipher.DECRYPT_MODE, objChave, objIv);

		//decritpar 
		byte[] texto = objCifra.doFinal(Base64.getDecoder().decode(criptograma));
		
		//
		return new String(texto, "UTF-8");
	}
	
	//Program decrypt (fixed key)
	public static String ProgramDecrypt(String criptograma) throws Exception {
		String chave = programKey;
		//inicializar  cifra
		Cipher objCifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//especificar a chave
		SecretKey objChave = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
		
		//especificacao do vetor de inicializacao
		IvParameterSpec objIv = new IvParameterSpec("0123456789123456".getBytes());
		
		//inicializar a cifra
		objCifra.init(Cipher.DECRYPT_MODE, objChave, objIv);

		//decritpar 
		byte[] texto = objCifra.doFinal(Base64.getDecoder().decode(criptograma));
		
		//
		return new String(texto, "UTF-8");
	}
}
