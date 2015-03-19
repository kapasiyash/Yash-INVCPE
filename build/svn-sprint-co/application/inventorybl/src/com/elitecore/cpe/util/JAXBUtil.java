package com.elitecore.cpe.util;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtil  {

	public static Object unmarshal(InputStream in, String className)throws Exception{
			Class clazz= Class.forName(className);
			return JAXBUtil.unmarshal(in, clazz);
		}
	
	public static void marshal(OutputStream os ,Object instance) throws Exception{
		try {
			JAXBContext jaxbContext =  JAXBContext.newInstance(instance.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(instance, os);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}

	public static Object unmarshal(InputStream in,Class clazz) throws Exception {
		Object returnObj = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			if(context!=null && in!=null){

				Unmarshaller jaxbUnmarshaller  = context.createUnmarshaller();
				returnObj =  jaxbUnmarshaller.unmarshal(in);
				in.close();
			}
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return returnObj;

	}
}
