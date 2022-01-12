package com.ooad.kmis.teacher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ooad.kmis.GUtilities;
import com.ooad.kmis.Subjects;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditMarks extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Marks marks;
	private static ClassMarkPanel marksPage;
	
	
	private JPanel contentPane;
	private JTextField txtBOT;
	private JTextField txtMOT;
	private JTextField txtEOT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditMarks frame = new EditMarks(marks, marksPage);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	String firstName = "";
	String lastName = "";
	public void Connect() {
		try {
			Class.forName(GUtilities.driver);
			con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(EditMarks.this, "Failed to located Class");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(EditMarks.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public EditMarks(Marks studentMarks, ClassMarkPanel parent) {
		marksPage = parent;
		Connect();
		marks = studentMarks;
		try {
			pst = con.prepareStatement(
					"SELECT first_name, last_name FROM students WHERE reg_no = ?");
			pst.setString(1, marks.registrationNo);
			rs = pst.executeQuery();
			rs.next();
			
			firstName = rs.getString("first_name");
			lastName = rs.getString("last_name");
		} catch (SQLException e1) {
            JOptionPane.showMessageDialog(EditMarks.this, "Failed to get student details");
			e1.printStackTrace();
		}
		
		setBounds(100, 100, 417, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel contentPane_1 = new JPanel();
		contentPane_1.setBounds(0, 0, 408, 336);
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(contentPane_1);
		
		JLabel lblNewLabel = new JLabel("BOT:");
		lblNewLabel.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblNewLabel.setBounds(96, 166, 37, 16);
		contentPane_1.add(lblNewLabel);
		
		txtBOT = new JTextField();
		txtBOT.setColumns(10);
		txtBOT.setBounds(145, 161, 130, 26);
		contentPane_1.add(txtBOT);
		
		JLabel lblEditstudentsMarks = new JLabel("Edit Student's marks");
		lblEditstudentsMarks.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblEditstudentsMarks.setBounds(133, 17, 142, 27);
		contentPane_1.add(lblEditstudentsMarks);
		
		txtMOT = new JTextField();
		txtMOT.setColumns(10);
		txtMOT.setBounds(145, 194, 130, 26);
		contentPane_1.add(txtMOT);
		
		JLabel lblMot = new JLabel("MOT:");
		lblMot.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblMot.setBounds(96, 199, 37, 16);
		contentPane_1.add(lblMot);
		
		txtEOT = new JTextField();
		txtEOT.setColumns(10);
		txtEOT.setBounds(145, 230, 130, 26);
		contentPane_1.add(txtEOT);
		
		JLabel lblEot = new JLabel("EOT:");
		lblEot.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblEot.setBounds(96, 235, 37, 16);
		contentPane_1.add(lblEot);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditMarks.this.setVisible(false);
			}
		});
		btnCancel.setForeground(Color.RED);
		btnCancel.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnCancel.setBounds(239, 268, 117, 29);
		contentPane_1.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String BOT = txtBOT.getText();
				String MOT = txtMOT.getText();
				String EOT = txtEOT.getText();
				
				try {
					if(marks.subject.intern() == Subjects.english.intern()) {
	                    pst = con.prepareStatement("UPDATE english SET BOT = ?, MOT = ?, EOT = ? WHERE reg_no = ? and year = ? and term = ? and class = ?");
					} else if(marks.subject.intern() == Subjects.math.intern()) {
	                    pst = con.prepareStatement("UPDATE mathematics SET BOT = ?, MOT = ?, EOT = ? WHERE reg_no = ? and year = ? and term = ? and class = ?");
					} else if(marks.subject.intern() == Subjects.sst.intern()) {
	                    pst = con.prepareStatement("UPDATE social_studies SET BOT = ?, MOT = ?, EOT = ? WHERE reg_no = ? and year = ? and term = ? and class = ?");
					} else if(marks.subject.intern() == Subjects.science.intern()) {
	                    pst = con.prepareStatement("UPDATE science SET BOT = ?, MOT = ?, EOT = ? WHERE reg_no = ? and year = ? and term = ? and class = ?");
					} else {
			            JOptionPane.showMessageDialog(EditMarks.this, "Invalid subject name");
					}
					
                    pst.setString(1, BOT);
                    pst.setString(2, MOT);
                    pst.setString(3, EOT);
                    pst.setString(4, marks.registrationNo);
                    pst.setString(5, marks.year);
                    pst.setString(6, marks.term);
                    pst.setString(7, marks.theClass);
                    int result = pst.executeUpdate();
                    
                    if(result > 0) {
                        JOptionPane.showMessageDialog(EditMarks.this, "Marks edited succesfully");
                        parent.loadMarks();
                        EditMarks.this.setVisible(false);
                    } else {
                    	JOptionPane.showMessageDialog(EditMarks.this, "Unknown error");
                        
                    }
                } catch (SQLException sexp) {
                	Logger.getLogger(EditMarks.class.getName()).log(Level.SEVERE, null, sexp);
                    JOptionPane.showMessageDialog(EditMarks.this, "Failed to edit marks");
                }
			}
		});
		btnSave.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnSave.setBounds(71, 268, 117, 29);
		contentPane_1.add(btnSave);
		
		JLabel lblNewLabel_1 = new JLabel("Reg No:");
		lblNewLabel_1.setBounds(77, 55, 55, 16);
		contentPane_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("First Name:");
		lblNewLabel_2.setBounds(53, 76, 79, 16);
		contentPane_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Last Name:");
		lblNewLabel_3.setBounds(53, 96, 79, 16);
		contentPane_1.add(lblNewLabel_3);
		
		JLabel lblRegNo = new JLabel(""+marks.registrationNo);
		lblRegNo.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblRegNo.setBounds(145, 55, 130, 16);
		contentPane_1.add(lblRegNo);
		
		JLabel lblFirstName = new JLabel(""+firstName);
		lblFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblFirstName.setBounds(145, 76, 130, 16);
		contentPane_1.add(lblFirstName);
		
		JLabel lblLastName = new JLabel(""+lastName);
		lblLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblLastName.setBounds(145, 96, 130, 16);
		contentPane_1.add(lblLastName);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 142, 336, 12);
		contentPane_1.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(106, 35, 192, 12);
		contentPane_1.add(separator_1);
		
		JLabel lblNewLabel_4 = new JLabel("Year:");
		lblNewLabel_4.setBounds(20, 124, 37, 16);
		contentPane_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Term:");
		lblNewLabel_5.setBounds(133, 124, 37, 16);
		contentPane_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Class:");
		lblNewLabel_5_1.setBounds(239, 124, 47, 16);
		contentPane_1.add(lblNewLabel_5_1);
		
		JLabel lblYear = new JLabel(""+marks.year);
		lblYear.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblYear.setBounds(58, 124, 55, 16);
		contentPane_1.add(lblYear);
		
		JLabel lblTerm = new JLabel(""+marks.term);
		lblTerm.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblTerm.setBounds(171, 124, 55, 16);
		contentPane_1.add(lblTerm);
		
		JLabel lblClass = new JLabel(""+marks.theClass);
		lblClass.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblClass.setBounds(283, 124, 55, 16);
		contentPane_1.add(lblClass);
		
		txtBOT.setText(marks.bot);
		txtMOT.setText(marks.mot);
		txtEOT.setText(marks.eot);
	}

}
