package com.best.bdaf.dao;

import com.best.bdaf.data.BestUtils;


public class Skp implements Cloneable {
	
	public enum LEVEL_TYPE_T {DEFAULT,F,S,A,M,VFR,NONE}
	public enum SPEED_TYPE_T {DEFAULT,KNOT_E,MACH_E,KMHOUR_E,NONE};
	public enum SSR_MODE {NO_MODE,MODE_A,MODE_B,MODE_C,MODE_1,MODE_2};      
	public enum GROUP_TYPE {G1,G2,G3,G4,G5,G6,G7,G8,G9,G10,NO_GROUP};
	public enum FPL_MES_TYPE_T {ICAO,AIDC,OLDI,ADEXP};
	public enum GENERIC_UNIT_SETTING_TYPE {UNKNOWN,METRIC,IMPERIAL,METRIC_MIXED,IMPERIAL_MIXED};
	
	private String callsign;
	private String actype;
	private int etd;
	private int aat;
	private int fl_m;  //10米
	private float fl_im; //英尺
	private int tas_m; //公里小时
	private float tas_im; //海里小时
	private String adep;
	private String dest;
	private int ssra;
	private int ssrb;
	private String remarks;
	private int eet;
	private int frules;
	private String route;
	private int pilot;
	private int requested_fl_m;
	private float requested_fl_im;
	private int cfl_m;
	private float cfl_im;
	private String field10a;
	private String field10b;
	private int time_for_est = 0;
	private String surveillance_equipment;
	
	public int getEtdFromString(String str)
	{
		int hour = Integer.parseInt(str.substring(0,2));
		int min = Integer.parseInt(str.substring(2,4));
		
		return hour*3600+min*60;
	}
	public String getEtdToString()
	{
		int mins = Math.abs(etd)/60;
		int hour = mins/60;
		int min = mins%60;
		
		return String.format("%1$02d%2$02d", hour, min);
	}
	public int getAatFromString(String str)
	{
		int hour = Integer.parseInt(str.substring(0,2));
		int min = Integer.parseInt(str.substring(2,4));
		
		return hour*3600+min*60;
	}
	public int getTime_for_estFromString(String str)
	{
		int hour = Integer.parseInt(str.substring(0,2));
		int min = Integer.parseInt(str.substring(2,4));
		
		return hour*3600+min*60;
	}
	public String getAatToString()
	{
		int mins = Math.abs(aat)/60;
		int hour = mins/60;
		int min = mins%60;
		
		return String.format("%1$02d%2$02d", hour, min);
	}
	public String getTime_for_estToString()
	{
		int mins = Math.abs(time_for_est)/60;
		int hour = mins/60;
		int min = mins%60;
		
		return String.format("%1$02d%2$02d", hour, min);
	}
	public int getEetFromString(String str)
	{
		int hour = Integer.parseInt(str.substring(0,2));
		int min = Integer.parseInt(str.substring(2,4));
		
		return hour*3600+min*60;
	}
	public String getEetToString()
	{
		int mins = Math.abs(eet)/60;
		int hour = mins/60;
		int min = mins%60;
		
		return String.format("%1$02d%2$02d", hour, min);
	}
	public int getFlFromString(String str)
	{
		return Integer.parseInt(str.substring(1));
	}
	public String getFlToString()
	{
		return String.format("S%04d", fl_m);
	}
	public int getTasFromString(String str)
	{
		return Integer.parseInt(str.substring(1));
	}
	public String getTasToString()
	{
		return String.format("K%04d", tas_m);
	}
	public int getRequest_flFromString(String str)
	{
		return Integer.parseInt(str.substring(1));
	}
	public String getRequest_flToString()
	{
		return String.format("S%04d", requested_fl_m);
	}
	public int getCflFromString(String str)
	{
		return Integer.parseInt(str.substring(1));
	}
	public String getCflToString()
	{
		return String.format("S%04d", cfl_m);
	}
	
