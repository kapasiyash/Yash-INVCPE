package com.elitecore.cpe.bl.data.system.audit;

public enum EntityTypeEnum {

	checkCPEResource(1L), BookCPEResource(2L),changeInventoryStatus(3L),releaseCPEResource(4L),markCPEAsFaultyWithOwnerChang(5L);
	
	private Long id;
	
	EntityTypeEnum(Long id) {
		this.id = id;
	}
	
	public Long getId() {
			return id;
	}
	
}
