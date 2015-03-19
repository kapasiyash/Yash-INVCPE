package com.elitecore.cpe.util.encryption.elitecrypt;

//import java.lang.*;

public class EncryptCRC
{ 
	 final static String[] UNENCRYPT_LIST = {"nikhil","sanjay","hemal" ,"harish"};
	public final static String NO_USER = "12NO_USER";
	public EncryptCRC()
	{
		super();
	}

	public static boolean checkEncrypted(String psswd ,String encrypted)
	{
		   /**
		    * To check if second string is the encrypted string of first string
		    * @param - first string to be checked
		    * @param - second string (encrypted password)
		    * @return boolean- true or false (first string mathced or not with second string)
		    */
	         if(encrypted.equals(encryptcrc(psswd)))
				 return true;
			 else
				 return false;

	}
   /**
    * To encrypt a string using CRC32
	* @param passwd- string to be encrypted
	* @return String- encrypted string
	*
	*/
	public static String encryptcrc(String passwd)
	{
		    /*CRC32 ch = new CRC32();
			ch.update(passwd.getBytes());
			return (Long.toString(ch.getValue()));*/
		StringBuffer encst = new StringBuffer();
			//String encst="";
			for(int i = 0; i< passwd.length() ; i++){
				char temp = (char)((int)passwd.charAt(i)+5);
				encst = encst.append(temp);
			}
			return encst.toString();

	   }



    /**
     * To decrpyt a string using CRC32
	 * @param passwd- string to be encrypted
	 * @return String- decrypted string
	 *
     */
     public static String decryptcrc(String username,String passwd){
            if (!isDecryptAllowed(username))
                return "whypasswd";
            StringBuffer encst = new StringBuffer();
//		    String encst="";
			for(int i = 0; i< passwd.length() ; i++){
				char temp = (char)((int)passwd.charAt(i)-5);
				encst = encst.append(temp);
			}
			return encst.toString();
	 }
     public static boolean isDecryptAllowed(String username){
            int size = UNENCRYPT_LIST.length;
            //Check if found in unecnrytpt list then return false
            if (username.equals(NO_USER))
                return true;
            for ( int i = 0; i < size ; i++){
                if (username.equalsIgnoreCase(UNENCRYPT_LIST[i]))
                    return false;
            }
            //If not found in list then
            return true;

     }

	 public static String decryptcrc(String passwd){
	        return decryptcrc(NO_USER,passwd);
	 }
   }