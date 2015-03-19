package com.elitecore.cpe.bl.exception;

public class AccessDeniedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccessDeniedException(){
		super("ACL restriction at bl level");
	}
	
	public AccessDeniedException(String message){
		super(message);
	}
	
	public AccessDeniedException(Throwable throwable){
		super(throwable);
	}
	
	public AccessDeniedException(String message,Throwable throwable){
		super(message,throwable);
	}

}
