package com.best.mmi.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import org.jfree.ui.Layer;

import com.best.mmi.data.airspace.Airspace;
import com.best.mmi.data.airspace.DangerArea;
import com.best.mmi.data.airspace.ForbidArea;
import com.best.mmi.data.airspace.QnhArea;
import com.best.mmi.data.airspace.RestrictArea;
import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.main.App;

public class AirspaceList implements ActionListener
{
	public static int TYPE_QNH = 3;
	public static final int TYPE_DANGER = 2;
	public static final int TYPE_FORBID = 11;
	public static final int TYPE_RESTRICT = 12;
	
	private ArrayList<QnhArea> m_sQnhAreas;
	private QnhArea m_pLocalQnhArea;
	private ArrayList<DangerArea> m_sDangerAreas;
	private ArrayList<ForbidArea> m_sForbidAreas;
	private ArrayList<RestrictArea> m_sRestrictAreas;
	
	private Airspace m_pAirspace_HiLight;
	private ArrayList<Airspace> m_cAirspaces_HiLight;
	private boolean m_bShowAirspace_HiLight;
	private Timer m_sAirspaceTimer_HiLight;
	private int m_nFlashCount_HiLight;
	
	private Color m_sColorQnh;
	private Color m_sColorDanger;
	private Color m_sColorForbid;
	private Color m_sColorRestrict;
	
	private boolean m_bShowQnh;
	private boolean m_bShowDanger;
	private boolean m_bShowForbid;
	private boolean m_bShowRestrict;
	
	private Font m_sFont;
	
	public AirspaceList()
	{
		m_sDangerAreas = new ArrayList<DangerArea>(16);
		m_sForbidAreas = new ArrayList<ForbidArea>(16);
		m_sRestrictAreas = new ArrayList<RestrictArea>(16);
		m_sQnhAreas = new ArrayList<QnhArea>(16);
		m_cAirspaces_HiLight = new ArrayList<Airspace>(16);
	}
	
	
	public Font getFont() {return m_sFont;}
	public QnhArea getLocalQnhArea() {return m_pLocalQnhArea;}
	public ArrayList<DangerArea> getDangerAreas() {return m_sDangerAreas;}
	public ArrayList<ForbidArea> getForbidAreas() {return m_sForbidAreas;}
	public ArrayList<RestrictArea> getRestrictAreas() {return m_sRestrictAreas;}
	public ArrayList<QnhArea> getQnhAreas() {return m_sQnhAreas;}

	public Color getColorDanger() {return m_sColorDanger;}
	public Color getColorForbid() {return m_sColorForbid;}
	public Color getColorRestrict() {return m_sColorRestrict;}
	public Color getColorQnh() {return m_sColorQnh;}
	
	public boolean isShowQnh() {return m_bShowQnh;}
	public boolean isShowDanger() {return m_bShowDanger;}
	public boolean isShowForbid() {return m_bShowForbid;}
	public boolean isShowRestrict() {return m_bShowRestrict;}
	
	public void setFont(Font f) {m_sFont = f;}
	public void setShowDanger(boolean b) {m_bShowDanger = b;}
	public void setShowForbid(boolean b) {m_bShowForbid = b;}
	public void setShowRestrict(boolean b) {m_bShowRestrict = b;}
	public void setShowQnh(boolean b) {m_bShowQnh = b;}
	
	public void setColorDanger(Color c) {m_sColorDanger = c;}
	public void setColorForbid(Color c) {m_sColorForbid = c;}
	public void setColorRestrict(Color c) {m_sColorRestrict = c;}
	public void setColorQnh(Color c) {m_sColorQnh = c;}
	
	
	public void init()
	{
		updateFont();
		
		IniData id = App.getApp().getIniData();
		String colorStr = id.getSysPara("Mapcolors", "QnhArea");	
		if(colorStr == null)
			m_sColorQnh = App.getApp().getSysInfo().getSysColor("Default Map");
		else
		{
			String[] colorStrs = colorStr.split(",");
			m_sColorQnh = new Color(Integer.parseInt(colorStrs[0]),Integer.parseInt(colorStrs[1]),Integer.parseInt(colorStrs[2]));
		}	
		

		colorStr = id.getSysPara("Mapcolors", "DangerArea");	
		if(colorStr == null)
			m_sColorDanger = App.getApp().getSysInfo().getSysColor("Default Map");
		else
		{
			String[] colorStrs = colorStr.split(",");
			m_sColorDanger = new Color(Integer.parseInt(colorStrs[0]),Integer.parseInt(colorStrs[1]),Integer.parseInt(colorStrs[2]));
		}	
		
		colorStr = id.getSysPara("Mapcolors", "ForbidArea");	
		if(colorStr == null)
			m_sColorForbid = App.getApp().getSysInfo().getSysColor("Default Map");
		else
		{
			String[] colorStrs = colorStr.split(",");
			m_sColorForbid = new Color(Integer.parseInt(colorStrs[0]),Integer.parseInt(colorStrs[1]),Integer.parseInt(colorStrs[2]));
		}	
		
		colorStr = id.getSysPara("Mapcolors", "RestrictArea");	
		if(colorStr == null)
			m_sColorRestrict = App.getApp().getSysInfo().getSysColor("Default Map");
		else
		{
			String[] colorStrs = colorStr.split(",");
			m_sColorRestrict = new Color(Integer.parseInt(colorStrs[0]),Integer.parseInt(colorStrs[1]),Integer.parseInt(colorStrs[2]));
		}	
		
		String showStr = id.getSysPara("Mapshows", "AdsbArea");
		if(showStr == null)
			m_bShowQnh = true;
		else
			m_bShowQnh = Boolean.parseBoolean(showStr);
		
		showStr = id.getSysPara("Mapshows", "DangerArea");
		if(showStr == null)
			m_bShowDanger = true;
		else
			m_bShowDanger = Boolean.parseBoolean(showStr);
		
		showStr = id.getSysPara("Mapshows", "ForbidArea");
		if(showStr == null)
			m_bShowForbid = true;
		else
			m_bShowForbid = Boolean.parseBoolean(showStr);
		
		showStr = id.getSysPara("Mapshows", "RestrictArea");
		if(showStr == null)
			m_bShowRestrict = true;
		else
			m_bShowRestrict = Boolean.parseBoolean(showStr);
		
		m_pAirspace_HiLight = null;
		m_bShowAirspace_HiLight = false;
		m_nFlashCount_HiLight = 0;
		m_sAirspaceTimer_HiLight = new Timer(1000, this);
		
		Polygon localPolygon = new Polygon();
		m_pLocalQnhArea = new QnhArea(App.getApp().getSysInfo().getLocalAirport(), new Polygon());
		m_sQnhAreas.add(m_pLocalQnhArea);
		
		reload();
	}
	
