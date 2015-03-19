package com.elitecore.cpe.bl.exception;

public class AccountBlockedException extends Exception  {


	private static final long serialVersionUID = 1L;
	
	public AccountBlockedException(){
		super("ACL restriction at bl level");
	}
	
	public AccountBlockedException(String message){
		super(message);
	}
	
	public AccountBlockedException(Throwable throwable){
		super(throwable);
	}
	
	public AccountBlockedException(String message,Throwable throwable){
		super(message,throwable);
	}
}
