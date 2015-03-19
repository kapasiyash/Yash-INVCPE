package com.elitecore.test.agent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.elitecore.cpe.bl.delegates.system.agent.SystemAgentBD;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.test.base.BaseTestCase;
import com.elitecore.utility.agentframework.data.AgentServiceSummaryData;

public class SystemAgentTestCase extends BaseTestCase {

	private SystemAgentBD agentBD;
	private String strAgentRunId;
	private String agentScheduleId;
	private String agentRunId;

	@Before
	public void setUp() {
		agentBD = new SystemAgentBD(getBDSessionContext());
		strAgentRunId = "";
		agentScheduleId="1";
		agentRunId="";
	}
	

	private void toString(Object obj){
		Class clazz = obj.getClass();
		System.out.println(clazz.getName());
		if(!clazz.isInterface()){
			Method[] methods = clazz.getMethods();
			for(Method method : methods){
				if(method.getName().startsWith("get")){
					if(method.getParameterTypes().length==0){
						try {
							System.out.println(method.getName().substring(3) +  " " + method.invoke(obj, new Object[]{}));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
			/*for(Field field :  fields){
				System.out.println(field.getName() + " " + field.getType());
				toString(field,obj);
			}*/
		}
	}
	
	/*private void toString(Field field ,Object obj){
		System.out.println(field.getType());
		if(field.getType().equals("java.lang.String")){
			try {
				System.out.println(field.get(obj));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	/*@Test
	public void testServiceChangeStatus() {
		System.out.println("testSErviceChangeStatus");
		AgentServiceVO agentServiceVO = new AgentServiceVO();
		agentServiceVO.setEnable(true);
		try {
			agentBD.changeAgentServiceStatus(agentServiceVO);
		} catch (TechnicalException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (UpdateBLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}*/

	public static void methodGenerator() {
		StringBuffer buffer = new StringBuffer();
		Class<SystemAgentBD> clazz = SystemAgentBD.class;
		Method[] methods = clazz.getMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				buffer.append("\n\n// Test Method");
				buffer.append("\n@Test\npublic void test" + method.getName()
						+ "() {");
				buffer.append("\t\nLogger.logTrace(\"AGENT-TC\",\" inside test"
						+ method.getName() + "  \"); ");
				buffer.append("\n\t try{");
				String parameter = "";

				buffer.append("\n\t\t agentBD." + method.getName() + "("
						+ parameter + ");");
				buffer.append("\n\t }catch(Exception ex){ ex.printStackTrace();\n"
						+ "fail(ex.getMessage());\n}");
				buffer.append("}");
			}
			System.out.println(buffer.toString());
		}

	}

	public static void main(String[] args) {
		methodGenerator();
	}
	
	/*// Test Method
	@Test
	public void teststopService() {
		Logger.logTrace("AGENT-TC", " inside teststopService  ");
		try {
			agentBD.stopService();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}
	// Test Method
		@Test
		public void teststartService() {
			Logger.logTrace("AGENT-TC", " inside teststartService  ");
			try {
				agentBD.startService();
			} catch (Exception ex) {
				ex.printStackTrace();
				fail(ex.getMessage());
			}
		}
	// Test Method
*/	@Test
	public void teststopAgentRunByName() {
		Logger.logTrace("AGENT-TC", " inside teststopAgentRunByName  ");
		try {
			agentBD.stopAgentRunByName(agentRunId);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	// Test Method
	@Test
	public void teststopAgentRunByScheduleId() {
		Logger.logTrace("AGENT-TC", " inside teststopAgentRunByScheduleId  ");
		try {
			agentBD.stopAgentRunByScheduleId(agentScheduleId);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	// Test Method
	@Test
	public void testgetAgentServiceThreadPools() {
		Logger.logTrace("AGENT-TC", " inside testgetAgentServiceThreadPools  ");
		try {
			System.out.println(agentBD.getAgentServiceThreadPools());
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	// Test Method
	@Test
	public void testgetMonitorShortDescription() {
		Logger.logTrace("AGENT-TC", " inside testgetMonitorShortDescription  ");
		try {
			String str =agentBD.getMonitorShortDescription();
			System.out.println(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	// Test Method
	@Test
	public void testgetAgentManagerDetails() {
		Logger.logTrace("AGENT-TC", " inside testgetAgentManagerDetails  ");
		try {
			System.out.println(agentBD.getAgentManagerDetails());
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	// Test Method
	@Test
	public void testgetAgentRunStates() {
		Logger.logTrace("AGENT-TC", " inside testgetAgentRunStates  ");
		try {
		Collection collections = agentBD.getAgentRunStates();
		System.out.println(collections);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	// Test Method
	@Test
	public void testdoThreadMonitoring() {
		Logger.logTrace("AGENT-TC", " inside testdoThreadMonitoring  ");
		try {
			agentBD.doThreadMonitoring();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	// Test Method
	@Test
	public void testgetServiceState() {
		Logger.logTrace("AGENT-TC", " inside testgetServiceState  ");
		try {
			System.out.println(agentBD.getServiceState());
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	// Test Method
	@Test
	public void testgetAgentServiceSummary() {
		Logger.logTrace("AGENT-TC", " inside testgetAgentServiceSummary  ");
		try {
			AgentServiceSummaryData summary = agentBD.getAgentServiceSummary();
			toString(summary);
			System.out.println(summary);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}
	
	// Test Method
	@Test
	public void teststopScheduleByAgentRunId() {
		Logger.logTrace("AGENT-TC", " inside teststopScheduleByAgentRunId  ");
		try {
			agentBD.stopScheduleByAgentRunId(strAgentRunId);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

}
