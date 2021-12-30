package com.ooad.kmis.teacher;

import java.awt.*;
import javax.swing.*;

public class Dashboard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final JPanel cards = new JPanel(new CardLayout());
    
    private static Teacher teacher = new Teacher();

	public Dashboard() {
        this.setPreferredSize(new Dimension(500, 300));
	}
	
	public Dashboard(Teacher tr) {
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
    
    public static void launch(Teacher tr) {
    	main(null);
    	setTeacher(tr);
    }

    public static Teacher getTeacher() {
		return teacher;
	}

	public static void setTeacher(Teacher tr) {
		teacher = tr;
	}

	private static void create() {
        JFrame f = new JFrame();
		f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel studentsPanel = new StudentsPage();
        cards.add(StudentsPage.name, studentsPanel);

        JPanel marksPanel = new MarksPage(teacher);
        cards.add(MarksPage.name, marksPanel);

        JPanel teachersPanel = new TeachersPage();
        cards.add(TeachersPage.name, teachersPanel);
        
//        Dashboard marks = new Dashboard();
//        cards.add("marks", marks);
//        cards.add(marks);
//        Dashboard subjectRegister = new Dashboard();
//        cards.add("subjectRegister", subjectRegister);
//        cards.add(subjectRegister);
        
        
        
//        JPanel control = new JPanel();
        JPanel control = new DashboardMenu(cards, f, teacher);
//        control.add(new JButton(new AbstractAction("\u22b2Prev") {
//
//            /**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//            public void actionPerformed(ActionEvent e) {
//                CardLayout cl = (CardLayout) cards.getLayout();
//                cl.previous(cards);
//            }
//        }));
//        control.add(new JButton(new AbstractAction("Next\u22b3") {
//
//            /**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//            public void actionPerformed(ActionEvent e) {
//                CardLayout cl = (CardLayout) cards.getLayout();
//                cl.next(cards);
//            }
//        }));
        
        f.add(cards, BorderLayout.CENTER);
        f.add(control, BorderLayout.WEST);
//        f.add(control, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
