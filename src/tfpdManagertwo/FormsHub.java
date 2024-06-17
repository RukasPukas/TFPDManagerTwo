package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormsHub 
{
	JFrame formsFrame;
	JPanel panel;
	
	
	FormsHub(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail)
	{
		formsFrame = new JFrame("Forms");
		formsFrame.setSize(535,573);
		formsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		formsFrame.setLocationRelativeTo(null);
		formsFrame.setResizable(false);
		
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		formsFrame.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		formsFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel headLabel = new JLabel("Forms");
		headLabel.setForeground(Color.WHITE);
		headLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
		headLabel.setBounds(206, 36, 100, 40);
		panel.add(headLabel);
		
		JButton overTimeButton = new JButton("Overtime Form");
		overTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new OverTimeFrame( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				formsFrame.dispose();
			}
		});
		overTimeButton.setBackground(Color.LIGHT_GRAY);
		overTimeButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		overTimeButton.setBounds(143, 103, 213, 59);
		panel.add(overTimeButton);
		
		JButton sickTimeButton = new JButton("Sick Time Form");
		sickTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SickTimeForm(enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				formsFrame.dispose();
			}
		});
		sickTimeButton.setBackground(Color.LIGHT_GRAY);
		sickTimeButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		sickTimeButton.setBounds(143, 185, 213, 59);
		panel.add(sickTimeButton);
		
		JButton vacationTimeButton = new JButton("Vacation Form");
		vacationTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VacationForm(enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				formsFrame.dispose();
			}
		});
		vacationTimeButton.setBackground(Color.LIGHT_GRAY);
		vacationTimeButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		vacationTimeButton.setBounds(143, 270, 213, 59);
		panel.add(vacationTimeButton);
		
		JButton callOffButton = new JButton("Call Off Form");
		callOffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CallOffForm(enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				formsFrame.dispose();
			}
		});
		callOffButton.setBackground(Color.LIGHT_GRAY);
		callOffButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		callOffButton.setBounds(143, 355, 213, 59);
		panel.add(callOffButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBackground(Color.LIGHT_GRAY);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				formsFrame.dispose();
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		cancelButton.setBounds(143, 441, 213, 59);
		panel.add(cancelButton);
		
		
		
		
		
		
		
		
		
		formsFrame.setVisible(true);
	}
}
