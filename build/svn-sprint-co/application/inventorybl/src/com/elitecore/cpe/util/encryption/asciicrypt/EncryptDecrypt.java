package com.elitecore.cpe.util.encryption.asciicrypt;

import java.util.Date;


public class EncryptDecrypt {
	
	static short ax,bx,cx,dx,si,tmp,x1a2,x1a0[],res,i,inter,cfc,cfd,compte; 
	static char cle[]; /* les variables sont definies de facon globale */ 
	static short c; 


	public static String padding(String s){
		if(s.length() >= 10){
			return s.substring(0,10);
		}else{
			StringBuilder builder = new StringBuilder();
			for(int i = s.length(); i < 10; i++)
			{
				builder.append("0");
			}
			builder.append(s);
			return builder.toString();
		}
	}

	public static String encrypt(String m_data){
		Date d = new Date();
		long time = d.getTime();
		String key = padding(Long.toString(time));
		x1a0 = new short[5];
		cle = new char[11];
		si=0; 
		x1a2=0; 
		i=0; 
		String m_output = "";
		String signature = key;
		//System.out.println(signature);
		m_output += key;
		signature.getChars(0,signature.length(),cle,0);
		byte data[] = new byte[1024];
		for(int i=0;i<10;i++)
		{
			data[i] = (byte)cle[i];
		}

		int length = m_data.length();
		int end=0;

		for(int i=0,j=10;i<length;i++,j+=3){ 
			c = (short)m_data.charAt(i);
			assemble(); 
			cfc=(short)(inter>>8); 
			cfd=(short)(inter&255); 
			for (compte=0;compte<=9;compte++){ 
				cle[compte]=(char)(cle[compte]^c); 
			} 
			c = (short)(c ^ (cfc^cfd)); 
			c = (short)(c&255);
			byte hex[] = new byte[4];
			String strHex = Integer.valueOf(c).toString();
			if(strHex.length() == 1){
				strHex = "00" + strHex;
			}else if (strHex.length() == 2){
				strHex = "0" + strHex;
			}
			m_output += strHex;
			hex = strHex.getBytes();
			if(hex.length >= 1){
				data[j] = hex[0];
			}else{
				data[j] = 0;
			}
			if(hex.length >= 2){
				data[j+1] = hex[1];
			}else{
				data[j+1] = 0;
			}
			if(hex.length >= 3){
				data[j+2] = hex[2];
			}else{
				data[j+2] = 0;
			}
			end = j+2;
		} 
		fin(); 
		return m_output;
	} 

	public static String decrypt(String m_data){
		x1a0 = new short[5];
		cle = new char[11];
		si=0; 
		x1a2=0; 
		i=0; 
		String signature = m_data.substring(0,10);
		//System.out.println(signature);
		signature.getChars(0,signature.length(),cle,0);

		int length = m_data.length();
		String m_output = "";
		
		byte data[] = m_data.getBytes();
		byte out[] = new byte[1024];
		int end=0;

		for(int i=10,j=0;i<length;i+=3,j++){ 
			byte hex[] = new byte[4];
			hex[0] = data[i];
			hex[1] = data[i+1];
			hex[2] = data[i+2];
			hex[3] = (byte)'\0';
			c = Integer.valueOf(m_data.substring(i,i+3)).shortValue();
			assemble(); 
			cfc=(short)(inter>>8); 
			cfd=(short)(inter&255);

			c = (short) (c ^ (cfc^cfd)); 
			c = (short)(c & 255);
			
			for (compte=0;compte<=9;compte++){ 
				cle[compte]=(char)(cle[compte]^(char)c); 
			} 
			out[j] = (byte)c;
			end = j;
		} 
		m_output = new String(out,0,end+1);
		fin(); 
		return m_output;
	} 


	public static void fin(){ 
		/* erase all variables */ 
		for (compte=0;compte<=9;compte++){ 
			cle[compte]=0; 
		} 
		ax=0; 
		bx=0; 
		cx=0; 
		dx=0; 
		si=0; 
		tmp=0; 
		x1a2=0; 
		x1a0[0]=0; 
		x1a0[1]=0; 
		x1a0[2]=0; 
		x1a0[3]=0; 
		x1a0[4]=0; 
		res=0; 
		i=0; 
		inter=0; 
		cfc=0; 
		cfd=0; 
		compte=0; 
		c=0; 
	} 

	public static void assemble(){ 
		x1a0[0]= (short)(( cle[0]*256 )+ cle[1]); 
		code(); 
		inter=res; 
		x1a0[1]= (short)(x1a0[0] ^ ( (cle[2]*256) + cle[3] )); 
		code(); 
		inter=(short)(inter^res); 
		x1a0[2]= (short)(x1a0[1] ^ ( (cle[4]*256) + cle[5] )); 
		code(); 
		inter=(short)(inter^res); 
		x1a0[3]=(short)( x1a0[2] ^ ( (cle[6]*256) + cle[7] )); 
		code(); 
		inter=(short)(inter^res); 
		x1a0[4]= (short)(x1a0[3] ^ ( (cle[8]*256) + cle[9] ));; 
		code(); 
		inter=(short)(inter^res); 
		i=0; 
	} 

	public static void code(){ 
		dx=(short)(x1a2+i); 
		ax=x1a0[i]; 
		cx=0x015a; 
		bx=0x4e35; 

		tmp=ax; 
		ax=si; 
		si=tmp; 

		tmp=ax; 
		ax=dx; 
		dx=tmp; 

		if (ax!=0){ 
			ax=(short)(ax*bx); 
		} 
		tmp=ax; 
		ax=cx; 
		cx=tmp; 
		if (ax!=0){ 
			ax=(short)(ax*si); 
			cx=(short)(ax+cx); 
		} 
		tmp=ax; 
		ax=si; 
		si=(short)tmp; 
		ax=(short)(ax*bx); 
		dx=(short)(cx+dx); 
		ax=(short)(ax+1); 
		x1a2=dx; 
		x1a0[i]=ax; 
		res=(short)(ax^dx); 
		i=(short)(i+1); 
	} 

	public static void main(String args[]){
		if(args.length < 1){
			return;
		}
		String reply = EncryptDecrypt.encrypt(args[0]);
		System.out.println("Encoded : "+reply + " Length : " + reply.length());
		reply = EncryptDecrypt.decrypt(reply);
		System.out.println("Decoded : "+reply + " Length : " + reply.length());
	}
}