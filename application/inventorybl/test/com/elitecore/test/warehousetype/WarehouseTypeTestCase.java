/**
 * 
 */
package com.elitecore.test.warehousetype;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 *
 */
public class WarehouseTypeTestCase  extends BaseTestCase{
	
	
	WareHouseBD  wareHouseBD=null;
	@Before
	public void setUp() {
		 wareHouseBD = new WareHouseBD(getBDSessionContext());
	}
	@Test
	public void testWarehouseTypeCreate() {
		try {

			WarehouseTypeVO warehouseTypeVO = new WarehouseTypeVO();
			warehouseTypeVO.setName("type"+new Random().nextInt(10000));
			String alias = ("type "+new Random().nextInt(10000)).toUpperCase();
			alias = alias.replace(" ", "_");
			warehouseTypeVO.setAlias(alias);
			warehouseTypeVO.setCreatedby(getBDSessionContext().getBLSession().getSessionUserId());
			warehouseTypeVO.setUpdatedby(getBDSessionContext().getBLSession().getSessionUserId());
			warehouseTypeVO.setCreateDate(new Timestamp(new Date().getTime()));
			wareHouseBD.saveWarehouseType(warehouseTypeVO);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testWarehouseTypeSearch() {
		
		try {
			WarehouseTypeVO data = new WarehouseTypeVO();
			
			
		
			List<WarehouseTypeVO> response = wareHouseBD.searchWarehouseTypeData(data);
			
			if(response!=null && !response.isEmpty()) {
				for(WarehouseTypeVO warehouseSubTypeVO : response) {
					System.out.println(warehouseSubTypeVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testWarehouseTypeUpdate() {

		try {
			WarehouseTypeVO warehouseVO = new WarehouseTypeVO();
			warehouseVO.setWarehouseTypeId(125L);
			warehouseVO.setName("Update"+new Random().nextInt(10000));
			
			warehouseVO.setUpdatedby(getBDSessionContext().getBLSession().getSessionUserId());
			warehouseVO.setReason("Update Test");
			
			wareHouseBD.updateWarehouseType(warehouseVO);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	
}
