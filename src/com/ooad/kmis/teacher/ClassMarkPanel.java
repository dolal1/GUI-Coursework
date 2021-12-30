package com.ooad.kmis.teacher;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import com.ooad.kmis.Subjects;
import com.ooad.kmis.student.Student;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClassMarkPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String thisClass;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	String searchTerm;
	Calendar cal = Calendar.getInstance();
	int currentYear = cal.get(Calendar.YEAR);
	
	private String subject;
	private String year;
	private String term;
	
	
	private JTextField txtSeachStudent;
	private JTable marksTable;
	private JTable searchTable;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(ClassMarkPanel.this, "Failed to locate JDBC Driver");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ClassMarkPanel.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the panel.
	 */
	public ClassMarkPanel(String tClass, String subj, String yr, String tm) {
		Connect();
		thisClass = tClass;
		subject = subj;
		year = yr;
		term = tm;
		
		final String finalprovidedClass = tClass;
		setLayout(null);
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(6, 6, 720, 329);
		add(panel_1);
		
		JLabel lblEnterNewMark = new JLabel("Enter new Mark");
		lblEnterNewMark.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		lblEnterNewMark.setBounds(33, 7, 152, 16);
		panel_1.add(lblEnterNewMark);
		
		txtSeachStudent = new JTextField();
		txtSeachStudent.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				thisClass = finalprovidedClass;
				searchFunc();
			}
		});
		txtSeachStudent.setColumns(10);
		txtSeachStudent.setBounds(29, 48, 139, 26);
		panel_1.add(txtSeachStudent);
		
		JLabel lblNewLabel_1 = new JLabel("Seach by registration number or name");
		lblNewLabel_1.setFont(new Font("Arial Narrow", Font.ITALIC, 13));
		lblNewLabel_1.setBounds(17, 75, 250, 16);
		panel_1.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisClass = finalprovidedClass;
				searchFunc();
			}
		});
		btnNewButton.setBounds(192, 48, 85, 29);
		panel_1.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(285, 7, 429, 316);
		panel_1.add(scrollPane);
		
		marksTable = new JTable();
		marksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
        		int row = marksTable.getSelectedRow();
                Marks marks = new Marks(subject, year, term, finalprovidedClass).fromTableModel(marksTable.getModel(), row);
                EditMarks editStudent = new EditMarks(marks, ClassMarkPanel.this);
                editStudent.setLocationRelativeTo(null);
                editStudent.setVisible(true);
			}
		});
		scrollPane.setViewportView(marksTable);
		//load Marks into table
		loadMarks();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 113, 271, 210);
		panel_1.add(scrollPane_1);
		
		searchTable = new JTable();
		searchTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
        		int row = searchTable.getSelectedRow();
                Student thisStudent = new Student();
				try {
					thisStudent.registrationNo = searchTable.getModel().getValueAt(row, 0).toString();
					thisStudent.firstName = searchTable.getModel().getValueAt(row, 1).toString();
					thisStudent.lastName = searchTable.getModel().getValueAt(row, 2).toString();
					thisStudent.studentClass = searchTable.getModel().getValueAt(row, 3).toString();
					ResultSet rs = thisStudent.getProfile();
					rs.next();
					thisStudent = thisStudent.fromResultSet(rs);
					
					EnterMarks enterMarks = new EnterMarks(thisStudent, subject, year, term, ClassMarkPanel.this);
					enterMarks.setLocationRelativeTo(null);
					enterMarks.setVisible(true);
				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(ClassMarkPanel.this, "Failed to load student details");
                    e1.printStackTrace();
				}
			}
		});
		scrollPane_1.setViewportView(searchTable);

	}
	public void searchFunc() {
		searchTerm = txtSeachStudent.getText();
		try {
			pst = con.prepareStatement(
					"SELECT reg_no, first_name, last_name, class FROM students WHERE (reg_no LIKE ? OR first_name LIKE ? OR last_name LIKE ?) AND class = ?");
			String editedTerm = searchTerm + "%";
			pst.setString(1, editedTerm);
			pst.setString(2, editedTerm);
			pst.setString(3, editedTerm);
			pst.setString(4, thisClass);
			rs = pst.executeQuery();

			searchTable.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void loadMarks() {
		try {
			if(subject.intern() == Subjects.english.intern()) {
				pst = con.prepareStatement("SELECT reg_no, BOT, MOT, EOT FROM english WHERE class = ? and year = ? and term = ?");
			} else if(subject.intern() == Subjects.math.intern()) {
				pst = con.prepareStatement("SELECT reg_no, BOT, MOT, EOT FROM mathematics WHERE class = ? and year = ? and term = ?");
			} else if(subject.intern() == Subjects.sst.intern()) {
				pst = con.prepareStatement("SELECT reg_no, BOT, MOT, EOT FROM social_studies WHERE class = ? and year = ? and term = ?");
			} else if(subject.intern() == Subjects.science.intern()) {
				pst = con.prepareStatement("SELECT reg_no, BOT, MOT, EOT FROM science WHERE class = ? and year = ? and term = ?");
			} else {
	            JOptionPane.showMessageDialog(ClassMarkPanel.this, "Invalid subject name");
			}
			
            pst.setString(1, thisClass);
            pst.setString(2, year);
            pst.setString(3, term);
            
			rs = pst.executeQuery();
			//use the DButils jar package for populating student data.
			//automatically into the table.
			marksTable.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ClassMarkPanel.this, "Failed to load marks data");
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ClassMarkPanel.this, "Failed to load marks data");
		}
	}

}
