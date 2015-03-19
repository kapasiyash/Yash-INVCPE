package com.elitecore.cpe.web.configuration.notification;

import java.util.ArrayList;
import java.util.List;

import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import com.elitecore.cpe.bl.data.notification.DocumentTemplateDetailWrapperdata;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.data.notification.MessageTagWrapperData;
import com.elitecore.cpe.bl.data.notification.SMSDocumentTemplateDetailWrapperData;
import com.elitecore.cpe.bl.delegates.notification.NotificationBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;
/**
 * 
 * @author Yash.Kapasi
 *
 */
public class UpdateDocumentTemplateComposer extends BaseModuleViewComposer {


	private static final long serialVersionUID = 1L;
	private Vlayout page1,page2,page3;
	private Textbox txtSubject,txtAreaSMS;
	private CKeditor emailCKEditorUpdate;
	private Button btnFinish2;
	private Hlayout updateTemplateWin;
	private Combobox comboMessageTagEmail,comboMessageTagSMS,comboEmailTemplate,comboSMSTemplate,comboMessageTagEmailForSubject;
	DocumentTemplateWrapperdata templateWrapperdata = null;
	List<DocumentTemplateDetailWrapperdata> templateDetailWrapperdatas = null;
	List<SMSDocumentTemplateDetailWrapperData> smsDocumentTemplateDetailWrapperDatas = null;



	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		
		comboMessageTagEmail.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if(comboMessageTagEmail.getSelectedItem()!= null) {
					MessageTagWrapperData wrapperData = (MessageTagWrapperData)comboMessageTagEmail.getSelectedItem().getValue();
					insertData(wrapperData.getMessageTag());
				}
				
				
			}

		});
		comboMessageTagEmailForSubject.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				MessageTagWrapperData wrapperData = (MessageTagWrapperData)comboMessageTagEmailForSubject.getSelectedItem().getValue();
				Clients.evalJavaScript("insertAtCursor(document.getElementById('" + txtSubject.getUuid() + "'), '"+wrapperData.getMessageTag()+"')");
			}

		});
		
		
		comboMessageTagSMS.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(comboMessageTagSMS.getSelectedItem()!=null) {
					MessageTagWrapperData wrapperData = (MessageTagWrapperData)comboMessageTagSMS.getSelectedItem().getValue();
					String txtArae = txtAreaSMS.getValue();
					txtAreaSMS.setValue(txtArae+wrapperData.getMessageTag());
				}
				
				
			}

		});
		
		NotificationBD notificationBD = new NotificationBD(getBDSessionContext());
		try {
			
			templateWrapperdata = notificationBD.findDocumentTemplateData(getViewEntityId());
			if(templateWrapperdata!=null) {
				List<MessageTagWrapperData> messageComboData= notificationBD.findMessageTagByDocCat(templateWrapperdata.getDocumentCategoryId());
				if(messageComboData!=null && !messageComboData.isEmpty()) {
					comboMessageTagEmail.setModel(new ListModelList<MessageTagWrapperData>(messageComboData));
					comboMessageTagEmail.setItemRenderer(new MessageItemDataRenderer());
					comboMessageTagSMS.setModel(new ListModelList<MessageTagWrapperData>(messageComboData));
					comboMessageTagSMS.setItemRenderer(new MessageItemDataRenderer());
					
					comboMessageTagEmailForSubject.setModel(new ListModelList<MessageTagWrapperData>(messageComboData));
					comboMessageTagEmailForSubject.setItemRenderer(new MessageItemDataRenderer());
					
				}
				if(templateWrapperdata.getDocumentTemplateDetails()!=null && !templateWrapperdata.getDocumentTemplateDetails().isEmpty()) {
					comboEmailTemplate.setValue("Yes");
					txtSubject.setValue(templateWrapperdata.getDocumentTemplateDetails().get(0).getSubject());
					emailCKEditorUpdate.setValue(new String(templateWrapperdata.getDocumentTemplateDetails().get(0).getTemplate()));
				} else {
					comboEmailTemplate.setValue("No");
				}
				if(templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas()!=null && !templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas().isEmpty()) {
					comboSMSTemplate.setValue("Yes");
					txtAreaSMS.setValue(new String(templateWrapperdata.getSmsDocumentTemplateDetailWrapperDatas().get(0).getTemplate()));
				} else {
					comboSMSTemplate.setValue("No");
				}
			}
			
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void onClick$btnNext1(Event event) {
		
		
		templateDetailWrapperdatas = new ArrayList<DocumentTemplateDetailWrapperdata>();
		smsDocumentTemplateDetailWrapperDatas = new ArrayList<SMSDocumentTemplateDetailWrapperData>();
		
		if(comboEmailTemplate.getSelectedItem()!=null && comboSMSTemplate.getSelectedItem()!=null) {
			String comboEmailData = comboEmailTemplate.getSelectedItem().getValue();
			String comboSMSData = comboSMSTemplate.getSelectedItem().getValue();
			if(comboEmailData.equals("N") && comboSMSData.equals("Y")) {
				page1.setVisible(false);
				page2.setVisible(false);
				page3.setVisible(true);
				
			} else if(comboEmailData.equals("N") && comboSMSData.equals("N")) {
				MessageUtility.failureInformation("Error", "Select Atleast One Template");
			}else if(comboEmailData.equals("Y") && comboSMSData.equals("N")) {
				btnFinish2.setLabel("Finish");
				page1.setVisible(false);
				page2.setVisible(true);
				page3.setVisible(false);
			} else {
				page1.setVisible(false);
				page2.setVisible(true);
				page3.setVisible(false);
				
			}
		}
		
	}
	
	public void onClick$btnFinish2(Event event) {
		prepareEmailData();
		if(comboEmailTemplate.getSelectedItem()!=null && comboSMSTemplate.getSelectedItem()!=null) {
			String comboEmailData = comboEmailTemplate.getSelectedItem().getValue();
			String comboSMSData = comboSMSTemplate.getSelectedItem().getValue();
			if(comboEmailData.equals("Y") && comboSMSData.equals("N")) {
				submitData();
				
			} else {
				page1.setVisible(false);
				page2.setVisible(false);
				page3.setVisible(true);
			}
		} 
		
	}
	
	
	public void onClick$btnFinish3(Event event) {
		prepareSMSData();
		submitData();
	}
	
	private void submitData() {
		NotificationBD notificationBD = new NotificationBD(getBDSessionContext());
		try {
			notificationBD.updateDocumentTemplate(templateWrapperdata);
			MessageUtility.successInformation("Success", "Document Template Successfully Updated");
		} catch (UpdateBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		resetComponents(txtSubject,txtSubject,txtAreaSMS,comboMessageTagEmail,comboMessageTagSMS,comboEmailTemplate,comboSMSTemplate);
		emailCKEditorUpdate.setValue("");
		templateWrapperdata = null;
		templateDetailWrapperdatas = null;
		smsDocumentTemplateDetailWrapperDatas = null;
		updateTemplateWin.detach();
	}
	
	private void prepareEmailData() {
		DocumentTemplateDetailWrapperdata detailWrapperdata = new DocumentTemplateDetailWrapperdata();
		if(txtSubject.getValue()!=null && !txtSubject.getValue().isEmpty()) {
			detailWrapperdata.setSubject(txtSubject.getValue());
		}
		if(emailCKEditorUpdate.getValue()!=null && !emailCKEditorUpdate.getValue().isEmpty()) {
			detailWrapperdata.setMimeType("ASCII");
			
			detailWrapperdata.setTemplate(emailCKEditorUpdate.getValue().getBytes());
			templateDetailWrapperdatas.add(detailWrapperdata);
			templateWrapperdata.setDocumentTemplateDetails(templateDetailWrapperdatas);
		}
	}
	
	private void prepareSMSData() {
		
		SMSDocumentTemplateDetailWrapperData detailWrapperdata = new SMSDocumentTemplateDetailWrapperData();
		if(txtAreaSMS.getValue()!=null && !txtAreaSMS.getValue().isEmpty()) {
			detailWrapperdata.setMimeType("ASCII");
			
			detailWrapperdata.setTemplate(txtAreaSMS.getValue().getBytes());
			smsDocumentTemplateDetailWrapperDatas.add(detailWrapperdata);
			templateWrapperdata.setSmsDocumentTemplateDetailWrapperDatas(smsDocumentTemplateDetailWrapperDatas);
		}
	}
	
	private void insertData(String data) {
		Clients.evalJavaScript("populateCkEditorUpdate('"+data+"')");
//		Clients.evalJavaScript("insertAtCursor(document.getElementById('" + emailCKEditorUpdate.getUuid() + "'), '"+data+"')");
	}
	
	
	public void onClick$btnBack2(Event event) {
		page1.setVisible(true);
		page2.setVisible(false);
		page3.setVisible(false);
	}
	



	public void onClick$btnBack3(Event event) {
		
		if(comboEmailTemplate.getSelectedItem()!=null && comboSMSTemplate.getSelectedItem()!=null) {
			String comboEmailData = comboEmailTemplate.getSelectedItem().getValue();
			String comboSMSData = comboSMSTemplate.getSelectedItem().getValue();
			if(comboEmailData.equals("N") && comboSMSData.equals("Y")) {
				page1.setVisible(true);
				page2.setVisible(false);
				page3.setVisible(false);
				
			} else {
				page1.setVisible(false);
				page2.setVisible(true);
				page3.setVisible(false);
				
			}
		}
		
	}
	
	private static class MessageItemDataRenderer implements ComboitemRenderer<MessageTagWrapperData> {

		@Override
		public void render(Comboitem comboItem, MessageTagWrapperData comboData, int index)
				throws Exception {
			comboItem.setLabel(comboData.getMessageText());
			comboItem.setValue(comboData);
			
		}
		
	}

}
