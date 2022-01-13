package com.ooad.kmis.teacher;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ooad.kmis.GUtilities;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class TimetablePage extends JPanel {
	private static final long serialVersionUID = 1L;
	public static String name = "timetable";

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try {
			Class.forName(GUtilities.driver);
			con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(TimetablePage.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(TimetablePage.this, "Failed to connect to database");
		}
	}
	
	public TimetablePage() {
		Connect();
        this.setPreferredSize(new Dimension(800, 450));
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 179, 113));
        panel.setBounds(0, 0, 800, 33);
        add(panel);
        
        JLabel lblNewLabel = new JLabel("Timetables");
        lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
        panel.add(lblNewLabel);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(26, 85, 753, 337);
        add(tabbedPane);
        
        JPanel panel_1 = new ClassTimeTablePanel("P1");
        tabbedPane.addTab("P1", null, panel_1, null);
        
        JPanel panel_2 = new ClassTimeTablePanel("P2");
		tabbedPane.addTab("P2", null, panel_2, null);

        JPanel panel_3 = new ClassTimeTablePanel("P3");
		tabbedPane.addTab("P3", null, panel_3, null);
		
        JPanel panel_4 = new ClassTimeTablePanel("P4");
		tabbedPane.addTab("P4", null, panel_4, null);
		
        JPanel panel_5 = new ClassTimeTablePanel("P5");
		tabbedPane.addTab("P5", null, panel_5, null);
		
        JPanel panel_6 = new ClassTimeTablePanel("P6");
		tabbedPane.addTab("P6", null, panel_6, null);
		
        JPanel panel_7 = new ClassTimeTablePanel("P7");
		tabbedPane.addTab("P7", null, panel_7, null);

	}
}
