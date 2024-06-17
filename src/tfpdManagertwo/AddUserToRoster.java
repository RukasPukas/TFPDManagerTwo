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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class AddUserToRoster
{

	JFrame addToRosterFrame;
	JPanel panel;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField emailTextField;
	
	AddUserToRoster(JFrame rosterManagementFrame, String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail)
	{
		addToRosterFrame = new JFrame("Manage Roster");
		addToRosterFrame.setSize(604,532);
		addToRosterFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		addToRosterFrame.setLocationRelativeTo(null);
		addToRosterFrame.setResizable(false);
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		addToRosterFrame.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		addToRosterFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel generalPrompt = new JLabel("Add a new PT member to roster:");
		generalPrompt.setForeground(Color.WHITE);
		generalPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
		generalPrompt.setBounds(74, 50, 470, 33);
		panel.add(generalPrompt);
		
		JLabel firstNamePrompt = new JLabel("First Name:");
		firstNamePrompt.setForeground(Color.WHITE);
		firstNamePrompt.setFont(new Font("Monospaced", Font.BOLD, 16));
		firstNamePrompt.setBounds(74, 175, 122, 41);
		panel.add(firstNamePrompt);
		
		JLabel lastNamePrompt = new JLabel("Last Name:");
		lastNamePrompt.setForeground(Color.WHITE);
		lastNamePrompt.setFont(new Font("Monospaced", Font.BOLD, 16));
		lastNamePrompt.setBounds(74, 227, 122, 41);
		panel.add(lastNamePrompt);
		
		JLabel emailPrompt = new JLabel("Email:");
		emailPrompt.setForeground(Color.WHITE);
		emailPrompt.setFont(new Font("Monospaced", Font.BOLD, 16));
		emailPrompt.setBounds(74, 280, 122, 41);
		panel.add(emailPrompt);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setBounds(250, 187, 265, 29);
		panel.add(firstNameTextField);
		firstNameTextField.setColumns(10);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(250, 239, 265, 29);
		panel.add(lastNameTextField);
		
		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setBounds(250, 292, 265, 29);
		panel.add(emailTextField);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String enteredFirstName = firstNameTextField.getText().trim();
				String enteredLastName = lastNameTextField.getText().trim();
				String newUserName ="";
				String newEmail = emailTextField.getText().trim();
				if (!enteredFirstName.isEmpty()) 
				{//-----------------------------------------Detect first name and set First initial--------------------------------->
		            String capitalizedFirstLetter = enteredFirstName.substring(0, 1).toUpperCase();
		            System.out.println("Capitalized First Letter: " + capitalizedFirstLetter);
		            //<-------------------------------------Detect last name-------------------------------------------------------->
		            if(!enteredLastName.isEmpty())
		            {
		            	//<----------------------------------------retrieve last name and new user name------------------------------->
		            	 newUserName = capitalizedFirstLetter+enteredLastName;
		            	 System.out.println("New User name = " + newUserName);
		            	 //<----------------------------------------retrieve email check----------------------------------------------->
		            	 if(!newEmail.isEmpty()) 
		            	 {
		            		 
		            		//<-------------------------------------Begin entry of data if valid--------------------------------------->
		            		 File generalRosterCheck = new File("GeneralRoster/generalroster.txt");
		            		 if(generalRosterCheck.exists())
		            		 {
		            			 boolean userExists = false;
		                         try (BufferedReader br = new BufferedReader(new FileReader(generalRosterCheck))) 
		                         {
		                             String line;
		                             
		                             while ((line = br.readLine()) != null) 
		                             {
		                                 if (line.trim().equalsIgnoreCase(newUserName)) 
		                                 {
		                                     
		                                     JOptionPane.showMessageDialog(null, "User Account already exists.");
		                                     userExists = true;
		                                     break; 
		                                 }
		                             }
		                             
		                             
		                         } 
		                         catch (IOException ex)
		                         {
		                        	 JOptionPane.showMessageDialog(null, "Error detecting if Account already exists.");
		                        	 System.out.println("Error detecting if user account exists");
		                             ex.printStackTrace();
		                         }
		                         
		                        //<-------------------------------------------add user to generalroster------------------------------------>
		                         if (userExists==false) 
		                         {
		                            
		                             try (FileWriter writer = new FileWriter(generalRosterCheck, true);
		                                  BufferedWriter bw = new BufferedWriter(writer);
		                                  PrintWriter out = new PrintWriter(bw)) 
		                             {

		                                 out.println(newUserName);
		                                 JOptionPane.showMessageDialog(null, "User Account added successfully.");
		                                 
		                                 File emailCheckFile = new File("User/Email/"+newUserName+".txt");
		                                 
		                                 if(emailCheckFile.exists()) 
		                                 {
		                                	    try (FileWriter writer2 = new FileWriter(emailCheckFile, false);
		                                	            BufferedWriter bw2 = new BufferedWriter(writer2);
		                                	            PrintWriter out2 = new PrintWriter(bw2))
		                                	    {

		                                	          
		                                	           out2.println(newEmail);

		                                	           JOptionPane.showMessageDialog(null, "An Email file associated with this account has already been found. The new Email has been updated.");

	                                	       } 
		                                	    catch (IOException ex) 
		                                	    {
		                                	           JOptionPane.showMessageDialog(null, "Error writing to the email file.");
		                                	           ex.printStackTrace();
	                                	        }

		                                 }
		                                 else 
		                                 {
		                                	 try {
		                                		    if (!emailCheckFile.exists())
		                                		    {
		                                		        emailCheckFile.createNewFile();
		                                		    }

		                                		    try (FileWriter writer3 = new FileWriter(emailCheckFile, false);
		                                		         BufferedWriter bw3 = new BufferedWriter(writer3);
		                                		         PrintWriter out3 = new PrintWriter(bw3)) {

		                                		        
		                                		        out3.println(newEmail);

		                                		        

		                                		    } catch (IOException ex) {
		                                		        JOptionPane.showMessageDialog(null, "Error writing new Email to the file.");
		                                		        ex.printStackTrace();
		                                		    }

		                                		} catch (IOException e1) {
		                                		    JOptionPane.showMessageDialog(null, "Error creating the email file.");
		                                		    e1.printStackTrace();
		                                		}
		                                 }
		                                	 
		                                 
		                                 

		                             }
		                             catch (IOException ex) 
		                             {
		                                 JOptionPane.showMessageDialog(null, "Error writing to the generalroster file.");
		                                 ex.printStackTrace();
		                             }
		                             
		                             rosterManagementFrame.setEnabled(true);
		                             addToRosterFrame.dispose();
		                         }

		                         
		                         
		            			 
		            		 }
		            		 
		            		 
		            	 }
		            	 else
		            	 {
								System.out.println("Failed to detect email address entry.");
								JOptionPane.showMessageDialog(null, "You must enter in an email address.");
		            	 }
		            
		            	
		            }
		            else
		            {
						System.out.println("Failed to detect last name entry.");
						JOptionPane.showMessageDialog(null, "You must enter in a last name.");
		            }
		            
		            
		        }
				else
				{
					System.out.println("Failed to detect first initial.");
					JOptionPane.showMessageDialog(null, "You must enter in a first name.");
				}
				
			}
		});
		submitButton.setFont(new Font("Monospaced", Font.BOLD, 14));
		submitButton.setBounds(134, 399, 130, 41);
		panel.add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rosterManagementFrame.setEnabled(true);
				addToRosterFrame.dispose();
				
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 14));
		cancelButton.setBounds(325, 399, 130, 41);
		panel.add(cancelButton);
		
		
		addToRosterFrame.setVisible(true);
	}
}
