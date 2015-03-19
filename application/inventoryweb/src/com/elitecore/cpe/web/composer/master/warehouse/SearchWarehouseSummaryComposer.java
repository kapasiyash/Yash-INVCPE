package com.elitecore.cpe.web.composer.master.warehouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.vo.master.WareHouseSummaryVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.core.BaseSearchComposer;
import com.elitecore.cpe.web.constants.Pages;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class SearchWarehouseSummaryComposer  extends BaseSearchComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "SearchWarehouseSummaryComposer";
	
	private Combobox cmbWarehouseName;
//	private Tab searchTab;
	private Listbox searchResultGrid;
	
	private Button btnDownload;
	
	private Tabbox searchWarehouseTabbox;
	private Listhead summaryListHead;
	List<Listheader> listheaders = null;
	
	List<String> notAllowedForCentral = new ArrayList<String>();
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		WareHouseBD  wareHouseBD = new WareHouseBD(getBDSessionContext());

		List<ComboData> wareHouseData = wareHouseBD.getAllWarehouseData();
		
		if(wareHouseData!=null && !wareHouseData.isEmpty()) {
			cmbWarehouseName.setModel(new ListModelList<ComboData>(wareHouseData));
			cmbWarehouseName.setItemRenderer(new ComboItemDataRenderer());
		}
		
		
		
		
		notAllowedForCentral.add("Reserved");
		notAllowedForCentral.add("Allocated");
		notAllowedForCentral.add("Delivered");
		notAllowedForCentral.add("Recovered");
	}


	@Override
	public void onDoubleClickedSearchItem(Event event) throws Exception {
	}
	
	public void onClick$btnSearch(Event event) {
		
		listheaders = new ArrayList<Listheader>();
		Logger.logTrace(MODULE, "Inside btnSearch ");
		searchResultGrid.setEmptyMessage(Labels.getLabel("gen.norecordfound"));
		searchResultGrid.setVisible(true);
		
		Iterator<Component>  iterator = summaryListHead.getChildren().iterator();
		
		while(iterator.hasNext()) {
			Component component = iterator.next();
			if(component.getId()!=null && !component.getId().isEmpty()) {
				iterator.remove();
//				summaryListHead.removeChild(component);
			}
		}
		InventoryManagementBD managementBD = new InventoryManagementBD(getBDSessionContext());
		List<ComboData> status = managementBD.getAllInventoryStatusData();
		if(status!=null && !status.isEmpty()) {
			for(ComboData comboData : status) {
				Listheader listheader = new Listheader(comboData.getName());
				listheader.setId(comboData.getName());
				summaryListHead.appendChild(listheader);
				listheaders.add(listheader);
			}
			Listheader listheader = new Listheader("Total");
			listheader.setId("Total");
			summaryListHead.appendChild(listheader);
			listheaders.add(listheader);
		}
		
		WareHouseBD  wareHouseBD = new WareHouseBD(getBDSessionContext());
		
		Long wareHouseId = 0L;
		
		if (cmbWarehouseName.getSelectedItem() != null) {
			ComboData selectedData = cmbWarehouseName.getSelectedItem().getValue();
			wareHouseId = selectedData.getId();
			String WHName = selectedData.getName();
			if(WHName.equals("Central")) {
				Iterator<Component>  iterator1 = summaryListHead.getChildren().iterator();
				
				while(iterator1.hasNext()) {
					Component component = iterator1.next();
					if(component.getId()!=null && !component.getId().isEmpty()) {
						iterator1.remove();
//						summaryListHead.removeChild(component);
					}
				}
				
				
				for(Listheader listheader : listheaders) {
					if(!notAllowedForCentral.contains(listheader.getId())) {
						summaryListHead.appendChild(listheader);
					}
				}
			}
		}
		try {
			List<WareHouseSummaryVO> listWarehouseSummaryData = wareHouseBD.searchWarehouseSummaryData(wareHouseId);
			
			System.out.println(listWarehouseSummaryData);
			
			if(listWarehouseSummaryData != null && !listWarehouseSummaryData.isEmpty()){
				btnDownload.setDisabled(false);
				searchResultGrid.setModel(new ListModelList<WareHouseSummaryVO>(listWarehouseSummaryData));
				searchResultGrid.setItemRenderer(new SearchListItemRenderer());
			}else{
				btnDownload.setDisabled(true);
				searchResultGrid.setModel(new ListModelList<WareHouseSummaryVO>());
			}
			
		} catch (SearchBLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void clickEdit(){
		Logger.logTrace(MODULE, "in clickEdit function...");
		
		if(searchResultGrid.getSelectedItem()!=null){
			WarehouseVO wrapperVO =(WarehouseVO) searchResultGrid.getSelectedItem().getValue();
			Map<String,Object> argMap = new HashMap<String, Object>();
			
			argMap.put(SEARCH_COMPOSER_REF, this);
			addViewTab(wrapperVO.getWarehouseId(), wrapperVO.getName(), searchWarehouseTabbox, Pages.VIEW_WAREHOUSE_EVENT,argMap);
		}
		
	}
	
	private class SearchListItemRenderer implements ListitemRenderer<WareHouseSummaryVO>{

		@Override
		public void render(Listitem item, WareHouseSummaryVO data, int index)throws Exception {
			
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getWareHouseName()));
			item.appendChild(new Listcell(data.getResourceName()));
			item.appendChild(new Listcell(data.getResourceType()));
			item.appendChild(new Listcell(data.getResourceSubType()));
			item.appendChild(new Listcell(data.getModel()));
			item.appendChild(new Listcell(data.getVendor()));
			
			if(listheaders!=null && !listheaders.isEmpty()) {
				Map<String, Long> map = data.getStatusCount();
				for(Listheader listheader : listheaders) {
					
					
					
					if(data.getWareHouseType().equals("Central")) {
						if(!notAllowedForCentral.contains(listheader.getId())) {
							if(map.containsKey(listheader.getId())) {
								item.appendChild(new Listcell(map.get(listheader.getId())+""));
							} else {
								item.appendChild(new Listcell("0"));
							}
						}
						
					} else {

						if(map.containsKey(listheader.getId())) {
							item.appendChild(new Listcell(map.get(listheader.getId())+""));
						} else {
							item.appendChild(new Listcell("0"));
						}
					}
					
					
					/*if(data.getWareHouseType().equals("Central")) {
						if(!notAllowedForCentral.contains(listheader.getId())) {
							if(map.containsKey(listheader.getId())) {
								item.appendChild(new Listcell(map.get(listheader.getId())+""));
							} else {
								item.appendChild(new Listcell("0"));
							}
						} else {
							List<Component> listHeaders  = summaryListHead.getChildren();
							Iterator<Component> iterate = listHeaders.iterator();
							while(iterate.hasNext()) {
								listheader = (Listheader) iterate.next();
							}
							
						}
					} else {
						if(map.containsKey(listheader.getId())) {
							item.appendChild(new Listcell(map.get(listheader.getId())+""));
						} else {
							item.appendChild(new Listcell("0"));
						}
					}*/
					
					
				}
			}
			
//			item.appendChild(new Listcell(data.getVendor()));
			
			item.setValue(data);
		}
		
	}
	
	public void onClick$btnDownload(Event event) throws Exception {
		
		searchResultGrid.renderAll();
		export_to_csv(searchResultGrid);
	}	
	
	
	public static void export_to_csv(Listbox listbox) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
		String date = dateFormat.format(new Date());
		
        String s = ",";
        StringBuffer sb = new StringBuffer();

        for (Object head : listbox.getHeads()) {
          String h = "";
          for (Object header : ((Listhead) head).getChildren()) {
            h += ((Listheader) header).getLabel() +s;
          }
          h = h.substring(0, h.lastIndexOf(","));
          sb.append(h + "\n");
        }
        for (Object item : listbox.getItems()) {
        	 StringBuffer i = new StringBuffer();
        	 String a = "";
          for (Object cell : ((Listitem) item).getChildren()) {
            i.append( ((Listcell) cell).getLabel() + s) ;
          }
          a = i.toString().substring(0, i.lastIndexOf(","));
          sb.append(a + "\n");
        }
        Filedownload.save(sb.toString().getBytes(), "text/csv", date+".csv");
      }
	
}
