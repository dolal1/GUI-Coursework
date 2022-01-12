package com.ooad.kmis.student;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.ooad.kmis.GUtilities;

import java.awt.Font;

public class MarksPage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String name = "marks";
	private static Student student;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	public void Connect() {
		try {
			Class.forName(GUtilities.driver);
			con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(MarksPage.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(MarksPage.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the panel.
	 */
	public MarksPage(Student st) {
		student = st;
		Connect();
        this.setPreferredSize(new Dimension(800, 450));
        setLayout(null);

		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		String currentTerm = "1";
		
		Marks englishMarks = new Marks("English", String.valueOf(currentYear), currentTerm);;
		Marks mathMarks = new Marks("Mathematics", String.valueOf(currentYear), currentTerm);;
		Marks sstMarks = new Marks("SocialStudies", String.valueOf(currentYear), currentTerm);;
		Marks scienceMarks = new Marks("Science", String.valueOf(currentYear), currentTerm);;
        
        try {
			pst = con.prepareStatement("SELECT * FROM english WHERE reg_no = ? and year = ? and term = ?");
	        pst.setString(1, student.registrationNo);
	        pst.setString(2, String.valueOf(currentYear));
	        pst.setString(3, currentTerm);
			rs = pst.executeQuery();
			if(rs.next()) {
				englishMarks = englishMarks.fromResultSet(rs);
			}
			
//			englishMarks.subject = "English";
//			englishMarks.year = String.format("%s", currentYear);
//			englishMarks.term = currentTerm;
			
			pst = con.prepareStatement("SELECT * FROM mathematics WHERE reg_no = ? and year = ? and term = ?");
	        pst.setString(1, student.registrationNo);
	        pst.setString(2, String.valueOf(currentYear));
	        pst.setString(3, currentTerm);
			rs = pst.executeQuery();
			if(rs.next()) {
				mathMarks = mathMarks.fromResultSet(rs);
			}
			
			pst = con.prepareStatement("SELECT * FROM social_studies WHERE reg_no = ? and year = ? and term = ?");
	        pst.setString(1, student.registrationNo);
	        pst.setString(2, String.valueOf(currentYear));
	        pst.setString(3, currentTerm);
			rs = pst.executeQuery();
			if(rs.next()) {
				sstMarks = sstMarks.fromResultSet(rs);
			}
			
			pst = con.prepareStatement("SELECT * FROM science WHERE reg_no = ? and year = ? and term = ?");
	        pst.setString(1, student.registrationNo);
	        pst.setString(2, String.valueOf(currentYear));
	        pst.setString(3, currentTerm);
			rs = pst.executeQuery();
			if(rs.next()) {
				scienceMarks = scienceMarks.fromResultSet(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBounds(296, 78, 481, 294);
        add(panel_1);
        
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(113, 20, 19, 144);
        panel_1.add(separator);
        
        JLabel lblEnglish = new JLabel("English");
        lblEnglish.setBounds(21, 55, 111, 16);
        panel_1.add(lblEnglish);
        
        JLabel lblLastName_1 = new JLabel("Social Studies");
        lblLastName_1.setBounds(21, 86, 111, 16);
        panel_1.add(lblLastName_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("Mathematics");
        lblNewLabel_1_1.setBounds(21, 114, 111, 16);
        panel_1.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("Science");
        lblNewLabel_1_1_1.setBounds(21, 142, 111, 16);
        panel_1.add(lblNewLabel_1_1_1);
        
        JLabel txtEnglishBOT = new JLabel(""+englishMarks.bot);
        txtEnglishBOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtEnglishBOT.setBounds(144, 55, 67, 16);
        panel_1.add(txtEnglishBOT);
        
        JLabel txtSstBOT = new JLabel(""+sstMarks.bot);
        txtSstBOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtSstBOT.setBounds(144, 86, 67, 16);
        panel_1.add(txtSstBOT);
        
        JLabel txtMathBOT = new JLabel(""+mathMarks.bot);
        txtMathBOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtMathBOT.setBounds(144, 114, 67, 16);
        panel_1.add(txtMathBOT);
        
        JLabel txtScienceBOT = new JLabel(""+scienceMarks.bot);
        txtScienceBOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtScienceBOT.setBounds(144, 142, 67, 16);
        panel_1.add(txtScienceBOT);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        separator_1.setBounds(223, 20, 19, 144);
        panel_1.add(separator_1);
        
        JLabel txtEnglishMOT = new JLabel(""+englishMarks.mot);
        txtEnglishMOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtEnglishMOT.setBounds(254, 55, 67, 16);
        panel_1.add(txtEnglishMOT);
        
        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(333, 20, 19, 144);
        panel_1.add(separator_1_1);
        
        JLabel txtEnglishEOT = new JLabel(""+englishMarks.eot);
        txtEnglishEOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtEnglishEOT.setBounds(364, 55, 67, 16);
        panel_1.add(txtEnglishEOT);
        
        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(21, 37, 433, 16);
        panel_1.add(separator_2);
        
        JLabel lblBot = new JLabel("BOT");
        lblBot.setBounds(131, 20, 86, 16);
        panel_1.add(lblBot);
        
        JLabel lblMidTerm = new JLabel("Mid Term");
        lblMidTerm.setBounds(235, 20, 86, 16);
        panel_1.add(lblMidTerm);
        
        JLabel lblEndOfTerm = new JLabel("End of Term");
        lblEndOfTerm.setBounds(345, 20, 86, 16);
        panel_1.add(lblEndOfTerm);
        
        JLabel txtSstMOT = new JLabel(""+sstMarks.mot);
        txtSstMOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtSstMOT.setBounds(254, 86, 67, 16);
        panel_1.add(txtSstMOT);
        
        JLabel txtSstEOT = new JLabel(""+sstMarks.eot);
        txtSstEOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtSstEOT.setBounds(364, 86, 67, 16);
        panel_1.add(txtSstEOT);
        
        JLabel txtMathMOT = new JLabel(""+mathMarks.mot);
        txtMathMOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtMathMOT.setBounds(254, 114, 67, 16);
        panel_1.add(txtMathMOT);
        
        JLabel txtMathEOT = new JLabel(""+mathMarks.eot);
        txtMathEOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtMathEOT.setBounds(364, 114, 67, 16);
        panel_1.add(txtMathEOT);
        
        JLabel txtScienceMOT = new JLabel(""+scienceMarks.mot);
        txtScienceMOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtScienceMOT.setBounds(254, 142, 67, 16);
        panel_1.add(txtScienceMOT);
        
        JLabel txtScienceEOT = new JLabel(""+scienceMarks.eot);
        txtScienceEOT.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        txtScienceEOT.setBounds(364, 142, 67, 16);
        panel_1.add(txtScienceEOT);
        
        JSeparator separator_1_2 = new JSeparator();
        separator_1_2.setBounds(21, 72, 433, 16);
        panel_1.add(separator_1_2);
        
        JSeparator separator_1_2_1 = new JSeparator();
        separator_1_2_1.setBounds(21, 101, 433, 16);
        panel_1.add(separator_1_2_1);
        
        JSeparator separator_1_2_2 = new JSeparator();
        separator_1_2_2.setBounds(21, 129, 433, 16);
        panel_1.add(separator_1_2_2);
        
        JSeparator separator_1_2_3 = new JSeparator();
        separator_1_2_3.setBounds(21, 159, 433, 16);
        panel_1.add(separator_1_2_3);
        
        JLabel lblMarks = new JLabel("Marks");
        lblMarks.setFont(new Font("Arial Narrow", Font.PLAIN, 20));
        lblMarks.setBounds(296, 28, 97, 38);
        add(lblMarks);
	}

}
