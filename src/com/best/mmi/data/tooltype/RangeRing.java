package com.best.mmi.data.tooltype;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import com.best.mmi.data.ConstantData;
import com.best.mmi.data.IniData;
import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.main.App;

public class RangeRing
{
	
	private Point m_sDistanceP;
	private Point m_sScreenP;
	private Point[] m_nScreenRect;
	private Point m_sSubScreenP;
	private Point[] m_nSubScreenRect;
	
	private int m_nRingScale;
	private int m_nRingNum;
	private boolean m_bIsShow;
	
	private static float[] style = {5,5};
	private static final Stroke DASH_STROKE = 
		new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f,style,0.0f);
	private static final Stroke REAL_STROKE = new BasicStroke();
	
	public void setDistanceXY(Point distanceP)
	{
		m_sDistanceP = distanceP;
		updateScreenPosition(0);
		updateScreenPosition(1);	
	}
	
	
	public RangeRing(Point distanceP,int ringScale,int ringNum)
	{
		resetRing(distanceP, ringScale, ringNum);
	}
	
	public RangeRing()
	{
		int centerx = 0;
		int centery = 0;
		int scale = ConstantData.n_MinRangeRingScale*1000;
		int num = ConstantData.n_MinRangeRingNum;
		
		IniData id = App.getApp().getIniData();
		String CenterX = id.getSysPara("RangeRing", "CenterX");
		if(CenterX != null)
			centerx = Integer.parseInt(CenterX);
		String CenterY = id.getSysPara("RangeRing", "CenterY");
		if(CenterY != null)
			centery = Integer.parseInt(CenterY);
		String Scale = id.getSysPara("RangeRing", "Scale");
		if(Scale != null)
			scale = Integer.parseInt(Scale)*1000;
		String Num = id.getSysPara("RangeRing", "Num");
		if(Num != null)
			num = Integer.parseInt(Num);
		String Show = id.getSysPara("RangeRing", "Show");
		if(Show != null)
			m_bIsShow = Boolean.parseBoolean(Show);
			
		resetRing(new Point(centerx, centery), scale, num);
	}
	
	public void resetRing(Point distanceP,int ringScale,int ringNum)
	{
		m_nRingScale = ringScale;
		m_nRingNum = ringNum;
		m_sDistanceP = distanceP;
		m_nScreenRect = new Point[m_nRingNum];
		for(int i=0;i<m_nScreenRect.length;i++)
		{
			m_nScreenRect[i] = new Point();
		}	
		m_nSubScreenRect = new Point[m_nRingNum];
		for(int i=0;i<m_nSubScreenRect.length;i++)
		{
			m_nSubScreenRect[i] = new Point();
		}
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	
	
	public void updateScreenPosition(int radarViewNum)
	{
		if(radarViewNum == 0)
		{
			m_sScreenP = ToolList.fromDistanceToScreen(m_sDistanceP,0);	
			for(int i=0;i<m_nScreenRect.length;i++)
			{
				double distanceRect = m_nRingScale*(i+1);
				m_nScreenRect[i].x = (int)(2*distanceRect* App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getXScale());
				m_nScreenRect[i].y = (int)(2*distanceRect* App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getYScale());	
			}
		}
		else if(radarViewNum == 1)
		{
			m_sSubScreenP = ToolList.fromDistanceToScreen(m_sDistanceP,1);	
			for(int i=0;i<m_nSubScreenRect.length;i++)
			{
				double distanceRect = m_nRingScale*(i+1);
				m_nSubScreenRect[i].x = (int)(2*distanceRect* App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getXScale());
				m_nSubScreenRect[i].y = (int)(2*distanceRect* App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getYScale());	
			}
		}	
	}
	
	
	public void draw(Graphics g,int radarViewNum)
	{
		if(!m_bIsShow)
			return;
		
		Point screenP = m_sScreenP;
		Point[] screenRect = m_nScreenRect;
		if(radarViewNum == 1)
		{
			screenP = m_sSubScreenP;
			screenRect = m_nSubScreenRect;
		}
	
		((Graphics2D)g).setStroke(DASH_STROKE); //�������ת�����������ٶȺ���
		for(int i=0;i<((screenRect.length+1)/2);i++)
		{
			g.drawOval(screenP.x-screenRect[2*i].x/2, screenP.y-screenRect[2*i].y/2, screenRect[2*i].x, screenRect[2*i].y);	
			g.drawString(""+(m_nRingScale*(2*i+1)/1000), screenP.x-screenRect[2*i].x/2, screenP.y);
			g.drawString(""+(m_nRingScale*(2*i+1)/1000), screenP.x+screenRect[2*i].x/2, screenP.y);
		}
		
		((Graphics2D)g).setStroke(REAL_STROKE);
		for(int i=0;i<(m_nScreenRect.length/2);i++)
		{
			g.drawOval(screenP.x-screenRect[2*i+1].x/2, screenP.y-screenRect[2*i+1].y/2, screenRect[2*i+1].x, screenRect[2*i+1].y);
			g.drawString(""+(m_nRingScale*(2*i+2)/1000), screenP.x-screenRect[2*i+1].x/2, screenP.y);
			g.drawString(""+(m_nRingScale*(2*i+2)/1000), screenP.x+screenRect[2*i+1].x/2, screenP.y);
		}	
	}
	
	public int getRingScale() {return m_nRingScale;}
	public int getRingNum() {return m_nRingNum;}
	public Point getDistanceP() {return m_sDistanceP;}
	public boolean getIsShow() {return m_bIsShow;}
	
	public void setIsShow(boolean b) {m_bIsShow = b;}
}


