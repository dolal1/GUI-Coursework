package com.ooad.kmis.teacher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ooad.kmis.student.Student;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Search extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSearch;
	private JTable table;
	private static StudentsPage studentsPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search frame = new Search(studentsPage);
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
	String searchTerm;
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(Search.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(Search.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public Search(StudentsPage students) {
		studentsPage = students;
		Connect();
		
		setBounds(100, 100, 627, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(123, 0, 348, 58);
		contentPane.add(panel_1);
		
		JLabel lblSearchForStudent = new JLabel("Search for students");
		lblSearchForStudent.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		panel_1.add(lblSearchForStudent);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				searchFunc();
			}
		});
		txtSearch.setToolTipText("search");
		txtSearch.setColumns(10);
		panel_1.add(txtSearch);
		
		JButton btnSearchStudent = new JButton("Search");
		btnSearchStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFunc();
			}
		});
		btnSearchStudent.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		panel_1.add(btnSearchStudent);
		
		JLabel lblNewLabel_1 = new JLabel("(Using Student first, last name or registration number)");
		lblNewLabel_1.setFont(new Font("Arial Narrow", Font.ITALIC, 11));
		panel_1.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 75, 589, 248);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
        		int row = table.getSelectedRow();
                Student thisStudent = new Student();
				try {
					thisStudent.registrationNo = table.getModel().getValueAt(row, 0).toString();
					ResultSet rs = thisStudent.getProfile();
					rs.next();
					thisStudent = thisStudent.fromResultSet(rs);
					
					EditStudent editStudent = new EditStudent(thisStudent, studentsPage);
	                editStudent.setVisible(true);
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(Search.this, "Failed to load student details");
                    e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search.this.setVisible(false);
			}
		});
		btnClose.setForeground(new Color(255, 0, 0));
		btnClose.setBounds(542, 0, 79, 29);
		contentPane.add(btnClose);
	}
	
	public void searchFunc() {
		searchTerm = txtSearch.getText();
		try {
			pst = con.prepareStatement("SELECT reg_no, first_name, last_name, gender, class FROM students WHERE reg_no LIKE ? OR first_name LIKE ? OR last_name LIKE ?");
			String editedTerm = searchTerm+"%";
			pst.setString(1, editedTerm);
			pst.setString(2, editedTerm);
			pst.setString(3, editedTerm);
			rs = pst.executeQuery();
			

			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
