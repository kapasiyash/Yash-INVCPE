package com.elitecore.cpe.util.encryption.elitecrypt;

import com.elitecore.cpe.util.encryption.DecryptionNotSupportedException;
import com.elitecore.cpe.util.encryption.DefaultEncryption;


public class EliteCryptEncryption extends DefaultEncryption { 
	
  public String crypt(String enteredPassword){
		return EncryptCRC.encryptcrc(enteredPassword);
  }
  
  /**
   * arg1 encryptedPassword
   * arg2 enteredPassword
   * returns true if the encryptedPassword and the enteredPassword matches 
   *		 false if the encryptedPassword and the enteredPassword does not match
   */
	
	public boolean matches(String encryptedPassword, String enteredPassword){
		return EncryptCRC.checkEncrypted(enteredPassword , encryptedPassword);
	}
	
	public String decrypt(String encryptedPassword)
		throws DecryptionNotSupportedException {
		
		return EncryptCRC.decryptcrc(encryptedPassword);
	}

}