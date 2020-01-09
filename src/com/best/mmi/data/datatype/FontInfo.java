package com.best.mmi.data.datatype;

import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FontInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public HashMap<String,Font> m_sSysFonts;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
	
	public FontInfo(HashMap<String,Font> fonts)
	{
		m_sSysFonts = fonts;
	}
	
	public HashMap<String,Font> getSysFonts()
	{
		return m_sSysFonts;
	}
	
	public void printSysFonts()
	{
		ArrayList<String> a = new ArrayList<String>(m_sSysFonts.keySet());
		for(int i=0;i<m_sSysFonts.size();i++)
		{
			String key = a.get(i);
			System.out.println(key+" "+(m_sSysFonts.get(key)).toString());
		}	
	}
}