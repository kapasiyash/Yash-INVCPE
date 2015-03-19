package com.elitecore.cpe.bl.facade.master.attribute;

import java.sql.Timestamp;

import com.elitecore.cpe.bl.entity.inventory.master.AttributeData;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.vo.master.AttributeVO;

public class AttributeUtil {

	
	public static AttributeData getAttributeData( AttributeVO attributeVO){
		
		AttributeData attributeData = new AttributeData();
		if(attributeVO != null){
			attributeData.setAttributeId(attributeVO.getAttributeId());
			attributeData.setName(attributeVO.getName());
			attributeData.setUsedBy(attributeVO.getUsedBy());
			attributeData.setCreatedby(attributeVO.getCreatedby());
			attributeData.setCreatedate(getCurrentTimestamp());
			attributeData.setUpdatedby(attributeVO.getUpdatedby());
			attributeData.setUpdatedate((attributeVO.getUpdatedate() == null)? getCurrentTimestamp(): attributeVO.getUpdatedate());
			attributeData.setSystemgenerated(attributeVO.getSystemgenerated());
			attributeData.setDataType(attributeVO.getDataType());
			attributeData.setDataValue(attributeVO.getDataValue());
			attributeData.setMandatory(attributeVO.getMandatory());
			attributeData.setReason(attributeVO.getReason());
			attributeData.setUnique(attributeVO.getUnique());
		}
		
		
		return attributeData;
	}
	
	public static AttributeVO getAttributeVO(AttributeData attributeData){
		
		AttributeVO attributeVO = new AttributeVO();
		if(attributeData != null){
			String createdby = null,updatedby = null;
			try {
				createdby = UserFactory.findUserById(attributeData.getCreatedby()).getName();
				updatedby = UserFactory.findUserById(attributeData.getUpdatedby()).getName();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			attributeVO.setAttributeId(attributeData.getAttributeId());
			attributeVO.setName(attributeData.getName());
			attributeVO.setUsedBy(attributeData.getUsedBy());
			attributeVO.setCreatedby(createdby);
			attributeVO.setCreatedate(attributeData.getCreatedate());
			attributeVO.setUpdatedby(updatedby);
			attributeVO.setUpdatedate(attributeData.getUpdatedate());
			attributeVO.setSystemgenerated(attributeData.getSystemgenerated());
			attributeVO.setDataType(attributeData.getDataType());
			attributeVO.setDataValue(attributeData.getDataValue());
			attributeVO.setMandatory(attributeData.getMandatory());
			attributeVO.setUnique(attributeData.getUnique());
		}
		
		return attributeVO;
	}

	
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
}
