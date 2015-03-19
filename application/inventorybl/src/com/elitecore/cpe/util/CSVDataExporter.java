package com.elitecore.cpe.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

public class CSVDataExporter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String basePath ;
	private String fileName ;
	private transient PrintWriter writer;
	private boolean isFileExists;
	
	
	public CSVDataExporter(File file) throws Exception {
		
		try {
			
			if(file.exists()){
				boolean  isDelete = file.delete();
				if(isDelete) {
					System.out.println("File Deleted");
				}
			}
			if(file.createNewFile()){
				isFileExists=true;
			}else{
				isFileExists =false;
			}
			if(isFileExists){
				this.writer = new PrintWriter(new  BufferedWriter(new FileWriter(file)));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
	}
	
	public CSVDataExporter(String basePath , String fileName) throws Exception {
		this.basePath=basePath;
		this.fileName = fileName;
		try {
			String fullPath = provideFullFilePath();
			File file = new File(fullPath);
			if(!file.exists()){
				if(file.createNewFile()){
					isFileExists=true;
				}else{
					isFileExists =false;
				}
			}else{
				isFileExists =false;
			}
			if(isFileExists){
				this.writer = new PrintWriter(new  BufferedWriter(new FileWriter(file)));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	private String provideFullFilePath(){
		if(this.basePath.endsWith("/") || this.basePath.endsWith("\\")){
			return this.basePath+this.fileName;
		}else{
			return this.basePath+ "/"+this.fileName;
		}
	}
	
	public void append(String...strValues){
		if(this.writer!=null){
			String commaSep = generateCSVString(strValues);
			this.writer.println(commaSep);
		}
	}
	public void append(List<String> list){
		if(this.writer!=null){
			String commaSep = generateCSVString(list);
			this.writer.println(commaSep);
		}
	}
	public void close(){
		if(this.writer!=null)
		this.writer.close();
	}
	
	private String generateCSVString(String ...strings){
		StringBuilder builder = new StringBuilder();
		if(strings!=null){
			boolean isFirst= true;
			for(String str : strings){
				if(!isFirst){
					builder.append(",");
				}else{
					isFirst=false;
				}
				
				builder.append(str);
			}
		}
		return builder.toString();
	}
	private String generateCSVString(List<String> list){
		StringBuilder builder = new StringBuilder();
		if(list!=null && !list.isEmpty()) {
			//builder.append(",");
			boolean isirst= true;
			for(String abc : list) {
				if(!isirst){
					builder.append(",");
				}else{
					isirst=false;
				}
				builder.append(abc);
			}
		}
	
	return builder.toString();
		
	}
	public String getPath(){
		return provideFullFilePath();
	}
	/*public static void main(String[] args) throws Exception{
		CSVDataExporter exporter = new CSVDataExporter("/home/elitecore/", "test2.csv");
		Set headerSet=new LinkedHashSet<String>();
		headerSet.add("BatchNumber");
		headerSet.add("WareHouse");
		headerSet.add("Resource Reference");
		List list=new ArrayList<String>(headerSet);
		exporter.append(list);
	//	exporter.append("A","A","A","A","A");
		exporter.append("A","A","A","A","A");
		exporter.append("A","A","A","A","A");
		exporter.append("A","A","A","A","A"); 
		exporter.close();
	}*/
}