	public String getCallsign() {
		return callsign;
	}
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}
	public String getActype() {
		return actype;
	}
	public void setActype(String actype) {
		this.actype = actype;
	}
	public int getEtd() {
		return etd;
	}
	public void setEtd(int etd) {
		this.etd = etd;
	}
	public int getAat() {
		return aat;
	}
	public void setAat(int aat) {
		this.aat = aat;
	}
	public int getFl_m() {
		return fl_m;
	}
	public void setFl_m(int fl_m) {
		this.fl_m = fl_m;
	}
	public float getFl_im() {
		return fl_im;
	}
	public void setFl_im(float fl_im) {
		this.fl_im = fl_im;
	}
	public int getTas_m() {
		return tas_m;
	}
	public void setTas_m(int tas_m) {
		this.tas_m = tas_m;
	}
	public float getTas_im() {
		return tas_im;
	}
	public void setTas_im(float tas_im) {
		this.tas_im = tas_im;
	}
	public String getAdep() {
		return adep;
	}
	public void setAdep(String adep) {
		this.adep = adep;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public int getSsra() {
		return ssra;
	}
	public void setSsra(int ssra) {
		this.ssra = ssra;
	}
	public int getSsrb() {
		return ssrb;
	}
	public void setSsrb(int ssrb) {
		this.ssrb = ssrb;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getEet() {
		return eet;
	}
	public void setEet(int eet) {
		this.eet = eet;
	}
	public int getFrules() {
		return frules;
	}
	public void setFrules(int frules) {
		this.frules = frules;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public int getPilot() {
		return pilot;
	}
	public void setPilot(int pilot) {
		this.pilot = pilot;
	}
	public int getRequested_fl_m() {
		return requested_fl_m;
	}
	public void setRequested_fl_m(int requested_fl_m) {
		this.requested_fl_m = requested_fl_m;
	}
	public float getRequested_fl_im() {
		return requested_fl_im;
	}
	public void setRequested_fl_im(float requested_fl_im) {
		this.requested_fl_im = requested_fl_im;
	}	
	public int getCfl_m() {
		return cfl_m;
	}
	public void setCfl_m(int cfl_m) {
		this.cfl_m = cfl_m;
	}
	public float getCfl_im() {
		return cfl_im;
	}
	public void setCfl_im(float cfl_im) {
		this.cfl_im = cfl_im;
	}
	public String getField10a() {
		return field10a;
	}
	public void setField10a(String field10a) {
		this.field10a = field10a;
	}
	public String getField10b() {
		return field10b;
	}
	public void setField10b(String field10b) {
		this.field10b = field10b;
	}
	public int getTime_for_est() {
		return time_for_est;
	}
	public void setTime_for_est(int time_for_est) {
		this.time_for_est = time_for_est;
	}
	public String getSurveillance_equipment() {
		return surveillance_equipment;
	}
	public void setSurveillance_equipment(String surveillance_equipment) {
		this.surveillance_equipment = surveillance_equipment;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	public String toString() {
		return "Skp [callsign=" + callsign + ", actype=" + actype + ", etd="
				+ etd + ", aat=" + aat + ", fl_m=" + fl_m + ", fl_im=" + fl_im
				+ ", tas_m=" + tas_m + ", tas_im=" + tas_im + ", adep=" + adep
				+ ", dest=" + dest + ", ssra=" + ssra + ", ssrb=" + ssrb
				+ ", remarks=" + remarks + ", eet=" + eet + ", frules="
				+ frules + ", route=" + route + ", pilot=" + pilot
				+ ", requested_fl_m=" + requested_fl_m + ", requested_fl_im="
				+ requested_fl_im + ", cfl_m=" + cfl_m + ", cfl_im=" + cfl_im
				+ ", field10a=" + field10a + ", field10b=" + field10b
				+ ", time_for_est=" + time_for_est
				+ ", surveillance_equipment=" + surveillance_equipment + "]";
	}
	
	
}
