package com.ooad.kmis.teacher;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;


import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;

import com.ooad.kmis.student.Student;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentsPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String name = "Students";

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable studentsTable;
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(StudentsPage.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(StudentsPage.this, "Failed to connect to database");
		}
	}
	
	/**
	 * Create the panel.
	 */
	public StudentsPage() {
		Connect();
        this.setPreferredSize(new Dimension(800, 450));
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 179, 113));
        panel.setBounds(0, 0, 800, 33);
        add(panel);
        
        JButton btnRegisterStudents = new JButton("Register Students");
        btnRegisterStudents.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RegisterStudents registerStudentsForm = new RegisterStudents(StudentsPage.this);
				registerStudentsForm.setLocationRelativeTo(null);
        		registerStudentsForm.setVisible(true);
        	}
        });
        btnRegisterStudents.setForeground(new Color(0, 0, 0));
        btnRegisterStudents.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        panel.add(btnRegisterStudents);
        
        JButton btnSearchStudent = new JButton("Search students");
        panel.add(btnSearchStudent);
        btnSearchStudent.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Search searchPage = new Search(StudentsPage.this);
        		searchPage.setLocationRelativeTo(null);
        		searchPage.setVisible(true);
        	}
        });
        btnSearchStudent.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(23, 81, 759, 351);
        add(scrollPane);
        
        studentsTable = new JTable();
        studentsTable.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int row = studentsTable.getSelectedRow();
                Student thisStudent = new Student().fromTableModel(studentsTable.getModel(), row);
                EditStudent editStudent = new EditStudent(thisStudent, StudentsPage.this);
                editStudent.setLocationRelativeTo(null);
                editStudent.setVisible(true);
        		
        		
        	}
        });
        studentsTable.setFont(new Font("Arial Narrow", Font.PLAIN, 12));
        scrollPane.setViewportView(studentsTable);
        //load student data into table
		loadAllStudents();
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		loadAllStudents();
        	}
        });
        btnRefresh.setBounds(21, 40, 75, 29);
        add(btnRefresh);

	}
	
	public void loadAllStudents() {
		try {
			pst = con.prepareStatement("SELECT reg_no, first_name, last_name, gender, date_of_birth, class, user_name FROM students");
			rs = pst.executeQuery();
			//use the DButils jar package for populating student data.
			//automatically into the table.
			studentsTable.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(StudentsPage.this, "Failed to load student data");
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(StudentsPage.this, "Failed to load student data");
		}
	}
}
