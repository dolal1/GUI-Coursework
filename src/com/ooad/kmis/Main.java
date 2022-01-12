package com.ooad.kmis;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.ooad.kmis.auth.Login;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Launch login
					Login loginScreen = new Login();
					loginScreen.setLocationRelativeTo(null);
					loginScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
