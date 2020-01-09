package com.best.mmi.data.tooltype;

import java.awt.Graphics;
import java.awt.Point;

import com.best.mmi.data.ToolList;

public class Text
{
	private int m_nDistanceX;
	private int m_nDistanceY;
	private int m_nScreenX[];
	private int m_nScreenY[];
	
	private String m_strText;
	
	public Text(Point distanceP,String strText)
	{
		m_nDistanceX = distanceP.x;
		m_nDistanceY = distanceP.y;
		m_strText = strText;
		m_nScreenX = new int[2];
		m_nScreenY = new int[2];
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public int getScreenX(int radarViewNum) {return m_nScreenX[radarViewNum];}
	public int getScreenY(int radarViewNum ) {return m_nScreenY[radarViewNum];}

	public void updateScreenPosition(int radarViewNum)
	{
		Point screenP = ToolList.fromDistanceToScreen(new Point(m_nDistanceX,m_nDistanceY),radarViewNum);
		m_nScreenX[radarViewNum] = screenP.x;
		m_nScreenY[radarViewNum] = screenP.y;
	}
	
	
	public void draw(Graphics g,int radarViewNum)
	{
		g.drawOval(m_nScreenX[radarViewNum]-2, m_nScreenY[radarViewNum]-2, 4, 4);
		g.drawString(m_strText, m_nScreenX[radarViewNum], m_nScreenY[radarViewNum]);
	}
}