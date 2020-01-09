package com.best.bdaf.dao;

import java.util.Date;



public class LocalFlightPlanDB implements Comparable<LocalFlightPlanDB> 
{	
	public enum E_flight_type {S,N,G,M,X,E,B,J,Q,NONE};
	public enum E_flight_rule {I,V,Y,Z,NONE};
	public enum E_flight_category {OVF,INT,DEP,ARR};
	public enum E_movement_category {DOMESTIC,INTERNATIONAL};
	public enum E_flight_origin {LPL,RPL,FPL,STE};
	public enum E_exit_protocol {NONE,AIDC,ICAO,OLDI,ADEXP};
	public enum E_wake_turbulence {L,M,H,J,NONE};
	public enum E_fpl_status {FUTURE,INACTIVE,PREACTIVE,SUSPENDED,INHIBITED,COORDINATED,ACTIVE,FINISHED,CANCELLED};
	public enum E_level_type {NODEFINED,F,S,A,M,VFR,NONE};
	public enum E_ssr_mode {MODE_A,MODE_B,MODE_C,MODE_D,MODE_R,MODE_S,NO_MODE};
	
	public static int ZERO_YEAR_AIM = 2000-1900;
	public static int ZERO_YEAR_ORI = 1970-1900;
	
    private Integer fpl_id;

    private Date record_time;

    private Date creation_time;

    private String flight_origin;

    private String callsign;

    private Integer flight_plan_number; 

    private String adep;

    private String ades;

    private String aircraft_type;

    private String aircraft_registration;

    private String aircraft_address;

    private Date atd;

    private Date ata;

    private String flight_rule;

    private String flight_type;

    private String flight_category;

    private String movement_category;

    private Date movement_date;

    private String dof;

    private Integer distance_flown_in_fir;

    private String entry_point;

    private Date entry_hour;

    private String exit_point;

    private Date exit_hour;

    private Boolean sent_to_fdo;

    private Integer storage_delay;

    private Integer fix_list_length;

    private Integer assr_code;

    private Integer pssr_code;

    private Date etd;

    private Date eta;

    private Float rfl;

    private Float ufl;

    private Float cfl;

    private String exit_protocol;

    private String field10a;

    private String field10b;

    private String wtc;

    private Date eobt;

    private Date eet;

    private String fpl_status;

    private String cfl_approach_mode;

    private Integer last_fix_overflown;

    private String dcc_identifier;

    private String aircraft_operator_name;
    
    private String route_field;

    
    public void setRoute_field(String route_field) {
        this.route_field = route_field == null ? null : route_field.trim();
    }

    public String getRoute_field() {
		return route_field;
	}
    
	public Integer getFpl_id() {
        return fpl_id;
    }

