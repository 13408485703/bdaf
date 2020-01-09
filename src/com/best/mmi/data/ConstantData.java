package com.best.mmi.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;



public class ConstantData
{
	public static String str_Version = "1.0.0.0";
		
	public static Color Color_RadarViewBackground = Color.GRAY;	
	public static Color Color_WindowBackground = new Color(175,175,175);
	
	public static Color Color_Temp = Color.WHITE;
	public static Color Color_TrackPoAck = Color.GRAY;
	public static Color Color_TrackPoMaskWarn = Color.GRAY;
	public static Color Color_TrackPoSsr = new Color(153,153,0);
	public static Color Color_TrackPoSpi = new Color(153, 255, 255);
	public static Color Color_TrackPoSpi_Light;

	public static Font font_Dialog_Plain = new Font("Serif",Font.PLAIN,11);
	public static Font font_Dialog_Bold = new Font("Serif",Font.BOLD,12);
	public static Font font_TrackPoSsr = new Font("Serif",Font.PLAIN,10);
	
	public static double d_MaxRadarScope = 1600.0;
	public static double d_MinRadarScope = 50.0;
	
	public static int n_MaxSpeed = 1500;
	public static int n_MinSpeed = 0; 
	public static double d_MaskHeading =500.0;

	public static int n_MaxNumHistDot = 600;
	
	public static int n_ClamWarnMaskHeight = 3500;
	
	public static int n_MinRangeRingNum = 5;
	public static int n_MaxRangeRingNum = 20;
	public static int n_MinRangeRingScale = 5;
	public static int n_MaxRangeRingScale = 100;
	
	public static Integer[] s_CompassTextScale = {5,10,15,20};
	public static Integer[] s_TrackRingScale = {5,10,20,30};
	
	public static int n_FlowWarnRemoveTime = 6;
	public static int n_TrackPoRemoveTime = 3*4;
	public static int n_TrackPoRemoveTime_MainUse = 2*3*4;
	public static int n_RadarChannelUpdateTime = 30;
	public static int n_QnhUpdateTime = 60; 
	
	public static String str_PrivateMapPath = "resource//privatemap//";

	public static int n_BufferSize = 13107100;
	
	public static String str_MapPath = "resource//map//";
	public static String str_MMapPath = "resource//mmap//";
	
	public static int n_WeatherGrid_Row = 40;
	public static int n_WeatherGrid_Column = 40;
	public static double d_WeatherGrid_MaxRange = 1000*1000;
	public static int n_WeatherGrid_UpdateTime = 660;
	
	public static int n_ClearViewHour = 20;
	
