package com.elitecore.cpe.util;

import java.text.SimpleDateFormat;




public class TransactionNoteBuilder implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StringBuffer rowBuffer  = new StringBuffer();
	private static final String HEADER_CELL_STYLE ="padding-top: 4px; padding-left: 4px; border-color: #E1E1E1; border-top-width: 1px; border-left-width: 0px; border-bottom-width: 1px; border-right-width: 1px; height: 18px; background-color: #E8E8E8; color: #1F1A17;";
	private static final String FIRST_HEADER_CELL_STYLE ="padding-top: 4px; padding-left: 4px; border-color: #E1E1E1; border-top-width: 1px; border-left-width: 1px; border-bottom-width: 1px; border-right-width: 1px; height: 18px; background-color: #E8E8E8; color: #1F1A17;";
	private static final String CELL_STYLE="border-style: solid; border-color: #E1E1E1; border-top-width: 1px; border-left-width: 0px; border-bottom-width: 1px; border-right-width: 1px; padding: 3px;";
	private static final String FIRST_CELL_STYLE="border-style: solid; border-color: #E1E1E1; border-top-width: 1px; border-left-width: 1px; border-bottom-width: 1px; border-right-width: 1px; padding: 3px;";
	public static final String DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	private boolean isHeadersGenerated =false;
	
	private static String prepareHeaderCell(String header,int width){
		return prepareHeaderCell(header,width,false);
	}
	private static String prepareHeaderCell(String header,int width,boolean isFirst){
		StringBuffer headerCell = new StringBuffer();
		headerCell.append("<td style=\"");
		if(isFirst){
			headerCell.append(FIRST_HEADER_CELL_STYLE);
		}else{
			headerCell.append(HEADER_CELL_STYLE);
		}
		headerCell.append("\" width=\""+width+"px\" > ");
		headerCell.append(header);
		headerCell.append("</td>");
		return headerCell.toString();
	}
	
	private static String prepareHeaderCell(String header,int width,int colspan){
		StringBuffer headerCell = new StringBuffer();
		headerCell.append("<td colspan=\"" +colspan+ "\" style=\"");
		headerCell.append(HEADER_CELL_STYLE);
		headerCell.append("\" width=\""+width+"px\" > ");
		headerCell.append(header);
		headerCell.append("</td>");
		return headerCell.toString();
	}
	
	private static String prepareCell(String value,int width){
		return prepareCell(value,width,false);
	}
	
	
	private static String prepareCell(String value,int width,boolean isfirstcell){
		StringBuffer headerCell = new StringBuffer();
		headerCell.append("<td style=\"");
		if(isfirstcell){
			headerCell.append(FIRST_CELL_STYLE);
		}else{
			headerCell.append(CELL_STYLE);
		}
		headerCell.append("\" width=\""+width+"px\" > ");
		headerCell.append(value);
		headerCell.append("</td>");
		return headerCell.toString();
	}
	
	
	private static String prepareCell(String value,int width ,int colspan){
		StringBuffer headerCell = new StringBuffer();
		headerCell.append("<td colspan=\"" +colspan+ "\" style=\"");
		headerCell.append(CELL_STYLE);
		headerCell.append("\" width=\""+width+"px\" > ");
		headerCell.append(value);
		headerCell.append("</td>");
		return headerCell.toString();
	}
	private static String tableCreator(String rows){
		StringBuffer tableBuffer = new StringBuffer();
		tableBuffer.append("<div width=\"800px\" >	<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family:Arial; font-size: 12px;\" width=\"800px\">	<tbody>");
		tableBuffer.append(rows);
		tableBuffer.append("</tbody> </table> </div>");
		return tableBuffer.toString();
	}
	public TransactionNoteBuilder() {
		
	}
	
	public TransactionNoteBuilder appendRow(String value1,String value2,String value3,String value4,boolean isHeader){
		if(!isHeadersGenerated && isHeader){
			isHeadersGenerated = isHeader;
		}
		this.rowBuffer.append("<tr>");
		if(isHeader){
			this.rowBuffer.append(prepareHeaderCell(value1, 200,true));
			this.rowBuffer.append(prepareHeaderCell(value2, 200));
			this.rowBuffer.append(prepareHeaderCell(value3, 200));
			this.rowBuffer.append(prepareHeaderCell(value4, 200));
		}else{
			this.rowBuffer.append(prepareCell(value1, 200,true));
			this.rowBuffer.append(prepareCell(value2, 200));
			this.rowBuffer.append(prepareCell(value3, 200));
			this.rowBuffer.append(prepareCell(value4, 200));
		}
		this.rowBuffer.append("</tr>");
		return this;
	}
	
	
	public TransactionNoteBuilder appendRow(String value1,String value2,boolean isHeader){
		if(!isHeadersGenerated && isHeader){
			isHeadersGenerated = isHeader;
		}
		this.rowBuffer.append("<tr>");
		if(isHeader){
			this.rowBuffer.append(prepareHeaderCell(value1, 200));
			this.rowBuffer.append(prepareHeaderCell(value2, 600, 3));
		}else{
			this.rowBuffer.append(prepareCell(value1, 200));
			this.rowBuffer.append(prepareCell(value2, 600, 3));
		}
		this.rowBuffer.append("</tr>");
		return this;
	}
	
	public String getTransactionNote(){
		return tableCreator(rowBuffer.toString());
	}
	
	public boolean isHeaderGenerated(){
		return this.isHeadersGenerated;
	}
	public TransactionNoteBuilder appendRow(String value1,String value2,String value3,String value4) {
		return appendRow(value1, value2, value3, value4, false);
	}
	public TransactionNoteBuilder appendRow(String value1,String value2){
		return appendRow(value1, value2, false);
	}
	
	public static void main(String[] args) {
		TransactionNoteBuilder builder = new TransactionNoteBuilder();
		builder.appendRow("Transaction Date", "Transaction Key","Bucket","Reward Points", true);
		builder.appendRow(new java.util.Date().toString(), "TxT00000000000001","Cash","100" ,false);
		System.out.println(builder.getTransactionNote());
	}
	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat(DATE_FORMAT);
	}
}
