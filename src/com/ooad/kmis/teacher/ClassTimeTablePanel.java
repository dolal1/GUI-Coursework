package com.ooad.kmis.teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ooad.kmis.GUtilities;
import com.ooad.kmis.Subjects;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClassTimeTablePanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	public static String thisClass;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;

	public void Connect() {
		try {
			Class.forName(GUtilities.driver);
			con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(ClassTimeTablePanel.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ClassTimeTablePanel.this, "Failed to connect to database");
		}
	}
	
	public ClassTimeTablePanel(String tClass) {
		Connect();
		thisClass = tClass;
		final String finalprovidedClass = tClass;
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(6, 6, 219, 395);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblCreateShedule = new JLabel("Create Shedule");
		lblCreateShedule.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		lblCreateShedule.setBounds(68, 17, 103, 16);
		panel.add(lblCreateShedule);
		
		JLabel lblDay = new JLabel("Day");
		lblDay.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblDay.setBounds(18, 78, 33, 16);
		panel.add(lblDay);
		
		JComboBox<String> txtDay = new JComboBox<String>();
		txtDay.setModel(new DefaultComboBoxModel<String>(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}));
		txtDay.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtDay.setBounds(86, 74, 130, 27);
		panel.add(txtDay);
		
		JLabel lblampm = new JLabel("7am - 10pm");
		lblampm.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblampm.setBounds(18, 129, 67, 16);
		panel.add(lblampm);
		
		JComboBox<String> txt7_10 = new JComboBox<String>();
		txt7_10.setModel(new DefaultComboBoxModel<String>(new String[] {Subjects.english, Subjects.math, Subjects.sst, Subjects.science}));
		txt7_10.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txt7_10.setBounds(86, 125, 130, 27);
		panel.add(txt7_10);
		
		JComboBox<String> txt11_1 = new JComboBox<String>();
		txt11_1.setModel(new DefaultComboBoxModel<String>(new String[] {Subjects.english, Subjects.math, Subjects.sst, Subjects.science}));
		txt11_1.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txt11_1.setBounds(86, 157, 130, 27);
		panel.add(txt11_1);
		
		JComboBox<String> txt2_5 = new JComboBox<String>();
		txt2_5.setModel(new DefaultComboBoxModel<String>(new String[] {Subjects.english, Subjects.math, Subjects.sst, Subjects.science}));
		txt2_5.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txt2_5.setBounds(86, 189, 130, 27);
		panel.add(txt2_5);
		
		JLabel lblampm_3 = new JLabel("11am - 1pm");
		lblampm_3.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblampm_3.setBounds(18, 161, 67, 16);
		panel.add(lblampm_3);
		
		JLabel lblpmpm = new JLabel("2pm - 5pm");
		lblpmpm.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblpmpm.setBounds(18, 193, 67, 16);
		panel.add(lblpmpm);
		
		JButton btnAddToTimetable = new JButton("Add to timetable");
		btnAddToTimetable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tDay = txtDay.getSelectedItem().toString();
				String t7_10 = txt7_10.getSelectedItem().toString();
				String t11_1 = txt11_1.getSelectedItem().toString();
				String t2_5 = txt2_5.getSelectedItem().toString();
				
				try {
                    pst = con.prepareStatement("INSERT INTO timetables (class, day, 7am_10am, 11am_1pm, 2pm_5pm) VALUES (?,?,?,?,?)");
                    pst.setString(1, finalprovidedClass);
                    pst.setString(2, tDay);
                    pst.setString(3, t7_10);
                    pst.setString(4, t11_1);
                    pst.setString(5, t2_5);
                    pst.executeUpdate();
                    
                    JOptionPane.showMessageDialog(ClassTimeTablePanel.this, "Schedule added succesfully");
                    
                    txtDay.setSelectedIndex(-1);
                    txt7_10.setSelectedIndex(-1);
                    txt11_1.setSelectedIndex(-1);
                    txt2_5.setSelectedIndex(-1);
                    

            		thisClass = finalprovidedClass;
                    loadShedule();
                } catch (SQLException sexp) {
                	Logger.getLogger(ClassTimeTablePanel.class.getName()).log(Level.SEVERE, null, sexp);
                }
                
                
			}
		});
		btnAddToTimetable.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnAddToTimetable.setBounds(54, 252, 117, 29);
		panel.add(btnAddToTimetable);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(226, 6, 431, 288);
		add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 419, 276);
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		//load class schedule into table
		thisClass = finalprovidedClass;
		loadShedule();

	}
	
	public void loadShedule() {
		try {
			pst = con.prepareStatement("SELECT day, 7am_10am, 11am_1pm, 2pm_5pm FROM timetables WHERE class = ?");
            pst.setString(1, thisClass);
			rs = pst.executeQuery();
			//use the DButils jar package for populating student data.
			//automatically into the table.
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ClassTimeTablePanel.this, "Failed to load student data");
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ClassTimeTablePanel.this, "Failed to load student data");
		}
	}
}
