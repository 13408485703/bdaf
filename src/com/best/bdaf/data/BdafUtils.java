package com.best.bdaf.data;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import sun.java2d.pipe.AATileGenerator;

import com.best.bdaf.dao.ComFplDB;
import com.best.bdaf.dao.LocalFlightPlanDB;
import com.best.bdaf.dao.LocalFlightPlanDB.E_exit_protocol;
import com.best.bdaf.dao.LocalFlightPlanDB.E_flight_category;
import com.best.bdaf.dao.LocalFlightPlanDB.E_flight_origin;
import com.best.bdaf.dao.LocalFlightPlanDB.E_flight_rule;
import com.best.bdaf.dao.LocalFlightPlanDB.E_flight_type;
import com.best.bdaf.dao.LocalFlightPlanDB.E_fpl_status;
import com.best.bdaf.dao.LocalFlightPlanDB.E_level_type;
import com.best.bdaf.dao.LocalFlightPlanDB.E_movement_category;
import com.best.bdaf.dao.LocalFlightPlanDB.E_wake_turbulence;
import com.best.bdaf.dao.LocalFlightPlanFixesDB;
import com.best.bdaf.dao.LocalFlightPlanFixesDB.E_fix_kind;
import com.best.bdaf.dao.Skp;
import com.best.bdaf.dao.Skp.FPL_MES_TYPE_T;
import com.best.bdaf.dao.Skp.GENERIC_UNIT_SETTING_TYPE;
import com.best.bdaf.dao.Skp.GROUP_TYPE;
import com.best.bdaf.dao.Skp.LEVEL_TYPE_T;
import com.best.bdaf.dao.Skp.SPEED_TYPE_T;
import com.best.bdaf.dao.Skp.SSR_MODE;
import com.best.bdaf.main.App;

public class BdafUtils {
	
	public static int pool_fpid = 1;
	public static int pool_fixid = 1;
	public static int pageid = 1;
	public static ArrayList<ComFplDB> cfds = new ArrayList<ComFplDB>(2000);
	private static SimpleDateFormat sdf_dof = new SimpleDateFormat("yyMMdd");
	private static final int maxskpsum = 500;
	
	//数据中无法解析出记录时间戳recordtime，因此是从外部传入的
	public static ComFplDB getComFplDBFromBytes(byte[] src, Date recordtime)
	{
		LocalFlightPlanDB lfp = getLocalFlightPlanDBFromBytes(src, recordtime);
		if(lfp == null)
			return null;
		
		ArrayList<LocalFlightPlanFixesDB> lfpfs = getLocalFlightPlanFixesDBsFromBytes(src, lfp);
	
		ComFplDB cfd = new ComFplDB(lfp, lfpfs);
		return cfd;
	}
	
	//数据中无法解析出记录时间戳recordtime，因此是从外部传入的
	public static LocalFlightPlanDB getLocalFlightPlanDBFromBytes(byte[] src, Date recordtime)
	{
		LocalFlightPlanDB lfp = new LocalFlightPlanDB();
		
		byte[] byte2 = new byte[2];
		byte[] byte4 = new byte[4];
		byte[] byte12 = new byte[12];
		
		String callsign = new String(src,2,10);
		//callsign
		lfp.setCallsign(callsign);
		
		//recordtime
		lfp.setRecord_time(recordtime);
		
		//adep
		String adep = new String(src,28,4).trim();
		lfp.setAdep(adep);
		
		//ades
		String ades = new String(src,32,4).trim();
		lfp.setAdes(ades);
		
		//aircraft_type
		String aircraft_type = new String(src,22,4).trim();
		lfp.setAircraft_type(aircraft_type);
		
		//aircraft_registration
		String aircraft_registration = new String(src,12,10).trim();
		lfp.setAircraft_registration(aircraft_registration);
		
		//flight_rules
		int n = new Integer(src[26]&0xf0 >> 4).byteValue();	
		//保护越界
		if(n >= E_flight_rule.values().length)
			n = 0;
		lfp.setFlight_rule(E_flight_rule.values()[n].toString());
		
		//flight_types
		n = new Integer(src[26]&0x0f).byteValue();
		if(n >= E_flight_type.values().length)
			n = 0;
		lfp.setFlight_type(E_flight_type.values()[n].toString());
		
		//flight_category
		n = new Integer(src[27]&0x0f).byteValue();
		lfp.setFlight_category(E_flight_category.values()[n].toString());
		
		//movement_category
		n = new Integer(src[82]&0x0f).byteValue();
		lfp.setMovement_category(E_movement_category.values()[n].toString());
		
		//dof
		String dof = new String(src,6561,6).trim();
		lfp.setDof(dof);
		
		//entry_point
		String entry_point = new String(src,60,11).trim();
		lfp.setEntry_point(entry_point);
		
		//exit_point
		String exit_point = new String(src,71,11).trim();
		lfp.setExit_point(exit_point);
		
		//movement_date
		System.arraycopy(src, 36, byte12, 0, 12);
		n = BestUtils.byteToInt(byte12, false);
		lfp.setMovement_date(new Date(1000*((long)n)));
		
		//exit_hour
		System.arraycopy(src, 48, byte4, 0, 4);
		n = BestUtils.byteToInt(byte4, false);
		lfp.setExit_hour(new Date(1000*((long)n)));
		
		//entry_hour
		System.arraycopy(src, 52, byte4, 0, 4);
		n = BestUtils.byteToInt(byte4, false);
		lfp.setEntry_hour(new Date(1000*((long)n)));
		
		//distance_flown
		System.arraycopy(src, 56, byte4, 0, 4);
		n = BestUtils.byteToInt(byte4, false);
		lfp.setDistance_flown_in_fir(n);
		
		//sent_to_fdo
		n = src[83];
		if(n != 0)
			lfp.setSent_to_fdo(true);
		else
			lfp.setSent_to_fdo(false);
		
		//storage_delay
		System.arraycopy(src, 84, byte12, 0, 12);
		n = BestUtils.byteToInt(byte12, false);
		lfp.setStorage_delay(n);
		
		//flight_origin
		n = new Integer(src[8509]&0xe0 >> 5).byteValue();
		lfp.setFlight_origin(E_flight_origin.values()[n].toString());
		
		//creation_time
		System.arraycopy(src, 8497, byte12, 0, 12);
		n = BestUtils.byteToInt(byte12, false);
		lfp.setCreation_time(new Date(1000*((long)n)));
		
		//atd
		System.arraycopy(src, 8527, byte4, 0, 4);
		n = BestUtils.byteToInt(byte4, false);
		lfp.setAtd(new Date(1000*((long)n)));
		
		//ata
		System.arraycopy(src, 8535, byte4, 0, 4);
		n = BestUtils.byteToInt(byte4, false);
		lfp.setAta(new Date(1000*((long)n)));
		
		//etd
		System.arraycopy(src, 8517, byte4, 0, 4);
		n = BestUtils.byteToInt(byte4, false);
		lfp.setEtd(new Date(1000*((long)n)));
		
		//eta
		System.arraycopy(src, 8531, byte4, 0, 4);
		n = BestUtils.byteToInt(byte4, false);
		lfp.setEta(new Date(1000*((long)n)));
		
		//route_field
		String route_field = new String(src, 8608, 156).trim();
		lfp.setRoute_field(route_field);
		
		//assr_code
		System.arraycopy(src, 8514, byte2, 0, 2);
		String assr_code = BestUtils.bytesToSsr(byte2, false);
		lfp.setAssr_code(Integer.parseInt(assr_code));
		//System.out.println(lfp.getAssr_code());
		
		//pssr_code
		System.arraycopy(src, 8892, byte2, 0, 2);
		String pssr_code = BestUtils.bytesToSsr(byte2, false);	
		lfp.setPssr_code(Integer.parseInt(pssr_code));
		//System.out.println(lfp.getPssr_code());
		
		//exit_protocol
		n = new Integer(src[8575]&0x07).byteValue();
		lfp.setExit_protocol(E_exit_protocol.values()[n].toString());
		
		//wtc
		n = new Integer(src[8516]&0x07).byteValue();
		lfp.setWtc(E_wake_turbulence.values()[n].toString());
		
		//field10a
		lfp.setField10a(new String(src, 8820, 50).trim());
		
		//field10b
		lfp.setField10b(new String(src, 8870, 20).trim());
		
		//fpl_status  应该是1e的 1..4
		n = new Integer(src[8914]&0x1e >> 1).byteValue();
		if(n >= E_fpl_status.values().length)
			n = 0;
		lfp.setFpl_status(E_fpl_status.values()[n].toString());
		
		//eobt
		System.arraycopy(src, 8778, byte4, 0, 4);
		n = BestUtils.byteToInt(byte4, false);
		lfp.setEobt(new Date(1000*((long)n)));

		
		//aircraft_operator_name
		lfp.setAircraft_operator_name(new String(src, 8935, 50).trim());
		
		//fix_list_length
		n = src[96];
		lfp.setFix_list_length(n);
		
		
		//------以下为不确定的部分---------
		
		//fpl_id 自分配
		lfp.setFpl_id(pool_fpid);
		pool_fpid++;
		
		//flight_plan_number
		System.arraycopy(src, 0, byte2, 0, 2);
		n = BestUtils.byte2ToInt_Low(byte2);
		lfp.setFlight_plan_number(n);
		
		//eet  无数据，填写0200
		Date ts = new Date(System.currentTimeMillis());
		ts.setHours(2);
		ts.setMinutes(0);
		ts.setSeconds(0);
		lfp.setEet(ts);

		//cfl_approach_mode 
		lfp.setCfl_approach_mode("cfl_approach_mode");
		
		//last_fix_overflown --Index of last fix overflown in the fix list 默认计划都为完成状态，都已过点
		lfp.setLast_fix_overflown(lfp.getFix_list_length()-1);
		
		//dcc_identifier;
		lfp.setDcc_identifier("dcc_identifier");
		
		
		//aircraft_address
		System.arraycopy(src, 9018, byte4, 0, 4);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<4;i++)
			sb.append(String.format("%02X", byte4[i]));
		lfp.setAircraft_address(sb.toString().substring(0, 6));
			
		
		//rfl
		System.arraycopy(src, 8543, byte2, 0, 2);
		int rfl_value = BestUtils.byte2ToInt_Low(byte2);	
		n = new Integer(src[8913]&0x07).byteValue();
		String rfl_unit = E_level_type.values()[n].toString();		
		lfp.setRfl((float)rfl_value);

	
		//ufl
		System.arraycopy(src, 8512, byte2, 0, 2);
		int ufl_value = BestUtils.byte2ToInt_Low(byte2);
		lfp.setUfl((float)ufl_value);
		
		
		//cfl
		System.arraycopy(src, 8510, byte2, 0, 2);
		int cfl_value = BestUtils.byte2ToInt_Low(byte2);	
		n = new Integer(src[8509]&0x1c >> 2).byteValue();
		String cfl_unit = E_level_type.values()[n].toString();
		lfp.setCfl((float)cfl_value);
		
		
		//println
		//System.out.println(lfp);
		
