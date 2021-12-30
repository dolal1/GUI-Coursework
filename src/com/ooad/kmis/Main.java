//package com.ooad.kmis;
//
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import java.awt.Color;
//import javax.swing.JLabel;
//import java.awt.Font;
//import javax.swing.JButton;
//import javax.swing.JToolBar;
//import javax.swing.JTextArea;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import javax.swing.JTextField;
//import javax.swing.JPasswordField;
//
//public class Main {
//
//	private JFrame frame;
//	private JTextField textField;
//	private JPasswordField passwordField;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Main window = new Main();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the application.
//	 */
//	public Main() {
//		initialize();
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	
//	private void initialize() {
//		frame = new JFrame();
//		frame.setBounds(100, 100, 662, 432);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
//		
//		JToolBar toolBar = new JToolBar();
//		toolBar.setBackground(new Color(51, 153, 51));
//		toolBar.setBounds(0, 0, 662, 34);
//		frame.getContentPane().add(toolBar);
//		
//		JLabel lblNewLabel = new JLabel("Katikamu P S");
//		toolBar.add(lblNewLabel);
//		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
//		
//		JPanel mainPanel = new JPanel();
//		mainPanel.setBounds(0, 37, 662, 367);
//		frame.getContentPane().add(mainPanel);
//		mainPanel.setLayout(null);
//		
//		
//		JPanel panel_1 = new JPanel();
//		panel_1.setBackground(Color.LIGHT_GRAY);
//		panel_1.setBounds(0, 0, 127, 361);
//		mainPanel.add(panel_1);
//		
//		JButton btnStudents = new JButton("Students");
//		btnStudents.setFont(new Font("Arial Narrow", Font.PLAIN, 10));
//		panel_1.add(btnStudents);
//		
//		JButton btnMarks = new JButton("Marks");
//		btnMarks.setFont(new Font("Arial Narrow", Font.PLAIN, 10));
//		panel_1.add(btnMarks);
//		
//		JButton btnSubjects = new JButton("Subjects");
//		btnSubjects.setFont(new Font("Arial Narrow", Font.PLAIN, 10));
//		panel_1.add(btnSubjects);
//		
//		JButton btnGoLogin = new JButton("Login");
//		btnGoLogin.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {          
//			}
//		});
//		panel_1.add(btnGoLogin);
//		
//		JPanel panel_1_1 = new JPanel();
//		panel_1_1.setLayout(null);
//		panel_1_1.setBackground(Color.WHITE);
//		panel_1_1.setBounds(129, 0, 527, 361);
//		mainPanel.add(panel_1_1);
//		
//		JLabel lbName = new JLabel("Name");
//		lbName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
//		lbName.setBounds(31, 74, 86, 16);
//		panel_1_1.add(lbName);
//		
//		JLabel lblSubjectsTaught = new JLabel("Subjects Taught");
//		lblSubjectsTaught.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
//		lblSubjectsTaught.setBounds(31, 112, 86, 16);
//		panel_1_1.add(lblSubjectsTaught);
//		
//		JLabel lbTeacherName = new JLabel("TeacherName");
//		lbTeacherName.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
//		lbTeacherName.setBounds(129, 74, 194, 16);
//		panel_1_1.add(lbTeacherName);
//		
//		JTextArea txtrSubjects = new JTextArea();
//		txtrSubjects.setText("Subjects");
//		txtrSubjects.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
//		txtrSubjects.setBounds(129, 112, 194, 39);
//		panel_1_1.add(txtrSubjects);
//		
//		JPanel panel = new JPanel();
//		panel.setLayout(null);
//		panel.setBorder(null);
//		panel.setBounds(101, 144, 265, 176);
//		panel_1_1.add(panel);
//		
//		JLabel lblNewLabel_1 = new JLabel("Username");
//		lblNewLabel_1.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
//		lblNewLabel_1.setBounds(21, 58, 61, 16);
//		panel.add(lblNewLabel_1);
//		
//		JLabel lblNewLabel_1_1 = new JLabel("Password");
//		lblNewLabel_1_1.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
//		lblNewLabel_1_1.setBounds(21, 87, 61, 16);
//		panel.add(lblNewLabel_1_1);
//		
//		textField = new JTextField();
//		textField.setColumns(10);
//		textField.setBounds(94, 53, 130, 26);
//		panel.add(textField);
//		
//		JButton btnLogin = new JButton("Login");
//		btnLogin.setBounds(71, 124, 117, 29);
//		panel.add(btnLogin);
//		
//		passwordField = new JPasswordField();
//		passwordField.setBounds(94, 82, 130, 26);
//		panel.add(passwordField);
//		
//		JLabel lblNewLabel_2 = new JLabel("KMIS");
//		lblNewLabel_2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
//		lblNewLabel_2.setBounds(222, 28, 51, 24);
//		panel_1_1.add(lblNewLabel_2);
//	}
//}
