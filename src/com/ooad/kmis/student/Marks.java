package com.ooad.kmis.student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Marks {
	public String subject;
	public String year;
	public String term;
	public String bot;
	public String mot;
	public String eot;
	
	public Marks() {
		
	}
	
	public Marks(String subject, String year, String term) {
		this.subject = subject;
		this.year = year;
		this.term = term;
		
	}
	
	public Marks fromResultSet (ResultSet rs) throws SQLException {
		Marks marks = new Marks();
		marks.subject = subject;
		marks.year = year;
		marks.term = term;
		marks.bot = rs.getString("BOT");
		marks.mot = rs.getString("MOT");
		marks.eot = rs.getString("EOT");
		
		return marks;
		
	}

}
