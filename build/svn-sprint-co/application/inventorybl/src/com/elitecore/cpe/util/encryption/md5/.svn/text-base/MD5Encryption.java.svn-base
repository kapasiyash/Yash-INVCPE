package com.elitecore.cpe.util.encryption.md5;

import com.elitecore.cpe.util.encryption.DefaultEncryption;


public class MD5Encryption extends DefaultEncryption {

	public String crypt(String unencryptedPassword){
		//MesgDigest md = new MD5();
		//md.addInput(unencryptedPassword);
		//byte [] fp = md.getMD();
		//byte [] fp = MD5.md5str (unencryptedPassword);
		//String sp = new String();
		//sp.
		//String os = util.bytes2hexStr(fp);
		return MD5.crypt(unencryptedPassword);
	}
	
	public boolean matches(String encryptedPassword, String unecyrptedPassword){
		return crypt(unecyrptedPassword).equals(encryptedPassword);
	}

}