    public void setFpl_id(Integer fpl_id) {
        this.fpl_id = fpl_id;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    public String getFlight_origin() {
        return flight_origin;
    }

    public void setFlight_origin(String flight_origin) {
        this.flight_origin = flight_origin == null ? null : flight_origin.trim();
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign == null ? null : callsign.trim();
    }

    public Integer getFlight_plan_number() {
        return flight_plan_number;
    }

    public void setFlight_plan_number(Integer flight_plan_number) {
        this.flight_plan_number = flight_plan_number;
    }

    public String getAdep() {
        return adep;
    }

    public void setAdep(String adep) {
        this.adep = adep == null ? null : adep.trim();
    }

    public String getAdes() {
        return ades;
    }

    public void setAdes(String ades) {
        this.ades = ades == null ? null : ades.trim();
    }

    public String getAircraft_type() {
        return aircraft_type;
    }

    public void setAircraft_type(String aircraft_type) {
        this.aircraft_type = aircraft_type == null ? null : aircraft_type.trim();
    }

    public String getAircraft_registration() {
        return aircraft_registration;
    }

    public void setAircraft_registration(String aircraft_registration) {
        this.aircraft_registration = aircraft_registration == null ? null : aircraft_registration.trim();
    }

    public String getAircraft_address() {
        return aircraft_address;
    }

    public void setAircraft_address(String aircraft_address) {
        this.aircraft_address = aircraft_address == null ? null : aircraft_address.trim();
    }

    public Date getAtd() {
        return atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    public Date getAta() {
        return ata;
    }

    public void setAta(Date ata) {
        this.ata = ata;
    }

    public String getFlight_rule() {
        return flight_rule;
    }

    public void setFlight_rule(String flight_rule) {
        this.flight_rule = flight_rule == null ? null : flight_rule.trim();
    }

    public String getFlight_type() {
        return flight_type;
    }

    public void setFlight_type(String flight_type) {
        this.flight_type = flight_type == null ? null : flight_type.trim();
    }

    public String getFlight_category() {
        return flight_category;
    }

    public void setFlight_category(String flight_category) {
        this.flight_category = flight_category == null ? null : flight_category.trim();
    }

    public String getMovement_category() {
        return movement_category;
    }

    public void setMovement_category(String movement_category) {
        this.movement_category = movement_category == null ? null : movement_category.trim();
    }

    public Date getMovement_date() {
        return movement_date;
    }

    public void setMovement_date(Date movement_date) {
        this.movement_date = movement_date;
    }

    public String getDof() {
        return dof;
    }

    public void setDof(String dof) {
        this.dof = dof == null ? null : dof.trim();
    }

    public Integer getDistance_flown_in_fir() {
        return distance_flown_in_fir;
    }

    public void setDistance_flown_in_fir(Integer distance_flown_in_fir) {
        this.distance_flown_in_fir = distance_flown_in_fir;
    }

    public String getEntry_point() {
        return entry_point;
    }

    public void setEntry_point(String entry_point) {
        this.entry_point = entry_point == null ? null : entry_point.trim();
    }

    public Date getEntry_hour() {
        return entry_hour;
    }

    public void setEntry_hour(Date entry_hour) {
        this.entry_hour = entry_hour;
    }

    public String getExit_point() {
        return exit_point;
    }

    public void setExit_point(String exit_point) {
        this.exit_point = exit_point == null ? null : exit_point.trim();
    }

    public Date getExit_hour() {
        return exit_hour;
    }

    public void setExit_hour(Date exit_hour) {
        this.exit_hour = exit_hour;
    }

    public Boolean getSent_to_fdo() {
        return sent_to_fdo;
    }

    public void setSent_to_fdo(Boolean sent_to_fdo) {
        this.sent_to_fdo = sent_to_fdo;
    }

    public Integer getStorage_delay() {
        return storage_delay;
    }

    public void setStorage_delay(Integer storage_delay) {
        this.storage_delay = storage_delay;
    }

    public Integer getFix_list_length() {
        return fix_list_length;
    }

    public void setFix_list_length(Integer fix_list_length) {
        this.fix_list_length = fix_list_length;
    }

    public Integer getAssr_code() {
        return assr_code;
    }

    public void setAssr_code(Integer assr_code) {
        this.assr_code = assr_code;
    }

    public Integer getPssr_code() {
        return pssr_code;
    }

    public void setPssr_code(Integer pssr_code) {
        this.pssr_code = pssr_code;
    }

    public Date getEtd() {
        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    public Date getEta() {
        return eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    public Float getRfl() {
        return rfl;
    }

    public void setRfl(Float rfl) {
        this.rfl = rfl;
    }

    public Float getUfl() {
        return ufl;
    }

    public void setUfl(Float ufl) {
        this.ufl = ufl;
    }

    public Float getCfl() {
        return cfl;
    }

    public void setCfl(Float cfl) {
        this.cfl = cfl;
    }

    public String getExit_protocol() {
        return exit_protocol;
    }

    public void setExit_protocol(String exit_protocol) {
        this.exit_protocol = exit_protocol == null ? null : exit_protocol.trim();
    }

    public String getField10a() {
        return field10a;
    }

    public void setField10a(String field10a) {
        this.field10a = field10a == null ? null : field10a.trim();
    }

    public String getField10b() {
        return field10b;
    }

    public void setField10b(String field10b) {
        this.field10b = field10b == null ? null : field10b.trim();
    }

    public String getWtc() {
        return wtc;
    }

    public void setWtc(String wtc) {
        this.wtc = wtc == null ? null : wtc.trim();
    }

    public Date getEobt() {
        return eobt;
    }

    public void setEobt(Date eobt) {
        this.eobt = eobt;
    }

    public Date getEet() {
        return eet;
    }

    public void setEet(Date eet) {
        this.eet = eet;
    }

    public String getFpl_status() {
        return fpl_status;
    }

    public void setFpl_status(String fpl_status) {
        this.fpl_status = fpl_status == null ? null : fpl_status.trim();
    }

    public String getCfl_approach_mode() {
        return cfl_approach_mode;
    }

    public void setCfl_approach_mode(String cfl_approach_mode) {
        this.cfl_approach_mode = cfl_approach_mode == null ? null : cfl_approach_mode.trim();
    }

    public Integer getLast_fix_overflown() {
        return last_fix_overflown;
    }

    public void setLast_fix_overflown(Integer last_fix_overflown) {
        this.last_fix_overflown = last_fix_overflown;
    }

    public String getDcc_identifier() {
        return dcc_identifier;
    }

    public void setDcc_identifier(String dcc_identifier) {
        this.dcc_identifier = dcc_identifier == null ? null : dcc_identifier.trim();
    }

    public String getAircraft_operator_name() {
        return aircraft_operator_name;
    }

    public void setAircraft_operator_name(String aircraft_operator_name) {
        this.aircraft_operator_name = aircraft_operator_name == null ? null : aircraft_operator_name.trim();
    }

	@Override
	public String toString() {
		return "LocalFlightPlanDB [fpl_id=" + fpl_id + ", record_time="
				+ record_time + ", creation_time=" + creation_time
				+ ", flight_origin=" + flight_origin + ", callsign=" + callsign
				+ ", flight_plan_number=" + flight_plan_number + ", adep="
				+ adep + ", ades=" + ades + ", aircraft_type=" + aircraft_type
				+ ", aircraft_registration=" + aircraft_registration
				+ ", aircraft_address=" + aircraft_address + ", atd=" + atd
				+ ", ata=" + ata + ", flight_rule=" + flight_rule
				+ ", flight_type=" + flight_type + ", flight_category="
				+ flight_category + ", movement_category=" + movement_category
				+ ", movement_date=" + movement_date + ", dof=" + dof
				+ ", distance_flown_in_fir=" + distance_flown_in_fir
				+ ", entry_point=" + entry_point + ", entry_hour=" + entry_hour
				+ ", exit_point=" + exit_point + ", exit_hour=" + exit_hour
				+ ", sent_to_fdo=" + sent_to_fdo + ", storage_delay="
				+ storage_delay + ", fix_list_length=" + fix_list_length
				+ ", assr_code=" + assr_code + ", pssr_code=" + pssr_code
				+ ", etd=" + etd + ", eta=" + eta + ", rfl=" + rfl + ", ufl="
				+ ufl + ", cfl=" + cfl + ", exit_protocol=" + exit_protocol
				+ ", field10a=" + field10a + ", field10b=" + field10b
				+ ", wtc=" + wtc + ", eobt=" + eobt + ", eet=" + eet
				+ ", fpl_status=" + fpl_status + ", cfl_approach_mode="
				+ cfl_approach_mode + ", last_fix_overflown="
				+ last_fix_overflown + ", dcc_identifier=" + dcc_identifier
				+ ", aircraft_operator_name=" + aircraft_operator_name + "]";
	}

	@Override
	public int compareTo(LocalFlightPlanDB o) {
		// TODO Auto-generated method stub
		if(this.fpl_id > o.getFpl_id())
			return 1;
		else if(this.fpl_id < o.getFpl_id())
			return -1;
		else
			return 0;
	}
    
    
}