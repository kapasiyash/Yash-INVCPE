  package com.elitecore.cpe.util.encryption;

  public class PasswordEncryption{

  public static final int NONE = 0;	  
  public static final int UNIX_CRYPT = 1;
  public static final int MD5 = 2;
  public static final int ELITECRYPT = 3;
  public static final int ASCIICRYPT = 4;
  
  public static final String STR_NONE = "NONE";	  
  public static final String STR_UNIX_CRYPT = "UNIX_CRYPT";
  public static final String STR_MD5 = "MD5";
  public static final String STR_ELITECRYPT = "ELITE_CRYPT";
  public static final String STR_ASCIICRYPT = "ASCIICRYPT";

  public static final String NONE_ENCRYPTION_TYPE_ID = "ENT01";	  
  public static final String UNIX_CRYPT_ENCRYPTION_TYPE_ID = "ENT02";
  public static final String ELITE_CRYPT_ENCRYPTION_TYPE_ID = "ENT03";

  public static final String PREFIX_NONE = "{none}";
  public static final String PREFIX_CRYPT = "{crypt}";
  public static final String PREFIX_MD5 = "{md5}";
  public static final String PREFIX_ELITECRYPT = "{elitecrypt}";
  public static final String PREFIX_ASCII = "{ascii}";
  
  private PasswordEncryption(){
	
  }

  /**
   *	Returns encrypted string
   *	arg1 unecrypted string
   *	arg2 type of encryption
   */	  
  public static final String crypt(String unencryptedPassword,String strAlias) throws NoSuchEncryptionException {
	 return PasswordEncryption.getCrypt(getEncryptionTypeFromAlias(strAlias)).crypt(unencryptedPassword);
  }
  
  /**
   *	Returns int For CorresPonding Alias
   *	arg1 Alias string
  */
   public static final int getEncryptionTypeFromAlias(String strAlias) {
	if(strAlias.equalsIgnoreCase(STR_UNIX_CRYPT) )
	{
		return UNIX_CRYPT;
	}
	else if(strAlias.equalsIgnoreCase(STR_MD5)) 
	{
		return MD5;
	}
	else if(strAlias.equalsIgnoreCase(STR_ELITECRYPT))
	{
		return  ELITECRYPT;
	}
	else if(strAlias.equalsIgnoreCase(STR_ASCIICRYPT))
	{
		return ASCIICRYPT;
	}
	else
	{
		return NONE;
	}
	
  }

  /**
   *	Returns encrypted string
   *    arg1 encryptedPassword String
   *	arg2 unencryptedPassword string
   *	arg3 Alias of encryption
   */
  public static final boolean matches(String encryptedPassword, String unencryptedPassword,String strAlias) 
	  throws NoSuchEncryptionException {
	  
		return PasswordEncryption.getCrypt(getEncryptionTypeFromAlias(strAlias)).matches(encryptedPassword,unencryptedPassword);
  }
  
  public static final String decrypt(String encryptedPassword, String strAlias) 
	  throws NoSuchEncryptionException, DecryptionNotSupportedException {
	  
		return PasswordEncryption.getCrypt(getEncryptionTypeFromAlias(strAlias)).decrypt(encryptedPassword);
  }
	
  public static IEncryption getCrypt(int type) 
	  throws NoSuchEncryptionException{
	  
	String class_name= null;
	if ( type == UNIX_CRYPT)
		class_name = "com.elitecore.cpe.util.encryption.unixcrypt.UnixCryptEncryption";
	else if ( type == MD5)
		class_name = "com.elitecore.cpe.util.encryption.md5.MD5Encryption";
	else if ( type == NONE ) 
		class_name = "com.elitecore.cpe.util.encryption.plaintext.PlainTextEncryption";
	else if ( type == ELITECRYPT ) 
		class_name = "com.elitecore.cpe.util.encryption.elitecrypt.EliteCryptEncryption";
	else if ( type == ASCIICRYPT ) 
		class_name = "com.elitecore.cpe.util.encryption.asciicrypt.AsciiEncryption";
	else
		throw new NoSuchEncryptionException();
	IEncryption  icrypt;
	try {
		icrypt = (IEncryption)Class.forName(class_name).newInstance();
	}catch(java.lang.ClassNotFoundException e){
		throw new NoSuchEncryptionException();
	}catch(java.lang.InstantiationException e1){
		throw new NoSuchEncryptionException();
	}catch(java.lang.IllegalAccessException e1){
		throw new NoSuchEncryptionException();
	}
	return icrypt;
  }

  
}