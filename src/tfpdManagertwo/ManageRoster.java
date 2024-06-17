package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ManageRoster {
	
	
	JFrame rosterManagementFrame;
	JPanel panel;
	
	ManageRoster(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame userOptionFrame, JFrame hubFrame)
	{
		
		String filePath = "Generalroster/generalroster.txt";
		String ftFilePath ="FTRoster/FTRoster.txt";
		
		
		
		rosterManagementFrame = new JFrame("Manage Roster");
		rosterManagementFrame.setSize(604,532);
		rosterManagementFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		rosterManagementFrame.setLocationRelativeTo(null);
		rosterManagementFrame.setResizable(false);
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		rosterManagementFrame.setIconImage(img);
		rosterManagementFrame.setResizable(false);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		rosterManagementFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Monospaced", Font.PLAIN, 12));
		comboBox.setBounds(76, 138, 435, 47);
		panel.add(comboBox);
		
		
		//<------------------------------------------------------This is where generalroster.txt is read------------------------------------->
		 try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                comboBox.addItem(line);
	            }
	        } catch (IOException e) {
	        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the roster.");
	        	System.out.println("An error occurred retrieving the roster.");
	            e.printStackTrace();
	        }
		
		//<--------------------------------------------------------End reading of generalroster.txt------------------------------------------->
		
		
		JLabel manageUserPrompt = new JLabel("Select a member to manage:");
		manageUserPrompt.setForeground(Color.WHITE);
		manageUserPrompt.setFont(new Font("Monospaced", Font.BOLD, 27));
		manageUserPrompt.setBounds(87, 41, 435, 64);
		panel.add(manageUserPrompt);
		
		JButton addUserButton = new JButton("Add New User");
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rosterManagementFrame.setEnabled(false);
				new AddUserToRoster(rosterManagementFrame,enteredUserName,enteredPassword,adminStatus,superAdminStatus,userEmail);
			}
		});
		addUserButton.setFont(new Font("Monospaced", Font.BOLD, 12));
		addUserButton.setBounds(226, 231, 143, 47);
		panel.add(addUserButton);
		
		JButton deleteUser = new JButton("Delete Selected");
		deleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 Object selectedItem = comboBox.getSelectedItem();
				 String selectedItemString = comboBox.getSelectedItem().toString();

				 
				 
			        if (selectedItem != null) 
			        {
			        	
			        	
			        	File ftCheck = new File("User/"+selectedItemString+".txt");
			        	if(ftCheck.exists()&&superAdminStatus==false) {
			        		JOptionPane.showMessageDialog(null, "You do not have permission to remove full-time users.");
			        	}
			        	else
			        	{
			        		ftCheck.delete();
			        		System.out.println("Account File Deleted");
			        		File emailDelete = new File("User/Email/"+selectedItemString+".txt");
			        		if(emailDelete.exists())
			        		{
			        			emailDelete.delete();
			        			System.out.println("Email File Deleted");
			        		}
			        		File adminDelete = new File("User/Permissions/Admin/" + selectedItemString+".txt");
			        		if(adminDelete.exists()) 
			        		{
			        			adminDelete.delete();
			        			System.out.println("Admin File Deleted");
			        		}
			        		File superDelete = new File ("User/Permissions/SuperAdmin/"+selectedItemString+".txt");
			        		if(superDelete.exists()) 
			        		{
			        			superDelete.delete();
			        			System.out.println("Super Admin File Deleted");
			        		}
			        		
			        		
				            //<--------------------------------------- Remove the selected item from the JComboBox---------------------->
				            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();
				            model.removeElement(selectedItem);
	
				            // <-------------------------------------Update the general rostertext file by rewriting its content---------------------->
				            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) 
				            {
				                for (int i = 0; i < model.getSize(); i++) 
				                {
				                    writer.write(model.getElementAt(i).toString());
				                    writer.newLine();
				                }
				            } 
				            catch (IOException ex) 
				            {
				            	JOptionPane.showMessageDialog(null, "An issue occurred updating the general roster.");
				            	System.out.println("Error removing selected item from roster.");
				                ex.printStackTrace();
				                
				            }
				            //end updating general roster
				            //begin updating ft roster
				            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ftFilePath))) 
				            {
				                for (int i = 0; i < model.getSize(); i++) 
				                {
				                    writer.write(model.getElementAt(i).toString());
				                    writer.newLine();
				                }
				            } 
				            catch (IOException ex) 
				            {
				            	JOptionPane.showMessageDialog(null, "An issue occurred updating the FT roster.");
				            	System.out.println("Error removing selected item from FT roster.");
				                ex.printStackTrace();
				                
				            }
				            //end updating ft roster
				        }
			        }

				
			}
		});
		
		if(adminStatus == true) 
		{
			deleteUser.setEnabled(true);
		}
		else
		{
			deleteUser.setEnabled(false);
		}
		deleteUser.setFont(new Font("Monospaced", Font.BOLD, 11));
		deleteUser.setBounds(226, 291, 143, 47);
		panel.add(deleteUser);
		
		JButton editUserButton = new JButton("Edit User");
		editUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				Object selectedObject = comboBox.getSelectedItem();
				if(selectedObject == null)
				{
					JOptionPane.showMessageDialog(null, "You must select a user to edit.");
				}
				
				else 
				{
				String userSelected = selectedObject.toString();
				System.out.println("The selected user is: " + userSelected);
				new EditGeneralRoster(enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, rosterManagementFrame,userSelected);
				
				rosterManagementFrame.setEnabled(false);
				}
			}
		});
		editUserButton.setFont(new Font("Monospaced", Font.BOLD, 12));
		editUserButton.setBounds(226, 349, 143, 47);
		if(superAdminStatus==false)
		{
			editUserButton.setEnabled(false);
		}
		else
		{
			editUserButton.setEnabled(true);
		}
		panel.add(editUserButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rosterManagementFrame.dispose();
				new UserOptionsHub(enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, userOptionFrame);
			}
		});
		exitButton.setFont(new Font("Monospaced", Font.BOLD, 12));
		exitButton.setBounds(226, 407, 143, 47);
		panel.add(exitButton);
		
		
		
		
		
		
		
		
		
		
		rosterManagementFrame.setVisible(true);
		
	}
}
