package com.main.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.xml.sax.helpers.DefaultHandler;
import com.main.dao.AssetDaoImpl;
import com.main.data.AssetData;
import com.main.handler.AssetDataHandler;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class AssetServiceImpl extends DefaultHandler implements Job
{
	protected Properties  prop =null;
	protected InputStream input =AssetServiceImpl.class.getClassLoader().getResourceAsStream("com/main/data/config.properties");
	public List<String> fleet =null;

	public AssetServiceImpl() throws IOException
	{
		prop= new Properties();
		prop.load(input);
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		try {
			String name = prop.getProperty("username");
			String password =  prop.getProperty("password");
			StringBuffer response =null;
			BufferedReader in= null;
			List<AssetData> data=null;
			List<AssetData> finalData=new ArrayList<AssetData> ();

			Date currDate = new Date();  
			System.out.println("-------------------------------");
			System.out.println("Current date : "+currDate); 
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			Date lastWeekDate = cal.getTime();    
			System.out.println("Start date is : "+lastWeekDate);
			System.out.println("End date is : "+currDate); 

			int fromEpoch = (int) ((lastWeekDate.getTime() - new Date(0).getTime()) / (1000 ));
			int toEpoch = (int) ((currDate.getTime() - new Date(0).getTime()) / (1000));
			System.out.println(new Date(0));

			String fromEpoch_new = Integer.toString(fromEpoch);
			String toEpoch_new = Integer.toString(toEpoch);

			System.out.println("from epoch : "+fromEpoch_new);
			System.out.println("to epoch : "+toEpoch_new);
			/*
			long currentEpochSecod = Instant.now().getEpochSecond();

			String fromEpoch_new = String.valueOf(currentEpochSecod - 1800 );
			String toEpoch_new = String.valueOf(currentEpochSecod);*/
			System.out.println("from epoch " + fromEpoch_new);
			System.out.println("to epoch "+toEpoch_new);

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
			Date date = new Date(); 
			System.out.println(formatter.format(date));  

			String startUrl = prop.getProperty("starturl");
			String midUrl = "starttime="+fromEpoch_new+"&endtime="+toEpoch_new;
			String endUrl = prop.getProperty("endurl");

			String userpass = name + ":" + password;
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

			long startTime = System.currentTimeMillis();
			String url = startUrl+midUrl+endUrl;
			System.out.println("The URL is :" +url);

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty ("Authorization", basicAuth);	
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);

			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("TIME TAKEN TO FETCH DATA FROM API IS :" +totalTime + "ms");			

			InputStream responseObj=new ByteArrayInputStream(response.toString().getBytes());	
			/**The handler instance**/
			AssetDataHandler handler = new AssetDataHandler();
			/**Parse the file**/
			long parseStartTime = System.currentTimeMillis();
			data = handler.readDataFromXml(responseObj);
			long parseEndTime   = System.currentTimeMillis();
			long totalParsingTime = endTime - startTime;
			System.out.println("TIME TAKEN TO PARSE DATA OF API IS :" +totalParsingTime + "ms");		
			//finalData.addAll(data);	
			System.out.println("NUMBER OF ENTRIES IN THE PARSED XML IS:" + data.size());
			
			AssetDaoImpl dao= new AssetDaoImpl();
			dao.saveAssetData(data);
			in.close();

		} 
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

}
