package com.best.mmi.data.tooltype;

import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;

import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.data.observer.Observer;
import com.best.mmi.main.App;


public class Rbl implements Observer
{
	private Point m_sDistancePoint1;
	private Point m_sDistancePoint2;
	private Point[] m_sScreenPoint1;
	private Point[] m_sScreenPoint2;
	
	private int m_nAngle;
	private String m_strAngle;
	private String m_strDistance;
	private String m_strDistanceImMeter;
	private int m_nType;
	private int m_nRadarNum;
	private String m_strTime;
	
	private static DecimalFormat strFormat = new DecimalFormat("0.00"); 

	public Rbl()
	{
		m_nRadarNum = 99;
		m_nType = 5;
		m_sDistancePoint1 = new Point(-1,-1);
		m_sDistancePoint2 = new Point(-1,-1);
		m_sScreenPoint1 = new Point[2];
		m_sScreenPoint2 = new Point[2];
	}
	
	public void setRblBegin(Point p)
	{
		m_sDistancePoint1 = p;
		m_sDistancePoint2 = p;
	}
	
	public void setRblEnd(Point p)
	{
		m_sDistancePoint2 = p;
		updateDistanceAngle();
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public void setRadarNum(int radarNum)
	{
		m_nRadarNum = radarNum;
	}
	
	public Rbl(Point p1,Point p2,int radarNum)
	{
		m_nRadarNum = radarNum;
		m_nType = 1;
		m_sDistancePoint1 = p1;
		m_sDistancePoint2 = p2;
		m_sScreenPoint1 = new Point[2];
		m_sScreenPoint2 = new Point[2];
		updateDistanceAngle();
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public int getRadarNum() {return m_nRadarNum;}
	public int getType() {return m_nType;}
	public Point getScreenPoint1(int radarViewNum) {return m_sScreenPoint1[radarViewNum];}
	public Point getScreenPoint2(int radarViewNum) {return m_sScreenPoint2[radarViewNum];}
	
	public void updateDistanceAngle()
	{
		double distance = -1.0;		
		switch(m_nType)
		{
		case 1:
		case 5:
			m_nAngle = (int)(ToolList.computeAngle(m_sDistancePoint1, m_sDistancePoint2)+
					App.getApp().getSysInfo().getMagnetAngle_Rbl()+0.5);
			m_nAngle = (m_nAngle+360)%360;
			distance = Math.hypot(m_sDistancePoint1.x-m_sDistancePoint2.x,m_sDistancePoint1.y-m_sDistancePoint2.y)/1000;
			break;
		case 2:

			break;
		case 3:
			
			break;
		case 4:
			
			break;
		}
		m_strAngle = String.format("%03d", m_nAngle);
		m_strDistanceImMeter =strFormat.format(distance*0.53996);
		m_strDistance = strFormat.format(distance);
	}
	
	
	public void draw(Graphics g,int radarViewNum)
	{	
		if((m_sDistancePoint1.x==-1) && (m_sDistancePoint1.y==-1))
				return;
	
		String strDistance = " M "+m_strDistance;
		if(App.getApp().getSysInfo().getUnitType() == 1)
			strDistance = " F "+m_strDistanceImMeter;
		
		Point screenPoint1 = m_sScreenPoint1[radarViewNum];
		Point screenPoint2 = m_sScreenPoint2[radarViewNum];
		Point screenTrack1 = null;
		Point screenTrack2 = null;
		
		switch(m_nType)
		{
		case 1:
		case 5:
			if((m_sDistancePoint1.x==m_sDistancePoint2.x) && 
					(m_sDistancePoint1.y==m_sDistancePoint2.y)) 
				return;
			g.drawLine(screenPoint1.x, screenPoint1.y, screenPoint2.x, screenPoint2.y);
			g.drawString(m_strAngle+strDistance,(screenPoint1.x+screenPoint2.x)/2, 
					(screenPoint1.y+screenPoint2.y)/2);	
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		}	
	}
	
	public void updateScreenPosition(int radarViewNum)
	{
		switch(m_nType)
		{
		case 1:
		case 5:
			m_sScreenPoint1[radarViewNum] = ToolList.fromDistanceToScreen(m_sDistancePoint1,radarViewNum);
			m_sScreenPoint2[radarViewNum] = ToolList.fromDistanceToScreen(m_sDistancePoint2,radarViewNum);
			break;
		case 2:
			m_sScreenPoint1[radarViewNum] = ToolList.fromDistanceToScreen(m_sDistancePoint1,radarViewNum);
			break;
		case 3:
			m_sScreenPoint1[radarViewNum] = ToolList.fromDistanceToScreen(m_sDistancePoint1,radarViewNum);
			break;
		case 4:
			break;
		
		}
	}
	
	@Override
	public void update(Object obj, int part)
	{
		if(part == -1)
			App.getApp().getToolList().removeRbl(this);
		else
			updateDistanceAngle();
		
	}
}