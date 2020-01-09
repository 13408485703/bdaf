package com.best.mmi.data.airspace;

import java.awt.Point;
import java.util.ArrayList;

import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.maptype.Sector;

public class StcaArea extends Airspace
{
	private int m_nCmhd; 
	private int m_nCmvd; 
	private int m_nAlertTime; 
	
	private ArrayList<Point>[] m_sScreenPoints;
	
	public StcaArea(String name,Polygon polygon)
	{		
		super(name,polygon,1,1);
		m_nCmhd = 0;
		m_nCmvd = 0;
		m_nAlertTime = 0;
	}
	public StcaArea(String name,Circle circle)
	{		
		super(name,circle,1,1);
		m_nCmhd = 0;
		m_nCmvd = 0;
		m_nAlertTime = 0;
	}
	public StcaArea(String name,Sector sector)
	{		
		super(name,sector,1,1);
		m_nCmhd = 0;
		m_nCmvd = 0;
		m_nAlertTime = 0;
	}
	
	public void setCmhd(int n) {m_nCmhd = n;}
	public void setCmvd(int n) {m_nCmvd = n;}
	public void setAlertTime(int n) {m_nAlertTime = n;}	
	
	
	public int getCmhd() {return m_nCmhd;}
	public int getCmvd() {return m_nCmvd;}
	public int getAlertTime() {return m_nAlertTime;}
}