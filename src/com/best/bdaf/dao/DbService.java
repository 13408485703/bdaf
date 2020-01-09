package com.best.bdaf.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;

import com.best.bdaf.data.ConstantPara;

public class DbService {
	
	private QueryRunner qr = new TxQueryRunner();


	public List<LocalFlightPlanDB> getLocalFlightPlanDBs() throws Exception
	{
		String sql = "select * from " + ConstantPara.str_dbname_fpl;
		
		List<LocalFlightPlanDB> list = qr.query(sql, new BeanListHandler<LocalFlightPlanDB>(LocalFlightPlanDB.class));
//			System.out.println(list);
		
		return list;
	}
	
	public List<LocalFlightPlanFixesDB> getLocalFlightPlanFixesDBs() throws Exception
	{
		String sql = "select * from " + ConstantPara.str_dbname_fplfix;
		
		List<LocalFlightPlanFixesDB> list = qr.query(sql, new BeanListHandler<LocalFlightPlanFixesDB>(LocalFlightPlanFixesDB.class));
//			System.out.println(list);
		
		return list;
	}
	
	public LocalFlightPlanDB getLocalFlightPlanDB(int fpid) throws Exception
	{
		String sql = "select * from " + ConstantPara.str_dbname_fpl + " where fpl_id=?";
		
		return (qr.query(sql, new BeanHandler<LocalFlightPlanDB>(LocalFlightPlanDB.class), fpid));
	}
	
	public List<LocalFlightPlanFixesDB> getLocalFlightPlanFixesDBs(int fpid) throws Exception
	{
		String sql = "select * from " + ConstantPara.str_dbname_fplfix + " where fpl_id=?";
		
		List<LocalFlightPlanFixesDB> list= qr.query(sql, new BeanListHandler<LocalFlightPlanFixesDB>(LocalFlightPlanFixesDB.class), fpid);
		
		return list;
	}

	
	public void updateComFplDB(ComFplDB cfd, boolean iscreate) throws Exception
	{
		updateLocalFlightPlan(cfd.getLfp(), iscreate);
		for(LocalFlightPlanFixesDB lfpf : cfd.getLfpls())
			updateLocalFlightPlanFixes(lfpf, iscreate);
	}
	
	public void updateLocalFlightPlan(LocalFlightPlanDB lfp, boolean iscreate) throws Exception
	{
		String sql = null;
		Object[] params = null;
		if(iscreate)
		{
			sql = "INSERT INTO "+ConstantPara.str_dbname_fpl+" ("+
	            "fpl_id, record_time, creation_time, flight_origin, callsign, "+
	            "flight_plan_number, adep, ades, aircraft_type, aircraft_registration, "+
	            "aircraft_address, atd, ata, flight_rule, flight_type, flight_category, "+
	            "movement_category, movement_date, dof, distance_flown_in_fir, "+
	            "entry_point, entry_hour, exit_point, exit_hour, sent_to_fdo, "+
	            "storage_delay, fix_list_length, assr_code, pssr_code, etd, eta, "+
	            "rfl, ufl, cfl, exit_protocol, field10a, field10b, wtc, eobt, "+
	            "eet, fpl_status, cfl_approach_mode, last_fix_overflown, dcc_identifier, "+
	            "aircraft_operator_name, route_field) "+
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, "+
	            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";	
			
			params = new Object[]{lfp.getFpl_id(),lfp.getRecord_time(),lfp.getCreation_time(),
				lfp.getFlight_origin(),lfp.getCallsign(),lfp.getFlight_plan_number(),lfp.getAdep(),
				lfp.getAdes(),lfp.getAircraft_type(),lfp.getAircraft_registration(),
				lfp.getAircraft_address(),lfp.getAtd(),lfp.getAta(),lfp.getFlight_rule(),
				lfp.getFlight_type(),lfp.getFlight_category(),lfp.getMovement_category(),lfp.getMovement_date(),
				lfp.getDof(),lfp.getDistance_flown_in_fir(),lfp.getEntry_point(),lfp.getEntry_hour(),
				lfp.getExit_point(),lfp.getExit_hour(),lfp.getSent_to_fdo(),lfp.getStorage_delay(),
				lfp.getFix_list_length(),lfp.getAssr_code(),lfp.getPssr_code(),lfp.getEtd(),lfp.getEta(),
				lfp.getRfl(),lfp.getUfl(),lfp.getCfl(),lfp.getExit_protocol(),lfp.getField10a(),
				lfp.getField10b(),lfp.getWtc(),lfp.getEobt(),lfp.getEet(),lfp.getFpl_status(),
				lfp.getCfl_approach_mode(),lfp.getLast_fix_overflown(),lfp.getDcc_identifier(),
				lfp.getAircraft_operator_name(),lfp.getRoute_field()
				};
		}
		else
		{
			sql = "UPDATE "+ConstantPara.str_dbname_fpl+" "+
			   "SET fpl_id=?, record_time=?, creation_time=?, flight_origin=?, callsign=?, "+
		       "flight_plan_number=?, adep=?, ades=?, aircraft_type=?, aircraft_registration=?, "+
		       "aircraft_address=?, atd=?, ata=?, flight_rule=?, flight_type=?, "+
		       "flight_category=?, movement_category=?, movement_date=?, dof=?, "+
		       "distance_flown_in_fir=?, entry_point=?, entry_hour=?, exit_point=?, "+
		       "exit_hour=?, sent_to_fdo=?, storage_delay=?, fix_list_length=?, "+
		       "assr_code=?, pssr_code=?, etd=?, eta=?, rfl=?, ufl=?, cfl=?, "+
		       "exit_protocol=?, field10a=?, field10b=?, wtc=?, eobt=?, eet=?, "+
		       "fpl_status=?, cfl_approach_mode=?, last_fix_overflown=?, dcc_identifier=?, "+
		       "aircraft_operator_name=?, route_field=? "+
		       "WHERE fpl_id=?;";
			
			params = new Object[]{lfp.getFpl_id(),lfp.getRecord_time(),lfp.getCreation_time(),
				lfp.getFlight_origin(),lfp.getCallsign(),lfp.getFlight_plan_number(),lfp.getAdep(),
				lfp.getAdes(),lfp.getAircraft_type(),lfp.getAircraft_registration(),
				lfp.getAircraft_address(),lfp.getAtd(),lfp.getAta(),lfp.getFlight_rule(),
				lfp.getFlight_type(),lfp.getFlight_category(),lfp.getMovement_category(),lfp.getMovement_date(),
				lfp.getDof(),lfp.getDistance_flown_in_fir(),lfp.getEntry_point(),lfp.getEntry_hour(),
				lfp.getExit_point(),lfp.getExit_hour(),lfp.getSent_to_fdo(),lfp.getStorage_delay(),
				lfp.getFix_list_length(),lfp.getAssr_code(),lfp.getPssr_code(),lfp.getEtd(),lfp.getEta(),
				lfp.getRfl(),lfp.getUfl(),lfp.getCfl(),lfp.getExit_protocol(),lfp.getField10a(),
				lfp.getField10b(),lfp.getWtc(),lfp.getEobt(),lfp.getEet(),lfp.getFpl_status(),
				lfp.getCfl_approach_mode(),lfp.getLast_fix_overflown(),lfp.getDcc_identifier(),
				lfp.getAircraft_operator_name(),lfp.getRoute_field(),
				lfp.getFpl_id()};
		}
		qr.update(sql, params);
	}
	
