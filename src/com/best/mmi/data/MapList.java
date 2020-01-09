package com.best.mmi.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.best.mmi.main.App;

public class MapList
{
	private HashMap<String,String> m_sMapParas;
	private HashMap<String,MapData> m_sMaps;
	private ArrayList<String> m_sMapNames;
	
	private MapData m_pFreeMapData;
	
	private Font m_sFont;
	
	public static final float[] style = {5,5};
	public static final BasicStroke[] DASH_STROKES = {
		new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f,style,0.0f),
		new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f,style,0.0f),
		new BasicStroke(3,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f,style,0.0f),
		new BasicStroke(5,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f,style,0.0f),
		new BasicStroke(10,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f,style,0.0f)};
		
	public static final BasicStroke[] REAL_STROKES = {
		new BasicStroke(1),
		new BasicStroke(1),
		new BasicStroke(3),
		new BasicStroke(5),
		new BasicStroke(10)};
	public static HashMap<Integer,BasicStroke>[] ROUTE_STROKES;
	
	public MapData findMapDataFromName(String str)
	{
		return m_sMaps.get(str);
	}
	
	public ArrayList<String> getMapNames() {return m_sMapNames;}
	public MapData getFreeMapData() {return m_pFreeMapData;}
	
	public void setMapColor(String mapName,Color color) 
	{
		m_sMaps.get(mapName).setColor(color);
	}
	
	public void setFreeMapData(MapData md) 
	{
		if(md == null)
			return;
		m_pFreeMapData = md;
		m_pFreeMapData.updateScreenPosition(0);
		m_pFreeMapData.updateScreenPosition(1);
	}
	
	public void setFreeMapData(String mapFileName) 
	{
		if(mapFileName == null)
			return;
		m_pFreeMapData = new MapData("FreeMap", mapFileName);
		m_pFreeMapData.updateScreenPosition(0);
		m_pFreeMapData.updateScreenPosition(1);
	}
	
	public void addMapData(String mapName,String mapPath)
	{
		MapData md = new MapData(mapName, mapPath);
		md.updateScreenPosition(0);
		md.updateScreenPosition(1);
		m_sMapParas.put(mapName, mapPath);
		m_sMaps.put(mapName, md);
		m_sMapNames.add(mapName);
	}
	
	public MapList()
	{
		m_sMapParas = new HashMap<String,String>();
		m_sMaps = new HashMap<String,MapData>();
		m_sMapNames = new ArrayList<String>();
		
		ROUTE_STROKES = new HashMap[2];
		for(int i=0;i<ROUTE_STROKES.length;i++)
			ROUTE_STROKES[i] = new HashMap<Integer, BasicStroke>(16);
	}
	
	
	public void init()
	{
		String navFixName = "CHARACTERISTIC_POINTS";
		
		m_sMapParas = App.getApp().getIniData().getFieldParas("Maps");
		Set<String> keySet = m_sMapParas.keySet();
		m_sMapNames.addAll(keySet);
		m_sMapNames.add(navFixName);
		Collections.sort(m_sMapNames);
		
		for(int i=0;i<m_sMapNames.size();i++)
		{
			String mapName = m_sMapNames.get(i);
			m_sMaps.put(mapName, new MapData(mapName,m_sMapParas.get(mapName)));
		}
		
		m_sMaps.put(navFixName, new MapData(navFixName,null));
		
		updateFont();
		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	
	public void draw(Graphics g,int radarViewNum)
	{
		g.setFont(m_sFont);
		
		MapData mdRoute = m_sMaps.get("Route");
		if(mdRoute != null)
			mdRoute.draw(g, radarViewNum);
		
		for(int i=0;i<m_sMapNames.size();i++)
		{
			String name = m_sMapNames.get(i);
			if(!name.equals("Route"))
				m_sMaps.get(name).draw(g, radarViewNum);
		}
		
		if(m_pFreeMapData != null)
			m_pFreeMapData.draw(g, radarViewNum);
		
		if(App.getApp().getSysInfo().getShowCenter())
		{
			g.setColor(ConstantData.Color_Temp);
			Dimension size = App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).getScreenSize();
			int x = size.width/2;
			int y = size.height/2;
			g.drawLine(x-8, y, x+8, y);
			g.drawLine(x, y-8, x, y+8);
		}	
	}
	
	public void updateScreenPosition(int radarViewNum)
	{
		for(int i=0;i<m_sMapNames.size();i++)
		{
			m_sMaps.get(m_sMapNames.get(i)).updateScreenPosition(radarViewNum);
		}
		
		if(m_pFreeMapData != null)
			m_pFreeMapData.updateScreenPosition(radarViewNum);
		
		HashMap<Integer, BasicStroke> strokes = ROUTE_STROKES[radarViewNum];
		ArrayList<Integer> widths = new ArrayList<Integer>(strokes.keySet());
		for(int i=0;i<widths.size();i++)
		{
			int width = widths.get(i).intValue();
			double screenWidth = App.getApp().getSysInfo().getRadarViewInfo(radarViewNum).
				getXScale()*width*1000;
			strokes.put(width, new BasicStroke((float)screenWidth,
				BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		}		
	}
	
	public void updateFont()
	{
		m_sFont = App.getApp().getSysInfo().getSysFont("Map");
	}
}


