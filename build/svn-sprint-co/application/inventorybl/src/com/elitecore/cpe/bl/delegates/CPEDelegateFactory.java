package com.elitecore.cpe.bl.delegates;

import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CPEDelegateFactory {

	
	private static CPEDelegateFactory instance;
	
	private InitialContext initialContext;
	
	private CPEDelegateFactory(Hashtable environment){
		if(environment!=null){
			try {
				initialContext = new InitialContext(environment);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}else{
			try {
				initialContext = new InitialContext();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static CPEDelegateFactory getInstance(Hashtable environment ){
		if(instance== null){
			instance = new CPEDelegateFactory(environment);
		}
		
		return instance;
	}
	
	public final Object lookup(Class<?> className) throws NamingException {
		System.out
				.println("************* inside lookup - start *****************");
		return initialContext.lookup("cinventoryapp/"
				+ className.getSimpleName().substring(0,
						className.getSimpleName().lastIndexOf("Remote"))
				+ "/remote-" + className.getName());
	}

	public final Object lookupLocal(Class<?> className)
			throws NamingException {
		System.out
				.println("************* inside lookup local - start *****************");
	
		return initialContext.lookup("cinventoryapp/"
				+ className.getSimpleName().substring(0,
						className.getSimpleName().lastIndexOf("Local"))
				+ "/local-" + className.getName());
	}
	
	
	

}
