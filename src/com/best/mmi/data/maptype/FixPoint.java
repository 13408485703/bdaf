package com.best.mmi.data.maptype;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;

import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.main.App;

public class FixPoint implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int m_nDistanceX;
	private int m_nDistanceY;
	
	private int[] m_nScreenX;
	private int[] m_nScreenY;
	private boolean[] m_bInScreen;
	
	private String m_strLat;
	private String m_strLong;
	
	private String m_strName;
	private boolean m_bShowName;
	private int m_nType;
	

	public int getType() {return m_nType;}
	public String getName() {return m_strName;}
	
	public FixPoint(Point point)
	{
		m_nDistanceX = point.x;
		m_nDistanceY = point.y;
		m_strLat = "";
		m_strLong = "";	
		m_strName = "";
		m_bShowName = false;
		m_nType = 16;	
		m_nScreenX = new int[2];
		m_nScreenY = new int[2];
		m_bInScreen = new boolean[2];
		updateScreenPosition(0);
		updateScreenPosition(1);

	}
	
	
	public FixPoint()
	{
		m_nDistanceX = -1;
		m_nDistanceY = -1;
		m_strLat = "";
		m_strLong = "";	
		m_strName = "";
		m_bShowName = false;
		m_nType = 16;	
		m_nScreenX = new int[2];
		m_nScreenY = new int[2];
		m_bInScreen = new boolean[2];
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public FixPoint(String name,String longitude,String latitude,int type)
	{
		m_strName = name;
		m_bShowName = false;
		m_nType = type;
		m_strLat = latitude;
		m_strLong = longitude;
		m_nScreenX = new int[2];
		m_nScreenY = new int[2];
		m_bInScreen = new boolean[2];
			
		double longDeg = Double.parseDouble(m_strLong.substring(0, 3));
		double longMin = Double.parseDouble(m_strLong.substring(3, 5));
		double longSec = Double.parseDouble(m_strLong.substring(5, 7));	
		double longi = longDeg + longMin/60.0 + longSec/3600.0;

		double latDeg = Double.parseDouble(m_strLat.substring(0, 2));
		double latMin = Double.parseDouble(m_strLat.substring(2, 4));
		double latSec = Double.parseDouble(m_strLat.substring(4, 6));			
		double lati = latDeg + latMin/60.0 + latSec/3600.0;
		
		Point p = App.getApp().getLatLongData().LatitudeAndLongitudeToDistanceXY(new Point2D.Double(longi, lati));
		
		m_nDistanceX = p.x;
		m_nDistanceY = p.y;	
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public void setDistanceXY(int x,int y) 
	{
		m_nDistanceX = x;m_nDistanceY = y;
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	public void setName(String str) {m_strName = str;}
	public void setShowName(boolean b) {m_bShowName = b;}
	public void setType(int n) {m_nType = n;}
	
	public int getDistanceX() {return m_nDistanceX;}
	public int getDistanceY() {return m_nDistanceY;}
	public boolean getShowName() {return m_bShowName;}
	public int getScreenX(int radarViewNum) {return m_nScreenX[radarViewNum];}
	public int getScreenY(int radarViewNum) {return m_nScreenY[radarViewNum];}
	
		
	public void draw(Graphics g,int radarViewNum)
	{
		if(m_bInScreen[radarViewNum] == false)
			return;
		
		if(m_nDistanceX == -1 && m_nDistanceY == -1)
			return;
		
		drawPoint(g, m_nScreenX[radarViewNum], m_nScreenY[radarViewNum], m_nType);
			
		if(m_bShowName && (!m_strName.equals("")))
		{
			g.drawString(m_strName, m_nScreenX[radarViewNum], m_nScreenY[radarViewNum]-5);
		}

	}
	
	public static void drawPoint(Graphics g,int x,int y,int type)
	{
		switch(type)
		{
			case 0:
				//g.drawRect(m_nScreenX[radarViewNum]-3, m_nScreenY[radarViewNum]-3, 6, 6);
				break;
			case 1:
				java.awt.Polygon p = new java.awt.Polygon();
				p.addPoint(x-4, y+4);
				p.addPoint(x+4, y+4);
				p.addPoint(x, y-4);
				g.fillPolygon(p);
				break;
			case 2:
				g.drawLine(x-4, y+4, x+4, y+4);
				g.drawLine(x+4, y+4, x, y-4);
				g.drawLine(x-4, y+4, x, y-4);
				break;
			case 3:
				g.drawOval(x-4, y-4, 8, 8);
				g.drawLine(x-6, y, x+6, y);
				g.drawLine(x, y-6, x, y+6);
				break;
			case 4:
				g.drawLine(x-4, y, x, y-6);
				g.drawLine(x+4, y, x, y-6);
				break;
			case 5:
				g.drawOval(x-1, y-1, 2, 2);
				g.drawLine(x-2, y+3, x+2, y+3);
				g.drawLine(x-2, y+3, x-8, y-3);
				g.drawLine(x+2, y+3, x+8, y-3);
				g.drawLine(x-8, y-3, x+8, y-3);
				break;
			case 6:
				g.drawLine(x-2, y-2, x-2, y+2);
				g.drawLine(x+2, y-2, x+2, y+2);
				g.drawLine(x-2, y, x+2, y);
				g.drawOval(x-5, y-5, 10, 10);
				break;
			case 7:
				g.drawArc(x-4, y-4, 8, 8, 0, 180);
				g.drawLine(x-4, y, x+4, y);
				g.drawLine(x-4, y, x, y+6);
				g.drawLine(x+4, y, x, y+6);
				break;
			case 8:
				g.drawOval(x-3, y-3, 6, 6);
				break;
			case 9:
				g.drawRect(x-4, y-4, 8, 8);
				g.drawLine(x-4, y, x, y+4);
				g.drawLine(x, y+4, x+4, y);
				g.drawLine(x+4, y, x, y-4);
				g.drawLine(x, y-4, x-4, y);
				break;
			case 10:
				g.drawRect(x-3, y-3, 6, 6);
				break;
			case 11:
				g.drawOval(x-3, y-3, 6, 6);
				g.drawLine(x, y-3, x, y-6);
				g.drawLine(x, y+3, x, y+6);
				g.drawLine(x-3, y, x-6, y);
				g.drawLine(x+3, y, x+6, y);
				break;
			case 12:
				g.drawOval(x-3, y-3, 6, 6);
				g.drawOval(x-5, y-5, 10, 10);
				break;
			case 13:
				g.drawOval(x-3, y-3, 6, 6);
				g.drawOval(x-5, y-5, 10, 10);
				g.drawLine(x, y-5, x, y-7);
				g.drawLine(x, y+5, x, y+7);
				g.drawLine(x-5, y, x-7, y);
				g.drawLine(x+5, y, x+7, y);
				break;
			case 14:
				g.drawLine(x-3, y-3, x+3, y+3);
				g.drawLine(x-3, y+3, x+3, y-3);
				break;
			case 15:
				g.drawOval(x-5, y-5, 10, 10);
				g.drawLine(x-3, y, x, y+3);
				g.drawLine(x, y+3, x+3, y);
				g.drawLine(x+3, y, x, y-3);
				g.drawLine(x, y-3, x-3, y);
				break;
			case 16:
				g.drawOval(x-3, y-3, 6, 6);
				break;
				
			case 17:	
				g.fillOval(x-5, y-5, 10, 10);
				g.drawOval(x-8, y-8, 16, 16);
				break;
			case 18: 
				g.drawOval(x-5, y-5, 10, 10);
				g.drawOval(x-8, y-8, 16, 16);
				break;
			case 19: 
				g.drawOval(x-5, y-5, 10, 10);
				g.drawOval(x-8, y-8, 16, 16);
				g.drawLine(x-5, y, x, y-5);
				g.drawLine(x-3, y+3, x+3, y-3);
				g.drawLine(x+5, y, x, y+5);
				break;
			case 20: 
				g.drawOval(x-5, y-5, 10, 10);
				break;
			case 21:
				g.drawLine(x-6, y+4, x+6, y+4);
				java.awt.Polygon pD = new java.awt.Polygon();
				pD.addPoint(x-4, y+4);
				pD.addPoint(x+4, y+4);
				pD.addPoint(x, y-6);
				g.fillPolygon(pD);
				g.drawArc(x-4, y-8, 8, 8, 0, 180);
				break;
			case 22:   
				g.drawOval(x-8, y-8, 16, 16);
				g.drawLine(x-8, y, x-4, y-3);
				g.drawLine(x-4, y-3, x, y);
				g.drawLine(x, y, x+4, y-3);
				g.drawLine(x+4, y-3, x+8, y);
				
				g.drawLine(x-7, y+2, x-4, y-1);
				g.drawLine(x-4, y-1, x, y+2);
				g.drawLine(x, y+2, x+4, y-1);
				g.drawLine(x+4, y-1, x+7, y+2);
				break;
			case 23:  
				g.drawLine(x-5, y, x, y-8);
				g.drawLine(x+5, y, x, y-8);
				g.drawLine(x, y, x, y);
				break;
			case 24: 
				g.drawOval(x-5, y-5, 10, 10);
				g.drawLine(x, y, x, y);
				break;
			case 25:
				g.drawRect(x-5, y-5, 10, 10);
				g.drawLine(x-5, y, x, y-5);
				g.drawLine(x, y-5, x+5, y);
				g.drawLine(x+5, y, x, y+5);
				g.drawLine(x, y+5, x-5, y);
				g.drawLine(x, y, x, y);
				break;
			case 26:
				g.drawLine(x-8, y-8, x+8, y+8);
				g.drawLine(x, y, x+8, y);
				g.drawLine(x, y, x, y+8);
				g.drawLine(x+8, y+8, x+12, y+8);
				g.drawLine(x+8, y+8, x+8, y+12);
				break;
	
			default:
				break;
		}
	}
	
	
	public void updateScreenPosition(int radarViewNum)
	{
		Point screenP = ToolList.fromDistanceToScreen(new Point(m_nDistanceX,m_nDistanceY),radarViewNum);
		m_nScreenX[radarViewNum] = screenP.x;
		m_nScreenY[radarViewNum] = screenP.y;
		
		Dimension size = App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getScreenSize();
		if((screenP.x>0) && (screenP.x<size.width) && (screenP.y>0) && (screenP.y<size.height))
			m_bInScreen[radarViewNum] = true;
		else
			m_bInScreen[radarViewNum] = false;	
	}
}