package com.best.mmi.data.datatype;

import java.awt.Color;
import java.awt.Point;

import com.best.mmi.data.ConstantData;
import com.best.mmi.data.ToolList;

public class RouteFix
{
	private String m_strFixName;
	private Point m_sDistanceP;
	private Point m_sScreenP[];
	
	public RouteFix(String FixName,Point distanceP)
	{
		m_strFixName = FixName;
		m_sDistanceP = distanceP;
		m_sScreenP = new Point[2];
		for(int i=0;i<m_sScreenP.length;i++)
			m_sScreenP[i] = new Point();
		updateScreenPosition(0);
	}
	
	public String getFixName()  {return m_strFixName;}
	public Point getDistanceP()	{return m_sDistanceP;}
	public Point getScreenP(int n)	{return m_sScreenP[n];}
	
	
	public void updateScreenPosition(int radarViewNum)
	{
		m_sScreenP[radarViewNum] = ToolList.fromDistanceToScreen(m_sDistanceP,radarViewNum);
	}
}