package com.elitecore.cpe.web.system.agent;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
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
public class DeleteAgentScheduleComposer extends BaseModuleViewComposer {


	private static final long serialVersionUID = 1L;
//	private static final String MODULE = "DELETE_AGENT_SCHEDULE";
//	private Window viewscheduleWin;
//	private Hlayout deleteschedule;
	private Textbox reasontb;
	private Hlayout parent;
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		parent = comp;
		//SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		/*try {
			List<ComboData> list = agentBD.findAgentScheduleStatus();
			
			deleteScheduleCombo.setModel(new ListModelList<ComboData>(list));
			deleteScheduleCombo.setItemRenderer(new ComboItemDataRenderer());
			deleteScheduleCombo.setValue("Active");
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}*/
		
	}
	
	
	public void onClick$btnDelete(Event event) {
		if(reasontb.getValue()!=null){
			SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
			try {
				agentBD.cancelAgentSchedule(getViewEntityId(), reasontb.getValue());
				MessageUtility.successInformation("Schedule Deactivation Success", "Agent Schedule is deactivated successfully");
				parent.detach();
			} catch (WrongValueException e) {
				e.printStackTrace();
			} catch (UpdateBLException e) {
				e.printStackTrace();
			} catch (TechnicalException e) {
				e.printStackTrace();
			}catch(Exception e){
				MessageUtility.failureInformation("Schedule Deactivation Failed", "Agent Schedule deactivation failed.");
			}
		}
	}

}
