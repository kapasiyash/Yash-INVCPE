package com.elitecore.cpe.bl.facade.master.attribute;

import java.util.List;

import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.core.IBLSession;



public interface IAttributeFacade {

	public void saveAttribute(AttributeVO attributeVO,IBLSession iblSession) throws CreateBLException;

	public List<AttributeVO> searchAttributeData(AttributeVO attributeVO);

	public AttributeVO viewAttribute(AttributeVO attributeVO);

	public void updateAttribute(AttributeVO attributeVO,IBLSession iblSession) throws UpdateBLException;
	public List<AttributeVO> getAttributesByRef(String usedby);
	
}
