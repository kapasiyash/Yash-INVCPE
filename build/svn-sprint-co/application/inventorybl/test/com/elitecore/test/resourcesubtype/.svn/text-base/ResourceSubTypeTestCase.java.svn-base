/**
 * 
 */
package com.elitecore.test.resourcesubtype;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.ResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceTypeVO;
import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 *
 */
public class ResourceSubTypeTestCase extends BaseTestCase{
	ItemBD itemBD =null;
	@Before
	public void setUp() {
		 itemBD = new ItemBD(getBDSessionContext());
	}
	@Test
	public void testResourceSubTypeCreate() {
		try {
			
			//ItemBD itemBD = new ItemBD(getBDSessionContext());
			ResourceSubTypeVO typeVO = new ResourceSubTypeVO();
			typeVO.setResourceSubTypeName("testSubtype"+new Random().nextInt(10000));
			typeVO.setDescription("Test");
			
				typeVO.setResourceTypeId(123L);
			
			
			itemBD.createResourceSubType(typeVO);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testResourceSubTypeSearch() {
		
		try {
			
			//ItemBD itemBD = new ItemBD(getBDSessionContext());
			List<SearchResourceSubTypeVO> response = itemBD.searchResourceSubTypeData(null, null);
			
			if(response!=null && !response.isEmpty()) {
				for(SearchResourceSubTypeVO resourceSubTypeVO : response) {
					System.out.println(resourceSubTypeVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testResourceSubTypeUpdate() {
		
		try {
			ItemBD itemBD = new ItemBD(getBDSessionContext());
			
			UpdateResourceSubTypeVO typeVO = new UpdateResourceSubTypeVO();
			typeVO.setResourceTypeId(123L);
			typeVO.setResourceSubTypeId(241L);
			typeVO.setResourceSubTypeName("updated"+new Random().nextInt(10000));
			
			
			typeVO.setReason("Updating Name");
			
			itemBD.updateResourceSubType(typeVO);
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	



}
