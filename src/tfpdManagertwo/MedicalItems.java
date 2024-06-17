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
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class MedicalItems 
{
	
	JFrame medicalFrame;
	JPanel panel;
	private JTextField quantityTextField;
	private JTextField addItemTextField;
	File tempFile;
	String tempFileContent;
	
		MedicalItems(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail)
		{
			medicalFrame = new JFrame("Medical Item Requests");
			medicalFrame.setSize(911,791);
			medicalFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
			medicalFrame.setLocationRelativeTo(null);
			medicalFrame.setResizable(false);
			
			
			ImageIcon icon = new ImageIcon("TroyIcon.png");
			Image img = icon.getImage();
			medicalFrame.setIconImage(img);
			

			panel=new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			panel.setLayout(null);
			medicalFrame.getContentPane().add(panel);
			
			JLabel medicalLabelPrompt = new JLabel("Medical Supplies");
			medicalLabelPrompt.setForeground(Color.WHITE);
			medicalLabelPrompt.setFont(new Font("Monospaced", Font.BOLD, 30));
			medicalLabelPrompt.setBounds(10, 14, 357, 71);
			panel.add(medicalLabelPrompt);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(33, 156, 245, 49);
			panel.add(comboBox);
			
			//<------------------------------------------------------This is where medicalList.txt is read------------------------------------->
		       String filePath = "ItemLists/medicalitems.txt";
		       
		       try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			            String line;
			            while ((line = reader.readLine()) != null) {
			            	comboBox.addItem(line);
			            	
			            }
			            System.out.println("Medical Items List loaded successfully");
			        } catch (IOException e) {
			        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the Medical Items List.");
			        	System.out.println("An Error occurred retrieving the Medical Items List.");
			            e.printStackTrace();
			        }
				
				//<--------------------------------------------------------End reading of medicalList.txt------------------------------------------->
			
				JLabel selectItemPrompt = new JLabel("Select Item:");
				selectItemPrompt.setForeground(Color.WHITE);
				selectItemPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
				selectItemPrompt.setBounds(82, 96, 174, 49);
				panel.add(selectItemPrompt);
				
				JList list = new JList();
				list.setBounds(532, 156, 322, 444);
				panel.add(list);
				
			       DefaultListModel<String> itemListModel = new DefaultListModel<>();
			       list.setModel(itemListModel); 
				
		       
				quantityTextField = new JTextField();
				quantityTextField.setBounds(322, 154, 160, 49);
				panel.add(quantityTextField);
				quantityTextField.setColumns(10);
				
				JLabel lblQuantityDescription = new JLabel("Quantity Description:");
				lblQuantityDescription.setForeground(Color.WHITE);
				lblQuantityDescription.setFont(new Font("Monospaced", Font.BOLD, 16));
				lblQuantityDescription.setBounds(303, 98, 227, 49);
				panel.add(lblQuantityDescription);
				
				JLabel shoppingCartPrompt = new JLabel("Shopping Cart:");
				shoppingCartPrompt.setForeground(Color.WHITE);
				shoppingCartPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
				shoppingCartPrompt.setBounds(603, 96, 203, 49);
				panel.add(shoppingCartPrompt);
				
				JTextArea notesTextArea = new JTextArea();
				notesTextArea.setBounds(33, 305, 459, 83);
				notesTextArea.setWrapStyleWord(true);
				notesTextArea.setLineWrap(true);
				panel.add(notesTextArea);
				
				JLabel additionalNotesPrompt = new JLabel("Additional Notes: (optional)");
				additionalNotesPrompt.setForeground(Color.WHITE);
				additionalNotesPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
				additionalNotesPrompt.setBounds(33, 253, 449, 49);
				panel.add(additionalNotesPrompt);
				
				JLabel dontSeePrompt = new JLabel("Dont see an item? Add it to the item list here:");
				dontSeePrompt.setForeground(Color.WHITE);
				dontSeePrompt.setFont(new Font("Monospaced", Font.BOLD, 16));
				dontSeePrompt.setBounds(28, 465, 474, 49);
				panel.add(dontSeePrompt);
				
				JLabel warningPrompt = new JLabel("WARNING: This will reset the page, clearing your cart.");
				warningPrompt.setForeground(Color.WHITE);
				warningPrompt.setFont(new Font("Monospaced", Font.BOLD, 12));
				warningPrompt.setBounds(38, 498, 459, 37);
				panel.add(warningPrompt);
				
				addItemTextField = new JTextField();
				addItemTextField.setBounds(33, 525, 459, 37);
				panel.add(addItemTextField);
				addItemTextField.setColumns(10);
				
				
				JButton addNewItemButton = new JButton("Add Item To Item List");
				addNewItemButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String newItemToAdd = addItemTextField.getText().trim();
						if(newItemToAdd.equals(""))
						{
							JOptionPane.showMessageDialog(null, "Invalid item entry, please enter a vaild item to be added to the list.");
						}
						else 
						{
							    boolean itemExists = false;

							    for (int i = 0; i < comboBox.getItemCount(); i++) {
							        if (newItemToAdd.equals(comboBox.getItemAt(i))) {
							            itemExists = true;
							            break;
							        }
							    }

							    if (itemExists) 
							    {
							        JOptionPane.showMessageDialog(null, "The requested item is already in the available list.");
							    }
							    else 
							    {
							    	
					       			//<-------------------------------Update medical items file--------------------------------------->
					       			File cleaningItemsFile = new File("ItemLists/medicalitems.txt");
					       			
					       			try {
					       			    StringBuilder existingContent = new StringBuilder();
					       			    if (cleaningItemsFile.exists()) {
					       			        try (BufferedReader reader = new BufferedReader(new FileReader(cleaningItemsFile))) {
					       			            String line;
					       			            while ((line = reader.readLine()) != null) {
					       			                existingContent.append(line).append("\n");
					       			            }
					       			        }
					       			    }

					       			    try (BufferedWriter writer = new BufferedWriter(new FileWriter(cleaningItemsFile))) {
					       			     
					       			        writer.write(existingContent.toString());
					       			        writer.write(newItemToAdd);
					       			        System.out.println("Medical Items File updated successfully");
					       			    }
					       			} catch (IOException ex) {
					       			    JOptionPane.showMessageDialog(null, "Issue updating the Medical items file.");
					       			    ex.printStackTrace();
					       			}

					       			System.out.println("The selected item was added to the Medical item list:  " + newItemToAdd);
					       			JOptionPane.showMessageDialog(null, "Item was successfully added to the list, the page will now refresh.");
					       			medicalFrame.dispose();
					       			new MedicalItems(enteredUserName, enteredPassword, adminStatus, superAdminStatus, userEmail);
							        
							    }
						}
					}
				});
				
				addNewItemButton.setFont(new Font("Monospaced", Font.PLAIN, 11));
				addNewItemButton.setBounds(149, 573, 190, 43);
				panel.add(addNewItemButton);
				
				JButton addToCartButton = new JButton("Add to Shopping Cart");
				addToCartButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String getQuantity = quantityTextField.getText().trim();
						if(getQuantity.equals("") )
						{
							JOptionPane.showMessageDialog(null, "You must enter a quantity.");
						}
						else
						{
							 Object selectedItem = comboBox.getSelectedItem();
							if(selectedItem == null)
							{
								JOptionPane.showMessageDialog(null, "You must select an item.");
							}
							else
							{
								String itemSelectedString = selectedItem.toString();
								String notes = notesTextArea.getText().trim();
								if(notes.equals(""))
								{
									notes = "None";
								}
								
								tempFile = new File("ItemLists/tempmedicalfile.txt");
								String lineToWrite = getQuantity + " of " + selectedItem;
								try  (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true))) 
								{
									 writer.write(lineToWrite);
					                    writer.newLine(); // Add a newline for the next item
					                    System.out.println("Item added to temp file: " + lineToWrite);
								} 
								catch (IOException e1) {
									JOptionPane.showMessageDialog(null, "Issue writing to temp holding file. This will cause issues updating the bulletin board.");
									e1.printStackTrace();
								}
								
						        System.out.println(getQuantity +" of " + selectedItem + " were added to shopping cart.");
						        itemListModel.addElement("<html>" + getQuantity + " of " + selectedItem + "<br>Notes: " + notes + "</html>");
						        notesTextArea.setText("");
						        quantityTextField.setText("");
								
							}
							
						}
						
					}
				});
				addToCartButton.setFont(new Font("Monospaced", Font.PLAIN, 11));
				addToCartButton.setBounds(149, 399, 190, 43);
				panel.add(addToCartButton);
		
		
				JButton submitButton = new JButton("Submit");
				submitButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
		        	      //<-------------------------------------------CHECK EMAIL LIST VALIDITY------------------------------->
		        		String checkPath = "EmailLists/medicalemail.txt";

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
		        		
						
						ListModel<String> listModel = list.getModel();
						int itemCount = listModel.getSize();
						System.out.println("Current shopping list item count: " +itemCount);
						
						if (itemCount == 0)
						{
							JOptionPane.showMessageDialog(null, "You cannot submit an empty shopping cart.");
						}
						else
						{
							
							//<-------------------------------Begin emailing process------------------------------->
							ListModel<String> listModel1 = list.getModel();
							int itemCount1 = listModel1.getSize();

							// Initialize an empty StringBuilder to concatenate the strings
							StringBuilder concatenatedString = new StringBuilder();

							// Iterate through the elements in the JList
							for (int i = 0; i < itemCount1; i++) {
							    // Append each element to the StringBuilder
							    concatenatedString.append(listModel1.getElementAt(i));

							    // Append <br> unless it's the last element
							    if (i < itemCount1 - 1) {
							        concatenatedString.append("<br>");
							    }
							}

							// Get the final concatenated string
							String resultString = concatenatedString.toString();
							String locateUserNames = "EmailLists/medicalemail.txt"; 
							
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
												    String htmlContent = "<H2>"+ enteredUserName+ " has sent a medical supplies request from TFPD Manager.</H2>"
												            + "<p>The items requested are as follows: </p>"
												    		+ "<p>"+resultString+"</p>"
												    		+ "<p> This request was made on: " + currentDate+"</p>"
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

												    message.setSubject("Medical Supplies Request From "+ enteredUserName);
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
							
							 
							
							//<-------------------------------End Emailing process--------------------------------->
							
							

			       			//<-------------------------------Update BB file--------------------------------------->
			       			File bbfile = new File("bulletinboard/bb.txt");
			       			
			       			try 
			       			{
			       			    
			       			    StringBuilder existingContent = new StringBuilder();
			       			    if (bbfile.exists()) 
			       			    {
			       			        try (BufferedReader reader = new BufferedReader(new FileReader(bbfile)))
			       			        {
			       			            String line;
			       			            while ((line = reader.readLine()) != null) 
			       			            {
			       			                existingContent.append(line).append("\n");
			       			            }
			       			        }
			       			    }
			       			   
			       			    
			       			 try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) 
			       			 {
			       			    StringBuilder content = new StringBuilder();
			       			    String line;

			       			    while ((line = reader.readLine()) != null) 
			       			    {
			       			        content.append(line).append(System.lineSeparator());
			       			    }

			       			    
			       			     tempFileContent = content.toString();

			       			    
			       			    System.out.println("Content of tempFile:\n" + tempFileContent);
			       			} 
			       			 catch (IOException ex) 
			       			 {
			       			    ex.printStackTrace();
			       			 }
			       			 	Date todayDate = new Date();
			       			    String newContent = "\n" + enteredUserName + " requested the following medical supplies:\n" + tempFileContent+ "on " + todayDate +"\n";
			       			    String combinedContent = newContent + existingContent.toString();

			       			   
			       			    try (BufferedWriter writer = new BufferedWriter(new FileWriter(bbfile))) 
			       			    {
			       			        writer.write(combinedContent);
			       			        System.out.println("Bulletin board file updated successfully");
			       			        
				       			     try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(tempFile))) 
				       			     {
				       			      
				       			     } 
				       			     catch (IOException e1) 
				       			     {
				       			    	 System.out.println("Issue resetting the temp file.");
				       			      e1.printStackTrace();
				       			     }
			       			    }
			       			    
			       			}
			       			catch (IOException ex) 
			       			{
			       			    JOptionPane.showMessageDialog(null, "Issue updating the bulletin board.");
			       			    ex.printStackTrace();
			       			}
			       		//<-------------------------------End Update BB file--------------------------------------->
			       			JOptionPane.showMessageDialog(null, "Your request is being processed if no earlier error messages were received.");
			       			new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
			       			medicalFrame.dispose();
			       			
			       		 
						}
					}
				});
				submitButton.setFont(new Font("Monospaced", Font.BOLD, 20));
				submitButton.setBounds(480, 700, 190, 43);
				panel.add(submitButton);
				
				
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
						medicalFrame.dispose();
					}
				});
				cancelButton.setFont(new Font("Monospaced", Font.BOLD, 20));
				cancelButton.setBounds(697, 700, 190, 43);
				panel.add(cancelButton);
				
				
				JButton emailRecipients = new JButton("Email Recipients");
				emailRecipients.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						new MedicalRecipients( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, medicalFrame);
						medicalFrame.setEnabled(false);
					}
				});
				emailRecipients.setFont(new Font("Monospaced", Font.PLAIN, 11));
				emailRecipients.setBounds(697, 11, 190, 43);
				if(superAdminStatus == true)
				{
					emailRecipients.setVisible(true);
				}
				else
				{
					emailRecipients.setVisible(false);
				}
				panel.add(emailRecipients);
				
				
				
				JButton removeFronCartButton = new JButton("Remove Selected From Cart");
				removeFronCartButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						int selectedIndex = list.getSelectedIndex();
			       		if(selectedIndex!= -1)
			       		{
			       			itemListModel.remove(selectedIndex);
			       			System.out.println("Item removed from shopping cart List.");
			       		}
			       		else
			       		{
			       			JOptionPane.showMessageDialog(null,"You must select an item in the cart to remove.");
			       		}
					}
				});
				removeFronCartButton.setFont(new Font("Monospaced", Font.PLAIN, 11));
				removeFronCartButton.setBounds(590, 605, 203, 43);
				panel.add(removeFronCartButton);
				
				
				
				
				
				
				
				
				
				medicalFrame.setVisible(true);
			
		}
}
