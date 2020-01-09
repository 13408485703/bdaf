package com.best.mmi.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.best.mmi.data.airspace.Airspace;
import com.best.mmi.data.maptype.Ellipse;
import com.best.mmi.data.maptype.Line;
import com.best.mmi.data.maptype.Polygon;
import com.best.mmi.data.tooltype.RangeRing;
import com.best.mmi.data.tooltype.Rbl;
import com.best.mmi.data.tooltype.SimpleRect;
import com.best.mmi.data.tooltype.Text;
import com.best.mmi.main.App;

public class ToolList
{	
	private ArrayList<Rbl> m_sRbls;
	private ArrayList<Text> m_sTexts;
	private RangeRing m_pRangeRing;
	
	private Rbl m_pTmpRbl;
	private SimpleRect m_pSimpleRect;
	private Line m_pTmpLine;
	private Polygon m_pTmpPolygon;
	private Ellipse m_pTmpEllipse;
	private MapData m_pTmpMapData;
	private Airspace m_pTmpAirspace;

	private Color m_sRblColor;
	private Color m_sTempMapDataColor;
	private Color m_sTextColor;
	private Color m_sSepProbeColor;
	private Color m_sRangeRingColor;
	private Color m_sFutureRouteColor;
	private Color m_sCompassColor;
	
	private Font m_sRblFont;
	private Font m_sTempMapDataFont;
	private Font m_sSepProbeFont;
	private Font m_sRangeRingFont;
	private Font m_sTextFont;
	private Font m_sFutureRouteFont;
	private Font m_sCompassFont;
	
	public ToolList()
	{
		m_sRbls = new ArrayList<Rbl>();
		m_sTexts = new ArrayList<Text>();
		m_pTmpRbl = new Rbl();
		m_pTmpLine = new Line();
		m_pSimpleRect = new SimpleRect();
		m_pTmpPolygon = new Polygon();
		m_pTmpEllipse = new Ellipse();
		m_pTmpMapData = new MapData();
		m_pTmpAirspace = new Airspace("TmpAirspace", new Polygon(), 0, 30000);
	}

	public void init()
	{
		m_pRangeRing = new RangeRing();
		
		updateColor();
		updateFont();
	}
	
	public MapData getTmpMapData()
	{
		return m_pTmpMapData;
	}
	public Airspace getTmpAirspace()
	{
		return m_pTmpAirspace;
	}
	
	public Ellipse getTmpEllipse()
	{
		return m_pTmpEllipse;
	}

	public Line getTmpLine()
	{
		return m_pTmpLine;
	}
	
	public com.best.mmi.data.maptype.Polygon getTmpPolygon()
	{
		return m_pTmpPolygon;
	}

	public int getRblTotle()
	{
		return m_sRbls.size();
	}

	public int getTextTotle()
	{
		return m_sTexts.size();
	}

	
	public void addTmpLinePoint(Point distanceP)
	{
		m_pTmpLine.addLinePoint(distanceP);
	}

	public void removeRbl(Rbl rbl)
	{
		m_sRbls.remove(rbl);
	}
	
	public void removeAllRbl()
	{
		m_sRbls.clear();
	}

	public void removeText(Text freeText)
	{
		m_sTexts.remove(freeText);
	}
	
	public void removeAllText()
	{
		m_sTexts.clear();
	}

	
	public void updateColor()
	{
		SysInfo info = App.getApp().getSysInfo();
		m_sRblColor = info.getSysColor("RBL");
		m_sTempMapDataColor = ConstantData.Color_Temp;
		m_sTextColor = info.getSysColor("Free Text");
		m_sSepProbeColor = info.getSysColor("SEP Probe");
		m_sRangeRingColor = info.getSysColor("Range Rings");
		m_sFutureRouteColor = info.getSysColor("RBL");
		m_sCompassColor = ConstantData.Color_Temp;
	}
	
