package com.best.bdaf.dao;

import java.util.ArrayList;

public class ComFplDB {
	
	private LocalFlightPlanDB lfp;
	private ArrayList<LocalFlightPlanFixesDB> lfpls;
	
	public ComFplDB(LocalFlightPlanDB lfp, ArrayList<LocalFlightPlanFixesDB> lfpls) {
		super();
		this.lfp = lfp;
		this.lfpls = lfpls;
	}

	public LocalFlightPlanDB getLfp() {
		return lfp;
	}

	public ArrayList<LocalFlightPlanFixesDB> getLfpls() {
		return lfpls;
	}
}
