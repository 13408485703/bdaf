package com.best.bdaf.data;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.best.bdaf.main.App;

public class AppLogger {
	
	private static Logger logger = Logger.getLogger("App");
	
	public static void info(String message)
	{
		logger.info(message);
		App.getApp().getMainView().log(String.format("%1$tH:%1$tM:%1$tS ", Calendar.getInstance())+message);
	}
	
	public static void error(Exception e)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		e.printStackTrace(new PrintStream(baos));  
		String str = baos.toString();  
		
		logger.error(str);
	}
	
	public static void warn(String message)
	{
		logger.warn(message);
	}
	
	public static void debug(String message)
	{
		logger.debug(message);
	}
	
	public static void fatal(String message)
	{
		logger.fatal(message);
	}

}
