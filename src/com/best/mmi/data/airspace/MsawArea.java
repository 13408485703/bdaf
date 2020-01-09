package com.best.mmi.data.airspace;

import java.awt.Graphics;

import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.maptype.Sector;

public class MsawArea extends Airspace
{
	private int m_nAlertTime;
	
	public MsawArea(String name,Polygon polygon,int height)
	{		
		super(name,polygon,1,height);
	}
	public MsawArea(String name,Circle circle,int height)
	{		
		super(name,circle,1,height);
	}
	public MsawArea(String name,Sector sector,int height)
	{		
		super(name,sector,1,height);
	}
	
	public void draw(Graphics g,int radarViewNum)
	{
		super.draw(g, radarViewNum);
		
		if(m_bShowText == true)
			g.drawString(""+m_nHighHeight, m_sTextScreenPoints[radarViewNum].x, m_sTextScreenPoints[radarViewNum].y);
	}
	
	public void setAlertTime(int n) {m_nAlertTime = n;}
	
	
	public int getAlertTime() {return m_nAlertTime;}
}