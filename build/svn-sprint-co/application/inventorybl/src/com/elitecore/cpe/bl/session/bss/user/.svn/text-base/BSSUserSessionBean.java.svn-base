package com.elitecore.cpe.bl.session.bss.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.elitecore.cpe.bl.entity.inventory.bss.user.BSSUser;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.session.BaseSessionBean;
import com.elitecore.cpe.util.logger.Logger;

/**
 * Session Bean implementation class BSSUserSessionBean
 */
@Stateless
public class BSSUserSessionBean extends BaseSessionBean implements BSSUserSessionBeanLocal {

    private static final String MODULE = "BSS-USER-SB";

	/**
     * Default constructor. 
     */
    public BSSUserSessionBean() {
    }

	@Override
	public BSSUser findBSSUserById(String userId) throws SearchBLException {
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside findBSSUserById userId : " + userId  );
		}
		try{
			BSSUser user= null;
			if(userId!=null){
				
				user = getEntityManager().find(BSSUser.class, userId);
			}else{
				throw new SearchBLException("No user found.");
			}
			if(isTraceLevel()){
				Logger.logTrace(MODULE, "returning findBSSUserById");
			}
			
			return user;
		}catch(Exception e){
			throw new SearchBLException(e.getMessage());
		}
	}


	@Override
	public BSSUser findByUsername(String username) throws SearchBLException {
		if(isTraceLevel())
			logTrace(MODULE, "Inside  findByUsername");
		try {
			return (BSSUser)getEntityManager().createNamedQuery("BSSUser.findByUsername")
			.setParameter("username", username).getSingleResult();
		}catch(NoResultException e) {
			return null;
		}catch(Exception e) {
			if(isErrorLevel())
				logError(MODULE, "Error in findByUsername Reason" +e.getMessage());
			throw new SearchBLException("Find System User by username operation failed " , e);
		}
	}

	@Override
	public List<BSSUser> findAllUser() throws SearchBLException {
		
		if(isTraceLevel()){
			Logger.logTrace(MODULE, "inside findAllUser"  );
		}
		try{
				
				return getEntityManager().createNamedQuery("BSSUser.findAll").getResultList();
			
			
		}catch(Exception e){
			throw new SearchBLException(e.getMessage());
		}
		
	}

}
