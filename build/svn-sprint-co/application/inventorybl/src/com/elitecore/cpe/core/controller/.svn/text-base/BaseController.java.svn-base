package com.elitecore.cpe.core.controller;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.elitecore.cpe.core.base.BaseBL;

public abstract class BaseController extends BaseBL implements Controller{
	
	public Object lookup(Class<?> className) throws NamingException {
		return new InitialContext().lookup("inventoryapp/"+className.getSimpleName().substring(0, className.getSimpleName().lastIndexOf("Local")) + "/local-" + className.getName());
	}
	
}