		return lfp;
	}
	
	
	public static ArrayList<LocalFlightPlanFixesDB> getLocalFlightPlanFixesDBsFromBytes(byte[] src, LocalFlightPlanDB lfp)
	{
		ArrayList<LocalFlightPlanFixesDB> lfpfs = new ArrayList<LocalFlightPlanFixesDB>();
		int index = 97;
		byte[] byte4 = new byte[4];
		byte[] byte2 = new byte[2];
		LocalFlightPlanFixesDB lastLfpf = null;
		LocalFlightPlanFixesDB currentLfpf = null;
		
		for(int i=0;i<lfp.getFix_list_length();i++)
		{
			lastLfpf = currentLfpf;
			currentLfpf = new LocalFlightPlanFixesDB();
					
			//recordtime
			currentLfpf.setRecord_time(lfp.getRecord_time());
			
			//fpl_id
			currentLfpf.setFpl_id(lfp.getFpl_id());
			
			//fix_kind
			int n = src[19+index];
			currentLfpf.setFix_kind(E_fix_kind.values()[n].toString());
			
			//fix_name
			String str = new String(src, 8+index, 11).trim();
			currentLfpf.setFix_name(str);
			
			//current_functional_sector
			str = new String(src, 35+index, 5).trim();
			currentLfpf.setCurrent_functional_sector(str);
			
			//next_functional_sector
			str = new String(src, 40+index, 5).trim();
			currentLfpf.setNext_functional_sector(str);
			
			//flight_rule_after_fix
			n = src[28+index];
			currentLfpf.setFlight_rule_after_fix(E_flight_rule.values()[n].toString());
			
			//current_sector
			str = new String(src, 29+index, 6).trim();
			currentLfpf.setCurrent_sector(str);
			
			//reference_eto
			System.arraycopy(src, 20+index, byte4, 0, 4);
			n = BestUtils.byteToInt(byte4, false);
			currentLfpf.setReference_eto(new Date(1000*((long)n)));
			
			//displayed_eto
			System.arraycopy(src, 24+index, byte4, 0, 4);
			n = BestUtils.byteToInt(byte4, false);
			currentLfpf.setDisplayed_eto(new Date(1000*((long)n)));
			
			
			//------以下为不确定的部分---------
			
			//fix_id 自分配
			currentLfpf.setFix_id(pool_fixid);
			pool_fixid++;
			
			//fix_index
			currentLfpf.setFix_index(i);
			
			
			//geoposition
			System.arraycopy(src, 0+index, byte4, 0, 4);
			float lat = BestUtils.byteToFloat(byte4, false);
			System.arraycopy(src, 4+index, byte4, 0, 4);
			float lon = BestUtils.byteToFloat(byte4, false);
			
			int latHour = (int)lat;
			int latMin = ((int)(lat*60.0))%60;
			int lonHour = (int)lon;
			int lonMin = ((int)(lon*60.0))%60;
			String geoposition = String.format("%1$02d%2$02dN%3$02d%4$02dE", latHour,latMin,lonHour,lonMin);
			currentLfpf.setGeoposition(geoposition);
			
			//distance_from_previous_fix
			System.arraycopy(src, 52+index, byte4, 0, 4);
			n = BestUtils.byte4ToInt_Low(byte4);
			currentLfpf.setDistance_from_previous_fix((float)n);
			
			//actual_flight_level
			System.arraycopy(src, 45+index, byte2, 0, 2);
			n = BestUtils.byte2ToInt_Low(byte2);
			currentLfpf.setActual_flight_level((float)n);
			
			//pre_cleared_flight_level
			System.arraycopy(src, 47+index, byte2, 0, 2);
			n = BestUtils.byte2ToInt_Low(byte2);
			currentLfpf.setPre_cleared_flight_level((float)n);
			
			//associated_flight_plan
			currentLfpf.setAssociated_flight_plan(lfp.getFpl_id().intValue());
			
			//fix_name_and_pos
			currentLfpf.setFix_name_and_pos(currentLfpf.getFix_name()+" "+currentLfpf.getGeoposition());
			
			//sector_changed --Flag if the CURRENT_SECTOR field has changed value from that of the previous fix
			Boolean changed = false;
			if(lastLfpf != null)
				changed = !(currentLfpf.getCurrent_sector().equals(lastLfpf.getCurrent_sector()));
			currentLfpf.setSector_changed(changed.toString());
					
			
			//print
			//System.out.println(currentLfpf);
			
			lfpfs.add(currentLfpf);
			
			//每个FIX为56字节长度
			index = index + 56;
		}
			
		return lfpfs;
	}
	

	
	public static void getComFplDBsFromFile(File file, boolean isDirect2Excel) throws Exception
	{		
		ArrayList<ComFplDB> localCfds = new ArrayList<ComFplDB>(64);
		
		byte[] byte2 = new byte[2];
		byte[] byte12 = new byte[12];
		byte[] byte4 = new byte[4];
		byte[] bufheader = new byte[124];
		byte[] bufdata = new byte[51200];
		byte[] buftmp = new byte[512000];
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int readlength = -1;
			
		//读取每个数据总长度，头+数据
		while((readlength = bis.read(byte2, 0, byte2.length)) == byte2.length)
		{
			int alllength = BestUtils.byte2ToInt_Low(byte2);
			readlength = bis.read(byte2, 0, byte2.length);
			
			//读取每个数据头
			readlength = bis.read(bufheader, 0, bufheader.length);
			String fifoname = new String(bufheader,14,50).trim();
			
			//得到记录时间戳
			System.arraycopy(bufheader, 0, byte12, 0, 12);
			int n = BestUtils.byteToInt(byte12, false);
			Date date = new Date(1000*((long)n));
			
			//得到数据长度
			System.arraycopy(bufheader, 116, byte4, 0, 4);
			int datalength = BestUtils.byte4ToInt_Low(byte4);
		
			
			//通过数据长度读取数据
			readlength = bis.read(bufdata, 0, datalength);
			
			
			//判断数据类型加以处理
			if(fifoname.equals("FLIGHT_PLAN_T"))
			{				
				ComFplDB cfd = getComFplDBFromBytes(bufdata, date);
				if(isDirect2Excel)
					cfds.add(cfd);
				else
					localCfds.add(cfd);
			}	
			else if(fifoname.equals("DAF_ICAO_MSGS_T"))
			{
				
				
			}
			else if(fifoname.equals("DAF_ATN_ICAO_MSGS_T"))
			{
				
				
			}
			else if(fifoname.equals("DAF_GRIB_T"))
			{
				
				
			}
			else if(fifoname.equals("DAF_ULAM_ACP_T"))
			{
				
			}
			else if(fifoname.equals("DCC_RECORD_T"))
			{
			
			}
			else if(fifoname.equals("DAF_POSTING_INFO_T"))
			{
				
			}
			else if(fifoname.equals("HANDOFF_INFO_T"))
			{
				
			}
			else if(fifoname.equals("CAPACITY_OVERLOAD_INFO_T"))
			{
				
			}
			else if(fifoname.equals("DAF_DCC_ALERT_T"))
			{
				
			}
			else if(fifoname.equals("DAF_NAS_MSG_T"))
			{
				
			}
			else if(fifoname.equals("DAF_SECTORISATION_T"))
			{
				
			}
			else
			{
				System.out.println("No defined type in rec file!");
				break;
			}
			
			int restlength = alllength-bufheader.length-datalength;
			if(restlength != 0)
				readlength = bis.read(buftmp, 0, alllength-bufheader.length-datalength);
		}
		
		bis.close();
		
		if(!isDirect2Excel)
		{
			//单个文件读取飞行计划后直接存储到数据库	
			AppLogger.info("Begin save new FPLs! "+file.getPath());
			saveComFplDBsToDB(localCfds);
			AppLogger.info("Finish save new FPLS! "+file.getPath());
		}
	}
	
	public static void decompressFilesInFolder(File file) throws Exception
	{
        if (file.exists()) 
        {
            File[] files = file.listFiles();
            if (files==null || files.length==0) 
            	AppLogger.info("Folder is empty! "+file.getPath());
            else {
                for (File file2 : files) 
                {
                    if (file2.isDirectory())
                    	decompressFilesInFolder(file2);
                    else 
                    {
                    	if(file2.getName().endsWith("gz"))
                    	{
                    		String abspath = file2.getAbsolutePath();
                    		File filedata = new File(abspath.substring(0, abspath.length()-3));
                    		//已发现解压文件，无需再次解压
                    		if(filedata.exists())
                    			continue;
                    		
                    		AppLogger.info("Depressing GZ file, please wait: "+file2.getPath());
                    		BestUtils.decompress_gz(file2, false);
                    		AppLogger.info("Finish depress GZ file: "+file2.getPath());
                    	}
                    }
                }
            }
        } 
        else
        	AppLogger.info("Folder isnot exist: "+file.getPath());
    }
	
	
	public static void decodeFilesInFolder(File file, boolean isDirect2Excel) throws Exception
	{
        if (file.exists()) 
        {
            File[] files = file.listFiles();
            if (files.length == 0) 
            	AppLogger.info("Folder is empty! "+file.getPath());
            else {
                for (File file2 : files) 
                {
                    if (file2.isDirectory())
                    	decodeFilesInFolder(file2, isDirect2Excel);
                    else 
                    {
                    	if(file2.getName().endsWith("nrpf") && file2.getName().startsWith("REC_FDP_"))
                    	{
                    		AppLogger.info("Begin convert file: "+file2.getPath());
                    		BdafUtils.getComFplDBsFromFile(file2, isDirect2Excel);
                    		AppLogger.info("Finish conver file: "+file2.getPath());
                    	}
                    }
                }
            }
        } 
        else
        	AppLogger.info("Folder isnot exist! "+file.getPath());
    }
	
	public static void saveComFplDBsToDB() throws Exception
	{
		for(ComFplDB cfd : cfds)
			App.getApp().getDbService().updateComFplDB(cfd, true);
		
	}
	
	public static void saveComFplDBsToDB(ArrayList<ComFplDB> localCfds) throws Exception
	{
		for(ComFplDB cfd : localCfds)
			App.getApp().getDbService().updateComFplDB(cfd, true);
		
	}
	
	//不满足条件的计划返回值为null
	public static Skp getSkpFromFpl(LocalFlightPlanDB lfp, Date time, int durationsecs, 
			ArrayList<String> choosedsectornames, boolean routeFromReality, boolean routeRangeSector) throws Exception
	{
		//用入界时间来判断执行日期
		if(!sdf_dof.format(time).equals(sdf_dof.format(lfp.getEntry_hour())))
			return null;

		//判断是否经过扇区
		//航路串来源于数据库表
		List<LocalFlightPlanFixesDB> lfpfs = App.getApp().getDbService().getLocalFlightPlanFixesDBs(lfp.getFpl_id());
		boolean isSectorRelative = false;
		checksectorrelatived:
		for(LocalFlightPlanFixesDB lfpf : lfpfs)
		{
			for(String choosedsectorname: choosedsectornames)
			{
				if(lfpf.getCurrent_functional_sector().equals(choosedsectorname))
				{
					isSectorRelative = true;
					break checksectorrelatived;
				}
			}	
		}
		if(isSectorRelative == false)
			return null;
		
		//准备离线数据
		HashMap<String, String> offl_fixnames = App.getApp().getNavData().getFixnames();
		HashMap<String, Integer> actypes = App.getApp().getNavData().getActypes();
		
		//尝试校验字段
		//校验机型
		String comboactype = lfp.getAircraft_type()+"/"+lfp.getWtc();
		if(actypes.get(comboactype) == null)
		{
			AppLogger.info(lfp.getCallsign()+" AcType "+comboactype+" undifined, continue!");
			return null;
		}
		
		//校验注册号
		if(lfp.getAircraft_registration().length() == 0)
		{
			AppLogger.info(lfp.getCallsign()+" REG is empty, use callsign!");
			lfp.setAircraft_registration("B"+lfp.getCallsign());
		}
		
		//整理航路
		//去除临近的经纬度重复点,出入界点不删
		ArrayList<LocalFlightPlanFixesDB> removelfpfs = new ArrayList<LocalFlightPlanFixesDB>();
		
		for(int i=0;i<lfpfs.size()-1;i++)
		{
			LocalFlightPlanFixesDB lfpfnow = lfpfs.get(i);
			LocalFlightPlanFixesDB lfpfnext = lfpfs.get(i+1);
			
			if(lfpfnow.getGeoposition().equals(lfpfnext.getGeoposition()))
			{
				E_fix_kind kind = E_fix_kind.valueOf(lfpfnow.getFix_kind());
				if(kind.equals(E_fix_kind.ENT_POINT) || kind.equals(E_fix_kind.EXT_POINT))
					removelfpfs.add(lfpfnext);
				else
					removelfpfs.add(lfpfnow);
			}
		}
		for(LocalFlightPlanFixesDB lfpf: removelfpfs)
			lfpfs.remove(lfpf);
		
		//剔除掉头尾起降机场
		if(lfpfs.size()>0 && lfpfs.get(0).getFix_kind().equals(E_fix_kind.AIRPORT.toString()))
			lfpfs.remove(0);
		if(lfpfs.size()>0 && lfpfs.get(lfpfs.size()-1).getFix_kind().equals(E_fix_kind.AIRPORT.toString()))
			lfpfs.remove(lfpfs.size()-1);
		
		//缩短航路，取入界点和出界点之间并各自向外延伸一个已定义的航路点，防止出现点太远无法生成航迹
		int entryindex = 0;
		int exitindex = lfpfs.size()-1;
		//找入界点
		for(int i=0;i<lfpfs.size();i++)
		{		
			LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
			E_fix_kind kind = E_fix_kind.valueOf(lfpf.getFix_kind());
			if(kind.equals(E_fix_kind.ENT_POINT))
			{
				entryindex = i;
				break;
			}
		}
		//找出界点
		for(int i=lfpfs.size()-1;i>=0;i--)
		{		
			LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
			E_fix_kind kind = E_fix_kind.valueOf(lfpf.getFix_kind());
			if(kind.equals(E_fix_kind.EXT_POINT))
			{
				exitindex = i;
				break;
			}
		}
		//找离入界点、出界点最近的在导航台中有定义的点
		int nearest_out_enterindex = entryindex;
		int nearest_out_exitindex = exitindex;
		for(int i=entryindex-1;i>=0;i--)
		{		
			LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
			if(offl_fixnames.get(lfpf.getFix_name()) != null)
			{
				nearest_out_enterindex = i;
				break;
			}
		}
		for(int i=exitindex+1;i<lfpfs.size();i++)
		{		
			LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
			if(offl_fixnames.get(lfpf.getFix_name()) != null)
			{
				nearest_out_exitindex = i;
				break;
			}
		}
		lfpfs = lfpfs.subList(nearest_out_enterindex, nearest_out_exitindex+1);

		
		//判断AAT和出现位置（航路第一个点）、出现高度以及航路
		//出现点在航路中的序号
		int aatpointindex = -1;
		int aatsecs = 0;
		int height_im = -1; //百英尺
		Date timefirstroute = lfpfs.get(0).getReference_eto();
		Date timelastroute = lfpfs.get(lfpfs.size()-1).getReference_eto();
		//仿真时间在第一个航路点过点时间之前，出现点则为第一个航路点，补充高度
		if(time.before(timefirstroute))
		{
			aatpointindex = 0;
			aatsecs = (int)((timefirstroute.getTime() - time.getTime())/1000);
			LocalFlightPlanFixesDB first_lfpf = lfpfs.get(0);
			height_im = ((Float)first_lfpf.getActual_flight_level()).intValue();
//			first_lfpf.setFix_name(first_lfpf.getFix_name()+"/F"+String.format("%03d", height_im)+"/B");	
			first_lfpf.setFix_name(first_lfpf.getFix_name()+"/B");		
		}
		//在最后一个航路点过点时间之后，已飞完
		else if(time.after(timelastroute))
		{
			return null;
		}
		else
		{
			//在某两个航路点过点时间之间，
			//则应按比例推算出此时间的位置和高度，并插入航路在这两个点之间
			for(int i=0; i<lfpfs.size()-1; i++)
			{
				LocalFlightPlanFixesDB lfpf1 = lfpfs.get(i);
				LocalFlightPlanFixesDB lfpf2 = lfpfs.get(i+1);
				
				Date route1time = lfpf1.getReference_eto();
				Date route2time = lfpf2.getReference_eto();
				//正好是当前点的时间，则就在该点出现，补充高度
				if(time.compareTo(route1time) == 0) 
				{
					aatpointindex = i;
					aatsecs = 0;
					height_im = ((Float)lfpf1.getActual_flight_level()).intValue();
//					lfpf1.setFix_name(lfpf1.getFix_name()+"/F"+String.format("%03d", height_im)+"/B");
					lfpf1.setFix_name(lfpf1.getFix_name()+"/B");
					break;
				}
				//正好是下一个点的时间，则就在该点出现，补充高度
				else if(time.compareTo(route2time) == 0)
				{
					aatpointindex = i+1;
					aatsecs = 0;
					height_im = ((Float)lfpf2.getActual_flight_level()).intValue();
//					lfpf2.setFix_name(lfpf2.getFix_name()+"/F"+String.format("%03d", height_im)+"/B");
					lfpf2.setFix_name(lfpf2.getFix_name()+"/B");
					break;
				}
				//两个点之间，则插入出现点，并设置其过点时间、功能扇区等
				else if(time.after(route1time) && time.before(route2time))
				{
					aatpointindex = i+1;
					aatsecs = 0;
					
					//计算第一个点位置
					Point distancep1 = LatLongData.latlongstringToDistancep(lfpf1.getGeoposition());
					Point distancep2 = LatLongData.latlongstringToDistancep(lfpf2.getGeoposition());
					
					double deltasecs = (double)((route2time.getTime()-route1time.getTime())/1000);
					int deltax = distancep2.x-distancep1.x;
					int deltay = distancep2.y-distancep1.y;
					
					Point point = new Point();
					double curdeltasecs = (double)((time.getTime()-route1time.getTime())/1000);
					point.x = (int)(deltax*curdeltasecs/deltasecs)+distancep1.x;
					point.y = (int)(deltay*curdeltasecs/deltasecs)+distancep1.y;
					String aatfixname = LatLongData.distancepToLatlongstring(point);
					
					//出现点的高度使用前一个点的实际过点高度
					height_im = ((Float)lfpf1.getActual_flight_level()).intValue();
//					aatfixname = aatfixname+"/F"+String.format("%03d", height_im)+"/B";
					aatfixname = aatfixname+"/B";
					
					LocalFlightPlanFixesDB insertlfpf = new LocalFlightPlanFixesDB();
					insertlfpf.setFix_name(aatfixname);
					insertlfpf.setReference_eto(time);
					insertlfpf.setCurrent_functional_sector(lfpf1.getCurrent_functional_sector());	
					lfpfs.add(i+1, insertlfpf);
					
					break;
				}
			}
		}
		
		//若只选择扇区相关航路，则将出现点延迟到快进扇区时的最后一个已定义的点，并在扇区结束后最后一个点消失
		if(routeRangeSector == true)
		{
			//找到相关扇区入界点和出界点
			//先找到入界时第一个所选扇区内的点
			int first_sector_index = 0;
			int last_sector_index = lfpfs.size()-1;
			loop1:
			for(int i=0;i<lfpfs.size();i++)
			{
				LocalFlightPlanFixesDB lfpf = lfpfs.get(i);		
				for(String choosedsectorname: choosedsectornames)
				{
					if(lfpf.getCurrent_functional_sector().equals(choosedsectorname))
					{
						first_sector_index = i;
						break loop1;
					}
				}	
			}
			//再向入界前外延一个点，并保证不超出0
			first_sector_index-- ;
			if(first_sector_index < 0)
				first_sector_index = 0;
			
			//先找到出界时第一个所选扇区内的点
			loop2:
			for(int i=lfpfs.size()-1;i>=0;i--)
			{
				LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
				for(String choosedsectorname: choosedsectornames)
				{	
					if(lfpf.getCurrent_functional_sector().equals(choosedsectorname))
					{
						last_sector_index = i;
						break loop2;
					}
				}	
			}
			//再向出界后延一个点，并保证不超出所有航路点
			last_sector_index++ ;
			if(last_sector_index > (lfpfs.size()-1))
				first_sector_index = lfpfs.size()-1;
			
			//找扇区入界点和出界点最近的在导航台中有定义的点
			int nearest_first_sector_index = first_sector_index;
			int nearest_last_sector_index = last_sector_index;
			for(int i=first_sector_index;i>=0;i--)
			{		
				LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
				if(offl_fixnames.get(lfpf.getFix_name()) != null)
				{
					nearest_first_sector_index = i;
					break;
				}
			}
			for(int i=last_sector_index;i<lfpfs.size();i++)
			{		
				LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
				if(offl_fixnames.get(lfpf.getFix_name()) != null)
				{
					nearest_last_sector_index = i;
					break;
				}
			}
			
			//然后看看出现点与扇区入界点的时间差，若扇区入界点时间晚于出现点时间，则出现时间后移，变成扇区入界点时间
			int diffSecs = (int)((lfpfs.get(nearest_first_sector_index).getReference_eto().getTime()-
				lfpfs.get(aatpointindex).getReference_eto().getTime())/1000);
			if(diffSecs > 0)
			{
				LocalFlightPlanFixesDB lfpf_old = lfpfs.get(aatpointindex);
				LocalFlightPlanFixesDB lfpf_new = lfpfs.get(nearest_first_sector_index);
				
				aatpointindex = nearest_first_sector_index;
				aatsecs = diffSecs;
				lfpf_old.setFix_name(lfpf_old.getFix_name().substring(0, lfpf_old.getFix_name().length()-2));
				lfpf_new.setFix_name(lfpf_new.getFix_name()+"/B");
			}
			
			//如果有扇区出界点能找到，且不是最后一个点，且大于出现点序号，则将扇区出界点名称+/E
			if(nearest_last_sector_index>0 && (nearest_last_sector_index<lfpfs.size()-1) &&
				(nearest_last_sector_index>aatpointindex))
			{	
				LocalFlightPlanFixesDB lfpf_nearest_last_sector = lfpfs.get(nearest_last_sector_index);
				lfpf_nearest_last_sector.setFix_name(
						lfpf_nearest_last_sector.getFix_name()+"/E");
			}	
		}
		
		//将航路中未定义名称的点转换为经纬度表示(或删除-已注释)
		String patternlatlon = "[0-9]{4}N[0-9]{5}E";
		for(int i=lfpfs.size()-1;i>=0;i--)
		{
			LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
			//对出现点和出界点处理-未定义的点名称则转换为经纬度
			//同时出现点为第一个点时，去掉/B
			if(lfpf.getFix_name().endsWith("/B") || lfpf.getFix_name().endsWith("/E"))
			{
				//去掉/B或/E，取出原始点名称
				String ori_name = lfpf.getFix_name().substring(0,lfpf.getFix_name().length()-2);		
				String name_end = lfpf.getFix_name().substring(lfpf.getFix_name().length()-2); 
				//该点不是经纬度，且未定义，则替换成使用经纬度+/B或/E
				if((!ori_name.matches(patternlatlon)) && (offl_fixnames.get(ori_name)==null))	
				{
					lfpf.setFix_name(lfpf.getGeoposition()+name_end);
				}

				//出现点为航路第一个点，则去掉/B；出界点为航路最后一个点，则去掉/E
				if(i==0 || i==(lfpfs.size()-1))
					lfpf.setFix_name(ori_name);
				continue;
			}

			//对经纬度点不用替换名称
			if(lfpf.getFix_name().matches(patternlatlon))
				continue;				
			if(offl_fixnames.get(lfpf.getFix_name())==null)	
			{
//				lfpfs.remove(lfpf);
				lfpf.setFix_name(lfpf.getGeoposition());
			}
		}
		
		//去除除了出现点和第一个点、最后一个点以外的经纬度点
		for(int i=lfpfs.size()-1;i>=0;i--)
		{
			if(i==aatpointindex || i==0 || i==(lfpfs.size()-1))
				continue;
			LocalFlightPlanFixesDB lfpf = lfpfs.get(i);
			if(lfpf.getFix_name().matches(patternlatlon))
				lfpfs.remove(lfpf);	
		}
		
		//形成最终航路串
		StringBuffer sbrealroute = new StringBuffer();
		for(LocalFlightPlanFixesDB lfpf: lfpfs)
		{
			sbrealroute.append(lfpf.getFix_name());
			sbrealroute.append(' ');
		}
	
		//控制仿真时长，AAT出现时间在仿真时间之后的计划，不参与仿真
		if(aatsecs > durationsecs)
			return null;
		
		//航路过长，文件对应字段放不下，则认为无效
		if(sbrealroute.length() > 500)
		{
			AppLogger.info(lfp.getCallsign()+" route is too long >500, continue!");
			return null;
		}
		
		Skp skp = new Skp();
		
		//callsign
		skp.setCallsign(lfp.getCallsign());
		
		//actype
		skp.setActype(comboactype);
		
		//etd，相对于AAT，= realEtd - realAat
		Calendar calrealaat = Calendar.getInstance();
		calrealaat.setTime(time);
		calrealaat.add(Calendar.SECOND, aatsecs);
		int etdsecs = (int)((lfp.getEtd().getTime()-calrealaat.getTimeInMillis())/1000);
		skp.setEtd(etdsecs);
		
		//aat
		skp.setAat(aatsecs);
		
		//fl,fl_unit
		float fl_im = lfp.getRfl(); 
		double fl_m = fl_im*100*0.3048;
		if(height_im != -1)
		{ 
			fl_im = height_im;
			fl_m = fl_im*100*0.3048;
		}
		int n = (int)(Math.round(fl_m/10));        
		skp.setFl_m(n);
		skp.setFl_im(fl_im*100);
		
		//tas,tas_unit 无数据，默认800KM/H
		int tas_m = ConstantPara.n_default_speed;
		
		//用机型尾流去匹配AIRCRAFT_PERFORMANCE.ASF中对应机型的最大速度
//		HashMap<String, Integer> mapSpeed = App.getApp().getNavData().getActypes();
//		Integer speed = mapSpeed.get(comboactype);
//		if(speed != null)
//			tas_m = speed;	
		
		//根据尾流，H尾流使用830KM/H，M用750KM/H
		if(lfp.getWtc().equals(E_wake_turbulence.H.toString()))
			tas_m = 830;
		else if(lfp.getWtc().equals(E_wake_turbulence.M.toString()))
			tas_m = 750;
		
		//设置值
		skp.setTas_m(tas_m);
		float tas_im = tas_m/1.8523f;
		skp.setTas_im(tas_im);
		

		//adep
		skp.setAdep(lfp.getAdep());
		
		//dest
		skp.setDest(lfp.getAdes());
		
		//ssra
		skp.setSsra(lfp.getAssr_code());
		
		//ssrb
		skp.setSsrb(lfp.getAssr_code());
		
		//remarks
		skp.setRemarks("REG/"+lfp.getAircraft_registration().substring(1));
		
		//eet 记录文件中原有eet是默认值0200，转换时，使用起降机场+尾流匹配，匹配不上时才使用原有eet
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(lfp.getEet().getTime());
		//开始匹配
		HashMap<String, String> eets = App.getApp().
				getNavData().getEets();
		String key = lfp.getAdep()+lfp.getAdes()+lfp.getWtc();
		String eet = eets.get(key);
		if(eet != null)
		{
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(eet.substring(0, 2)));
			cal.set(Calendar.MINUTE, Integer.parseInt(eet.substring(2, 4)));
		}
		n = cal.get(Calendar.HOUR_OF_DAY)*3600+cal.get(Calendar.MINUTE)*60;
		skp.setEet(n);
		
		//frules
		skp.setFrules(E_flight_rule.valueOf(lfp.getFlight_rule()).ordinal());
		
		//route
		//skp.setRoute(lfp.getRoute_field());
		skp.setRoute(sbrealroute.toString().trim());
		
		//pilot
		//在外部设置
		
		//requested_fl
		float requested_fl_im = lfp.getRfl(); 
		double requested_fl_m = requested_fl_im*100*0.3048;
		n = (int)(Math.round(requested_fl_m/10));  
		skp.setRequested_fl_m(n);
		skp.setRequested_fl_im(requested_fl_im*100);
		
		//cfl 初次转换，使用rfl，不使用cfl
		float cfl_im = lfp.getRfl(); 
		if(cfl_im == 65535.0)
			cfl_im = 0;
		double cfl_m = cfl_im*100*0.3048;
		n = (int)(Math.round(cfl_m/10));  
		skp.setCfl_m(n);
		skp.setCfl_im(requested_fl_im*100);

		
		//field10a,10b
		//为了防止机载设备校验，则设置为SW/C
