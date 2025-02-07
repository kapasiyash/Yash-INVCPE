package com.elitecore.cpe.web.configuration.notification;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.notification.DocumentTemplateDetailWrapperdata;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.data.notification.MessageTagWrapperData;
import com.elitecore.cpe.bl.data.notification.SMSDocumentTemplateDetailWrapperData;
import com.elitecore.cpe.bl.delegates.notification.NotificationBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.configuration.notification.CheckValidDateForTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.NotificationCategoryVO;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.utils.MessageUtility;
/**
 * 
 * @author Yash.Kapasi
 *
 */
public class CreateNotifiactionTemplateComposer extends BaseModuleComposer {

	private static final long serialVersionUID = 1L;
	private Vlayout page1,page2,page3;
	private Textbox txtName,txtDescription,txtSubject,txtAreaSMS;
	private CKeditor emailCKEditor;
	private Datebox validFrom;
	private Datebox validTo;
	private Button btnFinish2;
	private Combobox docCategoryList,comboMessageTagEmail,comboMessageTagSMS,comboEmailTemplate,comboSMSTemplate,comboMessageTagEmailForSubject;
	DocumentTemplateWrapperdata templateWrapperdata = null;
	List<DocumentTemplateDetailWrapperdata> templateDetailWrapperdatas = null;
	List<SMSDocumentTemplateDetailWrapperData> smsDocumentTemplateDetailWrapperDatas = null;
	
