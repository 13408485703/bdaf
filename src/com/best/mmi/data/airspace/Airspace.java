package com.best.mmi.data.airspace;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;

import com.best.mmi.data.ToolList;
import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.maptype.Sector;


public class Airspace
{
	protected String m_strName; 
	protected Polygon m_sPolygon; 
	protected Circle m_sCircle; 
	protected Sector m_sSector; 
	protected int m_nLowHeight; 
	protected int m_nHighHeight;
	protected boolean m_bIsValid;
	
	protected Point m_sTextDistancePoint; 
	protected Point[] m_sTextScreenPoints;
	
	protected boolean m_bShowText;
		
	public Airspace(String name,Polygon polygon,int low,int high)
	{
		m_strName = name;
		m_nLowHeight = low;
		m_nHighHeight = high;
		m_bIsValid = true;
		
		m_bShowText = false;
		
		setPolygon(polygon);
	}
	
	public Airspace(String name,Circle circle,int low,int high)
	{
		m_strName = name;	
		m_nLowHeight = low;
		m_nHighHeight = high;
		m_bIsValid = true;

		m_bShowText = false;
		
		setCircle(circle);
	}
	
	public Airspace(String name,Sector sector,int low,int high)
	{
		m_strName = name;	
		m_nLowHeight = low;
		m_nHighHeight = high;
		m_bIsValid = true;
		
		m_bShowText = false;
		
		setSector(sector);
	}
	
	public void setName(String str) {m_strName = str;}
	public void setValid(boolean b) {m_bIsValid = b;}
	public void setLowHeight(int n) {m_nLowHeight = n;}
	public void setHighHeight(int n) {m_nHighHeight = n;}
	public void setShowText(boolean b) {m_bShowText = b;}
	public void setPolygon(Polygon polygon) 
	{
		m_sPolygon = polygon;
		
		m_sTextDistancePoint = new Point();
		if(polygon.getSize() > 2)
		{
			int[] textDistanceXs = new int[polygon.getSize()];
			int[] textDistanceYs = new int[polygon.getSize()];
			for(int i=0;i<polygon.getSize();i++)
			{
				Point borderP = polygon.getBorderPoints().get(i);
				textDistanceXs[i] = borderP.x;
				textDistanceYs[i] = borderP.y;
			}
			Arrays.sort(textDistanceXs);
			Arrays.sort(textDistanceYs);
			m_sTextDistancePoint.x = (textDistanceXs[0]+textDistanceXs[polygon.getSize()-1])/2;
			m_sTextDistancePoint.y = (textDistanceYs[0]+textDistanceYs[polygon.getSize()-1])/2;
		}
		
		m_sTextScreenPoints = new Point[2];
		for(int i=0;i<m_sTextScreenPoints.length;i++)
			m_sTextScreenPoints[i] = new Point();
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	public void setCircle(Circle circle) 
	{
		m_sCircle = circle;
		
		m_sTextDistancePoint = new Point();
		m_sTextDistancePoint.x = circle.getDistanceX();
		m_sTextDistancePoint.y = circle.getDistanceY();
		
		m_sTextScreenPoints = new Point[2];
		for(int i=0;i<m_sTextScreenPoints.length;i++)
			m_sTextScreenPoints[i] = new Point();
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	public void setSector(Sector sector) 
	{
		m_sSector = sector;
		
		m_sTextDistancePoint = new Point();
		m_sTextDistancePoint.x = sector.getDistanceX();
		m_sTextDistancePoint.y = sector.getDistanceY();
		
		m_sTextScreenPoints = new Point[2];
		for(int i=0;i<m_sTextScreenPoints.length;i++)
			m_sTextScreenPoints[i] = new Point();
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public boolean getShowText() {return m_bShowText;}
	public boolean getValid() {return m_bIsValid;}
	public String getName()	   {return m_strName;}
	public int getLowHeight()  {return m_nLowHeight;}
	public int getHighHeight() {return m_nHighHeight;}
	public Polygon getPolygon() {return m_sPolygon;}
	public Circle getCircle() {return m_sCircle;}
	
	public void addPoint(int x,int y)
	{
		m_sPolygon.addPoint(new Point(x, y));
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public void updateScreenPosition(int radarViewNum)
	{
		if(m_sPolygon != null)
		{
			m_sPolygon.updateScreenPosition(radarViewNum);
		}
		else if(m_sCircle != null)
		{
			m_sCircle.updateScreenPosition(radarViewNum);
		}
		else if(m_sSector != null)
		{
			m_sSector.updateScreenPosition(radarViewNum);
		}
	
		m_sTextScreenPoints[radarViewNum] = ToolList.fromDistanceToScreen(m_sTextDistancePoint,radarViewNum);
	}
	
	public void draw(Graphics g,int radarViewNum)
	{	
		if(m_bIsValid == false)
			return;
		
		drawIgnoreValid(g, radarViewNum);
	}
	
	public void drawIgnoreValid(Graphics g,int radarViewNum)
	{
		if(m_sPolygon != null)
		{
			m_sPolygon.draw(g, radarViewNum);
		}
		else if(m_sCircle != null)
		{
			m_sCircle.draw(g, radarViewNum);
		}
		else if(m_sSector != null)
		{
			m_sSector.draw(g, radarViewNum);
		}
	}	
	
	public static String shapeTypeToString(int n)
	{
		switch(n)
		{
		case 1:
			return "多边形";
		case 2:
			return "圆形";
		default:
			return "未定义";
		}
	}
	
	public static int shapeTypeToInt(String str)
	{
		if(str.equals("多边形"))
			return 1;
		else if(str.equals("圆形"))
			return 2;
		else
			return -1;
	}
	
}