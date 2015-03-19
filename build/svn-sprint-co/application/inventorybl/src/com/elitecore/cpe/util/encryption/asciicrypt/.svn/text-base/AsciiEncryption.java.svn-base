package com.elitecore.cpe.util.encryption.asciicrypt;

import com.elitecore.cpe.util.encryption.DecryptionNotSupportedException;
import com.elitecore.cpe.util.encryption.DefaultEncryption;

public class AsciiEncryption extends DefaultEncryption {

  public String crypt(String enteredPassword){
	  
		return EncryptDecrypt.encrypt(enteredPassword);
  }
  
  /**
   * arg1 encryptedPassword
   * arg2 enteredPassword
   * returns true if the encryptedPassword and the enteredPassword matches 
   *		 false if the encryptedPassword and the enteredPassword does not match
   */

	public boolean matches(String encryptedPassword, String enteredPassword){
		
		boolean bValid = false;
		if(encryptedPassword != null && enteredPassword != null) {
			try {
				String decryptPassword = decrypt(encryptedPassword);
				if(decryptPassword != null) {
					bValid = decryptPassword.equals(enteredPassword);
				}
			}catch(Exception e) {
				System.out.println("Error while authentication");
			}
		}
		return bValid;
	}
	
	// if decrypt fails it returns null
	public String decrypt(String encryptedPassword) 
			throws DecryptionNotSupportedException {
		
		String decryptPassword = null;
		try {
			decryptPassword = EncryptDecrypt.decrypt(encryptedPassword);
		}catch(Exception e) {
			System.out.println("Error while decryption");
		}
		return decryptPassword;
	}
}