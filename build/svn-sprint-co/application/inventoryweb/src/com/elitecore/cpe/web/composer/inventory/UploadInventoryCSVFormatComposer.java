package com.elitecore.cpe.web.composer.inventory;

import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.elitecore.cpe.bl.vo.master.AttributeVO;
import com.elitecore.cpe.web.base.ui.module.BaseModuleComposer;
import com.elitecore.cpe.web.core.exception.ModuleInitializationException;

public class UploadInventoryCSVFormatComposer extends BaseModuleComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ATTRIBUTEVO_DATA = "ATTRIBUTEVO_DATA";
	private List<AttributeVO> attributeVOs;
	private Rows rows;
	
	@Override
	public void afterCompose(Window comp) throws ModuleInitializationException {
		
		System.out.println("In Upload Inventory Format");
		
		if(arg.containsKey(ATTRIBUTEVO_DATA)) {
			attributeVOs = (List<AttributeVO>) arg.get(ATTRIBUTEVO_DATA);
		}
		
		if(attributeVOs!=null && !attributeVOs.isEmpty()) {
			prepareAttributeRow(attributeVOs);
		}
		
	}

	/**
	 * <row  align="top">
									<label value="10." style="text-align: left;" />
									<label value="MAC Address" style="text-align: left;" />
									<label value="MAC Address Information(Attributes attached with the resource)" style="text-align: left;" />
									<label value="N" style="text-align: left;" />
									<label value="N" style="text-align: left;" />
									<label value="50" style="text-align: left;" />
									<label value="Test Value" style="text-align: left;" />
								</row>
	 * 
	 */
	
	
	private void prepareAttributeRow(List<AttributeVO> attributeVOs2) {
		
		int count = 9;
		for(AttributeVO attributeVO : attributeVOs2) {
			Row row = new Row();
			row.setAlign("top");
			
			Label sr = new Label(String.valueOf(count)+".");
			sr.setStyle("text-align: left;");
			row.appendChild(sr);
			
			
			Label attribute = new Label(attributeVO.getName());
			attribute.setStyle("text-align: left;");
			row.appendChild(attribute);
			
			Label attributeInfo = new Label(attributeVO.getName()+" Information(Attributes attached with the resource)");
			attributeInfo.setStyle("text-align: left;");
			row.appendChild(attributeInfo);
			
			Label mandatory = new Label(attributeVO.getMandatory()+"");
			mandatory.setStyle("text-align: left;");
			row.appendChild(mandatory);
			
			Label unique = new Label(attributeVO.getUnique()+"");
			unique.setStyle("text-align: left;");
			row.appendChild(unique);
			
			Label size = new Label("50");
			size.setStyle("text-align: left;");
			row.appendChild(size);
			
			Label sample = new Label("Test Value");
			sample.setStyle("text-align: left;");
			row.appendChild(sample);
			
			rows.appendChild(row);
			
			count++;
		}
		
	}

}
