package com.elitecore.cpe.web.system.agent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.constants.system.AgentConstants;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.vo.system.agent.AgentParameterVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentScheduleParamVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.agent.AgentVO;
import com.elitecore.cpe.bl.vo.system.agent.ViewVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.InvalidDataException;
import com.elitecore.cpe.web.system.configuration.cronexpr.CronExpressionBuilderComposer;
import com.elitecore.cpe.web.utils.CustomFieldUtils;
import com.elitecore.cpe.web.utils.MessageUtility;

public class CreateAgentScheduleComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MODULE = "CREATE_AGENT_SCHEDULE_COMPOSER";
	
//	private Window createAgentScheduleWin;
	private Vlayout page1,page2,page3,page4;
	private Textbox txtScheduleName,txtFixed,txtReason;
	private Radio RadioForeever,RadioFixed,RadioAuto,RadioManual,All,Selected;
	private Datebox dtDate;
	private Timebox dtTime;
	private Row numOfExecRow;
	private Combobox priorityType;
	private Cell execCell,execText;
	private Listbox agentLB,SelectedLB;
	private Radiogroup executionType,requiredNumOfExe;
//	private Label lbAgentName,lbScheduleName,paramAlias,paramAliasMode;
	private Label lbAgentName2;
	private Cell ComponenetCell,ComponenetCellMode;
	private List<AgentParameterVO> parameterVO;
	private Div scheduleContent;
	private Radiogroup policySelect;
	private Label lbAgentName1,lbScheduleName1,lbPriority,lbExeInterval,lbExeType,lbNoOfExec,lbExeStartDate,lbReason;
	private CronExpressionBuilderComposer builderComposer;
	
