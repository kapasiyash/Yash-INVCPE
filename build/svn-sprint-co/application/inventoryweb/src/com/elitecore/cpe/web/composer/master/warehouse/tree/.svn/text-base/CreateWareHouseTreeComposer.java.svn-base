package com.elitecore.cpe.web.composer.master.warehouse.tree;

import java.util.ArrayList;
import java.util.Collection;
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
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.master.warehouse.CreateWareHouseTreeVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;

public class CreateWareHouseTreeComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String MODULE = "WH-TREE";
	public static final String COMPOSER_REF = "_composerRef";
	public static final String MODE = "_mode";
	private Tabs moduleTabs;
	private Tabpanels moduleTabpanels;
//	private String type;
	private Tree moduleTree = null;
	private EventListener<Event> treeItemCheckListener;
//			checkBoxItemCheckListener;

	List<Long> actionIds = new ArrayList<Long>();
	private List<Component> actionsCheckBox = new ArrayList<Component>();
	Set<SystemActionData> auditableActionsList = new HashSet<SystemActionData>();

	public void afterCompose(Window comp) {
		/*
		if (arg.get(MODE) != null) {
			type = arg.get(MODE).toString();
		}

		if (type.equals("Create")) {
			ConfigureAuditComposer composer = (ConfigureAuditComposer) arg
					.get(COMPOSER_REF);
//			composer.setTreeComposer(this);
		} else {

		}*/
		Logger.logTrace(MODULE, "creating tree");

		

		treeItemCheckListener = new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {

				Treeitem newTreeItem = (Treeitem) event.getTarget();
				if (newTreeItem.getTreechildren() != null
						&& newTreeItem.getTreechildren().getItemCount() != 0) {
					
				}
			}
		};
		Events.postEvent("onMainCreate", comp, null);
	}

	public void onMainCreate(Event event) {
		
		WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		
		try {
			List<CreateWareHouseTreeVO> wareHouseVOs = wareHouseBD.createWareHouseTree();
//			Collections.sort(systemModuleGroupVO);
			if (wareHouseVOs != null && !wareHouseVOs.isEmpty()) {
				prepareWHGroup(wareHouseVOs);
			}

		} catch (SearchBLException e) {
			e.printStackTrace();
		} 

	}

	private void prepareWHGroup(List<CreateWareHouseTreeVO> groups) {
		Tab newTab = new Tab("WareHouses");
		newTab.setId("1");
		// newTab.setClosable(true);
		Tabpanel newTabpanel = new Tabpanel();
		newTabpanel.setStyle("padding: 5px;");
		newTabpanel.setId("CPE");
		moduleTree = new Tree();
		moduleTree.setZclass("z-dottree");
		
		if(groups!=null && !groups.isEmpty()) {
			Treechildren subTree = new Treechildren();
			for(CreateWareHouseTreeVO createWareHouseTreeVO : groups) {
				Treeitem moduleItem = prepareModule(createWareHouseTreeVO);
				subTree.appendChild(moduleItem);

			}
			moduleTree.appendChild(subTree);
		}
		 
		

		newTabpanel.appendChild(moduleTree);
		moduleTabs.appendChild(newTab);
		moduleTabpanels.appendChild(newTabpanel);
	}

	private Treeitem prepareTreeItem(String label, boolean isModule, Long id,
			Character isAuditable, Character enableAudit) {
		Treeitem item = new Treeitem();
		Treerow row = new Treerow();
		Treecell cell = new Treecell(label);
		Treecell checkcell = new Treecell();
		cell.setStyle("color:black;");
		if (isModule) {


		} else {

			System.out.println("-->" + id);
			System.out.println("-->" + isAuditable);
			System.out.println("-->" + enableAudit);
			System.out.println("-->" + isModule);
			System.out.println("-->" + label);

			

		}
		item.setId("tree" + id + "");
		row.appendChild(cell);
		row.appendChild(checkcell);
		item.appendChild(row);

		item.addEventListener(Events.ON_CLICK, treeItemCheckListener);
		return item;
	}

	private Treeitem prepareModule(CreateWareHouseTreeVO module) {
		Treeitem moduleItem = prepareTreeItem(module.getWareHouseName()+" ("+module.getWareHouseTypeName()+")", true,
				module.getWarehouseId(), 'N', 'N');
		if (module.getChildWareHouses() != null
				&& !module.getChildWareHouses().isEmpty()) {
//			Collections.sort(module.getChildWareHouses());
			Treechildren subTree = new Treechildren();
			for (CreateWareHouseTreeVO action : module.getChildWareHouses()) {
				Treeitem actionItem = prepareSystemAction(action);
				subTree.appendChild(actionItem);
			}
			moduleItem.appendChild(subTree);
		}
		return moduleItem;
	}

	private Treechildren prepareChildTree(List<CreateWareHouseTreeVO> childWareHouses) {
		
		if(childWareHouses!=null && !childWareHouses.isEmpty()) {
			Treechildren subTree = new Treechildren();
			for(CreateWareHouseTreeVO houseTreeVO : childWareHouses) {
				
				Treeitem item = new Treeitem();
				Treerow row = new Treerow();
				Treecell cell = new Treecell(houseTreeVO.getWareHouseName());
				cell.setStyle("color:black;");
				row.appendChild(cell);
				item.appendChild(row);
				Treechildren treechildren = prepareChildTree(houseTreeVO.getChildWareHouses());
				if(treechildren!=null) {
					item.appendChild(treechildren);
				}
				
				subTree.appendChild(item);
			}
			return subTree;
		}
		
		return null;
	}
	
	
	private Treeitem prepareSystemAction(CreateWareHouseTreeVO action) {
		Treeitem actionItem = prepareTreeItem(action.getWareHouseName()+" ("+action.getWareHouseTypeName()+")", false,
				action.getWarehouseId(), 'Y','Y');
		
		if (action.getChildWareHouses() != null && !action.getChildWareHouses().isEmpty()) {
//			Collections.sort(action.getChildActions());
			Treechildren subTree = new Treechildren();
			for (CreateWareHouseTreeVO childAction : action.getChildWareHouses()) {
				Treeitem childActionItem = prepareSystemAction(childAction);
				subTree.appendChild(childActionItem);
			}

			actionItem.appendChild(subTree);
		}

		return actionItem;
	}

	public List<Long> getActionIds() {
		return getAction(actionsCheckBox, auditableActionsList);
	}

	public List<Long> getAction(List<Component> actionsCheckBox2,
			Set<SystemActionData> actionDatas) {
		SystemActionData actionData = null;
		for (Component checkbox : actionsCheckBox2) {
			actionData = new SystemActionData();
			
			auditableActionsList.add(actionData);

		}

		return actionIds;
	}

	public Set<SystemActionData> getAuditableActionsList() {
		return auditableActionsList;
	}

	public void setAuditableActionsList(
			Set<SystemActionData> auditableActionsList) {
		this.auditableActionsList = auditableActionsList;
	}

	private void addActionCheckbox(Checkbox checkbox) {
		this.actionsCheckBox.add(checkbox);
	}

	public void reset() {
		getAction(actionsCheckBox, auditableActionsList);
	}

	public void onClick$btnCollapseAll(Event event) {
		Logger.logTrace(MODULE, "onClick button collapseAll");

		expandTree(false);

	}

	public void onClick$btnExpandAll(Event event) {
		Logger.logTrace(MODULE, "onClick button ExpandAll");

		expandTree(true);

	}

	public void expandTree(Boolean expander) {
		Collection itm = moduleTree.getItems();
		if (itm != null) {
			for (Object item : itm) {
				if (item instanceof Treeitem) {
					Treeitem treeitem = (Treeitem) item;
					treeitem.setOpen(expander);
				}
			}
		}
	}

}
