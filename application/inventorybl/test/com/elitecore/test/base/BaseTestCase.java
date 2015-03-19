package com.elitecore.test.base;

import org.junit.Test;

import junit.framework.TestCase;

import com.elitecore.cpe.bl.delegates.system.user.UserBD;
import com.elitecore.cpe.bl.exception.AccessDeniedException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.core.IBDSessionContext;


public class BaseTestCase extends TestCase{
	private IBDSessionContext bdSessionContext;
		
		protected final IBDSessionContext getBDSessionContext() {
			if (bdSessionContext == null) {
				try {
					bdSessionContext = new UserBD().doLogin("admin", "sysadmin","127.0.0.1");
				} catch (AccessDeniedException e) {
					e.printStackTrace();
				} catch (TechnicalException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
			return bdSessionContext;
		}
		
		@Test
		public void testBaseTestCase() {
			System.out.println("Junit Start");
		}
}
