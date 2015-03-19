package com.elitecore.cpe.web.configuration.notification;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.delegates.notification.NotificationBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.configuration.notification.ViewDocumentTemplateVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer.ActionMenuItem;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class ViewDocumentTemplateComposer extends BaseCPEViewComposer {


	private static final long serialVersionUID = 1L;
	private static final String MODULE = "UPDATE-DOCUMENT-TEMPLATE-BASICDETAILS";
	private Label lbName,lbDescription,lbCategory,lbValidFrom,lbValidTo;
	private Label lbCreatedDate;
	private Label lbCreatedBy;
	private Label lbUpdatedBy;
	private Label lbUpdateDate;
	private LinkedList<ActionMenuItem> actionItemList;
	//private LinkedList<ActionMenuItem> viewItemList;
	
	@Override
	protected List<ActionMenuItem> getActionItemList() {
		actionItemList = new LinkedList<ActionMenuItem>();
		
		
		
		if(isPermittedAction(ActionAlias.UPDATE_DOCUMENT_TEMPLATE_BASICDETAILS)) {
			SystemActionData actionUpdateLookup =  getSystemActionData(ActionAlias.UPDATE_DOCUMENT_TEMPLATE_BASICDETAILS);
			if(actionUpdateLookup!=null){
				Logger.logTrace(MODULE, actionUpdateLookup.toString());
				ActionMenuItem menuItemLookup = new ActionMenuItem(actionUpdateLookup.getActionAlias(), actionUpdateLookup.getName(), actionUpdateLookup.getZulPageUrl(),MENU_ITEM);
				actionItemList.add(menuItemLookup);
			}else{
				Logger.logError(MODULE, "Getting null");
			}
		}
		
		if(isPermittedAction(ActionAlias.UPDATE_DOCUMENT_TEMPLATE)) {
			SystemActionData actionUpdateLookup =  getSystemActionData(ActionAlias.UPDATE_DOCUMENT_TEMPLATE);
			if(actionUpdateLookup!=null){
				Logger.logTrace(MODULE, actionUpdateLookup.toString());
				ActionMenuItem menuItemLookup = new ActionMenuItem(actionUpdateLookup.getActionAlias(), actionUpdateLookup.getName(), actionUpdateLookup.getZulPageUrl(),MENU_ITEM);
				actionItemList.add(menuItemLookup);
			}else{
				Logger.logError(MODULE, "Getting null");
			}
		}
		
		return actionItemList;
	}

	@Override
	protected List<ActionMenuItem> getViewItemList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshView() {
		fetchViewEntity();
		
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
			fetchViewEntity();
		
	}

	private void fetchViewEntity() {
		NotificationBD notificationBD = new NotificationBD(getBDSessionContext());
		try {
			
			ViewDocumentTemplateVO templateVO = notificationBD.findDocumentViewData(getViewEntityId());
			populateData(templateVO);
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}
	
	
	private void populateData(ViewDocumentTemplateVO templateVO) {
		
		SimpleDateFormat dateFormat  = new SimpleDateFormat(getDateFormat());
		lbName.setValue(templateVO.getName());
		lbDescription.setValue(templateVO.getDescription());
		lbCategory.setValue(templateVO.getCategory());
		lbValidFrom.setValue(dateFormat.format(templateVO.getValidFrom()));
		lbValidTo.setValue(dateFormat.format(templateVO.getValidTo()));
		lbCreatedBy.setValue(templateVO.getCreatedBy());
		lbUpdatedBy.setValue(templateVO.getLastModifiedBy());
		lbCreatedDate.setValue(dateFormat.format(templateVO.getCreatedDate()));
		lbUpdateDate.setValue(dateFormat.format(templateVO.getLastModifiedDate()));
		
	}

}
