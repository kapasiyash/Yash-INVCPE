package com.elitecore.cpe.web.system.agent;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.system.agent.PreUpdateAgentParamVO;
import com.elitecore.cpe.bl.vo.system.agent.UpdateAgentParameterVO;
import com.elitecore.cpe.util.expr.cron.exception.InvalidPatternException;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseComposer.ComboBoxItemDataRenderer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.InvalidDataException;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class UpdateAgentParameterComposer extends BaseModuleViewComposer {


	private static final long serialVersionUID = 1L;
	private static final String MODULE = "UPDATE-AGENT-PARAM";
	private Hlayout updatesagentparam;
	private Textbox txtReason;
	private Combobox combooverrulemechunksize,combooverruleechunksize;
	private Longbox longMEConCurrencyLimit,longEConCurrencyLimit,longMEChunkSize,longMEChunkSizeLimit,
	longEChunkSize,longEChunkSizeLimit;
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		List<ComboBoxData> comboTypeData = new ArrayList<ComboBoxData>();
		comboTypeData.add(new ComboBoxData("Y","Y"));
		comboTypeData.add(new ComboBoxData("N","N"));
		
		combooverrulemechunksize.setModel(new ListModelList<ComboBoxData>(comboTypeData));
		combooverrulemechunksize.setItemRenderer(new ComboBoxItemDataRenderer());
		
		combooverruleechunksize.setModel(new ListModelList<ComboBoxData>(comboTypeData));
		combooverruleechunksize.setItemRenderer(new ComboBoxItemDataRenderer());
		
		SystemAgentBD systemAgentBD = new SystemAgentBD(getBDSessionContext());
		updatesagentparam = comp;
		try {
			PreUpdateAgentParamVO preUpdateData  = systemAgentBD.preUpdateAgentParam(getStrViewEntityId());
			if(preUpdateData != null){
				populateData(preUpdateData);
			}
		} catch (TechnicalException e) {
			e.printStackTrace();
		} catch (SearchBLException e) {
			e.printStackTrace();
		}	
	}
	
	public void onClick$btnUpdate(Event event) throws InvalidPatternException {
		
		SystemAgentBD systemAgentBD = new SystemAgentBD(getBDSessionContext());
		UpdateAgentParameterVO updateAgentParameterVO = null;
		try {
			updateAgentParameterVO = prepareUpdateData();
			
			systemAgentBD.updateAgentParam(updateAgentParameterVO);
			MessageUtility.successInformation("Success", "Agent Parameters Updated Successfully");
			updatesagentparam.detach();
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
			
		} catch (UpdateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", "Reason : "+e.getMessage());
		} catch (TechnicalException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", "Reason : "+e.getMessage());
		} catch (InvalidDataException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", "Reason : "+e.getMessage());
		}
	
	}
	
	public void populateData(PreUpdateAgentParamVO preUpdateAgentParamVO) {
		
		longEChunkSize.setValue(preUpdateAgentParamVO.getPareChunkSize());
		longEChunkSizeLimit.setValue(preUpdateAgentParamVO.getAreChunkSizeELimit());
		longEConCurrencyLimit.setValue(preUpdateAgentParamVO.getAreConcurrencyLimit());
		longMEChunkSize.setValue(preUpdateAgentParamVO.getParmeChunkSize());
		longMEChunkSizeLimit.setValue(preUpdateAgentParamVO.getArmeChunkSizeELimit());
		longMEConCurrencyLimit.setValue(preUpdateAgentParamVO.getArmeConcurrencyLimit());
		combooverruleechunksize.setValue(String.valueOf(preUpdateAgentParamVO.getOverRuleAEChunkSize()));
		combooverrulemechunksize.setValue(String.valueOf(preUpdateAgentParamVO.getOverRuleAMEChunkSize()));
		txtReason.setValue(preUpdateAgentParamVO.getReason());
	}
	
	private UpdateAgentParameterVO prepareUpdateData() throws InvalidDataException {
		
		UpdateAgentParameterVO updateAgentParameterVO = new UpdateAgentParameterVO();
		
		updateAgentParameterVO.setAgentid(getStrViewEntityId());
		updateAgentParameterVO.setAreChunkSizeELimit(longEChunkSizeLimit.getValue());
		updateAgentParameterVO.setAreConcurrencyLimit(longEConCurrencyLimit.getValue());
		updateAgentParameterVO.setArmeChunkSizeELimit(longMEChunkSizeLimit.getValue());
		updateAgentParameterVO.setArmeConcurrencyLimit(longMEConCurrencyLimit.getValue());
		
		if(combooverruleechunksize.getSelectedItem()!=null){
			ComboBoxData overRuleEntityCombo = combooverruleechunksize.getSelectedItem().getValue();
			updateAgentParameterVO.setOverRuleAEChunkSize(overRuleEntityCombo.getId().charAt(0));
		}
		if(combooverrulemechunksize.getSelectedItem()!=null){
			ComboBoxData overRuleMasterEntityCombo = combooverrulemechunksize.getSelectedItem().getValue();
			updateAgentParameterVO.setOverRuleAMEChunkSize(overRuleMasterEntityCombo.getId().charAt(0));
		}
		
		updateAgentParameterVO.setPareChunkSize(longEChunkSize.getValue());
		updateAgentParameterVO.setParmeChunkSize(longMEChunkSize.getValue());
		updateAgentParameterVO.setReason(txtReason.getValue());
		
		return updateAgentParameterVO;
	}
}
