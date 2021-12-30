package com.ooad.kmis.student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

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

public class EditProfile extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private static Student student;
	private static ProfilePage profilePage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditProfile frame = new EditProfile(student, profilePage);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection con;
	PreparedStatement pst;
//	ResultSet rs;
	private JTextField txtUsername;
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(EditProfile.this, "Failed to located Class");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(EditProfile.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public EditProfile(Student studentObj, ProfilePage profile) {
		Connect();
		student = studentObj;
		profilePage = profile;
		
		setBounds(100, 100, 411, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(204, 204, 204));
		panel_1.setBounds(6, 6, 397, 337);
		contentPane.add(panel_1);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblFirstName.setBounds(30, 122, 64, 16);
		panel_1.add(lblFirstName);
		
		JLabel lblNewLabel_1 = new JLabel("Edit Profile");
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(166, 31, 108, 24);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblLastName.setBounds(30, 150, 71, 16);
		panel_1.add(lblLastName);
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(121, 117, 247, 26);
		panel_1.add(txtFirstName);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtLastName.setColumns(10);
		txtLastName.setBounds(121, 145, 247, 26);
		panel_1.add(txtLastName);
		
		JLabel lbDob = new JLabel("Date of birth");
		lbDob.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lbDob.setBounds(30, 193, 64, 16);
		panel_1.add(lbDob);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(121, 183, 247, 26);
		panel_1.add(dateChooser);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = txtUsername.getText();
				String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                java.util.Date dob = dateChooser.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(dob);
                Date theDate = Date.valueOf(date);
                
                Student newStudent = new Student(studentObj.registrationNo, firstName, lastName, userName, theDate);
                
            	try {
            		int res = newStudent.editProfile();
            		
//                    pst = con.prepareStatement("UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, username = ? WHERE reg_no = ?");
//                    pst.setString(1, firstName);
//                    pst.setString(2, lastName);
//                    pst.setDate(3, theDate);
//                    pst.setString(4, userName);
//                    pst.setString(5, studentObj.registrationNo);
//                    pst.executeUpdate();
            		if(res > 0) {
            			JOptionPane.showMessageDialog(EditProfile.this, "Profile edited succesfully");
                        EditProfile.this.setVisible(false);
                        profilePage.reloadStudentProfile();
            		}
                } catch (SQLException sexp) {
                    JOptionPane.showMessageDialog(EditProfile.this, "Failed to edit profile");
                	Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, sexp.getLocalizedMessage(), sexp);
                    sexp.printStackTrace();
                } catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEdit.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnEdit.setBounds(30, 246, 117, 29);
		panel_1.add(btnEdit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditProfile.this.setVisible(false);
			}
		});
		btnCancel.setForeground(new Color(255, 0, 0));
		btnCancel.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnCancel.setBounds(251, 246, 117, 29);
		panel_1.add(btnCancel);
		
		txtFirstName.setText(student.firstName);
		txtLastName.setText(student.lastName);
		dateChooser.setDate(student.dateOfBirth);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblUsername.setBounds(30, 89, 64, 16);
		panel_1.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setText((String) null);
		txtUsername.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtUsername.setColumns(10);
		txtUsername.setBounds(121, 84, 247, 26);
		panel_1.add(txtUsername);
		

		txtUsername.setText(student.userName);
		txtFirstName.setText(student.firstName);
		txtLastName.setText(student.lastName);
		dateChooser.setDate(student.dateOfBirth);
	}

}
