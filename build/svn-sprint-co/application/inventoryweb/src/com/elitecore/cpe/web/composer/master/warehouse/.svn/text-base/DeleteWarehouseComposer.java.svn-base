package com.elitecore.cpe.web.composer.master.warehouse;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.web.base.ui.core.BaseCPEViewComposer;
import com.elitecore.cpe.web.base.ui.module.BaseModuleViewComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;
import com.elitecore.cpe.web.utils.MessageUtility;

public class DeleteWarehouseComposer extends BaseModuleViewComposer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Hlayout deleteWarehouse;
	
	
	private Textbox txtReason;
	
//	private Combobox cmbParentWHname;
	
	@Override
	public void afterCompose(Hlayout comp) throws ModuleInitializationException {
		
		this.deleteWarehouse = comp;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$btnDelete(Event event){
		
		final WareHouseBD wareHouseBD = new WareHouseBD(getBDSessionContext());
		try {
			
			IBDSessionContext sessionContext = getBDSessionContext();
			final WarehouseVO warehouseVO = new WarehouseVO();
			warehouseVO.setWarehouseId(getViewEntityId());
			warehouseVO.setUpdatedby(sessionContext.getBLSession().getSessionUserId());
			warehouseVO.setReason(txtReason.getValue());
			
			 Messagebox.show("Are you sure to delete?", "Confirm", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener()
	         {
	            public void onEvent(Event e)
	               {
	            		if(Messagebox.ON_YES.equals(e.getName()))
	    				{
	            			try {
								wareHouseBD.deleteWarehouse(warehouseVO);
								
								MessageUtility.successInformation("Success", "Warehouse Deleted Successfully");
								deleteWarehouse.detach();
								
								if(arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY)!=null){
									BaseCPEViewComposer viewComposer = (BaseCPEViewComposer)arg.get(BaseCPEViewComposer.VIEW_COMPOSER_KEY);
									viewComposer.closeParentTab();
								}
								
							} catch (UpdateBLException e1) {
								e1.printStackTrace();
								MessageUtility.failureInformation("ERROR", e1.getMessage());
							}
	    				}
	               }   
	         });
			
			
		
			
			
		} catch (Exception e) {
			MessageUtility.failureInformation("ERROR", e.getMessage());
		}
		
	}
}
