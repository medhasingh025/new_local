package com.main.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.main.service.ThirdRyeServiceImpl;

public class ThirdRyeBackingBean
{

	final static Logger log= Logger.getLogger(ThirdRyeBackingBean.class);	
	
	public static void main(String args[]) throws IOException {
		JSch jsch = new JSch();
		Session session = null;
		ArrayList<String> succesStatusList= new ArrayList<String>();
		ArrayList<String> sftpList = new ArrayList<String>();
		ThirdRyeServiceImpl service= new ThirdRyeServiceImpl();
		String root="/ThirdEyeData";
		String date="2018-05-09";
		try 
		{
			session = jsch.getSession("Deloitte_EXT", "FTP.PIKE.COM", 22);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword("7vf544yv");
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.cd(root);

			sftpList=service.getSftpEntryList(sftpChannel);	
			log.info("The data from SFTP is :" +sftpList);
			System.out.println("The data from SFTP is :" +sftpList);

			succesStatusList=service.getSuccessStatusList(sftpList,date);
			ListIterator<String> it=succesStatusList.listIterator();
			while(it.hasNext())
			{   
				System.out.println("The Sucessfull Status list data is :" +it.next());

			}  		
			
			service.insertCsv(succesStatusList,root,sftpChannel);
			
			sftpChannel.exit();
			session.disconnect();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}
}
