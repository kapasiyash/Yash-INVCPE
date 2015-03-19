package com.elitecore.cpe.bl.session.user;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;

import com.elitecore.cpe.bl.entity.inventory.user.EncryptionType;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.util.logger.Logger;


@Stateless
@TransactionManagement (TransactionManagementType.CONTAINER)
public class UserSessionBean extends BaseSessionBean implements UserSessionBeanLocal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "USER-SB";
	
	

	public EncryptionType findEncryptionTypeById(String encryptionType) throws SearchBLException {
		if(isTraceLevel())
				Logger.logTrace(MODULE, "Inside  findEncryptionTypeById : encryptionTypeId = " + encryptionType);
		try {				
			return (EncryptionType)getEntityManager().createNamedQuery("EncryptionType.findById").setParameter("encryptionTypeId", encryptionType).getSingleResult();
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Error in findEncryptionTypeById Reason" +e.getMessage());
			throw new SearchBLException("Find EncryptionType  by encryptiontype operation failed, reason: " + e.getMessage(), e);
		}
	}
}
