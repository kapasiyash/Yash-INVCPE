package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;
import java.util.List;
import com.elitecore.cpe.bl.ws.data.input.vo.CPEInventoryVO;

public class ReleaseCPERequestVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer operationType;
	private List<CPEInventoryVO> inventoryVOs;
	
	public Integer getOperationType() {
		return operationType;
	}
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	public List<CPEInventoryVO> getInventoryVOs() {
		return inventoryVOs;
	}
	public void setInventoryVOs(List<CPEInventoryVO> inventoryVOs) {
		this.inventoryVOs = inventoryVOs;
	}
	@Override
	public String toString() {
		return "ReleaseCPERequestVO [operationType=" + operationType
				+ ", inventoryVOs=" + inventoryVOs + "]";
	}
}
