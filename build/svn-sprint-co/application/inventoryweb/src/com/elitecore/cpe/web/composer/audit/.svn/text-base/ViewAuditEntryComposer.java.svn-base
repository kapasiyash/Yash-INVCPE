package com.elitecore.cpe.web.composer.audit;


import java.util.List;

import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.audit.ViewAuditEntryVO;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class ViewAuditEntryComposer extends BaseModuleViewComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConfigureAuditBD auditBD;
	private List<ViewAuditEntryVO> auditEntryList;
	private Rows rows;
	private Row rowTable;
	
	
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		auditBD = new ConfigureAuditBD(getBDSessionContext());
		try {
			auditEntryList = auditBD.findAuditEntryDataById(getViewEntityId());
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		populateFields();
		
	}
	
	
	private void populateFields() {
		if(auditEntryList != null && !auditEntryList.isEmpty()) {
			rowTable = new Row();
			Label tablelabel = new Label(AuditConstants.TABLE);
			Label fieldlabel = new Label(AuditConstants.FIELD);
			Label oldlabel = new Label(AuditConstants.OLDVALUE);
			Label newlabel = new Label(AuditConstants.NEWVALUE);
			Label blanklabel = new Label();
			tablelabel.setStyle("font-weight:bold;");
			fieldlabel.setStyle("font-weight:bold;");
			oldlabel.setStyle("font-weight:bold;");
			newlabel.setStyle("font-weight:bold;");
			
			
			rowTable.appendChild(tablelabel);
			rowTable.appendChild(fieldlabel);
			rowTable.appendChild(oldlabel);
			rowTable.appendChild(newlabel);
			rowTable.appendChild(blanklabel);
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
				rowTable.appendChild(new Label());
				rows.appendChild(rowTable);
				
			}
		}
		
	}

	

}