	public static int n_MaxLeaderLength = 400;


	
	public static void init(IniData id)
	{
		try
		{
			
			String StrBackground = id.getSysPara("Syscolors", "RadarView");
			if(StrBackground != null)
			{
				String[] StrBackgrounds = StrBackground.split(",");
				Color_RadarViewBackground = new Color(Integer.parseInt(StrBackgrounds[0]), 
					Integer.parseInt(StrBackgrounds[1]), Integer.parseInt(StrBackgrounds[2]));
			}
			Color_TrackPoSpi_Light = new Color(
				Color_TrackPoSpi.getRed(), Color_TrackPoSpi.getGreen(), 
				Color_TrackPoSpi.getBlue(), 120);
			
			String strMaxRadarScope = id.getSysPara("SysPara", "MaxRadarScope");
			if(strMaxRadarScope != null)
				d_MaxRadarScope = Double.parseDouble(strMaxRadarScope);	
			
			String strMinRadarScope = id.getSysPara("SysPara", "MinRadarScope");
			if(strMinRadarScope != null)
				d_MinRadarScope = Double.parseDouble(strMinRadarScope);	
			
			String strMaxSpeed = id.getSysPara("SysPara", "MaxSpeed");
			if(strMaxSpeed != null)
				n_MaxSpeed = Integer.parseInt(strMaxSpeed);
			
			String strMinSpeed = id.getSysPara("SysPara", "MinSpeed");
			if(strMinSpeed != null)
				n_MinSpeed = Integer.parseInt(strMinSpeed);
			
			String strMaskHeading = id.getSysPara("SysPara", "MaskHeading");
			if(strMaskHeading != null)
				d_MaskHeading = Double.parseDouble(strMaskHeading);
			
			String strMaxNumHistDot = id.getSysPara("SysPara", "MaxNumHistDot");
			if(strMaxNumHistDot != null)
				n_MaxNumHistDot = Integer.parseInt(strMaxNumHistDot);
			
			String strMinRangeRingNum = id.getSysPara("SysPara", "MinRangeRingNum");
			if(strMinRangeRingNum != null)
				n_MinRangeRingNum = Integer.parseInt(strMinRangeRingNum);
			
			String strMaxRangeRingNum = id.getSysPara("SysPara", "MaxRangeRingNum");
			if(strMaxRangeRingNum != null)
				n_MaxRangeRingNum = Integer.parseInt(strMaxRangeRingNum);
			
			String strMinRangeRingScale = id.getSysPara("SysPara", "MinRangeRingScale");
			if(strMinRangeRingScale != null)
				n_MinRangeRingScale = Integer.parseInt(strMinRangeRingScale);
			
			String strMaxRangeRingScale = id.getSysPara("SysPara", "MaxRangeRingScale");
			if(strMaxRangeRingScale != null)
				n_MaxRangeRingScale = Integer.parseInt(strMaxRangeRingScale);
			
			
			String strFlowWarnRemoveTime = id.getSysPara("SysPara", "FlowWarnRemoveTime");
			if(strFlowWarnRemoveTime != null)
				n_FlowWarnRemoveTime = Integer.parseInt(strFlowWarnRemoveTime);
			
			String strTrackPoRemoveTime = id.getSysPara("SysPara", "TrackPoRemoveTime");
			if(strTrackPoRemoveTime != null)
				n_TrackPoRemoveTime = Integer.parseInt(strTrackPoRemoveTime);
			
			String strRadarChannelUpdateTime = id.getSysPara("SysPara", "RadarChannelUpdateTime");
			if(strRadarChannelUpdateTime != null)
				n_RadarChannelUpdateTime = Integer.parseInt(strRadarChannelUpdateTime);
			
			String strQnhUpdateTime = id.getSysPara("SysPara", "QnhUpdateTime");
			if(strQnhUpdateTime != null)
				n_QnhUpdateTime = Integer.parseInt(strQnhUpdateTime);
			
			String strBufferSize = id.getSysPara("SysPara", "BufferSize");
			if(strBufferSize != null)
				n_BufferSize = Integer.parseInt(strBufferSize);
			
			String strClearViewHour = id.getSysPara("SysPara", "ClearViewHour");
			if(strClearViewHour != null)
				n_ClearViewHour = Integer.parseInt(strClearViewHour);
			
			String strWeatherGrid_Row = id.getSysPara("SysPara", "WeatherGrid_Row");
			if(strWeatherGrid_Row != null)
				n_WeatherGrid_Row = Integer.parseInt(strWeatherGrid_Row);
			
			String strWeatherGrid_Column = id.getSysPara("SysPara", "WeatherGrid_Column");
			if(strWeatherGrid_Column != null)
				n_WeatherGrid_Column = Integer.parseInt(strWeatherGrid_Column);
			
			String strWeatherGrid_MaxRange = id.getSysPara("SysPara", "WeatherGrid_MaxRange");
			if(strWeatherGrid_MaxRange != null)
				d_WeatherGrid_MaxRange = Double.parseDouble(strWeatherGrid_MaxRange)*1000;
			
			String strWeatherGrid_UpdateTime = id.getSysPara("SysPara", "WeatherGrid_UpdateTime");
			if(strWeatherGrid_UpdateTime != null)
				n_WeatherGrid_UpdateTime = Integer.parseInt(strWeatherGrid_UpdateTime);
		
			String strPrivateMapPath = id.getSysPara("SysPara", "PrivateMapPath");
			if(strPrivateMapPath != null)
				str_PrivateMapPath = strPrivateMapPath;
			
			String strMapPath = id.getSysPara("SysPara", "MapPath");
			if(strMapPath != null)
				str_MapPath = strMapPath;
			
			String strMaxLeaderLength = id.getSysPara("Init", "MaxLeaderLength");
			if(strMaxLeaderLength != null)
				n_MaxLeaderLength = Integer.parseInt(strMaxLeaderLength);
			
		}
		catch (Exception e) {
			System.out.println("Init ContantData failed!");
			e.printStackTrace();
		}	
	}
}

