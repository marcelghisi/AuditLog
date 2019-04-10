package com.jumia.audit.api.cryptograph;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AESCryptoServiceImpl implements CryptographService {

	//Refactor
	private String key = "Bar12345Bar12345";
	
	private Key aesKey = null;
	
	private Cipher cipher = null;
	
	public AESCryptoServiceImpl() {
		try {
			aesKey = new SecretKeySpec(key.getBytes(), "AES");
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	public void encrypt(Object object,String fieldNameToEncrypt) throws Exception {
		Field field = null;
		Object value = null;
		String toEncrypt = null;
		Optional<String> valueToEncrypt = null;
		try {
			field = object.getClass().getDeclaredField(fieldNameToEncrypt);
			field.setAccessible(true);
			value = field.get(object);
			valueToEncrypt = Optional.of(String.valueOf(value));
			if (value != null && value instanceof String) { 
				toEncrypt = this.encrypt(valueToEncrypt.get().toString());
				field.set(object, toEncrypt);
			};
		} catch (Exception e) {
			throw new Exception("Not possible to encrypt");
		}
	}
	
	public void decrypt(Object object,String fieldNameToEncrypt) throws Exception {
		Field field = null;
		Object value = null;
		String toDecrypt = null;
		Optional<String> valueToDecrypt = null;
		try {
			field = object.getClass().getDeclaredField(fieldNameToEncrypt);
			field.setAccessible(true);
			value = field.get(object);
			valueToDecrypt = Optional.of(String.valueOf(value));
			if (value != null && value instanceof String) { 
				toDecrypt = this.decrypt(valueToDecrypt.get().toString());
				field.set(object, toDecrypt);
			};
		} catch (Exception e) {
			throw new Exception("Not possible to encrypt");
		}
	}
	
	@Override
	public String encrypt(String text) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			String base64 = Base64.getEncoder().encodeToString(encrypted);
			return new String(base64);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String decrypt(String encryptedText) {
        try {
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte[] asBytes = Base64.getDecoder().decode(encryptedText);
			String decrypted = new String(cipher.doFinal(asBytes));
			return decrypted;
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
        return null;
	}

}
