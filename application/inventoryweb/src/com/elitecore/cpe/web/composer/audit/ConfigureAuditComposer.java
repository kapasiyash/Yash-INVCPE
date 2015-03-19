package com.elitecore.cpe.web.composer.audit;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.audit.ConfigureAuditWrapperData;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class ConfigureAuditComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 private CreateTreeComposer treeComposer;
//	 private Window auditconfiguration; 
	 private Div viewActionContent;
	 
	 
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		Logger.logTrace("Audit", "Inside afterCompose [JM]");
		Map<String,Object> typeMap=new HashMap<String,Object>();
		typeMap.put(CreateTreeComposer.MODE, "Create");
		typeMap.put(CreateTreeComposer.COMPOSER_REF, this);
		Executions.createComponents(Pages.CREATE_TREE_AUDIT, viewActionContent,typeMap);
//		List<Long> list=treeComposer.getActionIds();
		
	
	}
	
	public void onClick$btnUpdate(Event event) {
		ConfigureAuditWrapperData wrapperData = new ConfigureAuditWrapperData(); 
		ConfigureAuditBD auditBD = new ConfigureAuditBD(getBDSessionContext());
		try {
			wrapperData.setAuditableActions(treeComposer.getActionIds());
			wrapperData.setAuditableActionsList(treeComposer.getAuditableActionsList());
			auditBD.updateSystemAction(wrapperData.getAuditableActionsList());
			
			MessageUtility.successInformation("Success", "Audit Configuration Updated successfully");
		
		//	auditconfiguration.detach();
		
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
		} catch (UpdateBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}
	
	public void setTreeComposer(CreateTreeComposer createTreeComposer) {
		this.treeComposer= createTreeComposer;
	}
	
}
