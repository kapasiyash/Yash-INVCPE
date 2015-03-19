package com.elitecore.cpe.bl.facade.master.warehouse;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.rowset.CachedRowSet;


import com.elitecore.cpe.bl.constants.notification.NotificationConstants;
import com.elitecore.cpe.bl.data.notification.NotificationData;
import com.elitecore.cpe.bl.entity.inventory.inventorymgt.InventoryData;
import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.entity.inventory.master.ConfigureThresholdData;
import com.elitecore.cpe.bl.entity.inventory.master.ItemData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceSubTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.ResourceTypeData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseData;
import com.elitecore.cpe.bl.entity.inventory.master.WarehouseTypeData;
import com.elitecore.cpe.bl.exception.NoSuchControllerException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.vo.inventorymgt.ThresholdNotificationEmailVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.ThresholdStatusVO;
import com.elitecore.cpe.bl.vo.master.WareHouseSummaryVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.util.logger.Logger;
import com.sun.rowset.CachedRowSetImpl;

public class WarehouseUtil {

	public static WarehouseData getWarehouseData(WarehouseVO warehouseVO){
		WarehouseData warehouseData = new WarehouseData();
		if(warehouseVO != null){
			
			warehouseData.setWarehouseId(warehouseVO.getWarehouseId());
			warehouseData.setName(warehouseVO.getName());
			warehouseData.setLocation(warehouseVO.getLocation());
			warehouseData.setAlias(warehouseVO.getAlias());
			warehouseData.setDescription(warehouseVO.getDescription());
			warehouseData.setWarehouseCode(warehouseVO.getWarehouseCode());
			warehouseData.setCreatedby(warehouseVO.getCreatedby());
			warehouseData.setCreatedate(warehouseVO.getCreateDate());
			warehouseData.setUpdatedby(warehouseVO.getUpdatedby());
			warehouseData.setUpdatedate(warehouseVO.getUpdatedDate());
			warehouseData.setSystemgenerated(warehouseVO.getSystemgenerated());
			warehouseData.setEditable(warehouseVO.getEditable());
			warehouseData.setReason(warehouseVO.getReason());
			if(warehouseVO.getParentWarehouseId() != null){
				WarehouseData parentWHData = new WarehouseData();
				parentWHData.setWarehouseId(warehouseVO.getParentWarehouseId());
				warehouseData.setParentWarehouse(parentWHData);
			}if(warehouseVO.getWarehouseType() != null){
				WarehouseTypeData data = new WarehouseTypeData();
				data.setWarehouseTypeId(warehouseVO.getWarehouseType().getWarehouseTypeId());
				warehouseData.setWarehouseTypeData(data);
			}
			warehouseData.setOwner(warehouseVO.getOwner());
			warehouseData.setContactNo(warehouseVO.getContactNo());
			warehouseData.setEmailId(warehouseVO.getEmailId());
		}
		return warehouseData;
	}
	
	public static WarehouseVO getWarehouseVO(WarehouseData warehouseData){
		WarehouseVO warehouseVO = new WarehouseVO();
		if(warehouseData != null){
			warehouseVO.setWarehouseId(warehouseData.getWarehouseId());
			warehouseVO.setName(warehouseData.getName());
			warehouseVO.setLocation(warehouseData.getLocation());
			warehouseVO.setAlias(warehouseData.getAlias());
			warehouseVO.setDescription(warehouseData.getDescription());
			warehouseVO.setWarehouseCode(warehouseData.getWarehouseCode());
			try{
			if(warehouseData.getCreatedby()!=null)
			warehouseVO.setCreatedby(UserFactory.findUserById(warehouseData.getCreatedby()).getName());
			warehouseVO.setCreateDate(warehouseData.getCreatedate());
			if(warehouseData.getUpdatedby()!=null)
			warehouseVO.setUpdatedby(UserFactory.findUserById(warehouseData.getUpdatedby()).getName());
			} catch (SearchBLException e) {
				e.printStackTrace();
			} catch (NoSuchControllerException e) {
				e.printStackTrace();
			}
			warehouseVO.setUpdatedDate(warehouseData.getUpdatedate());
			warehouseVO.setSystemgenerated(warehouseData.getSystemgenerated());
			warehouseVO.setEditable(warehouseData.getEditable());
			if(warehouseData.getParentWarehouse() != null){
				warehouseVO.setParentWarehouseId(warehouseData.getParentWarehouse().getWarehouseId());
				warehouseVO.setParentWarehouseName(warehouseData.getParentWarehouse().getName() );
			}if(warehouseData.getWarehouseTypeData() != null){
				WarehouseTypeVO vo = new WarehouseTypeVO();
				vo.setWarehouseTypeId(warehouseData.getWarehouseTypeData().getWarehouseTypeId());
				vo.setName(warehouseData.getWarehouseTypeData().getName());
				warehouseVO.setWarehouseType(vo);
			}
			warehouseVO.setOwner(warehouseData.getOwner());
			warehouseVO.setContactNo(warehouseData.getContactNo());
			warehouseVO.setEmailId(warehouseData.getEmailId());
			
		}
		return warehouseVO;
	}
	
