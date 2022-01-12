package com.ooad.kmis.teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.TableModel;

import com.ooad.kmis.GUtilities;
import com.ooad.kmis.User;

public class Teacher implements User{
	public String userId;
	public String userName;
	public String firstName;
	public String lastName;
	public String subject;
	
	public Teacher() {
		this.userId = "";
		this.userName = "";
	}
	
	public Teacher(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	
	public Teacher(ResultSet resultSet) throws SQLException {
		this.userId = resultSet.getString("id");
		this.userName = resultSet.getString("user_name");
		this.firstName = resultSet.getString("first_name");
		this.lastName = resultSet.getString("last_name");
		String subjectsString = resultSet.getString("subject");
//		this.subject = subjectsString.split(",");
		this.subject = subjectsString;
	}
	
//	public ResultSet getProfile(Connection connection, PreparedStatement preparedStatement) throws SQLException {
//		preparedStatement = connection.prepareStatement("SELECT * FROM teachers WHERE id = ? and user_name = ?");
//		
//		preparedStatement.setString(1, userId);
//		preparedStatement.setString(2, userName);
//		ResultSet rs = preparedStatement.executeQuery();
//    	return rs;
//	}
	
	public ResultSet getProfile() throws SQLException, ClassNotFoundException {
		Connection con;
		PreparedStatement preparedStatement;

		Class.forName(GUtilities.driver);
		con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		
		preparedStatement = con.prepareStatement("SELECT * FROM teachers WHERE id = ? and user_name = ?");
		
		preparedStatement.setString(1, userId);
		preparedStatement.setString(2, userName);
		ResultSet rs = preparedStatement.executeQuery();
    	
    	return rs;
	}
	
	public int editProfile()  throws SQLException, ClassNotFoundException {
		Connection connection;
		PreparedStatement preparedStatement;

		Class.forName(GUtilities.driver);
		connection = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		
		preparedStatement = connection.prepareStatement("UPDATE teachers SET first_name = ?, last_name = ?, subject = ?, user_name =? WHERE id = ?");
		preparedStatement.setString(1, firstName);
		preparedStatement.setString(2, lastName);
		String subjectsString = String.join(",", subject);
		preparedStatement.setString(3, subjectsString);
		preparedStatement.setString(4, userName);
		preparedStatement.setString(5, userId);
		int result = preparedStatement.executeUpdate();
    	return result;
	}
	
	public boolean changePassword(String newPassword)  throws SQLException, ClassNotFoundException {
		Connection con;
		PreparedStatement preparedStatement;

		Class.forName(GUtilities.driver);
		con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		
		preparedStatement = con.prepareStatement("UPDATE teachers SET password = ? WHERE id = ?");
		preparedStatement.setString(1, newPassword);
		preparedStatement.setString(2, userId);
		
		int res = preparedStatement.executeUpdate();
		boolean result = false;
		if(res > 0) {
    		result = true;
    	}
		preparedStatement.close();
		return result;
	}
	
	public Teacher fromResultSet (ResultSet rs) throws SQLException {
		Teacher teacher = new Teacher();
		String subjectsString;
    	if(rs.next()) {
    		teacher.userId = rs.getString("id");
    		teacher.userName = rs.getString("user_name");
    		teacher.firstName = rs.getString("first_name");
    		teacher.lastName = rs.getString("last_name");
    		subjectsString = rs.getString("subject");
//    		subject = subjectsString.split(",");
    		teacher.subject = subjectsString;
    		
    		
    	}
    	
    	return teacher;
	}
	
	public Teacher fromTableModel(TableModel tM, int row) {
		Teacher teacher = new Teacher();
		teacher.userId = tM.getValueAt(row, 0).toString();
		teacher.firstName = tM.getValueAt(row, 1).toString();
		teacher.lastName = tM.getValueAt(row, 2).toString();
		teacher.subject = tM.getValueAt(row, 3).toString();
		teacher.userName = tM.getValueAt(row, 4).toString();
		
		return teacher;
	}

}
