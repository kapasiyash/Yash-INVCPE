package com.elitecore.cpe.web.composer.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.system.internal.SystemActionData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleData;
import com.elitecore.cpe.bl.data.system.internal.SystemModuleGroupData;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class CreateTreeComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String MODULE = "ACL-TREE";
	public static final String COMPOSER_REF = "_composerRef";
	public static final String MODE = "_mode";
	private Tabs moduleTabs;
	private Tabpanels moduleTabpanels;	
	private String type;
	private Tree moduleTree=null;
	private EventListener<Event> treeItemCheckListener,checkBoxItemCheckListener;
	
	List<Long> actionIds = new ArrayList<Long>();
	private List<Component> actionsCheckBox = new ArrayList<Component>();
	Set<SystemActionData> auditableActionsList = new HashSet<SystemActionData>();
	
	



	public void afterCompose(Window comp){
		
		 if(arg.get(MODE)!=null){
			 type=arg.get(MODE).toString();
		 }
		 
		 if(type.equals("Create")){
			 ConfigureAuditComposer composer = (ConfigureAuditComposer)arg.get(COMPOSER_REF);
			 composer.setTreeComposer(this);
		 }else{
				
		 }
		 Logger.logTrace(MODULE, "creating tree");
		 
		 checkBoxItemCheckListener =new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Checkbox checkBox=(Checkbox)event.getTarget();
					Treeitem newTreeItem = (Treeitem) checkBox.getParent().getParent().getParent();
					if(newTreeItem!=null && newTreeItem.getTreechildren()!=null && newTreeItem.getTreechildren().getItems()!=null) {
						Collection<Treeitem> treeItemList=(Collection<Treeitem>)newTreeItem.getTreechildren().getItems();
						if(checkBox.isChecked()) {
							for(Treeitem item : treeItemList){		            									
								
//	    							((Checkbox)checkBox).setChecked(true);
	    							for(Component checkbox :actionsCheckBox) {
			    						String id = checkbox.getId();
			    						if(item.getId().equals("tree"+id)) {
			    							((Checkbox)checkbox).setChecked(true);
			    						}
	    							}
	    									            													         
							}
						} else {
							for(Treeitem item : treeItemList){		            									
								
									for(Component checkbox :actionsCheckBox) {
			    						String id = checkbox.getId();
			    						if(item.getId().equals("tree"+id)) {
			    							((Checkbox)checkbox).setChecked(false);
			    						}
									}
	    							
	    									            													         
							}
						}
					}
					
				}
				
		 };
		 
		 
		 treeItemCheckListener =new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				
				Treeitem newTreeItem=(Treeitem)event.getTarget();	            						            		            			
    			if(newTreeItem.getTreechildren()!=null && newTreeItem.getTreechildren().getItemCount()!=0){
//    					Collection<Treeitem> treeItemList=(Collection<Treeitem>)newTreeItem.getTreechildren().getItems();
    						/*if(newTreeItem.isSelected()){
    							for(Treeitem item : treeItemList){		            									
    								item.setSelected(true);					            													         
    							}
    						} else {
    							for(Treeitem item : treeItemList){
    		    					for(Component checkbox :actionsCheckBox) {
    		    						String id = checkbox.getId();
    		    						if(item.getId().equals("tree"+id)) {
    		    							((Checkbox)checkbox).setChecked(false);
    		    						}
    		    					}

            						System.out.println("name-->"+item.getLabel());
            					}			            								
    						}*/
    					}  
			}
		};
		 Events.postEvent("onMainCreate", comp, null);   
	}
	
	 
	
	 public void onMainCreate(Event event) {
		 ConfigureAuditBD systemBD=new ConfigureAuditBD(getBDSessionContext());
		try {
			List<SystemModuleGroupData> systemModuleGroupVO = systemBD.getSystemModuleData();
				Collections.sort(systemModuleGroupVO);
				if(systemModuleGroupVO!= null && !systemModuleGroupVO.isEmpty()){
					for(SystemModuleGroupData group : systemModuleGroupVO){
						prepareSystemGroup(group);
					}
				}
				
		} catch (SearchBLException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}			
		
	 }
	
	
	 private void prepareSystemGroup(SystemModuleGroupData group) {
		Tab newTab = new Tab("Actions");
		newTab.setId("1");
//		newTab.setClosable(true);
		Tabpanel newTabpanel = new Tabpanel();
		newTabpanel.setStyle("padding: 5px;");
		newTabpanel.setId("AR");
		 moduleTree = new Tree();
		moduleTree.setZclass("z-dottree");
		 if(group.getSystemModules()!=null && !group.getSystemModules().isEmpty()){
     		Collections.sort(group.getSystemModules());
     		Treechildren subTree = new Treechildren();
        	 for(SystemModuleData module : group.getSystemModules()){
        		 Treeitem moduleItem = prepareModule(module);
        		 subTree.appendChild(moduleItem);
        	 }
        	
        	 moduleTree.appendChild(subTree);
         }
		 
     	
        newTabpanel.appendChild(moduleTree);
     	moduleTabs.appendChild(newTab);
     	moduleTabpanels.appendChild(newTabpanel);
	}

	private Treeitem prepareTreeItem(String label,boolean isModule,Long id, Character isAuditable, Character enableAudit){
		Treeitem item = new Treeitem();
		Treerow row = new Treerow();
		Treecell cell = new Treecell(label);
		Treecell checkcell = new Treecell();
		cell.setStyle("color:black;");
		if(isModule){
//			cell.setStyle("color:red;font-style: oblique;");
			System.out.println(id);
			System.out.println(isAuditable);
			System.out.println(enableAudit);
			System.out.println(isModule);
			System.out.println(label);
			
			Checkbox box = new Checkbox();
			box.setId("module"+id+"");
			box.addEventListener(Events.ON_CLICK, checkBoxItemCheckListener);
//			addActionCheckbox(box);
			checkcell.appendChild(box);
			
		}else{

			System.out.println("-->"+id);
			System.out.println("-->"+isAuditable);
			System.out.println("-->"+enableAudit);
			System.out.println("-->"+isModule);
			System.out.println("-->"+label);
			
			Checkbox box = new Checkbox();
			if(isAuditable=='Y' && enableAudit=='Y'){
				box.setChecked(true);
			}
			box.setId(id+"");
			box.addEventListener(Events.ON_CLICK, checkBoxItemCheckListener);
			addActionCheckbox(box);
			checkcell.appendChild(box);
			
		}
		item.setId("tree"+id+"");
		row.appendChild(cell);
		row.appendChild(checkcell);
		item.appendChild(row);

		item.addEventListener(Events.ON_CLICK, treeItemCheckListener);
		return item;
	}


	private Treeitem prepareModule(SystemModuleData module) {
		Treeitem moduleItem = prepareTreeItem(module.getName(), true,module.getModuleId(),'N','N');
 		if(module.getSystemActions()!=null && !module.getSystemActions().isEmpty()){
 			Collections.sort(module.getSystemActions());
 			Treechildren subTree = new Treechildren();
 			for(SystemActionData action : module.getSystemActions()){
 				Treeitem actionItem = prepareSystemAction(action);
 				if(actionItem!=null) {
 					subTree.appendChild(actionItem);
 				}
 			}
 			moduleItem.appendChild(subTree);
 		}
		return moduleItem;
	}



	private Treeitem prepareSystemAction(SystemActionData action) {
		
		/*if(action.getName().startsWith("Search") && !action.getName().equals("Search Inventory")) {
			return null;
		}
		
		if( action.getName().startsWith("View") 
				||  action.getName().equals("Transfer Inventory Batch") || action.getName().equals("Warehouse Summary")
				|| action.getName().equals("Warehouse Tree View") || action.getName().equals("Warehouse Hierarchy")
				|| action.getName().equals("Inventory History Detail")) {
			return null;
		} else {
			Treeitem actionItem  = prepareTreeItem(action.getName(), false,action.getActionId(),action.getIsAuditable(), action.getEnableAudit());
			Logger.logTrace(MODULE, action.getIsAuditable()+" "+action.getEnableAudit());
			if(action.isParentAction() && action.getChildActions()!=null && !action.getChildActions().isEmpty()){
				Collections.sort(action.getChildActions());
	 			Treechildren subTree = new Treechildren();
	 			for(SystemActionData childAction : action.getChildActions()){
	 				Treeitem childActionItem = prepareSystemAction(childAction);
	 				if(childActionItem!=null) {
	 					subTree.appendChild(childActionItem);
	 				}
	 			}
	 			
	 			actionItem.appendChild(subTree);
	 		}
			
			return actionItem;
		}*/
		
		Treeitem actionItem = null;
		if(action.getIsAuditable() == 'Y') {
			
			 actionItem  = prepareTreeItem(action.getName(), false,action.getActionId(),action.getIsAuditable(), action.getEnableAudit());
			Logger.logTrace(MODULE, action.getIsAuditable()+" "+action.getEnableAudit());
			if(action.isParentAction() && action.getChildActions()!=null && !action.getChildActions().isEmpty()){
				Collections.sort(action.getChildActions());
				Treechildren subTree = new Treechildren();
				for(SystemActionData childAction : action.getChildActions()){
					Treeitem childActionItem = prepareSystemAction(childAction);
					if(childActionItem!=null) {
						subTree.appendChild(childActionItem);
					}
				}
				
				actionItem.appendChild(subTree);
			}
		}
		
		return actionItem;
		
		
	}

	public List<Long> getActionIds(){
		return getAction(actionsCheckBox,auditableActionsList);
	}
		
	public List<Long> getAction(List<Component> actionsCheckBox2,Set<SystemActionData> actionDatas) {
		SystemActionData actionData = null;
		for(Component checkbox :actionsCheckBox2) {
			actionData = new SystemActionData();
			if(((Checkbox) checkbox).isChecked()) {
					actionIds.add(Long.parseLong(checkbox.getId()));
					actionData.setActionId(Long.parseLong(checkbox.getId()));
					actionData.setEnableAudit('Y');
			} else {
				actionData.setActionId(Long.parseLong(checkbox.getId()));
				actionData.setEnableAudit('N');
			}
			auditableActionsList.add(actionData);
			
		}
		
		return actionIds;
	}
		
	public Set<SystemActionData> getAuditableActionsList() {
		return auditableActionsList;
	}



	public void setAuditableActionsList(Set<SystemActionData> auditableActionsList) {
		this.auditableActionsList = auditableActionsList;
	}
	
	private void addActionCheckbox(Checkbox checkbox){
		this.actionsCheckBox.add(checkbox);
	}
	
	public void reset() {
		getAction(actionsCheckBox,auditableActionsList);
	}
public void onClick$btnCollapseAll(Event event) {
	 Logger.logTrace(MODULE, "onClick button collapseAll");
	
	 expandTree(false);
	
		
	}
public void onClick$btnExpandAll(Event event) {
	 Logger.logTrace(MODULE, "onClick button ExpandAll");
		
	 expandTree(true);
	
	}
public void expandTree(Boolean expander){
	Collection itm = moduleTree.getItems();
	 if (itm != null){
		 for (Object item : itm){
			 if (item instanceof Treeitem) {
				 Treeitem treeitem = (Treeitem) item;
				 treeitem.setOpen(expander);
			 }
		 }
	 }
}
}
