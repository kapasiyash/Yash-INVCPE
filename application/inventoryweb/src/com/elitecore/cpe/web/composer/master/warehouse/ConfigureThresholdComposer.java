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
import org.zkoss.zul.Comboitem;
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
import com.elitecore.cpe.bl.delegates.master.ItemBD;
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
	private Combobox cmbResourceType,cmbResourceSubType,cmbThresholdType,cmbResource,cmbAutomaticOrder;
	private Listbox configThresholdGrid,viewThresholdGrid;
	private Div thresholdNoConfig,thresholdConfigDiv;
	private Textbox txtEmail,txtMobile;
	
	private ListModelList<ConfigureThresholdVO> thresholdModel;
	private int editIndex;
	private boolean editMode;
	private ConfigureThresholdVO editConfigureThresholdVO;
	
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
		
		thresholdModel = new ListModelList<ConfigureThresholdVO>();
		
		viewThresholdGrid.setVisible(false);
		fetchViewEntity();
	}
	private void fetchViewEntity(){
		
		
		
		List<ComboBoxData> automaticOrder = new ArrayList<ComboBoxData>();
		automaticOrder.add(new ComboBoxData("Yes", "Yes"));
		automaticOrder.add(new ComboBoxData("No", "No"));
		cmbAutomaticOrder.setModel(new ListModelList<ComboBoxData>(automaticOrder));
		cmbAutomaticOrder.setItemRenderer(new ComboBoxItemDataRenderer());
		
		ConfigureThresholdVO configureThresholdVO = new ConfigureThresholdVO();
		configureThresholdVO.setWarehouseID(getViewEntityId());
		List<ConfigureThresholdVO> listConfigureThresholdVOs = wareHouseBD.searchThresholdData(configureThresholdVO);
		count=0;
		try{
		Logger.logTrace("Threshold","Received data from DB:"+listConfigureThresholdVOs);
		resultMap = wareHouseBD.getAllResourceTypeWithResource(getViewEntityId());
		Logger.logTrace("Threshold","Size of ResourceType Map:"+resultMap.keySet().size());
		if (resultMap.keySet().size()==0){
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
			thresholdModel = new ListModelList<ConfigureThresholdVO>(listConfigureThresholdVOs);
			configThresholdGrid.setModel(thresholdModel);
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

	
	
	public void onChange$cmbAutomaticOrder(Event event) {
		
		if (cmbAutomaticOrder.getSelectedItem() != null) {
			ComboBoxData comboBoxData = cmbAutomaticOrder.getSelectedItem().getValue();
			if(comboBoxData.getName().equals("No")) {
				txtQuantity.setValue(null);
				txtQuantity.setDisabled(true);
			} else {
				txtQuantity.setDisabled(false);
			}
		}
		
	}
	
	public void onChange$cmbResourceSubType(Event e) throws InterruptedException {
		
		cmbResource.setSelectedItem(null);
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		if (cmbResourceType.getSelectedItem() != null) {
			ComboData selectedTypeData = cmbResourceType.getSelectedItem().getValue();
			
			if (cmbResourceSubType.getSelectedItem() != null) {
				ComboData selectedSubTypeData = cmbResourceSubType.getSelectedItem().getValue();
				
				List<ComboData> resourceComboBoxDatas = itemBD.getAllResourceTypeDataByResourceTypeAndSubTypeId(selectedTypeData.getId(),selectedSubTypeData.getId(),getViewEntityId());
				if(resourceComboBoxDatas!=null && !resourceComboBoxDatas.isEmpty()){
					cmbResource.setModel(new ListModelList<ComboData>(resourceComboBoxDatas));
					cmbResource.setItemRenderer(new ComboItemDataRenderer());
				}
			}
			
		}	
		
	}
	
	public void onChange$cmbResourceType(Event e) throws InterruptedException {

		List<ComboData> comboBoxDatas = null;
		ItemBD itemBD = new ItemBD(getBDSessionContext());

		cmbResourceSubType.setSelectedItem(null);
		cmbResource.setSelectedItem(null);
		
		if (cmbResourceType.getSelectedItem() != null) {
			ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
			
			List<ComboData> resourceComboBoxDatas = itemBD.getAllResourceTypeDataByResourceTypeId(selectedData.getId(),getViewEntityId());
			if(resourceComboBoxDatas!=null && !resourceComboBoxDatas.isEmpty()){
				cmbResource.setModel(new ListModelList<ComboData>(resourceComboBoxDatas));
				cmbResource.setItemRenderer(new ComboItemDataRenderer());
			}
			
			Logger.logInfo("WAREHOUSE", "ComboData :" + selectedData.getId()+ "::" + selectedData.getName());
			comboBoxDatas = resultMap.get(selectedData);
			
			

			cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
			cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());

		}

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

			
			public void onClick$btnSubmit(Event e) throws InterruptedException{
				Logger.logTrace("Warehouse","Into  onClick$btnSubmit()");
				
				
				List<ConfigureThresholdVO> receivedConfigureThresholdVOs=null;
				
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
					
					
					if(cmbResource.getSelectedItem() != null){
						
						ComboData selectedData = cmbResource.getSelectedItem().getValue();
						configureThresholdVO.setItemId(selectedData.getId());
						configureThresholdVO.setResourceName(selectedData.getName());
						
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
				
				if(cmbAutomaticOrder.getSelectedItem() != null){
					
					ComboBoxData selectedData = cmbAutomaticOrder.getSelectedItem().getValue();
					configureThresholdVO.setAutomaticOrder(selectedData.getName());
					
				} else {
					MessageUtility.successInformation("Error", "Please Enter select Automatic Order");
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
				
				
				configureThresholdVOs=(List<ConfigureThresholdVO>)configThresholdGrid.getModel();
				if(configureThresholdVOs!=null && !configureThresholdVOs.isEmpty()){
					for(ConfigureThresholdVO thresholdVO:configureThresholdVOs){
						
						String key = GeneralUtility.blankDisplayValueIfNull(configureThresholdVO.getResourceSubTypeName())+"#"+GeneralUtility.blankDisplayValueIfNull(configureThresholdVO.getResourceTypeName())+"#"+GeneralUtility.blankDisplayValueIfNull(configureThresholdVO.getResourceName());
						
						System.out.println("KEY ::" + key + "   Grid Key :: "+ thresholdVO.getKey());
						
						if(key.equals(thresholdVO.getKey())) {
							MessageUtility.failureInformation("Threshold", "Threshold Already Configured");
							flag=true;
							break;
						}
						
						/*Logger.logTrace("Threshold","Existing Threshold Config VO:"+ thresholdVO );
						
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
						break;*/
						
					}
				}
				
				if(!flag){
					
					//--Added Rinkal
					thresholdModel.add(configureThresholdVO);
				}
				System.out.println("::::::::addVOList:::::::::"+thresholdModel);
				
				configThresholdGrid.setVisible(true);
				configThresholdGrid.setMultiple(true);
				configThresholdGrid.setModel(thresholdModel);
				configThresholdGrid.setItemRenderer(new SearchListItemRenderer());
				
			/*if (configureThresholdVOs != null && !configureThresholdVOs.isEmpty()) {
			} else {
				//configThresholdGrid.setModel(new ListModelList<ConfigureThresholdVO>());
				configThresholdGrid.setVisible(false);
			}*/
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
				
				ConfigureThresholdVO configureThresholdVO=this.editConfigureThresholdVO;
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
					
					if(cmbResource.getSelectedItem() != null){
						
						ComboData selectedData = cmbResource.getSelectedItem().getValue();
						configureThresholdVO.setItemId(selectedData.getId());
						configureThresholdVO.setResourceName(selectedData.getName());
						
					}
					
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
				
				if(cmbAutomaticOrder.getSelectedItem() != null){
					ComboBoxData comboBoxData = cmbAutomaticOrder.getSelectedItem().getValue();
					configureThresholdVO.setAutomaticOrder(comboBoxData.getName());
					
				} else {
					MessageUtility.failureInformation("Error", "Please Enter select Automatic Order");
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
				/*configureThresholdVOs=(List<ConfigureThresholdVO>)configThresholdGrid.getModel();
				if(configureThresholdVOs!=null && !configureThresholdVOs.isEmpty()){
					for(ConfigureThresholdVO thresholdVO:configureThresholdVOs){
						Logger.logTrace("Inside Save() before:","thresholdVO:::"+ thresholdVO );
						
						String key = GeneralUtility.blankDisplayValueIfNull(configureThresholdVO.getResourceSubTypeName())+"#"+GeneralUtility.blankDisplayValueIfNull(configureThresholdVO.getResourceTypeName())+"#"+GeneralUtility.blankDisplayValueIfNull(configureThresholdVO.getResourceName());
						
						System.out.println("KEY ::" + key + "   Grid Key :: "+ thresholdVO.getKey());
						
						if(key.equals(thresholdVO.getKey())) {
							MessageUtility.failureInformation("Threshold", "Threshold Already Configured");
							flag=true;
							break;
						}
						
						
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
			*/
				
			
			thresholdModel.remove(this.editIndex);
			thresholdModel.add(this.editIndex, configureThresholdVO);
			configThresholdGrid.setModel(thresholdModel);
			configThresholdGrid.setItemRenderer(new SearchListItemRenderer());
			
			txtThreshold.setValue(0);
			
			Collection<ComboData> resourceTypecomboBoxDatas = resultMap.keySet();
			cmbResourceType.setModel(new ListModelList<ComboData>(resourceTypecomboBoxDatas));
			cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
//			cmbThresholdType.setModel(new ListModelList<ComboBoxData>(thresholdType));
//			cmbThresholdType.setItemRenderer(new ComboBoxItemDataRenderer());

			btnAddNew.setDisabled(true);
			cmbResourceType.setDisabled(false);
			cmbResourceSubType.setDisabled(false);
			cmbThresholdType.setDisabled(false);
			cmbResource.setDisabled(false);
			
			
			reset();
			
			}catch (Exception exp) {
				exp.printStackTrace();
				MessageUtility.failureInformation("ERROR", exp.getMessage());
			}
			
			
		}
			
			
			private void reset(){
				
				resetComponents(txtThreshold,cmbResourceType,cmbResourceSubType,cmbResource,cmbThresholdType,txtEmail,txtMobile,txtQuantity,cmbAutomaticOrder);
				cmbResourceSubType.setModel(null);
				cmbResource.setModel(null);
				btnSave.setDisabled(true);
				btnAddNew.setDisabled(false);
				btnSave.setVisible(false);
				txtThreshold.setMaxlength(5);
			}
			
			
			private class SearchListItemRenderer implements ListitemRenderer<ConfigureThresholdVO>{

				private EventListener<Event> editItemListener,editHoverListner,editOutListener,removeItemListener,removeHoverListner,removeOutListener;
				private static final String THRESHOLD_DATA="THRESHOLD_DATA";
				
				public SearchListItemRenderer() {
					
				
					editItemListener = new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							Image img = (Image) event.getTarget();
							clickEdit((Integer) img.getAttribute(THRESHOLD_DATA));
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
							Image img = (Image) event.getTarget();
							clickRemove((Integer) img.getAttribute(THRESHOLD_DATA));
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
					
					String key = GeneralUtility.blankDisplayValueIfNull(data.getResourceSubTypeName())+"#"+GeneralUtility.blankDisplayValueIfNull(data.getResourceTypeName())+"#"+GeneralUtility.blankDisplayValueIfNull(data.getResourceName());
					data.setKey(key);
					
					item.appendChild(new Listcell(String.valueOf(no)));
					
					item.appendChild(new Listcell(data.getResourceTypeName()));
					
					item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceSubTypeName())));
					item.appendChild(new Listcell(GeneralUtility.displayValueIfNull(data.getResourceName())));
					
					
					item.appendChild(new Listcell(data.getThresholdType()));
					item.appendChild(new Listcell(String.valueOf(data.getThresholdValue())));
					
					item.appendChild(new Listcell(data.getAutomaticOrder()));
					
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
					edit.setAttribute(THRESHOLD_DATA, index);
					
					operationCell.appendChild(edit);
					item.appendChild(operationCell);
					Listcell removeOperationCell = new Listcell();
					Image remove = new Image(BaseConstants.REMOVE_ITEM_IMAGE);
					
					remove.addEventListener(Events.ON_MOUSE_OVER, removeHoverListner);
					remove.addEventListener(Events.ON_MOUSE_OUT, removeOutListener);
					remove.addEventListener(Events.ON_CLICK, removeItemListener);
					remove.setAttribute(THRESHOLD_DATA, index);
					
					removeOperationCell.appendChild(remove);
					item.appendChild(removeOperationCell);
					item.setValue(data);
					
				}
				
			}
			public void clickEdit(int index){
				
				Logger.logTrace("WAREHOUSE", "in clickEdit function...");
				
				this.editIndex= index;
				this.editMode =true;
				this.editConfigureThresholdVO= thresholdModel.get(index);
				
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
						
						List<ComboData> comboitem = new ArrayList<ComboData>();
						comboitem.add(new ComboData(editVO.getItemId(), editVO.getResourceName()));
						cmbResource.setModel(new ListModelList<ComboData>(comboitem));
						cmbResource.setItemRenderer(new ComboItemDataRenderer());
						cmbResource.setValue(editVO.getResourceName());
						cmbResource.setDisabled(true);
						
						btnSave.setVisible(true);
						
						//this should be placed Last du to junk Data
						if(editVO.getAutomaticOrder()!=null && !editVO.getAutomaticOrder().isEmpty()) {
							cmbAutomaticOrder.setValue(editVO.getAutomaticOrder());
						}
						
					}
				}catch(Exception ex){
					ex.printStackTrace();
					
				}
					
					
				}
				
			
			
			
			public void clickRemove(int index){
				Logger.logTrace("WAREHOUSE", "in clickRemove function...");
				
				if(thresholdModel!=null){
					thresholdModel.remove(index);
					configThresholdGrid.setModel(thresholdModel);
					configThresholdGrid.setItemRenderer(new SearchListItemRenderer());
				}
				
				/*ConfigureThresholdVO removeVO=null;
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
			}*/
				
			}
			

}
