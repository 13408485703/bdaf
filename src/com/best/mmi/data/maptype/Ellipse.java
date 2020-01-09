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

public class Ellipse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int m_nDistanceX; 
	private int m_nDistanceY;
	
	private int m_nDistanceWidth; 
	private int m_nDistanceHeight;
	
	private int m_nBeginAngle; 
	private int m_nArcAngle; 
	
	private int[] m_nScreenX;
	private int[] m_nScreenY;
	
	private int[] m_nScreenWidth;
	private int[] m_nScreenHeight;
	
	private int m_nType; 
	private int m_nWidth;
	private Stroke stroke;
	
	
	public Ellipse()
	{
		m_nDistanceX = -1;
		m_nDistanceY = -1;
		m_nDistanceWidth = -1;
		m_nDistanceHeight = -1;
		m_nBeginAngle = -361;
		m_nArcAngle = -361;
		m_nScreenX = new int[2];
		m_nScreenY = new int[2];
		m_nScreenWidth = new int[2];
		m_nScreenHeight = new int[2];
		m_nType = 1;
		m_nWidth = 0;
		stroke = MapList.REAL_STROKES[0];
	}
	
	public Ellipse(Ellipse ep)
	{
		m_nDistanceX = ep.getDistanceX();
		m_nDistanceY = ep.getDistanceY();
		m_nDistanceWidth = ep.getDistanceWidth();
		m_nDistanceHeight = ep.getDistanceHeight();	
		m_nBeginAngle = ep.getBeginAngle();
		m_nArcAngle = ep.getArcAngle();
		m_nScreenX = new int[2];
		m_nScreenY = new int[2];
		m_nScreenWidth = new int[2];
		m_nScreenHeight = new int[2];
		m_nType = ep.getType();
		m_nWidth = ep.getWidth();
		stroke = ep.getStroke();
		updateScreenPosition(0);
		updateScreenPosition(1);	
	}
	
	public int getDistanceX() {return m_nDistanceX;}
	public int getDistanceY() {return m_nDistanceY;}
	public int getDistanceWidth() {return m_nDistanceWidth;}
	public int getDistanceHeight() {return m_nDistanceHeight;}
	public int getBeginAngle() {return m_nBeginAngle;}
	public int getArcAngle() {return m_nArcAngle;}
	public int getType() {return m_nType;}
	public int getWidth() {return m_nWidth;}
	public Stroke getStroke() {return stroke;}
	
	
	public void setTypeAndWidth(int type,int width) 
	{m_nType = type;m_nWidth = width;updateStroke();}
	
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
	
	public void setDistanceXY(int x,int y)
	{
		m_nDistanceX = x;
		m_nDistanceY = y;
		if((m_nDistanceX==-1) && (m_nDistanceY==-1))
			return;
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public void setDistanceWH(int w,int h)
	{
		m_nDistanceWidth = w;
		m_nDistanceHeight = h;
		if((m_nDistanceWidth==-1) && (m_nDistanceHeight==-1))
			return;
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public void setBeginAngle(int degree)
	{
		m_nBeginAngle = degree;
	}
	
	public void setEndAngle(int degree)
	{
		m_nArcAngle = (degree - m_nBeginAngle + 720)%360;
	}
	
	
	public void updateScreenPosition(int radarViewNum)
	{
		Point screenP = ToolList.fromDistanceToScreen(
			new Point(m_nDistanceX,m_nDistanceY), radarViewNum);
		m_nScreenX[radarViewNum] = screenP.x;
		m_nScreenY[radarViewNum] = screenP.y;
		
		double scale = App.getApp().getSysInfo().getRadarViewInfo(
				radarViewNum).getXScale(); 
		m_nScreenWidth[radarViewNum] = (int)(scale*((double)m_nDistanceWidth));
		m_nScreenHeight[radarViewNum] = (int)(scale*((double)m_nDistanceHeight));
	}
	
	public void draw(Graphics g,int radarViewNum)
	{
		if((m_nDistanceX==-1) && (m_nDistanceY==-1))
			return;
		
		if((m_nDistanceWidth==-1) && (m_nDistanceHeight==-1))
			return;
		
		if((m_nType != 1) || (m_nWidth != 0))
			((Graphics2D)g).setStroke(stroke);
		
		if((m_nBeginAngle==-361) || (m_nArcAngle==-361)) 
			g.drawOval(m_nScreenX[radarViewNum], m_nScreenY[radarViewNum], 
				m_nScreenWidth[radarViewNum], m_nScreenHeight[radarViewNum]);
		else
			g.drawArc(m_nScreenX[radarViewNum], m_nScreenY[radarViewNum],
				m_nScreenWidth[radarViewNum], m_nScreenHeight[radarViewNum],
				m_nBeginAngle, m_nArcAngle);
		
		if((m_nType != 1) || (m_nWidth != 0))
			((Graphics2D)g).setStroke(MapList.REAL_STROKES[0]);
	}
	
}