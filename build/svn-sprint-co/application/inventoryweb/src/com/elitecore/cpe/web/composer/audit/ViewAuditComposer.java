package com.elitecore.cpe.web.composer.audit;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditEntryVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.constants.ActionAlias;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;



public class ViewAuditComposer extends BaseCPEViewComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "VIEW-AUDIT";
	
	private LinkedList<ActionMenuItem> actionItemList;
	private LinkedList<ActionMenuItem> viewItemList;
	private ConfigureAuditBD auditBD;
	private ViewAuditVO viewAuditVO;
	SimpleDateFormat dateFormat;
	private Label lbAuditDate,lbAuditAction,lbAuditReason,lbAuditUser,lbAuditIp,lbAuditType,lbAuditRemarks;
	private List<ViewAuditEntryVO> auditEntryList;
	private Rows rows;
	private Row rowTable;
	private Grid updateAuditEntryDetailgrid;
	private Div auditentrydetail;

	@Override
	protected List<ActionMenuItem> getActionItemList() {
		if (actionItemList == null) {
			actionItemList = new LinkedList<ActionMenuItem>();
			
		}
		return actionItemList;
	}

	@Override
	protected List<ActionMenuItem> getViewItemList() {
		if (viewItemList == null) {
			viewItemList = new LinkedList<ActionMenuItem>();
			
			if(isPermittedAction(ActionAlias.VIEW_AUDIT_ENTRY)) {
				//SystemActionData actionData = getBDSessionContext().getBLSession().getSystemAction(ActionAlias.VIEW_AUDIT_ENTRY);
			//	viewItemList.add(new ActionMenuItem(actionData.getActionAlias(), actionData.getName(), actionData.getZulPageUrl(),MENU_ITEM));
			}
//			viewItemList.add(new ActionMenuItem("View Audit Entry", "View Audit Entry", Pages.VIEW_AUDIT_ENTRY,MENU_ITEM));
//			viewItemList.add(new ActionMenuItem("VIEW_AUDIT_ENTRY", "View Audit Entry", Pages.VIEW_AUDIT_ENTRY,MENU_ITEM));
		}
		return viewItemList;
	}

	@Override
	public void refreshView() {
		fecthViewEntity();
		
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		Logger.logTrace(MODULE, "inside afterCompose");
		auditBD = new ConfigureAuditBD(getBDSessionContext());
		try {
		auditEntryList = auditBD.findAuditEntryDataById(getViewEntityId());
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		fecthViewEntity();
		
	}
	
	protected void fecthViewEntity() {
		try {
			SearchAuditVO auditWrapper=(SearchAuditVO)arg.get("auditWrapper");
			Logger.logTrace("Audit", "Audit Action in View:"+auditWrapper.getAuditAction());
			Logger.logTrace("Audit", "Audit Action in View Auditwrapper Object:"+auditWrapper.getAuditAction()+"  Type: "+auditWrapper.getAuditType());
			if(auditWrapper.getAuditType().equalsIgnoreCase("update")){
				Logger.logTrace("Audit", "Inside If block");
				updateAuditEntryDetailgrid.setVisible(true);
				auditentrydetail.setVisible(true);
			}
			dateFormat = new SimpleDateFormat(getDateTimeFormat());
			//dateFormat = new SimpleDateFormat(getDateFormat());
			auditBD = new ConfigureAuditBD(getBDSessionContext());
			Logger.logTrace(MODULE, getViewEntityId().toString());
			viewAuditVO = auditBD.findAuditDataById(getViewEntityId());
			populateFields();
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		} 

	}
	
	public void populateFields() {
		lbAuditDate.setValue(dateFormat.format(viewAuditVO.getAuditDate()));
		lbAuditAction.setValue(viewAuditVO.getAuditAction());
		lbAuditUser.setValue(viewAuditVO.getUserName());
		lbAuditReason.setValue(viewAuditVO.getReason());
		lbAuditIp.setValue(viewAuditVO.getIpAddress());
		lbAuditType.setValue(viewAuditVO.getAuditType());
		lbAuditRemarks.setValue(viewAuditVO.getRemarks());
		///////////////////////////////////////////////////////////
		if(auditEntryList != null && !auditEntryList.isEmpty()) {
			rowTable = new Row();
			//Label tablelabel = new Label(AuditConstants.TABLE);
			Label fieldlabel = new Label(AuditConstants.FIELD);
			Label oldlabel = new Label(AuditConstants.OLDVALUE);
			Label newlabel = new Label(AuditConstants.NEWVALUE);
		
			//tablelabel.setStyle("font-weight:bold;");
			fieldlabel.setStyle("font-weight:bold;");
			oldlabel.setStyle("font-weight:bold;");
			newlabel.setStyle("font-weight:bold;");
			
			
			//rowTable.appendChild(tablelabel);
			rowTable.appendChild(fieldlabel);
			rowTable.appendChild(oldlabel);
			rowTable.appendChild(newlabel);
		//	rowTable.appendChild(blanklabel);
			rows.appendChild(rowTable);
			
			for(ViewAuditEntryVO entryVO : auditEntryList) {
				rowTable = new Row();
				//rowTable.appendChild(new Label(entryVO.getTableName()));
				rowTable.appendChild(new Label(entryVO.getFieldName()));
				if(entryVO.getOldValue() == null || entryVO.getOldValue().isEmpty()) {
					rowTable.appendChild(new Label(AuditConstants.NOVALUE));
				} else {
					rowTable.appendChild(new Label(entryVO.getOldValue()));
				}
				if(entryVO.getNewValue() == null || entryVO.getNewValue().isEmpty()) {
					rowTable.appendChild(new Label(AuditConstants.NOVALUE));
				} else {
					rowTable.appendChild(new Label(entryVO.getNewValue()));
				}
				//rowTable.appendChild(new Label());
				rows.appendChild(rowTable);
				
			}
		}
		////////////////////////////////////////////////////////////////////
	}
	
	public void refreshSearchGrid(Event event){
		if(arg.containsKey(SearchAuditComposer.SEARCH_AUDIT_COMPOSER_REF)){
//			SearchAuditComposer searchAuditComposer = (SearchAuditComposer) arg.get(SearchAuditComposer.SEARCH_AUDIT_COMPOSER_REF);
			//searchAuditComposer.refreshSearchGrid(event);
			
		}
	}

}
