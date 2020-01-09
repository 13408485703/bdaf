package com.best.mmi.data;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import com.best.mmi.data.MsgCenter.GlobalMsg;
import com.best.mmi.main.App;

public class SysInfo
{
	public enum FuncStat {NONE,RBL,MOUSE_WALK,RANGE_RING_PED,};
	
	private HashMap<String,Color> m_sSysColors;
	private HashMap<String,Font> m_sSysFonts;
	
	private Dimension m_sScreenSize;
	
	private double m_dCenterLongitude;
	private double m_dCenterLatitude;
	private double m_dMagnetAngle; 
	private double m_dMagnetAngle_Rbl;
	private String m_strLocalAirport;
	private int m_nLocalHeight;
	
	private double[] m_dHomeRadarScopes;
	private int[] m_nHomeOffCenterXs;
	private int[] m_nHomeOffCenterYs;
	
	private int m_nUnitType;
	private boolean m_bMouseWalk;
	private FuncStat m_nFuncStat;  	
	private boolean m_bShowCenter;
		
	private RadarViewInfo m_pRadarViewInfo[];
	
	public SysInfo()
	{
		m_dCenterLongitude = -1;
		m_dCenterLatitude = -1;
		m_dMagnetAngle = 0;
		m_dMagnetAngle_Rbl = 0;
		
		m_nUnitType = 0;
		
		m_nFuncStat = FuncStat.NONE;
	
		m_dHomeRadarScopes = new double[3];
		for(int i=0;i<m_dHomeRadarScopes.length;i++)
			m_dHomeRadarScopes[i] = 400.0;
		m_nHomeOffCenterXs = new int[m_dHomeRadarScopes.length];
		m_nHomeOffCenterYs = new int[m_dHomeRadarScopes.length];
		
		m_pRadarViewInfo = new RadarViewInfo[2];
		
		m_bShowCenter = false;
		
		m_bMouseWalk = false;
	}
	
	public RadarViewInfo getRadarViewInfo(int radarViewNum)
	{
		return m_pRadarViewInfo[radarViewNum];
	}
	
	
	public void setSysColor(String ColorName,Color c)
	{
		m_sSysColors.put(ColorName, c);
	}
	public void setSysColors(HashMap<String,Color> colors) {m_sSysColors = colors;}
	public void setSysFonts(HashMap<String,Font> fonts) {m_sSysFonts = fonts;}
	public void setUnitType(int n) {m_nUnitType = n;}
	public void setMouseWalk(boolean b) {m_bMouseWalk = b;}
	