	public void updateFont()
	{
		SysInfo info = App.getApp().getSysInfo();
		m_sRblFont = info.getSysFont("RBL");
		m_sTempMapDataFont = info.getSysFont("Map");
		m_sSepProbeFont = info.getSysFont("SEP Probe");
		m_sRangeRingFont = info.getSysFont("Range Rings");
		m_sTextFont = info.getSysFont("Free Text");
		m_sFutureRouteFont = new Font("Monospaced",Font.BOLD,12);
		m_sCompassFont = new Font("Monospaced",Font.BOLD,12);
	}
	
	public void updateScreenPosition(int radarViewNum)
	{
		for (int i = 0; i < m_sRbls.size(); i++)
		{
			m_sRbls.get(i).updateScreenPosition(radarViewNum);
		}
		for (int i = 0; i < m_sTexts.size(); i++)
		{
			m_sTexts.get(i).updateScreenPosition(radarViewNum);
		}
		m_pRangeRing.updateScreenPosition(radarViewNum);
		
		m_pTmpRbl.updateScreenPosition(radarViewNum);
		m_pTmpLine.updateScreenPosition(radarViewNum);
		m_pTmpEllipse.updateScreenPosition(radarViewNum);
		m_pTmpPolygon.updateScreenPosition(radarViewNum);
		m_pTmpMapData.updateScreenPosition(radarViewNum);
		m_pTmpAirspace.updateScreenPosition(radarViewNum);
	}

	public void draw(Graphics g,int radarViewNum)
	{	
		g.setColor(m_sRblColor);
		g.setFont(m_sRblFont);
		m_pTmpRbl.draw(g,radarViewNum);
		for (int i = 0; i < m_sRbls.size(); i++)
		{
			m_sRbls.get(i).draw(g,radarViewNum);
		}

		g.setColor(m_sTextColor);
		g.setFont(m_sTextFont);
		for (int i = 0; i < m_sTexts.size(); i++)
		{
			m_sTexts.get(i).draw(g,radarViewNum);
		}

		g.setColor(m_sRangeRingColor);
		g.setFont(m_sRangeRingFont);
		m_pRangeRing.draw(g,radarViewNum);
		
			
		m_pSimpleRect.draw(g,radarViewNum);
		m_pTmpLine.draw(g, radarViewNum);
		m_pTmpEllipse.draw(g, radarViewNum);
		m_pTmpPolygon.draw(g, radarViewNum);
		
		g.setColor(m_sTempMapDataColor);
		g.setFont(m_sTempMapDataFont);
		m_pTmpMapData.draw(g, radarViewNum);
		m_pTmpAirspace.draw(g, radarViewNum);
	}
	
	public Rbl findRblFromXy(int x, int y, int radarViewNum)
	{
		Rbl rbl = null;
		int textX = 0;
		int textY = 0;
		for (int i = 0; i < m_sRbls.size(); i++)
		{
			rbl = m_sRbls.get(i);

			switch (rbl.getType())
			{
			case 1:
				textX = (rbl.getScreenPoint1(radarViewNum).x + rbl.getScreenPoint2(radarViewNum).x) / 2;
				textY = (rbl.getScreenPoint1(radarViewNum).y + rbl.getScreenPoint2(radarViewNum).y) / 2;
				break;
			default:
				break;
			}
			if ((x > textX) && (x < textX + 90) && (y < textY)
					&& (y > textY - 20))
				return rbl;
		}

		return null;

	}

	

	public Text findTextFromXy(int x, int y, int radarViewNum)
	{
		Text text = null;
		int textX = 0;
		int textY = 0;
		for (int i = 0; i < m_sTexts.size(); i++)
		{
			text = (Text) m_sTexts.get(i);
			textX = text.getScreenX(radarViewNum);
			textY = text.getScreenY(radarViewNum);
			if ((x > textX) && (x < textX + 90) && (y < textY)
					&& (y > textY - 20))
				return text;
		}
		return null;
	}

	public void setTmpRblBegin(Point distanceP,int radarNum)
	{
		m_pTmpRbl.setRblBegin(distanceP);	
		m_pTmpRbl.setRadarNum(radarNum);
	}
	
	public void setTmpRblEnd(Point distanceP)
	{
		m_pTmpRbl.setRblEnd(distanceP);	
	}

