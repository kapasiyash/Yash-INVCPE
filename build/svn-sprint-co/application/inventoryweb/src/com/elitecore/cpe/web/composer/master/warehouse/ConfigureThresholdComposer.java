/**
 * 
 */
package com.elitecore.cpe.web.composer.master.warehouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.master.ConfigureThresholdVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.GeneralUtility;
import com.elitecore.cpe.web.utils.MessageUtility;

/**
 * @author Joel.Macwan
 *
 */
public class ConfigureThresholdComposer extends BaseModuleViewComposer{

//	private Hlayout configureThreshold;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Intbox txtThreshold,txtQuantity;
	private Button btnSave,btnAddNew;
	private Combobox cmbResourceType,cmbResourceSubType,cmbThresholdType;
	private Listbox configThresholdGrid,viewThresholdGrid;
	private Div thresholdNoConfig,thresholdConfigDiv;
	private Textbox txtEmail,txtMobile;
	
	private Integer count;
	Map<ComboData,List<ComboData>> resultMap=null; 
	Map<String,Long> mapResourceType=new HashMap<String, Long>();;
	WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
	List<ComboBoxData> thresholdType=new ArrayList<ComboBoxData>();
	List<ConfigureThresholdVO> removeVOList=new ArrayList<ConfigureThresholdVO>();
	List<ConfigureThresholdVO> addVOList=new ArrayList<ConfigureThresholdVO>();
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		// TODO Auto-generated method stub
	//	this.configureThreshold = comp;
		viewThresholdGrid.setVisible(false);
		fetchViewEntity();
	}
	private void fetchViewEntity(){
		
		// WarehouseVO warehouseVO = new WarehouseVO();
		//
		// warehouseVO.setWarehouseId(getViewEntityId());
		ConfigureThresholdVO configureThresholdVO = new ConfigureThresholdVO();
		configureThresholdVO.setWarehouseID(getViewEntityId());
		List<ConfigureThresholdVO> listConfigureThresholdVOs = wareHouseBD.searchThresholdData(configureThresholdVO);
		count=0;
		try{
		Logger.logTrace("Threshold","Received data from DB:"+listConfigureThresholdVOs);
		resultMap = wareHouseBD.getAllResourceTypeWithResource(getViewEntityId());
		Logger.logTrace("Threshold","Size of ResourceType Map:"+resultMap.keySet().size());
		if (resultMap.keySet().size()==0){
		// Executions.sendRedirect("/configureThreshold_empty.zul");
		// Executions.createComponents("/WEB-INF/pages/core/master/warehouse/configureThreshold_empty.zul", configureThreshold, null);
			thresholdConfigDiv.setVisible(false);
			thresholdNoConfig.setVisible(true);
			
		}
		Logger.logTrace("Threshold","Received data from DB for combodata:"+resultMap);
		Collection<ComboData> resourceTypecomboBoxDatas = resultMap.keySet();
		ComboBoxData absolutecomboboxdata=new ComboBoxData("Absolute","Absolute");
		ComboBoxData percentcomboboxdata=new ComboBoxData("Percentage","Percentage");
		thresholdType.clear();
		thresholdType.add(absolutecomboboxdata);
		thresholdType.add(percentcomboboxdata);
		cmbThresholdType.setModel(new ListModelList<ComboBoxData>(thresholdType));
		cmbThresholdType.setItemRenderer(new ComboBoxItemDataRenderer());
		for (ComboData comboData2 : resourceTypecomboBoxDatas) {
			Logger.logInfo("Threshold","Key ComboData List ---:" + comboData2.getId() + "::"+ comboData2.getName());
			mapResourceType.put(comboData2.getName(), comboData2.getId());
			List<ComboData> comboBoxDatas2 = resultMap.get(comboData2);
			for (ComboData comboData3 : comboBoxDatas2) {
				Logger.logInfo("Threshold", "ComboData :" + comboData3.getId()+ "::--" + comboData3.getName());
			}
		}
		cmbResourceType.setModel(new ListModelList<ComboData>(resourceTypecomboBoxDatas));
		cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
		if (listConfigureThresholdVOs != null && !listConfigureThresholdVOs.isEmpty()) {
			count=listConfigureThresholdVOs.size();
			configThresholdGrid.setVisible(true);
			configThresholdGrid.setMultiple(true);
			configThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>(listConfigureThresholdVOs));
			configThresholdGrid.setItemRenderer(new SearchListItemRenderer());
			
		} else {
			configThresholdGrid.setVisible(false);
			
		//	configThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>());
		}
		}catch(Exception e){
			e.printStackTrace();
		}

		Logger.logTrace("Warehouse","mapResourceType:"+ mapResourceType );
		
		
		}

			public void onChange$cmbResourceType(Event e) throws InterruptedException{
			//	Messagebox.show("Hi combo");
				List<ComboData> comboBoxDatas=null;
			//	comboBoxDatas.clear();
				//String selectedValue=cmbResourceType.getSelectedItem().getLabel();
				if(cmbResourceType.getSelectedItem() != null){
				ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
				Logger.logInfo("WAREHOUSE", "ComboData :"+selectedData.getId()+"::"+selectedData.getName());
				 comboBoxDatas = resultMap.get(selectedData);
				for(ComboData comboData1:comboBoxDatas){
					Logger.logInfo("Threshold", "ComboData :"+comboData1.getId()+"::"+comboData1.getName());
				}
			
				cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
				cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
				
			}
		//	Messagebox.show("Hi combo:"+cmbResourceType.getSelectedItem().getLabel());
				
				}
			public void onChange$cmbThresholdType(Event e) throws InterruptedException{
				if(cmbThresholdType.getSelectedItem() != null){

					ComboBoxData comboBoxData = cmbThresholdType.getSelectedItem().getValue();
					if (comboBoxData.getName().equalsIgnoreCase("Percentage")) {
							txtThreshold.setMaxlength(3);
						}else{
							txtThreshold.setMaxlength(5);
						}
					}
				
				}
		
