package tfpdManagertwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EmailAuthenticator {
	
	String userEmail;
	
	EmailAuthenticator(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, JFrame loginFrame)
	{
		
		File file = new File("User/Email/"+enteredUserName+".txt");
		
		if(file.exists())
		{
			System.out.println("User Email file has been found.");
			
			try 
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				try
				{
					while ((line = br.readLine()) != null)
					{
						userEmail=line;
						
					}
					
					System.out.println("User email is: " + userEmail);

				} 
				catch (IOException e)
				{
					
					JOptionPane.showMessageDialog(null, "There was an issue reading the user's email file. This may cause future issues.");
				}
			} 
			catch (FileNotFoundException e) 
			{
				JOptionPane.showMessageDialog(null, "There was an issue accessing your account email.");
			}
			
			
		}
		else
		{
			JOptionPane.showMessageDialog(null, "No Email account has been found associated with this account. This may cause future issues.");
		}
		
		
		
		new CentralHub(enteredUserName,enteredPassword,adminStatus,superAdminStatus,userEmail);
		loginFrame.dispose();
	}

}
