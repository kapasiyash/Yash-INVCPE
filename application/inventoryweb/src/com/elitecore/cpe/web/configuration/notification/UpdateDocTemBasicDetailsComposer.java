package com.elitecore.cpe.web.configuration.notification;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.delegates.notification.NotificationBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.configuration.notification.CheckValidDateForTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.ViewDocumentTemplateVO;
import com.elitecore.cpe.util.GeneralUtility;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class UpdateDocTemBasicDetailsComposer extends BaseModuleViewComposer {

	private static final long serialVersionUID = 1L;
	
	private Textbox txtDescription;
	private Datebox validfrom,validto;
	private Hlayout updateDocTemplate;
	private NotificationBD notificationBD;
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		 notificationBD = new NotificationBD(getBDSessionContext());
		
		try {
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 00);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.MILLISECOND, 00);
			
			
			ViewDocumentTemplateVO basicVO =notificationBD.findDocumentViewData(getViewEntityId());
			txtDescription.setValue(basicVO.getDescription());
			
			System.out.println(basicVO.getValidFrom().getTime());
			System.out.println(calendar.getTime().getTime());
			
			if(basicVO.getValidFrom().before(calendar.getTime())) {
				validfrom.setDisabled(true);
			} else {
				validfrom.setConstraint("no empty,no past: Please select valid from date.");
			}

			validfrom.setValue(basicVO.getValidFrom());
			validto.setValue(basicVO.getValidTo());
			
			validto.setConstraint("no empty,no past : Please select valid to date.Date must be greater than or equal to current Date");
			
	
			validfrom.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					// TODO Auto-generated method stub
					if (validfrom.getValue() != null){
						//validto.setConstraint("");
						//validto.setValue(null);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						validto.setConstraint("after "+sdf.format(validfrom.getValue())+": Please enter valid to date");
					}
				}
			});
	} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		} 
	}
	
	public void onClick$btnUpdate(Event event) {
		
		if((validfrom.getValue().after(validto.getValue()))){
			MessageUtility.failureInformation("Invalid date", "Valid from date cannot be after valid to date.");
			return;
		}	
		
		 notificationBD = new NotificationBD(getBDSessionContext());
		
		DocumentTemplateWrapperdata wrapperdata = new DocumentTemplateWrapperdata();
		try {
			prepareData(wrapperdata);
			//Added By Rinkal --Start
			Calendar calendarFrom = Calendar.getInstance();
			calendarFrom.setTime(wrapperdata.getValidFormDate());
			calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
			calendarFrom.set(Calendar.MINUTE, 0);
			calendarFrom.set(Calendar.SECOND, 0);
			
			Calendar calendarTo = Calendar.getInstance();
			calendarTo.setTime(wrapperdata.getValidToDate());
			calendarTo.set(Calendar.HOUR_OF_DAY, 23);
			calendarTo.set(Calendar.MINUTE, 59);
			calendarTo.set(Calendar.SECOND, 59);
			CheckValidDateForTemplateVO checkValidDateForTemplateVO=new CheckValidDateForTemplateVO();
			checkValidDateForTemplateVO.setDocumentCategoryId(wrapperdata.getDocumentCategoryId());
			checkValidDateForTemplateVO.setFlag("Update");
			checkValidDateForTemplateVO.setValidFormDate(new Timestamp(calendarFrom.getTimeInMillis()));
			checkValidDateForTemplateVO.setValidToDate(new Timestamp(calendarTo.getTimeInMillis()));
			checkValidDateForTemplateVO.setDocumentId(getViewEntityId());
			//commented for Date Validation --String isPresent = notificationBD.checkValidDateForTemplate(wrapperdata.getDocumentCategoryId(),new Timestamp(calendarFrom.getTimeInMillis()),new Timestamp(calendarTo.getTimeInMillis()));
			String isPresent = notificationBD.checkValidDateForTemplate(checkValidDateForTemplateVO);

			if(isPresent!=null) {
				MessageUtility.failureInformation("Error", isPresent);
				return;
			}
			//--End
			notificationBD.updateDocumentTemplateBasicDetails(wrapperdata);
			MessageUtility.successInformation("Success", "Document Basic Details has been Updated Successfully");
			resetComponents(txtDescription,txtDescription,validfrom,validto);
			updateDocTemplate.detach();
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
		} catch (UpdateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", e.getMessage());
		} catch (TechnicalException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
	}

	private void prepareData(DocumentTemplateWrapperdata wrapperdata) {
		wrapperdata.setDocumentId(getViewEntityId());
		try {
			DocumentTemplateWrapperdata data =notificationBD.findDocumentTemplateData(getViewEntityId());
			wrapperdata.setDocumentCategoryId(data.getDocumentCategoryId());
		} catch (SearchBLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if(txtDescription.getValue()!=null && !txtDescription.getValue().isEmpty()) {
			wrapperdata.setDescription(txtDescription.getValue());
		}
		if(validfrom.getValue()!=null) {
			wrapperdata.setValidFormDate(new Timestamp(validfrom.getValue().getTime()));
		}
		if(validto.getValue()!=null) {
			wrapperdata.setValidToDate(new Timestamp(validto.getValue().getTime()));
		}
		
	}
	

}
