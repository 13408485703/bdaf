package com.best.bdaf.data;




public class ConstantPara
{
	public static final String str_version = "3.2.20200219" ;
	
	public static String str_time_zone = "UTC";
	public static int n_max_excelcolumn = 60000;
	public static int n_default_speed = 800;
	
	public static String str_none = "";
	public static String str_dbname_fpl = "local_flight_plan";
	public static String str_dbname_fplfix = "local_flight_plan_fixes";
	
	public static String str_skplname = "SKPL_FILE";
	
	
	public static void init(IniData id)
	{
		try
		{
			
			
		}
		catch (Exception e) {
			AppLogger.error(e);
		}	
	}
}