//	List<String> selectedLBData = new ArrayList<String>();
	private AgentScheduleVO scheduleVO = new AgentScheduleVO();
	private ViewVO viewVO = new ViewVO();
	

	
	
	@Override
	public void afterCompose(Window comp) {
		
		All =  new Radio("All");
		Selected = new Radio("Selected");
		policySelect.appendChild(All);
		policySelect.appendChild(Selected);
		populateData();
		Map<String,Object> hmap = new HashMap<String, Object>();
		hmap.put(CronExpressionBuilderComposer.COMP_REF, this);
		Executions.createComponents(Pages.CRON_EXPRESSION_BUILDER, scheduleContent, hmap);
		RadioFixed.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				execCell.setVisible(true);
				execText.setVisible(true);
				
			}
		});
		
		RadioForeever.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				execCell.setVisible(false);
				execText.setVisible(false);
				
			}
		});
		
		RadioManual.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				numOfExecRow.setVisible(false);
				
			}
		});
		
		RadioAuto.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				numOfExecRow.setVisible(true);
				
				
			}
			
		});
		
		policySelect.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				Radiogroup group = (Radiogroup) event.getTarget();
				if(group.getSelectedItem()!=null){
					if( group.getSelectedItem().getLabel().equalsIgnoreCase("All")){
						SelectedLB.setVisible(false);
					}else{
						SelectedLB.setVisible(true);
					}
				}
				
			}
		});
		
		
		
	}
	
	private void populateData() {
		
		List<ComboData> priorityTypeData = new ArrayList<ComboData>();
		priorityTypeData.add(new ComboData(1l,AgentConstants.HIGH));
		priorityTypeData.add(new ComboData(2l,AgentConstants.MEDIUM));
		priorityTypeData.add(new ComboData(3l,AgentConstants.LOW));
		priorityType.setModel(new ListModelList<ComboData>(priorityTypeData));
		priorityType.setItemRenderer(new ComboItemDataRenderer());

		
		List<AgentVO> agents = null;
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			agents = agentBD.getAllAgentsList();
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		} 
		
		ListModelList<AgentVO> model = new ListModelList<AgentVO>(agents);
		agentLB.setModel(model);
		agentLB.setCheckmark(true);
		agentLB.setItemRenderer(new ListitemRenderer<AgentVO>() {

			@Override
			public void render(Listitem item, AgentVO data, int index)
					throws Exception {
				item.appendChild(new Listcell((index+1)+""));
				item.appendChild(new Listcell(data.getName()));
				item.appendChild(new Listcell(data.getDescription()));
				item.setValue(data);
				
			}
			
		});
		
		
		/*List<ComboData> hours = new ArrayList<ComboData>();
		List<ComboData> minutes = new ArrayList<ComboData>();
		for(long i=0;i<=12;i++) {
			hours.add(new ComboData(i, ""+i));
		}
		for(long i=0;i<=60;i++) {
			minutes.add(new ComboData(i, ""+i));
		}
		
		Hour.setModel(new ListModelList<ComboData>(hours));
		Hour.setItemRenderer(new ComboItemDataRenderer());
		
		Minute.setModel(new ListModelList<ComboData>(minutes));
		Minute.setItemRenderer(new ComboItemDataRenderer());
		
		
		*/
		
	}

	public void onClick$btnNext1(Event event) {
		
				

			Set<Listitem> agents = agentLB.getSelectedItems();
			if(agents!=null && !agents.isEmpty()){
				for(Listitem item : agents){
					Logger.logTrace(MODULE, ((AgentVO)item.getValue()).getName());
					String agentId = ((AgentVO)item.getValue()).getName();
					viewVO.setAgentName(((AgentVO)item.getValue()).getName());
					scheduleVO.setAgentId(((AgentVO)item.getValue()).getAgentId());
					page1.setVisible(false);
					page2.setVisible(true);	
					lbAgentName2.setValue(viewVO.getAgentName());
				}
			} else {
				MessageUtility.failureInformation("Failure", "Please select one Agent");
			}
			
				
	}
	
	
	public void onClick$btnNext2(Event event) {
		
		prepareDataAgentSchedule();
		page1.setVisible(false);
		page2.setVisible(false);
//		page3.setVisible(true);
		page4.setVisible(true);
		/*lbAgentName.setValue(viewVO.getAgentName());
		lbScheduleName.setValue(scheduleVO.getScheduleName());
		createAgentScheduleWin.invalidate();*/
		
	/*	SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			
			
			parameterVO = agentBD.getSystemAgentParameters(scheduleVO.getAgentId());
			
			for(AgentParameterVO agentParameterVO : parameterVO) {
				Logger.logTrace(MODULE, agentParameterVO.getCustomfieldtypeid());
				Component component = CustomFieldUtils.getCustomComponenet(agentParameterVO, null,SelectedLB);
				
				if(agentParameterVO.getCustomfieldtypeid().equalsIgnoreCase(SystemParameterConstants.LIST_BOX)) {
					if(ComponenetCell.getChildren()!=null) {
						ComponenetCell.getChildren().clear();
					}
					paramAlias.setValue(agentParameterVO.getName());
					
					if(component instanceof Listbox) {
						ComponenetCell.setVisible(false);
						//Logger.logTrace(MODULE,selectedLBData.toString() );
						
					} else {
						ComponenetCell.setVisible(true);
						ComponenetCell.appendChild(component);
					}
				}
				if(agentParameterVO.getCustomfieldtypeid().equalsIgnoreCase(SystemParameterConstants.SQL_COMBO_BOX)) {
					if(ComponenetCellMode.getChildren()!=null) {
						ComponenetCellMode.getChildren().clear();
					}
					ComponenetCellMode.appendChild(component);
					paramAliasMode.setValue(agentParameterVO.getName());
				}
				
			}
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}*/
		lbAgentName1.setValue(viewVO.getAgentName());
		lbScheduleName1.setValue(scheduleVO.getScheduleName());
		lbPriority.setValue(scheduleVO.getPriority());
		if(scheduleVO.getExecutionInterval()!=null) {
			lbExeInterval.setValue(scheduleVO.getExecutionInterval().toString()+" (In Minutes)");
		}
		
		if(scheduleVO.getExecutionType().equals(AgentConstants.EXECUTION_TYPE_AUTOMATIC_EXECUTION)) {
			lbExeType.setValue("Automatic");
		} else if(scheduleVO.getExecutionType().equals(AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION)) {
			lbExeType.setValue("Manual");
		}
		
		if(scheduleVO.getRequiredNoOfExecutions().equals(-1L)) {
			lbNoOfExec.setValue("Forever");
		} else {
			lbNoOfExec.setValue(scheduleVO.getRequiredNoOfExecutions().toString());
		}
		
		
		lbExeStartDate.setValue(scheduleVO.getExecutionStartDate().toString());
		lbReason.setValue(scheduleVO.getReason());
	}
	
	
	public void onClick$btnNext3(Event event) {
		List<AgentScheduleParamVO> scheduleParamVOList;
		try {
		if(ComponenetCell.getChildren()!=null) {
			for(Component component : ComponenetCell.getChildren()) {
				if(component instanceof Textbox) {
					if(((Textbox)component).getValue()==null) {
						MessageUtility.failureInformation("Error", "Please Enter Value");
					}
				}
				if(component instanceof Datebox){
					if(((Datebox)component).getValue()==null) {
						MessageUtility.failureInformation("Error", "Please Enter Date");
					}
				}
				if(component instanceof Combobox) {
					if(((Combobox)component).getSelectedItem().getLabel()==null) {
						MessageUtility.failureInformation("Error", "Please Select Value");
					}
				}
				
			}
		}
		
		if(policySelect.getSelectedItem()==null) {
			throw new InvalidDataException("Please Select atleast one Policy");
		}
		
		lbAgentName1.setValue(viewVO.getAgentName());
		lbScheduleName1.setValue(scheduleVO.getScheduleName());
		lbPriority.setValue(scheduleVO.getPriority());
		if(scheduleVO.getExecutionInterval()!=null) {
			lbExeInterval.setValue(scheduleVO.getExecutionInterval().toString()+" (In Minutes)");
		}
		
		if(scheduleVO.getExecutionType().equals(AgentConstants.EXECUTION_TYPE_AUTOMATIC_EXECUTION)) {
			lbExeType.setValue("Automatic");
		} else if(scheduleVO.getExecutionType().equals(AgentConstants.EXECUTION_TYPE_MANUAL_EXECUTION)) {
			lbExeType.setValue("Manual");
		}
		
		if(scheduleVO.getRequiredNoOfExecutions().equals(-1L)) {
			lbNoOfExec.setValue("Forever");
		} else {
			lbNoOfExec.setValue(scheduleVO.getRequiredNoOfExecutions().toString());
		}
		
		
		lbExeStartDate.setValue(scheduleVO.getExecutionStartDate().toString());
		lbReason.setValue(scheduleVO.getReason());
		
		
		
		StringBuffer valueField = new StringBuffer();
		scheduleParamVOList = new ArrayList<AgentScheduleParamVO>();
		
		String policyAlias = "";
		String policyModeAlias = "";
		
		for(AgentParameterVO paramVO : parameterVO) {
			if(paramVO.getCustomfieldtypeid().equalsIgnoreCase(SystemParameterConstants.LIST_BOX)) {
				policyAlias = paramVO.getAlias();
			} 
			if(paramVO.getCustomfieldtypeid().equalsIgnoreCase(SystemParameterConstants.SQL_COMBO_BOX)) {
				policyModeAlias = paramVO.getAlias();
			}
		}
		
		if(All.isSelected()) {
//			for(String value : selectedLBData) {
//				valueField = valueField.append(value+",");
//			}
//			String retString = valueField.toString().substring(0, valueField.toString().lastIndexOf(","));
			AgentScheduleParamVO scheduleParamVO = new AgentScheduleParamVO();
			scheduleParamVO.setValueField(AgentConstants.ALL_ENTITIES);
			scheduleParamVO.setParamAlias(policyAlias);
			scheduleParamVOList.add(scheduleParamVO);
		}
		if(Selected.isSelected()) {
			Set<Listitem> policies = SelectedLB.getSelectedItems();
			if(policies!=null && !policies.isEmpty()){
				for(Listitem item : policies){
					String[] dataList = (String[])item.getValue();
					valueField = valueField.append((dataList[0])+",");
				}
				String retString = valueField.toString().substring(0, valueField.toString().lastIndexOf(","));
				AgentScheduleParamVO scheduleParamVO = new AgentScheduleParamVO();
				scheduleParamVO.setValueField(retString);
				scheduleParamVO.setParamAlias(policyAlias);
				scheduleParamVOList.add(scheduleParamVO);
				
			}
		}
			
			
		
		
		
		if(ComponenetCellMode.getChildren()!=null) {
			List<Component> components = ComponenetCellMode.getChildren();
			for(Component component : components) {
					String valueFields = CustomFieldUtils.getComponentValue(component);
					AgentScheduleParamVO scheduleParamVO = new AgentScheduleParamVO();
					scheduleParamVO.setValueField(valueFields);
					scheduleParamVO.setParamAlias(policyModeAlias);
					scheduleParamVOList.add(scheduleParamVO);
			}
		}
		scheduleVO.setScheduleParamVO(scheduleParamVOList);
		
		
		
		
		page1.setVisible(false);
		page2.setVisible(false);
		page3.setVisible(false);
		page4.setVisible(true);
		} catch(InvalidDataException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Failure", e.getMessage());
		}
	}
	
	public void onClick$btnFinish(Event event) {
		
		
		SystemAgentBD agentBD = new SystemAgentBD(getBDSessionContext());
		try {
			
			agentBD.createAgentSchedule(scheduleVO);
			MessageUtility.successInformation("Success", "AgentSchedule Created Successfully");
			page1.setVisible(true);
			page2.setVisible(false);
			page3.setVisible(false);
			page4.setVisible(false);
			agentLB.clearSelection();
			resetComponents(txtScheduleName,txtFixed,txtReason,priorityType,dtDate,executionType,requiredNumOfExe);
			
		} catch (CreateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("Create Operation Failed", e.getMessage());
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}
	
	
	public void onClick$btnCancel2(Event event) {
		resetComponents(txtScheduleName,txtScheduleName,txtFixed,txtReason,priorityType,dtDate,executionType,requiredNumOfExe);
	}
	
	
	private void prepareDataAgentSchedule() {
		
		
		scheduleVO.setScheduleName(txtScheduleName.getValue());
		if(priorityType.getSelectedItem()!=null){
			ComboData category = priorityType.getSelectedItem().getValue();
			scheduleVO.setPriority(category.getName());
		}
		
		Calendar timeCal = Calendar.getInstance();	
		Calendar dateCal = Calendar.getInstance();
		
		if(dtDate.getValue() !=null) {
			dateCal.setTime(dtDate.getValue());
		}
		if(dtTime.getValue()!=null) {
			timeCal.setTime(dtTime.getValue());
		}
		
		
		
		dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR));
		dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
		
		scheduleVO.setExecutionStartDate(new Timestamp(dateCal.getTime().getTime()));
		scheduleVO.setReason(txtReason.getValue());
		
		
		if(RadioManual.isChecked()) {
			scheduleVO.setExecutionType(AgentConstants.EXE_MANUAL);
			scheduleVO.setRequiredNoOfExecutions(0L);
			scheduleVO.setExecutionInterval(0l);
		} else if(RadioAuto.isChecked()) {
			scheduleVO.setExecutionType(AgentConstants.EXE_AUTO);
			
			if(RadioForeever.isChecked()) {
				scheduleVO.setRequiredNoOfExecutions(-1L);
			} else if(RadioFixed.isChecked()) {
				scheduleVO.setRequiredNoOfExecutions(Long.parseLong(txtFixed.getValue()));
			}
			
			/*Long day = Long.parseLong(txtDays.getValue())*24*60;
			Long hour = 0l;
			Long min = 0l;
			
			if(Hour.getSelectedItem()!=null) {
				ComboData category = Hour.getSelectedItem().getValue();
				hour = category.getId()*60;
			}
			
			if(Minute.getSelectedItem()!=null) {
				ComboData category = Minute.getSelectedItem().getValue();
				min = category.getId();
			}
			scheduleVO.setExecutionInterval(day+hour+min);*/
		}
		if(builderComposer!=null) {
			Logger.logTrace(MODULE, builderComposer.prepareFinalExpression());
			
			scheduleVO.setSchedulePattern(builderComposer.prepareFinalExpression());
		}
		
		
		
	}
	
	
	public void onClick$btnBack2(Event event) {
		
			page1.setVisible(true);
			page2.setVisible(false);
			page3.setVisible(false);
			page4.setVisible(false);
		
	}
	
	public void onClick$btnBack3(Event event) {
		
		page1.setVisible(false);
		page2.setVisible(true);
		page3.setVisible(false);
		page4.setVisible(false);
	}
	
	public void onClick$btnBack4(Event event) {
		/*page1.setVisible(false);
		page2.setVisible(false);
		page3.setVisible(true);
		page4.setVisible(false);*/
		page1.setVisible(false);
		page2.setVisible(true);
		page3.setVisible(false);
		page4.setVisible(false);
	}

	public void setCronComposer(
			CronExpressionBuilderComposer cronExpressionBuilderComposer) {
		this.builderComposer = cronExpressionBuilderComposer;
		
	}

}
