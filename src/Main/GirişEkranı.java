package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GirişEkranı extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GirişEkranı frame = new GirişEkranı();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public GirişEkranı() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 572);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(98, 106, 123));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnNewButton = new JButton("Başla");
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(237, 315, 153, 45);
		btnNewButton.setBackground(new Color(109, 122, 146));
		btnNewButton.setBorderPainted(false);   // Kenarlığı kaldır
		btnNewButton.setFocusPainted(false);    // Tıklanınca oluşan odak çerçevesini kaldır
		
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(56, 10, 512, 525);
		lblNewLabel.setIcon(new ImageIcon("C:\\java\\WhoWantsToBeMillioner\\kmoiphoto.jpg"));
		contentPane.add(lblNewLabel);
		
		
		
		
		
		

		
	}
}
