package com.main.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;

import com.main.adapter.ReadUrlAdapter;
import com.main.dao.AssetDaoImpl;
import com.main.data.AssetData;

public class AssetServiceImpl extends DefaultHandler
{
	final static Logger log= Logger.getLogger(AssetServiceImpl.class);

	protected Properties  prop =null;
	protected InputStream input =AssetServiceImpl.class.getClassLoader().getResourceAsStream("com/main/data/config.properties");
	public List<String> fleet =null;
	private AssetData assetData;
	private volatile List<AssetData> finalData=new ArrayList<AssetData> ();

	public AssetServiceImpl() throws IOException
	{
		prop= new Properties();
		prop.load(input);
	}

	public static void main(String[] args) throws IOException
	{
		AssetServiceImpl oobj_Test_HTTP_XML=new AssetServiceImpl();
		oobj_Test_HTTP_XML.getResponse();
	}

	public void getResponse()
	{
		List<AssetData> data=null;
		try {
			String name = prop.getProperty("username");
			String password =  prop.getProperty("password");
			
			log.debug("The UserName is :" +name + " and the Password is :" +password);

			Date currDate = new Date();  			

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -7);
			Date lastWeekDate = cal.getTime();    
			System.out.println("Start date is : "+lastWeekDate);
			System.out.println("End date is : "+currDate); 

			int fromEpoch = (int) ((lastWeekDate.getTime() - new Date(0).getTime()) / (1000 ));
			int toEpoch = (int) ((currDate.getTime() - new Date(0).getTime()) / (1000));
			System.out.println(new Date(0));

			String fromEpochString = Integer.toString(fromEpoch);
			String toEpochString = Integer.toString(toEpoch);

			System.out.println("from epoch : "+fromEpochString);
			System.out.println("to epoch : "+toEpochString);

			String startUrl = prop.getProperty("starturl");
			String midUrl = "starttime="+fromEpochString+"&endtime="+toEpochString;
			String endUrl = prop.getProperty("endurl");

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
			Date date = new Date(); 
			System.out.println(formatter.format(date));  

			String userpass = name + ":" + password;
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

			long startTime = System.currentTimeMillis();

			ThreadPoolExecutor executorService = new ThreadPoolExecutor(20, 20, 10000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(30));
			executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			List<Future<List<AssetData>>> futureList = new ArrayList<Future<List<AssetData>>>();
			Future<List<AssetData>> future= null;

			AssetDaoImpl dao= new AssetDaoImpl();
			fleet=dao.getFleetIds();
			int count = fleet.size();
			int count1 = (int) Math.ceil((double)count / 1000);
			int k=0;
			int maxCount=0;

			for(int j=1;j<=count1;j++){
				maxCount = j*1000;
				if(maxCount >= count){
					maxCount = count;
				}
				for(int i = k;i<=maxCount;i++) 
				{
					System.out.println("The fleet id is : " +i);
					String url = startUrl+midUrl+endUrl+"&target="+fleet.get(i);
					assetData.setFleet(fleet.get(i));
					future = executorService.submit(new ReadUrlAdapter(url, basicAuth));
					futureList.add(future);
				}	
				for(Future<List<AssetData>> fut : futureList) {
					try {
						data =  fut.get();
						addToFinalData(data);
						finalData.addAll(data);						
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}				
				futureList.clear();
				k=maxCount;
			}
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("TIME TAKEN TO FETCH DATA FROM API IS :" +totalTime + "ms");			
			executorService.shutdown();
			dao.saveAssetData(finalData);
		} 
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	public synchronized void addToFinalData(List<AssetData> data) {
		this.finalData.addAll(data);
	}
}
