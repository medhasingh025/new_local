package com.main.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import com.main.dao.ThirdRyeDaoImpl;

public class ThirdRyeServiceImpl {

	public ArrayList<String> getSftpEntryList(ChannelSftp sftpChannel) 
	{
		ArrayList<String> sftpList = new ArrayList<String>();
		try {
			@SuppressWarnings("unchecked")
			Vector<ChannelSftp.LsEntry> files = sftpChannel.ls("/ThirdEyeData");
			for(int i=0; i<files.size();i++){
			    if (files.get(i).getAttrs().isDir()) {
			    	LsEntry entry = files.get(i);
			    	sftpList.add(entry.getFilename());// get only directories
			    	
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sftpList;
	}

	public ArrayList<String> getSuccessStatusList(ArrayList<String> sftpList, String date)
	{
		ArrayList<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for(int i=0;i<sftpList.size();i++)
			{
				Date date1=sdf.parse(sftpList.get(i));
				Date date2 = sdf.parse(date);
				
				System.out.println("the date is"+date1);
				if (date1.compareTo(date2) > 0) 
				{
					list.add(sftpList.get(i)) ;
				}
				else if(date1.compareTo(date2) == 0)
				{
					list.add(sftpList.get(i)) ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void insertCsv(ArrayList<String> succesStatusList, String root, ChannelSftp sftpChannel) {
		ThirdRyeDaoImpl dao =new ThirdRyeDaoImpl();
		try 
		{
			for(int i=0; i<=succesStatusList.size();i++)
			{
				String url=root.concat("/").concat(succesStatusList.get(i));
				System.out.println("The path is :" +url);
				sftpChannel.cd(url);
				
				InputStream files = sftpChannel.get("*.csv");
				dao.bulkInsertCSVtoOracle(files);
				
			}
		} catch (ClassNotFoundException | SftpException | SQLException | IOException e) {
			e.printStackTrace();
		}

	}
}
