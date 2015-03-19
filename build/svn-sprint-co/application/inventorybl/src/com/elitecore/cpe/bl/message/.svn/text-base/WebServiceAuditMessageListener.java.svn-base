package com.elitecore.cpe.bl.message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.elitecore.cpe.bl.data.system.audit.AuditSummary;
import com.elitecore.cpe.bl.data.system.audit.WebServiceAuditData;
import com.elitecore.cpe.bl.facade.system.audit.SystemAuditFacadeLocal;
import com.elitecore.cpe.util.logger.Logger;

@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(
			                propertyName = "destination",
			                propertyValue = "queue/WebServiceAuditQueue") 
			})
public class WebServiceAuditMessageListener implements MessageListener{

	private static final String MODULE = "WEBSERVICEAUDIT-JMS";
	
	@EJB private SystemAuditFacadeLocal systemAuditFacadeLocal;

	@Override
	public void onMessage(Message message) {
		
		Logger.logInfo(MODULE,"Inside of onMessage ::::: ");
		
		
			try {
	             if (message instanceof ObjectMessage) {
	            	 ObjectMessage objMessageRef = (ObjectMessage)message;
	            	 
	            	 if(objMessageRef.getObject() instanceof WebServiceAuditData) {
	            		 
	            		 WebServiceAuditData auditSummary = (WebServiceAuditData) objMessageRef.getObject();
	            		 
	            		 Logger.logInfo(MODULE,auditSummary.toString());
	            		 systemAuditFacadeLocal.doWebSerivceAuditEntry(auditSummary);
	            	 }
	             }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
