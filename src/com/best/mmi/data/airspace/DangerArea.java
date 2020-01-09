package com.best.mmi.data.airspace;

import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.maptype.Sector;


public class DangerArea extends Airspace
{
	private String m_strTime;
	private int m_nAlertTime;
	
	public DangerArea(String name,Polygon polygon,int low,int high)
	{
		super(name,polygon,low,high);
	}
	
	public DangerArea(String name,Circle circle,int low,int high)
	{
		super(name,circle,low,high);
	}
	
	public DangerArea(String name,Sector sector,int low,int high)
	{
		super(name,sector,low,high);
	}
	
	public void setTime(String str) {m_strTime = str;}
	public void setAlertTime(int n) {m_nAlertTime = n;}
	
	
	public String getTime() {return m_strTime;}
	public int getAlertTime() {return m_nAlertTime;}
}