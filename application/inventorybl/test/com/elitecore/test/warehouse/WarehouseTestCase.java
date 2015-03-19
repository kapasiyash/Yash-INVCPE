/**
 * 
 */
package com.elitecore.test.warehouse;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.data.common.ComboData;
import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.delegates.master.WareHouseBD;
import com.elitecore.cpe.bl.vo.master.ResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.SearchResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.UpdateResourceSubTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseTypeVO;
import com.elitecore.cpe.bl.vo.master.WarehouseVO;
import com.elitecore.cpe.util.logger.Logger;

import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 * 
 */
public class WarehouseTestCase extends BaseTestCase {

	WareHouseBD wareHouseBD = null;

	@Before
	public void setUp() {
		wareHouseBD = new WareHouseBD(getBDSessionContext());
	}

	@Test
	public void testWarehouseCreate() {
		try {
			String alias = ("WH" + new Random().nextInt(10000)).toUpperCase();
			alias = alias.replace(" ", "_");
			WarehouseVO warehouseVO = new WarehouseVO();
			warehouseVO.setName("WH" + new Random().nextInt(10000));

			warehouseVO.setLocation("TestLoc");
			warehouseVO.setAlias(alias);

			warehouseVO.setCreatedby(getBDSessionContext().getBLSession()
					.getSessionUserId());
			warehouseVO.setUpdatedby(getBDSessionContext().getBLSession()
					.getSessionUserId());
			warehouseVO.setCreateDate(new Timestamp(new Date().getTime()));

			warehouseVO.setOwner("TestOwner");
			warehouseVO.setParentWarehouseId(22L);
			warehouseVO.setEmailId("test@test.com");
			WarehouseTypeVO warehouseTypeVO = new WarehouseTypeVO();
			warehouseTypeVO.setWarehouseTypeId(41L);

			warehouseVO.setWarehouseType(warehouseTypeVO);
			wareHouseBD.saveWarehouse(warehouseVO);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testWarehouseSearch() {

		try {
			WarehouseVO data = new WarehouseVO();

			List<WarehouseVO> response = wareHouseBD.searchWarehouseData(data);

			if (response != null && !response.isEmpty()) {
				for (WarehouseVO warehouseVO : response) {
					System.out.println(warehouseVO);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	public void testWarehouseUpdate() {

		try {
			WarehouseVO warehouseVO = new WarehouseVO();
			warehouseVO.setWarehouseId(201L);
			warehouseVO.setName("UpdateWH" + new Random().nextInt(10000));
			warehouseVO.setDescription("Updation");

			warehouseVO.setUpdatedby(getBDSessionContext().getBLSession()
					.getSessionUserId());
			warehouseVO.setReason("Update test");

			warehouseVO.setParentWarehouseId(22L);

			WarehouseTypeVO warehouseTypeVO = new WarehouseTypeVO();
			warehouseTypeVO.setWarehouseTypeId(41L);

			warehouseVO.setWarehouseType(warehouseTypeVO);

			wareHouseBD.updateWarehouse(warehouseVO);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
