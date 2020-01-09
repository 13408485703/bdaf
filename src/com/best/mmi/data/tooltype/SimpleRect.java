package com.best.mmi.data.tooltype;


import java.awt.*;


public class SimpleRect
{
	private int m_nScreenX[];
	private int m_nScreenY[];
	
	private int m_nWidth;
	private int m_nHeight;
	
	public SimpleRect()
	{
		m_nScreenX = new int[2];
		m_nScreenY = new int[2];
		
		m_nWidth = 0;
		m_nHeight = 0;
	}
	
	public void setMember(int x,int y,int width,int height,int radarViewNum)
	{
		m_nScreenX[radarViewNum] = x;
		m_nScreenY[radarViewNum] = y;
		m_nWidth = width;
		m_nHeight = height;
	}
	
	public void draw(Graphics g,int radarViewNum)
	{
		if(m_nWidth==0 || m_nHeight==0)
			return;
		if(m_nScreenX[radarViewNum] == m_nScreenY[radarViewNum])
			return;
		g.drawRect(m_nScreenX[radarViewNum],m_nScreenY[radarViewNum],m_nWidth,m_nHeight);
	}
}
