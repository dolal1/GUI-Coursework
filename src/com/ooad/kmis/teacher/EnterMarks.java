package com.ooad.kmis.teacher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ooad.kmis.GUtilities;
import com.ooad.kmis.Subjects;
import com.ooad.kmis.student.Student;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class EnterMarks extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Student student;
	private static String subject;
	private static String year;
	private static String term;
	private static ClassMarkPanel marksPage;
	
	private JPanel contentPane;
	private JTextField txtBOT;
	private JLabel lblEnterStudentsMarks;
	private JTextField txtMOT;
	private JLabel lblMot;
	private JTextField txtEOT;
	private JLabel lblEot;
	private JButton btnCancel;
	private JButton btnSave;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblRegNo;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JSeparator separator;
	private JSeparator separator_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterMarks frame = new EnterMarks(student, subject, year, term, marksPage);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection con;
	PreparedStatement pst;
	private JLabel lblNewLabel_4;
	private JTextField txtYear;
	private JLabel lblNewLabel_5;
	private JTextField txtClass;
	public void Connect() {
		try {
			Class.forName(GUtilities.driver);
			con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(EnterMarks.this, "Failed to located Class");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(EnterMarks.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public EnterMarks(Student stu, String subj, String yr, String thisTerm, ClassMarkPanel parent) {
		student = stu;
		subject = subj;
		year = yr;
		term = thisTerm;
		marksPage = parent;
		
		Connect();
		setBounds(100, 100, 408, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOT:");
		lblNewLabel.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblNewLabel.setBounds(96, 166, 37, 16);
		contentPane.add(lblNewLabel);
		
		txtBOT = new JTextField();
		txtBOT.setBounds(145, 161, 130, 26);
		contentPane.add(txtBOT);
		txtBOT.setColumns(10);
		
		lblEnterStudentsMarks = new JLabel("Enter Student's marks");
		lblEnterStudentsMarks.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblEnterStudentsMarks.setBounds(133, 17, 142, 27);
		contentPane.add(lblEnterStudentsMarks);
		
		txtMOT = new JTextField();
		txtMOT.setColumns(10);
		txtMOT.setBounds(145, 194, 130, 26);
		contentPane.add(txtMOT);
		
		lblMot = new JLabel("MOT:");
		lblMot.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblMot.setBounds(96, 199, 37, 16);
		contentPane.add(lblMot);
		
		txtEOT = new JTextField();
		txtEOT.setColumns(10);
		txtEOT.setBounds(145, 230, 130, 26);
		contentPane.add(txtEOT);
		
		lblEot = new JLabel("EOT:");
		lblEot.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblEot.setBounds(96, 235, 37, 16);
		contentPane.add(lblEot);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnterMarks.this.setVisible(false);
			}
		});
		btnCancel.setForeground(Color.RED);
		btnCancel.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnCancel.setBounds(239, 268, 117, 29);
		contentPane.add(btnCancel);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String BOT = txtBOT.getText();
				String MOT = txtMOT.getText();
				String EOT = txtEOT.getText();
				
				try {
					if(subject.intern() == Subjects.english.intern()) {
	                    pst = con.prepareStatement("INSERT INTO english (reg_no, year, term, class, BOT, MOT, EOT) VALUES (?,?,?,?,?,?,?)");
					} else if(subject.intern() == Subjects.math.intern()) {
	                    pst = con.prepareStatement("INSERT INTO mathematics (reg_no, year, term, class, BOT, MOT, EOT) VALUES (?,?,?,?,?,?,?)");
					} else if(subject.intern() == Subjects.sst.intern()) {
	                    pst = con.prepareStatement("INSERT INTO social_studies (reg_no, year, term, class, BOT, MOT, EOT) VALUES (?,?,?,?,?,?,?)");
					} else if(subject.intern() == Subjects.science.intern()) {
	                    pst = con.prepareStatement("INSERT INTO science (reg_no, year, term, class, BOT, MOT, EOT) VALUES (?,?,?,?,?,?,?)");
					} else {
			            JOptionPane.showMessageDialog(EnterMarks.this, "Invalid subject name");
					}
					
                    pst.setString(1, student.registrationNo);
                    pst.setString(2, year);
                    pst.setString(3, term);
                    pst.setString(4, student.studentClass);
                    pst.setString(5, BOT);
                    pst.setString(6, MOT);
                    pst.setString(7, EOT);
                    int result = pst.executeUpdate();
                    
                    if(result > 0) {
                        JOptionPane.showMessageDialog(EnterMarks.this, "Mark added succesfully");
                        marksPage.loadMarks();
                        EnterMarks.this.setVisible(false);
                    }
                } catch (SQLException sexp) {
                	Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, null, sexp);
                    JOptionPane.showMessageDialog(EnterMarks.this, "Failed to add mark");
                }
			}
		});
		btnSave.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnSave.setBounds(71, 268, 117, 29);
		contentPane.add(btnSave);
		
		lblNewLabel_1 = new JLabel("Reg No:");
		lblNewLabel_1.setBounds(77, 55, 55, 16);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("First Name:");
		lblNewLabel_2.setBounds(53, 76, 79, 16);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Last Name:");
		lblNewLabel_3.setBounds(53, 96, 79, 16);
		contentPane.add(lblNewLabel_3);
		
		lblRegNo = new JLabel(""+student.registrationNo);
		lblRegNo.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblRegNo.setBounds(145, 55, 130, 16);
		contentPane.add(lblRegNo);
		
		lblFirstName = new JLabel(""+student.firstName);
		lblFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblFirstName.setBounds(145, 76, 130, 16);
		contentPane.add(lblFirstName);
		
		lblLastName = new JLabel(""+student.lastName);
		lblLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		lblLastName.setBounds(145, 96, 130, 16);
		contentPane.add(lblLastName);
		
		separator = new JSeparator();
		separator.setBounds(77, 111, 192, 12);
		contentPane.add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(106, 35, 192, 12);
		contentPane.add(separator_1);
		
		lblNewLabel_4 = new JLabel("Year:");
		lblNewLabel_4.setBounds(20, 138, 37, 16);
		contentPane.add(lblNewLabel_4);
		
		txtYear = new JTextField(""+year);
		txtYear.setColumns(10);
		txtYear.setBounds(53, 133, 63, 26);
		contentPane.add(txtYear);
		
		lblNewLabel_5 = new JLabel("Term:");
		lblNewLabel_5.setBounds(137, 138, 37, 16);
		contentPane.add(lblNewLabel_5);
		
		JComboBox<String> comboBoxTerm = new JComboBox<String>();
		comboBoxTerm.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3"}));
		comboBoxTerm.setSelectedItem(""+term);
		comboBoxTerm.setBounds(176, 134, 63, 27);
		contentPane.add(comboBoxTerm);
		
		JLabel lblNewLabel_5_1 = new JLabel("Class:");
		lblNewLabel_5_1.setBounds(251, 138, 47, 16);
		contentPane.add(lblNewLabel_5_1);
		
		txtClass = new JTextField(""+student.studentClass);
		txtClass.setColumns(10);
		txtClass.setBounds(293, 133, 63, 26);
		contentPane.add(txtClass);
	}
}