////		Logger.logInfo("WAREHOUSE", "size of warehouse list :"+comboBoxDatas.size());
//		cmbResourceType.setModel(new ListModelList<ComboData>(comboBoxDatas));
//		cmbResource.setItemRenderer(new ComboItemDataRenderer());
//		
//		List<ComboData> comboBoxDatas1 = wareHouseBD.getAllWarehouseTypeData();
//		cmbWHTypename.setModel(new ListModelList<ComboData>(comboBoxDatas1));
//		cmbWHTypename.setItemRenderer(new ComboItemDataRenderer());
		
	//	populateData(data);
		
//	}
	
/*	private void populateData(WarehouseVO data){
		
		if(data != null){
			txtName.setValue(data.getName());
			txtLocation.setValue(data.getLocation());
			txtDesc.setValue(data.getDescription());
			
			cmbParentWHname.setValue(data.getParentWarehouseName());
			if(data.getWarehouseType() != null){
				cmbWHTypename.setValue(data.getWarehouseType().getName());
			}
		}
		
	}*/
			
			
			public void onClick$btnSubmit(Event e) throws InterruptedException{
				Logger.logTrace("Warehouse","Into  onClick$btnSubmit()");
				
				
				List<ConfigureThresholdVO> receivedConfigureThresholdVOs=null;
			//	IBDSessionContext sessionContext = getBDSessionContext();
				
			try{	
				
			
						
						List<ConfigureThresholdVO> 	targetConfigureThresholdVOs=(List<ConfigureThresholdVO>)configThresholdGrid.getModel();
						receivedConfigureThresholdVOs=new ArrayList<ConfigureThresholdVO>();
						Logger.logTrace("Warehouse","Into  onClick$btnSubmit()receivedConfigureThresholdVOs.size():"+receivedConfigureThresholdVOs.size());
						if(targetConfigureThresholdVOs!=null && !targetConfigureThresholdVOs.isEmpty()){
							Logger.logTrace("Warehouse","Into  onClick$btnSubmit() targetConfigureThresholdVOs is not empty ");
							for(ConfigureThresholdVO thresholdVO:targetConfigureThresholdVOs){
								Logger.logTrace("Warehouse","Send for saveThreshold() VO:"+ thresholdVO );
								receivedConfigureThresholdVOs.add(thresholdVO);
							}

						}
						else{
							System.out.println("In else condition of remove part");
							ConfigureThresholdVO singleThresholdVO=new ConfigureThresholdVO();
							singleThresholdVO.setWarehouseID(getViewEntityId());
//							ConfigureThresholdVO thresholdVo1=removeVOList.get(0);
							receivedConfigureThresholdVOs.add(singleThresholdVO);							
							//--Added By Rinkal
					    	//receivedConfigureThresholdVOs.addAll(removeVOList);
							
						}
						if(receivedConfigureThresholdVOs!=null && (!receivedConfigureThresholdVOs.isEmpty() || (receivedConfigureThresholdVOs.size()<count))){
							Logger.logTrace("Warehouse","Into  onClick$btnSubmit() receivedConfigureThresholdVOs is not null "+receivedConfigureThresholdVOs.toString());
							wareHouseBD.saveThreshold(receivedConfigureThresholdVOs);
							Logger.logTrace("Warehouse","After calling saveThreshold()");
						}
						else{
							Logger.logTrace("Warehouse","Nothing to send for saveThreshold():"+ receivedConfigureThresholdVOs );
						}
				
				MessageUtility.successInformation("Success", "Threshold Configured Successfully");
				removeVOList.clear();
				//reset();
				fetchViewEntity();
			}catch (CreateBLException exp) {
				exp.printStackTrace();
				MessageUtility.failureInformation("ERROR", exp.getMessage());
			}
					
		}
			
			public void onClick$btnAddNew(Event e) throws InterruptedException{
				
				ConfigureThresholdVO configureThresholdVO=new ConfigureThresholdVO();
				IBDSessionContext sessionContext = getBDSessionContext();
				List<ConfigureThresholdVO> configureThresholdVOs=null;
				boolean flag=false;
			try{	
				configureThresholdVO.setWarehouseID(getViewEntityId());
				configureThresholdVO.setCreatedby(sessionContext.getBLSession().getSessionUserId());
				configureThresholdVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
				
				if(cmbResourceType.getSelectedItem() != null){
					ComboData resourceTypeSelectedData = cmbResourceType.getSelectedItem().getValue();
					configureThresholdVO.setResourceTypeName(resourceTypeSelectedData.getName());
					configureThresholdVO.setResourceTypeID(resourceTypeSelectedData.getId());
					if(cmbResourceSubType.getSelectedItem() != null){
						
						ComboData selectedData = cmbResourceSubType.getSelectedItem().getValue();
						configureThresholdVO.setResourceSubTypeID(selectedData.getId());
						configureThresholdVO.setResourceSubTypeName(selectedData.getName());
						
					}
					if(cmbThresholdType.getSelectedItem() != null){
						
						ComboBoxData comboBoxData = cmbThresholdType.getSelectedItem().getValue();
						if(comboBoxData.getName().equalsIgnoreCase("Percentage")){
							if(Long.parseLong(txtThreshold.getValue().toString())>100){
								MessageUtility.successInformation("Threshold", "Please Enter valid Threshold value");
								return;
							}else{
								configureThresholdVO.setThresholdType(comboBoxData.getName());
							}
						}else{
							configureThresholdVO.setThresholdType(comboBoxData.getName());
						}
						
						
					}
					
				}else{
					MessageUtility.successInformation("Success", "Please Select Resource Type");
					return;
				}
				if(Long.parseLong(txtThreshold.getValue().toString())>0){
				configureThresholdVO.setThresholdValue(Long.parseLong(txtThreshold.getValue().toString()));
				}
				else{
					MessageUtility.successInformation("Threshold", "Please Enter valid Threshold value");
					return;
				}
				
				if(txtQuantity.getValue()!=null && txtQuantity.getValue()==0) {
					MessageUtility.successInformation("Order Quantity", "Please Enter valid Quantity");
					return;
				} else {
					configureThresholdVO.setQuantity(txtQuantity.getValue());
				}
				configureThresholdVO.setEmail(txtEmail.getValue());
				configureThresholdVO.setMobile(txtMobile.getValue());
				
				
			//	wareHouseBD.saveThreshold(configureThresholdVO);
				
			//	MessageUtility.successInformation("Success", "Threshold Configured Successfully");
//				ListModel<ConfigureThresholdVO> testModel=configThresholdGrid.getModel();
//				for(ListModelList<configureThresholdVO> testModel:configThresholdGrid.getModel()){
//					
//				}
				///////////////////////////////////////////////////////////////////////
				configureThresholdVOs=(List<ConfigureThresholdVO>)configThresholdGrid.getModel();
				Logger.logTrace("Threshold","New Threshold Config VO:"+ configureThresholdVO );
				if(configureThresholdVOs!=null && !configureThresholdVOs.isEmpty()){
					for(ConfigureThresholdVO thresholdVO:configureThresholdVOs){
						Logger.logTrace("Threshold","Existing Threshold Config VO:"+ thresholdVO );
						
						if(thresholdVO.getResourceSubTypeName()!=null && configureThresholdVO.getResourceSubTypeName()!=null){
							Logger.logTrace("Threshold","Inside on click Add New button Inside first if block" );
						if(thresholdVO.getResourceSubTypeName().equals(configureThresholdVO.getResourceSubTypeName()) 
							 
								&& thresholdVO.getResourceTypeName().equals(configureThresholdVO.getResourceTypeName())){
							Logger.logTrace("Threshold","Inside on click Add New button Inside second if block" );
							flag=true;
							MessageUtility.successInformation("Threshold", "Threshold Already Configured");
							
						}
						}else if(thresholdVO.getResourceSubTypeName()==null && configureThresholdVO.getResourceSubTypeName()==null){
							 if(thresholdVO.getResourceTypeName().equals(configureThresholdVO.getResourceTypeName())){
								Logger.logTrace("Threshold","thresholdVO.getResourceSubTypeName()!=null && configureThresholdVO.getResourceSubTypeName()!=null false" );
								flag=true;
								MessageUtility.successInformation("Threshold", "Threshold Already Configured");
							}
						}
						
						if(flag)
						break;
						
					}
					//configureThresholdVOs.add(configureThresholdVO);
				}
				
				//////////////////////////////////////////////////////////////////
			//	configureThresholdVOs=(List<ConfigureThresholdVO>)configThresholdGrid.getModel();
				if(!flag){
					if (configureThresholdVOs != null && !configureThresholdVOs.isEmpty()) {
						configureThresholdVOs.add(configureThresholdVO);
					} else {
						configureThresholdVOs = new ArrayList<ConfigureThresholdVO>();
						configureThresholdVOs.add(configureThresholdVO);
					}
					//--Added Rinkal
					addVOList.add(configureThresholdVO);
				}
				System.out.println("::::::::addVOList:::::::::"+addVOList);
				
			if (configureThresholdVOs != null && !configureThresholdVOs.isEmpty()) {
				configThresholdGrid.setVisible(true);
				configThresholdGrid.setMultiple(true);
				configThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>(configureThresholdVOs));
				configThresholdGrid.setItemRenderer(new SearchListItemRenderer());
			} else {
				//configThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>());
				configThresholdGrid.setVisible(false);
			}
			Logger.logTrace("Inside Add New()","configureThresholdVO::"+ configureThresholdVO );
			txtThreshold.setValue(0);
			reset();
			}catch (Exception exp) {
				exp.printStackTrace();
				MessageUtility.failureInformation("ERROR", exp.getMessage());
			}
					
		}
			
			
			
			public void onClick$btnSave(Event e) throws InterruptedException{
				Logger.logTrace("Warehouse","Inside onClick event of Save ");
				btnAddNew.setDisabled(true);
				 cmbResourceType.setDisabled(false);
				 cmbResourceSubType.setDisabled(false);
				 cmbThresholdType.setDisabled(false);
				ConfigureThresholdVO configureThresholdVO=new ConfigureThresholdVO();
				IBDSessionContext sessionContext = getBDSessionContext();
				List<ConfigureThresholdVO> configureThresholdVOs=null;
				boolean flag=false;
			try{	
				configureThresholdVO.setWarehouseID(getViewEntityId());
				configureThresholdVO.setCreatedby(sessionContext.getBLSession().getSessionUserId());
				configureThresholdVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
				
				if(cmbResourceType.getSelectedItem() != null){
					ComboData resourceTypeSelectedData = cmbResourceType.getSelectedItem().getValue();
					configureThresholdVO.setResourceTypeName(resourceTypeSelectedData.getName());
					if(cmbResourceSubType.getSelectedItem() != null){
						
						ComboData selectedData = cmbResourceSubType.getSelectedItem().getValue();
						configureThresholdVO.setResourceSubTypeID(selectedData.getId());
						configureThresholdVO.setResourceSubTypeName(selectedData.getName());
						
					}
					if(cmbThresholdType.getSelectedItem() != null){
						
						ComboBoxData comboBoxData = cmbThresholdType.getSelectedItem().getValue();
					
						configureThresholdVO.setThresholdType(comboBoxData.getName());
						
					}
					
				}else{
					MessageUtility.successInformation("Threshold", "Please Select Resource Type");
					return;
				}
				if(Long.parseLong(txtThreshold.getValue().toString())>0){
					if(configureThresholdVO.getThresholdType().equals("Percentage")){
						
							if(Long.parseLong(txtThreshold.getValue().toString())>100){
								MessageUtility.successInformation("Threshold", "Please Enter valid Threshold value");
								return;
							}else{
								configureThresholdVO.setThresholdValue(Long.parseLong(txtThreshold.getValue().toString()));
							}
					}else{
						configureThresholdVO.setThresholdValue(Long.parseLong(txtThreshold.getValue().toString()));
					}
				
				}else{
					MessageUtility.successInformation("Threshold", "Please Enter valid Threshold value");
					return;
				}
				
				
				if(txtQuantity.getValue()!=null && txtQuantity.getValue()<=0) {
					MessageUtility.successInformation("Order Quantity", "Please Enter valid Quantity");
					return;
				} else {
					configureThresholdVO.setQuantity(txtQuantity.getValue());
				}
				configureThresholdVO.setEmail(txtEmail.getValue());
				configureThresholdVO.setMobile(txtMobile.getValue());
					
				Logger.logTrace("Inside Save()","configureThresholdVO:::"+ configureThresholdVO );
				configureThresholdVOs=(List<ConfigureThresholdVO>)configThresholdGrid.getModel();
				if(configureThresholdVOs!=null && !configureThresholdVOs.isEmpty()){
					for(ConfigureThresholdVO thresholdVO:configureThresholdVOs){
						Logger.logTrace("Inside Save() before:","thresholdVO:::"+ thresholdVO );
						if(thresholdVO.getResourceSubTypeName()!=null && configureThresholdVO.getResourceSubTypeName()!=null){
						if(thresholdVO.getResourceSubTypeName().equals(configureThresholdVO.getResourceSubTypeName()) && thresholdVO.getResourceTypeName().equals(configureThresholdVO.getResourceTypeName())){
							
							thresholdVO.setThresholdValue(configureThresholdVO.getThresholdValue());
							thresholdVO.setEmail(configureThresholdVO.getEmail());
							thresholdVO.setMobile(configureThresholdVO.getMobile());
							thresholdVO.setQuantity(configureThresholdVO.getQuantity());
							
							Logger.logTrace("Inside Save() if block","thresholdVO having resourcesubtype:::"+ thresholdVO );
							flag=true;
						}
						}else if(thresholdVO.getResourceTypeName().equals(configureThresholdVO.getResourceTypeName()) 
								&& (thresholdVO.getResourceSubTypeName()==null && configureThresholdVO.getResourceSubTypeName()==null)){
							thresholdVO.setThresholdValue(configureThresholdVO.getThresholdValue());
							thresholdVO.setEmail(configureThresholdVO.getEmail());
							thresholdVO.setMobile(configureThresholdVO.getMobile());
							thresholdVO.setQuantity(configureThresholdVO.getQuantity());
							
							Logger.logTrace("Inside Save() if block","thresholdVO without resourcesubtype:::"+ thresholdVO );
							flag=true;
						}
						if(flag)
							break;
						
						
					}
					//configureThresholdVOs.add(configureThresholdVO);
				}
				
			if (configureThresholdVOs != null && !configureThresholdVOs.isEmpty()) {
				configThresholdGrid.setVisible(true);
				configThresholdGrid.setMultiple(true);
				configThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>(configureThresholdVOs));
				configThresholdGrid.setItemRenderer(new SearchListItemRenderer());
			} else {
				//configThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>());
					configThresholdGrid.setVisible(false);
				}
			txtThreshold.setValue(0);
			reset();
			Collection<ComboData> resourceTypecomboBoxDatas = resultMap.keySet();
			cmbResourceType.setModel(new ListModelList<ComboData>(resourceTypecomboBoxDatas));
			cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
//			cmbThresholdType.setModel(new ListModelList<ComboBoxData>(thresholdType));
//			cmbThresholdType.setItemRenderer(new ComboBoxItemDataRenderer());
			
			}catch (Exception exp) {
				exp.printStackTrace();
				MessageUtility.failureInformation("ERROR", exp.getMessage());
			}
					
		}
			
			
			private void reset(){
				
				resetComponents(txtThreshold,cmbResourceType,cmbResourceSubType,cmbThresholdType,txtEmail,txtMobile,txtQuantity);
				cmbResourceSubType.setModel(null);
				btnSave.setDisabled(true);
				btnAddNew.setDisabled(false);
				btnSave.setVisible(false);
				txtThreshold.setMaxlength(5);
			}
			
			
			private class SearchListItemRenderer implements ListitemRenderer<ConfigureThresholdVO>{

				private EventListener<Event> editItemListener,editHoverListner,editOutListener,removeItemListener,removeHoverListner,removeOutListener;
				
				public SearchListItemRenderer() {
					
				
					editItemListener = new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
						//	Image img = (Image) event.getTarget();
							clickEdit();
						}

					};
					
					editHoverListner = new EventListener<Event>() {
						
						@Override
						public void onEvent(Event event) throws Exception {
							Image img = (Image) event.getTarget();
							img.setSrc(BaseConstants.EDIT_ITEM_HOVER_IMAGE);
						}
					};
					
					editOutListener = new EventListener<Event>() {
						
						@Override
						public void onEvent(Event event) throws Exception {
							Image img = (Image) event.getTarget();
							img.setSrc(BaseConstants.EDIT_ITEM_IMAGE);
						}
					};
					removeItemListener = new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
						//	Image img = (Image) event.getTarget();
							clickRemove();
						}

					};
					
					removeHoverListner = new EventListener<Event>() {
						
						@Override
						public void onEvent(Event event) throws Exception {
							Image img = (Image) event.getTarget();
							img.setSrc(BaseConstants.REMOVE_ITEM_HOVER_IMAGE);
						}
					};
					
					removeOutListener = new EventListener<Event>() {
						
						@Override
						public void onEvent(Event event) throws Exception {
							Image img = (Image) event.getTarget();
							img.setSrc(BaseConstants.REMOVE_ITEM_IMAGE);
						}
					};
				}
				
				@Override
				public void render(Listitem item, ConfigureThresholdVO data, int index)throws Exception {
					
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
					
					Listcell operationCell = new Listcell();
					Image edit = new Image(BaseConstants.EDIT_ITEM_IMAGE);
					
					edit.addEventListener(Events.ON_MOUSE_OVER, editHoverListner);
					edit.addEventListener(Events.ON_MOUSE_OUT, editOutListener);
					edit.addEventListener(Events.ON_CLICK, editItemListener);
					
					operationCell.appendChild(edit);
					item.appendChild(operationCell);
					Listcell removeOperationCell = new Listcell();
					Image remove = new Image(BaseConstants.REMOVE_ITEM_IMAGE);
					
					remove.addEventListener(Events.ON_MOUSE_OVER, removeHoverListner);
					remove.addEventListener(Events.ON_MOUSE_OUT, removeOutListener);
					remove.addEventListener(Events.ON_CLICK, removeItemListener);
					
					removeOperationCell.appendChild(remove);
					item.appendChild(removeOperationCell);
					item.setValue(data);
					
				}
				
			}
			public void clickEdit(){
				
				Logger.logTrace("WAREHOUSE", "in clickEdit function...");
				ConfigureThresholdVO editVO;
				List<ComboData> comboBoxDatas=null;
				Collection<ComboData> resourceTypecomboBoxDatas=null;
				btnAddNew.setDisabled(true);
				btnSave.setDisabled(false);
				try{
					
				
				if(configThresholdGrid.getSelectedItem()!=null){
					 editVO =(ConfigureThresholdVO) configThresholdGrid.getSelectedItem().getValue();
					 Logger.logTrace("WAREHOUSE", "VO receive in Edit button:"+editVO);
					 ComboData comboData=new ComboData();
					 comboData.setId(mapResourceType.get(editVO.getResourceTypeName()));
					 comboData.setName(editVO.getResourceTypeName());
					 resourceTypecomboBoxDatas=new ArrayList<ComboData>();
					 resourceTypecomboBoxDatas.add(comboData);
					 cmbResourceType.setModel(new ListModelList<ComboData>(resourceTypecomboBoxDatas));
					 cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
					 cmbResourceType.setValue(editVO.getResourceTypeName());
					
					 cmbResourceType.setDisabled(true);
					
					 comboBoxDatas = resultMap.get(comboData);
					 	cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
					 	cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
					 	cmbResourceSubType.setValue(editVO.getResourceSubTypeName());
					 	cmbResourceSubType.setDisabled(true);
					 	
					 	cmbThresholdType.setModel(new ListModelList<ComboBoxData>(thresholdType));
						cmbThresholdType.setItemRenderer(new ComboBoxItemDataRenderer());
						cmbThresholdType.setValue(editVO.getThresholdType());
						cmbThresholdType.setDisabled(true);
						if(editVO.getThresholdType().equalsIgnoreCase("Percentage")){
							txtThreshold.setMaxlength(3);	
						}else{
							txtThreshold.setMaxlength(5);
						}
						txtThreshold.setValue(editVO.getThresholdValue().intValue());
						if(editVO.getEmail()!=null) {
							txtEmail.setValue(editVO.getEmail());
						}
						if(editVO.getMobile()!=null) {
							txtMobile.setValue(editVO.getMobile());
						}
						txtQuantity.setValue(editVO.getQuantity());
						
						btnSave.setVisible(true);
						
					}
				}catch(Exception ex){
					ex.printStackTrace();
					
				}
					
					
				}
				
			
			
			
			public void clickRemove(){
				Logger.logTrace("WAREHOUSE", "in clickRemove function...");
				ConfigureThresholdVO removeVO=null;
				if(configThresholdGrid.getSelectedItem()!=null){
					 removeVO =(ConfigureThresholdVO) configThresholdGrid.getSelectedItem().getValue();
					Logger.logTrace("WAREHOUSE","Selected cell to be remove:"+removeVO);
				}
				
				List<ConfigureThresholdVO> configureThresholdVOs=null;
			try{	
			
				configureThresholdVOs=(List<ConfigureThresholdVO>)configThresholdGrid.getModel();
				if(configureThresholdVOs!=null && !configureThresholdVOs.isEmpty()){
					if(removeVO!=null){
					//Added By Rinkal
						removeVOList.add(removeVO);
					configureThresholdVOs.remove(removeVO);
					}
				}
				System.out.println("::::::removeVOList:::::::"+removeVOList);
			if (configureThresholdVOs != null && !configureThresholdVOs.isEmpty()) {
				configThresholdGrid.setVisible(true);
				configThresholdGrid.setMultiple(true);
				configThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>(configureThresholdVOs));
				configThresholdGrid.setItemRenderer(new SearchListItemRenderer());
			} else {
				
				configThresholdGrid.setVisible(false);
			}
			reset();
			}catch (Exception exp) {
				exp.printStackTrace();
				MessageUtility.failureInformation("ERROR", exp.getMessage());
			}
				
			}
			

}
