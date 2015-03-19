/**
 * 
 */
package com.elitecore.test.inventorymgt;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.delegates.inventorymgt.InventoryManagementBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryBatchViewVO;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryDetailVO;
import com.elitecore.cpe.bl.vo.inventorymgt.SearchInventoryVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 *
 */
public class InventoryTestCase extends BaseTestCase{
	
	InventoryManagementBD inventoryManagementBD = null;
	
	@Before
	public void setUp() {
		inventoryManagementBD = new InventoryManagementBD(getBDSessionContext());
	}
	
	
	@Test
	public void testInventorySearch() {
		
		try {
			SearchInventoryVO data = new SearchInventoryVO();
			
			
			List<InventoryDetailVO> response = inventoryManagementBD.searchInventoryDetailData(data);
			
			if(response!=null && !response.isEmpty()) {
				for(InventoryDetailVO inventoryDetailVO : response) {
					System.out.println(inventoryDetailVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testInventoryBatchSearch() {
		
		try {
			SearchInventoryVO data = new SearchInventoryVO();
			
			
			List<InventoryBatchViewVO> response = inventoryManagementBD.searchInventoryBatchData(data);
			
			if(response!=null && !response.isEmpty()) {
				for(InventoryBatchViewVO inventoryDetailVO : response) {
					System.out.println(inventoryDetailVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	

}
