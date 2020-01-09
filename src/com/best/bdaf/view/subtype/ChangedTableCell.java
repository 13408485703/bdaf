package com.best.bdaf.view.subtype;

public class ChangedTableCell {
	
	public String rowName;
	public String columnName;
	
	public ChangedTableCell(String rowName, String columnName) {
		super();
		this.rowName = rowName;
		this.columnName = columnName;
	}

	@Override
	public String toString() {
		return "ChangedTableCell [rowName=" + rowName + ", columnName="
				+ columnName + "]";
	}
	
	
}
