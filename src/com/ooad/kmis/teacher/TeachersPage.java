package com.ooad.kmis.teacher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

public class TeachersPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String name = "Teachers";

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable teachersTable;
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(TeachersPage.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(TeachersPage.this, "Failed to connect to database");
		}
	}
	
	/**
	 * Create the panel.
	 */
	public TeachersPage() {
		Connect();
        this.setPreferredSize(new Dimension(800, 450));
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 179, 113));
        panel.setBounds(0, 0, 800, 33);
        add(panel);
        
        JButton btnRegisterTeachers = new JButton("Register Teachers");
        btnRegisterTeachers.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RegisterTeachers registerTeachersForm = new RegisterTeachers(TeachersPage.this);
        		registerTeachersForm.setLocationRelativeTo(null);
        		registerTeachersForm.setVisible(true);
        	}
        });
        btnRegisterTeachers.setForeground(new Color(0, 0, 0));
        btnRegisterTeachers.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        panel.add(btnRegisterTeachers);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(23, 81, 759, 351);
        add(scrollPane);
        
        teachersTable = new JTable();
        teachersTable.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int row = teachersTable.getSelectedRow();
                Teacher thisTeacher = new Teacher().fromTableModel(teachersTable.getModel(), row);
                EditTeacher editTeacher = new EditTeacher(thisTeacher, TeachersPage.this);
                editTeacher.setVisible(true);
        		
        		
        	}
        });
        teachersTable.setFont(new Font("Arial Narrow", Font.PLAIN, 12));
        scrollPane.setViewportView(teachersTable);
        //load teacher data into table
		loadAllTeachers();
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		loadAllTeachers();
        	}
        });
        btnRefresh.setBounds(21, 40, 75, 29);
        add(btnRefresh);

	}
	
	public void loadAllTeachers() {
		try {
			pst = con.prepareStatement("SELECT id, first_name, last_name, subject, user_name FROM teachers");
			rs = pst.executeQuery();
			//use the DButils jar package for populating teacher data.
			//automatically into the table.
			teachersTable.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(TeachersPage.this, "Failed to load teacher data");
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(TeachersPage.this, "Failed to load teacher data");
		}
	}
}
