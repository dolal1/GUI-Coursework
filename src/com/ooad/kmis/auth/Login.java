package com.ooad.kmis.auth;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ooad.kmis.GUtilities;
import com.ooad.kmis.student.Student;
import com.ooad.kmis.student.StudentDashboard;
import com.ooad.kmis.teacher.Dashboard;
import com.ooad.kmis.teacher.Teacher;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	ResultSet rs;

	public void Connect() {
		try {
			Class.forName(GUtilities.driver);
			con = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(Login.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Login.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		Connect();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 434);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblKatikamuPrimarySchool = new JLabel("Katikamu Primary School");
		lblKatikamuPrimarySchool.setHorizontalAlignment(SwingConstants.CENTER);
		lblKatikamuPrimarySchool.setBounds(6, 6, 400, 24);
		lblKatikamuPrimarySchool.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		contentPane.add(lblKatikamuPrimarySchool);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(null);
		panel.setBounds(72, 64, 265, 176);
		contentPane.add(panel);

		JLabel lblNewLabel_1_2 = new JLabel("User Type");
		lblNewLabel_1_2.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblNewLabel_1_2.setBounds(21, 25, 61, 16);
		panel.add(lblNewLabel_1_2);

		JComboBox<String> cbUserType = new JComboBox<String>();
		cbUserType.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		cbUserType.setModel(new DefaultComboBoxModel<String>(new String[] { "Teacher", "Student" }));
		cbUserType.setBounds(94, 21, 130, 20);
		panel.add(cbUserType);

		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(21, 58, 61, 16);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(21, 87, 61, 16);
		panel.add(lblNewLabel_1_1);

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtUsername.setColumns(10);
		txtUsername.setBounds(94, 53, 130, 26);
		panel.add(txtUsername);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userType = cbUserType.getSelectedItem().toString();
				String userName = txtUsername.getText();
				String password = String.valueOf(txtPassword.getPassword());

				try {
					// creating prepared statement
					if (userType.intern() == "Teacher") {
						pst = con.prepareStatement("SELECT * FROM teachers WHERE user_name = BINARY ? and password = BINARY ?");
					} else {
						pst = con.prepareStatement("SELECT * FROM students WHERE user_name = BINARY ? and password = BINARY ?");
					}
					pst.setString(1, userName);
					pst.setString(2, password);

					rs = pst.executeQuery();

					if (rs.next()) {
						String dbUserName = rs.getString("user_name");
						String dbPassword = rs.getString("password");

						// if its a teacher, launch teacher dashboard.
						if (userType.intern() == "Teacher") {
							if ((dbUserName.intern() == userName.intern())
									&& (dbPassword.intern() == password.intern())) {

								// close the login form
								Login.this.setVisible(false);
								Teacher teacher = new Teacher(rs);
								Dashboard.launch(teacher);
							} else {
								JOptionPane.showMessageDialog(Login.this, "Invalid username or password");
							}
						} else {
							if ((dbUserName.intern() == userName.intern())
									&& (dbPassword.intern() == password.intern())) {
								// close the login form
								Login.this.setVisible(false);

								Student student = new Student().fromResultSet(rs);
								StudentDashboard.launch(student);
							} else {
								java.util.logging.Logger.getLogger(Dashboard.class.getName())
								.log(java.util.logging.Level.SEVERE, "Username"+dbUserName+"_"+userName+"_", "");

								java.util.logging.Logger.getLogger(Dashboard.class.getName())
								.log(java.util.logging.Level.SEVERE, "password"+dbPassword+"_"+password+"_", "");
								JOptionPane.showMessageDialog(Login.this, "Invalid username or password");
							}

						}

					} else {
						java.util.logging.Logger.getLogger(Dashboard.class.getName())
								.log(java.util.logging.Level.SEVERE, "Login failed", "");
						JOptionPane.showMessageDialog(Login.this, "Login failed");
						txtUsername.setText("");
						txtPassword.setText("");
						cbUserType.setSelectedIndex(-1);
					}

				} catch (SQLException sqle) {
					JOptionPane.showMessageDialog(Login.this, "Failed to connect to database");
				} catch (Exception exe) {

					JOptionPane.showMessageDialog(Login.this, "Failed to connect to database");
				}
			}
		});
		btnLogin.setBounds(71, 124, 117, 29);
		panel.add(btnLogin);

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtPassword.setBounds(94, 82, 130, 26);
		panel.add(txtPassword);

	}
}
