package com.elitecore.cpe.bl.delegates;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.core.IBLSession;
import com.elitecore.cpe.core.base.BaseBL;



/**
 * @author yash.kapasi
 *
 */
public abstract class BaseBusinessDelegate extends BaseBL{

	public static final int LOCAL_EJB_EXE_MODE = 0;
	public static final int REMOTE_EJB_EXE_MODE = 1;

	private static Context initialContext;
	private static int executionMode = REMOTE_EJB_EXE_MODE;
	private IBDSessionContext businessDelegateSessionContext;

	static {
		try {
			initializeContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BaseBusinessDelegate() {
	}
	
	public BaseBusinessDelegate(IBDSessionContext context){
		this.businessDelegateSessionContext = context;
	}
	
	private static void initializeContext() throws NamingException {
		Properties properties = null;

		Properties props = System.getProperties();

		String systemJndiIp = props.getProperty("cpe.bl.ip");
		String systemJndiPort = props.getProperty("cpe.bl.port");
		String systemJndiAppServer = props.getProperty("cpe.bl.appserver");

		if (systemJndiIp != null && systemJndiPort != null
				&& !"".equals(systemJndiIp.trim())
				&& !"".equals(systemJndiPort.trim())) {
			if (systemJndiAppServer == null
					|| systemJndiAppServer.equalsIgnoreCase("jboss")
					|| systemJndiAppServer.trim().length() == 0) {

				System.out.println("Load from system properties.");

				properties = new Properties();
				properties.put("java.naming.factory.initial",
						"org.jnp.interfaces.NamingContextFactory");
				properties.put("java.naming.factory.url.pkgs",
						"org.jboss.naming:org.jnp.interfaces");
				properties.put("java.naming.provider.url", systemJndiIp + ":"
						+ systemJndiPort);
			}
		} else {
			try {
				properties = new Properties();
				ClassLoader classLoader = Thread.currentThread()
						.getContextClassLoader();
				InputStream inputStream = classLoader.getResourceAsStream("jndi.properties");
				if(inputStream!=null) {
					properties.load(inputStream);
				} else {
					properties = null;
				}
				System.out.println("Load from properties file.");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				properties = null;
			}
		}
		if (properties != null) {
			System.out.println("java.naming.factory.initial : "
					+ properties.getProperty("java.naming.factory.initial"));
			System.out.println("java.naming.factory.url.pkgs : "
					+ properties.getProperty("java.naming.factory.url.pkgs"));
			System.out.println("java.naming.provider.url : "
					+ properties.getProperty("java.naming.provider.url"));
		} else {
			properties = new Properties();
			properties.put("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			properties.put("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			properties.put("java.naming.provider.url", "127.0.0.1:1199");
		}
		initialContext = new InitialContext(properties);
	}

	protected final boolean isLocalMode() {
		return executionMode == LOCAL_EJB_EXE_MODE;
	}

	protected final int getExecutionMode() {
		return executionMode;
	}

	protected final Object lookup(String jndiName) throws NamingException {
		if (initialContext == null)
			initializeContext();

		return initialContext.lookup(jndiName);
	}

	protected final Object lookup(Class<?> className) throws NamingException {
		System.out
				.println("************* inside lookup - start *****************");
		return initialContext.lookup("inventoryapp/"
				+ className.getSimpleName().substring(0,
						className.getSimpleName().lastIndexOf("Remote"))
				+ "/remote-" + className.getName());
	}

	protected final Object lookupLocal(Class<?> className)
			throws NamingException {
		System.out
				.println("************* inside lookup local - start *****************"+className.getSimpleName());
	
		return initialContext.lookup("inventoryapp/"
				+ className.getSimpleName().substring(0,
						className.getSimpleName().lastIndexOf("Local"))
				+ "/local-" + className.getName());
	}
	
	protected final IBLSession getBLSession() {
		if (this.businessDelegateSessionContext != null)
			 return this.businessDelegateSessionContext.getBLSession();
		
		return null;
	}

	public Context getInitialContext() {
		return initialContext;
	}

	protected final Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

}
