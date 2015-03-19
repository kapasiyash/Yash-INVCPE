package com.elitecore.cpe.web.composer.audit;

import java.sql.Timestamp;
import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.system.audit.SearchWsAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.ViewWsAuditEntryVO;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;

public class ViewWSAuditComposer extends BaseCPEViewComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private Window viewWsAuditWin;
	//private Grid viewwsauditgrid;
	private Label lblEntityName,lblStatus,lblResponseCode,lblResponseMessage,lblProcessedDate,lblInputData,lblOutputData;
	private SearchWsAuditVO auditVO;

	@Override
	protected List<ActionMenuItem> getActionItemList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<ActionMenuItem> getViewItemList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		if(arg.containsKey(SearchWsAuditComposer.SEARCH_AUDIT_COMPOSER_REF)) {
			auditVO  = (SearchWsAuditVO) arg.get(SearchWsAuditComposer.SEARCH_AUDIT_COMPOSER_REF);
		}
		
		ConfigureAuditBD auditBD = new ConfigureAuditBD(getBDSessionContext());
		
		try {
			
			ViewWsAuditEntryVO data = auditBD.findWsAuditDataById(auditVO.getWsAuditId());
			System.out.println(data);
			if(data!=null) {
				populateData(data);
			}
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
		
	}

	private void populateData(ViewWsAuditEntryVO data) {
		
		lblEntityName.setValue(GeneralUtility.displayValueIfNullOrEmpty(data.getEntityTypeName()));
		lblStatus.setValue(GeneralUtility.displayValueIfNullOrEmpty(data.getStatus()));
		lblResponseMessage.setValue(GeneralUtility.displayValueIfNullOrEmpty(data.getResponseMessage()));
		lblResponseCode.setValue(GeneralUtility.displayValueIfNullOrEmpty(data.getResponseCode()+""));
		lblInputData.setValue(GeneralUtility.displayValueIfNullOrEmpty(data.getInputData()));
		lblOutputData.setValue(GeneralUtility.displayValueIfNullOrEmpty(data.getOutputData()));
		lblProcessedDate.setValue(GeneralUtility.displayINDateTimeFormat(new Timestamp(data.getProcessedDate().getTime())));
		
	}

}
