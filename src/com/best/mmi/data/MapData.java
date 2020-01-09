package com.best.mmi.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.best.bdaf.data.BestUtils;
import com.best.bdaf.data.LatLongData;
import com.best.mmi.data.maptype.Circle;
import com.best.mmi.data.maptype.Ellipse;
import com.best.mmi.data.maptype.FixPoint;
import com.best.mmi.data.maptype.Line;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.maptype.Sector;
import com.best.mmi.main.App;

public class MapData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Polygon> m_sPolygons;
	private ArrayList<FixPoint> m_sFixPoints;
	private ArrayList<Line> m_sLines;
	private ArrayList<Circle> m_sCircles;
	private ArrayList<Sector> m_sSectors;
	private ArrayList<Ellipse> m_sEllipses; 
	
	private Color m_sColor;
	private int m_nLineWidth;
	private String m_strName;
	
	private boolean m_bShowMap;
	
	public void reverseShowMap() {m_bShowMap = !m_bShowMap;}
	public void setColor(Color c) {m_sColor = c;}
	public void setLineWidth(int n) {m_nLineWidth = 0;}
	public void setIsShow(boolean b) {m_bShowMap = b;}
	public void setName(String name) {m_strName = name;}
	
	public ArrayList<Polygon> getPolygons() {return m_sPolygons;}
	public ArrayList<Line> getLines() {return m_sLines; }
	public ArrayList<FixPoint> getFixPoints() {return m_sFixPoints;}
	public ArrayList<Circle> getCircles() {return m_sCircles;}
	public ArrayList<Ellipse> getEllipses() {return m_sEllipses;}
	public ArrayList<Sector> getSectors() {return m_sSectors;}
	public Color getColor() {return m_sColor;}
	public String getName() {return m_strName;}
	public int getLineWidth() {return m_nLineWidth;}
	public boolean isShow() {return m_bShowMap;}
	
	
	public MapData()
	{
		m_sPolygons = new ArrayList<Polygon>(100);
		m_sFixPoints = new ArrayList<FixPoint>(100);
		m_sLines = new ArrayList<Line>(100);
		m_sCircles = new ArrayList<Circle>(100);
		m_sEllipses = new ArrayList<Ellipse>(100);
		m_sSectors = new ArrayList<Sector>(100);
		
		m_sColor = ConstantData.Color_Temp;
		m_strName = "";
		m_nLineWidth = 0;
		m_bShowMap = true;
	}
	
	public MapData(MapData md)
	{
		m_sPolygons = new ArrayList<Polygon>(100);
		m_sPolygons.addAll(md.getPolygons());
		
		m_sFixPoints = new ArrayList<FixPoint>(100);
		m_sFixPoints.addAll(md.getFixPoints());
		
		m_sLines = new ArrayList<Line>(100);
		m_sLines.addAll(md.getLines());
		
		m_sCircles = new ArrayList<Circle>(100);
		m_sCircles.addAll(md.getCircles());
		
		m_sEllipses = new ArrayList<Ellipse>(100);
		m_sEllipses.addAll(md.getEllipses());
		
		m_sSectors = new ArrayList<Sector>(100);
		m_sSectors.addAll(md.getSectors());
		
		m_sColor = ConstantData.Color_Temp;
		m_strName = "";
		m_bShowMap = true;

		updateScreenPosition(0);
		updateScreenPosition(1);
	}
	
	public void addFixPoint(Point p,int type)
	{
		FixPoint fp = new FixPoint(p);
		fp.setType(type);
		m_sFixPoints.add(fp);
	}
	
	public void addFixPoint(Point p,String name,int type,Font font)
	{
		FixPoint fp = new FixPoint(p);
		fp.setShowName(true);
		fp.setName(name);
		fp.setType(type);
		m_sFixPoints.add(fp);
	}
	
	
	public void addLinePoint(Point p,boolean newLine)
	{
		Line line = null;
		if(newLine == true)
		{
			line = new Line();
			m_sLines.add(line);
		}
		else
		{
			line = m_sLines.get(m_sLines.size()-1);
		}
		line.addLinePoint(p);
	}
	
	public void addEllipse(Ellipse ep)
	{
		Ellipse epNew = new Ellipse(ep);
		m_sEllipses.add(epNew);
	}
	
	public void addLine(Line l)
	{
		Line line = new Line(l);
		m_sLines.add(line);
	}
	
	public void addPolygon(Polygon p)
	{
		Polygon pNew = new Polygon(p);
		m_sPolygons.add(pNew);
	}
	
	public void clear()
	{
		m_sPolygons.clear();
		m_sFixPoints.clear();
		m_sLines.clear();
		m_sCircles.clear();
		m_sEllipses.clear();
	}
	
	public void writeToFile()
	{
		try
		{
			File outFileFolder = new File(ConstantData.str_PrivateMapPath);
			if(!outFileFolder.exists())
				outFileFolder.mkdir();
			
			File outFile = File.createTempFile("tmpMap", ".tmp");
			File inFile = new File(ConstantData.str_PrivateMapPath+m_strName+".map");
			
		    FileOutputStream fos = new FileOutputStream(outFile);
		    ObjectOutputStream out = new ObjectOutputStream(fos);

		    out.writeObject(this);

		    out.close();
		    inFile.delete();
		    outFile.renameTo(inFile);
		}
		catch(Exception e)
		{
			System.out.println("Restore PrivateMap:"+ m_strName +" to privateMap folder failed");
			e.printStackTrace();
		}
		
	}
	
	public boolean isEmpty()
	{
		if((m_sPolygons.size()==0) && (m_sFixPoints.size()==0) &&
			(m_sLines.size()==0) && (m_sCircles.size()==0) &&
			(m_sEllipses.size()==0))
			return true;
		else
			return false;
	}
	
	public MapData(String name,String fileName)
	{	
		m_strName = name;
		m_sPolygons = new ArrayList<Polygon>(100);
		m_sFixPoints = new ArrayList<FixPoint>(1000);
		m_sLines = new ArrayList<Line>(100);
		m_sCircles = new ArrayList<Circle>(100);
		m_sEllipses = new ArrayList<Ellipse>(100);
		m_sSectors = new ArrayList<Sector>(100);
		
		String colorStr = App.getApp().getIniData().getSysPara("Mapcolors", m_strName);	
		if(colorStr == null)
			m_sColor = App.getApp().getSysInfo().getSysColor("Default Map");
		else
		{
			String[] colorStrs = colorStr.split(",");
			m_sColor = new Color(Integer.parseInt(colorStrs[0]),Integer.parseInt(colorStrs[1]),Integer.parseInt(colorStrs[2]));
		}	
		String showStr = App.getApp().getIniData().getSysPara("Mapshows", m_strName);
		if(showStr == null)
			m_bShowMap = true;
		else
			m_bShowMap = Boolean.parseBoolean(showStr);
		
		try
		{
			if(name.equals("CHARACTERISTIC_POINTS"))
			{
				HashMap<String, String> map = com.best.bdaf.main.App.getApp().getNavData().getFixnames();
				Iterator<String> it = map.keySet().iterator();
				while(it.hasNext())
				{
					String fname = it.next();
					String fpos = map.get(fname);
					FixPoint fp = new FixPoint();
					fp.setName(fname);
					fp.setShowName(true);
					Point distancep = ToolList.convertLatLongStringToDistanceP(fpos);
					if(distancep != null)
						fp.setDistanceXY(distancep.x, distancep.y);
					
					m_sFixPoints.add(fp);		
				}
			}
			else if(fileName.equals("db"))
			{
				
			}
			else if(fileName.toLowerCase().endsWith("txt"))
				initTxt(name,fileName);
			else if(fileName.toLowerCase().endsWith("lex"))
				initLex(name,fileName);
		}
		catch(Exception e)
		{
			System.out.println("Load mapdata "+name+" failed");
			e.printStackTrace();	
		}
	}
	
	
	public void initTxt(String name, String fileName)
	{
		String line = null;
		
		try
		{
			if(name.equals("ChinaBoarder"))
			{
				initChinaBorder(name, fileName);
			}	
		}
		catch (Exception e) {e.printStackTrace();System.out.println(line);}	
	}
	
	public void initLex(String name,String fileName)
	{
		try
		{
			InputStreamReader isr = new InputStreamReader(new FileInputStream("resource//map//"+fileName),"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String str = br.readLine();
			boolean isBeginPolygon = false;
			boolean isBeginBrokenLine = false;
			boolean isBeginLabel = false;
			String polygonText = "POLYGON";
			String brokenLineText = "BROKENLINE";
			String pointText = "POINT";
			String endText = "EXPAND 1";
			double scale = 0;
			if(name.endsWith("NEW"))
				scale = 7.2344*32; 
			else
				scale = 7.2344;		//1nm/256
			Polygon polygon = null;
			Line line = null;
			
			while(str != null)
			{
				str = str.trim();				
				
				if(str.equals(polygonText))
				{
					isBeginPolygon = true;
					polygon = new Polygon();
					m_sPolygons.add(polygon);					
				}
				else if(str.equals(brokenLineText))
				{
					isBeginBrokenLine = true;
					line = new Line();
					line.setTypeAndWidth(2, 1);
					m_sLines.add(line);
				}
				else if(str.equals(endText))
				{
					isBeginPolygon = false;
					isBeginBrokenLine = false;		
				}
				else if(str.startsWith(pointText))
				{
					if(isBeginPolygon)
					{
						String[] strs = str.split(" {1,}");
						for(int j=0;j<strs.length;j++)
						{
							if(strs[j].startsWith(pointText))
							{
								Point distanceP = new Point();
								distanceP.x = (int)(Integer.parseInt(strs[j+1])*scale);
								distanceP.y = (int)(Integer.parseInt(strs[j+2])*scale);
								
								if((Math.abs(distanceP.x) < (ConstantData.d_MaxRadarScope*1000)) && (Math.abs(distanceP.y) < (ConstantData.d_MaxRadarScope*1000)))
									polygon.addPoint(distanceP);
							}	
						}
					}
					if(isBeginBrokenLine)
					{
						String[] strs = str.split(" {1,}");
						for(int j=0;j<strs.length;j++)
						{
							if(strs[j].startsWith(pointText))
							{
								Point distanceP = new Point();
								distanceP.x = (int)(Integer.parseInt(strs[j+1])*scale);
								distanceP.y = (int)(Integer.parseInt(strs[j+2])*scale);
								
								if((Math.abs(distanceP.x) < (ConstantData.d_MaxRadarScope*1000)) && (Math.abs(distanceP.y) < (ConstantData.d_MaxRadarScope*1000)))
									line.addLinePoint(distanceP);	
							}
						}
					}
				}
				
				else if(str.startsWith("VECTOR"))
				{
					line = new Line();
					m_sLines.add(line);
					
					String[] strs = str.split(" {1,}");
					
					Point distanceP1 = new Point();
					distanceP1.x = (int)(Integer.parseInt(strs[2])*scale);
					distanceP1.y = (int)(Integer.parseInt(strs[3])*scale);
					Point distanceP2 = new Point();
					distanceP2.x = (int)(Integer.parseInt(strs[5])*scale);
					distanceP2.y = (int)(Integer.parseInt(strs[6])*scale);
						
					if((Math.abs(distanceP1.x) < (ConstantData.d_MaxRadarScope*1000)) && (Math.abs(distanceP1.y) < (ConstantData.d_MaxRadarScope*1000)))
						line.addLinePoint(distanceP1);	
					if((Math.abs(distanceP2.x) < (ConstantData.d_MaxRadarScope*1000)) && (Math.abs(distanceP2.y) < (ConstantData.d_MaxRadarScope*1000)))
						line.addLinePoint(distanceP2);		
				}
				else if(str.startsWith("CODE"))
				{
					String[] strs = str.split(" ");
					int pointIndex = -1;
					inner:
					for(int i=0;i<strs.length;i++)
					{
						if(strs[i].equals("POINT"))
						{
							pointIndex = i;
							break inner;
						}
					}
					Point distanceP = new Point();
					distanceP.x = (int)(Integer.parseInt(strs[pointIndex+1])*scale);
					distanceP.y = (int)(Integer.parseInt(strs[pointIndex+2])*scale);
				
					FixPoint fp = new FixPoint(distanceP);
					m_sFixPoints.add(fp);
				}
				else if(str.startsWith("LEADER"))
				{
					String[] strs = str.split(" ");
					int pointIndex = -1;
					inner:
					for(int i=0;i<strs.length;i++)
					{
						if(strs[i].equals("POINT"))
						{
							pointIndex = i;
							break inner;
						}
					}
					Point distanceP = new Point();
					distanceP.x = (int)(Integer.parseInt(strs[pointIndex+1])*scale);
					distanceP.y = (int)(Integer.parseInt(strs[pointIndex+2])*scale);
				
					FixPoint fp = new FixPoint(distanceP);
					fp.setType(0);
					fp.setShowName(true);
					m_sFixPoints.add(fp);
				}
				else if(str.equals("LABEL"))
				{
					isBeginLabel = true;
				}
				else if(isBeginLabel == true)
				{
					String label = str.substring(1, str.length()-1);
					m_sFixPoints.get(m_sFixPoints.size()-1).setName(label);
					isBeginLabel = false;
				}
				
				str = br.readLine();
			}
			
			br.close();	
		}
		catch (Exception e) {System.out.println("Load guojiexian map failed!");e.printStackTrace();}	
	}
	
	
	
	public void initChinaBorder(String name,String fileName)
	{
		try
		{
			InputStreamReader isr = new InputStreamReader(new FileInputStream("resource//map//"+fileName),"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String str = br.readLine();
				
			Line l = null;
			while(str != null)
			{
				str = str.trim();
				if(str.startsWith("pline"))
				{
					if(l != null)
						m_sLines.add(l);
					l = new Line();
				}
				else 
				{
					String[] strSplit = str.split(" ");
					if(strSplit.length == 2)
					{
						double dLong = Double.parseDouble(strSplit[0]);
						double dLat = Double.parseDouble(strSplit[1]);
						Point distanceP = App.getApp().getLatLongData().LatitudeAndLongitudeToDistanceXY(new Point2D.Double(dLong, dLat));
						if((Math.abs(distanceP.x) < (ConstantData.d_MaxRadarScope*1000)) && (Math.abs(distanceP.y) < (ConstantData.d_MaxRadarScope*1000)))
							l.addLinePoint(distanceP);	
					}
				}
				str = br.readLine();
			}
			if(l != null)
				m_sLines.add(l);
			
			br.close();	
		}
		catch (Exception e) {System.out.println("Load guojiexian map failed!");e.printStackTrace();}	
	}
	
	public void draw(Graphics g,int radarViewNum)
	{
		if(!m_bShowMap)
			return;

		g.setColor(m_sColor);
		for(int i=0;i<m_sPolygons.size();i++)
		{
			m_sPolygons.get(i).draw(g,radarViewNum);
		}
		for(int i=0;i<m_sFixPoints.size();i++)
		{
			m_sFixPoints.get(i).draw(g,radarViewNum);
		}
		for(int i=0;i<m_sLines.size();i++)
		{
			m_sLines.get(i).draw(g,radarViewNum);
		}
		for(int i=0;i<m_sCircles.size();i++)
		{
			m_sCircles.get(i).draw(g,radarViewNum);
		}
		for(int i=0;i<m_sEllipses.size();i++)
		{
			m_sEllipses.get(i).draw(g, radarViewNum);
		}
		for(int i=0;i<m_sSectors.size();i++)
		{
			m_sSectors.get(i).draw(g, radarViewNum);
		}
	}
	
	public void updateScreenPosition(int radarViewNum)
	{
		for(int i=0;i<m_sFixPoints.size();i++)
		{
			m_sFixPoints.get(i).updateScreenPosition(radarViewNum);	
		}
		for(int i=0;i<m_sLines.size();i++)
		{
			m_sLines.get(i).updateScreenPosition(radarViewNum);	
		}
		for(int i=0;i<m_sPolygons.size();i++)
		{
			m_sPolygons.get(i).updateScreenPosition(radarViewNum);
		}
		for(int i=0;i<m_sCircles.size();i++)
		{
			m_sCircles.get(i).updateScreenPosition(radarViewNum);
		}
		for(int i=0;i<m_sEllipses.size();i++)
		{
			m_sEllipses.get(i).updateScreenPosition(radarViewNum);
		}
		for(int i=0;i<m_sSectors.size();i++)
		{
			m_sSectors.get(i).updateScreenPosition(radarViewNum);
		}
	}
}