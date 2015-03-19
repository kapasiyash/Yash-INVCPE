package com.elitecore.cpe.bl.ws.data.input.request;

import java.io.Serializable;

public class ResourceRequestData implements Serializable{

	private static final long serialVersionUID = 1L;
	private String resourceType;
	private String resourceName;
	private String model;
	
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResourceRequestData [resourceType=");
		builder.append(resourceType);
		builder.append(", resourceName=");
		builder.append(resourceName);
		builder.append(", model=");
		builder.append(model);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
