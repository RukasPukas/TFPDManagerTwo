package tfpdManagertwo;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Font;


public class IntroPage {
	
	JFrame introFrame;
	JPanel panel;
	ImageIcon img;
	private JLabel courageLabel;
	private JLabel honorLabel;
	private JLabel integrityLabel;
	
	IntroPage()
	{
		
		introFrame = new JFrame("TFPD Manager 2.0");
		introFrame.setResizable(false);
		introFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		introFrame.getContentPane().setBackground(new Color(0, 0, 0));
		introFrame.setSize(688,691);
		introFrame.setLocationRelativeTo(null);
		introFrame.setResizable(false);
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
	
		introFrame.setIconImage(img);
		introFrame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 674, 1);
		panel.setBackground(new Color(0, 0, 0));
		introFrame.getContentPane().add(panel);
		panel.setLayout(null);
		

		
		Image displayImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon resizedImage = new ImageIcon(displayImage);
		JLabel ltfpdImage = new JLabel(resizedImage);
		ltfpdImage.setBounds(220, 36, 219, 244);
		introFrame.getContentPane().add(ltfpdImage);
		
		courageLabel = new JLabel("Courage");
		courageLabel.setForeground(new Color(255, 255, 255));
		courageLabel.setFont(new Font("Lucida Calligraphy", Font.ITALIC, 32));
		courageLabel.setBounds(256, 273, 162, 100);
		introFrame.getContentPane().add(courageLabel);
		
		honorLabel = new JLabel("Honor");
		honorLabel.setForeground(Color.WHITE);
		honorLabel.setFont(new Font("Lucida Calligraphy", Font.ITALIC, 32));
		honorLabel.setBounds(266, 375, 132, 100);
		introFrame.getContentPane().add(honorLabel);
		
		integrityLabel = new JLabel("Integrity");
		integrityLabel.setForeground(Color.WHITE);
		integrityLabel.setFont(new Font("Lucida Calligraphy", Font.ITALIC, 32));
		integrityLabel.setBounds(244, 470, 162, 100);
		introFrame.getContentPane().add(integrityLabel);
		
		introFrame.setVisible(true);
		
        Timer timer = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                introFrame.dispose();
                new LoginPage();
            }
        });
        timer.setRepeats(false);
        // Start the timer
        timer.start();
		
 

	}
	}

