package com.elitecore.cpe.bl.exception;


/**
 * Exception raised in Update Operation
 *
 */
public class UpdateBLException extends Exception {
	
	private static final long serialVersionUID = 1L;	

	public UpdateBLException() {
		super("Exception while merging entity");
	}
	
	public UpdateBLException(String message) {
		super(message);
	}
	
	public UpdateBLException(Throwable throwable) {
		super(throwable);
	} 
	
	public UpdateBLException(String message, Throwable throwable) {
		super(message, throwable);
	}	
}