	@Override
	public void afterCompose(Window comp) {
		
		populatedata();

		
		
		docCategoryList.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				if(docCategoryList.getSelectedItem()!=null) {
					NotificationCategoryVO comboData = docCategoryList.getSelectedItem().getValue();
					if(!comboData.isEmail()) {
						comboEmailTemplate.setValue("No");
						comboEmailTemplate.setDisabled(true);
					} else {
//						comboEmailTemplate.setValue("");
						comboEmailTemplate.setDisabled(false);
					}
					
					if(!comboData.isSms()) {
						comboSMSTemplate.setValue("No");
						comboSMSTemplate.setDisabled(true);
					} else {
//						comboSMSTemplate.setValue("");
						comboSMSTemplate.setDisabled(false);
					}
				}
				
			}
		});
		
		comboMessageTagEmail.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				MessageTagWrapperData wrapperData = (MessageTagWrapperData)comboMessageTagEmail.getSelectedItem().getValue();
				insertData(wrapperData.getMessageTag());
				
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
				MessageTagWrapperData wrapperData = (MessageTagWrapperData)comboMessageTagSMS.getSelectedItem().getValue();
				String txtArae = txtAreaSMS.getValue();
				txtAreaSMS.setValue(txtArae+wrapperData.getMessageTag());
				
			}

		});
		
		validFrom.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				// TODO Auto-generated method stub
				if (validFrom.getValue() != null){
					//validto.setConstraint("");
					//validto.setValue(null);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					validTo.setConstraint("after "+sdf.format(validFrom.getValue())+": Please enter valid to date");
				}
			}
		});
		
	}
		
	

	private void populatedata() {
		NotificationBD notificationBD = new NotificationBD(getBDSessionContext());
		try {
			List<NotificationCategoryVO> categoryComboDatas = notificationBD.findAllTemplateCategory();
//			List<ComboBoxData> boxDatas = notificationBD.findAllTemplateCategoryFromEngine();
			
			if(categoryComboDatas!=null && !categoryComboDatas.isEmpty()) {
				docCategoryList.setModel(new ListModelList<NotificationCategoryVO>(categoryComboDatas));
				docCategoryList.setItemRenderer(new NotificationItemDataRenderer());
			}
			
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		
		
	}


	public static class NotificationItemDataRenderer implements ComboitemRenderer<NotificationCategoryVO>{

		private Combobox combobox ;
		private Long id;
		

		public NotificationItemDataRenderer() {
			super();
		}
		public NotificationItemDataRenderer(Combobox combobox, Long id) {
			super();
			this.combobox = combobox;
			this.id = id;
		}


		@Override
		public void render(Comboitem comboItem, NotificationCategoryVO comboData,int index) throws Exception {
			comboItem.setValue(comboData);
			comboItem.setLabel(comboData.getName());
			if(combobox !=null && id !=null && id.equals(comboData.getId())){
				combobox.setSelectedItem(comboItem);
			}
		}

	}
	public void onClick$btnNext1(Event event) {
		
		templateWrapperdata = new DocumentTemplateWrapperdata();
		templateDetailWrapperdatas = new ArrayList<DocumentTemplateDetailWrapperdata>();
		smsDocumentTemplateDetailWrapperDatas = new ArrayList<SMSDocumentTemplateDetailWrapperData>();
		
		NotificationBD notificationBD = new NotificationBD(getBDSessionContext());
		if(docCategoryList.getSelectedItem()!=null) {
			NotificationCategoryVO comboData = docCategoryList.getSelectedItem().getValue();
			System.out.println(validTo);
			if(validTo.getValue()!=null){
			} else {
				MessageUtility.failureInformation("ERROR", "Please Enter Valid To Date");
				return;
			}
			
			try {
				templateWrapperdata.setDocumentCategoryId(comboData.getId());
				List<MessageTagWrapperData> messageComboData= notificationBD.findMessageTagByDocCat(comboData.getId());
				if(messageComboData!=null && !messageComboData.isEmpty()) {
					comboMessageTagEmail.setModel(new ListModelList<MessageTagWrapperData>(messageComboData));
					comboMessageTagEmail.setItemRenderer(new MessageItemDataRenderer());
					comboMessageTagSMS.setModel(new ListModelList<MessageTagWrapperData>(messageComboData));
					comboMessageTagSMS.setItemRenderer(new MessageItemDataRenderer());
					
					comboMessageTagEmailForSubject.setModel(new ListModelList<MessageTagWrapperData>(messageComboData));
					comboMessageTagEmailForSubject.setItemRenderer(new MessageItemDataRenderer());
					
				}
			} catch (SearchBLException e) {
				e.printStackTrace();
			} catch (TechnicalException e) {
				e.printStackTrace();
			}
		}
		
		preparedata(templateWrapperdata);
		
		try {
			
			Calendar calendarFrom = Calendar.getInstance();
			calendarFrom.setTime(templateWrapperdata.getValidFormDate());
			calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
			calendarFrom.set(Calendar.MINUTE, 0);
			calendarFrom.set(Calendar.SECOND, 0);
			
			Calendar calendarTo = Calendar.getInstance();
			if(templateWrapperdata.getValidToDate()!=null){
				calendarTo.setTime(templateWrapperdata.getValidToDate());
				calendarTo.set(Calendar.HOUR_OF_DAY, 23);
				calendarTo.set(Calendar.MINUTE, 59);
				calendarTo.set(Calendar.SECOND, 59);
				CheckValidDateForTemplateVO checkValidDateForTemplateVO=new CheckValidDateForTemplateVO();
				checkValidDateForTemplateVO.setDocumentCategoryId(templateWrapperdata.getDocumentCategoryId());
				checkValidDateForTemplateVO.setValidFormDate(new Timestamp(calendarFrom.getTimeInMillis()));
				checkValidDateForTemplateVO.setValidToDate(new Timestamp(calendarTo.getTimeInMillis()));
				checkValidDateForTemplateVO.setFlag("Create");
				String isPresent = notificationBD.checkValidDateForTemplate(checkValidDateForTemplateVO);

				//String isPresent = notificationBD.checkValidDateForTemplate(templateWrapperdata.getDocumentCategoryId(),new Timestamp(calendarFrom.getTimeInMillis()),new Timestamp(calendarTo.getTimeInMillis()));
				if(isPresent!=null) {
					MessageUtility.failureInformation("Error", isPresent);
					return;
				}	
			}

		} catch (TechnicalException e) {
			e.printStackTrace();
			return;
		}
		
		
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
	
	
	private void preparedata(DocumentTemplateWrapperdata templateWrapperdata) {
		
		
		if(txtName.getValue()!=null && !txtName.getValue().isEmpty()) {
			templateWrapperdata.setName(txtName.getValue());
		}
		if(txtDescription.getValue()!=null && !txtDescription.getValue().isEmpty()) {
			templateWrapperdata.setDescription(txtDescription.getValue());
		}
		if(validFrom.getValue()!=null) {
			templateWrapperdata.setValidFormDate(new Timestamp(validFrom.getValue().getTime()));
		}
		if(validTo.getValue()!=null) {
			templateWrapperdata.setValidToDate(new Timestamp(validTo.getValue().getTime()));
		}
	}



	private void insertData(String data) {
		Clients.evalJavaScript("populateCkEditor('"+data+"')");
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
			notificationBD.createDocumentTemplate(templateWrapperdata);
			MessageUtility.successInformation("Success", "Document Template Successfully Created");
			
			resetComponents(txtName,txtName, txtDescription,txtSubject,txtAreaSMS,docCategoryList,comboMessageTagEmail,comboMessageTagSMS,comboEmailTemplate,comboSMSTemplate,validFrom,validTo);
			emailCKEditor.setValue("");
			templateWrapperdata = null;
			templateDetailWrapperdatas = null;
			smsDocumentTemplateDetailWrapperDatas = null;
			page1.setVisible(true);
			page2.setVisible(false);
			page3.setVisible(false);
			
		} catch (CreateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", e.getMessage());
		} catch (TechnicalException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
	}

	private void prepareEmailData() {
		DocumentTemplateDetailWrapperdata detailWrapperdata = new DocumentTemplateDetailWrapperdata();
		if(txtSubject.getValue()!=null && !txtSubject.getValue().isEmpty()) {
			detailWrapperdata.setSubject(txtSubject.getValue());
		}
		if(emailCKEditor.getValue()!=null && !emailCKEditor.getValue().isEmpty()) {
			detailWrapperdata.setMimeType("ASCII");
			detailWrapperdata.setTemplate(emailCKEditor.getValue().getBytes());
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
