package com.ooad.kmis.student;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import com.ooad.kmis.auth.Login;

import javax.swing.SwingConstants;

public class DashboardMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public DashboardMenu(JPanel cards, JFrame mainFame, Student student) {
		this.setBackground(new Color(153, 204, 153));
//		this.setBounds(0, 31, 156, 365);
        this.setPreferredSize(new Dimension(182, 500));
        
        JButton btnShowProfile = new JButton("Profile");
        btnShowProfile.setPreferredSize(new Dimension(150, 20));
        btnShowProfile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, ProfilePage.name);
            }
        });
        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setPreferredSize(new Dimension(100, 100));
        add(detailsPanel);
        detailsPanel.setLayout(null);
        
        JButton btnLogOut = new JButton("Log Out");
        btnLogOut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		mainFame.setVisible(false);
        		Login loginScreen = new Login();
        		loginScreen.setLocationRelativeTo(null);
        		loginScreen.setVisible(true);
        	}
        });
        btnLogOut.setBounds(6, 36, 88, 29);
        detailsPanel.add(btnLogOut);
        
        JLabel lblTrFirstName = new JLabel("User: "+student.userName);
        lblTrFirstName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        lblTrFirstName.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrFirstName.setPreferredSize(new Dimension(150, 20));
        add(lblTrFirstName);
        
        JSeparator separator = new JSeparator();
        separator.setBackground(new Color(51, 51, 51));
        separator.setForeground(Color.LIGHT_GRAY);
        separator.setPreferredSize(new Dimension(100, 7));
        add(separator);
        btnShowProfile.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        add(btnShowProfile);
        
        JButton btnMarks = new JButton("Marks");
        btnMarks.setPreferredSize(new Dimension(150, 20));
        btnMarks.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, MarksPage.name);
            }
        });
        btnMarks.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        add(btnMarks);
        
        JButton btnTimetable = new JButton("Timetable");
        btnTimetable.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, Timetable.name);
        	}
        });
        btnTimetable.setPreferredSize(new Dimension(150, 20));
        btnTimetable.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        add(btnTimetable);
	}
}
