package com.elitecore.cpe.bl.delegates.notification;

import java.sql.Timestamp;
import java.util.List;

import javax.naming.NamingException;

import com.elitecore.cpe.bl.data.common.ComboBoxData;
import com.elitecore.cpe.bl.data.notification.DocumentTemplateWrapperdata;
import com.elitecore.cpe.bl.data.notification.MessageTagWrapperData;
import com.elitecore.cpe.bl.delegates.BaseBusinessDelegate;
import com.elitecore.cpe.bl.exception.CreateBLException;
import com.elitecore.cpe.bl.exception.SearchBLException;
import com.elitecore.cpe.bl.exception.TechnicalException;
import com.elitecore.cpe.bl.exception.UpdateBLException;
import com.elitecore.cpe.bl.facade.notification.INotificationFacade;
import com.elitecore.cpe.bl.facade.notification.NotificationFacadeLocal;
import com.elitecore.cpe.bl.facade.notification.NotificationFacadeRemote;
import com.elitecore.cpe.bl.vo.configuration.notification.CheckValidDateForTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.NotificationCategoryVO;
import com.elitecore.cpe.bl.vo.configuration.notification.SearchDocumentTemplateVO;
import com.elitecore.cpe.bl.vo.configuration.notification.ViewDocumentTemplateVO;
import com.elitecore.cpe.bl.vo.master.warehouse.CreateWareHouseTreeVO;
import com.elitecore.cpe.core.IBDSessionContext;

/**
 * 
 * @author Yash.Kapasi
 *
 */
public class NotificationBD extends BaseBusinessDelegate {

	public NotificationBD(IBDSessionContext context) {
		super(context);
		
	}
	
	private static final String MODULE ="NOTIFICATION-BD";
	private static INotificationFacade facade;
	
	private INotificationFacade getFacade() throws NamingException {
 		if (facade == null) {
 			if (isLocalMode()) {
 				facade = (INotificationFacade)lookupLocal(NotificationFacadeLocal.class);
 			}else {
 				facade = (INotificationFacade)lookup(NotificationFacadeRemote.class);
 			}
 		}
 		return facade;
 	}

	
	/**
	 * Find all Template category
	 * @return {@link List}<{@link NotificationCategoryVO}>
	 * @throws SearchBLException
	 */
	public List<NotificationCategoryVO> findAllTemplateCategory() throws SearchBLException, TechnicalException {
		try {
			return getFacade().findAllTemplateCategory();
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	/**
	 * Find Message Tag by Document Category Id
	 * @param Long documentCatId
	 * @return {@link List}<{@link MessageTagWrapperData}>
	 * @throws SearchBLException
	 */
	public List<MessageTagWrapperData> findMessageTagByDocCat(Long documentCatId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().findMessageTagByDocCat(documentCatId);
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	/**
	 * Create Document Template
	 * @param {@link DocumentTemplateWrapperdata} templateWrapperdata
	 * @throws CreateBLException
	 */
	public void createDocumentTemplate(DocumentTemplateWrapperdata templateWrapperdata) throws CreateBLException, TechnicalException {
		try {
			getFacade().createDocumentTemplate(templateWrapperdata,getBLSession());
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
		
	}

	
	/**
	 * Search Document Template by name and categoryId
	 * @param {@link String} name
	 * @param {@link Long} categoryId
	 * @return {@link List}<{@link SearchDocumentTemplateVO}>
	 * @throws SearchBLException
	 */
	public List<SearchDocumentTemplateVO> searchDocumentTemplate(String name,Long categoryId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().searchDocumentTemplate(name,categoryId);
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	
	/**
	 * Find Document Data by Document Id
	 * @param {@link Long} documentId
	 * @return {@link ViewDocumentTemplateVO}
	 * @throws SearchBLException
	 */
	public ViewDocumentTemplateVO findDocumentViewData(Long documentId) throws SearchBLException, TechnicalException {
		
		try {
			return getFacade().findDocumentViewData(documentId);
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	
	/**
	 * Update Document Template Basic Details
	 * @param {@link DocumentTemplateWrapperdata} wrapperdata
	 * @throws UpdateBLException
	 */
	public void updateDocumentTemplateBasicDetails(DocumentTemplateWrapperdata wrapperdata) throws UpdateBLException, TechnicalException {
		
		try {
			getFacade().updateDocumentTemplateBasicDetails(wrapperdata,getBLSession());
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	/**
	 * Find Document Template Data by document Id
	 * @param {@link Long} documentId
	 * @return {@link DocumentTemplateWrapperdata}
	 * @throws SearchBLException
	 */
	public DocumentTemplateWrapperdata findDocumentTemplateData(
			Long documentId) throws SearchBLException, TechnicalException {
		try {
			return getFacade().findDocumentTemplateData(documentId);
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}


	/**
	 * Update Document Template 
	 * @param {@link DocumentTemplateWrapperdata} templateWrapperdata
	 * @throws UpdateBLException
	 */
	public void updateDocumentTemplate(DocumentTemplateWrapperdata templateWrapperdata) throws UpdateBLException, TechnicalException {
		
		try {
			getFacade().updateDocumentTemplate(templateWrapperdata,getBLSession());
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

	/**
	 * Find Document Template Categories
	 * @return {@link List}<{@link ComboBoxData}>
	 * @throws SearchBLException
	 */
	public List<ComboBoxData> findAllTemplateCategoryFromEngine() throws SearchBLException, TechnicalException {
		try {
			return getFacade().findAllTemplateCategoryFromEngine();
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

/*	public String checkValidDateForTemplate(Long documentCategoryId,
			Timestamp validFormDate, Timestamp validToDate) throws TechnicalException {
*/
	public String checkValidDateForTemplate(CheckValidDateForTemplateVO checkValidDateForTemplateVO) throws TechnicalException {

	try {
			return getFacade().checkValidDateForTemplate(checkValidDateForTemplateVO);
		} catch (NamingException e) {
				e.printStackTrace();
			throw new TechnicalException(e.getMessage(),e);
		}
	}

}