	public static WarehouseTypeData getWarehouseTypeData(WarehouseTypeVO warehouseTypeVO){
		WarehouseTypeData warehouseTypeData = new WarehouseTypeData();
		if(warehouseTypeVO != null){
			
			warehouseTypeData.setWarehouseTypeId(warehouseTypeVO.getWarehouseTypeId());
			warehouseTypeData.setName(warehouseTypeVO.getName());
			warehouseTypeData.setAlias(warehouseTypeVO.getAlias());
			warehouseTypeData.setDescription(warehouseTypeVO.getDescription());
			
			warehouseTypeData.setCreatedby(warehouseTypeVO.getCreatedby());
			warehouseTypeData.setCreatedate(warehouseTypeVO.getCreateDate());
			warehouseTypeData.setUpdatedby(warehouseTypeVO.getUpdatedby());
			warehouseTypeData.setUpdatedate(warehouseTypeVO.getUpdatedDate());
			warehouseTypeData.setSystemgenerated(warehouseTypeVO.getSystemgenerated());
			warehouseTypeData.setReason(warehouseTypeVO.getReason());
		}
		return warehouseTypeData;
	}
	
	public static WarehouseTypeVO getWarehouseTypeVO(WarehouseTypeData warehouseTypeData){
		WarehouseTypeVO warehouseTypeVO = new WarehouseTypeVO();
		if(warehouseTypeData != null){
			warehouseTypeVO.setWarehouseTypeId(warehouseTypeData.getWarehouseTypeId());
			warehouseTypeVO.setName(warehouseTypeData.getName());
			warehouseTypeVO.setAlias(warehouseTypeData.getAlias());
			warehouseTypeVO.setDescription(warehouseTypeData.getDescription());
		//	warehouseTypeVO.setWarehouseTypename(warehouseTypeData.);
		try{	
			warehouseTypeVO.setCreatedby(UserFactory.findUserById(warehouseTypeData.getCreatedby()).getName());
			warehouseTypeVO.setUpdatedby(UserFactory.findUserById(warehouseTypeData.getUpdatedby()).getName());
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (NoSuchControllerException e) {
			e.printStackTrace();
		}
			warehouseTypeVO.setSystemgenerated(warehouseTypeData.getSystemgenerated());
			warehouseTypeVO.setCreateDate(warehouseTypeData.getCreatedate());
			warehouseTypeVO.setUpdatedDate(warehouseTypeData.getUpdatedate());
		}
		return warehouseTypeVO;
	}
	
	
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static List<ConfigureThresholdData> getThresholdData(List<ConfigureThresholdVO> configureThresholdVOs) {
		List <ConfigureThresholdData> configureThresholdDatas = new ArrayList<ConfigureThresholdData>();
		if (configureThresholdVOs != null && !configureThresholdVOs.isEmpty()) {
			for(ConfigureThresholdVO configureThresholdVO:configureThresholdVOs){
				Logger.logTrace("Inside Util class:","VO from Composer::"+configureThresholdVO);
			ConfigureThresholdData configureThresholdData=new  ConfigureThresholdData();	
			/*WarehouseData warehouseData=new WarehouseData();
			warehouseData.setWarehouseId(configureThresholdVO.getWarehouseID());*/
			
			if(configureThresholdVO.getResourceTypeID()!=null){
			/*ResourceTypeData resourceTypeData=new ResourceTypeData();
			resourceTypeData.setResourceTypeId(configureThresholdVO.getResourceTypeID());*/
			configureThresholdData.setResourceTypeId(configureThresholdVO.getResourceTypeID());
			}
			
			if(configureThresholdVO.getResourceSubTypeID()!=null){
				/*ResourceSubTypeData resourceSubTypeData=new ResourceSubTypeData();
				resourceSubTypeData.setResourceSubTypeId(configureThresholdVO.getResourceSubTypeID());*/
				configureThresholdData.setResourceSubTypeId(configureThresholdVO.getResourceSubTypeID());
			}
			if(configureThresholdVO.getThresholdID()!=null){
				configureThresholdData.setThresholdID(configureThresholdVO.getThresholdID());
			}
			configureThresholdData.setWarehouseId(configureThresholdVO.getWarehouseID());
			configureThresholdData.setThresholdType(configureThresholdVO.getThresholdType());
			configureThresholdData.setThresholdValue(configureThresholdVO.getThresholdValue());
			configureThresholdVO.setSystemgenerated("N");
			configureThresholdData.setCreatedby(configureThresholdVO.getCreatedby());
			configureThresholdData.setCreatedate(getCurrentTimestamp());
			configureThresholdData.setUpdatedby(configureThresholdVO.getUpdatedby());
			configureThresholdData.setUpdatedate(getCurrentTimestamp());
			configureThresholdData.setSystemgenerated(configureThresholdVO.getSystemgenerated());
			configureThresholdData.setEmail(configureThresholdVO.getEmail());
			configureThresholdData.setMobile(configureThresholdVO.getMobile());
			if(configureThresholdVO.getQuantity()!=null) {
				configureThresholdData.setQuantity(configureThresholdVO.getQuantity().longValue());
			}
			configureThresholdDatas.add(configureThresholdData);
			}

		}
		Logger.logTrace("Inside Util class:","DATA List::::"+configureThresholdDatas);
		return configureThresholdDatas;
	}
	
	public static ConfigureThresholdVO getConfigureThresholdVO(ConfigureThresholdData configureThresholdData){
		ConfigureThresholdVO configureThresholdVO = new ConfigureThresholdVO();
		if(configureThresholdData != null){
			
			if(configureThresholdData.getWarehousedata() != null){
				configureThresholdVO.setWarehouseID(configureThresholdData.getWarehousedata().getWarehouseId());
			}
			if(configureThresholdData.getResourceTypedata() != null){
				configureThresholdVO.setResourceTypeName(configureThresholdData.getResourceTypedata().getName());
				configureThresholdVO.setResourceTypeID(configureThresholdData.getResourceTypedata().getResourceTypeId());
				if(configureThresholdData.getResourceSubTypeData() != null){
				configureThresholdVO.setResourceSubTypeID(configureThresholdData.getResourceSubTypeData().getResourceSubTypeId());
				configureThresholdVO.setResourceSubTypeName(configureThresholdData.getResourceSubTypeData().getName());
				}
			}
			
			configureThresholdVO.setThresholdID(configureThresholdData.getThresholdID());
			configureThresholdVO.setThresholdValue(configureThresholdData.getThresholdValue());
			configureThresholdVO.setThresholdType(configureThresholdData.getThresholdType());
			configureThresholdVO.setCreatedby(configureThresholdData.getCreatedby());
			configureThresholdVO.setUpdatedby(configureThresholdData.getUpdatedby());
			configureThresholdVO.setSystemgenerated(configureThresholdData.getSystemgenerated());
			configureThresholdVO.setEmail(configureThresholdData.getEmail());
			configureThresholdVO.setMobile(configureThresholdData.getMobile());
			if(configureThresholdData.getQuantity()!=null) {
				configureThresholdVO.setQuantity(configureThresholdData.getQuantity().intValue());
			}
		}
		
		return configureThresholdVO;
	}
	
	public static void processThresholdNotification(CachedRowSetImpl cachedRowSetImpl){
		
		Map <String,ThresholdStatusVO> map=new LinkedHashMap<String, ThresholdStatusVO>();
		try{
			Logger.logTrace("WarehouseUtil","processThresholdNotification() called");
			boolean flag=false;
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "192.168.0.252");
			props.put("mail.smtp.port", "25");
			
//			Session session = Session.getDefaultInstance(props,
			
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("mtone@ec.com","mT0N");
						}
					});
			StringBuffer body=new StringBuffer();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mtone@ec.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("kapasiyash@gmail.com"));
			message.setSentDate(new Date());
			
			String header="";
			List<String> headerList=new ArrayList<String>();
			headerList.add("WareHouse");
			headerList.add("Resource");
			headerList.add("ThresholdLimit");
			headerList.add("Current Available");
			

			CachedRowSet rs=cachedRowSetImpl;
			
			while(rs.next()){
				flag=true;
//				if(!rs.getString("Total").equals(null)){
				if(rs.getString("Total") != null && !rs.getString("Total").equals("")){
					ThresholdStatusVO thresholdStatusVO;
					
					thresholdStatusVO=map.get(rs.getString("warehouse"));
					if(thresholdStatusVO!=null){
						thresholdStatusVO.getRemarks().append("<tr><td>"+rs.getString("warehouse")+"</td>"
								+"<td>"+rs.getString("resourcename")+"</td>"+"<td>"+rs.getString("THRESHOLDVALUE")+"</td>"+"<td>"+rs.getString("available")+"</td></tr>");
						//thresholdStatusVO.getRemarks().append("\n");
					}else{
						
						thresholdStatusVO=new ThresholdStatusVO();
						thresholdStatusVO.setWarehouse(rs.getString("warehouse"));
					//	thresholdStatusVO.getRemarks().append("\n");
						thresholdStatusVO.setRemarks(new StringBuffer("<tr><td>"+rs.getString("warehouse")+"</td>"
						+"<td>"+rs.getString("resourcename")+"</td>"+"<td>"+rs.getString("THRESHOLDVALUE")+"</td>"+"<td>"+rs.getString("available")+"</td></tr>"));
						map.put(rs.getString("warehouse"), thresholdStatusVO);
					}
				}
			}
			message.setSubject("Resource Threshold Limit Status Report");
			body.append("<html>");
			body.append("Hello Owner,"+ "</br></br>");
			body.append("<table border=\"1\">");
			body.append("<tr>");
			for(String str:headerList){
				body.append("<td>");
				body.append(str);
				body.append("</td>");
			}
			body.append("</tr>");
				for(ThresholdStatusVO thresholdStatusVO:map.values() ){
					
					body.append(thresholdStatusVO.getRemarks().toString());
				}
				body.append("</table>");
				body.append("</html>");
				
				
				message.setContent(body.toString(), "text/html");
				
			//	message.setText(body.toString());
				if(flag){
				Transport.send(message);
				}else{
					Logger.logTrace("WarehouseUtil","No Need to send email,No resource cross threshold value");
				}

				Logger.logTrace("Inside Util class:","processThresholdNotification() Completed");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	public static void main(String[] args) throws Exception{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "192.168.0.252");
		props.put("mail.smtp.port", "25");
		
		Properties propsEmailconfig = new Properties();
		propsEmailconfig=readEmailProperty("EmailConfig");
		System.out.println("Property:::"+propsEmailconfig.getProperty("smtp.port"));
