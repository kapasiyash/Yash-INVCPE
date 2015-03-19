/**
 * 
 */
package com.elitecore.cpe.bl.agents.customs;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.elitecore.cpe.bl.agents.base.BaseAgentRun;
import com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter;
import com.elitecore.cpe.bl.agents.base.BaseEntity;
import com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter;
import com.elitecore.cpe.bl.agents.base.BaseMasterEntity;
import com.elitecore.cpe.bl.agents.base.BaseSchedule;
import com.elitecore.cpe.bl.constants.system.audit.AuditConstants;
import com.elitecore.cpe.bl.constants.system.parameter.SystemParameterConstants;
import com.elitecore.cpe.bl.constants.user.UserConstants;
import com.elitecore.cpe.bl.facade.inventorymgt.InventoryManagementFacadeRemote;
import com.elitecore.cpe.bl.facade.system.systemparameter.SystemParameterFacadeRemote;
import com.elitecore.cpe.bl.facade.system.user.UserFacadeRemote;
import com.elitecore.cpe.bl.factory.system.user.UserFactory;
import com.elitecore.cpe.bl.vo.inventorymgt.InventoryUploadVO;
import com.elitecore.cpe.bl.vo.system.user.UserVO;
import com.elitecore.cpe.core.IBDSessionContext;
import com.elitecore.cpe.util.logger.Logger;
import com.elitecore.utility.agentframework.entities.IAgentRunEntity;
import com.elitecore.utility.agentframework.entities.IAgentRunMasterEntity;

/**
 * @author Joel.Macwan
 *
 */
public class InventoryUploadAgent  extends BaseAgentRun implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private static final String MODULE = "INVENTORYUPLOAD-AGENT";
	SystemParameterFacadeRemote systemParameterFacadeRemote=null;
	
