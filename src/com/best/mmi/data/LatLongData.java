package com.best.mmi.data;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.osgeo.proj4j.Projection;
import org.osgeo.proj4j.ProjectionFactory;

public class LatLongData 
{
    String[] proj4_w = new String[] {
		 "+proj=tmerc",
		 "+ellps=WGS84",
		 "+lon_0=108.92361111111111",
		 "+lat_0=34.54083333333333",
		 "+units=m",
		 "+no_defs"
	};
    
    Projection proj; 
    
    
    public void init(IniData id)
    {
    	double centerLatitude = Double.parseDouble(id.getSysPara("Center", "Latitude"));		
		double centerLongitude = Double.parseDouble(id.getSysPara("Center", "Longitude"));
		for(int i=0;i<proj4_w.length;i++)
		{
			if(proj4_w[i].startsWith("+lon_0"))
				proj4_w[i] = "+lon_0="+centerLongitude;
			else if(proj4_w[i].startsWith("+lat_0"))
				proj4_w[i] = "+lat_0="+centerLatitude;		
		}
		proj = ProjectionFactory.fromPROJ4Specification(proj4_w);
    }
	
	public Point LatitudeAndLongitudeToDistanceXY(Point2D.Double latlon)
	{
		double lat = latlon.y;
		double lon = latlon.x;
		Point2D.Double pll = new Point2D.Double(lon, lat);
		Point2D.Double pxy = proj.transform(pll, new Point2D.Double());
		
		return (new Point((int)Math.round(pxy.x), (int)Math.round(pxy.y)));  
	}
	
	public Point2D.Double XYToLatitudeAndLongitude(Point XY)
	{
		Point2D.Double pxy = new Point2D.Double(XY.x, XY.y);
		Point2D.Double pll = proj.inverseTransform(pxy, new Point2D.Double());

		return pll;
	}
}
	