package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ItemHub
{
	JFrame itemHubFrame;
	JPanel panel;
	
	ItemHub(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail)
	{
		
		itemHubFrame = new JFrame("Item Requests");
		itemHubFrame.setSize(535,364);
		itemHubFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		itemHubFrame.setLocationRelativeTo(null);
		itemHubFrame.setResizable(false);
		
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		itemHubFrame.setIconImage(img);
		

		panel=new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);
		itemHubFrame.getContentPane().add(panel);
		
		JLabel promptLabel = new JLabel("Request Items For Ordering");
		promptLabel.setForeground(Color.WHITE);
		promptLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
		promptLabel.setBounds(95, 44, 333, 47);
		panel.add(promptLabel);
		
		JButton cleaningSuppliesButton = new JButton("Cleaning Supplies");
		cleaningSuppliesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CleaningItems( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				itemHubFrame.dispose();
			}
		});
		cleaningSuppliesButton.setBackground(Color.LIGHT_GRAY);
		cleaningSuppliesButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		cleaningSuppliesButton.setBounds(24, 148, 213, 65);
		panel.add(cleaningSuppliesButton);
		
		JButton medicalSuppliesButton = new JButton("Medical Supplies");
		medicalSuppliesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new MedicalItems( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				itemHubFrame.dispose();
			}
		});
		medicalSuppliesButton.setBackground(Color.LIGHT_GRAY);
		medicalSuppliesButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		medicalSuppliesButton.setBounds(277, 148, 213, 65);
		panel.add(medicalSuppliesButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBackground(Color.LIGHT_GRAY);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				itemHubFrame.dispose();
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		cancelButton.setBounds(148, 251, 213, 65);
		panel.add(cancelButton);
		
		
		
		itemHubFrame.setVisible(true);
	}
}
