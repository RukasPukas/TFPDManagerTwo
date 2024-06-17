package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;


public class EditGeneralRoster
{
	JFrame editGenrealRosterFrame;
	JPanel panel;
	private JLabel userEmailPrompt;
	private JLabel userStartDatePrompt;
	private JLabel hoursWorkedPrompt;
	private JTextField emailTextField;
	private JTextField startDateTextField;
	private JTextField hoursWorkedTextField;
	String emailContentFinal,startDateContentFinal,hoursWorkedContentFinal;
	private JRadioButton partTimeRadioButton, rdbtnSuperAdmin, adminRadioButton,userRadioButton;
	
	int x;
	
	EditGeneralRoster(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame rosterManagementFrame, String userSelected)
	{
		//<-----------------------------------------------------INITIALIZE SECURITY SELECTION------------------------------------------------->
		x=0;
		System.out.println("Security Permission = "+x);
		//<-----------------------------------------------------THATS ALL---------------------------------------------------------------------->
		
		//<-----------------------------------------------------AUTO FILLING BEGIN------------------------------------------------------------>

		
		//<----------------------------------------------------------EMAIL AUTO FILL---------------------------------------------------------->
		File getEmailFile = new File("User/Email/"+userSelected+".txt");
		if(getEmailFile.exists())
		{
			try 
			{
                FileReader fileReader = new FileReader(getEmailFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder emailContent = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                	emailContent.append(line);
                }
                bufferedReader.close();

                // Convert the StringBuilder to a String
                emailContentFinal = emailContent.toString();
                System.out.println("Email Line added: " + emailContentFinal);

            } 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(null, "An issue occurred retrieving the user Email.");
                e.printStackTrace();
            }
        } 
		else 
		{
            System.out.println("Email not found.");
            emailContentFinal ="N/A";
        }
		
