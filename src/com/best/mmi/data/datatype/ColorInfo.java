package com.best.mmi.data.datatype;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ColorInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public HashMap<String,Color> m_sSysColors;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
	
	public ColorInfo(HashMap<String,Color> colors)
	{
		m_sSysColors = colors;
	}
	
	public HashMap<String,Color> getSysColors()
	{
		return m_sSysColors;
	}
	
	public void printSysColors() 
	{
		ArrayList<String> a = new ArrayList<String>(m_sSysColors.keySet());
		for(int i=0;i<m_sSysColors.size();i++)
		{
			String key = a.get(i);
			System.out.println(key+" "+(m_sSysColors.get(key)).toString());
		}	
	}
}