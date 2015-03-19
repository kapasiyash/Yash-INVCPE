/**
 * 
 */
package com.elitecore.test.attributemgt;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.delegates.master.AttributeBD;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;

import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 *
 */
public class AttributeTestCase extends BaseTestCase{
	AttributeBD  attributeBD = null;
	@Before
	public void setUp() {
		attributeBD =  new AttributeBD(getBDSessionContext());
	}
	
	
	@Test
	public void testAttributeCreate() {
		try {
			AttributeVO attributeVO = new AttributeVO();
			attributeVO.setName("Attribute"+new Random().nextInt(10000));
			attributeVO.setUsedBy("Resource");
			
			attributeVO.setCreatedate(new Timestamp(new Date().getTime()));
			attributeVO.setUpdatedate(new Timestamp(new Date().getTime()));
			attributeVO.setCreatedby(getBDSessionContext().getBLSession().getSessionUserId());
			attributeVO.setUpdatedby(getBDSessionContext().getBLSession().getSessionUserId());
			attributeVO.setDataType("String");
		//	attributeVO.setDataValue(txtDatavalue.getValue());
		//	attributeVO.setMandatory((chkMandatory.isChecked())?'Y':'N');
		//	attributeVO.setUnique((chkUnique.isChecked())?'Y':'N');
			
			attributeBD.saveAttribute(attributeVO);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testAttributeSearch() {
		
		try {
			
			//ItemBD itemBD = new ItemBD(getBDSessionContext());
			AttributeVO data = new AttributeVO();
			data.setUsedBy("Resource");
			List<AttributeVO> response = attributeBD.searchAttributeData(data);
			
			if(response!=null && !response.isEmpty()) {
				for(AttributeVO searchAttributeVO : response) {
					System.out.println(searchAttributeVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testAttributeUpdate() {
		
		try {
			
			AttributeVO attributeVO = new AttributeVO();
			attributeVO.setAttributeId(122L);
			attributeVO.setName("Update"+new Random().nextInt(10000));
			
			attributeVO.setUsedBy("Warehouse");
		
			attributeVO.setUpdatedate(new Timestamp(new Date().getTime()));
			attributeVO.setUpdatedby(getBDSessionContext().getBLSession().getSessionUserId());
			attributeVO.setDataType("Number");
	
			attributeVO.setReason("Update Attribute Test");
			
			attributeBD.updateAttribute(attributeVO);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
