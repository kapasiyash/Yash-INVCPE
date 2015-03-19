package com.elitecore.cpe.bl.session.bss.user;

import java.util.List;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.bss.user.BSSUser;
import com.elitecore.cpe.bl.exception.SearchBLException;


@Local
public interface BSSUserSessionBeanLocal {

	public BSSUser findBSSUserById(String userId) throws SearchBLException;
	public BSSUser findByUsername(String username) throws SearchBLException;
	public List<BSSUser> findAllUser() throws SearchBLException;
	
}
