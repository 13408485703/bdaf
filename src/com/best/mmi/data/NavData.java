package com.best.mmi.data;

import java.util.ArrayList;

import com.best.mmi.data.maptype.FixPoint;

public class NavData {

	private ArrayList<FixPoint> m_sFixs;
	
	public NavData()
	{
		m_sFixs = new ArrayList<FixPoint>();
	}
	
	public void init()
	{
		
	}
	
	public FixPoint getFixPoint(String name)
	{
		for(FixPoint fp : m_sFixs)
		{
			if(fp.getName().equals(name))
				return fp;
		}
		return null;
	}
	
	public ArrayList<FixPoint> getFixpoints()
	{
		return m_sFixs;
	}
	
}
