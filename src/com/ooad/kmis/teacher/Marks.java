package com.ooad.kmis.teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.TableModel;


public class Marks {
	public String subject;
	public String registrationNo;
	public String theClass;
	public String year;
	public String term;
	public String bot;
	public String mot;
	public String eot;
	
	private Marks() {
		
	}
	
	public Marks(String subject, String year, String term, String sClass) {
		this.subject = subject;
		this.year = year;
		this.term = term;
		this.theClass = sClass;
	}
	
	public Marks fromResultSet (ResultSet rs) throws SQLException {
		Marks marks = new Marks();
		marks.subject = subject;
		marks.registrationNo = rs.getString("reg_no");
		marks.theClass = rs.getString("class");
		marks.year = rs.getString("year");
		marks.term = rs.getString("term");
		marks.bot = rs.getString("BOT");
		marks.mot = rs.getString("MOT");
		marks.eot = rs.getString("EOT");
		
		return marks;
		
	}
	
	public Marks fromTableModel(TableModel tM, int row) {
		Marks marks = new Marks();
		marks.subject = subject;
		marks.theClass = theClass;
		marks.year = year;
		marks.term = term;
		marks.registrationNo = tM.getValueAt(row, 0).toString();
		marks.bot = tM.getValueAt(row, 1).toString();
		marks.mot = tM.getValueAt(row, 2).toString();
		marks.eot = tM.getValueAt(row, 3).toString();
		
		return marks;
	}

}
