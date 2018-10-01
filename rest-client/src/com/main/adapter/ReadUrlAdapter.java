package com.main.adapter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.main.data.AssetData;
import com.main.handler.AssetDataHandler;

public class ReadUrlAdapter implements Callable<List<AssetData>> {	
	String url;
	String basicAuth;

	public ReadUrlAdapter() {

	}

	public ReadUrlAdapter(String url, String basicAuth) {
		this.url = url;
		this.basicAuth = basicAuth;
	}

	public List<AssetData> call() throws Exception {

		List<AssetData> data=new ArrayList<AssetData> ();
		StringBuffer response =null;
		BufferedReader in= null;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty ("Authorization", basicAuth);
			con.setRequestMethod("GET");
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(99999999);
			con.setReadTimeout(99999999);
			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK)
			{
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				if(in!=null)
					in.close();
				if(con!=null)
					con.disconnect();
			}
			else
			{
				System.out.println("GET request not worked");
			}
			InputStream responseObj=new ByteArrayInputStream(response.toString().getBytes());
			AssetDataHandler handler = new AssetDataHandler();
			//Parse the file
			data = handler.readDataFromXml(responseObj);
			System.out.println("NUMBER OF ENTRIES IN THE PARSED XML IS:" + data.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
