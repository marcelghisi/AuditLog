package com.jumia.audit.api.cryptograph;

public interface CryptographService {

	public String encrypt(String text);
	
	public void encrypt(Object object,String fieldNameToEncrypt) throws Exception;
	
	public void decrypt(Object object,String fieldNameToEncrypt)throws Exception;
	
	public String decrypt(String encryptedText);
	
}
