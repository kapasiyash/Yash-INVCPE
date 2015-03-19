/**
 * 
 */
package com.elitecore.test.agent;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.bl.delegates.system.audit.ConfigureAuditBD;
import com.elitecore.cpe.bl.vo.system.agent.AgentVO;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentRunInQueueVO;
import com.elitecore.cpe.bl.vo.system.agent.SearchAgentScheduleVO;
import com.elitecore.cpe.bl.vo.system.audit.SearchAuditVO;
import com.elitecore.test.base.BaseTestCase;

/**
 * @author Joel.Macwan
 *
 */
public class AgentTestCase extends BaseTestCase{

	
	SystemAgentBD agentBD = null;
	@Before
	public void setUp() {
		agentBD = new SystemAgentBD(getBDSessionContext());
	}
	
	@Test
	public void testAgentSearch() {
		
		try {
			
			//ItemBD itemBD = new ItemBD(getBDSessionContext());
			
			List<AgentVO> response = agentBD.getAllAgentsListByName(null);
			
			if(response!=null && !response.isEmpty()) {
				for(AgentVO agentVO : response) {
					System.out.println(agentVO);
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}	
		@Test
		public void testAgentScheduleSearch() {
			
			try {
				
				//ItemBD itemBD = new ItemBD(getBDSessionContext());
				
				List<SearchAgentScheduleVO> response = agentBD.findSystemAgentSchedule(null,null);
				
				if(response!=null && !response.isEmpty()) {
					for(SearchAgentScheduleVO agentVO : response) {
						System.out.println(agentVO);
					}
				}
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		
	}
		
		@Test
		public void testAgentRunInQueueSearch() {
			
			try {
				
				//ItemBD itemBD = new ItemBD(getBDSessionContext());
				
				List<SearchAgentRunInQueueVO> response = agentBD.findAgentRunInQueue(null,null,null);
				
				if(response!=null && !response.isEmpty()) {
					for(SearchAgentRunInQueueVO agentVO : response) {
						System.out.println(agentVO);
					}
				}else{
					System.out.println("No Agent In Queue");
				}
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		
	}

}
