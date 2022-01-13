package com.ooad.kmis.student;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ooad.kmis.GUtilities;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;

public class Timetable extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String name = "timetable";
	private static Student student;

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;
	public void Connect() {
		try {
			Class.forName(GUtilities.driver);
			con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(Timetable.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(Timetable.this, "Failed to connect to database");
		}
	}
	
	public Timetable(Student st) {
		student = st;
		Connect();
        this.setPreferredSize(new Dimension(800, 450));
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 179, 113));
        panel.setBounds(0, 0, 800, 33);
        add(panel);
        
        JLabel lblClass = new JLabel("Class : "+ student.studentClass);
        lblClass.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        panel.add(lblClass);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(89, 76, 539, 344);
        add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);

        try {
			pst = con.prepareStatement("SELECT day, 7am_10am, 11am_1pm, 2pm_5pm FROM timetables WHERE class = ?");
	        pst.setString(1, student.studentClass);
			rs = pst.executeQuery();
			//use the DButils jar package for populating student data.
			//automatically into the table.
			table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
