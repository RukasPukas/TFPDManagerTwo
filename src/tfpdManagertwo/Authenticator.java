package tfpdManagertwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Authenticator {
	Authenticator(String enteredUserName, String enteredPassword, JFrame loginFrame,JTextField userNameTextField, JPasswordField passwordField){
		
		File file = new File("User/"+enteredUserName+".txt");
		if(file.exists()) 
		{
			System.out.println("User Account Verified");
			
			try 
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				try
				{
					while ((line = br.readLine()) != null)
					{
					    System.out.println("Correct user password token: "+line); 
						if(line.equals(enteredPassword)) 
						{
							System.out.println("Password Authentication successful.");
							new SecurityAuthenticator(enteredUserName, enteredPassword,loginFrame);
						}
						else 
						{
							JOptionPane.showMessageDialog(null, "The password you entered for this account is incorrect.");
							System.out.println("The entered password of: "+enteredPassword+" was incorrect.");
							passwordField.setText("");
							passwordField.requestFocus();
						}
					}

				} 
				catch (IOException e)
				{
					
					e.printStackTrace();
				}
			} 
			catch (FileNotFoundException e) 
			{
				JOptionPane.showMessageDialog(null, "There was an issue accessing your account password.");
			}
		}
		else 
		{
			JOptionPane.showMessageDialog(null, "Account not found.");
			userNameTextField.setText("");
			passwordField.setText("");
			userNameTextField.requestFocus();
		}
		
	}

}
