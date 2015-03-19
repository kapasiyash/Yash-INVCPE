package com.elitecore.cpe.util.encryption.unixcrypt;

import com.elitecore.cpe.util.encryption.DefaultEncryption;


public class UnixCryptEncryption extends DefaultEncryption {

	public String crypt(String unencryptedPassword){
		return UnixCrypt.crypt(unencryptedPassword);
	}
	
	public boolean matches(String encryptedPassword, String unecyrptedPassword){
		return UnixCrypt.matches(encryptedPassword,unecyrptedPassword);
	}

}
