package com.elitecore.cpe.bl.message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.facade.notification.NotificationFacadeLocal;
import com.elitecore.cpe.util.logger.Logger;

@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(
			                propertyName = "destination",
			                propertyValue = "queue/SystemNotificationQueue") 
			})
public class SystemNotificationMessageListener implements MessageListener{

	private static final String MODULE = "SYSTEMNOTIFICATION-JMS";
	@EJB private static NotificationFacadeLocal notificationFacadeLocal;
	

	@Override
	public void onMessage(Message message) {
		
		Logger.logInfo(MODULE,"Inside of onMessage ::::: ");
		
			try {
	             if (message instanceof ObjectMessage) {
	            	 ObjectMessage objMessageRef = (ObjectMessage)message;
	            	 if(objMessageRef.getObject() instanceof NotificationData) {
	            		 NotificationData data = (NotificationData)objMessageRef.getObject();
	            		 notificationFacadeLocal.sendNotificationService(data);
	            	 }
	             }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
