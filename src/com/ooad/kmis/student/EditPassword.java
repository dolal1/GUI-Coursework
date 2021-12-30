package com.ooad.kmis.student;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditPassword extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditPassword frame = new EditPassword(student);
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
	private JPasswordField passCurrent;
	private JPasswordField passNew;
	private JPasswordField passConfirm;
	private static Student student;
	String password = "pwd";

	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(EditPassword.this, "Failed to located Class");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(EditPassword.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public EditPassword(Student stud) {

		Connect();
		student = stud;

		try {
			pst = con.prepareStatement("SELECT password FROM students WHERE reg_no = ?");
			pst.setString(1, student.registrationNo);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				password = rs.getString("password");
			} else {
				JOptionPane.showMessageDialog(EditPassword.this, "Unable to load student details!");
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(EditPassword.this, "Unable to load student details!");
			e1.printStackTrace();
		}

		setBounds(100, 100, 416, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Change Password");
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(141, 16, 141, 24);
		contentPane.add(lblNewLabel_1);

		JLabel lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblCurrentPassword.setBounds(44, 73, 94, 16);
		contentPane.add(lblCurrentPassword);

		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblNewPassword.setBounds(44, 114, 94, 16);
		contentPane.add(lblNewPassword);

		JLabel lblCurrentPassword_1_1 = new JLabel("Confirm Password");
		lblCurrentPassword_1_1.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblCurrentPassword_1_1.setBounds(44, 146, 94, 16);
		contentPane.add(lblCurrentPassword_1_1);

		passCurrent = new JPasswordField();
		passCurrent.setBounds(152, 68, 174, 21);
		contentPane.add(passCurrent);

		passNew = new JPasswordField();
		passNew.setBounds(152, 109, 174, 21);
		contentPane.add(passNew);

		passConfirm = new JPasswordField();
		passConfirm.setBounds(152, 141, 174, 21);
		contentPane.add(passConfirm);

		JButton btnEdit = new JButton("Confirm");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentPassword = String.valueOf(passCurrent.getPassword());
				String newPassword = String.valueOf(passNew.getPassword());
				String confirmPassword = String.valueOf(passConfirm.getPassword());

				if (newPassword.intern() != confirmPassword.intern()) {
					JOptionPane.showMessageDialog(EditPassword.this, "Passwords do not match!");
				} else if (currentPassword.intern() != password.intern()) {
					JOptionPane.showMessageDialog(EditPassword.this, "Incorrect password!");
				} else if (currentPassword.intern() == password.intern()) {
					try {
						boolean result = student.changePassword(newPassword);
						if (result) {
							JOptionPane.showMessageDialog(EditPassword.this, "Succesfully changed password");
							EditPassword.this.setVisible(false);
						}
					} catch (ClassNotFoundException | SQLException e1) {
						JOptionPane.showMessageDialog(EditPassword.this, "Failed to change password!");
						e1.printStackTrace();
					}
				}

			}
		});
		btnEdit.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnEdit.setBounds(56, 199, 117, 29);
		contentPane.add(btnEdit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditPassword.this.setVisible(false);
			}
		});
		btnCancel.setForeground(Color.RED);
		btnCancel.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnCancel.setBounds(258, 199, 117, 29);
		contentPane.add(btnCancel);
	}
}
