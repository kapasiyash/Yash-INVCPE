package com.elitecore.cpe.bl.exception;

public class PasswordExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PasswordExpiredException(){
		super("ACL restriction at bl level");
	}
	
	public PasswordExpiredException(String message){
		super(message);
	}
	
	public PasswordExpiredException(Throwable throwable){
		super(throwable);
	}
	
	public PasswordExpiredException(String message,Throwable throwable){
		super(message,throwable);
	}
	
}
