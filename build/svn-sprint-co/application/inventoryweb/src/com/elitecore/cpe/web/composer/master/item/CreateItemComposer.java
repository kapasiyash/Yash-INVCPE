package com.elitecore.cpe.web.composer.master.item;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.inventorymgt.ResourceAttributeVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceCategoryVO;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.module.BaseConstants;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.constants.CommonConstants;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class CreateItemComposer extends BaseModuleComposer {

private static final long serialVersionUID = 2L;
	
//	private Window createResource;
	private Textbox txtName;
	private Textbox txtmodelnumber;
	private Textbox txtvendor;
	private Textbox txtDesc;
	private Textbox txtrefid;
	private Textbox txtprefix;
	private Listbox attrlist;
	private ListModelList<ResourceAttributeVO> attributeModel;
	private Combobox cmbResourceType,cmbResourceSubType,cmbResourceAttribute,cmbInventoryGenerationType;
	private Row prefixRow;
	
	List<ComboData> attributes = null,attributesInitial = null;
	
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		System.out.println("in createItem  composer after Composer");
	//	this.createResource = comp;
		init();
		
		final ItemBD  itemBD = new ItemBD(getBDSessionContext());
		
		
		/*List<ComboData> categories = itemBD.getAllResourceCategories();
		if(categories!=null && !categories.isEmpty()) {
			cmbResourceCategory.setModel(new ListModelList<ComboData>(categories));
			cmbResourceCategory.setItemRenderer(new ComboItemDataRenderer());
		}*/
		
		
		attributes = itemBD.getAllResourceAttributes();
		attributesInitial = new ArrayList<ComboData>();
		
		if(attributes!=null && !attributes.isEmpty()) {
			attributesInitial.addAll(attributes);
			cmbResourceAttribute.setModel(new ListModelList<ComboData>(attributes));
			cmbResourceAttribute.setItemRenderer(new ComboItemDataRenderer());
		}
		
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
					prefixRow.setVisible(false);
				} else {
					prefixRow.setVisible(true);
				}
				
			}
			
		});
		
	}
	private void init(){
		
		attributeModel = new ListModelList<ResourceAttributeVO>();
		IBDSessionContext sessionContext = getBDSessionContext();
		ItemBD  itemBD = new ItemBD(sessionContext);
		
		List<ComboData> comboBoxDatas = itemBD.getAllResourceTypeData();
		 
		
		cmbResourceType.setModel(new ListModelList<ComboData>(comboBoxDatas));
		cmbResourceType.setItemRenderer(new ComboItemDataRenderer());
		
		List<ComboData> comboBoxList = new ArrayList<ComboData>();
		comboBoxList.add(new ComboData(1L, CommonConstants.INVENTORY_AUTOMATIC));
		comboBoxList.add(new ComboData(2L, CommonConstants.INVENTORY_MANUAL));
		cmbInventoryGenerationType.setModel(new ListModelList<ComboData>(comboBoxList));
		cmbInventoryGenerationType.setItemRenderer(new ComboItemDataRenderer());
		
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
	
	
	public void onClick$btnCreate(Event event) {
		System.out.println("in onClick$btnCreate for create Item");
		
	try {
			String alias = txtName.getValue().toUpperCase();
			alias = alias.replace(" ", "_");
			
			IBDSessionContext sessionContext = getBDSessionContext();
			
			System.out.println("staff :"+sessionContext.getBLSession().getSessionUserId());
			
	
			ItemBD  itemBD = new ItemBD(sessionContext);

		 
		    ItemVO itemVo=new ItemVO();	
		    ResourceTypeVO resourceTypeVO=new ResourceTypeVO();
			itemVo.setName(txtName.getValue());
			itemVo.setAlias(alias);
			itemVo.setDescription(txtDesc.getValue());
			
			itemVo.setCreatedate(new Timestamp(new Date().getTime()));

		
			itemVo.setCreatedby(sessionContext.getBLSession().getSessionUserId());
		//	itemVo.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			itemVo.setModelnumber(txtmodelnumber.getValue());
			itemVo.setVendor(txtvendor.getValue());
			itemVo.setReferenceID(txtrefid.getValue());
			
			
//			if(cmbResourceCategory.getSelectedItem() != null){
				ResourceCategoryVO categoryVO = new ResourceCategoryVO();
//				ComboData selectedData = cmbResourceCategory.getSelectedItem().getValue();
//				categoryVO.setResourceCategoryId(selectedData.getId());
				categoryVO.setResourceCategoryId(100L);
				itemVo.setResourceCategoryVO(categoryVO);
//			}
			if(cmbResourceType.getSelectedItem() != null){
				ComboData selectedData = cmbResourceType.getSelectedItem().getValue();
				resourceTypeVO.setResourceTypeId(selectedData.getId());
				resourceTypeVO.setResourceTypeName(selectedData.getName());
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
			
			if(cmbInventoryGenerationType.getSelectedItem()!=null) {
				ComboData comboData = cmbInventoryGenerationType.getSelectedItem().getValue();
				Long typeId = comboData.getId();
				itemVo.setInventoryGeneration(typeId);
				
				if(typeId.equals(1L)) {
					itemVo.setPrefix(txtprefix.getValue());
				}
			}
			
			
			itemVo.setResourceTypeVO(resourceTypeVO);
			
			itemVo = itemBD.createItem(itemVo);
						
			MessageUtility.successInformation("Success", "Resource with Resource Number :  "+itemVo.getResourceNumber()+" Created Successfully");
			reset();
			
		}
	catch (CreateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
	
	
	public void onClick$btnCancel(Event event){
		reset();
	}
	
	private void reset(){
		//cmbResourceCategory
		resetComponents(txtName,txtName,txtmodelnumber,txtvendor,txtDesc,txtrefid,cmbResourceSubType,cmbResourceType,txtprefix,cmbResourceAttribute);
		
		attributeModel = new ListModelList<ResourceAttributeVO>();
		attrlist.setModel(attributeModel);
		attrlist.setItemRenderer(new AttributeItemRenderer());
		if(attributesInitial!=null && !attributesInitial.isEmpty()) {
			cmbResourceAttribute.setModel(new ListModelList<ComboData>(attributesInitial));
			cmbResourceAttribute.setItemRenderer(new ComboItemDataRenderer());
		}
		
		ItemBD  itemBD = new ItemBD(getBDSessionContext());
		attributes = itemBD.getAllResourceAttributes();
		cmbResourceSubType.setModel(null);
		

	}
}
