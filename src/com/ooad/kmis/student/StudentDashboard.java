package com.ooad.kmis.student;

import java.awt.*;

import javax.swing.*;


public class StudentDashboard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final JPanel cards = new JPanel(new CardLayout());
    
    private static Student student = new Student();

	public StudentDashboard() {
        this.setPreferredSize(new Dimension(500, 300));
	}
	
	public StudentDashboard(Student tr) {
		setTeacher(tr);
        this.setPreferredSize(new Dimension(500, 300));
	}

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                create();
            }
        });
    }
    
    public static void launch(Student tr) {
    	main(null);
    	setTeacher(tr);
    }

    public static Student getTeacher() {
		return student;
	}

	public static void setTeacher(Student student) {
		StudentDashboard.student = student;
	}

	private static void create() {
        JFrame f = new JFrame();
		f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel profilePanel = new ProfilePage(student);
        cards.add(ProfilePage.name, profilePanel);
        
        JPanel marksPanel = new MarksPage(student);
        cards.add(MarksPage.name, marksPanel);
        
        JPanel control = new DashboardMenu(cards, f, student);
        
        f.add(cards, BorderLayout.CENTER);
        f.add(control, BorderLayout.WEST);
//        f.add(control, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
