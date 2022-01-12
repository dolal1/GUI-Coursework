package com.ooad.kmis;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface User {
	public ResultSet getProfile() throws SQLException, ClassNotFoundException  ;
	public int editProfile() throws SQLException, ClassNotFoundException;
	public boolean changePassword(String newPassword) throws SQLException, ClassNotFoundException ;
}
