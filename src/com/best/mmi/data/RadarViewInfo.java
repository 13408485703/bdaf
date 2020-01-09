package com.best.mmi.data;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.best.mmi.data.observer.Observer;
import com.best.mmi.data.observer.Subject;

public class RadarViewInfo implements Subject
{
	private ArrayList<Observer> m_sRadarViewInfoObservers;
	
	private double m_dRadarScope; //km
	private int m_nRadarViewNum;
	
	private Dimension m_sSize;
	private Rectangle m_sRectangle;
	
	private double m_dXScale; 
	private double m_dYScale;
	private int m_nOffCenterX; 
	private int m_nOffCenterY;
	
	
	public RadarViewInfo(Dimension screenSize,double radarScope,
		int offCenterX,int offCenterY,int radarViewNum)
	{
		m_sRadarViewInfoObservers = new ArrayList<Observer>();
		m_nRadarViewNum = radarViewNum;
		
		setScreenSize(screenSize);
		
		setRadarScope(radarScope); 
		m_nOffCenterX=offCenterX;
		m_nOffCenterY=offCenterY;
	}

	public void setScreenSize(Dimension ds)
	{
		m_sSize = ds;
		m_dXScale = m_sSize.width/(2*m_dRadarScope*1000);
		m_dYScale = m_sSize.width/(2*m_dRadarScope*1000);
		m_sRectangle = new Rectangle(m_sSize);
	}
	
	public void setRadarScope(double d) 
	{
		m_dRadarScope = d;
		m_dXScale = m_sSize.width/(2*m_dRadarScope*1000);
		m_dYScale = m_sSize.width/(2*m_dRadarScope*1000);
	}
	
	public void stepOffScreenX(int step) 
	{m_nOffCenterX += (int)(((double)step)/m_dXScale);}
	public void stepOffScreenY(int step) 
	{m_nOffCenterY += (int)(((double)step)/m_dYScale);}	
	public void setOffCenterX(int x) {m_nOffCenterX = x;}
	public void setOffCenterY(int y) {m_nOffCenterY = y;}
	
	public double getRadarScope() {return m_dRadarScope;}
	public Dimension getScreenSize() {return m_sSize;}
	public Rectangle getRectangle() {return m_sRectangle;}
	public double getXScale() {return m_dXScale;}
	public double getYScale() {return m_dYScale;}
	public int getOffCenterX() 	{return m_nOffCenterX;}
	public int getOffCenterY() 	{return m_nOffCenterY;}
	public int getOffScreenX() 
	{
		return (int)(((double)m_nOffCenterX)*m_dXScale);
	}
	public int getOffScreenY() 
	{
		return (int)(((double)m_nOffCenterY)*m_dXScale);
	}
	public int getRadarViewNum() {return m_nRadarViewNum;}
	
	
	
	public void registerObserver(Observer o)
	{
		m_sRadarViewInfoObservers.add(o);
	}
	
	public void removeObserver(Observer o)
	{
		int i = m_sRadarViewInfoObservers.indexOf(o);
		if(i>=0)
			m_sRadarViewInfoObservers.remove(i);
	}
	

	@Override
	public void notifyObservers(int part)
	{
		for(int i=0;i<m_sRadarViewInfoObservers.size();i++)
		{
			Observer observer = m_sRadarViewInfoObservers.get(i);
			observer.update(this, 0);		
		}
	}

}