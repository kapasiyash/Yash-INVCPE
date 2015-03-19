package com.elitecore.test.migration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementFacadeRemote;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.AttributeMigrationVO;
import com.elitecore.cpe.bl.vo.inventorymgt.migration.InventoryMigrationVO;
import com.elitecore.test.base.BaseTestCase;

public class MigrationTestCase extends BaseTestCase{ 

	
	@Before
	public void setUp() {
	}
	
	@Test
	public void testMigrationAPI() {
		
		try {
		
			Properties properties = new Properties();
			properties.put("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			properties.put("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			properties.put("java.naming.provider.url", "10.105.1.32" + ":"
					+ "1199");
			
			List<InventoryMigrationVO> inventoryMigrationVOs = prepareMigrationVO();
//			InventoryMigrationResponseVO data = itemBD.migrateInventory(inventoryMigrationVOs,true);
//			System.out.println(data);
			
			InitialContext context = new InitialContext(properties);
			InventoryManagementFacadeRemote remote =  (InventoryManagementFacadeRemote) context.lookup("inventoryapp/InventoryManagementFacade/remote-com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementFacadeRemote");
			remote.uploadMigrationInventory(inventoryMigrationVOs, false);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	
	public List<InventoryMigrationVO> prepareMigrationVO() {
		
		List<InventoryMigrationVO> inventoryMigrationVOs = new ArrayList<InventoryMigrationVO>();
		for(int i=0;i<10;i++) {
			
			inventoryMigrationVOs.add(prepareSingleMigrationVO(i));
			
		}
		
		
		return inventoryMigrationVOs;
	}
	
	
	public InventoryMigrationVO prepareSingleMigrationVO(int i) {
		InventoryMigrationVO migrationVO = new InventoryMigrationVO();
		
		migrationVO.setBatchNumber("BATCHMIG012");
		migrationVO.setCreateDate(new Date());
		migrationVO.setInventoryNumber("INVMIG12"+i);
		migrationVO.setNew("Y");
		
		migrationVO.setResourceNumber("RES0000000066");
		/*migrationVO.setResourceName("DemoTest");
		migrationVO.setResourceType("DemoTest");
		migrationVO.setResourceSubType("DemoTest");*/
		
		
		migrationVO.setWareHouseName("DemoTest");
//		migrationVO.setStatus("Available");
		
		List<AttributeMigrationVO> attributeVOs = new ArrayList<AttributeMigrationVO>();
		AttributeMigrationVO attributeVO = new AttributeMigrationVO();
		attributeVO.setKey("Serial Number");
		attributeVO.setValue("48-44-47-50"+i);
		attributeVOs.add(attributeVO);
		
		migrationVO.setAttributeMigrationVOs(attributeVOs);
		
		return migrationVO;
	}
	
}
