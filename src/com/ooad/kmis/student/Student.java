package com.ooad.kmis.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.TableModel;

import com.ooad.kmis.User;

public class Student implements User{
	public String registrationNo;
	public String userName;
	public String firstName;
	public String lastName;
	public String gender;
	public String studentClass;
	public Date dateOfBirth;
	
	public Student() {
		this.registrationNo = "";
		this.userName = "";
	}
	
	public Student(String userId, String userName) {
		this.registrationNo = userId;
		this.userName = userName;
	}
	
	public Student(String userId, String firstName, String lastName, String username, Date dob) {
		this.registrationNo = userId;
		this.userName = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dob;
	}
	
	public Student(ResultSet resultSet) throws SQLException {
		this.registrationNo = resultSet.getString("reg_no");
		this.userName = resultSet.getString("user_name");
		this.firstName = resultSet.getString("first_name");
		this.lastName = resultSet.getString("last_name");
	}
	
	public ResultSet getProfile(Connection connection, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement = connection.prepareStatement("SELECT * FROM students WHERE reg_no = ? and user_name = ?");
		
		preparedStatement.setString(1, registrationNo);
		preparedStatement.setString(2, userName);
		ResultSet rs = preparedStatement.executeQuery();
		return rs;
	}
	
	public ResultSet getProfile() throws SQLException, ClassNotFoundException {
		Connection con;
		PreparedStatement preparedStatement;

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		
		preparedStatement = con.prepareStatement("SELECT * FROM students WHERE reg_no = ?");
		
		preparedStatement.setString(1, registrationNo);
		ResultSet rs = preparedStatement.executeQuery();

		return rs;
	}
	
	public int editProfile(Connection connection, PreparedStatement preparedStatement)  throws SQLException {
		preparedStatement = connection.prepareStatement("UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, user_name =? WHERE reg_no = ?");
		preparedStatement.setString(1, firstName);
		preparedStatement.setString(2, lastName);
		java.sql.Date sqlDate = new java.sql.Date(dateOfBirth.getTime());
		preparedStatement.setDate(3, sqlDate);
		preparedStatement.setString(4, userName);
		preparedStatement.setString(5, registrationNo);
		int res = preparedStatement.executeUpdate();
		preparedStatement.close();
		return res;
	}
	
	public int editProfile()  throws SQLException, ClassNotFoundException {
		Connection con;
		PreparedStatement preparedStatement;

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		
		preparedStatement = con.prepareStatement("UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, user_name =? WHERE reg_no = ?");
		preparedStatement.setString(1, firstName);
		preparedStatement.setString(2, lastName);
		java.sql.Date sqlDate = new java.sql.Date(dateOfBirth.getTime());
		preparedStatement.setDate(3, sqlDate);
		preparedStatement.setString(4, userName);
		preparedStatement.setString(5, registrationNo);
		int result = preparedStatement.executeUpdate();
		preparedStatement.close();
		return result;
	}
	
	public boolean changePassword(String newPassword)  throws SQLException, ClassNotFoundException {
		Connection con;
		PreparedStatement preparedStatement;

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		
		preparedStatement = con.prepareStatement("UPDATE students SET password = ? WHERE reg_no = ?");
		preparedStatement.setString(1, newPassword);
		preparedStatement.setString(2, registrationNo);
		
		int res = preparedStatement.executeUpdate();
		boolean result = false;
		if(res > 0) {
    		result = true;
    	}
		preparedStatement.close();
		return result;
	}
	
	public Student fromResultSet (ResultSet rs) throws SQLException {
		Student student = new Student();
		student.registrationNo = rs.getString("reg_no");
		student.userName = rs.getString("user_name");
		student.firstName = rs.getString("first_name");
		student.lastName = rs.getString("last_name");
		student.studentClass = rs.getString("class");
		String dbGender = rs.getString("gender").toString();
		//intern the string in order for the comparison to work.
		if(dbGender.intern()  == "M") { 
			student.gender = "Male";
		} else {
			student.gender = "Female";}
		
		java.sql.Date sqlDate = rs.getDate("date_of_birth");
		student.dateOfBirth = new Date(sqlDate.getTime());
    	
    	return student;
	}
	
	public Student fromTableModel(TableModel tM, int row) {
		Student student = new Student();
		student.registrationNo = tM.getValueAt(row, 0).toString();
		student.firstName = tM.getValueAt(row, 1).toString();
		student.lastName = tM.getValueAt(row, 2).toString();

		String dbGender = tM.getValueAt(row, 3).toString();
		//intern the string
		if(dbGender.intern() == "M") student.gender = "Male";
        else student.gender = "Female";

		java.sql.Date sqlDate = (java.sql.Date) tM.getValueAt(row, 4);
		student.dateOfBirth = new Date(sqlDate.getTime());
		
		student.studentClass = tM.getValueAt(row, 5).toString();
		student.userName = tM.getValueAt(row, 6).toString();
		
		return student;
	}
	
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		calendar2.setTime(dateOfBirth);
		int studentYOB = calendar2.get(Calendar.YEAR);
		return currentYear - studentYOB;
	}

}
