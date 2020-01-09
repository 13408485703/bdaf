package com.best.bdaf.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class NavData 
{
	private ArrayList<String> sectornames = new ArrayList<String>();
	private HashMap<String,String> fixnames = new HashMap<String,String>();
	private HashMap<String,Integer> actypes = new HashMap<String, Integer>();
	private HashMap<String, String> eets = new HashMap<String, String>();
	
	public void init()
	{	
		try{
			File file = new File("resource//offl//CHARACTERISTIC_POINTS.ASF");
			String strbegin = "/DEFINITIONS/";
			String strend = null;
			String strtypedummy = "DUMMY";
			//DUMMY类型的点都当做未定义的点，不允识别
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			boolean isfixbegin = false;
			while((line=br.readLine()) != null)
			{
				String[] strs = line.split("\\|");
				if(strs.length >= 8)
				{
					String fixname = strs[0].trim();
					String fixpos = strs[1].trim();
					String fixtype = strs[2].trim();
					if(!fixtype.equals(strtypedummy) && !fixname.startsWith("--"))
					{
						fixnames.put(fixname, fixpos);
//						System.out.println(fixname);
					}
				}	
			}
			br.close();	
			
			file = new File("resource//offl//CUE_POINTS.ASF");
			strbegin = "/CUE_POINTS/";
			strend = null;
			
			br = new BufferedReader(new FileReader(file));
			line = null;
			isfixbegin = false;
			while((line=br.readLine()) != null)
			{
				if(line.equals(strbegin))
				{				
					isfixbegin = true;
					for(int i=0;i<(4+1);i++)
						line = br.readLine();
				}
				if(isfixbegin == true)
				{
					String[] strs = line.split("\\|");
					
					if(strs.length >= 3)
					{
						String fixname = strs[0].trim();
						String fixpos = strs[1].trim();		
						if(!fixname.startsWith("--"))
						{
							fixnames.put(fixname, fixpos);
//							System.out.println(fixname);
						}
					}	
				}	
			}
			br.close();	
			
			file = new File("resource//offl//FUNCTIONAL_SECTOR_VOLUME_LINK.ASF");		
			br = new BufferedReader(new FileReader(file));
			line = null;
			while((line=br.readLine()) != null)
			{
				String[] strs = line.split("\\|");
				if(strs.length==4 || strs.length==5)
				{
					String sectorname = strs[2].replace(" ", "");
					if(!sectorname.equals(""))
						sectornames.add(sectorname);
				}	
			}
			br.close();	
			
			file = new File("resource//offl//AIRCRAFT_PERFORMANCES.ASF");		
			br = new BufferedReader(new FileReader(file));
			line = null;
			strbegin = "/";
			strend = "*";
			String strSpeedBegin = "--      max speed";
			boolean aircraftBegin = false;
			ArrayList<String> aircrafts = null;
			while((line=br.readLine()) != null)
			{
				line = line.trim();
				if(line.startsWith(strbegin) && line.endsWith(strbegin))
				{
					aircraftBegin = true;
					aircrafts = new ArrayList<String>();
				}
				else if(line.equals(strend))
					aircraftBegin = false;
				else
				{
					if(aircraftBegin == true)
					{
						String[] strs = line.split(" {1,}");
						for(String str : strs)
						{
							str = str.trim();
							if(str.equals(""))
								continue;
							aircrafts.add(str);
						}
					}
					else 
					{
						if(line.startsWith(strSpeedBegin))
						{
							line = br.readLine();
							line = br.readLine();
							
							String[] strs = line.split("\\|");
							String strspeed = strs[0].trim();
							int speed = ConstantPara.n_default_speed;
							if(strspeed.startsWith("N"))
								speed = (int)(Integer.parseInt(strspeed.substring(1))*1.8523);
							else if(strspeed.startsWith("M"))
								speed = Integer.parseInt(strspeed.substring(1));		
							for(String str : aircrafts)
							{
								actypes.put(str, speed);
//								System.out.println(str+" "+speed);
							}
						}
					}	
				}
			}
			br.close();	
			
			file = new File("resource//offl//eet.csv");		
			br = new BufferedReader(new FileReader(file));
			line = null;
			while((line = br.readLine()) != null)
			{
				String[] strs = line.split(",");
				eets.put(strs[0]+strs[1]+strs[2], strs[3]);
			}
			br.close();
		}
		catch (Exception e) {
			AppLogger.info("NavData init failed!");
			AppLogger.error(e);;
		}	
	}

	public ArrayList<String> getSectornames() {
		return sectornames;
	}

	public HashMap<String,String> getFixnames() {
		return fixnames;
	}
	
	public HashMap<String,Integer> getActypes() {
		return actypes;
	}
	
	public HashMap<String,String> getEets() {
		return eets;
	}

	public String getLowPrecisionFromHigh(String latlon)
	{
		StringBuffer sb = new StringBuffer(latlon);
		sb.delete(4, 6).delete(10, 12);
		return sb.toString();
	}

}
