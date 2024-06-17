package tfpdManagertwo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SickTimeForm implements ActionListener, PropertyChangeListener{
	
	JFrame Datepicker;
	JFrame sickFrame;
	JPanel panel;
	ButtonGroup group;
	JButton datebutton;
	JFormattedTextField datefield;
	JComboBox comboBox;
	JTextArea notestext;
	int x;
	String selectedUser,hoursString;
	JComboBox hoursComboBox;
	
	SickTimeForm(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail)
	{
		sickFrame = new JFrame("Sick Time Form");
		sickFrame.setSize(762,641);
		sickFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		sickFrame.setLocationRelativeTo(null);
		sickFrame.setResizable(false);
		
		String [] hours = {"", "1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		sickFrame.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		sickFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
        JLabel iconLabel = new JLabel("New label");
        iconLabel.setBounds(584, 11, 154, 153);
        panel.add(iconLabel);
        ImageIcon imageIcon = new ImageIcon("TroyIcon.png");
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(iconLabel.getWidth(), iconLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        iconLabel.setIcon(resizedIcon);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new FormsHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
        		sickFrame.dispose();
        	}
        });
        
        cancelButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setBounds(525, 534, 213, 59);
        panel.add(cancelButton);
        
        JLabel headLabelSickTime = new JLabel("Sick Time Request Form");
        headLabelSickTime.setForeground(Color.WHITE);
        headLabelSickTime.setFont(new Font("Monospaced", Font.BOLD, 30));
        headLabelSickTime.setBounds(10, 0, 428, 59);
        panel.add(headLabelSickTime);
        
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	      //<-------------------------------------------CHECK EMAIL LIST VALIDITY------------------------------->
        		String checkPath = "EmailLists/stemail.txt";

        	        try (BufferedReader reader = new BufferedReader(new FileReader(checkPath))) {
        	            String line = reader.readLine();
        	            if (line == null)
        	            {
        	            	JOptionPane.showMessageDialog(null, "There is currently nobody listed to receive your request.");
        	                System.out.println("No Recipients found in the email file.");
        	                return;
        	            }
        	        } 
        	        catch (IOException e1) 
        	        {
        	            
        	            System.err.println("Error reading the file: " + e1.getMessage());
        	        }
        		
        	        //<-------------------------------------------END CHECK EMAIL LIST VALIDITY------------------------------->
        		
        		
        		//<----------------------------------------------BEGIN COLLECTING FORM DATA------------------------------------------->
        		Object selection = comboBox.getSelectedItem();
        		
					if(selection == null)
					{
						JOptionPane.showMessageDialog(null, "You must select a user to fill out on behalf of.");
						return;
					}
					else
					{
						selectedUser =  selection.toString();
					}
					
					Object hoursSelection = hoursComboBox.getSelectedItem();
					if(hoursSelection == null)
					{
						JOptionPane.showMessageDialog(null, "You must select the amount of hours to be used.");
						return;
					}
					else
					{
						 hoursString = hoursSelection.toString();
					}
					
					String notesString = notestext.getText().trim();
					
	           		String dateString = datefield.getText().trim();
	           		if (dateString.equals("")) 
	           		{
	           			JOptionPane.showMessageDialog(null, "You must enter a date.");
	           			return;
	           		}
        		//<---------------------------------------------END COLLECTING FORM DATA----------------------------------------------->
	          		//<---------------------------------------------BEGIN EMAILING PROCESS------------------------------------------------->
					String locateUserNames = "EmailLists/stemail.txt"; 
					
					  try (BufferedReader usersToEmail = new BufferedReader(new FileReader(locateUserNames))) {
				            String users;
				            while ((users = usersToEmail.readLine()) != null) 
				            {
				               System.out.println("User being emailed: " + users);
					           File userToBeEmailedFile = new File("User/Email/"+users+".txt");
					           System.out.println(userToBeEmailedFile);
					           
					           try (BufferedReader fileReader = new BufferedReader(new FileReader(userToBeEmailedFile))) 
					           {	LocalDate currentDate = LocalDate.now();
				                    String finalEmail;
				                    while ((finalEmail = fileReader.readLine()) != null) 
				                    {
				                        System.out.println("Content of " + userToBeEmailedFile + ": " + finalEmail);
				                        
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
										    String htmlContent = "<H2>"+ enteredUserName+ " has sent an Sick Time request from TFPD Manager.</H2>"
										            
										    		+ "<p>"+selectedUser+" has used the following hours of Sick Time:</p>"
										    		+ "<p>" +hoursString  +" hours on the date of " + dateString +"</p>"
										    		+ "<p>The following was included as a given reason / note: "+ notesString
										            + "<H3>Please contact the sender with any questions you may have @ " + userEmail + ".</H3>"
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

										    message.setSubject("Sick Time Form Request Submitted By: "+ enteredUserName);
										    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(finalEmail));

										    Transport.send(message);
										    
										    System.out.println("The email has been sent to the user(s).");

											
											} 
											catch (MessagingException e1)
											{
													JOptionPane.showMessageDialog(null, "No Email was generated due to an error. The request has not been received.");
													e1.printStackTrace();
													
											} 
										
										
										
										
										
				                    }
				                    
				                    
				                    
				                    
				                }
					           catch (IOException e1) 
					           {
					        	   JOptionPane.showMessageDialog(null, "Issue finding " +userToBeEmailedFile+" to receive email.");
				                    e1.printStackTrace();
				               }
				            }
				            

				        } 
					    catch (IOException e1) 
					    {
					    	JOptionPane.showMessageDialog(null, "Issue finding email roster.");
				            e1.printStackTrace();
				        }
	           		
	           		
	           		
	           		//<---------------------------------------------END EMAILING PROCESS--------------------------------------------------->
		       			//<-------------------------------Update BB file--------------------------------------->
				        String filePath = "bulletinboard/bb.txt";
				        String content = enteredUserName + " submitted a sick time form for "+ selectedUser+ " for the date of " + dateString+"\n";
				        
				        try {
				            
				            List<String> lines = Files.readAllLines(Paths.get(filePath));
				            
				            // Prepend the new content to the existing lines
				            lines.add(0, content);
				            
				            // Write the combined content back to the file
				            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
				                writer.write(lines.stream().collect(Collectors.joining(System.lineSeparator())));
				                System.out.println("Content prepended to the file.");
				            }
				            
				        } catch (IOException e1) {
				            e1.printStackTrace();
				        }
						
		       		//<-------------------------------End Update BB file--------------------------------------->
		       			JOptionPane.showMessageDialog(null, "Your OT Request Has Been Submitted.");
		       			new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
		       			sickFrame.dispose();
					  
        	}
        });
        submitButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        submitButton.setBackground(Color.LIGHT_GRAY);
        submitButton.setBounds(525, 464, 213, 59);
        panel.add(submitButton);
        
        JLabel forWhoPrompt = new JLabel("Filling out for:");
        forWhoPrompt.setForeground(Color.WHITE);
        forWhoPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
        forWhoPrompt.setBounds(79, 127, 240, 37);
        panel.add(forWhoPrompt);
        
	    comboBox = new JComboBox();
		comboBox.setBounds(79, 175, 262, 42);
		comboBox.setVisible(true);
		panel.add(comboBox);
		
	       String filePath = "FTRoster/FTRoster.txt";
	       
	       try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
	       {
		            String line;
		            while ((line = reader.readLine()) != null) 
		            {
		            	comboBox.addItem(line);
		            	
		            }
		            System.out.println("FT Roster for OT email list loaded successfully");
	        }
	       catch (IOException e1) 
	       {
		        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the FT Roster for OT notification.");
		        	System.out.println("An Error occurred retrieving the FT Roster for OT Notification List.");
		            e1.printStackTrace();
	       }
		
 		 //<----------------------------------------------------Begin Date Picker--------------------------------------------->
 		 
		datebutton = new JButton("Calendar");
		datebutton.setBounds(241, 292, 100, 23);
		datebutton.addActionListener(this);
		panel.add(datebutton);
		
		Datepicker = new Datepicker();
		Datepicker.setUndecorated(true);
		Datepicker.addPropertyChangeListener(this);
		
		datefield = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.SHORT));
		datefield.setBounds(79, 291, 150, 25);
		panel.add(datefield);
		
		JLabel lblSelectDate = new JLabel("For Date Of:");
		lblSelectDate.setForeground(Color.WHITE);
		lblSelectDate.setFont(new Font("Monospaced", Font.BOLD, 20));
		lblSelectDate.setBounds(79, 240, 168, 37);
		panel.add(lblSelectDate);
		
		JLabel hoursUsedPrompt = new JLabel("Hours Used:");
		hoursUsedPrompt.setForeground(Color.WHITE);
		hoursUsedPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		hoursUsedPrompt.setBounds(79, 342, 168, 37);
		panel.add(hoursUsedPrompt);
		
		hoursComboBox = new JComboBox(hours);
		hoursComboBox.setBounds(79, 397, 168, 37);
		panel.add(hoursComboBox);
		
		//<----------------------------------------------------END DATE PICKER-------------------------------------------------->
		
		
		
		notestext = new JTextArea ("N/A");
		notestext.setBounds(79, 492, 359, 87);
		notestext.setWrapStyleWord(true);
		notestext.setLineWrap(true);
		panel.add(notestext);
		
		JLabel notesPrompt = new JLabel("Notes:");
		notesPrompt.setForeground(Color.WHITE);
		notesPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		notesPrompt.setBounds(79, 444, 168, 37);
		panel.add(notesPrompt);
		
		JButton recipientButton = new JButton("Email Roster");
		recipientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SickTimeRecipients( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, sickFrame);
				sickFrame.setEnabled(false);
				
			}
		});
		recipientButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		recipientButton.setBackground(Color.LIGHT_GRAY);
		recipientButton.setBounds(525, 292, 213, 59);
		panel.add(recipientButton);
  		 
  		 if(superAdminStatus==true)
  		 {
  			 recipientButton.setVisible(true);
  		 }
  		 else
  		 {
  			 recipientButton.setVisible(false);
  		 }
        
        
        sickFrame.setVisible(true);
        
        
        
        
	}
	
	public void actionPerformed(ActionEvent e) {
		
		Datepicker.setLocation(datefield.getLocationOnScreen().x,
				(datefield.getLocationOnScreen().y + datefield.getHeight()));
		Date selectedDate = (Date) datefield.getValue();
		datefield.setValue(selectedDate);
		Datepicker.setVisible(true);
			
		}

	public void propertyChange(PropertyChangeEvent event) {
		
		if(event.getPropertyName().equals("selectedDate"))
		{
			java.util.Calendar cal = (java.util.Calendar)event.getNewValue();
			Date selDate = cal.getTime();
			datefield.setValue(selDate);
		}
		
	}
}
