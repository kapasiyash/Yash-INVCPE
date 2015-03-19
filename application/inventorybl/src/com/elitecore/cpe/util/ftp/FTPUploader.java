package com.elitecore.cpe.util.ftp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUploader {
	
	private FTPClient ftp = null;
	
	public FTPUploader(String host,int port, String user, String pwd) throws Exception{
		ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		int reply;
		ftp.connect(host,port);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new Exception("Exception in connecting to FTP Server");
		}
		ftp.login(user, pwd);
		
		ftp.setFileType(FTP.LOCAL_FILE_TYPE);
		ftp.enterLocalPassiveMode();
	}
	
	public void uploadFile(String localFileFullName, String fileName, String hostDir)
			throws Exception {
		InputStream input  =null;
		try{
			input = new BufferedInputStream(new FileInputStream(new File(localFileFullName)));
			this.ftp.storeFile(hostDir + fileName, input);
		}catch(Exception e){
			throw e;
		}finally{
			if(input!=null){
				input.close();
			}
		}
	}

	public void disconnect(){
		if (this.ftp.isConnected()) {
			try {
				this.ftp.logout();
				this.ftp.disconnect();
			} catch (IOException f) {
				// do nothing as file is already saved to server
			}
		}
	}
	
/*	public static void main(String[] args) {
		try {
			FTPUploader uploader = new FTPUploader("ftp.elitecore.com",21 ,"sterlite", "EC@str123");
			uploader.uploadFile("D:\\tmp\\log_04193542.log", "test.log", "/");
			uploader.disconnect();
			System.out.println("Done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
}
