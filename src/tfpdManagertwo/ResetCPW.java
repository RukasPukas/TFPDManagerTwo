package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ResetCPW {
	
	
	JFrame resetCPWFrame;
	JPanel panel;
	private JTextField textField;
	
	ResetCPW(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame hubFrame)
	{
		resetCPWFrame = new JFrame("Change your password");
		resetCPWFrame.setSize(535,364);
		resetCPWFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		resetCPWFrame.setLocationRelativeTo(null);
		resetCPWFrame.setResizable(false);
		
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		resetCPWFrame.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		resetCPWFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel newPasswordPrompt = new JLabel("Enter your new pasword:");
		newPasswordPrompt.setForeground(Color.WHITE);
		newPasswordPrompt.setFont(new Font("Monospaced", Font.BOLD, 30));
		newPasswordPrompt.setBounds(52, 36, 421, 60);
		panel.add(newPasswordPrompt);
		
		textField = new JTextField();
		textField.setBounds(162, 107, 191, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File userFile = new File("User/"+enteredUserName+".txt");
				if(userFile.exists())
				{
					
					String newValue = textField.getText().trim();
					
					if (!newValue.equals(""))
						{
							 try (FileWriter fileWriter = new FileWriter(userFile, false))
							 {
						            fileWriter.write(newValue);
						            fileWriter.close();
						            System.out.println("User Password overwritten successfully to: "+ newValue);
						            JOptionPane.showMessageDialog(null, "Your Password has been changed to: " + newValue);
						            new UserOptionsHub(enteredUserName,enteredPassword,adminStatus,superAdminStatus,userEmail,hubFrame);
						            resetCPWFrame.dispose();
						            
						            
						            
				        	 } 
							 catch (IOException e1) 
							 {
						        	System.out.println("User password was unable to be updated.");
						        	JOptionPane.showMessageDialog(null, "The user password was unable to be updated.");
					         }
						}
						else 
						{
							JOptionPane.showMessageDialog(null, "Your Password can't be blank.");
							textField.setText("");
						}
					
				}
				else
				{
					System.out.println("User file unable to be located.");
					JOptionPane.showMessageDialog(null, "The user account was unable to be found.");
				}
				
			}
		});
		submitButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		submitButton.setBounds(187, 162, 139, 38);
		panel.add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				resetCPWFrame.dispose();
				new UserOptionsHub(enteredUserName,enteredPassword,adminStatus,superAdminStatus,userEmail,hubFrame);
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		cancelButton.setBounds(187, 211, 139, 38);
		panel.add(cancelButton);
		
		
		
		
		
		resetCPWFrame.setVisible(true);
		
		
	}
}
