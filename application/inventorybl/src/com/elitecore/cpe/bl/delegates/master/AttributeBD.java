package com.elitecore.cpe.bl.delegates.master;

import java.util.List;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.master.attribute.AttributeFacadeLocal;
import com.elitecore.cpe.bl.facade.master.attribute.AttributeFacadeRemote;
import com.elitecore.cpe.bl.facade.master.attribute.IAttributeFacade;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.core.IBDSessionContext;

public class AttributeBD extends BaseBusinessDelegate{

	private static final String MODULE ="ATTRIBUTE-BD";
	
	private static IAttributeFacade facade;
	
	public AttributeBD(IBDSessionContext context){
		super(context);
	}
	
	private IAttributeFacade getFacade()  throws NamingException{
		if (facade == null) {
			if(isLocalMode()){
				facade = (IAttributeFacade)lookupLocal(AttributeFacadeLocal.class);
			}else{
				facade = (IAttributeFacade)lookup(AttributeFacadeRemote.class);
			}
		}
		return facade;
	}
	
	
	/**
	 * Save/Create Attribute  
	 * @param {@link AttributeVO} attributeVO
	 * @throws CreateBLException
	 */
	public void saveAttribute(AttributeVO attributeVO) throws CreateBLException{
		try {
			getFacade().saveAttribute(attributeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(CreateBLException ex){
			throw ex;
		}
	}

	
	/**
	 * Search Attribute Data  
	 * @param {@link AttributeVO} attributeVO
	 * @return {@link List}<{@link AttributeVO}> list
	 */
	public List<AttributeVO> searchAttributeData(AttributeVO attributeVO){
		List<AttributeVO> list = null;
		try {
			list = getFacade().searchAttributeData(attributeVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	/**
	 * View Attribute Data  
	 * @param {@link AttributeVO} attributeVO
	 * @return {@link AttributeVO} attributeVO
	 */
	public AttributeVO viewAttribute(AttributeVO attributeVO){
		AttributeVO attributeVO2 = null;
		try {
			attributeVO2 = getFacade().viewAttribute(attributeVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attributeVO2;
	}

	/**
	 * Update Attribute Data  
	 * @param {@link AttributeVO} attributeVO
	 * @throws UpdateBLException
	 */
	public void updateAttribute(AttributeVO attributeVO) throws UpdateBLException{
		try {
			getFacade().updateAttribute(attributeVO,getBLSession());
		} catch (NamingException e) {
			e.printStackTrace();
		}catch(UpdateBLException ex){
			throw ex;
		}
	}
	
	
	
	public List<AttributeVO> getAttributesByRef(String usedby){
		List<AttributeVO> list = null;
		try {
			list = getFacade().getAttributesByRef(usedby);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


}