//	@EJB InventoryManagementFacadeLocal inventoryManagementFacadeLocal;
	
	
	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#agentRunStarted()
	 */
	@Override
	public void agentRunStarted() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#preAgentRunMasterEntity(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseMasterEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter)
	 */
	@Override
	public void preAgentRunMasterEntity(BaseSchedule schedule,
			BaseMasterEntity runMasterEntity,
			BaseEnityEnvParameter agentRunEnvParamList) throws Exception {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside preAgentRunMasterEntity ");
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#getAgentRunMasterEntities(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter)
	 */
	@Override
	public List<IAgentRunMasterEntity> getAgentRunMasterEntities(
			BaseSchedule schedule, BaseEnityEnvParameter agentRunEnvParamList) {
		// TODO Auto-generated method stub
		List<IAgentRunMasterEntity> retList= new ArrayList<IAgentRunMasterEntity>();
		try{
			
			
			
			BaseMasterEntity baseMasterEntity = new BaseMasterEntity("1", "1");
			retList.add(baseMasterEntity);
						
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return retList;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#getAgentRunMasterEntityData(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseMasterEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter)
	 */
	@Override
	public BaseMasterEnityEnvParameter getAgentRunMasterEntityData(
			BaseSchedule schedule, BaseMasterEntity runMasterEntity,
			BaseEnityEnvParameter agentRunEnvParamList) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside getAgentRunMasterEntityData");
		BaseMasterEnityEnvParameter masterEnityEnvParameter = new BaseMasterEnityEnvParameter();
		return masterEnityEnvParameter;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#agentMasterEntityRunStarted(com.elitecore.cpe.bl.agents.base.BaseMasterEntity)
	 */
	@Override
	public void agentMasterEntityRunStarted(BaseMasterEntity runMasterEntity) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "agentMasterEntityRunStarted : " );
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#preAgentRunProcess(com.elitecore.cpe.bl.agents.base.BaseSchedule)
	 */
	@Override
	public void preAgentRunProcess(BaseSchedule schedule) throws Exception {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "preAgentRunProcess() : " );
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#getAgentRunEntities(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseMasterEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter, com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter)
	 */
	@Override
	public List<IAgentRunEntity> getAgentRunEntities(BaseSchedule schedule,
			BaseMasterEntity runMasterEntity,
			BaseEnityEnvParameter enityEnvParameter,
			BaseMasterEnityEnvParameter masterEnityEnvParameter) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, " ---------inside getAgentRun Entities"+masterEnityEnvParameter);
		List<IAgentRunEntity> retList = new ArrayList<IAgentRunEntity>();
		try {
		 systemParameterFacadeRemote = (SystemParameterFacadeRemote) lookup(SystemParameterFacadeRemote.class);
		
		String sourcePath = systemParameterFacadeRemote.getSystemParameterValue(SystemParameterConstants.INVENTORY_SOURCE_FILE_PATH);
		File folder = new File(sourcePath);
		Logger.logTrace(MODULE, "Directory Name :" + folder.getName());
		File[] listOfFiles = folder.listFiles();
		File Oldfile=null;
		String filename=null;
	//	String filenameNew;
		String ext =null;

		if(listOfFiles!=null) 
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					if(listOfFiles[i].getName().toLowerCase().endsWith(".csv")){
						
					//filename = listOfFiles[i].getName();
					 filename=listOfFiles[i].getAbsolutePath();
					// filenameNew=listOfFiles[i].getName();
					 Logger.logTrace(MODULE, "Filename:"+ filename);
					 ext=filename.substring(filename.lastIndexOf("."));
					 Logger.logTrace(MODULE, "Exten.:"+ ext);
					filename=filename.replace(ext,".picked");
					File change = new File(filename); 
					 Logger.logTrace(MODULE, "Filename:"+ filename);
					 Oldfile=new File(listOfFiles[i].getAbsolutePath());
					 boolean isChanged = Oldfile.renameTo(change);
					 
						if(isChanged){
							Logger.logDebug(MODULE, "File renamedAfter Picked");
						}
					 Logger.logTrace(MODULE, "Filename change file:"+change.getAbsolutePath() );
					 Logger.logTrace(MODULE, "Filename After change status:"+Oldfile.getAbsolutePath() );
					Logger.logTrace(MODULE, "File Added:" + Oldfile);
					retList.add(new BaseEntity("/"+change.getAbsolutePath()));
					
					
					}
				} 
			}

			for(IAgentRunEntity list:retList){
				Logger.logTrace(MODULE, "List in getAgentRun Entities():" + list.getEntityId());
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
		return retList;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#runAgentTask(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter, com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter)
	 */
	@Override
	public boolean runAgentTask(BaseSchedule schedule,
			BaseEntity agentRunEntity, BaseEnityEnvParameter enityEnvParameter,
			BaseMasterEnityEnvParameter masterEnityEnvParameter) {

		boolean retValue = true;
		
		if(agentRunEntity!=null) {
			 
			 Boolean status=false;
			 
			 File file =null;
			try{
				
				Logger.logTrace(MODULE, "#########inside runAgentTask "+agentRunEntity.getEntityId() );
				//SystemParameterFacadeRemote systemParameterFacadeRemote = (SystemParameterFacadeRemote) lookup(SystemParameterFacadeRemote.class);
				if(systemParameterFacadeRemote!=null){
					Logger.logTrace(MODULE, " In runAgentTask systemParameterFacadeRemote is not null ");
				}else{
					Logger.logTrace(MODULE, " In runAgentTask systemParameterFacadeRemote is null ");
					 systemParameterFacadeRemote = (SystemParameterFacadeRemote) lookup(SystemParameterFacadeRemote.class);
				}
				String sourcePath = systemParameterFacadeRemote.getSystemParameterValue(SystemParameterConstants.INVENTORY_SOURCE_FILE_PATH);
				String destinationPath = systemParameterFacadeRemote.getSystemParameterValue(SystemParameterConstants.INVENTORY_DESTINATION_FILE_PATH);
				String MODULE="test agent";
				if(sourcePath!=null && !sourcePath.isEmpty() && destinationPath!=null && !destinationPath.isEmpty()){
				
					Logger.logTrace(MODULE,"Path of Source file"+agentRunEntity.getEntityId());
					if (agentRunEntity != null) {
						file = new File( agentRunEntity.getEntityId());
						// file = new
						// File("file://"+"/home/elitecore/InventoryUpload.csv");

						Logger.logTrace(MODULE,"Path of URISource file" + file.toURI());
						UserFacadeRemote userFacadeRemote = (UserFacadeRemote) lookup(UserFacadeRemote.class);
						
						
						IBDSessionContext sessionContext = null;
						sessionContext = userFacadeRemote.doLogin("agent", "agent", "127.0.0.1");
						/*Map<String, UserVO> map = UserFactory.findAllUser();
						
						if(map!=null && map.containsKey(UserConstants.ADMIN_USERID)) {
							UserVO admin = map.get(UserConstants.ADMIN_USERID);
							
							try {
								sessionContext = userFacadeRemote.doLogin(admin.getUsername(), admin.getPassword(), "127.0.0.1");
							} catch (Exception e) {
								e.printStackTrace();
								sessionContext = userFacadeRemote.doLogin("agent", "agent", "127.0.0.1");
							}
						} else {
							 sessionContext = userFacadeRemote.doLogin("agent", "agent", "127.0.0.1");
						}*/
						

						

						Logger.logTrace(MODULE,"Path of file" + file.getAbsolutePath());
						FileInputStream fin = new FileInputStream(file);
						byte fileContent[] = new byte[(int) file.length()];
						fin.read(fileContent);
						Logger.logTrace(MODULE, "size of array:"+ fileContent.length);
						InventoryUploadVO uploadVO = new InventoryUploadVO();
						uploadVO.setUploadbyteData(fileContent);
						uploadVO.setStaffId(UserConstants.AGENT_USERID);

						InventoryManagementFacadeRemote inventoryManagementFacadeRemote = (InventoryManagementFacadeRemote) lookup(InventoryManagementFacadeRemote.class);
						InventoryUploadVO inventoryUploadVO2 = inventoryManagementFacadeRemote.uploadInventory(uploadVO,sessionContext.getBLSession());
						Logger.logTrace(MODULE,	"After inventoryManagementFacadeLocal.uploadInventory() call inventoryUploadVO2:"+ inventoryUploadVO2);
						if (inventoryUploadVO2 != null	&& (inventoryUploadVO2.getValidEntry() > 0)) {
							
							status = true;
							if (inventoryUploadVO2.getBatchNo() != null && !inventoryUploadVO2.getBatchNo().isEmpty()) {
								inventoryManagementFacadeRemote.searchInventoryUploadData(inventoryUploadVO2.getBatchNo(),status, destinationPath);
							}
						}
						if (inventoryUploadVO2 != null && (inventoryUploadVO2.getInvalidEntry() > 0)) {
							
							status = false;
							if (inventoryUploadVO2.getBatchNo() != null && !inventoryUploadVO2.getBatchNo().isEmpty()) {
								inventoryManagementFacadeRemote.searchInventoryUploadData(inventoryUploadVO2.getBatchNo(),status, destinationPath);
							}
						}
						fin.close();
					}
					
					String filename=agentRunEntity.getEntityId();
					String ext=filename.substring(filename.lastIndexOf("."));
					
					filename=filename.replace(ext,".processed");
					File change = new File(filename); 
					boolean isChanged = file.renameTo(change);
					if(isChanged){
						Logger.logDebug(MODULE, "File renamed");
					}
				}else{
					Logger.logTrace(MODULE, "Source path or Destination Path  empty");
					
				}
			}catch(Exception e){
				e.printStackTrace();
				retValue = false;
				status=false;
				String filename=agentRunEntity.getEntityId();
				String ext=filename.substring(filename.lastIndexOf("."));
				
				filename=filename.replace(ext,".processed");
				File change = new File(filename); 
				if(file!=null) {
					boolean isChanged = file.renameTo(change);
					if(isChanged){
						Logger.logDebug(MODULE, "File renamed");
					}
				}
			}
		}
		
		
		
		return retValue;
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#postAgentRunMasterEntity(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseMasterEntity, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter, com.elitecore.cpe.bl.agents.base.BaseMasterEnityEnvParameter)
	 */
	@Override
	public void postAgentRunMasterEntity(BaseSchedule schedule,
			BaseMasterEntity agentProcessEntity, BaseEnityEnvParameter gParam,
			BaseMasterEnityEnvParameter aParam) throws Exception {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside postAgentRunMasterEntity ");
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#postAgentRun(com.elitecore.cpe.bl.agents.base.BaseSchedule, com.elitecore.cpe.bl.agents.base.BaseEnityEnvParameter)
	 */
	@Override
	public void postAgentRun(BaseSchedule schedule,
			BaseEnityEnvParameter agentRunEnvParamList) throws Exception {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside postAgentRun ");
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#agentRunEnded()
	 */
	@Override
	public void agentRunEnded() {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside postAgentRun ");
		
	}

	/* (non-Javadoc)
	 * @see com.elitecore.cpe.bl.agents.base.BaseAgentRun#agentMasterEntityRunEnded(com.elitecore.cpe.bl.agents.base.BaseMasterEntity)
	 */
	@Override
	public void agentMasterEntityRunEnded(BaseMasterEntity runMasterEntity) {
		// TODO Auto-generated method stub
		Logger.logTrace(MODULE, "inside agentMasterEntityRunEnded ");
	}

}
