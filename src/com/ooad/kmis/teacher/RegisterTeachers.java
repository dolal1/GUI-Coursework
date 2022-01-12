package com.ooad.kmis.teacher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ooad.kmis.GUtilities;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

public class RegisterTeachers extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static TeachersPage teachersPage;
	

    private JPanel contentPane;
    private JTextField firstname;
    private JTextField lastname;
    private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterTeachers frame = new RegisterTeachers(teachersPage);
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
            JOptionPane.showMessageDialog(RegisterTeachers.this, "Failed to located Class");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(RegisterTeachers.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public RegisterTeachers(TeachersPage parent) {
		teachersPage = parent;
		//initialise connection to database
		Connect();
        
        setBounds(450, 190, 471, 392);
        setResizable(true);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewUserRegister = new JLabel("Teacher Registration");
        lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        lblNewUserRegister.setBounds(114, 6, 259, 50);
        contentPane.add(lblNewUserRegister);

        JLabel lblName = new JLabel("First name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblName.setBounds(58, 75, 87, 29);
        contentPane.add(lblName);

        JLabel lblNewLabel = new JLabel("Last name");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel.setBounds(58, 116, 87, 29);
        contentPane.add(lblNewLabel);

        firstname = new JTextField();
        firstname.setFont(new Font("Tahoma", Font.PLAIN, 12));
        firstname.setBounds(157, 75, 228, 30);
        contentPane.add(firstname);
        firstname.setColumns(10);

        lastname = new JTextField();
        lastname.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lastname.setBounds(157, 116, 228, 30);
        contentPane.add(lastname);
        lastname.setColumns(10);
        
        JLabel lblSubject = new JLabel("Subject");
        lblSubject.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        lblSubject.setBounds(58, 161, 71, 20);
        contentPane.add(lblSubject);
        
        JComboBox<String> chooserSubject = new JComboBox<String>();
        chooserSubject.setModel(new DefaultComboBoxModel<String>(new String[] {"English", "Mathematics", "Social Studies", "Science"}));
        chooserSubject.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        chooserSubject.setBounds(157, 158, 130, 27);
        contentPane.add(chooserSubject);

        btnNewButton = new JButton("Register");
        btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                String firstName = firstname.getText();
                String lastName = lastname.getText();
                String subjectTaught = chooserSubject.getSelectedItem().toString();

                String userName = String.valueOf(lastName.toLowerCase().charAt(0)).concat(firstName.toLowerCase());


//                try {
//                    Connection connection = DriverManager.getConnection(GUtilities.connectionUrl, GUtilities.dbUsername, GUtilities.dbPassword);
//
//                    String query = "INSERT INTO teachers (first_name, last_name, subject, user_name, password) VALUES ('" + firstName + "','" + lastName + "', '" + subjectsTaught + "', '" + userName + "', '" + password + "')";
////                    String query1 = "INSERT INTO teachers values('" + firstName + "','" + lastName + "','" + userName + "','" +
////                        password + "','" + emailId + "','" + mobileNumber + "')";
//
//                    Statement sta = connection.createStatement();
//                    int x = sta.executeUpdate(query);
//                    if (x == 0) {
//                        JOptionPane.showMessageDialog(btnNewButton, "User already exists!");
//                    } else {
//                        JOptionPane.showMessageDialog(btnNewButton,
//                            "Teacher account has been created sucessfully");
//                    }
//                    connection.close();
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                    JOptionPane.showMessageDialog(btnNewButton,
//                            "Error:"+ exception.getMessage()+"");
//                }
                
                try {
                    pst = con.prepareStatement("INSERT INTO teachers (first_name, last_name, subject, user_name) VALUES (?,?,?,?)");
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, subjectTaught);
                    pst.setString(4, userName);
                    int x = pst.executeUpdate();
                    
                    if (x == 0) {
                        JOptionPane.showMessageDialog(btnNewButton, "User already exists!");
                    } else {
                        JOptionPane.showMessageDialog(btnNewButton,
                            "Teacher account has been created sucessfully");
                    }
                    pst.close();
                    
                    firstname.setText("");
                    lastname.setText("");
                    chooserSubject.setSelectedIndex(-1);
                    teachersPage.loadAllTeachers();
                } catch (SQLException sexp) {
                	Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, sexp.getMessage(), sexp);
                }
				
			}
        });
        btnNewButton.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
        btnNewButton.setBounds(58, 262, 124, 29);
        contentPane.add(btnNewButton);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RegisterTeachers.this.setVisible(false);
        	}
        });
        btnCancel.setForeground(Color.RED);
        btnCancel.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
        btnCancel.setBounds(268, 262, 117, 29);
        contentPane.add(btnCancel);
	}
}
