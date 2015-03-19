package com.elitecore.cpe.core.base;

import com.elitecore.cpe.bl.data.system.audit.EntityTypeEnum;
import com.elitecore.cpe.bl.data.system.audit.WebServiceAuditData;
import com.elitecore.cpe.bl.facade.system.audit.WebServiceAuditQueueHandler;
import com.elitecore.cpe.util.logger.Logger;

public class BaseWebServiceBL  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE ="WEBSERVICEBL";
	
	public void webServiceAuditLog(Object requestData,
			Object responseData,String responseCode,String responseMessage,String enumType) {
		try {
            
			WebServiceAuditData auditData = new WebServiceAuditData();
			
			EntityTypeEnum typeEnum = EntityTypeEnum.valueOf(enumType);
			
			if(typeEnum!=null) {
				auditData.setEntityTypeId(typeEnum.getId());
				auditData.setEventName(typeEnum.name());
			}
			
			if(responseData!=null) {
				if(responseCode.equals("0")) {
					auditData.setEventProcessStatus("SUCCESS");
				} else {
					auditData.setEventProcessStatus("ERROR");
				}
				
				auditData.setResponseCode(responseCode);
				auditData.setResponseMessage(responseMessage);
				auditData.setOutputParam(responseData.toString());
				
			}
			if(requestData!=null) {
				auditData.setInputParam(requestData.toString());
			}
			
			auditData.setActionAlias("WS_AUDIT");
			
            WebServiceAuditQueueHandler webServiceAuditQueueHandler = new WebServiceAuditQueueHandler();
            webServiceAuditQueueHandler.send(auditData);
            webServiceAuditQueueHandler.close();
			
		} catch(javax.jms.JMSException e){
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			Logger.logTrace(MODULE,e.getMessage());
			e.printStackTrace();
		}
	}
	
}
