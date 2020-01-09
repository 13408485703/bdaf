package com.best.bdaf.dao;

import java.util.Date;

public class LocalFlightPlanFixesDB implements Comparable<LocalFlightPlanFixesDB>{
	
	public enum E_fix_kind {RP,CP,GP,UPR,EOC_POINT,EXT_POINT,ENT_POINT,
		TRANS_POINT,AIRPORT,HLD_POINT,FPSA_ENT,FPSA_EXT,APR_FIX,
		PCFL_TAS_FIX,DPE_POINT,METEO_POINT,CALIBRATED_POINT,EOA_POINT};
	
	
    private Integer fix_id;

    private Date record_time;

    private String fix_kind;

    private String fix_name;

    private Date displayed_eto;

    private String current_functional_sector;

    private String next_functional_sector;

    private String sector_changed;

    private String current_sector;

    private Date reference_eto;

    private String flight_rule_after_fix;

    private Float distance_from_previous_fix;

    private String geoposition;

    private Float actual_flight_level;

    private Float pre_cleared_flight_level;

    private Integer associated_flight_plan;

    private String fix_name_and_pos;

    private Integer fpl_id;

    private Integer fix_index;

    public Integer getFix_id() {
        return fix_id;
    }

    public void setFix_id(Integer fix_id) {
        this.fix_id = fix_id;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public String getFix_kind() {
        return fix_kind;
    }

    public void setFix_kind(String fix_kind) {
        this.fix_kind = fix_kind == null ? null : fix_kind.trim();
    }

    public String getFix_name() {
        return fix_name;
    }

    public void setFix_name(String fix_name) {
        this.fix_name = fix_name == null ? null : fix_name.trim();
    }

    public Date getDisplayed_eto() {
        return displayed_eto;
    }

    public void setDisplayed_eto(Date displayed_eto) {
        this.displayed_eto = displayed_eto;
    }

    public String getCurrent_functional_sector() {
        return current_functional_sector;
    }

    public void setCurrent_functional_sector(String current_functional_sector) {
        this.current_functional_sector = current_functional_sector == null ? null : current_functional_sector.trim();
    }

    public String getNext_functional_sector() {
        return next_functional_sector;
    }

    public void setNext_functional_sector(String next_functional_sector) {
        this.next_functional_sector = next_functional_sector == null ? null : next_functional_sector.trim();
    }

    public String getSector_changed() {
        return sector_changed;
    }

    public void setSector_changed(String sector_changed) {
        this.sector_changed = sector_changed == null ? null : sector_changed.trim();
    }

    public String getCurrent_sector() {
        return current_sector;
    }

    public void setCurrent_sector(String current_sector) {
        this.current_sector = current_sector == null ? null : current_sector.trim();
    }

    public Date getReference_eto() {
        return reference_eto;
    }

    public void setReference_eto(Date reference_eto) {
        this.reference_eto = reference_eto;
    }

    public String getFlight_rule_after_fix() {
        return flight_rule_after_fix;
    }

    public void setFlight_rule_after_fix(String flight_rule_after_fix) {
        this.flight_rule_after_fix = flight_rule_after_fix == null ? null : flight_rule_after_fix.trim();
    }

    public Float getDistance_from_previous_fix() {
        return distance_from_previous_fix;
    }

    public void setDistance_from_previous_fix(Float distance_from_previous_fix) {
        this.distance_from_previous_fix = distance_from_previous_fix;
    }

    public String getGeoposition() {
        return geoposition;
    }

    public void setGeoposition(String geoposition) {
        this.geoposition = geoposition == null ? null : geoposition.trim();
    }

    public Float getActual_flight_level() {
        return actual_flight_level;
    }

    public void setActual_flight_level(Float actual_flight_level) {
        this.actual_flight_level = actual_flight_level;
    }

    public Float getPre_cleared_flight_level() {
        return pre_cleared_flight_level;
    }

    public void setPre_cleared_flight_level(Float pre_cleared_flight_level) {
        this.pre_cleared_flight_level = pre_cleared_flight_level;
    }

    public Integer getAssociated_flight_plan() {
        return associated_flight_plan;
    }

    public void setAssociated_flight_plan(Integer associated_flight_plan) {
        this.associated_flight_plan = associated_flight_plan;
    }

    public String getFix_name_and_pos() {
        return fix_name_and_pos;
    }

    public void setFix_name_and_pos(String fix_name_and_pos) {
        this.fix_name_and_pos = fix_name_and_pos == null ? null : fix_name_and_pos.trim();
    }

    public Integer getFpl_id() {
        return fpl_id;
    }

    public void setFpl_id(Integer fpl_id) {
        this.fpl_id = fpl_id;
    }

    public Integer getFix_index() {
        return fix_index;
    }

    public void setFix_index(Integer fix_index) {
        this.fix_index = fix_index;
    }

	@Override
	public String toString() {
		return "LocalFlightPlanFixesDB [fix_id=" + fix_id + ", record_time="
				+ record_time + ", fix_kind=" + fix_kind + ", fix_name="
				+ fix_name + ", displayed_eto=" + displayed_eto
				+ ", current_functional_sector=" + current_functional_sector
				+ ", next_functional_sector=" + next_functional_sector
				+ ", sector_changed=" + sector_changed + ", current_sector="
				+ current_sector + ", reference_eto=" + reference_eto
				+ ", flight_rule_after_fix=" + flight_rule_after_fix
				+ ", distance_from_previous_fix=" + distance_from_previous_fix
				+ ", geoposition=" + geoposition + ", actual_flight_level="
				+ actual_flight_level + ", pre_cleared_flight_level="
				+ pre_cleared_flight_level + ", associated_flight_plan="
				+ associated_flight_plan + ", fix_name_and_pos="
				+ fix_name_and_pos + ", fpl_id=" + fpl_id + ", fix_index="
				+ fix_index + "]";
	}

	@Override
	public int compareTo(LocalFlightPlanFixesDB o) {
		// TODO Auto-generated method stub
		if(this.fix_index > o.getFix_index())
			return 1;
		else if(this.fix_index < o.getFix_index())
			return -1;
		else
			return 0;
	}
}