//		skp.setField10a(lfp.getField10a());
//		skp.setField10b(lfp.getField10b());
		skp.setField10a("SW");
		skp.setField10b("C");
		
		//eqp/surveillance_equipment
		skp.setSurveillance_equipment("");
	
		//time_for_est
		skp.setTime_for_est(0);
		
		return skp;
	}

	public static ArrayList<Skp> getSkpsFromFpls(String exenum, Date time, int durationsecs, ArrayList<String> choosedsectornames, int pilot, 
		boolean routeFromReality, boolean routeRangeSector, ArrayList<LocalFlightPlanDB> lfps) throws Exception
	{
		ArrayList<Skp> skps = new ArrayList<Skp>();
		
		for(int i=0;i<lfps.size();i++)	
		{
			LocalFlightPlanDB lfp = lfps.get(i);
			
			Skp skp = BdafUtils.getSkpFromFpl(lfp, time, durationsecs, choosedsectornames,
				routeFromReality, routeRangeSector);
			if(skp == null) //不符合筛选条件
				continue;

			skps.add(skp);
		}
		for(int i=0;i<skps.size();i++)
		{
			Skp skp = skps.get(i);
			skp.setPilot(i%pilot+1);
		}
		return skps;
	}
	
	public static void writeSkpsToExe(String exenum, ArrayList<Skp> skps) throws Exception
	{
		if(skps.size() > maxskpsum)
		{
			AppLogger.info("Sum of SKP reach max: "+maxskpsum+", can't save, please try again!");	
			return;
		}
		
		File file = new File("exe//EXE"+exenum);
		if(!file.exists())
		{
			AppLogger.info("Exe file isnot exist! "+file.getName());
			return;
		}	
		
		file.delete();
		file.createNewFile();
		int filesize = 526000;
		int headersize = 12;
		byte[] bytetmp = null;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), filesize);		

		for(Skp skp: skps)	
		{
			//header
			bos.write(0); //STATUS==USED
			String callsign = skp.getCallsign();
			bos.write(callsign.getBytes());
			bytetmp = new byte[8-callsign.length()];
			bos.write(bytetmp);
			bytetmp = new byte[headersize-9];
			bos.write(bytetmp);	
			
			//callsign
			bos.write(callsign.getBytes());
			bytetmp = new byte[8-callsign.length()];
			for(int j=0;j<bytetmp.length;j++)
				bytetmp[j] = 32;
			bos.write(bytetmp);
			
			//actype
			String actype = skp.getActype();
			bos.write(actype.getBytes());
			bytetmp = new byte[8-actype.length()];
			for(int j=0;j<bytetmp.length;j++)
				bytetmp[j] = 32;
			bos.write(bytetmp);
			
			//etd    //暂时使用etd与输入时间的差值
			bytetmp = new byte[4];
			bytetmp[0] = 1; //presence
			if(skp.getEtd() < 0)
				bytetmp[2] = 1; //NEGATIF SKP的ETD应该总是负数？
			bos.write(bytetmp);
			bytetmp = BestUtils.intToByte_Low(Math.abs(skp.getEtd()), 12);
			bos.write(bytetmp);
			
			//aat   //使用正负值
			bytetmp = new byte[4];
			bytetmp[0] = 1;
			if(skp.getAat() < 0)
				bytetmp[2] = 0; //0正 1负
			bos.write(bytetmp);
			bytetmp = BestUtils.intToByte_Low(Math.abs(skp.getAat()), 12);
			bos.write(bytetmp);
			
			//fl
			bytetmp = new byte[2];
			bytetmp[0] = 1; //presence
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bytetmp[0] = 2; //unity=S
			bos.write(bytetmp);   
			bytetmp = BestUtils.intToByte_Low(skp.getFl_m(), 2); //value_m, 米制高度10米
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bos.write(bytetmp);
			bytetmp = BestUtils.floatToByte4_Low(skp.getFl_im(), 4); //value_im, 英制高度英尺
			bos.write(bytetmp);
			
			//tas  
			bytetmp = new byte[2];
			bytetmp[0] = 1; //presence
			bos.write(bytetmp);
			bytetmp = BestUtils.intToByte_Low(skp.getTas_m(), 2); //value_m 公里/小时
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bytetmp[0] = 3; //unit: KMHOUR_E
			bos.write(bytetmp);
			bytetmp = new byte[2]; 
			bos.write(bytetmp);		
			bytetmp = BestUtils.floatToByte4_Low(skp.getTas_im(), 4); //value_im 海里/小时
			bos.write(bytetmp);
			
			//adep
			bytetmp = skp.getAdep().getBytes();
			bos.write(bytetmp);
			
			//aster
			bos.write(0);
			
			//dest
			bytetmp = skp.getDest().getBytes();
			bos.write(bytetmp);
			bos.write(0);
			
			//ssra
			bytetmp = new byte[2];
			bytetmp[1] = 1; //presence
			bos.write(bytetmp);
			bytetmp = BestUtils.ssrToBytes(String.format("%04d", skp.getSsra()), false);
			bos.write(bytetmp);		
			
			
			//ssrb
			bytetmp = new byte[2];
			bytetmp[0] = 1; //presence
			bos.write(bytetmp);
			bytetmp = BestUtils.ssrToBytes(String.format("%04d", skp.getSsrb()), false);
			bos.write(bytetmp);		
			
			
			//remarks  
			String remarks = skp.getRemarks();
			bos.write(remarks.getBytes());
			bytetmp = new byte[102-remarks.length()];
			for(int j=0;j<bytetmp.length;j++)
				bytetmp[j] = 32;
			bos.write(bytetmp);
			
			
			//eet     
			bytetmp = new byte[4];
			bytetmp[0] = 1;
			bos.write(bytetmp);
			bytetmp = BestUtils.intToByte_Low(Math.abs(skp.getEet()), 12);
			bos.write(bytetmp);
			
			//frules
			bytetmp = new byte[4];
			bytetmp[0] = new Integer(skp.getFrules()).byteValue();
			bos.write(bytetmp);
			
			//actype
			bos.write(actype.getBytes());
			bytetmp = new byte[8-actype.length()];
			for(int j=0;j<bytetmp.length;j++)
				bytetmp[j] = 32;
			bos.write(bytetmp);
			
			//mode
			bos.write(1);
			
			//mode_c_used
			bos.write(1);
			
			//route
			String route = skp.getRoute();
			bos.write(route.getBytes());
			bytetmp = new byte[500-route.length()];
			for(int j=0;j<bytetmp.length;j++)
				bytetmp[j] = 32;  //' '
			bos.write(bytetmp);
			//System.out.println(route);
			
			//pilot   
			bytetmp = new byte[2];
			bytetmp[0] = 1; //presence
			bos.write(bytetmp);
			bytetmp = BestUtils.intToByte2_Low(skp.getPilot());
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bos.write(bytetmp);
			
			//requested_fl
			bytetmp = new byte[2];
			bytetmp[0] = 1; //presence
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bytetmp[0] = 2; //unity=S
			bos.write(bytetmp);   
			bytetmp = BestUtils.intToByte_Low(skp.getRequested_fl_m(), 2); //value_m, 米制高度10米
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bos.write(bytetmp);
			bytetmp = BestUtils.floatToByte4_Low(skp.getRequested_fl_im(), 4); //value_im, 英制高度英尺
			bos.write(bytetmp);
			
			//lower_fl == cfl
			int cfl_m_final = skp.getCfl_m();
			float cfl_im_final = skp.getCfl_im();
			bytetmp = new byte[2];
			bytetmp[0] = 1; //presence
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bytetmp[0] = 2; //unity=S
			bos.write(bytetmp); 
			//若cfl无值或为零，则使用rfl
//			if(cfl_m_final == 0)
//			{
//				cfl_m_final = skp.getRequested_fl_m();
//				cfl_im_final = skp.getRequested_fl_im();
//			}
			bytetmp = BestUtils.intToByte_Low(cfl_m_final, 2); //value_m, 米制高度10米
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bos.write(bytetmp);
			bytetmp = BestUtils.floatToByte4_Low(cfl_im_final, 4); //value_im, 英制高度英尺
			bos.write(bytetmp);
			
			//upper_fl   == cfl
			bytetmp = new byte[2];
			bytetmp[0] = 0; //presence
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bytetmp[0] = 6; //unity=NONE
			bos.write(bytetmp);
			bytetmp = BestUtils.intToByte_Low(cfl_m_final, 2); //value_m, 米制高度10米
			bos.write(bytetmp);
			bytetmp = new byte[2];
			bos.write(bytetmp);
			bytetmp = BestUtils.floatToByte4_Low(cfl_im_final, 4); //value_im, 英制高度英尺
			bos.write(bytetmp);
			
			//status
			bytetmp = new byte[132];
			bos.write(bytetmp);
			
			//track_to_create
			bos.write(1);
			
			//fpl_to_create
			bos.write(1);
			
			//equipment  //暂时使用SW/C
			String field10a = skp.getField10a();
			bos.write(field10a.getBytes());
			bytetmp = new byte[50-field10a.length()];
			for(int j=0;j<bytetmp.length;j++) 
				bytetmp[j] = 32;
			bos.write(bytetmp);
			String field10b = skp.getField10b();
			bos.write(field10b.getBytes());
			bytetmp = new byte[20-field10b.length()];
			for(int j=0;j<bytetmp.length;j++)
				bytetmp[j] = 32;
			bos.write(bytetmp);
			
			//fpl_mes_type
			bytetmp = new byte[4];
			bos.write(bytetmp);
			
			//time_for_est
			bytetmp = new byte[4];
			bytetmp[0] = 1;
			bos.write(bytetmp);
			bytetmp = BestUtils.intToByte_Low(Math.abs(skp.getTime_for_est()), 12);
			bos.write(bytetmp);
			
			//time_for_pac
			bytetmp = new byte[4];
			bos.write(bytetmp);
			bytetmp = new byte[12];
			bos.write(bytetmp);
			
			//time_for_toc
			bytetmp = new byte[4];
			bos.write(bytetmp);
			bytetmp = new byte[12];
			bos.write(bytetmp);
			
			//time_for_cpl
			bytetmp = new byte[4];
			bos.write(bytetmp);
			bytetmp = new byte[12];
			bos.write(bytetmp);
			
			//dep_to_send
			bos.write(0);
			
			//aircraft_unit
			bos.write(1);
			
			//surveillance_equipment
			String surveillance_equipment = skp.getSurveillance_equipment();
			bos.write(surveillance_equipment.getBytes());
			if(surveillance_equipment.length() != 2)
			{
				bytetmp = new byte[2-surveillance_equipment.length()];
				for(int j=0;j<bytetmp.length;j++)
					bytetmp[j] = 32;
				bos.write(bytetmp);
			}

			//System.out.println(surveillance_equipment);
			
		}	
		for(int i=0;i<maxskpsum-skps.size();i++)
		{
			//header
			bos.write(1); //STATUS==NOT_USED
			bytetmp = new byte[headersize-1];
			bos.write(bytetmp);	
			
			bytetmp = new byte[1040];
			bos.write(bytetmp);	
		}
		
		bos.flush();
		bos.close();
		
		//更新LIST_EXE.LST中的SKP数量
		writeSkpsumToExeList(exenum, skps.size());
	}
	
	
	public static void writeSkpsumToExeList(String exenum, int skpsum) throws Exception
	{
		File fileread = new File("exe//LIST_EXE.LIS");
		if(!fileread.exists())
		{
			AppLogger.info("LIST_EXE.LST file isnot exist, please try again! "+fileread.getName());
			return;
		}	
		
		File filewrite = new File("filetmp");
		filewrite.createNewFile();
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileread));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filewrite));		
		byte[] bytetmp = new byte[702];
		byte[] byte2 = new byte[2];
		
		for(int i=0;i<999;i++)
		{
			bis.read(bytetmp, 0, bytetmp.length);
			
			String readedexenum = new String(bytetmp, 0, 12);
			int index = readedexenum.indexOf("EXE"+exenum);
			if(index != -1)
			{
				byte2 = BestUtils.intToByte2_Low(skpsum);
				System.arraycopy(byte2, 0, bytetmp, index+6, 2);
			}
			
			bos.write(bytetmp);
		}
		
		bis.close();
		bos.close();
		
		fileread.delete();
		filewrite.renameTo(fileread);
	}
	
	public static ArrayList<Skp> getSkpsFromExeFile(String exenum) throws Exception
	{
		ArrayList<Skp> skps = new ArrayList<Skp>();
		File file = null;
		if(exenum.equals(ConstantPara.str_skplname))
			file = new File("exe//"+exenum);
		else	
			file = new File("exe//EXE"+exenum);
		if(!file.exists())
		{
			AppLogger.info("Exe file isnot exist, please try again! "+file.getName());
			return skps;
		}	
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[1024];
		byte[] byte2 = new byte[2];
		byte[] byte4 = new byte[4];
		
		while(true)
		{
			int headerlength = 12;
			bis.read(bytes, 0, headerlength);
			if(bytes[0] == 1) //STATUS==NOT_USED
				break;
			
			Skp skp = new Skp();
			skps.add(skp);
			
			bis.read(bytes, 0, 8);
			String callsign = new String(Arrays.copyOf(bytes, 8)).trim();
			skp.setCallsign(callsign);
			
			bis.read(bytes, 0, 8);
			String actype = new String(Arrays.copyOf(bytes, 6)).trim();
			skp.setActype(actype);
			
			//etd
			bis.read(bytes, 0, 16);
			int n = BestUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 16), false);
			if(bytes[2] == 1)
				n = -n;
			skp.setEtd(n);
			
			//aat
			bis.read(bytes, 0, 16);
			n = BestUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 16), false);
			if(bytes[2] == 1)
				n = -n;
			skp.setAat(n);
			
			//fl
			bis.read(bytes, 0, 12);
			int presence = bytes[0];
			String leveltype = LEVEL_TYPE_T.values()[bytes[2]].toString();
			System.arraycopy(bytes, 4, byte2, 0, 2);
			n = BestUtils.byte2ToInt_Low(byte2);
			System.arraycopy(bytes, 8, byte4, 0, 4);
			float f = BestUtils.byteToFloat(byte4, false);
			skp.setFl_m(n);
			skp.setFl_im(f);
			
			//tas
			bis.read(bytes, 0, 12);
			presence = bytes[0];
			System.arraycopy(bytes, 2, byte2, 0, 2);
			n = BestUtils.byte2ToInt_Low(byte2);
			String speedtype = SPEED_TYPE_T.values()[bytes[4]].toString();
			System.arraycopy(bytes, 8, byte4, 0, 4);
			f = BestUtils.byteToFloat(byte4, false);
			skp.setTas_m(n);
			skp.setTas_im(f);
			
			//adep
			bis.read(bytes, 0, 4);
			String adep = new String(Arrays.copyOf(bytes, 4)).trim();
			skp.setAdep(adep);
			
			//aster
			bis.read(bytes, 0, 1);
			char c = (char)bytes[0];
			
			//dest
			bis.read(bytes, 0, 5);
			String dest = new String(Arrays.copyOf(bytes, 4)).trim();
			skp.setDest(dest);
			
			//ssra
			bis.read(bytes, 0, 4);
			int auto = bytes[0];
			presence = bytes[1];
			System.arraycopy(bytes, 2, byte2, 0, 2);
			String ssra = BestUtils.bytesToSsr(byte2, false);
			skp.setSsra(Integer.parseInt(ssra));
			
			//ssrb
			bis.read(bytes, 0, 4);
			presence = bytes[0];
			System.arraycopy(bytes, 2, byte2, 0, 2);
			String ssrb = BestUtils.bytesToSsr(byte2, false);
			skp.setSsrb(Integer.parseInt(ssrb));
			
			//remarks
			bis.read(bytes, 0, 102);
			String remarks = new String(Arrays.copyOf(bytes, 100)).trim();
			skp.setRemarks(remarks);
			
			//eet
			bis.read(bytes, 0, 16);
			n = BestUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 16), false);
			if(bytes[2] == 1)
				n = -n;
			skp.setEet(n);
			
			//frules
			bis.read(bytes, 0, 4);
			String frules = E_flight_rule.values()[bytes[0]].toString();
			skp.setFrules(new Integer(bytes[0]));
			
			//actype
			bis.read(bytes, 0, 8);
			actype = new String(Arrays.copyOf(bytes, 8)).trim();
			
			//mode
			bis.read(bytes, 0, 1);
			String mode = SSR_MODE.values()[bytes[0]].toString();
			
			//mode_c_used
			bis.read(bytes, 0, 1);
			n = bytes[0];
			
			//route
			bis.read(bytes, 0, 500);
			String route = new String(Arrays.copyOf(bytes, 500)).trim();
			skp.setRoute(route);
			
			//pilot
			bis.read(bytes, 0, 6);
			presence = bytes[0];
			System.arraycopy(bytes, 2, byte2, 0, 2);
			n = BestUtils.byte2ToInt_Low(byte2);
			skp.setPilot(n);
			
			//requested_fl
			bis.read(bytes, 0, 12);
			presence = bytes[0];
			leveltype = LEVEL_TYPE_T.values()[bytes[2]].toString();
			System.arraycopy(bytes, 4, byte2, 0, 2);
			n = BestUtils.byte2ToInt_Low(byte2);
			System.arraycopy(bytes, 8, byte4, 0, 4);
			f = BestUtils.byteToFloat(byte4, false);
			skp.setRequested_fl_m(n);
			skp.setRequested_fl_im(f);
			
			//lower_fl
			bis.read(bytes, 0, 12);
			presence = bytes[0];
			leveltype = LEVEL_TYPE_T.values()[bytes[2]].toString();
			System.arraycopy(bytes, 4, byte2, 0, 2);
			n = BestUtils.byte2ToInt_Low(byte2);
			System.arraycopy(bytes, 8, byte4, 0, 4);
			f = BestUtils.byteToFloat(byte4, false);
			skp.setCfl_m(n);
			skp.setCfl_im(f);
			
			//upper_fl
			bis.read(bytes, 0, 12);
			presence = bytes[0];
			leveltype = LEVEL_TYPE_T.values()[bytes[2]].toString();
			System.arraycopy(bytes, 4, byte2, 0, 2);
			n = BestUtils.byte2ToInt_Low(byte2);
			System.arraycopy(bytes, 8, byte4, 0, 4);
			f = BestUtils.byteToFloat(byte4, false);
			
			//status
			bis.read(bytes, 0, 132);
			for(int i=0;i<GROUP_TYPE.values().length-1;i++)
			{
				int activated_for = bytes[i];
			}
			for(int i=0;i<GROUP_TYPE.values().length-1;i++)
			{
				int time_activated_for = BestUtils.byteToInt(Arrays.copyOfRange(bytes, 12+i*12, 12+(i+1)*12), false);
			}
			
			//track_to_status
			bis.read(bytes, 0, 1);
			n = bytes[0];
			
			//fpl_to_status
			bis.read(bytes, 0, 1);
			n = bytes[0];
			
			//equipment
			bis.read(bytes, 0, 70);
			String field10a = new String(Arrays.copyOf(bytes, 50)).trim();
			String field10b = new String(Arrays.copyOfRange(bytes, 50, 70)).trim();
			skp.setField10a(field10a);
			skp.setField10b(field10b);
			
			
			//fpl_mes_type
			bis.read(bytes, 0, 4);
			String fpl_mes_type = FPL_MES_TYPE_T.values()[bytes[0]].toString();
			
			
			//time_for_est
			bis.read(bytes, 0, 16);
			n = BestUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 16), false);
			skp.setTime_for_est(n);
	
			
			//time_for_pac
			bis.read(bytes, 0, 16);
			n = BestUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 16), false);
		
			
			//time_for_toc
			bis.read(bytes, 0, 16);
			n = BestUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 16), false);
			
			
			//time_for_cpl
			bis.read(bytes, 0, 16);
			n = BestUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 16), false);
			
			
			//dep_to_send
			bis.read(bytes, 0, 1);
			n = bytes[0];
	
			
			//aircraft_unit
			bis.read(bytes, 0, 1);
			String  aircraft_unit = GENERIC_UNIT_SETTING_TYPE.values()[bytes[0]].toString();
			
			
			
			//surveillance_equipment
			bis.read(bytes, 0, 2);
			String  surveillance_equipment = new String(Arrays.copyOf(bytes, 2)).trim();
			skp.setSurveillance_equipment(surveillance_equipment);
		}
		
		bis.close();
		
		return skps;
	}
}
