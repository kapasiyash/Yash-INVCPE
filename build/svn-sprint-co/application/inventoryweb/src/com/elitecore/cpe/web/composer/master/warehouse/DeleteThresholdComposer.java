package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;
import com.elitecore.cpe.web.utils.MessageUtility;

public class DeleteThresholdComposer extends BaseModuleViewComposer {
	
	private static final long serialVersionUID = 1L;

	private Listbox configThresholdGrid,viewThresholdGrid;
	private WareHouseBD wareHouseBD;
	private Button btnDelete;
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		wareHouseBD = new WareHouseBD(getBDSessionContext());
		viewThresholdGrid.setVisible(false);
		configThresholdGrid.setCheckmark(true);
		fetchViewEntity();
	}
	private void fetchViewEntity(){
		
		ConfigureThresholdVO configureThresholdVO = new ConfigureThresholdVO();
		configureThresholdVO.setWarehouseID(getViewEntityId());
		List<ConfigureThresholdVO> listConfigureThresholdVOs = wareHouseBD.searchThresholdData(configureThresholdVO);
		try{
		Logger.logTrace("Threshold","Received data from DB:"+listConfigureThresholdVOs);
		if (listConfigureThresholdVOs != null && !listConfigureThresholdVOs.isEmpty()) {
			configThresholdGrid.setVisible(true);
			ListModelList<ConfigureThresholdVO> model = new ListModelList<ConfigureThresholdVO>(listConfigureThresholdVOs);
			model.setMultiple(true);
			configThresholdGrid.setModel(model);
			configThresholdGrid.setItemRenderer(new SearchListItemRenderer());
			btnDelete.setDisabled(false);

		} else {
			configThresholdGrid.setVisible(false);
			btnDelete.setDisabled(true);
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}

		}
	
	//--Added By Rinkal start
	public void onClick$btnDelete(Event event){
		
		final WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
//		IBDSessionContext sessionContext = getBDSessionContext();
		List<ConfigureThresholdVO> configureThresholdVOs=new ArrayList<ConfigureThresholdVO>();
		try {
			for(Listitem item:configThresholdGrid.getSelectedItems()){
				ConfigureThresholdVO configureThresholdVO =(ConfigureThresholdVO)item.getValue();
				configureThresholdVOs.add(configureThresholdVO);
				System.out.println("::configureThresholdVO.getResourceTypeName()::"+configureThresholdVO.getResourceTypeName()+":::configureThresholdVO.getResourceTypeID():::"+configureThresholdVO.getResourceTypeID()+"::::configureThresholdVO.getResourceSubTypeName():::"+configureThresholdVO.getResourceSubTypeName()+":::::configureThresholdVO.getResourceSubTypeID()::::"+configureThresholdVO.getResourceSubTypeID());
				System.out.println("configureThresholdVO."+configureThresholdVO.getWarehouseID()+"--configureThresholdVO.getThresholdID()-----"+configureThresholdVO.getThresholdID()+"-------------------");

			}
			if( (configureThresholdVOs!=null) && (!configureThresholdVOs.isEmpty()) ){
				System.out.println("In Composer ::::");
				wareHouseBD.deleteThreshold(configureThresholdVOs);
				MessageUtility.successInformation("Success", "Threshold Deleted Successfully");
				fetchViewEntity();
			}else{
				MessageUtility.failureInformation("Alert", "Please Select atleast One.");
			}			
			
		} catch (Exception e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
	}

	private static class SearchListItemRenderer implements ListitemRenderer<ConfigureThresholdVO>{
		@Override
		public void render(final Listitem item, ConfigureThresholdVO data, int index)throws Exception {
			Logger.logTrace("WAREHOUSE", "Data received:"+data);
			int no = index+1;
			item.appendChild(new Listcell(String.valueOf(no)));
			item.appendChild(new Listcell(data.getResourceTypeName()));
			item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceSubTypeName())));
			item.appendChild(new Listcell(data.getThresholdType()));
			item.appendChild(new Listcell(String.valueOf(data.getThresholdValue())));
			
			if(data.getQuantity()!=null) {
				item.appendChild(new Listcell(String.valueOf(data.getQuantity())));
			} else {
				item.appendChild(new Listcell(""));
			}
			
			item.appendChild(new Listcell(data.getEmail()));
			item.appendChild(new Listcell(data.getMobile()));
			item.setValue(data);
		/*	item.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					if(item.isSelected()){
						
						btnDelete.setDisabled(false);
					}
						}

			});*/
			
				
			/*item.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				if(i)
			});*/
			
		}
	}

}