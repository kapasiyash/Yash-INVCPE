package com.elitecore.cpe.bl.message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.elitecore.cpe.bl.constants.system.audit.AuditTagConstant;
import com.elitecore.cpe.bl.data.system.audit.AuditQueueData;
import com.elitecore.cpe.bl.data.system.audit.AuditSummary;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.facade.system.audit.SystemAuditFacadeLocal;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryWrapperVO;
import com.elitecore.cpe.util.logger.Logger;

@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(
			                propertyName = "destination",
			                propertyValue = "queue/SystemAuditQueue") 
			})
public class SystemAuditMessageListener implements MessageListener{

	private static final String MODULE = "SYSTEMAUDIT-JMS";
	
	@EJB private static SystemAuditFacadeLocal systemAuditFacadeLocal;
	private static final int MYTHREADS = 30;

	@Override
	public void onMessage(Message message) {
		
		Logger.logInfo(MODULE,"Inside of onMessage ::::: ");
		
			try {
	             if (message instanceof ObjectMessage) {
	            	 ObjectMessage objMessageRef = (ObjectMessage)message;
	            	 
	            	 if(objMessageRef.getObject() instanceof AuditSummary) {
	            		 AuditSummary auditSummary = (AuditSummary)objMessageRef.getObject();
	            		 
	            		 Logger.logInfo(MODULE,auditSummary.toString());
	            		 systemAuditFacadeLocal.doAuditEntry(auditSummary);
	            	 } else if(objMessageRef.getObject() instanceof AuditQueueData) {
	            		 
	            		 ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
	            		 int count = 0;
	            		 AuditQueueData queueData =  (AuditQueueData) objMessageRef.getObject();
	            		 Logger.logTrace(MODULE, "Before For Loop onMessage");
	            		 List<AuditSummary> summaries = new ArrayList<AuditSummary>();
	            		 for(Object inventoryWrapper : queueData.getObjectList()){
	            			 count++;
	            			 InventoryWrapperVO inventoryWrapperVO = (InventoryWrapperVO) inventoryWrapper; 
	            			 
	                 		if(inventoryWrapperVO.getInventoryData() != null){
	         	        		Map<String,Object> mapAudit = new HashMap<String, Object>();
	         	        		mapAudit.put(AuditTagConstant.INVENTORYNO,inventoryWrapperVO.getInventoryData().getInventoryNo());
	         	        		AuditSummary data = addToAuditDynamicMessage(queueData.getActionAlias(), queueData.getReason(),queueData.getAuditTypeId(), mapAudit,queueData.getSessionUserId(),queueData.getIpAddress());
//	         	        		summaries.add(data);
	         	        		Runnable worker = new MyRunnable(data);
	         	        		executor.execute(worker);
	                 		}
	                 		Logger.logTrace(MODULE, "inside For Loop onMessage count " +count);
	                 	}
	            		 executor.shutdown();
	            		 
//	            		 systemAuditFacadeLocal.doAuditEntry(summaries);
	            		 
	            		 Logger.logTrace(MODULE, "After For Loop onMessage");
	            		 
	            	 } else {
	            		 Logger.logTrace(MODULE, "Instance of "+ objMessageRef.getObject());
	            	 }
	            	 
	             }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static class MyRunnable implements Runnable {

		private AuditSummary data ;
		
		public MyRunnable(AuditSummary data ) {
			this.data = data;
		}
		
		@Override
		public void run() {
			
			try {
				systemAuditFacadeLocal.doAuditEntry(data);
			} catch (CreateBLException e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	
	
	/**
	 * Add to Audit Data 
	 * @param strActionAlias
	 * @param strReason
	 * @param auditTypeId
	 * @param mapData
	 * @param sessionUserId
	 * @param ipAddress
	 * @return {@link AuditSummary}
	 */
	private AuditSummary addToAuditDynamicMessage(String strActionAlias, String strReason,
			String auditTypeId, Map<String, Object> mapData,String sessionUserId,String ipAddress) {
		
		String strSystemUserId="STF0001";
		String strIpAddress="127.0.0.1";
		
		AuditSummary data = new AuditSummary();
		data.setActionAlias(strActionAlias);
		data.setAuditTypeId(auditTypeId);
		data.setAuditDate(new Timestamp(new Date().getTime()));
		data.setReason(strReason);
		data.setRemarks(null);
		data.setTagMap(mapData);
		
		strSystemUserId = sessionUserId;
		
			
		//Added if block on 13 october	
		if(!ipAddress.equals("::1")){
			strIpAddress = ipAddress;
		}
		data.setUserId(strSystemUserId);
		data.setIpAddress(strIpAddress);
		
		
		return data;
		/*try {
			systemAuditFacadeLocal.doAuditEntry(data);
		} catch (CreateBLException e) {
			e.printStackTrace();
		}*/
	}
		
	
	
}
