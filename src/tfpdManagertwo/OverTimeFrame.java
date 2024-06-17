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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class OverTimeFrame implements ActionListener, PropertyChangeListener{
	
	
	JFrame Datepicker;
	JFrame otFrame;
	JPanel panel;
	ButtonGroup group;
	JButton datebutton;
	JFormattedTextField datefield;
	private JTextField startTimeTextField;
	private JTextField endTimeTextField;
	private JTextField notesTextField;
	JComboBox comboBox;
	int x;
	String selectedUser;
	
	
	OverTimeFrame(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail)
	{
		

		otFrame = new JFrame("OT Form");
		otFrame.setSize(762,641);
		otFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		otFrame.setLocationRelativeTo(null);
		otFrame.setResizable(false);
		
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		otFrame.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		otFrame.getContentPane().add(panel);
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
           		otFrame.dispose();
           	}
           });
           cancelButton.setFont(new Font("Monospaced", Font.BOLD, 18));
           cancelButton.setBackground(Color.LIGHT_GRAY);
           cancelButton.setBounds(525, 534, 213, 59);
           panel.add(cancelButton);
           
           JLabel lblOtRequestForm = new JLabel("OT Request Form");
           lblOtRequestForm.setForeground(Color.WHITE);
           lblOtRequestForm.setFont(new Font("Monospaced", Font.BOLD, 30));
           lblOtRequestForm.setBounds(10, 0, 307, 59);
           panel.add(lblOtRequestForm);
           
           JButton submitButton = new JButton("Submit");
           submitButton.addActionListener(new ActionListener() {
           	public void actionPerformed(ActionEvent e) {
           		
      	      //<-------------------------------------------CHECK EMAIL LIST VALIDITY------------------------------->
      		String checkPath = "EmailLists/otemail.txt";

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
           		if(x==0)
           		{
           			 selectedUser = enteredUserName;
           		}
           		else if (x==1)
           		{
           		
           			Object selectionObject = comboBox.getSelectedItem();
           					if(selectionObject == null)
           					{
           						JOptionPane.showMessageDialog(null, "If filling out on behalf of another, you must select a user.");
           						return;
           					}
           					else
           					{
           						 selectedUser =  selectionObject.toString();
           					}
           		}
           		
           		String startTimeString = startTimeTextField.getText().trim();
           		
           		if (startTimeString.equals("")) 
           		{
           			JOptionPane.showMessageDialog(null, "You must enter a start time.");
           			return;
           			
           		}
           		
           		String endTimeString = endTimeTextField.getText().trim();
           		if (endTimeString.equals("")) 
           		{
           			JOptionPane.showMessageDialog(null, "You must enter an end time.");
           			return;
           		}
           		
           		String notesString = notesTextField.getText().trim();
           		if(notesString.equals(""))
           		{
           			notesString = "N/A";
           		}
           		
           		String dateString = datefield.getText().trim();
           		if (dateString.equals("")) 
           		{
           			JOptionPane.showMessageDialog(null, "You must enter a date.");
           			return;
           		}
           			
       			//<---------------------------------------------END COLLECTING FORM DATA----------------------------------------------->
           		
           		//<---------------------------------------------BEGIN EMAILING PROCESS------------------------------------------------->
				String locateUserNames = "EmailLists/otemail.txt"; 
				
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
									    String htmlContent = "<H2>"+ enteredUserName+ " has sent an OT request from TFPD Manager.</H2>"
									            
									    		+ "<p>"+selectedUser+" has worked the following hours of OT:</p>"
									    		+ "<p>" +startTimeString + " - " + endTimeString +" on the date of " + dateString +"</p>"
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

									    message.setSubject("OT Form Request Submitted By: "+ enteredUserName);
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
				  File bbfile = new File("bulletinboard/bb.txt");

				  try {
				      // Read existing content
				      StringBuilder existingContent = new StringBuilder();
				      if (bbfile.exists()) {
				          try (BufferedReader reader = new BufferedReader(new FileReader(bbfile))) {
				              String line;
				              while ((line = reader.readLine()) != null) {
				                  existingContent.append(line).append("\n");
				              }
				          }
				      }

				      // Write new content followed by existing content
				      try (BufferedWriter writer = new BufferedWriter(new FileWriter(bbfile))) {
				          writer.write(enteredUserName + " has filled out an OT form for " + selectedUser + ", for the date of: " + dateString+"\n");
				          writer.newLine();
				          writer.write(existingContent.toString());
				          System.out.println("Bulletin board file updated successfully");
				      }

				  } catch (IOException ex) {
				      JOptionPane.showMessageDialog(null, "Issue updating the bulletin board.");
				      ex.printStackTrace();
				      return;
				  }
	       		//<-------------------------------End Update BB file--------------------------------------->
				  
				  //Add to monthly log
				  String yearString = datefield.getText();
				  System.out.println(yearString);
				  
				  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
			        
			        // Parse the string to obtain a LocalDate object
			        try {
			            LocalDate date = LocalDate.parse(yearString, formatter);
			            System.out.println("Parsed date: " + date);
			            
			            int year = date.getYear();
			            Month month = date.getMonth();
			            String monthString = month.toString();
			            
			            File monthlyOTFile = new File("Reports/overTimeReports/"+year+"/"+monthString+"/monthlyOTReport.txt");
			            
			            if (!monthlyOTFile.exists()) {
			                // If the file does not exist, create it
			                monthlyOTFile.getParentFile().mkdirs(); // Create parent directories if they do not exist
			                monthlyOTFile.createNewFile(); // Create the file
			            }
			            
			            // Write to the file without overwriting existing content
			            try (FileWriter writer = new FileWriter(monthlyOTFile, true)) {

			                writer.write("\n" +yearString + "\n" + selectedUser + ": Start:" + startTimeString + " End:" + endTimeString +"\n");
			            } catch (IOException e1) {
			                System.out.println("Failed to write to the monthly OT report file.");
			            }
			            
			            
			        } catch (DateTimeParseException e1) {
			            System.out.println("Failed to parse the date string.");
			        } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				  
				  
				  
				  
				  //end add to monthly log
				  
				  
	       			JOptionPane.showMessageDialog(null, "Your OT Request Has Been Submitted.");
	       			new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
	       			otFrame.dispose();
	       			
           	}
           });
           submitButton.setFont(new Font("Monospaced", Font.BOLD, 18));
           submitButton.setBackground(Color.LIGHT_GRAY);
           submitButton.setBounds(525, 464, 213, 59);
           panel.add(submitButton);
           
           JLabel forWhoPrompt = new JLabel("Filling out for:");
           forWhoPrompt.setForeground(Color.WHITE);
           forWhoPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
           forWhoPrompt.setBounds(10, 175, 240, 37);
           panel.add(forWhoPrompt);
           
           JRadioButton myselfRButton = new JRadioButton("Myself");
           myselfRButton.setForeground(Color.WHITE);
           myselfRButton.setFont(new Font("Monospaced", Font.BOLD, 20));
           myselfRButton.setBackground(Color.DARK_GRAY);
           myselfRButton.setBounds(6, 214, 107, 37);
           myselfRButton.setSelected(true);
           myselfRButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				comboBox.setVisible(false);
				x=0;
				System.out.println("Filling for self: x=" + x);
				
			}
		});
           panel.add(myselfRButton);
           
           JRadioButton otherRButton = new JRadioButton("Someone Else");
           otherRButton.setForeground(Color.WHITE);
           otherRButton.setFont(new Font("Monospaced", Font.BOLD, 20));
           otherRButton.setBackground(Color.DARK_GRAY);
           otherRButton.setBounds(115, 214, 198, 37);
   		   otherRButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				comboBox.setVisible(true);
				x=1;
				System.out.println("Filling for other: x=" + x);
			
			}
		});
		
           panel.add(otherRButton);
           
         group = new ButtonGroup();
  		 group.add(myselfRButton);
  		 group.add(otherRButton);
  		 
  		 
  		 //<----------------------------------------------------Begin Date Picker--------------------------------------------->
  		 
 		datebutton = new JButton("Calendar");
 		datebutton.setBounds(552, 211, 100, 23);
 		datebutton.addActionListener(this);
 		panel.add(datebutton);
 		
 		Datepicker = new Datepicker();
 		Datepicker.setUndecorated(true);
 		Datepicker.addPropertyChangeListener(this);
 		
		datefield = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.LONG));
		datefield.setBounds(392, 210, 150, 25);
		panel.add(datefield);
		
		JLabel lblSelectDate = new JLabel("Select Date:");
		lblSelectDate.setForeground(Color.WHITE);
		lblSelectDate.setFont(new Font("Monospaced", Font.BOLD, 20));
		lblSelectDate.setBounds(392, 175, 240, 37);
		panel.add(lblSelectDate);
		
		//<----------------------------------------------------END DATE PICKER-------------------------------------------------->
		
		JLabel startTimePrompt = new JLabel("Start Time:");
		startTimePrompt.setForeground(Color.WHITE);
		startTimePrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		startTimePrompt.setBounds(10, 312, 138, 37);
		panel.add(startTimePrompt);
		
		JLabel endTimePrompt = new JLabel("End Time:");
		endTimePrompt.setForeground(Color.WHITE);
		endTimePrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		endTimePrompt.setBounds(193, 312, 138, 37);
		panel.add(endTimePrompt);
		
		startTimeTextField = new JTextField("");
		startTimeTextField.setBounds(10, 360, 138, 37);
		panel.add(startTimeTextField);
		
		endTimeTextField = new JTextField("");
		endTimeTextField.setBounds(193, 360, 138, 37);
		panel.add(endTimeTextField);
		
	    comboBox = new JComboBox();
		comboBox.setBounds(27, 258, 240, 42);
		comboBox.setVisible(false);
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
		

		
		notesTextField = new JTextField("");
		notesTextField.setBounds(10, 448, 392, 37);
		panel.add(notesTextField);
		
		JLabel notesPrompt = new JLabel("Reason / Additional Info:");
		notesPrompt.setForeground(Color.WHITE);
		notesPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		notesPrompt.setBounds(10, 408, 318, 37);
		panel.add(notesPrompt);
		
		JButton recipientButton = new JButton("Email Roster");
		recipientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OTRecipients( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, otFrame);
				otFrame.setEnabled(false);
				
			}
		});
		recipientButton.setFont(new Font("Monospaced", Font.BOLD, 18));
		recipientButton.setBackground(Color.LIGHT_GRAY);
		recipientButton.setBounds(115, 534, 213, 59);
		panel.add(recipientButton);
  		 
  		 if(superAdminStatus==true)
  		 {
  			 recipientButton.setVisible(true);
  		 }
  		 else
  		 {
  			 recipientButton.setVisible(false);
  		 }
  		 
  		 
  		 
           
           
           otFrame.setVisible(true);
		
		
		
		
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