	public void updateLocalFlightPlanFixes(LocalFlightPlanFixesDB lfpf, boolean iscreate) throws Exception
	{
		//将为零的时间都改为2000年，避免存入数据库出错
//		Field[] fields = LocalFlightPlanFixesDB.class.getDeclaredFields();
//		for(Field field : fields)
//		{
//			field.setAccessible(true);
//			Object o = field.get(lfpf);
//			if(o instanceof Date)
//			{
//				Date date = (Date)o;
//				if(date.getYear() == LocalFlightPlanDB.ZERO_YEAR_ORI)
//				{
//					date.setYear(LocalFlightPlanDB.ZERO_YEAR_AIM);
//					field.set(lfpf, date);
//				}
//			}
//		}
				
		String sql = null;
		Object[] params = null;
		if(iscreate)
		{
			sql = "INSERT INTO "+ConstantPara.str_dbname_fplfix+" ("+
            "fix_id, record_time, fix_kind, fix_name, displayed_eto, current_functional_sector, "+
            "next_functional_sector, sector_changed, current_sector, reference_eto, "+
            "flight_rule_after_fix, distance_from_previous_fix, geoposition, "+
            "actual_flight_level, pre_cleared_flight_level, associated_flight_plan, "+
            "fix_name_and_pos, fpl_id, fix_index) "+
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				
			params = new Object[]{lfpf.getFix_id(),lfpf.getRecord_time(),lfpf.getFix_kind(),
				lfpf.getFix_name(),lfpf.getDisplayed_eto(),lfpf.getCurrent_functional_sector(),
				lfpf.getNext_functional_sector(),lfpf.getSector_changed(),lfpf.getCurrent_sector(),
				lfpf.getReference_eto(),lfpf.getFlight_rule_after_fix(),lfpf.getDistance_from_previous_fix(),
				lfpf.getGeoposition(),lfpf.getActual_flight_level(),lfpf.getPre_cleared_flight_level(),
				lfpf.getAssociated_flight_plan(),lfpf.getFix_name_and_pos(),lfpf.getFpl_id(),lfpf.getFix_index()};
		}
		else
		{
			sql = "UPDATE "+ConstantPara.str_dbname_fplfix+" "+
				"SET fix_id=?, record_time=?, fix_kind=?, fix_name=?, displayed_eto=?, "+
				"current_functional_sector=?, next_functional_sector=?, sector_changed=?, "+
				"current_sector=?, reference_eto=?, flight_rule_after_fix=?, distance_from_previous_fix=?, "+
				"geoposition=?, actual_flight_level=?, pre_cleared_flight_level=?, "+
				"associated_flight_plan=?, fix_name_and_pos=?, fpl_id=?, fix_index=? "+
				"WHERE fix_id=?;";
			
			params = new Object[]{lfpf.getFix_id(),lfpf.getRecord_time(),lfpf.getFix_kind(),
				lfpf.getFix_name(),lfpf.getDisplayed_eto(),lfpf.getCurrent_functional_sector(),
				lfpf.getNext_functional_sector(),lfpf.getSector_changed(),lfpf.getCurrent_sector(),
				lfpf.getReference_eto(),lfpf.getFlight_rule_after_fix(),lfpf.getDistance_from_previous_fix(),
				lfpf.getGeoposition(),lfpf.getActual_flight_level(),lfpf.getPre_cleared_flight_level(),
				lfpf.getAssociated_flight_plan(),lfpf.getFix_name_and_pos(),lfpf.getFpl_id(),lfpf.getFix_index(),
				lfpf.getFix_id()};
		}
		
		qr.update(sql, params);
	}
	
	public void clearLocalFlightPlanAndFixes() throws Exception
	{ 
		String sql1 = "DELETE FROM "+ConstantPara.str_dbname_fplfix+";";
		String sql2 = "DELETE FROM "+ConstantPara.str_dbname_fpl+";";
		

		qr.update(sql1);	
		qr.update(sql2);	
		
	}
}