	public void setTmpRect(int x, int y, int width, int height, int radarViewNum)
	{
		m_pSimpleRect.setMember(x, y, width, height,radarViewNum);
	}
	
	public void setTmpEllipseXY(int x,int y)
	{
		m_pTmpEllipse.setDistanceXY(x, y);
	}
	
	public void setTmpEllipseWH(int w,int h)
	{
		m_pTmpEllipse.setDistanceWH(w, h);
	}

	public ArrayList<Rbl> getRbls()
	{
		return m_sRbls;
	}

	public ArrayList<Text> getTexts()
	{
		return m_sTexts;
	}

	public RangeRing getRangeRing()
	{
		return m_pRangeRing;
	}
	
	public void addRbl(Rbl rbl)
	{
		m_sRbls.add(rbl);
	}
	

	public void addText(Text text)
	{
		m_sTexts.add(text);
	}
	
	public void deleteRbls()
	{
		m_sRbls.clear();
	}

	public void deleteTexts()
	{
		m_sTexts.clear();
	}
	
	public void clearTmpRbl()
	{
		setTmpRblBegin(new Point(-1,-1),99);
	}
	
	public void clearTmpLine()
	{
		m_pTmpLine.clear();
		m_pTmpLine.setTypeAndWidth(1,0);
	}

	public void clearTmpRect()
	{
		setTmpRect(-1, -1, 0, 0, 0);
		setTmpRect(-1, -1, 0, 0, 1);
	}
	
	public void clearTmpEllipse()
	{
		setTmpEllipseXY(-1, -1);
		setTmpEllipseWH(-1, -1);
		m_pTmpEllipse.setBeginAngle(-361);
		m_pTmpEllipse.setBeginAngle(-361);
		m_pTmpEllipse.setTypeAndWidth(1,0);
	}
	
	public void clearTmpAirspace()
	{
		m_pTmpAirspace.setName("TmpArea");
		m_pTmpAirspace.setLowHeight(0);
		m_pTmpAirspace.setHighHeight(30000);
		m_pTmpAirspace.setPolygon(new Polygon());
	}
	
	public void clearTmpPolygon()
	{
		m_pTmpPolygon.clear();
		m_pTmpPolygon.setTypeAndWidth(1,1);
	}
	
	
	//------------------------------------------------------------------------

	public static Point fromDistanceToScreen(Point distanceP,int radarViewNum)
	{
		RadarViewInfo info = App.getApp().getSysInfo().getRadarViewInfo(radarViewNum);
		
		double xscale = info.getXScale();
		double yscale = info.getYScale();
		int offx = info.getOffScreenX();
		int offy = info.getOffScreenY();
		int x = (info.getScreenSize().width / 2) + offx
				+ (int) (((double) (distanceP.x)) * xscale);
		int y = (info.getScreenSize().height / 2) + offy
				- (int) (((double) (distanceP.y)) * yscale);
		return (new Point(x, y));
	}

	public static Point fromScreenToDistance(Point screenP,int radarViewNum)
	{
		RadarViewInfo info = App.getApp().getSysInfo().getRadarViewInfo(radarViewNum);
		
		double xscale = info.getXScale();
		double yscale = info.getYScale();
		int offx = info.getOffScreenX();
		int offy = info.getOffScreenY();
		double x = ((double) (screenP.x
				- (info.getScreenSize().width / 2) - offx))
				/ xscale;
		double y = ((double) ((info.getScreenSize().height / 2)
				+ offy - screenP.y))
				/ yscale;
		
		return (new Point((int)x, (int)y));
	}
	
	public static Point fromRealToMagnetDistance(Point distanceP)
	{
		double r = Math.hypot(distanceP.x, distanceP.y);
		double angle = Math.atan2(distanceP.y, distanceP.x);
		angle = angle-App.getApp().getSysInfo().getMagnetAngle()*Math.PI/180.0;
		return new Point((int)(r*Math.cos(angle)),(int)(r*Math.sin(angle)));
	}

