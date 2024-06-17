package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;

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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.awt.event.ActionEvent;

public class ResetFTMemberPassword 
{
	
	JFrame resetMemberPWframe;
	JPanel panel;
	
	ResetFTMemberPassword(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame userOptionFrame, JFrame hubFrame)
	{
		String ftFilePath ="FTRoster/FTRoster.txt";
		
		resetMemberPWframe = new JFrame("Select Member's Password to Reset");
		resetMemberPWframe.setSize(535,364);
		resetMemberPWframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		resetMemberPWframe.setLocationRelativeTo(null);
		resetMemberPWframe.setResizable(false);
		
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		resetMemberPWframe.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		resetMemberPWframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JComboBox fteComboBox = new JComboBox();
		fteComboBox.setBounds(76, 109, 363, 50);
		panel.add(fteComboBox);
		
		//<------------------------------------------------------This is where FTRoster.txt is read------------------------------------->
		 try (BufferedReader reader = new BufferedReader(new FileReader(ftFilePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) 
	            {
	                fteComboBox.addItem(line);
	            }
	        } 
		 catch (IOException e) 
		 	{
	        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the roster.");
	        	System.out.println("An error occurred retrieving the roster.");
	            e.printStackTrace();
	        }
		
		//<--------------------------------------------------------End reading of FTRoster.txt------------------------------------------->
		
		JLabel resetPWprompt = new JLabel("Select a user to reset their password:");
		resetPWprompt.setForeground(Color.WHITE);
		resetPWprompt.setFont(new Font("Monospaced", Font.BOLD, 17));
		resetPWprompt.setBounds(67, 39, 415, 59);
		panel.add(resetPWprompt);
		
		JButton resetPWButton = new JButton("Reset PW");
		resetPWButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 Object selectedItem = fteComboBox.getSelectedItem();
				 
				 
				 if (selectedItem != null) {
					 
					 String selectedItemString = fteComboBox.getSelectedItem().toString();

					 File userFileToReset = new File("User/"+selectedItemString+".txt");
					 if(userFileToReset.exists())
					 {
						 String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
						 int length = 5;
						 StringBuilder randomString = new StringBuilder();
						 Random random = new Random();
						 
						 for (int i = 0; i < length; i++) 
						 {
					          
					            int index = random.nextInt(characters.length());
					            randomString.append(characters.charAt(index));
				         }
						 
						 String newPW = randomString.toString();
						 System.out.println("New Random PW: " + newPW);
						 
						  try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFileToReset)))
						  {
				                writer.write(newPW);
				                System.out.println("Password updated successfully.");
				               
				                File getEmail = new File("User/Email/"+selectedItemString+".txt");
				                try (BufferedReader br = new BufferedReader(new FileReader(getEmail))) 
				                {
				                   
				                    StringBuilder content = new StringBuilder();
				                    String line;

				                    while ((line = br.readLine()) != null)
				                    {
				                        content.append(line).append("\n");
				                    }

				                    
				                    String emailContent = content.toString();

				                    
				                    System.out.println("Email ContentFound: " + emailContent);
				                    
					                //<----------------------------------------------------------------ADD EMAIL HERE--------------------------------->
					                // ---------------------------------------------  EMAIL Changed HERE------------------------------------->
	                                
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
	        						    String htmlContent = "<H2>Hello, " + selectedItemString + ", your password has been reset by:</H2>"
	        						            + "<p><b>"+ enteredUserName + "</b></p>"
	        						            + "<p>You have  been assigned a new temporary random password that is listed below:</p>"
	        						            + "<p><strong>" + newPW + "</strong></p>"
	        						            + "<p>Once you have successfully signed in, you can change your password by going to:</p>"
	        						            + "<p><b>User Options > Change Your Password > And then enter in your new Password.</b></p>"
	        						            + "<p>If you did not request this password change, please report this to your captain or chief for further investigation.</p>"
	        						            + "<p>Please note that only <b>YOU</b> will be able to receive this temporary password. This is to ensure the security and privacy of your account.</p>"
	        						      
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

	        						    message.setSubject("Notfication of TFPD 2.0 Password Reset");
	        						    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailContent));

	        						    Transport.send(message);
	        						    
	        						    System.out.println("The pasword email has been sent to the new user.");

	        							
	        							} 
	        							catch (MessagingException e1)
	        							{
	        									JOptionPane.showMessageDialog(null, "There was an issue generating a pasword reset email to send to the user.");
	        									e1.printStackTrace();
	        							} 
	                                 //<--------------------------------------------------------------END EMAIL UPDATE------------------------------------------------------>
	                                 
					                //<----------------------------------------------------------------End EMAIL---------------------------------------->

				                } 
				                catch (IOException e1) 
				                {
				                	System.out.println("Unable to find the user's email file.");
				                    e1.printStackTrace();
				                }
				                

				                JOptionPane.showMessageDialog(null, "The User's PW has been reset. The user specified has received a notfication email with their new PW.");
				                userOptionFrame.setEnabled(true);
				                resetMemberPWframe.dispose();
						  }
						  catch (IOException e1) 
						  {
				                e1.printStackTrace();
				                System.out.println("Error writing the new password to the file.");
				          }
	
					 }
					 else
					 {
						 JOptionPane.showMessageDialog(null, "An Error occurred retrieving the user's file.");
					 }
				 }
				 else
				 {
					 JOptionPane.showMessageDialog(null, "You must select a user.");
				 }
				
			}
		});
		resetPWButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		resetPWButton.setBounds(76, 239, 161, 50);
		panel.add(resetPWButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				userOptionFrame.setEnabled(true);
				resetMemberPWframe.dispose();
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		cancelButton.setBounds(278, 239, 161, 50);
		panel.add(cancelButton);
		
		
		resetMemberPWframe.setVisible(true);
	}
}
