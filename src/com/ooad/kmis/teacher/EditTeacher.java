package com.ooad.kmis.teacher;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class EditTeacher extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private static Teacher teacher;
	private static TeachersPage teachersPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditTeacher frame = new EditTeacher(teacher, teachersPage);
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
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(EditTeacher.this, "Failed to located Class");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(EditTeacher.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public EditTeacher(Teacher teacherObj, TeachersPage teachers) {
		Connect();
		teacher = teacherObj;
		teachersPage = teachers;
		
		setBounds(100, 100, 471, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(204, 204, 204));
		panel_1.setBounds(6, 6, 459, 337);
		contentPane.add(panel_1);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblFirstName.setBounds(30, 70, 64, 16);
		panel_1.add(lblFirstName);
		
		JLabel lblNewLabel_1 = new JLabel("Edit Teacher records");
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
		
		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblSubject.setBounds(30, 145, 64, 16);
		panel_1.add(lblSubject);
		
		JComboBox<String> txtSubject = new JComboBox<String>();
		txtSubject.setModel(new DefaultComboBoxModel<String>(new String[] {"English", "Mathematics", "Social Studies", "Science"}));
		txtSubject.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtSubject.setBounds(121, 141, 130, 27);
		panel_1.add(txtSubject);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String subject = txtSubject.getSelectedItem().toString();
                
            	try {
                    pst = con.prepareStatement("UPDATE teachers SET first_name = ?, last_name = ? subject = ? WHERE id = ?");
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, subject);
                    pst.setString(4, teacherObj.userId);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(EditTeacher.this, "Teacher records edited sucefully");
                    EditTeacher.this.setVisible(false);
                    teachersPage.loadAllTeachers();
                } catch (SQLException sexp) {
                	Logger.getLogger(EditTeacher.class.getName()).log(Level.SEVERE, null, sexp);
                }
			}
		});
		btnEdit.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnEdit.setBounds(30, 246, 117, 29);
		panel_1.add(btnEdit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditTeacher.this.setVisible(false);
			}
		});
		btnCancel.setForeground(new Color(255, 0, 0));
		btnCancel.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnCancel.setBounds(313, 246, 117, 29);
		panel_1.add(btnCancel);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	try {
                    pst = con.prepareStatement("DELETE FROM teachers WHERE id = ?");
                    
                    pst.setString(1, teacherObj.userId);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(EditTeacher.this, "Teacher deleted sucefully");
                    EditTeacher.this.setVisible(false);
                    teachersPage.loadAllTeachers();
                } catch (SQLException sexp) {
                	Logger.getLogger(EditTeacher.class.getName()).log(Level.SEVERE, null, sexp);
                }
			}
		});
		btnDelete.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnDelete.setBounds(174, 246, 117, 29);
		panel_1.add(btnDelete);
		
		txtFirstName.setText(teacher.firstName);
		txtLastName.setText(teacher.lastName);
		txtSubject.setSelectedItem(teacher.subject);
	}

}
