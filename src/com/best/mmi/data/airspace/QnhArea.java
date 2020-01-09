package com.best.mmi.data.airspace;

import java.awt.Graphics;
import java.awt.Graphics2D;

import com.best.mmi.data.MapList;
import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.maptype.Sector;

public class QnhArea extends Airspace
{
	private int m_nQnh;
	private int m_nPlaybackQnh;
	private int m_nTrl; 
	private int m_nTra; 
	private long m_lUpdateTime;
	
	public QnhArea(String name,Polygon polygon)
	{
		super(name,polygon,1,3600);
		m_nTrl = 3600;
		m_nTra = 3600;
		
		m_nQnh = 1013;
		m_nPlaybackQnh = 1013;
		m_lUpdateTime = System.currentTimeMillis();	
	}
	public QnhArea(String name,Circle circle)
	{
		super(name,circle,1,3600);
		m_nTrl = 3600;
		m_nTra = 3600;
		
		m_nQnh = 1013;
		m_nPlaybackQnh = 1013;
		m_lUpdateTime = System.currentTimeMillis();	
	}
	public QnhArea(String name,Sector sector)
	{
		super(name,sector,1,3600);
		m_nTrl = 3600;
		m_nTra = 3600;
		
		m_nQnh = 1013;
		m_nPlaybackQnh = 1013;
		m_lUpdateTime = System.currentTimeMillis();	
	}
	
	public void draw(Graphics g,int radarViewNum)
	{	
		((Graphics2D)g).setStroke(MapList.DASH_STROKES[0]);
		super.draw(g, radarViewNum);
		((Graphics2D)g).setStroke(MapList.REAL_STROKES[0]);
	}
	
	public void setQnh(int n) 	{m_nQnh = n;}
	public void setPlaybackQnh(int n) {m_nPlaybackQnh = n;}
	public void setTrl(int n)		{m_nTrl = n;}
	public void setTra(int n)		{m_nTra = n;}
	public void setUpdateTime(long l) {m_lUpdateTime = l;}
	

	public int getQnh()	{return m_nQnh;}
	public int getPlaybackQnh() {return m_nPlaybackQnh;}
	public int getTrl() {return m_nTrl;}
	public int getTra()	{return m_nTra;}
	public long getUpdateTime() {return m_lUpdateTime;}
}