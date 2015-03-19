package com.elitecore.cpe.web.composer.master.attribute;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.delegates.master.AttributeBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.constants.CommonConstants;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class UpdateAttributeComposer extends BaseModuleViewComposer{

	private Hlayout updateAttribute;
	
	private Textbox txtName;
	private Selectbox selRel,selDatatype;
	private Textbox txtDatavalue,txtReason;
	private Checkbox chkMandatory,chkUnique;
	private Label lblDatavalueTooltip;

	
	ListModelList<String> usedbylist = new ListModelList<String>();
	ListModelList<String> dataTypelist = new ListModelList<String>();
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.updateAttribute = comp;
		
		init();
		
		fetchViewEntity();
		chkUnique.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if(chkUnique.isChecked()) {
					chkMandatory.setChecked(true);
				}	
			}
		});
		chkMandatory.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if(chkUnique.isChecked()) {
					chkMandatory.setChecked(true);
				}	
			}
		});
	}
	private void init(){
		txtDatavalue.setDisabled(true);
		usedbylist = new ListModelList<String>();
//		usedbylist.add("Inventory");
		usedbylist.add(CommonConstants.RESOURCE);
//		usedbylist.add(CommonConstants.WAREHOUSE);
		
		selRel.setModel(usedbylist);
		
		dataTypelist = new ListModelList<String>();
		dataTypelist.add("Date");
		dataTypelist.add("Discrete");
		dataTypelist.add("Number");
		dataTypelist.add("String");
		
		selDatatype.setModel(dataTypelist);
	}
	
	public void onSelect$selDatatype(Event event){
		Set<String> list = dataTypelist.getSelection();
		Iterator<String> itr = list.iterator();
		String dataType = itr.next();
		if(dataType.equalsIgnoreCase("Discrete")){
			txtDatavalue.setDisabled(false);
			lblDatavalueTooltip.setValue("Value1:Name1,Value2:Name2");
		}else{
			txtDatavalue.setDisabled(true);
			lblDatavalueTooltip.setValue("");
		}
	}
	private void fetchViewEntity(){
		
		AttributeBD attributeBD = new AttributeBD(getBDSessionContext());
		AttributeVO attributeVO = new AttributeVO();
		attributeVO.setAttributeId(getViewEntityId());
		
		attributeVO = attributeBD.viewAttribute(attributeVO);
		
		populateData(attributeVO);
		
	}
	
	private void populateData(AttributeVO attributeVO){
		
		if(attributeVO != null){
			txtName.setValue(attributeVO.getName());
			
			List<String> selUsedBy = new ArrayList<String>();
			selUsedBy.add(attributeVO.getUsedBy());
			usedbylist.setSelection(selUsedBy);
			
			selUsedBy = new ArrayList<String>();
			selUsedBy.add(attributeVO.getDataType());
			dataTypelist.setSelection(selUsedBy);
			
			if(attributeVO.getDataType().equalsIgnoreCase("Discrete")){
				txtDatavalue.setDisabled(false);
				lblDatavalueTooltip.setValue("Value1:Name1,Value2:Name2");
				txtDatavalue.setValue(attributeVO.getDataValue());
			}else{
				txtDatavalue.setDisabled(true);
				lblDatavalueTooltip.setValue("");
			}
			
			chkMandatory.setChecked((attributeVO.getMandatory() == 'Y')?true:false);
			chkUnique.setChecked((attributeVO.getUnique() == 'Y')?true:false);
		}
		
	}
	
	
	public void onClick$btnUpdate(Event event){
		
		AttributeBD attributeBD = new AttributeBD(getBDSessionContext());
		try {
			String selectUsedBy = null;
			Set<String> setUsedBy = usedbylist.getSelection();
			for(String usedBy : setUsedBy)
			{
				selectUsedBy = usedBy;
			}
			setUsedBy = dataTypelist.getSelection();
			Iterator<String> itr = setUsedBy.iterator();
			String dataType = itr.next();
			
			if(dataType.equalsIgnoreCase("Discrete") &&  (txtDatavalue.getValue() == null 
					|| txtDatavalue.getValue().toString().trim().equals(""))){
				MessageUtility.successInformation("Attribute", "Please Enter Data Value for Discrete.");
				return;
			}else if(dataType.equalsIgnoreCase("Discrete")){
				String dataValue = txtDatavalue.getValue();
				String[] dataValues  = dataValue.split(",");
				for(String keyPair : dataValues){
					if(keyPair.split(":").length != 2){
						MessageUtility.successInformation("Attribute", "Please Enter Proper Data Value for Discrete.");
						return;
					}
				}
			}
			
			
			IBDSessionContext sessionContext = getBDSessionContext();
			AttributeVO attributeVO = new AttributeVO();
			attributeVO.setAttributeId(getViewEntityId());
			attributeVO.setName(txtName.getValue());
			
			
			attributeVO.setUsedBy(selectUsedBy);
			attributeVO.setUpdatedate(new Timestamp(new Date().getTime()));
			attributeVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			attributeVO.setDataType(dataType);
			attributeVO.setDataValue(txtDatavalue.getValue());
			attributeVO.setMandatory((chkMandatory.isChecked())?'Y':'N');
			attributeVO.setUnique((chkUnique.isChecked())?'Y':'N');
			attributeVO.setReason(txtReason.getValue());
			
			attributeBD.updateAttribute(attributeVO);
		
			MessageUtility.successInformation("Success", "Attribute Updated Successfully");
			updateAttribute.detach();
			
			if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
				BaseCPEViewComposer viewComposer = (BaseCPEViewComposer) arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
				viewComposer.refreshView();
			}
			
		} catch (UpdateBLException e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
