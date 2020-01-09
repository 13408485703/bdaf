package com.best.bdaf.data;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.osgeo.proj4j.Projection;
import org.osgeo.proj4j.ProjectionFactory;

public class LatLongData 
{
   private static String[] proj4_w = new String[] {
			 "+proj=tmerc",
			 "+ellps=WGS84",
			 "+lon_0=108.92361111111111",
			 "+lat_0=34.54083333333333",
			 "+units=m",
			 "+no_defs"
	};
	private static Projection proj = ProjectionFactory.fromPROJ4Specification(proj4_w);  
   
	
	public static Point latlongToDistancep(Point2D.Double latlon)
	{
		double lat = latlon.y;
		double lon = latlon.x;
		Point2D.Double pll = new Point2D.Double(lon, lat);
		Point2D.Double pxy = proj.transform(pll, new Point2D.Double());
		
		return (new Point((int)Math.round(pxy.x), (int)Math.round(pxy.y)));  
	}
	
	public static Point2D.Double distancepToLatlong(Point XY)
	{
		Point2D.Double pxy = new Point2D.Double(XY.x, XY.y);
		Point2D.Double pll = proj.inverseTransform(pxy, new Point2D.Double());

		return pll;
	}
	
	public static Point latlongstringToDistancep(String str)
	{
		String latDeg = null;
		String latMin = null;

		String longDeg = null;
		String longMin = null;
		
		//4455N12509E
		if(str.matches("[0-9]{4}N[0-9]{5}E"))
		{
			latDeg = str.substring(0, 2);
			latMin = str.substring(2, 4);
			
			longDeg = str.substring(5, 8);
			longMin = str.substring(8, 10);
		}
		else
			return null;
		
		double dLongDeg = Double.parseDouble(longDeg);
		double dLongMin = Double.parseDouble(longMin);
		
		double dLatDeg = Double.parseDouble(latDeg);
		double dLatMin = Double.parseDouble(latMin);
		
		double lat = dLatDeg + dLatMin/60.0;
		double lon = dLongDeg + dLongMin/60.0;
	
		Point p = latlongToDistancep(new Point2D.Double(lon, lat));

		return p;
	}

	public static String distancepToLatlongstring(Point distanceP) 
	//4455N12509E
	{
		Point2D.Double latlong = distancepToLatlong(distanceP);
		
		double lat = latlong.y;
		double lon = latlong.x;
		
		int latD = (int)lat;
		int latM = (int)((lat-latD)*60.0);
		int lonD = (int)lon;
		int lonM = (int)((lon-lonD)*60.0);
		

		String longDeg = String.format("%03d", lonD);
		String longMin = String.format("%02d", lonM);
		
		String latDeg = String.format("%02d", latD);
		String latMin = String.format("%02d", latM);

		String result = ""+latDeg+latMin+"N"+longDeg+longMin+"E";
	
		return result;
	}
}
	