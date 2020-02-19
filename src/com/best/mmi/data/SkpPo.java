package com.best.mmi.data;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import com.best.bdaf.dao.Skp;
import com.best.bdaf.data.AppLogger;
import com.best.mmi.data.datatype.RouteFix;
import com.best.mmi.main.App;

public class SkpPo {

	private ArrayList<RouteFix> m_sRFs;
	
	private Skp m_sSkp;
	private int m_nAatFixIndex = 0;
	
	private int m_nLabelWidth; 
	private int m_nLabelHeight;
	
	private int m_nLabelX[];
	private int m_nLabelY[];
	
	private int m_nScreenX[];
	private int m_nScreenY[];
	
	private int m_nLabelOffsetX[]; 
	private int m_nLabelOffsetY[];
	
	private int m_nLine1X[];
	private int m_nLine1Y[];
	private int m_nLine2X[];
	private int m_nLine2Y[];
	private int m_nLine3X[];
	private int m_nLine3Y[];
	
	private int m_nLabelLeaderEndX[];
	private int m_nLabelLeaderEndY[];

	private Color m_sColor;
	private boolean m_bShow;
	private boolean m_bOnlyShowCurrentPos;
	
	
	public Point currentPos;  //当前位置
	public int nextRouteNum; //下一个航路点，向前播放时正序；向后播放时倒序
	public double currentAngle; //指向下一个航路点的方向角，弧度，正北
	public int endAat = 0; //若走到最后一个航路点，记下时间
	public int realNextRouteNum = -99; //真实的下一个航路点，无论向前向后播放，总正序；-99:all no passed 99:all passed   
	public double realCurrentAngle = -99; //真实的指向正序航路点的方向角；-99:no angle  
	public boolean forceShowRoute = false; //强制显示航路
	
	public SkpPo(Skp skp)
	{
		m_sSkp = skp;

		m_sRFs = new ArrayList<RouteFix>(60);
		HashMap<String, String> offl_fixnames = com.best.bdaf.main.App.getApp().getNavData().getFixnames();
		String patternlatlon = "[0-9]{4}N[0-9]{5}E";
		String route = skp.getRoute();
		String[] fixnames = route.split(" ");
		m_nAatFixIndex = 0;
		for(int i=0;i<fixnames.length;i++)
		{
			String fixname = fixnames[i];
			if(fixname.endsWith("/B"))
			{
				fixname = fixname.substring(0,fixname.length()-2);
				m_nAatFixIndex = i;
			}
			else if(fixname.endsWith("/E"))
			{
				fixname = fixname.substring(0,fixname.length()-2);
			}
			
			if(fixname.matches(patternlatlon))
			{
				Point p = ToolList.convertLatLongStringToDistanceP(fixname);
				RouteFix rf = new RouteFix(fixname, p);
				m_sRFs.add(rf);
			}
			else 
			{
				String offl_pos = offl_fixnames.get(fixname);
				if(offl_pos != null)	
				{
					Point p = ToolList.convertLatLongStringToDistanceP(offl_pos);
					RouteFix rf = new RouteFix(fixname, p);
					m_sRFs.add(rf);
				}
				else
				{
					AppLogger.info(skp.getCallsign()+":Skp routefix is undefined:"+fixname);
				}
			}	
		}
		
		m_nScreenX = new int[2];
		m_nScreenY = new int[2];
		m_nLabelX = new int[2];
		m_nLabelY = new int[2];
		m_nLabelOffsetX = new int[2];
		m_nLabelOffsetY = new int[2];
		m_nLine1X = new int[2];
		m_nLine1Y = new int[2];
		m_nLine2X = new int[2];
		m_nLine2Y = new int[2];
		m_nLine3X = new int[2];
		m_nLine3Y = new int[2];
		m_nLabelLeaderEndX = new int[2];
		m_nLabelLeaderEndY = new int[2];
		
		m_nLabelOffsetX[0] = 20;
		m_nLabelOffsetY[0] = -50;
		m_nLabelOffsetX[1] = m_nLabelOffsetX[0];
		m_nLabelOffsetY[1] = m_nLabelOffsetY[0];	
		
		currentPos = m_sRFs.get(m_nAatFixIndex).getDistanceP();
		nextRouteNum = m_nAatFixIndex+1;
		realNextRouteNum = m_nAatFixIndex+1;
		if(skp.getAat() > 0)
			realNextRouteNum = -99;
		
		updateScreenPosition(0);
	}
	
