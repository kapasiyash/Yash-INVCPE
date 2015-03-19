package com.elitecore.cpe.web.system.configuration.cronexpr;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

//import com.elitecore.cpe.web.composer.ConfigureEventComposer;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.composer.ConfigureProcSchedule;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.system.agent.CreateAgentScheduleComposer;
import com.elitecore.cpe.web.system.agent.UpdateAgentScheduleComposer;

public class CronExpressionBuilderComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final String MODULE = "CRON-EXPRESSION-BUILDER";
	public  static final String COMP_REF = "_compRef";
	
	private Radiogroup mingroup,hourgroup,daygroup,monthgroup,weekdaygroup;
	
	private Listbox everysellb;
	private Listbox everyselhrlb;
	private Listbox everyseldaylb;
	private Listbox everyselmonthlb;
	private Listbox everyselweeklb;
	private CreateAgentScheduleComposer composer;
	private UpdateAgentScheduleComposer updateComposer;
	private ConfigureProcSchedule configureProcSchedule;
	private String[] cronExpression;
//	private EventListener<Event> radioGroupChangeEvent;
	private EventListener<Event> listboxSelectionEvent;
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		cronExpression= new String[5];
		if(arg.containsKey(COMP_REF)){
			if(arg.get(COMP_REF) instanceof CreateAgentScheduleComposer) {
				composer = (CreateAgentScheduleComposer) arg.get(COMP_REF);
				composer.setCronComposer(this);
			} else if(arg.get(COMP_REF) instanceof UpdateAgentScheduleComposer) {
				updateComposer = (UpdateAgentScheduleComposer) arg.get(COMP_REF);
				updateComposer.setCronComposer(this);
			}
			if(arg.get(COMP_REF) instanceof ConfigureProcSchedule) {
				configureProcSchedule = (ConfigureProcSchedule) arg.get(COMP_REF);
				configureProcSchedule.setCronComposer(this);
			}
			
		}
		initAllComponents(); // Initializing all components. (i.e loading defaults )
		/*radioGroupChangeEvent = new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				Radiogroup radioGroup = (Radiogroup) event.getTarget();
				Radio selectedRadio = radioGroup.getSelectedItem();
				if(radioGroup == mingroup){
					if(selectedRadio.getValue()!=null){
						cronExpression[0] = new String(selectedRadio.getValue()+"");
					}else{
						cronExpression[0] = getSeletedItemString(everysellb); 
					}
				}else if(radioGroup == hourgroup){
					if(selectedRadio.getValue()!=null){
						cronExpression[1] = new String(selectedRadio.getValue()+"");
					}else{
						cronExpression[1] = getSeletedItemString(everyselhrlb); 
					}
				}else if(radioGroup == daygroup){
					if(selectedRadio.getValue()!=null){
						cronExpression[2] = new String(selectedRadio.getValue()+"");
					}else{
						cronExpression[2] = getSeletedItemString(everyseldaylb); 
					}
				}else if(radioGroup == monthgroup){
					if(selectedRadio.getValue()!=null){
						cronExpression[3] = new String(selectedRadio.getValue()+"");
					}else{
						cronExpression[3] = getSeletedItemString(everyselmonthlb); 
					}
				}else if(radioGroup == weekdaygroup){
					if(selectedRadio.getValue()!=null){
						cronExpression[4] = new String(selectedRadio.getValue()+"");
					}else{
						cronExpression[4] = getSeletedItemString(everyselweeklb); 
					}
				}
				
			}
		};*/
		 listboxSelectionEvent = new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				Listbox listbox = (Listbox) event.getTarget();
				if(listbox!=null){
					if(listbox.equals(everysellb)){
						cronExpression[0] = getSeletedItemString(listbox); 
					}else if(listbox.equals(everyselhrlb)){
						cronExpression[1] = getSeletedItemString(listbox); 
					}else if(listbox.equals(everyseldaylb)){
						cronExpression[2] = getSeletedItemString(listbox); 
					}else if(listbox.equals(everyselmonthlb)){
						cronExpression[3] = getSeletedItemString(listbox); 
					}else if(listbox.equals(everyselweeklb)){
						cronExpression[4] = getSeletedItemString(listbox); 
					}
				}
			}
		};
		
		everysellb.addEventListener(Events.ON_SELECTION, listboxSelectionEvent);
		everyselhrlb.addEventListener(Events.ON_SELECTION, listboxSelectionEvent);
		everyseldaylb.addEventListener(Events.ON_SELECTION, listboxSelectionEvent);
		everyselmonthlb.addEventListener(Events.ON_SELECTION, listboxSelectionEvent);
		everyselweeklb.addEventListener(Events.ON_SELECTION, listboxSelectionEvent);
		
		/*mingroup.addEventListener(Events.ON_CHANGE, radioGroupChangeEvent);
		hourgroup.addEventListener(Events.ON_CHANGE, radioGroupChangeEvent);
		daygroup.addEventListener(Events.ON_CHANGE, radioGroupChangeEvent);
		monthgroup.addEventListener(Events.ON_CHANGE, radioGroupChangeEvent);
		weekdaygroup.addEventListener(Events.ON_CHANGE, radioGroupChangeEvent);*/
	}
	
	
	
	private void initAllComponents(){
		populateDefaultValues(everysellb,0,59); // Populates minuntes default listbox
		populateDefaultValues(everyselhrlb,0,23); // Populates hours default listbox
		populateDefaultValues(everyseldaylb,1,31); // Populates days default listbox
		
	}
	
	public void getExpression(){
		prepareExpression(mingroup);
		prepareExpression(hourgroup);
		prepareExpression(daygroup);
		prepareExpression(monthgroup);
		prepareExpression(weekdaygroup);
	}
	private void prepareExpression(Radiogroup radioGroup){
		Radio selectedRadio = radioGroup.getSelectedItem();
		if(radioGroup == mingroup){
			if(selectedRadio.getValue()!=null){
				cronExpression[0] = selectedRadio.getValue().toString();
			}else{
				cronExpression[0] = getSeletedItemString(everysellb); 
			}
		}else if(radioGroup == hourgroup){
			if(selectedRadio.getValue()!=null){
				cronExpression[1] = selectedRadio.getValue().toString();
			}else{
				cronExpression[1] = getSeletedItemString(everyselhrlb); 
			}
		}else if(radioGroup == daygroup){
			if(selectedRadio.getValue()!=null){
				cronExpression[2] = selectedRadio.getValue().toString();
			}else{
				cronExpression[2] = getSeletedItemString(everyseldaylb); 
			}
		}else if(radioGroup == monthgroup){
			if(selectedRadio.getValue()!=null){
				cronExpression[3] = selectedRadio.getValue().toString();
			}else{
				cronExpression[3] = getSeletedItemString(everyselmonthlb); 
			}
		}else if(radioGroup == weekdaygroup){
			if(selectedRadio.getValue()!=null){
				cronExpression[4] = selectedRadio.getValue().toString();
			}else{
				cronExpression[4] = getSeletedItemString(everyselweeklb); 
			}
		}
	}
	public void onClick$btnCheck(Event event){
		getExpression();
		Logger.logTrace(MODULE, "Cron Expression : " + prepareFinalExpression());
	}
	
	public String prepareFinalExpression(){
		getExpression();
		StringBuilder builder = new StringBuilder();
		if(cronExpression!=null && cronExpression.length>0){
			boolean isfirst=true;
			for(String index: cronExpression){
				if(!isfirst){
					builder.append(" ");
				}else{
					isfirst=false;
				}
				builder.append(index);
			}
		}
		return builder.toString();
	}
	
	private void populateDefaultValues(Listbox listbox,int startvalue ,int endvalue){
		for(int i=startvalue;i<=endvalue;i++){
			Listitem item = new Listitem(i+"",i+"");
			listbox.appendChild(item);
		}
	}
	
	protected String getSeletedItemString(Listbox listbox){
		String retString = null;
		if(listbox!=null && listbox.getSelectedItems()!=null && !listbox.getSelectedItems().isEmpty()){
			StringBuilder builder = new StringBuilder();
			boolean isfirst = true;
			for(Listitem item : listbox.getSelectedItems()){
				if(!isfirst){
					builder.append(",");
				}else{
					isfirst=false;
				}
				builder.append(String.valueOf(item.getValue()));
			}
			retString= builder.toString();
		}
		return retString;
	}
	
}
