package com.best.mmi.data.maptype;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.io.Serializable;

import com.best.mmi.data.MapList;
import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.main.App;


public class Circle implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int m_nDistanceCenterX;
	private int m_nDistanceCenterY;
	
	private int m_nDistanceRadius;
	
	private int m_nScreenX[]; 
	private int m_nScreenY[];
	private int m_nScreenRadius[];
	
	private int m_nType; 
	private int m_nWidth;
	private Stroke stroke;
	
	
	public int getType() {return m_nType;}
	public int getWidth() {return m_nWidth;}
	public Stroke getStroke() {return stroke;}
	
	public void setTypeAndWidth(int type,int width) 
	{m_nType = type;m_nWidth = width;updateStroke();}
	public void setDistanceCenterX(int x) {m_nDistanceCenterX = x;}
	public void setDistanceCenterY(int y) {m_nDistanceCenterY = y;}
	public void setDistanceRadius(int x,int y)
	{
		m_nDistanceRadius = (int)Math.abs(m_nDistanceCenterX-x);
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	public void setDistanceRadius(int radius)
	{
		m_nDistanceRadius = radius;
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public int getDistanceX() {return m_nDistanceCenterX;}
	public int getDistanceY() {return m_nDistanceCenterY;}
	
	public Circle()
	{
		m_nScreenX = new int[2]; 
		m_nScreenY = new int[2];
		m_nScreenRadius = new int[2];
		m_nDistanceCenterX = -1;
		m_nDistanceCenterY = -1;
		m_nDistanceRadius = 0;
		m_nType = 1;	
		m_nWidth = 0;
		stroke = MapList.REAL_STROKES[0];
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
	
	public int getScreenX(int radarViewNum) {return m_nScreenX[radarViewNum];}
	public int getScreenY(int radarViewNum) {return m_nScreenY[radarViewNum];}
	
	
	public void draw(Graphics g,int radarViewNum)
	{
		if(m_nDistanceRadius == 0)
			return;
		
		if((m_nType != 1) || (m_nWidth != 0))
			((Graphics2D)g).setStroke(stroke);
		
		g.drawOval(m_nScreenX[radarViewNum]-m_nScreenRadius[radarViewNum], 
				m_nScreenY[radarViewNum]-m_nScreenRadius[radarViewNum], 
				2*m_nScreenRadius[radarViewNum], 2*m_nScreenRadius[radarViewNum]);
		
		if((m_nType != 1) || (m_nWidth != 0))
			((Graphics2D)g).setStroke(MapList.REAL_STROKES[0]);
	}
	
	public void updateScreenPosition(int radarViewNum)
	{
		Point screenP = ToolList.fromDistanceToScreen(new Point(m_nDistanceCenterX,m_nDistanceCenterY),radarViewNum);
		m_nScreenX[radarViewNum] = screenP.x;
		m_nScreenY[radarViewNum] = screenP.y;
		
		m_nScreenRadius[radarViewNum] = (int)(App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getXScale()*((double)m_nDistanceRadius));
	}
}