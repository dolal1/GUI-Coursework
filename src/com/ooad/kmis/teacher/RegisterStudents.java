package com.ooad.kmis.teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import com.ooad.kmis.GUtilities;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class RegisterStudents extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private static StudentsPage students;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterStudents frame = new RegisterStudents(students);
//					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection con;
	PreparedStatement pst;
	public void Connect() {
		try {
			Class.forName(GUtilities.driver);
			con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(RegisterStudents.this, "Failed to located Class");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(RegisterStudents.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public RegisterStudents(StudentsPage studentsPage) {
		students = studentsPage;
		//initialise connection to database
		Connect();
		
		setBounds(100, 100, 474, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(204, 204, 204));
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblFirstName.setBounds(30, 70, 64, 16);
		panel_1.add(lblFirstName);
		
		JLabel lblNewLabel_1 = new JLabel("Student Registration");
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(150, 6, 155, 24);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblLastName.setBounds(30, 98, 71, 16);
		panel_1.add(lblLastName);
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(121, 65, 313, 26);
		panel_1.add(txtFirstName);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtLastName.setColumns(10);
		txtLastName.setBounds(121, 93, 313, 26);
		panel_1.add(txtLastName);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblGender.setBounds(30, 128, 64, 16);
		panel_1.add(lblGender);
		
		JComboBox<String> txtGender = new JComboBox<String>();
		txtGender.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtGender.setModel(new DefaultComboBoxModel<String>(new String[] {"Male", "Female"}));
		txtGender.setBounds(121, 124, 130, 27);
		panel_1.add(txtGender);
		
		JLabel lbDob = new JLabel("Date of birth");
		lbDob.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lbDob.setBounds(30, 163, 64, 16);
		panel_1.add(lbDob);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(121, 153, 319, 26);
		panel_1.add(dateChooser);
		
		JLabel lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblClass.setBounds(30, 196, 64, 16);
		panel_1.add(lblClass);
		
		JComboBox<String> txtClass = new JComboBox<String>();
		txtClass.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtClass.setModel(new DefaultComboBoxModel<String>(new String[] {"P1", "P2", "P3", "P4", "P5", "P6", "P7"}));
		txtClass.setBounds(121, 192, 130, 27);
		panel_1.add(txtClass);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String gender = txtGender.getSelectedItem().toString();
                String genderAbr;
                if(gender.intern() == "Male") genderAbr = "M";
                else genderAbr = "F";
                String pupilClass = txtClass.getSelectedItem().toString();
                java.util.Date dob = dateChooser.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(dob);
                Date theDate = Date.valueOf(date);
                
                
                String userName = String.valueOf(lastName.toLowerCase().charAt(0)).concat(firstName.toLowerCase());
                
            	try {
                    pst = con.prepareStatement("INSERT INTO students (first_name, last_name, gender, date_of_birth, class, user_name) VALUES (?,?,?,?,?,?)");
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, genderAbr);
                    pst.setDate(4, theDate);
                    pst.setString(5, pupilClass);
                    pst.setString(6, userName);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(RegisterStudents.this, "Student registered succesfully");
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtGender.setSelectedIndex(-1);
                    txtClass.setSelectedIndex(-1);
                    txtFirstName.requestFocus();
                    studentsPage.loadAllStudents();
                } catch (SQLException sexp) {
                	Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, null, sexp);
                }
			}
			
		});
		btnRegister.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnRegister.setBounds(96, 246, 117, 29);
		panel_1.add(btnRegister);
		
		JButton btnClose = new JButton("Close");
		btnClose.setForeground(new Color(255, 0, 0));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterStudents.this.setVisible(false);
			}
		});
		btnClose.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnClose.setBounds(313, 246, 117, 29);
		panel_1.add(btnClose);
	}

}
