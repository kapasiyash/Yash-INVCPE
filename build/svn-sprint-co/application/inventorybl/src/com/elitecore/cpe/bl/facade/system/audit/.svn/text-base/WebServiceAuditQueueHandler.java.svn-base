
package com.elitecore.cpe.bl.facade.system.audit;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.system.audit.WebServiceAuditData;


public class WebServiceAuditQueueHandler {


	QueueConnection queueConnection;

  QueueSession queueSession;
  
  QueueSender queueSender;

//  Queue queue;

  
  public WebServiceAuditQueueHandler()throws JMSException, NamingException {
	
	String queueJNDI = "queue/WebServiceAuditQueue";
	
  
	Context ic = null;
	QueueConnectionFactory cf = null;
	
	
	
	try
	{         
		ic = getInitialContext();

		cf = (QueueConnectionFactory)ic.lookup("/ConnectionFactory");
		queueConnection = cf.createQueueConnection();
		Queue queue = (Queue)ic.lookup(queueJNDI);

		queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

		queueConnection.start();
		
		queueSender = queueSession.createSender(queue);

	}catch(Exception e){
		e.printStackTrace();
	}

  }

  
  public void send(WebServiceAuditData dataObj) throws JMSException {
  
    ObjectMessage message = queueSession.createObjectMessage();
    message.setObject(dataObj);
    queueSender.send(message);

    queueSession.close();
  }

  /**
   * Close session and connection.
   * When done, no sending is possible any more.
   */
  public void close() throws JMSException {
    queueSession.close();
    queueConnection.close();
  }
  
  public static Context getInitialContext()throws javax.naming.NamingException {

	  	Properties props = System.getProperties();

		String systemJndiIp = props.getProperty("cpe.bl.ip");
		String systemJndiPort = props.getProperty("cpe.bl.port");
	  
		System.out.println(systemJndiIp+" :: "+systemJndiPort);
		
		Properties p = new Properties( );
		p.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES," org.jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, "jnp://"+systemJndiIp+":"+systemJndiPort);

		return new javax.naming.InitialContext(p);
  }  
  
} 
