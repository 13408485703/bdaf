package com.best.mmi.data.airspace;

import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.maptype.Sector;


public class RestrictArea extends Airspace
{
	private int m_nAlertTime;
	
	public RestrictArea(String name,Polygon polygon,int low,int high)
	{
		super(name,polygon,low,high);
	}
	public RestrictArea(String name,Circle circle,int low,int high)
	{
		super(name,circle,low,high);
	}
	public RestrictArea(String name,Sector sector,int low,int high)
	{
		super(name,sector,low,high);
	}
	
	public void setAlertTime(int n) {m_nAlertTime = n;}
		
	public int getAlertTime() {return m_nAlertTime;}
}