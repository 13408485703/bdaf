package com.best.mmi.data.maptype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import com.best.mmi.data.MapList;
import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.main.App;

public class Line implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Point> m_sPoints;
	private ArrayList<Point>[] m_sScreenPoints;
	private boolean[] m_bInScreen;
	private transient ArrayList<Point2D.Double> m_sLatLonPoints;
	
	private int m_nType; 
	private int m_nWidth;
	private Stroke stroke;
	
	public ArrayList<Point> getDistancePs() {return m_sPoints;}
	public int getType() {return m_nType;}
	public int getWidth() {return m_nWidth;}
	public Stroke getStroke() {return stroke;}
	public int getPointTotle() {return m_sPoints.size();} 
	
	
	public void setTypeAndWidth(int type,int width) 
	{m_nType = type;m_nWidth = width;updateStroke();}
	
	public Line()
	{
		m_sPoints = new ArrayList<Point>();
		m_sLatLonPoints = new ArrayList<Point2D.Double>();
		m_sScreenPoints = new ArrayList[2];
		for(int i=0;i<m_sScreenPoints.length;i++)
		{
			m_sScreenPoints[i] = new ArrayList<Point>();
		}
		m_bInScreen = new boolean[2];
		m_nType = 1;
		m_nWidth = 0;
		stroke = MapList.REAL_STROKES[0];
	}
	
	public Line(Line l)
	{
		m_sPoints =  new ArrayList<Point>(l.getDistancePs());
		m_sLatLonPoints = new ArrayList<Point2D.Double>();
		m_sScreenPoints = new ArrayList[2];
		for(int i=0;i<m_sScreenPoints.length;i++)
			m_sScreenPoints[i] = new ArrayList<Point>();
		m_bInScreen = new boolean[2];
		updateScreenPosition(0);
		updateScreenPosition(1);
		m_nType = l.getType();
		m_nWidth = l.getWidth();
		stroke = l.getStroke();
	}
	
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
	
	public void clear()
	{
		m_sPoints.clear();
		m_sLatLonPoints.clear();
		for(int i=0;i<m_sScreenPoints.length;i++)
		{
			m_sScreenPoints[i].clear();
		}
	}
	
	public void addLinePoint(Point p) 
	{
		m_sPoints.add(p);
		m_sLatLonPoints.add(App.getApp().getLatLongData().XYToLatitudeAndLongitude(p));
		
		for(int i=0;i<m_sScreenPoints.length;i++)
		{
			Point screenP = ToolList.fromDistanceToScreen(p,i);
			m_sScreenPoints[i].add(screenP);
		}
	}
	
	
	public void updateScreenPosition(int radarViewNum)
	{
		Dimension size = App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getScreenSize();
		m_bInScreen[radarViewNum] = false;
		m_sScreenPoints[radarViewNum].clear();
		for(int i=0;i<m_sPoints.size();i++)
		{
			Point screenP = ToolList.fromDistanceToScreen(m_sPoints.get(i),radarViewNum);
			m_sScreenPoints[radarViewNum].add(screenP);
			
			if(m_bInScreen[radarViewNum] == false)
			{
				if((screenP.x>0) && (screenP.x<size.width) && (screenP.y>0) && (screenP.y<size.height))
					m_bInScreen[radarViewNum] = true;
			}		
		}
		
		if(m_bInScreen[radarViewNum] == false)
		{
			Rectangle rect = App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getRectangle();	
			for(int i=0;i<m_sScreenPoints[radarViewNum].size()-1;i++)
			{
				Point screenP = m_sScreenPoints[radarViewNum].get(i);
				Point screenPNext = m_sScreenPoints[radarViewNum].get(i+1);
			
				if(rect.intersectsLine(screenP.x, screenP.y, screenPNext.x, screenPNext.y) == true)
				{
					m_bInScreen[radarViewNum] = true;		
					break;
				}
			}
		}	
	}
	
	
	public void draw(Graphics g,int radarViewNum)
	{	
		if(m_bInScreen[radarViewNum] == false)
			return;
		
		if(m_nWidth >= 5)
		{
			((Graphics2D)g).setStroke(MapList.ROUTE_STROKES[radarViewNum].get(m_nWidth));	
		}
		else if((m_nType != 1) || (m_nWidth != 0))
		{
			((Graphics2D)g).setStroke(stroke);	
		}
			
		for(int i=0;i<m_sScreenPoints[radarViewNum].size()-1;i++)
		{
			if((m_sScreenPoints[radarViewNum].get(i).x == m_sScreenPoints[radarViewNum].get(i+1).x) && 
				(m_sScreenPoints[radarViewNum].get(i).y == m_sScreenPoints[radarViewNum].get(i+1).y))
				continue;
			
			g.drawLine(m_sScreenPoints[radarViewNum].get(i).x,m_sScreenPoints[radarViewNum].get(i).y,
				m_sScreenPoints[radarViewNum].get(i+1).x,m_sScreenPoints[radarViewNum].get(i+1).y);
		}
		
		if(m_nWidth == 4)
		{
			((Graphics2D)g).setStroke(MapList.DASH_STROKES[0]);	
			Color color = g.getColor();
			g.setColor(Color.YELLOW);
			
			for(int i=0;i<m_sScreenPoints[radarViewNum].size()-1;i++)
			{
				if((m_sScreenPoints[radarViewNum].get(i).x == m_sScreenPoints[radarViewNum].get(i+1).x) && 
					(m_sScreenPoints[radarViewNum].get(i).y == m_sScreenPoints[radarViewNum].get(i+1).y))
					continue;
				
				g.drawLine(m_sScreenPoints[radarViewNum].get(i).x,m_sScreenPoints[radarViewNum].get(i).y,
					m_sScreenPoints[radarViewNum].get(i+1).x,m_sScreenPoints[radarViewNum].get(i+1).y);
			}
			g.setColor(color);
		}
		
		if((m_nType != 1) || (m_nWidth != 0))
			((Graphics2D)g).setStroke(MapList.REAL_STROKES[0]);
	}
}
	
