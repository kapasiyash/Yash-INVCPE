/**
 * 
 */
package com.elitecore.test.audit;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.delegates.master.ItemBD;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.vo.master.ItemVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditWrapper;
import com.elitecore.cpe.bl.vo.system.audit.SearchWsAuditVO;
import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 *
 */
public class AuditTestCase extends BaseTestCase{
	
	
	ConfigureAuditBD configureAuditBD = null;
	@Before
	public void setUp() {
		 configureAuditBD = new ConfigureAuditBD(getBDSessionContext());
	}
	
	@Test
	public void testAuditSearch() {
		
		try {
			
			//ItemBD itemBD = new ItemBD(getBDSessionContext());
			
			 SearchAuditWrapper response = configureAuditBD.searchAudit(null);
			
			if(response!=null) {
				for(SearchAuditVO searchAuditVO : response.getSearchAuditVOs()) {
					System.out.println(searchAuditVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	@Test
	public void testWSAuditSearch() {
		
		try {
			
			//ItemBD itemBD = new ItemBD(getBDSessionContext());
			
			List<SearchWsAuditVO> response = configureAuditBD.searchWsAudit(null,null,null,null,null,null);
			
			if(response!=null && !response.isEmpty()) {
				for(SearchWsAuditVO searchAuditVO : response) {
					System.out.println(searchAuditVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
