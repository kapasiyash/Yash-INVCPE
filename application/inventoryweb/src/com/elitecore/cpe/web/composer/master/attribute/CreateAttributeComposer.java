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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.delegates.master.AttributeBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.constants.CommonConstants;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class CreateAttributeComposer extends BaseModuleComposer {

private static final long serialVersionUID = 1L;
	
//	private Window createAttributeTabbox;
	private Textbox txtName;
	private Selectbox selRel,selDatatype;
	private Textbox txtDatavalue;
	private Checkbox chkMandatory,chkUnique;
	private Label lblDatavalueTooltip;
	
	ListModelList<String> usedbylist = new ListModelList<String>();
	ListModelList<String> dataTypelist = new ListModelList<String>();
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		System.out.println("in CreateAttributeComposer composer afterCompose");
	//	this.createAttributeTabbox = comp;
		
		init();
		
		
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
		List<String> selList = new ArrayList<String>();
		selList.add(CommonConstants.RESOURCE);
		usedbylist.setSelection(selList);
		
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
			txtDatavalue.setValue("");
			txtDatavalue.setDisabled(true);
			lblDatavalueTooltip.setValue("");
		}
	}
	public void onClick$btnCreate(Event event) {
		System.out.println("in onClick$btnCreate");
		
		try {
			//validation
			if(usedbylist.getSelection().size() == 0){
				MessageUtility.successInformation("Attribute", "Please Select Used By.");
				return;
			}else if(dataTypelist.getSelection().size() == 0){
				MessageUtility.successInformation("Attribute", "Please Select Data Type.");
				return;
			}
			
			Set<String> list = usedbylist.getSelection();
			Iterator<String> itr = list.iterator();
			String usedBy = itr.next();
			
			list = dataTypelist.getSelection();
			itr = list.iterator();
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
			
			System.out.println("staff :"+sessionContext.getBLSession().getSessionUserId());
			
			AttributeBD  attributeBDBD = new AttributeBD(sessionContext);
			
			
			AttributeVO attributeVO = new AttributeVO();
			attributeVO.setName(txtName.getValue());
			attributeVO.setUsedBy(usedBy);
			
			attributeVO.setCreatedate(new Timestamp(new Date().getTime()));
			//attributeVO.setUpdatedate(new Timestamp(new Date().getTime()));
			attributeVO.setCreatedby(sessionContext.getBLSession().getSessionUserId());
			//attributeVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			attributeVO.setDataType(dataType);
			attributeVO.setDataValue(txtDatavalue.getValue());
			attributeVO.setMandatory((chkMandatory.isChecked())?'Y':'N');
			attributeVO.setUnique((chkUnique.isChecked())?'Y':'N');
			
			attributeBDBD.saveAttribute(attributeVO);
			
			MessageUtility.successInformation("Success", "Attribute Created Successfully");
			reset();
		} catch (CreateBLException e) {
			e.printStackTrace();
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
	
	public void onClick$btnCancel1(Event event) {
		reset();
	} 
	
	
	private void reset(){
		
		resetComponents(txtName, txtName,selRel,selDatatype,txtDatavalue,chkMandatory,lblDatavalueTooltip);
		
		chkMandatory.setChecked(false);
		chkUnique.setChecked(false);
		
	}
}
