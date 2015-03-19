package com.elitecore.cpe.bl.exception;



public class SearchBLException extends Exception {
	
	private static final long serialVersionUID = 1L;	
	private long errorCode = -1;
	private String errorMessage;
	
	public SearchBLException() {
		super("Exception while Searching entity");
	}
	
	public SearchBLException(String message) {
		super(message);
		this.errorMessage = message;
	}
	
	public SearchBLException(Throwable throwable) {
		super(throwable);
	} 
	
	public SearchBLException(String message, Throwable throwable) {
		super(message, throwable);
		this.errorMessage = message;
	}	
	
	public SearchBLException(long errorCode){
		this.errorCode = errorCode;
	}
	
	public SearchBLException(long errorCode,String message){
		this(message);
		this.errorCode = errorCode;
		this.errorMessage = message;
	}
	
	public SearchBLException(long errorCode,Throwable throwable){
		this(throwable);
		this.errorCode = errorCode;
	}
	
	public SearchBLException(long errorCode,String message, Throwable throwable) {
		this(message, throwable);
		this.errorCode = errorCode;
		this.errorMessage = message;
	}	
	
	public long getErrorCode(){
		return this.errorCode;
	}
	public String getErrorMessage(){
		return this.errorMessage;
	}
}

