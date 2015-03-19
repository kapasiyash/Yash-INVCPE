package com.elitecore.cpe.bl.session.user;

import java.io.Serializable;

import javax.ejb.Local;

import com.elitecore.cpe.bl.entity.inventory.user.EncryptionType;
import com.elitecore.cpe.bl.exception.SearchBLException;



@Local
public interface UserSessionBeanLocal extends Serializable {
	
	
	public EncryptionType findEncryptionTypeById(String encryptionType) throws SearchBLException;
}