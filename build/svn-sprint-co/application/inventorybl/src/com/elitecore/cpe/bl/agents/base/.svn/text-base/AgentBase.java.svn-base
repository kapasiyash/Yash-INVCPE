package com.elitecore.cpe.bl.agents.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.elitecore.cpe.core.base.BaseBL;

public class AgentBase extends BaseBL implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static InitialContext initialContext;
	private static Properties properties;
	static{
		try {
			prepareAgentContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Prepares Initial Agent Context
	 * @throws Exception
	 */
	private static void prepareAgentContext() throws Exception{
		 Properties props = System.getProperties();
		 
	        String systemJndiIp = props.getProperty("cpe.bl.ip");
	        String systemJndiPort = props.getProperty("cpe.bl.port");
	        System.out.println("------------------------------------------"+systemJndiIp+" : "+systemJndiPort);
	        if(systemJndiIp != null && systemJndiPort != null && !"".equals(systemJndiIp.trim()) && !"".equals(systemJndiPort.trim())){
	            System.out.println("Load from system properties.");
	            
	             properties = new Properties();
	            properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
	            properties.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
	            properties.put("java.naming.provider.url", systemJndiIp + ":" + systemJndiPort);
	            
	            initialContext = new InitialContext(properties);
	            
	        } else {
	            throw new Exception("System properties cpe.bl.ip & cpe.bl.port pointing to IP Address and Port of Application Server not set. Please use -Dcpe.bl.ip=<IPADDRESS> -Dcpe.bl.port=<PORT> in startup script.");
	        }
	}
	
	/**
	 * Lookup method for agent
	 * @param className
	 * @return Object
	 * @throws NamingException
	 */
	public Object lookup(Class<?> className) throws NamingException {
		return initialContext.lookup("inventoryapp/"+className.getSimpleName().substring(0, className.getSimpleName().lastIndexOf("Remote")) + "/remote-" + className.getName());
	}
	
	public Properties getProperties(){
		return AgentBase.properties;
	}
	protected final Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

}
