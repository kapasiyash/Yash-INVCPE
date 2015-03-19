package com.elitecore.cpe.util.encryption;

public class DefaultEncryption implements IEncryption {
	
	public String crypt(String enteredPassword) {
		return enteredPassword;
	}
	
	public boolean matches(String encryptedPassword, String enteredPassword) {
		return false;
	}
	
	public String decrypt(String encryptedPassword) 
		throws DecryptionNotSupportedException {
		
		throw new DecryptionNotSupportedException();
	}
}