	public void reload()
	{
		reload(TYPE_DANGER);
		reload(TYPE_FORBID);
		reload(TYPE_RESTRICT);	
	}
	
	public void reload(int type)
	{
		
		
	}
	
	public void updateFont()
	{
		m_sFont = App.getApp().getSysInfo().getSysFont("Map");
	}
	
	public void updateScreenPosition(int radarViewNum)
	{
		for(int i=0;i<m_sDangerAreas.size();i++)
			m_sDangerAreas.get(i).updateScreenPosition(radarViewNum);
		for(int i=0;i<m_sForbidAreas.size();i++)
			m_sForbidAreas.get(i).updateScreenPosition(radarViewNum);
		for(int i=0;i<m_sRestrictAreas.size();i++)
			m_sRestrictAreas.get(i).updateScreenPosition(radarViewNum);
	}
	
	public void draw(Graphics g,int radarViewNum)
	{
		g.setFont(m_sFont);
		
		if(m_bShowDanger)
		{
			g.setColor(m_sColorDanger);
			for(int i=0;i<m_sDangerAreas.size();i++)
				m_sDangerAreas.get(i).draw(g, radarViewNum);
		}
		if(m_bShowForbid)
		{
			g.setColor(m_sColorForbid);
			for(int i=0;i<m_sForbidAreas.size();i++)
				m_sForbidAreas.get(i).draw(g, radarViewNum);
		}
		if(m_bShowRestrict)
		{
			g.setColor(m_sColorRestrict);
			for(int i=0;i<m_sRestrictAreas.size();i++)
				m_sRestrictAreas.get(i).draw(g, radarViewNum);
		}
		
		
		
		if(m_bShowAirspace_HiLight)
		{
			g.setColor(ConstantData.Color_Temp);
			Stroke stroke = ((Graphics2D)g).getStroke();
			((Graphics2D)g).setStroke(MapList.REAL_STROKES[2]);
			if(m_pAirspace_HiLight != null)
				m_pAirspace_HiLight.drawIgnoreValid(g, radarViewNum);
			for(int i=0;i<m_cAirspaces_HiLight.size();i++)
				m_cAirspaces_HiLight.get(i).drawIgnoreValid(g, radarViewNum);
			((Graphics2D)g).setStroke(stroke);		
		}
	}
	
	public Airspace findAirspaceByName(String name,int type)
	{
		if(name.equals(""))
			return null;
		
		Airspace ap = null;
		switch(type)
		{
		case TYPE_DANGER: 
			for(int i=0;i<m_sDangerAreas.size();i++)
			{
				DangerArea da = m_sDangerAreas.get(i);
				if(da.getName().equals(name))
				{
					ap = da;
					break;
				}
			}
			break;
		case TYPE_FORBID:
			for(int i=0;i<m_sForbidAreas.size();i++)
			{
				ForbidArea fa = m_sForbidAreas.get(i);
				if(fa.getName().equals(name))
				{
					ap = fa;
					break;
				}
			}
			break;
		case TYPE_RESTRICT:
			for(int i=0;i<m_sRestrictAreas.size();i++)
			{
				RestrictArea rsa = m_sRestrictAreas.get(i);
				if(rsa.getName().equals(name))
				{
					ap = rsa;
					break;
				}
			}
			break;
		}		
		return ap;	
	}
	
	
	public void beginFlash(Airspace a)
	{
		m_pAirspace_HiLight = a;
		m_cAirspaces_HiLight.clear();
		m_nFlashCount_HiLight = 0;
		m_sAirspaceTimer_HiLight.restart();
	}
	
	public void beginFlash(ArrayList<Airspace> as)
	{
		m_cAirspaces_HiLight = as;
		m_pAirspace_HiLight = null;
		m_nFlashCount_HiLight = 0;
		m_sAirspaceTimer_HiLight.restart();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		m_bShowAirspace_HiLight = !m_bShowAirspace_HiLight;
		m_nFlashCount_HiLight++;
		if(m_nFlashCount_HiLight == 12)
		{
			m_nFlashCount_HiLight = 0;
			m_bShowAirspace_HiLight = false;
			m_sAirspaceTimer_HiLight.stop();
		}
	}
}