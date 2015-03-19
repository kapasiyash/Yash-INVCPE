/**
 * 
 */
package com.elitecore.test.resourcetype;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;


import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.vo.inventorymgt.ResourceAttributeVO;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.master.ResourceCategoryVO;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceTypeVO;
import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 *
 */
public class ResourceTypeTestCase extends BaseTestCase {
	@Test
	public void testResourceTypeCreate() {
		try {
			
			ItemBD itemBD = new ItemBD(getBDSessionContext());
			
			ResourceTypeVO typeVO = new ResourceTypeVO();
			typeVO.setResourceTypeName("testResource"+new Random().nextInt(10000));
			typeVO.setDescription("TEST");
			itemBD.createResourceType(typeVO);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testResourceTypeSearch() {
		
		try {
			
			ItemBD itemBD = new ItemBD(getBDSessionContext());
			List<SearchResourceTypeVO> response = itemBD.searchResourceTypeData(null);
			
			if(response!=null && !response.isEmpty()) {
				for(SearchResourceTypeVO resourceTypeVO : response) {
					System.out.println(resourceTypeVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testResourceTypeUpdate() {
		
		try {
			ItemBD itemBD = new ItemBD(getBDSessionContext());
			
			UpdateResourceTypeVO typeVO = new UpdateResourceTypeVO();
			typeVO.setResourceTypeId(85L);
			typeVO.setResourceTypeName("updated"+new Random().nextInt(10000));
			
			
			typeVO.setReason("Updating Name");
			
			itemBD.updateResourceType(typeVO);
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	

}
