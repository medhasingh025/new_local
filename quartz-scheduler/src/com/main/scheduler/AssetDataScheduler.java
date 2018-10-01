package com.main.scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.main.service.AssetServiceImpl;

public class AssetDataScheduler {

	protected Properties  prop =null;
	protected InputStream input =AssetDataScheduler.class.getClassLoader().getResourceAsStream("com/main/data/config.properties");

	public AssetDataScheduler() throws IOException
	{
		prop= new Properties();
		prop.load(input);
	}

	public static void main(String[] args) throws SchedulerException
	{
		try {
			AssetDataScheduler obj=new AssetDataScheduler();
			String cronTab=obj.getCronTab();
			
			JobDetail job = JobBuilder.newJob(AssetServiceImpl.class).build();

			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1").startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule(cronTab)).build();

			Scheduler s=StdSchedulerFactory.getDefaultScheduler();
			s.start();
			s.scheduleJob(job, trigger);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String getCronTab() {
		return  prop.getProperty("cronTab");
	}

}
