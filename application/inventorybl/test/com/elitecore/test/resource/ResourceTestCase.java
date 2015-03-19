/**
 * 
 */
package com.elitecore.test.resource;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceCategoryVO;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 *
 */
public class ResourceTestCase extends BaseTestCase{
	ItemBD itemBD =null;
	@Before
	public void setUp() {
		 itemBD = new ItemBD(getBDSessionContext());
	}
	@Test
	public void testResourceCreate() {
		try {
			ItemBD itemBD = new ItemBD(getBDSessionContext());
			ItemVO itemVo=new ItemVO();	
			String alias = ("RE "+new Random().nextInt(10000)).toUpperCase();
			alias = alias.replace(" ", "_");
			itemVo.setName("HR"+new Random().nextInt(10000));
			itemVo.setAlias(alias);
			
			
			itemVo.setCreatedate(new Timestamp(new Date().getTime()));
			
		
			itemVo.setCreatedby(getBDSessionContext().getBLSession().getSessionUserId());
			itemVo.setUpdatedby(getBDSessionContext().getBLSession().getSessionUserId());
			itemVo.setModelnumber("M"+new Random().nextInt(10000));
			itemVo.setVendor("V"+new Random().nextInt(10000));
			itemVo.setReferenceID("UT");
			ResourceTypeVO resourceTypeVO=new ResourceTypeVO();
			
			resourceTypeVO.setResourceTypeName("CPE");
			resourceTypeVO.setResourceTypeId(123L);
			
			ResourceSubTypeVO subTypeVO = new ResourceSubTypeVO();
		
		//	subTypeVO.setResourceSubTypeId(selectedData.getId());
			subTypeVO.setResourceSubTypeName("DEMOA");
			subTypeVO.setResourceSubTypeId(501L);
			resourceTypeVO.setResourceSubTypeVO(subTypeVO);
			itemVo.setResourceTypeVO(resourceTypeVO);
			ResourceCategoryVO categoryVO = new ResourceCategoryVO();
			categoryVO.setResourceCategoryId(100L);
			itemVo.setResourceCategoryVO(categoryVO);
			itemVo.setPrefix("TES");
			itemVo = itemBD.createItem(itemVo);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testResourceSearch() {
		
		try {
			
			//ItemBD itemBD = new ItemBD(getBDSessionContext());
			ItemVO itemVO=new ItemVO();
			List<ItemVO> response = itemBD.searchItemData(itemVO);
			
			
			if(response!=null && !response.isEmpty()) {
				for(ItemVO resourceTypeVO : response) {
					System.out.println(resourceTypeVO);
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	@Test
	public void testResourceUpdate() {
		try {
			ItemBD itemBD = new ItemBD(getBDSessionContext());
			ItemVO itemVo=new ItemVO();	
			
			itemVo.setName("Update"+new Random().nextInt(10000));
			itemVo.setItemId(70L);
			itemVo.setUpdatedby(getBDSessionContext().getBLSession().getSessionUserId());
//			itemVo.setModelnumber("M"+new Random().nextInt(10000));
//			itemVo.setVendor("V"+new Random().nextInt(10000));
//			itemVo.setReferenceID("UT");
			ResourceTypeVO resourceTypeVO=new ResourceTypeVO();
			ResourceSubTypeVO subTypeVO = new ResourceSubTypeVO();
			
			subTypeVO.setResourceSubTypeId(502L);
			resourceTypeVO.setResourceSubTypeVO(subTypeVO);
			resourceTypeVO.setResourceTypeName("CPE");
			resourceTypeVO.setResourceTypeId(123L);
			ResourceCategoryVO categoryVO = new ResourceCategoryVO();
			categoryVO.setResourceCategoryId(100L);
			itemVo.setResourceCategoryVO(categoryVO);
//			ResourceSubTypeVO subTypeVO = new ResourceSubTypeVO();
//		
//		//	subTypeVO.setResourceSubTypeId(selectedData.getId());
//			subTypeVO.setResourceSubTypeName("DEMOA");
//			subTypeVO.setResourceSubTypeId(501L);
//			resourceTypeVO.setResourceSubTypeVO(subTypeVO);
//			itemVo.setResourceTypeVO(resourceTypeVO);
//			ResourceCategoryVO categoryVO = new ResourceCategoryVO();
//			categoryVO.setResourceCategoryId(100L);
//			itemVo.setResourceCategoryVO(categoryVO);
//			itemVo.setPrefix("TES");
			itemVo.setResourceTypeVO(resourceTypeVO);
			itemVo.setReason("TestUpdate");
			itemBD.updateItem(itemVo);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	


}
