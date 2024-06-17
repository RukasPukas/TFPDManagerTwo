package tfpdManagertwo;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage {
	
	JFrame loginFrame;
	JPanel panel;
	private JTextField userNameTextField;
	private JPasswordField passwordField;
	
	
	LoginPage()
	{
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		
		loginFrame = new JFrame("Login");
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(372,421);
		loginFrame.setIconImage(img);
		panel=new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		loginFrame.getContentPane().add(panel);
		loginFrame.setResizable(false);
		
		panel.setLayout(null);
		
		JLabel passwordPrompt = new JLabel("Password:");
		passwordPrompt.setForeground(Color.WHITE);
		passwordPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
		panel.add(passwordPrompt);
		loginFrame.setLocationRelativeTo(null);
		
		Image displayImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon resizedImage = new ImageIcon(displayImage);
		JLabel ltfpdImage = new JLabel(resizedImage);
		ltfpdImage.setBounds(128, 11, 100, 100);
		ltfpdImage.setBackground(Color.DARK_GRAY);
		ltfpdImage.setOpaque(true);
		passwordPrompt.setBounds(25, 252, 132, 52);
		panel.add(ltfpdImage);
		
		JLabel userPrompt = new JLabel("User Name:");
		userPrompt.setForeground(Color.WHITE);
		userPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
		userPrompt.setBounds(10, 200, 147, 52);
		panel.add(userPrompt);
		
		JLabel tfpdManagerPrompt = new JLabel("TFPD Manager 2.0");
		tfpdManagerPrompt.setForeground(Color.WHITE);
		tfpdManagerPrompt.setFont(new Font("Lucida Calligraphy", Font.ITALIC, 24));
		tfpdManagerPrompt.setBounds(47, 135, 277, 52);
		panel.add(tfpdManagerPrompt);
		
		userNameTextField = new JTextField();
		userNameTextField.setBackground(Color.LIGHT_GRAY);
		userNameTextField.setBounds(160, 221, 164, 20);
		panel.add(userNameTextField);
		userNameTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.LIGHT_GRAY);
		passwordField.setBounds(160, 270, 164, 20);
		panel.add(passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBackground(Color.LIGHT_GRAY);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String enteredUserName = userNameTextField.getText();
                char[] passwordChars = passwordField.getPassword();
                String enteredPassword = new String(passwordChars);
                
                System.out.println("The entered User Name is: "+ enteredUserName);
                System.out.println("The entered User Password is: "+ enteredPassword);
                
                new Authenticator(enteredUserName, enteredPassword,loginFrame, userNameTextField, passwordField);
				
			}
		});
		loginButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		loginButton.setBounds(123, 315, 119, 42);
		panel.add(loginButton);
		
		
		
		loginFrame.setVisible(true);
	}
}