	public void draw(Graphics g,int radarViewNum)
	{
		if(m_bShow == false)
			return;
		
		drawLabel(g, radarViewNum);
		drawRouteFix(g, radarViewNum);
	}
	
	public void drawLabel(Graphics g,int radarViewNum)
	{
		if(m_bOnlyShowCurrentPos==true && (realNextRouteNum==-99 || realNextRouteNum==99))
			return;
			
		if(realNextRouteNum == -99)
			g.setColor(Color.BLUE);
		else if(realNextRouteNum == 99)
			g.setColor(Color.WHITE);
		else
			g.setColor(Color.GREEN);
			
//		g.drawRect(m_nLabelX[radarViewNum],m_nLabelY[radarViewNum],m_nLabelWidth,m_nLabelHeight);
		if((m_nLabelLeaderEndX[radarViewNum]!=-1) && (m_nLabelLeaderEndY[radarViewNum]!=-1))
			g.drawLine(m_nScreenX[radarViewNum], m_nScreenY[radarViewNum], 
				m_nLabelLeaderEndX[radarViewNum], m_nLabelLeaderEndY[radarViewNum]);
		g.drawString(m_sSkp.getCallsign(), 
			m_nLine1X[radarViewNum], m_nLine1Y[radarViewNum]);
		g.drawString(m_sSkp.getFlToString()+" "+m_sSkp.getTasToString(), 
			m_nLine2X[radarViewNum], m_nLine2Y[radarViewNum]);
		g.drawString(m_sSkp.getDest(), m_nLine3X[radarViewNum], m_nLine3Y[radarViewNum]);
		
		g.drawOval(m_nScreenX[radarViewNum]-3, m_nScreenY[radarViewNum]-3, 6, 6);
		
		if(realCurrentAngle!=-99)
		{
			double speedAngle = Math.PI/2 - realCurrentAngle;
			double smallDistance = 10;
			int ptlx = (int)(smallDistance*Math.cos(speedAngle)) + m_nScreenX[radarViewNum];
			int ptly = -(int)(smallDistance*Math.sin(speedAngle)) + m_nScreenY[radarViewNum];
			g.drawLine(m_nScreenX[radarViewNum], m_nScreenY[radarViewNum], ptlx, ptly);
		}	
	}
	
