package com.elitecore.cpe.web.composer.master.item;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.ResourceAttributeVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceCategoryVO;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.core.BaseComposer.ComboItemDataRenderer;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.constants.CommonConstants;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class UpdateItemComposer extends BaseModuleViewComposer{

	private Hlayout updatesItem;
	
	private Textbox txtName;
	private Textbox txtmodelnumber;
	private Textbox txtDesc,txtReason;
	private Textbox txtrefid;
	private Textbox txtvendor;
	private Textbox txtprefix;
//	private ItemVO itemData;
	private Listbox attrlist;
	private ListModelList<ResourceAttributeVO> attributeModel;
	private Combobox cmbResourceType,cmbResourceSubType,cmbResourceAttribute,cmbInventoryGenerationType;
	private Row rowPrefix;
	
	List<ComboData> attributes = null;
	private Long categoryId;
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		final ItemBD  itemBD = new ItemBD(getBDSessionContext());
		this.updatesItem = comp;
		init();
		attributes = itemBD.getAllResourceAttributes();
		if(attributes!=null && !attributes.isEmpty()) {
			cmbResourceAttribute.setModel(new ListModelList<ComboData>(attributes));
			cmbResourceAttribute.setItemRenderer(new ComboItemDataRenderer());
		}

		fetchViewEntity();
		
		cmbResourceType.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				

				if(cmbResourceType.getSelectedItem()!=null) {
					ComboData comboData = cmbResourceType.getSelectedItem().getValue();
					Long typeId = comboData.getId();
					List<ComboData> comboBoxDatas = itemBD.getAllResourceSubTypeDataByResourceTypeId(typeId);
					resetComponents(cmbResourceSubType, cmbResourceSubType);
					if(comboBoxDatas!=null && !comboBoxDatas.isEmpty()) {
						cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
						cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
					} else {
						cmbResourceSubType.setModel(new ListModelList<ComboData>());
					}
				}
				
			}
		});
		
		cmbInventoryGenerationType.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				ComboData comboData = cmbInventoryGenerationType.getSelectedItem().getValue();
				Long typeId = comboData.getId();
				if(typeId.equals(2L)) {
					rowPrefix.setVisible(false);
				} else {
					rowPrefix.setVisible(true);
				}
				
			}
			
		});
		
	}

	private void init(){
		attributeModel = new ListModelList<ResourceAttributeVO>();
		IBDSessionContext sessionContext = getBDSessionContext();
		ItemBD  itemBD = new ItemBD(sessionContext);
		List<ComboData> comboBoxDatas = itemBD.getAllResourceTypeData();
		List<ComboData> categories = itemBD.getAllResourceCategories();
		
		cmbResourceType.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
		
//		if(categories!=null && !categories.isEmpty()) {
//			cmbResourceCategory.setModel(new ListModelList<ComboData>(categories));
//			cmbResourceCategory.setItemRenderer(new ComboItemDataRenderer());
//		}
		
		for(ComboData comboData : categories){
			if(comboData.getName().equalsIgnoreCase("CPE")){
				categoryId = comboData.getId();
			}
		}
	}
	private void fetchViewEntity(){
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		ItemVO itemVo=new ItemVO();
		itemVo.setItemId(getViewEntityId());
		itemVo = itemBD.viewItem(itemVo);
		
		populateData(itemVo);
		
	}
	
	public void onClick$addAttributes(Event event){
		
		if(cmbResourceAttribute.getSelectedItem()!=null) {
			ComboData comboData = cmbResourceAttribute.getSelectedItem().getValue();
			attributeModel.add(new ResourceAttributeVO(comboData.getId(), comboData.getName()));
			attrlist.setModel(attributeModel);
			attrlist.setItemRenderer(new AttributeItemRenderer());
			if(attributes!=null && !attributes.isEmpty()) {
				attributes.remove(comboData);
				cmbResourceAttribute.setModel(new ListModelList<ComboData>(attributes));
				cmbResourceAttribute.setItemRenderer(new ComboItemDataRenderer());
			}
			resetComponents(cmbResourceAttribute, cmbResourceAttribute);
		} else {
			MessageUtility.failureInformation("ERROR", "Please select the Attribute");
		}
		
	}
	
	
	private void removeAttribute(Integer attribute,ResourceAttributeVO attributeVO) {
		
		System.out.println(attributeModel);
		System.out.println(attribute);
		if(attributeModel!=null){
			attributeModel.remove(attributeVO);
			attrlist.setModel(attributeModel);
			System.out.println(attributeModel);
			attrlist.setItemRenderer(new AttributeItemRenderer());
			if(attributes!=null) {
				attributes.add(new ComboData(attributeVO.getAttributeId(), attributeVO.getAttributeName()));
				cmbResourceAttribute.setModel(new ListModelList<ComboData>(attributes));
				cmbResourceAttribute.setItemRenderer(new ComboItemDataRenderer());
			}
		}
		
	}
	
	
	private class AttributeItemRenderer implements ListitemRenderer<ResourceAttributeVO>{

		private EventListener<Event> removeItemListener,removeHoverListner,remvoeOutListener;
		private static final String ATTRIBUTE_DATA="_DATA";
		private static final String ATTRIBUTE_VO="_VO";
		
		public AttributeItemRenderer() {
			removeItemListener = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
						Image img = (Image) event.getTarget();
						removeAttribute((Integer) img.getAttribute(ATTRIBUTE_DATA),(ResourceAttributeVO) img.getAttribute(ATTRIBUTE_VO));
				}
				
			};
			
			removeHoverListner = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.REMOVE_ITEM_HOVER_IMAGE);
				}
			};
			
			remvoeOutListener = new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					Image img = (Image) event.getTarget();
					img.setSrc(BaseConstants.REMOVE_ITEM_IMAGE);
				}
			};
			
		}
		
		@Override
		public void render(Listitem item, ResourceAttributeVO data, int index)
				throws Exception {
			
			item.setValue(data);
			item.appendChild(new Listcell((index+1)+""));
			item.appendChild(new Listcell(data.getAttributeName()));
			
			Listcell operationCell = new Listcell();
			Image remove = new Image(BaseConstants.REMOVE_ITEM_IMAGE);
			remove.addEventListener(Events.ON_MOUSE_OVER, removeHoverListner);
			remove.addEventListener(Events.ON_MOUSE_OUT, remvoeOutListener);
			remove.addEventListener(Events.ON_CLICK, removeItemListener);
			remove.setAttribute(ATTRIBUTE_DATA, index);
			remove.setAttribute(ATTRIBUTE_VO, data);
			operationCell.appendChild(remove);
			item.appendChild(operationCell);
			
		}
		
	}
	
	
	private void populateData(ItemVO vo){
		
		
		List<ComboData> comboBoxList = new ArrayList<ComboData>();
		comboBoxList.add(new ComboData(1L, CommonConstants.INVENTORY_AUTOMATIC));
		comboBoxList.add(new ComboData(2L, CommonConstants.INVENTORY_MANUAL));
		cmbInventoryGenerationType.setModel(new ListModelList<ComboData>(comboBoxList));
		cmbInventoryGenerationType.setItemRenderer(new ComboItemDataRenderer());
		
		if(vo != null){
			txtName.setValue(vo.getName());
			txtmodelnumber.setValue(vo.getModelnumber());
			txtrefid.setValue(vo.getReferenceID());
			txtvendor.setValue(vo.getVendor());
			
			txtDesc.setValue(vo.getDescription());
			
			System.out.println("in update :: "+vo.getPrefix());
			if(vo.getInventoryGeneration().equals(1L)) {
				cmbInventoryGenerationType.setValue(CommonConstants.INVENTORY_AUTOMATIC);
				rowPrefix.setVisible(true);
				txtprefix.setValue(vo.getPrefix().trim());
			} else {
				cmbInventoryGenerationType.setValue(CommonConstants.INVENTORY_MANUAL);
			}
			
			if(!vo.isAllowedPrefixChange()) {
				cmbInventoryGenerationType.setDisabled(true);
				txtprefix.setDisabled(true);
			}
			
			
			if(vo.getReason()!=null) {
				//txtReason.setValue(vo.getReason());
			}
		//	txtprefix.setValue(vo.getPrefix());
			cmbResourceType.setValue(vo.getResourceTypeVO().getResourceTypeName());
			
			ItemBD  itemBD = new ItemBD(getBDSessionContext());
			List<ComboData> comboBoxDatas = itemBD.getAllResourceSubTypeDataByResourceTypeId(vo.getResourceTypeVO().getResourceTypeId());
			if(comboBoxDatas!=null && !comboBoxDatas.isEmpty()) {
				cmbResourceSubType.setModel(new ListModelList<ComboData>(comboBoxDatas));
				cmbResourceSubType.setItemRenderer(new ComboItemDataRenderer());
			}
			
			List<Long> removeAttr = new ArrayList<Long>();
			if(vo.getAttributeVO()!=null && !vo.getAttributeVO().isEmpty()) {
				for(ResourceAttributeVO attributeVO : vo.getAttributeVO()) {
					
					removeAttr.add(attributeVO.getAttributeId());
					
				}
				
				
				
				
				attributeModel = new ListModelList<ResourceAttributeVO>(vo.getAttributeVO());
				attrlist.setModel(attributeModel);
				attrlist.setItemRenderer(new AttributeItemRenderer());
				
			}
			
			List<ComboData> comboDatas = new ArrayList<ComboData>();
			if(removeAttr!=null && !removeAttr.isEmpty() && attributes!=null && !attributes.isEmpty()) {
				for(ComboData data : attributes) {
						if(removeAttr.contains(data.getId())) {
							comboDatas.add(data);
							System.out.println("remove");
						}
				}
				if(comboDatas!=null && !comboDatas.isEmpty()) {
					attributes.removeAll(comboDatas);
				}
				
				cmbResourceAttribute.setModel(new ListModelList<ComboData>(attributes));
				cmbResourceAttribute.setItemRenderer(new ComboItemDataRenderer());
			}
			cmbResourceSubType.setValue(vo.getResourceTypeVO().getResourceSubTypeVO().getResourceSubTypeName());
		//	cmbResourceCategory.setValue(vo.getResourceCategoryVO().getResourceCategoryName());
		}
		
	}
	
	public void onClick$btnUpdate(Event event){
		
		ItemBD itemBD = new ItemBD(getBDSessionContext());
		try {
			
			IBDSessionContext sessionContext = getBDSessionContext();
//			ItemData itemData=new ItemData();

			ItemVO itemVo=new ItemVO();
			ResourceTypeVO resourceTypeVO=new ResourceTypeVO();
			itemVo.setItemId(getViewEntityId());
			
			itemVo.setName(txtName.getValue());
			itemVo.setModelnumber(txtmodelnumber.getValue());
			itemVo.setVendor(txtvendor.getValue());
			itemVo.setDescription(txtDesc.getValue());
			itemVo.setUpdatedate(new Timestamp(new Date().getTime()));
			itemVo.setReferenceID(txtrefid.getValue());
			//itemVo.setPrefix(txtprefix.getValue());
			
			
			/*if(cmbResourceCategory.getSelectedItem() != null){
				ResourceCategoryVO categoryVO = new ResourceCategoryVO();
				ComboData selectedData = cmbResourceCategory.getSelectedItem().getValue();
				categoryVO.setResourceCategoryId(selectedData.getId());
				itemVo.setResourceCategoryVO(categoryVO);
			}*/
			
			ResourceCategoryVO categoryVO = new ResourceCategoryVO();
			categoryVO.setResourceCategoryId(categoryId);
			itemVo.setResourceCategoryVO(categoryVO);
			if(cmbResourceType.getSelectedItem() != null){
				ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
				resourceTypeVO.setResourceTypeId(selectedData.getId());
			}
			if(cmbResourceSubType.getSelectedItem() != null){
				ResourceSubTypeVO subTypeVO = new ResourceSubTypeVO();
				ComboData selectedData = cmbResourceSubType.getSelectedItem().getValue();
				subTypeVO.setResourceSubTypeId(selectedData.getId());
				resourceTypeVO.setResourceSubTypeVO(subTypeVO);
			}
			
			if(attrlist.getItems()!=null && !attrlist.getItems().isEmpty()){
				List<Listitem> items = attrlist.getItems();
				List<ResourceAttributeVO> attributesList = new ArrayList<ResourceAttributeVO>();
				for(Listitem item: items){
					attributesList.add((ResourceAttributeVO) item.getValue());
				}
				itemVo.setAttributeVO(attributesList);
				
			}
			
			itemVo.setResourceTypeVO(resourceTypeVO);

			itemVo.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			itemVo.setReason(txtReason.getValue());
		
			if(cmbInventoryGenerationType.getSelectedItem()!=null) {
				ComboData comboData = cmbInventoryGenerationType.getSelectedItem().getValue();
				Long typeId = comboData.getId();
				itemVo.setInventoryGeneration(typeId);
				
				if(typeId.equals(1L)) {
					itemVo.setPrefix(txtprefix.getValue());
				}
			}
			
			itemBD.updateItem(itemVo);
		
			MessageUtility.successInformation("Success", "Resource Updated Successfully");
			updatesItem.detach();
			
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
				}
		catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
