package tfpdManagertwo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class AddNewFTE 
{
	
	JFrame addFTEFrame;
	JPanel panel;
	private JLabel firstNamePrompt;
	private JLabel lastNamePrompt;
	private JLabel emailPrompt;
	private JLabel securityPrompt;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField emailTextField;
	int x;
	String newUserName,randomPasswordString;
	
	AddNewFTE(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame userOptionFrame, JFrame hubFrame)
	{
		x = 0;
		System.out.println("Security Permission = "+x);
		
		addFTEFrame = new JFrame("Add New FTE");
		addFTEFrame.setSize(612,584);
		addFTEFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		addFTEFrame.setLocationRelativeTo(null);
		addFTEFrame.setResizable(false);
		
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		addFTEFrame.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		addFTEFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel addNewFTEPrompt = new JLabel("Add New FTE:");
		addNewFTEPrompt.setForeground(Color.WHITE);
		addNewFTEPrompt.setFont(new Font("Monospaced", Font.BOLD, 32));
		addNewFTEPrompt.setBounds(10, 11, 232, 59);
		panel.add(addNewFTEPrompt);
		
		firstNamePrompt = new JLabel("First Name:");
		firstNamePrompt.setForeground(Color.WHITE);
		firstNamePrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
		firstNamePrompt.setBounds(40, 117, 169, 59);
		panel.add(firstNamePrompt);
		
		lastNamePrompt = new JLabel("Last Name:");
		lastNamePrompt.setForeground(Color.WHITE);
		lastNamePrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
		lastNamePrompt.setBounds(40, 187, 169, 59);
		panel.add(lastNamePrompt);
		
		emailPrompt = new JLabel("Email:");
		emailPrompt.setForeground(Color.WHITE);
		emailPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
		emailPrompt.setBounds(40, 257, 169, 59);
		panel.add(emailPrompt);
		
		securityPrompt = new JLabel("Security Permission:");
		securityPrompt.setForeground(Color.WHITE);
		securityPrompt.setFont(new Font("Monospaced", Font.BOLD, 22));
		securityPrompt.setBounds(40, 327, 267, 59);
		panel.add(securityPrompt);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setBounds(276, 132, 259, 29);
		panel.add(firstNameTextField);
		firstNameTextField.setColumns(10);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(276, 207, 259, 29);
		panel.add(lastNameTextField);
		
		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setBounds(276, 281, 259, 29);
		panel.add(emailTextField);
		
		JRadioButton userRadioButton = new JRadioButton("User");
		userRadioButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		userRadioButton.setForeground(Color.WHITE);
		userRadioButton.setBackground(Color.DARK_GRAY);
		userRadioButton.setBounds(98, 393, 76, 23);
		userRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				x=0;
				System.out.println("Security Permission = "+x);
			}
		});
		panel.add(userRadioButton);
		
		JRadioButton adminRadioButton = new JRadioButton("Admin");
		adminRadioButton.setForeground(Color.WHITE);
		adminRadioButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		adminRadioButton.setBackground(Color.DARK_GRAY);
		adminRadioButton.setBounds(202, 393, 105, 23);
		adminRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				x=1;
				System.out.println("Security Permission = "+x);
			}
		});
		panel.add(adminRadioButton);
		
		JRadioButton rdbtnSuperAdmin = new JRadioButton("Super Admin");
		rdbtnSuperAdmin.setForeground(Color.WHITE);
		rdbtnSuperAdmin.setFont(new Font("Monospaced", Font.BOLD, 18));
		rdbtnSuperAdmin.setBackground(Color.DARK_GRAY);
		rdbtnSuperAdmin.setBounds(320, 396, 162, 23);
		rdbtnSuperAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				x=2;
				System.out.println("Security Permission = "+x);
			}
		});
		panel.add(rdbtnSuperAdmin);
		
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(rdbtnSuperAdmin);
		buttons.add(adminRadioButton);
		buttons.add(userRadioButton);
		
		if(superAdminStatus == true)
		{
			userRadioButton.setSelected(true);
		}
		else
		{
			userRadioButton.setSelected(true);
			userRadioButton.setEnabled(false);
			adminRadioButton.setEnabled(false);
			rdbtnSuperAdmin.setEnabled(false);
		}
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String newFirstName = firstNameTextField.getText().trim();
				String newLastName = lastNameTextField.getText().trim();
				String newEmail = emailTextField.getText().trim();
				
				if (!newFirstName.isEmpty()) 
				{//-----------------------------------------Detect first name and set First initial--------------------------------->
		            String capitalizedFirstLetter = newFirstName.substring(0, 1).toUpperCase();
		            System.out.println("Capitalized First Letter: " + capitalizedFirstLetter);
		            
		            if(!newLastName.isEmpty())
		            {
		            	 newUserName = capitalizedFirstLetter+newLastName;
		            	 System.out.println("New User name = " + newUserName);
		            	 
		            	 if(!newEmail.isEmpty())
		            	 {
		            		 //<----------------------------------------DETECT IF USER ALREADY EXISTS------------------------------>
		            		 File userFile = new File("User/"+newUserName+".txt");
		            		 if(userFile.exists())
            				 {
		            			 JOptionPane.showMessageDialog(null, "This Account already exists.");
		            		 }
		            		 else
		            		 {
		            			 try 
		            			 {
									userFile.createNewFile();
									if(userFile.exists())
									{//<----------------------------------------------GENERATE RANDOM PASSWORD------------------------>
										char startChar = 'A'; 
								        char endChar = 'Z';   
								        Random random = new Random();
								        
								        StringBuilder randomSet = new StringBuilder();
								        for (int i = 0; i < 5; i++) 
								        {
								            char randomChar = (char) (startChar + random.nextInt(endChar - startChar + 1));
								            randomSet.append(randomChar);
								        }
								        randomPasswordString = randomSet.toString();
								        System.out.println("Random generated Password: " + randomPasswordString);
								        //<----------------------------------------------------------WRITE RANDOM PASSWORD TO FILE----------------->
								        try (FileWriter passwordWriter = new FileWriter(userFile, false);
                               		         BufferedWriter bwPassword = new BufferedWriter(passwordWriter);
                               		         PrintWriter outPW = new PrintWriter(bwPassword)) 
								        {
                               		        outPW.println(randomPasswordString);
                               		        outPW.close();
                               		        //<--------------------------------------------CHECK FOR EXISTING PERMISSIONS-------------------------->
                               		        File adminCheck = new File("User/Permissions/Admin/"+newUserName+".txt");
                               		        File superAdminCheck = new File("User/Permissions/SuperAdmin/"+newUserName+".txt");
                               		        if(x==1)
                               		        {
                               		        	if(!adminCheck.exists())
                               		        	{
                               		        		adminCheck.createNewFile();
                               		        	}
                               		        	
                               		        }
                               		        else if(x==2)
                               		        {
                               		        	if(!adminCheck.exists())
                               		        	{
                               		        		adminCheck.createNewFile();
                               		        	}
                               		        	if(!superAdminCheck.exists())
                               		        	{
                               		        		
                               		        	}superAdminCheck.createNewFile();
                               		        }
                               		        //<--------------------------------------------------BEGIN UPDATING GENERAL ROSTER---------------------->
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
	            		                                     
	            		                                     System.out.println("User already found in genreal roster");
	            		                                     userExists = true;
	            		                                     
 //<----------------------------------------------BEGIN ADDING EMAIL FILE----------------->
	            			                                 
	            			                                 File emailCheckFile = new File("User/Email/"+newUserName+".txt");
	            			                                 
	            			                                 if(emailCheckFile.exists())
	            			                 				{
	            			                 					try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(emailCheckFile))) 
	            			                 					{
	            			                 			            writer1.write(newEmail);
	            			                 			            writer1.close();
	            			                 			            System.out.println("Email was updated to: " + newEmail);
	            			                 			        } 
	            			                 					catch (IOException e1) 
	            			                 					{
	            			                 			        	JOptionPane.showMessageDialog(null, "An issue occurred writing new user email content.");
	            			                 			            e1.printStackTrace();
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
	            			                                		        out3.close();

	            			                                		        

	            			                                		    } catch (IOException ex) {
	            			                                		        JOptionPane.showMessageDialog(null, "Error writing new Email to the file.");
	            			                                		        ex.printStackTrace();
	            			                                		    }

	            			                                		} catch (IOException e1) {
	            			                                		    JOptionPane.showMessageDialog(null, "Error creating the email file.");
	            			                                		    e1.printStackTrace();
	            			                                		}
	            			                                 }
	            			                                	//<----------------------------------------------END CREATING NEW EMAIL FILE----------> 
	            		                                     
	            		                                     break; 
	            		                                 }
	            		                             }
	            		                             
	            		                             
	            		                         }
	            		                         catch (IOException ex)
	            		                         {
	            		                        	 JOptionPane.showMessageDialog(null, "Error detecting if Account already exists in general roster.");
	            		                        	 System.out.println("Error detecting if general roster account exists");
	            		                             ex.printStackTrace();
	            		                         }
	            		                         //<----------------------------------------------------------Here updates generalroster if not already there------------>
	            		                         if (userExists==false)
	            		                         {
	            		                             try (FileWriter writer = new FileWriter(generalRosterCheck, true);
	            			                                  BufferedWriter bw = new BufferedWriter(writer);
	            			                                  PrintWriter out = new PrintWriter(bw)) 
	            			                             {

	            			                                 out.println(newUserName);
	            			                                 System.out.println("User added to general Rotser");
	            			                                 //<----------------------------------------------BEGIN ADDING EMAIL FILE----------------->
	            			                                 
	            			                                 File emailCheckFile = new File("User/Email/"+newUserName+".txt");
	            			                                 
	            			                                 if(emailCheckFile.exists())
	            			                 				{
	            			                 					try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(emailCheckFile))) 
	            			                 					{
	            			                 			            writer1.write(newEmail);
	            			                 			            writer1.close();
	            			                 			            System.out.println("Email was updated to: " + newEmail);
	            			                 			        } 
	            			                 					catch (IOException e1) 
	            			                 					{
	            			                 			        	JOptionPane.showMessageDialog(null, "An issue occurred writing new user email content.");
	            			                 			            e1.printStackTrace();
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
	            			                                		        out3.close();

	            			                                		        

	            			                                		    } catch (IOException ex) {
	            			                                		        JOptionPane.showMessageDialog(null, "Error writing new Email to the file.");
	            			                                		        ex.printStackTrace();
	            			                                		    }

	            			                                		} catch (IOException e1) {
	            			                                		    JOptionPane.showMessageDialog(null, "Error creating the email file.");
	            			                                		    e1.printStackTrace();
	            			                                		}
	            			                                 }
	            			                                	//<----------------------------------------------END CREATING NEW EMAIL FILE----------> 
	            			                                 
	            			                                 

	            			                             }

	            		                         }
                               		     	}
                               		     	else
                               		     	{
                               		     		JOptionPane.showMessageDialog(null, "Issue locating genral roster.");
                               		     	}
                               		        
                               		        
                               		     	
                               		     	//<--------------------------------------------------END UPDATING GENERAL ROSTER------------------------>
                               		     	
                               		     	//<--------------------------------------------------BEGIN UPDATING FT ROSTER--------------------------->
                               		     	File ftRoster = new File("FTRoster/FTRoster.txt");
                               		     	if(ftRoster.exists()) 
                               		     	{
                               		     	 boolean userExists = false;
	            		                         try (BufferedReader br = new BufferedReader(new FileReader(ftRoster))) 
	            		                         {
	            		                             String line;
	            		                             
	            		                             while ((line = br.readLine()) != null) 
	            		                             {
	            		                                 if (line.trim().equalsIgnoreCase(newUserName)) 
	            		                                 {
	            		                                     
	            		                                     System.out.println("User already found in FT roster.");
	            		                                     userExists = true;
	            		                                     break; 
	            		                                 }
	            		                             }
	            		                             
	            		                             
	            		                         }
	            		                         catch (IOException ex)
	            		                         {
	            		                        	 JOptionPane.showMessageDialog(null, "Error detecting if Account already exists in FT roster.");
	            		                        	 System.out.println("Error detecting if FT roster account exists");
	            		                             ex.printStackTrace();
	            		                         }
	            		                         if (userExists==false)
	            		                         {
	            		                             try (FileWriter writer = new FileWriter(ftRoster, true);
	            			                                  BufferedWriter bw = new BufferedWriter(writer);
	            			                                  PrintWriter out = new PrintWriter(bw)) 
	            			                             {

	            			                                 out.println(newUserName);
	            			                                 System.out.println("User added to FT Rotser");
	            			                                 JOptionPane.showMessageDialog(null, "User added to General roster, FT Roster and account created.");
	            			                                 // --------------------------------------------- WELCOME EMAIL HERE------------------------------------->
	            			                                 
	            			                                Properties properties = new Properties();
	            			         						properties.put("mail.smtp.auth", true);
	            			         						properties.put("mail.smtp.host", "smtp.gmail.com");
	            			         						properties.put("mail.smtp.port", 587);
	            			         						properties.put("mail.smtp.starttls.enable", true);
	            			         						properties.put("mail.transport.protocol" , "smtp");
	            			         						

	            			        						Session session = Session.getInstance(properties, new Authenticator() 
	            			        						{
	            			        							
	            			        							protected PasswordAuthentication getPasswordAuthentication() 
	            			        							{
	            			        								return new PasswordAuthentication("tfpdmailerbot@gmail.com", "fnltdivnqdvhlanh");
	            			        							}
	            			        							
	            			        						});
	            			        						
	            			        						try {
	            			        						    Message message = new MimeMessage(session);

	            			        						    
	            			        						    MimeMultipart multipart = new MimeMultipart("related");

	            			        						    // Add HTML text part
	            			        						    MimeBodyPart textPart = new MimeBodyPart();
	            			        						    String htmlContent = "<H2>Hello, " + newUserName + " and welcome to TFPD Manager 2.0!</H2>"
	            			        						            + "<p>" + enteredUserName + " has created an account for you; however, there are a few things to take care of before beginning your new TFPD Manager experience.</p>"
	            			        						            + "<p>First, your user name to log into TFPD Manager 2.0 is: </p>"
	            			        						            + "<p><b>"+ newUserName + "</b></p>"
	            			        						            + "<p>You have also been assigned a temporary random password that is listed below:</p>"
	            			        						            + "<p><strong>" + randomPasswordString + "</strong></p>"
	            			        						            + "<p>Once you have successfully signed in, you can change your password by going to:</p>"
	            			        						            + "<p><b>User Options > Change Your Password > And then enter in your new Password.</b></p>"
	            			        						            + "<p>If you are having any difficulties with this process, please reach out to your officer who will be able to assist with resetting your password.</p>"
	            			        						            + "<p>Please note that only <b>YOU</b> will be able to receive this temporary password. This is to ensure the security and privacy of your account.</p>"
	            			        						            + "<H3>Thanks and welcome to TFPD Manager 2.0!</H3>"
	            			        						            + "<p><img src=\"cid:image\"></p>"; 

	            			        						    textPart.setContent(htmlContent, "text/html");
	            			        						    multipart.addBodyPart(textPart);

	            			        						    // Add image part
	            			        						    MimeBodyPart imagePart = new MimeBodyPart();
	            			        						    DataSource fds = new FileDataSource("TroyIcon.png"); 
	            			        						    imagePart.setDataHandler(new DataHandler(fds));
	            			        						    imagePart.setHeader("Content-ID", "<image>");

	            			        						    multipart.addBodyPart(imagePart);

	            			        						    // Set the content of the message to the MimeMultipart
	            			        						    message.setContent(multipart);

	            			        						    message.setSubject("Welcome to TFPD Manager 2.0");
	            			        						    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(newEmail));

	            			        						    Transport.send(message);
	            			        						    
	            			        						    System.out.println("The welcome email has been sent to the new user.");

	            			        							
	            			        							} 
	            			        							catch (MessagingException e1)
	            			        							{
	            			        									JOptionPane.showMessageDialog(null, "There was an issue generating a welcome email to send to the user.");
	            			        									e1.printStackTrace();
	            			        							} 
	            			                                 //<--------------------------------------------------------------END EMAIL UPDATE------------------------------------------------------>
	            			                                 
	            			                                 userOptionFrame.setEnabled(true);
	            			                                 addFTEFrame.dispose();
	            			                             }
	            		                         }
	            		                         
                               		     	}
                               		     	else
                               		     	{
                               		     	JOptionPane.showMessageDialog(null, "Error locating FT Roster.");
                               		     	}
     	
                               		     	//End UPDATING FT ROSTER----------------------------------------------------------------------->
                               		    }
								        catch (IOException ex) 
								        {
                               		        JOptionPane.showMessageDialog(null, "Error writing temporary password to the file.");
                               		        ex.printStackTrace();
                               		    }
								        
									}
									else
									{
										JOptionPane.showMessageDialog(null, "Issue finding new user File.");
									}
								 } 
		            			 catch (IOException e1)
		            			 {
		            				JOptionPane.showMessageDialog(null, "There was an issue creating the user file.");
									e1.printStackTrace();
								 } 
		            			 
		            			 
		            		 }
		            	 }
		            	 else
		            	 {
		            		 JOptionPane.showMessageDialog(null, "You Must Enter A User Email.");
		            	 }
		            }
		            else
		            {
		            	JOptionPane.showMessageDialog(null, "You Must Enter A Last Name.");
		            }
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You Must Enter A First Name.");
				}
				
			}
		});
		submitButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		submitButton.setBounds(120, 466, 162, 51);
		panel.add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				userOptionFrame.setEnabled(true);
				addFTEFrame.dispose();
				
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		cancelButton.setBounds(320, 466, 162, 51);
		panel.add(cancelButton);
		
		
		
		
		
		
		
		addFTEFrame.setVisible(true);
	
	}
}
