package com.elitecore.cpe.bl.ws.data.adapter;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author yash.kapasi
 *
 */
public class TimestampAdapter extends XmlAdapter<Date, Timestamp>  {

	@Override
	public Timestamp unmarshal(Date v) throws Exception {
			return new Timestamp(v.getTime());
	}

	@Override
	public Date marshal(Timestamp v) throws Exception {
			return new Date(v.getTime());
	}

}
