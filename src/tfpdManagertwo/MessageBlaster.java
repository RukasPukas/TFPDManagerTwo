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
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class MessageBlaster {
	
	JFrame messageBlasterFrame;
	JPanel panel;
	
	
	
	
	MessageBlaster(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame hubFrame)
	{


		messageBlasterFrame = new JFrame("Broadcast Message");
		messageBlasterFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		messageBlasterFrame.setSize(890,871);
		messageBlasterFrame.setIconImage(img);
		messageBlasterFrame.setLocationRelativeTo(null);
		panel=new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);
		messageBlasterFrame.setResizable(false);

       
       
       
       messageBlasterFrame.getContentPane().add(panel);
       
       JTextArea messageTextArea = new JTextArea("<ENTER MESSAGE HERE>");
       messageTextArea.setForeground(Color.WHITE);
       messageTextArea.setBackground(Color.BLACK);
       messageTextArea.setFont(new Font("Monospaced", Font.BOLD, 13));
       messageTextArea.setBounds(50, 94, 362, 626);
       messageTextArea.setLineWrap(true);
       messageTextArea.setWrapStyleWord(true);
       panel.add(messageTextArea);
       
       JLabel lblNewLabel = new JLabel("Post to bulletin board and email recipients (optional).");
       lblNewLabel.setForeground(Color.WHITE);
       lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
       lblNewLabel.setBounds(38, 32, 807, 51);
       panel.add(lblNewLabel);
       
       JList emailListBox = new JList();
       emailListBox.setForeground(Color.WHITE);
       emailListBox.setBackground(Color.BLACK);
       emailListBox.setFont(new Font("Monospaced", Font.PLAIN, 12));
       emailListBox.setBounds(488, 319, 318, 312);
       panel.add(emailListBox);
       
       DefaultListModel<String> emailListModel = new DefaultListModel<>();
       emailListBox.setModel(emailListModel); 
       
       JComboBox comboBox = new JComboBox();
       comboBox.setBackground(Color.WHITE);
       comboBox.setBounds(488, 139, 318, 51);
       panel.add(comboBox);
       
		//<------------------------------------------------------This is where generalroster.txt is read------------------------------------->
       String filePath = "Generalroster/generalroster.txt";
       
       try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
       		{
	            String line;
	            while ((line = reader.readLine()) != null) 
	            {
	            	comboBox.addItem(line);
	            	
	            }
	            System.out.println("General Roster loaded successfully");
	        } 
       catch (IOException e)
       		{
	        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the roster.");
	        	System.out.println("An error occurred retrieving the roster.");
	            e.printStackTrace();
	        }
		
		//<--------------------------------------------------------End reading of generalroster.txt------------------------------------------->
       
       JButton addToEmailButton = new JButton("Add Selected");
       addToEmailButton.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		
			 Object selectedItem = comboBox.getSelectedItem();
			 
			 
				if(selectedItem == null)
				{
					JOptionPane.showMessageDialog(null, "You must select a user to add to the email list.");
				}
				
				else 
				{
					String selectedUserName = comboBox.getSelectedItem().toString();
				    if (emailListModel.contains(selectedUserName)) 
				    {
				        JOptionPane.showMessageDialog(null, "The selected user is already in the email list.");
				    } 
				    else 
				    {
				        System.out.println("The selected user added to the email list:  " + selectedUserName);
				        emailListModel.addElement(selectedUserName);
				    }
				
				
				}
       		
       	}
       });
       addToEmailButton.setBackground(Color.LIGHT_GRAY);
       addToEmailButton.setFont(new Font("Monospaced", Font.BOLD, 11));
       addToEmailButton.setBounds(561, 218, 153, 34);
       panel.add(addToEmailButton);
       
       JButton submitButton = new JButton("Submit");
       submitButton.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		
       		String finishedMessage = messageTextArea.getText();
       		if(finishedMessage.equals("") || finishedMessage.equals("<ENTER MESSAGE HERE>"))
       		{
       			JOptionPane.showMessageDialog(null, "You must enter a message.");
       		}
       		else
       		{
       			
       			//<-------------------------------Update BB file--------------------------------------->
       			File bbfile = new File("bulletinboard/bb.txt");
       			
       			try {
       			    
       			    StringBuilder existingContent = new StringBuilder();
       			    if (bbfile.exists()) {
       			        try (BufferedReader reader = new BufferedReader(new FileReader(bbfile))) {
       			            String line;
       			            while ((line = reader.readLine()) != null) {
       			                existingContent.append(line).append("\n");
       			            }
       			        }
       			    }

       			  
       			    String newContent = "\n" + enteredUserName + " posted:" + finishedMessage+"\n";
       			    String combinedContent = newContent + existingContent.toString() +"\n";

       			   
       			    try (BufferedWriter writer = new BufferedWriter(new FileWriter(bbfile))) {
       			        writer.write(combinedContent);
       			        System.out.println("Bulletin board file updated successfully");
       			    }
       			} catch (IOException ex) {
       			    JOptionPane.showMessageDialog(null, "Issue updating the bulletin board.");
       			    ex.printStackTrace();
       			}
       		//<-------------------------------End Update BB file--------------------------------------->
   			
       		//<-------------------------------Begin emailing added users from email roster-------------->
       			
       			String[] emailListItems = new String[emailListModel.size()];
       			emailListModel.copyInto(emailListItems);
       			
       			for (String userName : emailListItems)
       			{
       				String emailFileName = "User/Email/"+userName +".txt";
       				String email = null;
       				try(BufferedReader emailReader = new BufferedReader(new FileReader(emailFileName)))
       				{
       					StringBuilder emailBuilder = new StringBuilder();
       					String line;
       					
       					while((line = emailReader.readLine()) != null)
       					{
       						emailBuilder.append(line);
       					}
   						email = emailBuilder.toString();
   						System.out.println(email);
   						
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
						    String htmlContent = "<H2>Hello " + userName+ ", "+ enteredUserName+ " has sent you a message from TFPD Manager.</H2>"
						            + "<p>The message reads as follows: </p>"
						    		+ "<p>"+finishedMessage+"</p>"
						            + "<H3>Please contact the sender with any questions you may have.</H3>"
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

						    message.setSubject("Message from "+ enteredUserName);
						    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

						    Transport.send(message);
						    
						    System.out.println("The email has been sent to the user(s).");

							
							} 
							catch (MessagingException e1)
							{
									JOptionPane.showMessageDialog(null, "There was an issue generating a notification email to send to the user.");
									e1.printStackTrace();
							} 
       					
       				}
       				catch(IOException ex)
       				{
       					JOptionPane.showMessageDialog(null,  "An error occurred while reading the email file: " + emailFileName);
       					System.out.println("An error occurred while reading the email file: " + emailFileName);
       				}
       				
       			}
       			JOptionPane.showMessageDialog(null, "The Bulletin board has been updated with your message, and the specified accounts have been emailed.");
       			new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
       			messageBlasterFrame.dispose();
       			
       			
   			//<-------------------------------End emailing added user from email roster----------------->
       			
       			
       		}
       		
       	}
       });
       submitButton.setBackground(Color.LIGHT_GRAY);
       submitButton.setFont(new Font("Monospaced", Font.BOLD, 20));
       submitButton.setBounds(488, 755, 175, 57);
       panel.add(submitButton);
       
       JButton cancelButton = new JButton("Cancel");
       cancelButton.setBackground(Color.LIGHT_GRAY);
       cancelButton.setForeground(Color.BLACK);
       cancelButton.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		
       		new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
       		messageBlasterFrame.dispose();
       	}
       });
       cancelButton.setFont(new Font("Monospaced", Font.BOLD, 20));
       cancelButton.setBounds(237, 755, 175, 57);
       panel.add(cancelButton);
       
       JButton removeFromListButton = new JButton("Remove From List");
       removeFromListButton.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		
       		int selectedIndex = emailListBox.getSelectedIndex();
       		
       		if(selectedIndex!= -1)
       		{
       			emailListModel.remove(selectedIndex);
       			System.out.println("User removed from Email List");
       		}
       		else
       		{
       			JOptionPane.showMessageDialog(null,"You must select a user to remove.");
       		}
       	}
       });
       removeFromListButton.setFont(new Font("Monospaced", Font.BOLD, 11));
       removeFromListButton.setBackground(Color.LIGHT_GRAY);
       removeFromListButton.setBounds(561, 642, 153, 34);
       panel.add(removeFromListButton);
       
       JButton btnAddAll = new JButton("Add All");
       btnAddAll.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
       		
       		for(int i = 0; i<comboBox.getItemCount(); i++)
       		{
       			String item = comboBox.getItemAt(i).toString();
       			emailListModel.addElement(item);
       			btnAddAll.setEnabled(false);
       		}
       	}
       });
       btnAddAll.setFont(new Font("Monospaced", Font.BOLD, 11));
       btnAddAll.setBackground(Color.LIGHT_GRAY);
       btnAddAll.setBounds(561, 263, 153, 34);
       panel.add(btnAddAll);
       
       JLabel lblNewLabel_1 = new JLabel("Select user to add to Email Roster");
       lblNewLabel_1.setForeground(Color.WHITE);
       lblNewLabel_1.setFont(new Font("Monospaced", Font.BOLD, 15));
       lblNewLabel_1.setBounds(488, 94, 318, 34);
       panel.add(lblNewLabel_1);
       messageBlasterFrame.setVisible(true);
		
		
		
		
	}
}
