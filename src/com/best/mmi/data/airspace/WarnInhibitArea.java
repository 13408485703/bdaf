package com.best.mmi.data.airspace;

import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.maptype.Sector;

public class WarnInhibitArea extends Airspace
{
	private int m_nType; 
	
	public WarnInhibitArea(String name,Polygon polygon,int low,int high,int type)
	{
		super(name,polygon,low,high);
		m_nType = type;
	}
	public WarnInhibitArea(String name,Circle circle,int low,int high,int type)
	{
		super(name,circle,low,high);
		m_nType = type;
	}
	public WarnInhibitArea(String name,Sector sector,int low,int high,int type)
	{
		super(name,sector,low,high);
		m_nType = type;
	}
	
	public int getType() {return m_nType;}
}