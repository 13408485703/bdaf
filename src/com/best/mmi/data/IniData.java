package com.best.mmi.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class IniData
{
	private HashMap<String,HashMap<String,String>> m_cSysParas;
	private HashMap<String,HashMap<String,String>> m_cIlsParas;
	
	public IniData()
	{
		m_cSysParas = new HashMap<String,HashMap<String,String>>();
		m_cIlsParas = new HashMap<String,HashMap<String,String>>();
		
		init();
	}
	
	public void init()
	{
		initIni("mmi");
		initIni("private");
	}
	
	public void initIni(String iniName)
	{
		try
		{
			String path = "resource//ini//" + iniName + ".ini";
			HashMap<String,HashMap<String,String>> paras = null;
			if(iniName.equals("mmi"))
				paras = m_cSysParas;
			else if(iniName.equals("private"))
				paras = m_cSysParas;
			else
				System.out.println("initIni faild,no this iniName: "+iniName);
			
			InputStreamReader isr = new InputStreamReader(new FileInputStream(path),"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String str = br.readLine();
			String currentFieldName = null;
			while(str!=null)
			{
				String content = str.trim();
				if(content!="")
				{
					
					if(content.startsWith("[") && content.endsWith("]"))
					{
						currentFieldName = content.substring(1, content.length()-1);
						HashMap<String,String> fieldParas = new HashMap<String,String>();
						paras.put(currentFieldName, fieldParas);
					}
					else if(content.indexOf("=") != -1)
					{
						HashMap<String,String> fieldParas = paras.get(currentFieldName);				
						if(fieldParas == null)
						{
							System.out.println("Can't find this para field,pass! "+content);
						}
						else
						{
							int indexEqual = str.indexOf("=");
							String paraName = content.substring(0,indexEqual);
							String para = null;
							if(content.endsWith(";"))
								para = str.trim().substring(indexEqual+1,str.trim().length()-1);
							else
								para = str.trim().substring(indexEqual+1,str.trim().length());
							fieldParas.put(paraName, para);
						}
					}
				}
				str = br.readLine();		
			}
			br.close();
		}
		catch(Exception e) 
		{
			System.out.println("Init "+ iniName + ".ini failed");
			e.printStackTrace();
		}	
	}
	
	public String getSysPara(String fieldName,String paraName)
	{
		HashMap<String,String> fieldParas = m_cSysParas.get(fieldName);
		if(fieldParas == null)
			return null;
		else
			return fieldParas.get(paraName);
	}
	
	public HashMap<String,String> getFieldParas(String fieldName)
	{
		return m_cSysParas.get(fieldName);
	}
	
	public void setSysPara(String fieldName,String paraName,String paraValue)
	{
		HashMap<String,String> fieldParas = m_cSysParas.get(fieldName);
		fieldParas.put(paraName, paraValue);
	}

	public HashMap<String,HashMap<String,String>> getIlsParas() {return m_cIlsParas;}
}