	public static double computeAngle(Point p1, Point p2)
	{
		double distancex = (double) (p2.x - p1.x);
		double distancey = (double) (p2.y - p1.y);
		if (distancex == 0 && distancey < 0)
			return 180;
		if (distancex == 0 && distancey > 0)
			return 0;
		if (distancex > 0 && distancey == 0)
			return 90;
		if (distancex < 0 && distancey == 0)
			return 270;
		double absangle = Math.atan(Math.abs(distancex) / Math.abs(distancey))
				/ Math.PI * 180;
		if (distancex > 0 && distancey > 0)
			return absangle;
		if (distancex > 0 && distancey < 0)
			return (180 - absangle);
		if (distancex < 0 && distancey < 0)
			return (180 + absangle);
		else
			return (360 - absangle);
	}

	public static Point convertLatLongStringToDistanceP(String str)
	{
		String latDeg = null;
		String latMin = null;
		String latSec = null;

		String longDeg = null;
		String longMin = null;
		String longSec = null;
		
		if(str.matches("[0-9]{4}N[0-9]{5}E"))
		{
			latDeg = str.substring(0, 2);
			latMin = str.substring(2, 4);
			latSec = "0";

			longDeg = str.substring(5, 8);
			longMin = str.substring(8, 10);
			longSec = "0";	
		}
		//332946N1040151E
		else if(str.matches("[0-9]{6}N[0-9]{7}E"))
		{
			latDeg = str.substring(0, 2);
			latMin = str.substring(2, 4);
			latSec = str.substring(4, 6);

			longDeg = str.substring(7, 10);
			longMin = str.substring(10, 12);
			longSec = str.substring(12, 14);	
		}
		else
			return null;
		
		double dLongDeg = Double.parseDouble(longDeg);
		double dLongMin = Double.parseDouble(longMin);
		double dLongSec = Double.parseDouble(longSec);
		
		double dLatDeg = Double.parseDouble(latDeg);
		double dLatMin = Double.parseDouble(latMin);
		double dLatSec = Double.parseDouble(latSec);
		
		double lat = dLatDeg + dLatMin/60.0 + dLatSec/3600.0;
		double lon = dLongDeg + dLongMin/60.0 + dLongSec/3600.0;
	
		Point p = App.getApp().getLatLongData()
			.LatitudeAndLongitudeToDistanceXY(new Point2D.Double(lon, lat));

		double r = Math.hypot(p.x, p.y);
		double angle = Math.atan2(p.y, p.x);
		angle = angle-App.getApp().getSysInfo().getMagnetAngle()*Math.PI/180.0;
		Point newP = new Point((int)(r*Math.cos(angle)),(int)(r*Math.sin(angle)));
		
		return newP;

	}

	public static String convertDistancePToLatLongString(Point distanceP,int type) 
	//0:355010N1163010E; 1:35 50 10N 116 30 10E;
	{
		double r = Math.hypot(distanceP.x, distanceP.y);
		double angle = Math.atan2(distanceP.y, distanceP.x);
		angle = angle+App.getApp().getSysInfo().getMagnetAngle()*Math.PI/180.0;
		Point newP = new Point((int)(r*Math.cos(angle)),(int)(r*Math.sin(angle)));
		
		Point2D.Double latlong = App.getApp().getLatLongData()
				.XYToLatitudeAndLongitude(newP);
		
		double lat = latlong.y;
		double lon = latlong.x;
		
		int latD = (int)lat;
		int latM = (int)((lat-latD)*60.0);
		double latS = ((lat-latD)*60.0-latM)*60.0;
		int lonD = (int)lon;
		int lonM = (int)((lon-lonD)*60.0);
		double lonS = ((lon-lonD)*60.0-lonM)*60.0;
		

		String longDeg = String.format("%03d", lonD);
		String longMin = String.format("%02d", lonM);
		String longSec = String.format("%02d", (int)lonS);
		
		String latDeg = String.format("%02d", latD);
		String latMin = String.format("%02d", latM);
		String latSec = String.format("%02d", (int)latS);

		String result = null;
		if(type == 1)
			result = ""+latDeg+" "+latMin+" "+latSec+"N "+longDeg+" "+longMin+" "+longSec+"E";
		else
			result = ""+latDeg+latMin+latSec+"N"+longDeg+longMin+longSec+"E";
		return result;
	}
}