	public void addSysColor(String str,Color c) {m_sSysColors.put(str, c);}
	public void setCenterLongitude(double longitude) {m_dCenterLongitude = longitude;}
	public void setCenterLatitude(double latitude) {m_dCenterLatitude = latitude;}
	public void setHomeRadarScope(double d,int index) {m_dHomeRadarScopes[index] = d;}
	public void setHomeOffCenterX(int n,int index) {m_nHomeOffCenterXs[index] = n;}
	public void setHomeOffCenterY(int n,int index) {m_nHomeOffCenterYs[index] = n;}
	public void setShowCenter(boolean b) {m_bShowCenter = b;}
	public void setFuncStat(FuncStat n) 
	{
		m_nFuncStat = n;
		if(m_nFuncStat == FuncStat.NONE)
		{
			App.getApp().getRadarView(0).setCursor(
					Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		else if(m_nFuncStat == FuncStat.MOUSE_WALK)
		{
			App.getApp().getRadarView(0).setCursor(
					Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		else
		{
			App.getApp().getRadarView(0).setCursor(
					Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
	
	public Dimension getScreenSize() {return m_sScreenSize;}
	public double getCenterLongitude() {return m_dCenterLongitude;}
	public double getCenterLatitude() {return m_dCenterLatitude;}
	public String getLocalAirport() {return m_strLocalAirport;}
	public double getMagnetAngle() {return m_dMagnetAngle;}
	public double getMagnetAngle_Rbl() {return m_dMagnetAngle_Rbl;}
	public int getUnitType() {return m_nUnitType;}
	public HashMap<String,Color> getSysColors() {return m_sSysColors;}
	public HashMap<String,Font> getSysFonts() {return m_sSysFonts;}
	public Color getSysColor(String str) {return m_sSysColors.get(str);}
	public Font getSysFont(String str) {return m_sSysFonts.get(str);}
	public double getHomeRadarScope(int index) {return m_dHomeRadarScopes[index];}
	public int getHomeOffCenterX(int index) {return m_nHomeOffCenterXs[index];}
	public int getHomeOffCenterY(int index) {return m_nHomeOffCenterYs[index];}
	public int getHomeLength() {return m_dHomeRadarScopes.length;}
	public boolean getShowCenter() {return m_bShowCenter;}
	public FuncStat getFuncStat() {return m_nFuncStat;}
	public boolean getMouseWalk() {return m_bMouseWalk;}
	public int getLocalHeight() {return m_nLocalHeight;}
	
	
	public void init(IniData iniData) 
	{
		try
		{
			m_sScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
					
			m_dCenterLatitude = Double.parseDouble(iniData.getSysPara("Center", "Latitude"));		
			m_dCenterLongitude = Double.parseDouble(iniData.getSysPara("Center", "Longitude"));
		
			m_strLocalAirport = iniData.getSysPara("Seat", "LocalAirport");
			m_dMagnetAngle = Double.parseDouble(iniData.getSysPara("Init", "MagnetAngle"));
			m_dMagnetAngle_Rbl = Double.parseDouble(iniData.getSysPara("Init", "MagnetAngle_Rbl"));
			
			HashMap<String,String> colors1 = iniData.getFieldParas("Syscolors");
			HashMap<String,Color> colors = new HashMap<String,Color>();
			ArrayList<String> colorNames = new ArrayList<String>(colors1.keySet());
			for(int i=0;i<colorNames.size();i++)
			{
				String colorName = colorNames.get(i);
				String colorStr = colors1.get(colorName);
				String[] colorStrs = colorStr.split(",");
				Color color = new Color(Integer.parseInt(colorStrs[0]),
						Integer.parseInt(colorStrs[1]),Integer.parseInt(colorStrs[2]));
				colors.put(colorName, color);
			}
			setSysColors(colors);
			
			HashMap<String,String> fonts1 = iniData.getFieldParas("Sysfonts");
			HashMap<String,Font> fonts = new HashMap<String,Font>();
			ArrayList<String> fontNames = new ArrayList<String>(fonts1.keySet());
			for(int i=0;i<fontNames.size();i++)
			{
				String fontName = fontNames.get(i);
				String fontStr = fonts1.get(fontName);
				String[] fontStrs = fontStr.split(",");
				Font font = new Font(fontStrs[0],
						Integer.parseInt(fontStrs[1]),Integer.parseInt(fontStrs[2]));
				fonts.put(fontName, font);
			}
			setSysFonts(fonts);
			
			//Home
			for(int i=0;i<m_dHomeRadarScopes.length;i++)
			{
				String field = "DisplayPara"+(i+1);
				String strScope = iniData.getSysPara(field, "Scope");
				if(strScope != null)
					m_dHomeRadarScopes[i] = (double)Integer.parseInt(strScope);
				String strOffX = iniData.getSysPara(field, "OffCenterX");
				if(strOffX != null)
					m_nHomeOffCenterXs[i] = Integer.parseInt(strOffX);
				String strOffY = iniData.getSysPara(field, "OffCenterY");
				if(strOffY != null)
					m_nHomeOffCenterYs[i] = Integer.parseInt(strOffY);	
			}	
			
			m_pRadarViewInfo[1] = new RadarViewInfo(new Dimension(640,480),
				m_dHomeRadarScopes[0],m_nHomeOffCenterXs[0],m_nHomeOffCenterYs[0],1);
			m_pRadarViewInfo[0] = new RadarViewInfo(new Dimension(
				Integer.parseInt(iniData.getSysPara("Init", "WindowWidth")),
				Integer.parseInt(iniData.getSysPara("Init", "WindowHeight"))),
				m_dHomeRadarScopes[0],m_nHomeOffCenterXs[0],m_nHomeOffCenterYs[0],0);
		
			String localHeight = iniData.getSysPara("Seat", "LocalHeight");
			if(localHeight != null)
				m_nLocalHeight = Integer.parseInt(localHeight);
			
			String unit = iniData.getSysPara("Init", "Unit");
			if(unit != null)
			{
				if(unit.equals("M"))
					m_nUnitType = 0;
				else
					m_nUnitType = 1;		
			}
		}
		catch (Exception e) {
			System.out.println("SysInfo init failed!");
			e.printStackTrace();
		}
	}
}