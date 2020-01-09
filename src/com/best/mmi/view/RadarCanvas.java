package com.best.mmi.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.best.mmi.data.MapList;
import com.best.mmi.data.SkpPo;
import com.best.mmi.data.SysInfo;
import com.best.mmi.data.ToolList;
import com.best.mmi.main.App;
import com.best.mmi.viewlistener.RadarViewKeyListener;
import com.best.mmi.viewlistener.RadarViewMouseListener;

public class RadarCanvas extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int m_nCanvasNum; 
	private final Font fontLogo = new Font("Serif",Font.BOLD,40);
	private final String strLogo = "BEST";
	
	private RadarViewMouseListener m_pRadarViewMouseListener;
	private RadarViewKeyListener m_pRadarViewKeyListener;

	RadarCanvas(boolean b,int canvasNum)
	{
		super(b);
		setFocusable(true);
		
		m_nCanvasNum = canvasNum;
		m_pRadarViewMouseListener = new RadarViewMouseListener(m_nCanvasNum);
		
		addMouseListener(m_pRadarViewMouseListener);
		addMouseMotionListener(m_pRadarViewMouseListener);
		addMouseWheelListener(m_pRadarViewMouseListener);
		
		m_pRadarViewKeyListener = new RadarViewKeyListener(m_nCanvasNum);
		addKeyListener(m_pRadarViewKeyListener);
	}

	
	public void paintComponent(Graphics g) 
	{
		//super.paintComponents(g);
		
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		drawBrand(g,m_nCanvasNum);
		drawMap(g,m_nCanvasNum);
		drawAirspace(g,m_nCanvasNum);
		drawTool(g,m_nCanvasNum);
		drawSkpPo(g,m_nCanvasNum);
	}
	
	public void drawBrand(Graphics g,int m_nCanvasNum)
	{
		if(m_nCanvasNum == 0)
		{
			g.setColor(Color.gray);
			g.setFont(fontLogo);
			
			g.drawString(strLogo, 10, getSize().height-30);
		}
	}
	
	
	public void drawSkpPo(Graphics g,int radarViewNum)
	{	
		g.setFont(App.getApp().getSysInfo().getSysFont("Track"));
		ArrayList<SkpPo> sps = App.getApp().getSkpPos();
		for(SkpPo sp: sps)
			sp.draw(g, radarViewNum);
	}
	
	public void drawMap(Graphics g,int radarViewNum)
	{
		App.getApp().getMapList().draw(g,radarViewNum);		
	}
	
	public void drawAirspace(Graphics g,int radarViewNum)
	{
		App.getApp().getAirspaceList().draw(g, radarViewNum);
	}
	
	public void drawTool(Graphics g,int radarViewNum)
	{
		App.getApp().getToolList().draw(g,radarViewNum);
	}
	
	public RadarViewMouseListener getRadarViewMouseListener() {return m_pRadarViewMouseListener;}
}