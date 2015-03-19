package com.elitecore.cpe.bl.exception;

import java.io.Serializable;

public class TechnicalException extends Exception implements Serializable {


	private static final long serialVersionUID = 1L;
	
	public TechnicalException(){
		super("ACL restriction at bl level");
	}
	
	public TechnicalException(String message){
		super(message);
	}
	
	public TechnicalException(Throwable throwable){
		super(throwable);
	}
	
	public TechnicalException(String message,Throwable throwable){
		super(message,throwable);
	}
}
