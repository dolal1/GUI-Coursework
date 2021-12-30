package com.ooad.kmis.student;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ProfilePage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String name = "profile";
	private static Student student;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(ProfilePage.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(ProfilePage.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the panel.
	 */
	public ProfilePage(Student st) {
		student = st;
		Connect();
        this.setPreferredSize(new Dimension(800, 450));
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 240, 230));
        panel.setBounds(56, 55, 195, 189);
        add(panel);
        
        JButton btnEditProfile = new JButton("Edit Profile");
        btnEditProfile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                EditProfile editProfile = new EditProfile(student, ProfilePage.this);
                editProfile.setLocationRelativeTo(null);
                editProfile.setVisible(true);
        	}
        });
        btnEditProfile.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        btnEditProfile.setBounds(103, 271, 119, 29);
        add(btnEditProfile);
        
        JButton btnChangeCredentials = new JButton("Change Password");
        btnChangeCredentials.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                EditPassword editPassword = new EditPassword(student);
                editPassword.setLocationRelativeTo(null);
                editPassword.setVisible(true);
        	}
        });
        btnChangeCredentials.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        btnChangeCredentials.setBounds(103, 312, 119, 29);
        add(btnChangeCredentials);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(283, 55, 434, 294);
        add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Registration No");
        lblNewLabel.setBounds(21, 24, 111, 16);
        panel_1.add(lblNewLabel);
        
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(144, 24, 19, 174);
        panel_1.add(separator);
        
        JLabel lblLastName = new JLabel("First Name");
        lblLastName.setBounds(21, 55, 79, 16);
        panel_1.add(lblLastName);
        
        JLabel lblLastName_1 = new JLabel("Last Name");
        lblLastName_1.setBounds(21, 86, 79, 16);
        panel_1.add(lblLastName_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("Gender");
        lblNewLabel_1_1.setBounds(21, 114, 79, 16);
        panel_1.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("Date of birth");
        lblNewLabel_1_1_1.setBounds(21, 142, 111, 16);
        panel_1.add(lblNewLabel_1_1_1);
        
        JLabel lblNewLabel_1_1_1_1 = new JLabel("Class");
        lblNewLabel_1_1_1_1.setBounds(21, 170, 111, 16);
        panel_1.add(lblNewLabel_1_1_1_1);
        
        JLabel txtRegNo = new JLabel(""+student.registrationNo);
        txtRegNo.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtRegNo.setBounds(175, 24, 215, 16);
        panel_1.add(txtRegNo);
        
        JLabel txtFirstName = new JLabel(""+student.firstName);
        txtFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtFirstName.setBounds(175, 55, 215, 16);
        panel_1.add(txtFirstName);
        
        JLabel txtLastName = new JLabel(""+student.lastName);
        txtLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtLastName.setBounds(175, 86, 215, 16);
        panel_1.add(txtLastName);
        
        JLabel txtGender = new JLabel(""+student.gender);
        txtGender.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtGender.setBounds(175, 114, 215, 16);
        panel_1.add(txtGender);
        
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = "";
		if(student.dateOfBirth != null) {
			strDate = formatter.format(student.dateOfBirth);
		}
		
        JLabel txtDateOfBirth = new JLabel(String.format("%s", strDate));
        txtDateOfBirth.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtDateOfBirth.setBounds(175, 142, 215, 16);
        panel_1.add(txtDateOfBirth);
        
        JLabel txtClass = new JLabel(""+student.studentClass);
        txtClass.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtClass.setBounds(175, 170, 215, 16);
        panel_1.add(txtClass);
        
        JButton btnRefreshProfile = new JButton("Refresh Profile");
        btnRefreshProfile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		reloadStudentProfile();
        		txtRegNo.setText(student.registrationNo);
        		txtFirstName.setText(student.firstName);
        		txtLastName.setText(student.lastName);
        		txtGender.setText(student.gender);
        		
        		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        		String strDate = "";
        		if(student.dateOfBirth != null) {
        			strDate = formatter.format(student.dateOfBirth);
        		}
        		txtDateOfBirth.setText(String.format("%s", strDate));
        		txtClass.setText(student.studentClass);
        	}
        });
        btnRefreshProfile.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        btnRefreshProfile.setBounds(92, 259, 119, 29);
        panel_1.add(btnRefreshProfile);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(21, 42, 346, 16);
        panel_1.add(separator_1);
        
        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setBounds(21, 72, 346, 16);
        panel_1.add(separator_1_1);
        
        JSeparator separator_1_2 = new JSeparator();
        separator_1_2.setBounds(21, 102, 346, 16);
        panel_1.add(separator_1_2);
        
        JSeparator separator_1_3 = new JSeparator();
        separator_1_3.setBounds(21, 130, 346, 16);
        panel_1.add(separator_1_3);
        
        JSeparator separator_1_4 = new JSeparator();
        separator_1_4.setBounds(21, 158, 346, 16);
        panel_1.add(separator_1_4);
        
        
        

	}
	
	public void reloadStudentProfile() {
		try {
			ResultSet rs = student.getProfile();
			rs.next();
			Student newStudent = student.fromResultSet(rs);
			student = newStudent;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ProfilePage.this, "Failed to load student profile");
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ProfilePage.this, "Failed to load student profile");
		}
	}
}
