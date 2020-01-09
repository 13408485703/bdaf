package com.best.bdaf.main;

import java.util.TimeZone;

import org.apache.log4j.PropertyConfigurator;

import com.best.bdaf.dao.DbService;
import com.best.bdaf.data.AppLogger;
import com.best.bdaf.data.ConstantPara;
import com.best.bdaf.data.IniData;
import com.best.bdaf.data.NavData;
import com.best.bdaf.view.MainView;

public class App
{ 
	private static final App onlyOne = new App();
	
	private DbService dbService;
	private IniData iniData;
	private NavData navData;
	
	private MainView mainView;
	
	public static App getApp()
	{
		return onlyOne;
	}
	
	private App() 
	{			
		System.setProperty("user.timezone", ConstantPara.str_time_zone);
		TimeZone.setDefault(TimeZone.getTimeZone(ConstantPara.str_time_zone)); 
		
		PropertyConfigurator.configure("resource/xml/log4j.properties");
		System.setProperty("com.mchange.v2.c3p0.cfg.xml",
				"resource/xml/c3p0-config.xml");
		
		dbService = new DbService();
		iniData = new IniData();
		navData = new NavData();

		ConstantPara.init(iniData);
			
		mainView = new MainView();
	}
	
	
	public static void main(String[] args)
	{	
		App.getApp().init(args);	
//		App.getApp().test();
		
		
		com.best.mmi.main.App.getApp().init(args);
	}
	
	public void init(String[] args)
	{
		try
		{
			navData.init();
			
			mainView.init();
			
			AppLogger.info("System init finished!");
		}
		catch(Exception e) {System.out.println("App init failed!");AppLogger.error(e);}
	}
	
	public void test()
	{
		try
		{
			
	
		}
		catch (Exception e) {
			AppLogger.error(e);
		}	
	}
	
	public DbService getDbService() {return dbService;}
	public IniData getIniData() {return iniData;}
	public NavData getNavData() {return navData;}
	
	public MainView getMainView() {return mainView;}
}

