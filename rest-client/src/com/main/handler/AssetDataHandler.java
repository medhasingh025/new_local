package com.main.handler;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.main.data.AssetData;

public class AssetDataHandler extends DefaultHandler 
{
	private AssetData assetData;
	private String currentElement;
	public List<AssetData> data;
	private String id;
	private String fleet;

	private StringBuilder currentText;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-mm");


	public List<AssetData> readDataFromXml(InputStream responseObj) throws SAXException, IOException, ParserConfigurationException
	{
		SAXParserFactory spfac = SAXParserFactory.newInstance();
		SAXParser sp = spfac.newSAXParser();
		//Register handler with parser
		sp.parse(responseObj, this);
		return data;
	}

	public void startDocument() throws SAXException
	{
		//If it encounters new data it will store it in new List
		data=new ArrayList<>();
	}

	public void endDocument() throws SAXException
	{

		System.out.println("end of the document document     : ");
	}

	/* * When the parser encounters plain text (not XML elements),
	 * it calls(this method, which accumulates them in a string buffer*/

	public void characters(char[] buffer, int start, int length) throws SAXException {
		//System.out.println("Characters");
	}


	/* * Every time the parser encounters the beginning of a new element,
	 * it calls this method, which resets the string buffer */

	public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException {
		currentElement=qName;

		switch (currentElement) {
		case "asset":	
			id=attributes.getValue("id");
			//fleet=attributes.getValue("eventcount");
			break;

		case "event":			
			assetData= new AssetData();
			String accel=attributes.getValue("accel");
			String heading=attributes.getValue("heading");
			String fuel_units=attributes.getValue("fuel_units");
			String status=attributes.getValue("status");
			String address=attributes.getValue("address");
			String zone = attributes.getValue("zone");			
			String air_temp=attributes.getValue("air_temp");
			String avg_rpm=attributes.getValue("avg_rpm");
			String avg_load=attributes.getValue("avg_load");
			String fuel_level=attributes.getValue("fuel_level");
			String outside_pressure = attributes.getValue("outside_pressure");					
			String peak_rpm=attributes.getValue("peak_rpm");
			String average_torque=attributes.getValue("average_torque");
			String boost_pressure=attributes.getValue("boost_pressure");
			String ffilter_dpressure=attributes.getValue("ffilter_dpressure");
			String afilter_dpressure = attributes.getValue("afilter_dpressure");			
			String dpf_dpressure=attributes.getValue("dpf_dpressure");
			String regen_status = attributes.getValue("regen_status");
			String lat = attributes.getValue("lat");
			String speed = attributes.getValue("speed");
			String odometer = attributes.getValue("odometer");
			String fuel_counter = attributes.getValue("fuel_counter");
			String Long = attributes.getValue("long");
			String distance_traveled = attributes.getValue("distance_traveled");
			String time=attributes.getValue("time");

			assetData.setId(Integer.parseInt(id));
			//assetData.setFleet(Integer.parseInt(fleet));
			assetData.setAccel(Integer.parseInt(accel));
			assetData.setHeading(heading);
			assetData.setFuel_units(fuel_units);
			assetData.setStatus(status);
			assetData.setAddress(address);
			assetData.setZone(zone);
			assetData.setAir_temp(Integer.parseInt(air_temp));
			assetData.setAvg_rpm(Integer.parseInt(avg_rpm));
			assetData.setAvg_load(Integer.parseInt(avg_load));
			assetData.setFuel_level(Integer.parseInt(fuel_level));
			assetData.setOutside_pressure(Integer.parseInt(outside_pressure));
			assetData.setPeak_rpm(Integer.parseInt(peak_rpm));
			assetData.setAverage_torque(Integer.parseInt(average_torque));
			assetData.setBoost_pressure(Integer.parseInt(boost_pressure));
			assetData.setFfilter_dpressure(Integer.parseInt(ffilter_dpressure));
			assetData.setAfilter_dpressure(Integer.parseInt(afilter_dpressure));
			assetData.setDpf_dpressure(Integer.parseInt(dpf_dpressure));
			assetData.setRegen_status(Integer.parseInt(regen_status));
			assetData.setLat(new BigDecimal(lat));
			assetData.setSpeed(new BigDecimal(speed));
			assetData.setOdometer(new BigDecimal(odometer));
			assetData.setFuel_counter(new BigDecimal(fuel_counter));
			assetData.setLong(new BigDecimal(Long));
			assetData.setDistance_traveled(new BigDecimal(distance_traveled));		
			try {
				assetData.setTime(simpleDateFormat.parse(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data.add(assetData);
			break;

		default:
			currentText = new StringBuilder();
			break;
		}
	}

	/*When the parser encounters the end of an element, it calls this method*/
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = "";
	}

}


