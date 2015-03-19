package com.elitecore.cpe.web.composer.inventory.search;

import java.util.Collections;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryStatusLogVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class InventoryHDComposer extends BaseModuleViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hlayout inventoryhistorydetail;
	private Listbox searchResultGrid;
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		inventoryhistorydetail = comp;
		
		fetchViewEntity();
		
	}
	
	public void demo() {inventoryhistorydetail.detach();}
	
	
	private void fetchViewEntity(){
		
		
		InventoryManagementBD inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
		List<InventoryStatusLogVO> inventoryStatusLogVOs= inventoryManagementBD.viewInventoryHistoryData(getStrViewEntityId());
		if(inventoryStatusLogVOs!=null) {
			Collections.sort(inventoryStatusLogVOs);
		}
		searchResultGrid.setVisible(true);
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		if(inventoryStatusLogVOs!=null && !inventoryStatusLogVOs.isEmpty()){
			searchResultGrid.setModel(new ListModelList<InventoryStatusLogVO>(inventoryStatusLogVOs));
			searchResultGrid.setItemRenderer(new SearchListItemRenderer());
			
		}else{
			searchResultGrid.setModel(new ListModelList<InventoryStatusLogVO>());
		}
		
		
	}
	
	private static class SearchListItemRenderer implements ListitemRenderer<InventoryStatusLogVO>{

		
		
		public SearchListItemRenderer() {
			
				}
		
		@Override
		public void render(Listitem item, InventoryStatusLogVO data, int index)throws Exception {
			
			Logger.logTrace("Inventory", "Data received:"+data);
			
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			
			item.appendChild(new Listcell(data.getOldStatus()));
			item.appendChild(new Listcell(data.getNewStatus()));
			item.appendChild(new Listcell(data.getChangedby()));
			
			item.appendChild(new Listcell(com.elitecore.cpe.web.utils.GeneralUtility.displayINDateTimeFormat(data.getChangeDate())));
			item.appendChild(new Listcell(data.getRemarks()));
			
			

			
		}
		
	}
	
}
