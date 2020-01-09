package com.best.mmi.data;

import java.awt.Point;
import java.util.ArrayList;

import com.best.mmi.data.datatype.RouteFix;

public class SkpPoUtils {
	
	public static void updateSimTrack(SkpPo sp, int period, int time)
	{	
		if(time < sp.getSkp().getAat())
		{
			sp.realNextRouteNum = -99;
			return;
		}
		
		if(sp.endAat != 0)
		{
			if(time > sp.endAat)
			{
				sp.realNextRouteNum = 99;
				return;
			}
		}
		
		ArrayList<RouteFix> rfs = sp.getRFs();
		Point nowp = sp.currentPos;
		int speed = sp.getSkp().getTas_m();

		if(period >= 0)
		{
			for(int j=(rfs.size()-1);j>=0;j--) 
			{
				Point currentFix = rfs.get(j).getDistanceP();
				double distance = Math.hypot(currentFix.x-nowp.x, 
					currentFix.y-nowp.y);
				if(distance < (period*2*speed*1000.0/3600.0))
				{
					sp.nextRouteNum = j+1;				
					sp.realNextRouteNum = j+1;
					break;
				}		
			}	
		}
		else
		{
			for(int j=0;j<rfs.size();j++) 
			{
				Point currentFix = rfs.get(j).getDistanceP();
				double distance = Math.hypot(currentFix.x-nowp.x, 
					currentFix.y-nowp.y);
				if(distance < (Math.abs(period)*2*speed*1000.0/3600.0))
				{
					sp.nextRouteNum = j-1;
					sp.realNextRouteNum = j+1;
					break;
				}		
			}	
		}
		
		if(sp.nextRouteNum>(rfs.size()-1) && sp.endAat==0) //第一次走到航路尽头，记下时间
			sp.endAat = time;
		
		if(sp.nextRouteNum<0 || sp.nextRouteNum>(rfs.size()-1)) //走到头或尾了
			return;
		
		computeCurrentSpeedAngle(sp);

		double speedAngle = Math.PI/2 - sp.currentAngle;
		double smallDistance = ((double)speed*1000*Math.abs(period))/3600;
		Point newDistanceP = new Point();
		newDistanceP.x = (int)(smallDistance*Math.cos(speedAngle)) + nowp.x;
		newDistanceP.y = (int)(smallDistance*Math.sin(speedAngle)) + nowp.y;
		
		sp.currentPos = newDistanceP;
	}
	
	public static void computeCurrentSpeedAngle(SkpPo sp)
	{
		int nextRouteNum = sp.nextRouteNum;
		Point nextFix = sp.getRFs().get(nextRouteNum).getDistanceP();
		sp.currentAngle = computeAngle(sp.currentPos, nextFix);	
		
		int realNextRouteNum = sp.realNextRouteNum;
		if(realNextRouteNum>=0 && realNextRouteNum<sp.getRFs().size()) //在航路中时，计算真实角度
		{
			Point realNextFix = sp.getRFs().get(realNextRouteNum).getDistanceP();	
			sp.realCurrentAngle = computeAngle(sp.currentPos, realNextFix);	
		}	
	}
	
	public static double computeAngle(Point PointEnd,Point PointBegin)
	{
		double dy = PointBegin.y-PointEnd.y;
		double dx = PointBegin.x-PointEnd.x;
		double speedAngle = 0;
		if(dx == 0)
		{
			if(dy>=0)
				speedAngle = 0;
			else
				speedAngle = Math.PI;
		}
		else
		{
			speedAngle = Math.atan(dy/dx);
			if(speedAngle>0)
			{
				if(dy>=0)
					speedAngle = Math.PI/2 - speedAngle;
				else
					speedAngle = Math.PI*3/2 - speedAngle;
			}
			else
			{
				if(dy>=0)
					speedAngle = Math.PI*3/2 - speedAngle;
				else
					speedAngle = Math.PI/2 - speedAngle;
			}
		}
		return speedAngle;
	}
}