		//<------------------------------------------------------END EMAIL AUTO FILL------------------------------------------------------>
		//<------------------------------------------------------BEGIN START DATE AUTO FILL----------------------------------------------->
		File getStartDateFile = new File("StartDates/"+userSelected+".txt");
		if(getStartDateFile.exists()) 
		{
			try 
			{
                FileReader fileReader = new FileReader(getStartDateFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder startDateContent = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                	startDateContent.append(line);
                }
                bufferedReader.close();

                // Convert the StringBuilder to a String
                startDateContentFinal = startDateContent.toString();
                System.out.println("Start Date Line added: " + startDateContentFinal);

            } 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(null, "An issue occurred retrieving the user start date.");
                e.printStackTrace();
            }
		}
		else 
		{
            System.out.println("Start Date not found.");
            startDateContentFinal ="N/A";
        }
		//<------------------------------------------------------END START DATE AUTO FILLING----------------------------------------------->
		//<------------------------------------------------------START HOURS WORKED AUTO FILLING------------------------------------------->
		File getHoursWorkedFile = new File("HoursWorked/"+userSelected+".txt");
		if(getHoursWorkedFile.exists()) 
		{
			try 
			{
                FileReader fileReader = new FileReader(getHoursWorkedFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder hoursWorkedContent = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                	hoursWorkedContent.append(line);
                }
                bufferedReader.close();

                // Convert the StringBuilder to a String
                hoursWorkedContentFinal = hoursWorkedContent.toString();
                System.out.println("Start Date Line added: " + hoursWorkedContentFinal);

            } 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(null, "An issue occurred retrieving the user Hours Worked.");
                e.printStackTrace();
            }
		}
		else 
		{
            System.out.println("Hours Worked not found.");
            hoursWorkedContentFinal ="N/A";
        }
		
		//<------------------------------------------------------AUTO FILLING END--------------------------------------------------------->
		
		
		editGenrealRosterFrame = new JFrame("Manage User: "+ userSelected);
		editGenrealRosterFrame.setSize(604,532);
		editGenrealRosterFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		editGenrealRosterFrame.setLocationRelativeTo(null);
		editGenrealRosterFrame.setResizable(false);
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		editGenrealRosterFrame.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		editGenrealRosterFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Editing Member: " + userSelected);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
		lblNewLabel.setBounds(10, 11, 438, 61);
		panel.add(lblNewLabel);
		
		userEmailPrompt = new JLabel("User Email:");
		userEmailPrompt.setForeground(Color.WHITE);
		userEmailPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		userEmailPrompt.setBounds(10, 104, 151, 61);
		panel.add(userEmailPrompt);
		
		userStartDatePrompt = new JLabel("User Start Date:");
		userStartDatePrompt.setForeground(Color.WHITE);
		userStartDatePrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		userStartDatePrompt.setBounds(10, 176, 217, 61);
		panel.add(userStartDatePrompt);
		
		hoursWorkedPrompt = new JLabel("Hours Worked:");
		hoursWorkedPrompt.setForeground(Color.WHITE);
		hoursWorkedPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		hoursWorkedPrompt.setBounds(10, 248, 183, 61);
		panel.add(hoursWorkedPrompt);
		
		emailTextField = new JTextField(emailContentFinal);
		emailTextField.setBounds(240, 124, 306, 28);
		panel.add(emailTextField);
		emailTextField.setColumns(10);
		
		startDateTextField = new JTextField(startDateContentFinal);
		startDateTextField.setColumns(10);
		startDateTextField.setBounds(240, 200, 306, 28);
		panel.add(startDateTextField);
		
		hoursWorkedTextField = new JTextField(hoursWorkedContentFinal);
		hoursWorkedTextField.setColumns(10);
		hoursWorkedTextField.setBounds(240, 272, 306, 28);
		panel.add(hoursWorkedTextField);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				//<------------------------------------------------------BEGIN UPDATING EMAIL------------------------------------------->
				String updatedEmail = emailTextField.getText().trim();
				File getEmailFile = new File("User/Email/"+userSelected+".txt");
				if(getEmailFile.exists())
				{
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(getEmailFile))) 
					{
			            writer.write(updatedEmail);
			            writer.close();
			            System.out.println("Email was updated to: " + updatedEmail);
			        } 
					catch (IOException e1) 
					{
			        	JOptionPane.showMessageDialog(null, "An issue occurred writing new user email content.");
			            e1.printStackTrace();
			        }
				}
				else
				{
					try
					{
						 getEmailFile.createNewFile();
							try (BufferedWriter writer = new BufferedWriter(new FileWriter(getEmailFile))) 
							{
					            writer.write(updatedEmail);
					            writer.close();
					            System.out.println("Email was updated to: " + updatedEmail);
					        } 
							catch (IOException e1) 
							{
					        	JOptionPane.showMessageDialog(null, "An issue occurred writing new user email content.");
					            e1.printStackTrace();
					        }
						 
					} 
					catch (IOException e1)
					{
						JOptionPane.showMessageDialog(null, "No Email file was found, and TFPD Manager was unable to create a new Email File.");
						e1.printStackTrace();
					}
				}
				//<--------------------------------------------------------------End updating email file---------------------------------------->
				//<--------------------------------------------------------------Begin updating user start date file---------------------------->
				
				String updatedStartDate = startDateTextField.getText().trim();
				File getStartDateFile = new File("StartDates/"+userSelected+".txt");
				if(getStartDateFile.exists())
				{
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(getStartDateFile))) 
					{
			            writer.write(updatedStartDate);
			            writer.close();
			            System.out.println("Start date was updated to: " + updatedStartDate);
			        } 
					catch (IOException e1) 
					{
			        	JOptionPane.showMessageDialog(null, "An issue occurred writing new user start date content.");
			            e1.printStackTrace();
			        }
				}
				else
				{
					try
					{
						getStartDateFile.createNewFile();
							try (BufferedWriter writer = new BufferedWriter(new FileWriter(getStartDateFile))) 
							{
					            writer.write(updatedStartDate);
					            writer.close();
					            System.out.println("Start date was updated to: " + updatedStartDate);
					        } 
							catch (IOException e1) 
							{
					        	JOptionPane.showMessageDialog(null, "An issue occurred writing new user start date content.");
					            e1.printStackTrace();
					        }
						 
					} 
					catch (IOException e1)
					{
						JOptionPane.showMessageDialog(null, "No Start Date file was found, and TFPD Manager was unable to create a new Start Date File.");
						e1.printStackTrace();
					}
				}
				
				//-------------------------------------------------------------end updating user start date file-------------------------------->
				//<------------------------------------------------------------Begin updating hours worked file--------------------------------->
				String updatedHoursWorked = hoursWorkedTextField.getText().trim();
				File getHoursWorkedFile = new File("HoursWorked/"+userSelected+".txt");
				if(getHoursWorkedFile.exists())
				{
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(getHoursWorkedFile))) 
					{
			            writer.write(updatedHoursWorked);
			            writer.close();
			            System.out.println("Hours worked was updated to: " + updatedHoursWorked);
			        } 
					catch (IOException e1) 
					{
			        	JOptionPane.showMessageDialog(null, "An issue occurred writing new user hours worked content.");
			            e1.printStackTrace();
			        }
				}
				else
				{
					try
					{
						getHoursWorkedFile.createNewFile();
							try (BufferedWriter writer = new BufferedWriter(new FileWriter(getHoursWorkedFile))) 
							{
					            writer.write(updatedHoursWorked);
					            writer.close();
					            System.out.println("Hours worked was updated to: " + updatedHoursWorked);
					        } 
							catch (IOException e1) 
							{
					        	JOptionPane.showMessageDialog(null, "An issue occurred writing new user hours worked content.");
					            e1.printStackTrace();
					        }
						 
					} 
					catch (IOException e1)
					{
						JOptionPane.showMessageDialog(null, "No hours worked file was found, and TFPD Manager was unable to create a new hours worked File.");
						e1.printStackTrace();
					}
				}
				
				//<------------------------------------------------------------end updating hours worked file----------------------------------->
				//<------------------------------------------------------------Begin changing of security permissions--------------------------->
				File userFilePath = new File("User/"+userSelected+".txt");
				File adminFilePath = new File("User/Permissions/Admin/"+userSelected+".txt");
				File superAdminFilePath = new File("User/Permissions/SuperAdmin/"+userSelected+".txt");
				//<---------------------------------------------------Change to PT Member------------------------------------------------------->		
				if(x==1)
				{
					if(userFilePath.exists())
					{
						userFilePath.delete();
					}
					if(adminFilePath.exists()) 
					{
						adminFilePath.delete();
					}
					if(superAdminFilePath.exists())
					{
						superAdminFilePath.delete();
					}
					//<-------------------------------------------------------remove name from FT roster---------------------------------------->
					String ftFilePath ="FTRoster/FTRoster.txt";
					
			        try (BufferedReader reader = new BufferedReader(new FileReader(ftFilePath));
			                BufferedWriter writer = new BufferedWriter(new FileWriter(ftFilePath))) {

			               String currentLine;
			               while ((currentLine = reader.readLine()) != null) {
			                   
			                   if (!currentLine.equals(userSelected)) {
			                       
			                       writer.write(currentLine);
			                       writer.newLine();
			                   }
			               }

			           } catch (IOException ex) {
			               JOptionPane.showMessageDialog(null, "An issue occurred updating the FT roster.");
			               System.out.println("Error removing selected item from FT roster.");
			               ex.printStackTrace();
			           }
			        //<------------------------------------------------------end remove name from FT roster--------------------------------------->

				}
				//<-----------------------------------------------End change to PT Member------------------------------------------------------->
				//<-----------------------------------------------Change to user---------------------------------------------------------------->
				if(x==2)
				{
					if(adminFilePath.exists()) 
					{
						adminFilePath.delete();
					}
					if(superAdminFilePath.exists())
					{
						superAdminFilePath.delete();
					}
				}
				//<-------------------------------------------------End change to user----------------------------------------------------------->
				//<-------------------------------------------------Change to Admin-------------------------------------------------------------->
				
				if(x==3)
				{
					
					//<---------------------------------------------Create Admin File----------------------------------------------------------->
					 if (adminFilePath.exists())
					 {
				            System.out.println("Admin File already exists.");
			         } 
					 else 
					 {
				           
				            try 
				            {
				                if (adminFilePath.createNewFile()) 
				                {
				                    System.out.println("File created successfully at: " + adminFilePath.getAbsolutePath());
				                } 
				                else 
				                {
				                    System.out.println("Unable to create the Admin file.");
				                }
				            } 
				            catch (IOException e1) 
				            {
				                System.out.println("An error occurred while creating the Admin File: " + e1.getMessage());
				            }
				        }
					
					//<---------------------------------------------END Create Admin File----------------------------------------------------------->
					
					

					if(superAdminFilePath.exists())
					{
						superAdminFilePath.delete();
					}
				}
				//<-------------------------------------------------End Change to Admin----------------------------------------------------------->
				//<-------------------------------------------------Change to SuperAdmin---------------------------------------------------------->
				
				if(x==4)
				{
					//<---------------------------------------------Create Admin File----------------------------------------------------------->
					 if (adminFilePath.exists())
					 {
				            System.out.println("Admin File already exists.");
			         } 
					 else 
					 {
				           
				            try 
				            {
				                if (adminFilePath.createNewFile()) 
				                {
				                    System.out.println("File created successfully at: " + adminFilePath.getAbsolutePath());
				                } 
				                else 
				                {
				                    System.out.println("Unable to create the Admin file.");
				                }
				            } 
				            catch (IOException e1) 
				            {
				                System.out.println("An error occurred while creating the Admin File: " + e1.getMessage());
				            }
				        }
					
					//<---------------------------------------------END Create Admin File----------------------------------------------------------->
					//<----------------------------------------------Create SuperAdmin File--------------------------------------------------------->
					
					 if (superAdminFilePath.exists())
					 {
				            System.out.println("Super Admin File already exists.");
			         } 
					 else 
					 {
				           
				            try 
				            {
				                if (superAdminFilePath.createNewFile()) 
				                {
				                    System.out.println("File created successfully at: " + superAdminFilePath.getAbsolutePath());
				                } 
				                else 
				                {
				                    System.out.println("Unable to create the SuperAdmin file.");
				                }
				            } 
				            catch (IOException e1) 
				            {
				                System.out.println("An error occurred while creating the SuperAdmin File: " + e1.getMessage());
				            }
				        }
					
					
					 
					//<----------------------------------------------End Create SuperAdminFile------------------------------------------------------>

				}
				//<------------------------------------------------End Change to SuperAdmin------------------------------------------------------>
				//<------------------------------------------------------------End changing of security permissions------------------------------>
				rosterManagementFrame.setEnabled(true);
				editGenrealRosterFrame.dispose();
			}
		});
		submitButton.setFont(new Font("Monospaced", Font.BOLD, 16));
		submitButton.setBounds(148, 407, 129, 50);
		panel.add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rosterManagementFrame.setEnabled(true);
				editGenrealRosterFrame.dispose();
				
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 16));
		cancelButton.setBounds(327, 407, 129, 50);
		panel.add(cancelButton);
		
		JLabel securityPrompt = new JLabel("Security Permission:");
		securityPrompt.setForeground(Color.WHITE);
		securityPrompt.setFont(new Font("Monospaced", Font.BOLD, 18));
		securityPrompt.setBounds(10, 320, 223, 61);
		panel.add(securityPrompt);
		
		userRadioButton = new JRadioButton("User");
		userRadioButton.setBackground(Color.DARK_GRAY);
		userRadioButton.setForeground(Color.WHITE);
		userRadioButton.setFont(new Font("Monospaced", Font.BOLD, 14));
		userRadioButton.setBounds(293, 341, 61, 23);
		userRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				x=2;
				System.out.println("Security Permission = "+x);
			}
		});
		panel.add(userRadioButton);
		
		adminRadioButton = new JRadioButton("Admin");
		adminRadioButton.setForeground(Color.WHITE);
		adminRadioButton.setFont(new Font("Monospaced", Font.BOLD, 14));
		adminRadioButton.setBackground(Color.DARK_GRAY);
		adminRadioButton.setBounds(356, 341, 69, 23);
		adminRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				x=3;
				System.out.println("Security Permission = "+x);
			}
		});
		panel.add(adminRadioButton);
		
		rdbtnSuperAdmin = new JRadioButton("Super Admin");
		rdbtnSuperAdmin.setForeground(Color.WHITE);
		rdbtnSuperAdmin.setFont(new Font("Monospaced", Font.BOLD, 14));
		rdbtnSuperAdmin.setBackground(Color.DARK_GRAY);
		rdbtnSuperAdmin.setBounds(427, 341, 138, 23);
		rdbtnSuperAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				x=4;
				System.out.println("Security Permission = "+x);
			}
		});
		
		panel.add(rdbtnSuperAdmin);
		
		partTimeRadioButton = new JRadioButton("PT");
		partTimeRadioButton.setForeground(Color.WHITE);
		partTimeRadioButton.setFont(new Font("Monospaced", Font.BOLD, 14));
		partTimeRadioButton.setBackground(Color.DARK_GRAY);
		partTimeRadioButton.setBounds(240, 341, 51, 23);
		partTimeRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "WARNING: Changing a user to part-time will delete all their login credentials.");
				x=1;
				System.out.println("Security Permission = "+x);
			}
		});
		panel.add(partTimeRadioButton);
		
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(partTimeRadioButton);
		buttons.add(rdbtnSuperAdmin);
		buttons.add(adminRadioButton);
		buttons.add(userRadioButton);
		
		
		//<-----------------------------------------------------SECURITY AUTO FILL------------------------------------------------------------>
		
		File userCheck = new File("User/"+userSelected+".txt");
		if(userCheck.exists())
		{
			File AdminCheck = new File("User/Permissions/Admin/"+userSelected+".txt");
			if(AdminCheck.exists())
			{
			
				File superAdminCheck= new File("User/Permissions/SuperAdmin/"+userSelected+".txt");
				if(superAdminCheck.exists())
				{
					rdbtnSuperAdmin.setSelected(true);
				}
				else
				{
					adminRadioButton.setSelected(true);
				}
			
			}
			else
			{
				userRadioButton.setSelected(true);
			}
		}
		else
		{
			partTimeRadioButton.setSelected(true);
			partTimeRadioButton.setEnabled(false);
			userRadioButton.setEnabled(false);
			adminRadioButton.setEnabled(false);
			rdbtnSuperAdmin.setEnabled(false);
		}
		
		//<---------------------------------------------------END SECURITY AUTO FILL------------------------------------------------------------>
		
		
		editGenrealRosterFrame.setVisible(true);
		
		
	}

}