	public void drawRouteFix(Graphics g,int radarViewNum)
	{
		if(forceShowRoute==false &&m_bOnlyShowCurrentPos==true)
			return;
		
		for(int i=0;i<m_sRFs.size();i++)
		{
			if(i>=realNextRouteNum)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.WHITE);
			
			RouteFix rfNow = m_sRFs.get(i);
			g.drawOval(rfNow.getScreenP(radarViewNum).x-3, rfNow.getScreenP(radarViewNum).y-3, 6, 6);	
			g.drawString(rfNow.getFixName(), rfNow.getScreenP(radarViewNum).x+5, rfNow.getScreenP(radarViewNum).y+5);
			
			if(i<(m_sRFs.size()-1))
			{
				RouteFix rfNext = m_sRFs.get(i+1);
				
				if(i==(realNextRouteNum-1))
				{
					g.setColor(Color.WHITE);
					g.drawLine(rfNow.getScreenP(radarViewNum).x, rfNow.getScreenP(radarViewNum).y, 
						m_nScreenX[radarViewNum], m_nScreenY[radarViewNum]);
					g.setColor(Color.BLUE);
					g.drawLine(m_nScreenX[radarViewNum], m_nScreenY[radarViewNum], 
						rfNext.getScreenP(radarViewNum).x, rfNext.getScreenP(radarViewNum).y);
				}	
				else	
					g.drawLine(rfNow.getScreenP(radarViewNum).x, rfNow.getScreenP(radarViewNum).y, 
						rfNext.getScreenP(radarViewNum).x, rfNext.getScreenP(radarViewNum).y);
			}	
		}
	}
	
	public void updateScreenPosition(int radarViewNum)
	{	
		for(int i=0;i<m_sRFs.size();i++)
		{
			m_sRFs.get(i).updateScreenPosition(radarViewNum);
		}
		Point screenP = ToolList.fromDistanceToScreen(currentPos,radarViewNum);
		m_nScreenX[radarViewNum] = screenP.x;
		m_nScreenY[radarViewNum] = screenP.y;
		m_nLabelX[radarViewNum] = screenP.x + m_nLabelOffsetX[radarViewNum];
		m_nLabelY[radarViewNum] = screenP.y + m_nLabelOffsetY[radarViewNum];

		FontMetrics fm = App.getApp().getRadarView(0).getRadarCanvas().getGraphics().getFontMetrics(
				App.getApp().getSysInfo().getSysFont("Track")); 
		int chHeight = fm.getHeight();
		int chWidth = fm.stringWidth("A");
		
		m_nLabelWidth = chWidth*9;
		m_nLabelHeight = chHeight*3+3;
		m_nLine1X[radarViewNum] = m_nLabelX[radarViewNum]+3;	
		m_nLine1Y[radarViewNum] = m_nLabelY[radarViewNum]+chHeight;
		m_nLine2X[radarViewNum] = m_nLabelX[radarViewNum]+3;
		m_nLine2Y[radarViewNum] = m_nLabelY[radarViewNum]+chHeight*2;
		m_nLine3X[radarViewNum] = m_nLabelX[radarViewNum]+3;
		m_nLine3Y[radarViewNum] = m_nLabelY[radarViewNum]+chHeight*3;
		
		if(m_nScreenX[radarViewNum]>m_nLabelX[radarViewNum] 
		   && m_nScreenX[radarViewNum]<(m_nLabelX[radarViewNum]+m_nLabelWidth) 
		   && m_nScreenY[radarViewNum]>m_nLabelY[radarViewNum] 
		   && m_nScreenY[radarViewNum]<(m_nLabelY[radarViewNum]+m_nLabelHeight))
		{
			m_nLabelLeaderEndX[radarViewNum] = -1;
			m_nLabelLeaderEndY[radarViewNum] = -1;
		}		
		else if(m_nLabelOffsetX[radarViewNum]>0)
		{
			m_nLabelLeaderEndX[radarViewNum] = m_nLabelX[radarViewNum];
			m_nLabelLeaderEndY[radarViewNum] = m_nLabelY[radarViewNum]+m_nLabelHeight/2;
		}
		else if((m_nLabelOffsetX[radarViewNum]+m_nLabelWidth)<0)
		{
			m_nLabelLeaderEndX[radarViewNum] = m_nLabelX[radarViewNum] + m_nLabelWidth;
			m_nLabelLeaderEndY[radarViewNum] =  m_nLabelY[radarViewNum]+m_nLabelHeight/2;
			
		}
		else if(m_nLabelOffsetY[radarViewNum]>0)
		{
			m_nLabelLeaderEndX[radarViewNum] = m_nLabelX[radarViewNum] + m_nLabelWidth/2;
			m_nLabelLeaderEndY[radarViewNum] = m_nLabelY[radarViewNum];
		}
		else if((m_nLabelOffsetY[radarViewNum]+m_nLabelHeight)<0)
		{
			m_nLabelLeaderEndX[radarViewNum] = m_nLabelX[radarViewNum] + m_nLabelWidth/2;
			m_nLabelLeaderEndY[radarViewNum] = m_nLabelY[radarViewNum]+m_nLabelHeight;
		}
	}
	
	public void setLabelOffset(int x,int y,int radarViewNum)  
	{
		m_nLabelOffsetX[radarViewNum] = x;
		m_nLabelOffsetY[radarViewNum] = y;
		updateScreenPosition(radarViewNum);
	}
	public void setColor(Color c) {m_sColor = c;}
	public void setShow(boolean b) {m_bShow = b;}
	public void setOnlyShowCurrentPos(boolean b) {m_bOnlyShowCurrentPos = b;}

	public Skp getSkp() {return m_sSkp;}
	public int getLabelX(int radarViewNum) {return m_nLabelX[radarViewNum];}
	public int getLabelY(int radarViewNum) {return m_nLabelY[radarViewNum];}
	public int getScreenX(int radarViewNum) {return m_nScreenX[radarViewNum];}
	public int getScreenY(int radarViewNum) {return m_nScreenY[radarViewNum];}
	public int getLabelWidth() {return m_nLabelWidth;}
	public int getLabelHeight() {return m_nLabelHeight;}
	public int getLabelOffsetX(int radarViewNum) {return m_nLabelOffsetX[radarViewNum];}
	public int getLabelOffsetY(int radarViewNum) {return m_nLabelOffsetY[radarViewNum];}
	public int getSize() {return m_sRFs.size();}
	public int getAatFixIndex() {return m_nAatFixIndex;}
	public Color getColor() {return m_sColor;}
	public boolean getShow() {return m_bShow;}
	public ArrayList<RouteFix> getRFs() {return m_sRFs;}
	public boolean getOnlyShowCurrentPos() {return m_bOnlyShowCurrentPos;}
}
