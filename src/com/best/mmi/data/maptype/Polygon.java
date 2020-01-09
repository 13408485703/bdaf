package com.best.mmi.data.maptype;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import com.best.mmi.data.MapList;
import com.best.mmi.data.ToolList;
import com.best.mmi.main.App;


public class Polygon implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Point> m_sBorderFixPoints;
	private ArrayList<Point>[] m_sScreenBorderFixPoints;
	private int m_nType;
	private int m_nWidth;
	private Stroke stroke;
	private transient ArrayList<Point2D.Double> m_sBorderLatLonPoints; 
	
	
	public int getType() {return m_nType;}
	public int getWidth() {return m_nWidth;}
	public Stroke getStroke() {return stroke;}
	
	public void setTypeAndWidth(int type,int width) 
	{m_nType = type;m_nWidth = width;updateStroke();}
	
	public Polygon()
	{
		m_nType = 1;
		m_nWidth = 0;
		stroke = MapList.REAL_STROKES[0];
		m_sBorderFixPoints = new ArrayList<Point>();
		m_sBorderLatLonPoints = new ArrayList<Point2D.Double>(); 
		m_sScreenBorderFixPoints = new ArrayList[2];
		for(int i=0;i<m_sScreenBorderFixPoints.length;i++)
			m_sScreenBorderFixPoints[i] = new ArrayList<Point>();
	}
	
	public Polygon(Polygon p)
	{
		m_nType = p.getType();
		m_nWidth = p.getWidth();
		stroke = p.getStroke();
		m_sBorderFixPoints = new ArrayList<Point>(p.getBorderPoints());
		m_sBorderLatLonPoints = new ArrayList<Point2D.Double>(); 
		m_sScreenBorderFixPoints = new ArrayList[2];
		for(int i=0;i<m_sScreenBorderFixPoints.length;i++)
			m_sScreenBorderFixPoints[i] = new ArrayList<Point>();
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public void clear()
	{
		m_sBorderFixPoints.clear();
		m_sBorderLatLonPoints.clear();
		for(int i=0;i<m_sScreenBorderFixPoints.length;i++)
			m_sScreenBorderFixPoints[i].clear();
	}
	
	public ArrayList<Point> getBorderPoints() {return m_sBorderFixPoints;}
	public int getSize() {return m_sBorderFixPoints.size();}
	
	public void updateStroke()
	{
		if(m_nType == 1)
		{
			if(m_nWidth < 5)
				stroke = MapList.REAL_STROKES[m_nWidth];
			else
				stroke = MapList.REAL_STROKES[0];
		}	
		else
		{
			if(m_nWidth < 5)
				stroke = MapList.DASH_STROKES[m_nWidth];
			else
				stroke = MapList.DASH_STROKES[0];
		}
	}
	
	public void addPoint(Point distanceP)
	{
		m_sBorderFixPoints.add(distanceP);
		Point screenP = ToolList.fromDistanceToScreen(distanceP,0);
		m_sScreenBorderFixPoints[0].add(screenP);	
		Point subScreenP = ToolList.fromDistanceToScreen(distanceP,1);
		m_sScreenBorderFixPoints[1].add(subScreenP);	
		Point2D.Double p2 = App.getApp().getLatLongData().XYToLatitudeAndLongitude(
	        	new Point(distanceP.x,distanceP.y));
		m_sBorderLatLonPoints.add(p2);
	}
	
	public void draw(Graphics g,int radarViewNum)
	{
		if(m_sBorderFixPoints.isEmpty())
			return;
		
		if((m_nType != 1) || (m_nWidth != 0))
			((Graphics2D)g).setStroke(stroke);
		
		for(int i=0;i<m_sBorderFixPoints.size()-1;i++)
		{
			Point p1 = m_sScreenBorderFixPoints[radarViewNum].get(i);
			Point p2 = m_sScreenBorderFixPoints[radarViewNum].get(i+1);
			g.drawLine(p1.x,p1.y,p2.x,p2.y);
		}
		Point p1 = m_sScreenBorderFixPoints[radarViewNum].get(0);
		Point p2 = m_sScreenBorderFixPoints[radarViewNum].get(m_sScreenBorderFixPoints[radarViewNum].size()-1);
		g.drawLine(p1.x,p1.y,p2.x,p2.y);
		
		if((m_nType != 1) || (m_nWidth != 0))
			((Graphics2D)g).setStroke(MapList.REAL_STROKES[0]);
	}
	
	public void updateScreenPosition(int radarViewNum)
	{
		m_sScreenBorderFixPoints[radarViewNum].clear();
		for(int i=0;i<m_sBorderFixPoints.size();i++)
		{
			Point p = m_sBorderFixPoints.get(i);
			Point screenP = ToolList.fromDistanceToScreen(p,radarViewNum);
			m_sScreenBorderFixPoints[radarViewNum].add(screenP);	
		}	
	}
}