//		Session session = Session.getDefaultInstance(props,
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("mtone@ec.com","mT0N");
					}
				});
		StringBuffer body=new StringBuffer("");
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("mtone@ec.com"));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("kapasiyash@gmail.com"));
		message.setSentDate(new Date());
		message.setSubject("Resource Threshold Limit Status Report");
		message.setContent(body.toString(), "text/html");
		Transport.send(message);
		
	}
	
	
	public static void processThresholdNotificationEmailCommon(List<ThresholdNotificationEmailVO> thresholdDatas,String commonEmail){
		
		Map <String,ThresholdStatusVO> map=new LinkedHashMap<String, ThresholdStatusVO>();
		try{
			Logger.logTrace("WarehouseUtil","processThresholdNotification() called");
			boolean flag=false;
			final Properties propsEmailconfig =readEmailProperty("EmailConfig");
			
		
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", propsEmailconfig.getProperty("smtp.host"));
			props.put("mail.smtp.port", propsEmailconfig.getProperty("smtp.port"));
//			Session session = Session.getDefaultInstance(props,
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(propsEmailconfig.getProperty("admin.emailId"),propsEmailconfig.getProperty("admin.passwd"));
						}
					});
			StringBuffer body=new StringBuffer();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(propsEmailconfig.getProperty("admin.emailId")));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(commonEmail));
			message.setSentDate(new Date());
			
			String header="";
			List<String> headerList=new ArrayList<String>();
			headerList.add("WareHouse");
			headerList.add("Resource");
			headerList.add("ThresholdLimit");
			headerList.add("Current Available");
			
			for(ThresholdNotificationEmailVO thresholdData : thresholdDatas) {
				flag=true;
				ThresholdStatusVO thresholdStatusVO;
				
				thresholdStatusVO=map.get(thresholdData.getWareHouseName());
				if(thresholdStatusVO!=null){
					thresholdStatusVO.getRemarks().append("<tr><td>"+thresholdData.getWareHouseName()+"</td>"
							+"<td>"+thresholdData.getResourceName()+"</td>"+"<td>"+thresholdData.getThresholdValue()+"</td>"+"<td>"+thresholdData.getAvailable()+"</td></tr>");
					//thresholdStatusVO.getRemarks().append("\n");
				}else{
					
					thresholdStatusVO=new ThresholdStatusVO();
					thresholdStatusVO.setWarehouse(thresholdData.getWareHouseName());
				//	thresholdStatusVO.getRemarks().append("\n");
					thresholdStatusVO.setRemarks(new StringBuffer("<tr><td>"+thresholdData.getWareHouseName()+"</td>"
					+"<td>"+thresholdData.getResourceName()+"</td>"+"<td>"+thresholdData.getThresholdValue()+"</td>"+"<td>"+thresholdData.getAvailable()+"</td></tr>"));
					map.put(thresholdData.getWareHouseName(), thresholdStatusVO);
				}
			}
			
			
			message.setSubject("Resource Threshold Limit Status Report");
			body.append("<html>");
			body.append("Hello Owner,"+ "</br></br>");
			body.append("<table border=\"1\">");
			body.append("<tr>");
			for(String str:headerList){
				body.append("<td>");
				body.append(str);
				body.append("</td>");
			}
			body.append("</tr>");
				for(ThresholdStatusVO thresholdStatusVO:map.values() ){
					
					body.append(thresholdStatusVO.getRemarks().toString());
				}
				body.append("</table>");
				body.append("</html>");
				
				
				message.setContent(body.toString(), "text/html");
				
			//	message.setText(body.toString());
				if(flag){
				Transport.send(message);
				}else{
					Logger.logTrace("WarehouseUtil","No Need to send email,No resource cross threshold value");
				}

				Logger.logTrace("Inside Util class:","processThresholdNotification() Completed");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		processThresholdNotificationEmail(thresholdDatas);
		
		
	}
	
	public static void processThresholdNotificationEmail(List<ThresholdNotificationEmailVO> thresholdDatas){
		
		Map <String,ThresholdStatusVO> map=new LinkedHashMap<String, ThresholdStatusVO>();
		try{
			Logger.logTrace("WarehouseUtil","processThresholdNotification() called");
			boolean flag=false;
			final Properties propsEmailconfig =readEmailProperty("EmailConfig");
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", propsEmailconfig.getProperty("smtp.host"));
			props.put("mail.smtp.port", propsEmailconfig.getProperty("smtp.port"));
//			Session session = Session.getDefaultInstance(props,
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(propsEmailconfig.getProperty("admin.emailId"),propsEmailconfig.getProperty("admin.passwd"));
						}
					});
			
			
			
			String header="";
			List<String> headerList=new ArrayList<String>();
			headerList.add("WareHouse");
			headerList.add("Resource");
			headerList.add("ThresholdLimit");
			headerList.add("Current Available");
			
			for(ThresholdNotificationEmailVO thresholdData : thresholdDatas) {
				flag=true;
				ThresholdStatusVO thresholdStatusVO;
				
				thresholdStatusVO=map.get(thresholdData.getWareHouseName());
				if(thresholdStatusVO!=null){
					thresholdStatusVO.getRemarks().append("<tr><td>"+thresholdData.getWareHouseName()+"</td>"
							+"<td>"+thresholdData.getResourceName()+"</td>"+"<td>"+thresholdData.getThresholdValue()+"</td>"+"<td>"+thresholdData.getAvailable()+"</td></tr>");
					//thresholdStatusVO.getRemarks().append("\n");
				}else{
					
					thresholdStatusVO=new ThresholdStatusVO();
					thresholdStatusVO.setEmail(thresholdData.getEmailId());
					thresholdStatusVO.setWarehouse(thresholdData.getWareHouseName());
					thresholdStatusVO.setRemarks(new StringBuffer("<tr><td>"+thresholdData.getWareHouseName()+"</td>"
					+"<td>"+thresholdData.getResourceName()+"</td>"+"<td>"+thresholdData.getThresholdValue()+"</td>"+"<td>"+thresholdData.getAvailable()+"</td></tr>"));
					map.put(thresholdData.getWareHouseName(), thresholdStatusVO);
				}
			}
			
			
				for(ThresholdStatusVO thresholdStatusVO:map.values() ){
					
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(propsEmailconfig.getProperty("admin.emailId")));
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(thresholdStatusVO.getEmail()));
					message.setSentDate(new Date());
					message.setSubject("Resource Threshold Limit Status Report");
					StringBuffer body=new StringBuffer();
					body.append("<html>");
					body.append("Hello Owner,"+ "</br></br>");
					body.append("<table border=\"1\">");
					body.append("<tr>");
					for(String str:headerList){
						body.append("<td>");
						body.append(str);
						body.append("</td>");
					}
					body.append("</tr>");
					
					body.append(thresholdStatusVO.getRemarks().toString());
					body.append("</table>");
					body.append("</html>");
					message.setContent(body.toString(), "text/html");
					Transport.send(message);
				}
				
				
				
			//	message.setText(body.toString());
				
				Logger.logTrace("Inside Util class:","processThresholdNotification() Completed");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public static boolean isThreshold(ConfigureThresholdData thresholdData,int total,int available) {
		
		
		boolean result = false;
		if(thresholdData.getThresholdType().equals("Absolute")) {
			if(available <= thresholdData.getThresholdValue()) {
				result = true;
			}
		} else if(thresholdData.getThresholdType().equals("Percentage")) {
			Long absValue = (thresholdData.getThresholdValue()*100)/total;
			if(available<= absValue) {
				result = true;
			}
		} 
		Logger.logTrace("WarehouseUtil","isThreshold called result:::"+result);
		return result;
		
	}

	public static NotificationData convertThresholdVO(
			ConfigureThresholdData thresholdData,String resourceName, int available,String central,String orderNo) {
		
		NotificationData notificationData = new NotificationData();
		Map<String, String> map = new HashMap<String, String>();
		
//		emailVO.setWareHouseId(thresholdData.getWarehousedata().getWarehouseId());
		map.put(NotificationConstants.CPE_WAREHOUSE_NAME,thresholdData.getWarehousedata().getName());
		map.put(NotificationConstants.CPE_THRESHOLD_LIMIT,thresholdData.getThresholdValue()+"");
		map.put(NotificationConstants.CPE_AVAILABLE_QUANTITY, ""+available);
		map.put(NotificationConstants.CPE_RESOURCE_NAME, resourceName);
		map.put(NotificationConstants.CPE_ORDER_NUMBER, orderNo);
		
		if(thresholdData.getEmail()!=null) {
			notificationData.setToEmails(Arrays.asList(thresholdData.getWarehousedata().getEmailId(),central,thresholdData.getEmail()));
		} else {
			notificationData.setToEmails(Arrays.asList(thresholdData.getWarehousedata().getEmailId(),central));
		}
		
		notificationData.setAlias(NotificationConstants.RESOURCE_THRESHOLD);
		notificationData.setValueMap(map);
		
		return notificationData;
	}
	public static List<ResourceTypeData> getResourceTypeDatas(CachedRowSetImpl cachedRowSetImpl){
		
		List<ResourceTypeData> resourceTypeDatas=new ArrayList<ResourceTypeData>();
		CachedRowSet rs=cachedRowSetImpl;
		try{
		while(rs.next()){
			ResourceTypeData resourceTypeData=new ResourceTypeData();
			resourceTypeData.setResourceTypeId(rs.getLong("RESOURCETYPEID"));
			resourceTypeData.setName(rs.getString("NAME"));
			resourceTypeDatas.add(resourceTypeData);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resourceTypeDatas;
	}
	public static Properties readEmailProperty(String filename){
		
		Properties  props = new Properties();
		try{
			ResourceBundle rb = ResourceBundle.getBundle(filename);
			Enumeration <String> keys = rb.getKeys();
		
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				if(key.contains("smtp.host")){
					props.put("smtp.host", rb.getString(key));
				}else if(key.contains("smtp.port")){
					props.put("smtp.port", rb.getString(key));
				}else if(key.contains("admin.emailId")){
					props.put("admin.emailId", rb.getString(key));
				}else{
					props.put("admin.passwd", rb.getString(key));
				}
				
				String value = rb.getString(key);
				System.out.println(key + ":::: " + value);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return props;
	}

	public static List<WareHouseSummaryVO> convertToWareHouseSummaryVO(List<InventoryData> inventoryDatas) {
		List<WareHouseSummaryVO> summaryVOs = new ArrayList<WareHouseSummaryVO>();
		
		Map<Long, WareHouseSummaryVO> map = new HashMap<Long, WareHouseSummaryVO>();
		
		List<String> notAllowedForCentral = new ArrayList<String>();
		notAllowedForCentral.add("Reserved");
		notAllowedForCentral.add("Allocated");
		notAllowedForCentral.add("Delivered");
		notAllowedForCentral.add("Recovered");
		
		for(InventoryData inventoryData : inventoryDatas) {
			WareHouseSummaryVO summaryVO = null;
			
			if(map.containsKey(inventoryData.getItemId())) {
				summaryVO = map.get(inventoryData.getItemId());
				Map<String, Long> mapStatus = summaryVO.getStatusCount();
				if(mapStatus!=null) {
					if(mapStatus.containsKey(inventoryData.getStatusData().getName())) {
						Long count = mapStatus.get(inventoryData.getStatusData().getName());
						mapStatus.put(inventoryData.getStatusData().getName(), count+1);
						summaryVO.setStatusCount(mapStatus);
						map.put(inventoryData.getItemId(), summaryVO);
					} else {
						mapStatus.put(inventoryData.getStatusData().getName(), 1L);
						summaryVO.setStatusCount(mapStatus);
						map.put(inventoryData.getItemId(), summaryVO);
					}
				} else {
					mapStatus = new HashMap<String, Long>();
					mapStatus.put(inventoryData.getStatusData().getName(), 1L);
					summaryVO.setStatusCount(mapStatus);
					map.put(inventoryData.getItemId(), summaryVO);
				}
				
			} else {
				summaryVO  = new WareHouseSummaryVO();
				map.put(inventoryData.getItemId(), summaryVO);
				Map<String, Long> mapStatus = new HashMap<String, Long>();
				mapStatus.put(inventoryData.getStatusData().getName(), 1L);
				summaryVO.setStatusCount(mapStatus);
				summaryVO.setWareHouseId(inventoryData.getWarehousedata().getWarehouseId());
				summaryVO.setWareHouseName(inventoryData.getWarehousedata().getName());
				summaryVO.setWareHouseType(inventoryData.getWarehousedata().getWarehouseTypeData().getName());
				summaryVO.setResourceName(inventoryData.getItemData().getName());
				summaryVO.setModel(inventoryData.getItemData().getModelnumber());
				summaryVO.setVendor(inventoryData.getItemData().getVendor());
				if(inventoryData.getItemData().getResourceType()!=null) {
					summaryVO.setResourceType(inventoryData.getItemData().getResourceType().getName());
				}
				if(inventoryData.getItemData().getResourceSubTypeData()!=null) {
					summaryVO.setResourceSubType(inventoryData.getItemData().getResourceSubTypeData().getName());
				}
				map.put(inventoryData.getItemId(), summaryVO);
			}
			
			
		}
		
		for(Entry<Long, WareHouseSummaryVO> entry : map.entrySet()) {
			
			Long total = 0L;
			for(Entry<String,Long> entryStatus : entry.getValue().getStatusCount().entrySet()) {
				if(entry.getValue().getWareHouseType().equals("Central")) {
					if(!notAllowedForCentral.contains(entryStatus.getKey())) {
						total = total + entryStatus.getValue();
					}
				} else {
					total = total + entryStatus.getValue();
				}
			}
			
			WareHouseSummaryVO wareHouse = entry.getValue();
			wareHouse.getStatusCount().put("Total", total);
			
			summaryVOs.add(wareHouse);
		}
		
		
		
		
		return summaryVOs;
	}
}
