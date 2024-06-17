package tfpdManagertwo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;

public class CallOffRecipients {
	
	
	
	JFrame callOffRForm;
	JPanel panel;
	JList list;
	
	CallOffRecipients(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame callOffFrame)
	{
		
		
		callOffRForm = new JFrame("Call Off Notification Recipients");
		callOffRForm.setSize(538,940);
		callOffRForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		callOffRForm.setLocationRelativeTo(null);
		callOffRForm.setResizable(false);
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		callOffRForm.setIconImage(img);
		
		panel=new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);
		callOffRForm.getContentPane().add(panel);
		
		JLabel availablePrompt = new JLabel("Available Recipients:");
		availablePrompt.setForeground(Color.WHITE);
		availablePrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		availablePrompt.setBounds(124, 53, 266, 67);
		panel.add(availablePrompt);
		
		JLabel currentRecipientsPrompt = new JLabel("Current Recipients:");
		currentRecipientsPrompt.setForeground(Color.WHITE);
		currentRecipientsPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		currentRecipientsPrompt.setBounds(145, 270, 266, 67);
		panel.add(currentRecipientsPrompt);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(124, 123, 255, 51);
		panel.add(comboBox);
		
		//<------------------------------------------------------This is where FTROSTER.txt is read------------------------------------->
	       String filePath = "FTRoster/FTRoster.txt";
	       
	       try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
	       {
		            String line;
		            while ((line = reader.readLine()) != null) 
		            {
		            	comboBox.addItem(line);
		            	
		            }
		            System.out.println("FT Roster for Call Off Notification email list loaded successfully");
	        }
	       catch (IOException e) 
	       {
		        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the FT Roster for Call Off notification.");
		        	System.out.println("An Error occurred retrieving the FT Roster for Call Off Notification List.");
		            e.printStackTrace();
	       }
			
			//<--------------------------------------------------------End reading of FTROSTER.txt------------------------------------------->
		
			list = new JList();
			list.setBounds(100, 342, 311, 381);
			panel.add(list);
			
			//<------------------------------------------------------------Populate Roster List-------------------------------------------------->
			
			DefaultListModel<String> listModel = new DefaultListModel<>();
			String emailFilePath = "EmailLists/calloffemail.txt";

			try (BufferedReader reader = new BufferedReader(new FileReader(emailFilePath))) {
			    String line;
			    while ((line = reader.readLine()) != null) {
			        listModel.addElement(line);
			    }
			    System.out.println("Call Off Notificaiton email list loaded successfully");
			} catch (IOException e) {
			    JOptionPane.showMessageDialog(null, "An error occurred retrieving the Call Off Notification email list.");
			    System.out.println("An error occurred retrieving the Call Off Notification email list.");
			    e.printStackTrace();
			}

			list.setModel(listModel);

			

			//<------------------------------------------------------------end Populate Roster List---------------------------------------------->
		
			JButton addToListButton = new JButton("Add To List");
			addToListButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					 Object selectedItem = comboBox.getSelectedItem();
					 
					 
						if(selectedItem == null)
						{
							JOptionPane.showMessageDialog(null, "You must select a user to add to the email list.");
						}
						
						else 
						{
							String selectedUserName = comboBox.getSelectedItem().toString();
						    if (listModel.contains(selectedUserName)) 
						    {
						        JOptionPane.showMessageDialog(null, "The selected user is already in the email list.");
						    } 
						    else 
						    {
						        System.out.println("The selected user added to the email list:  " + selectedUserName);
						        listModel.addElement(selectedUserName);
						    }
						
						
						}
				}
			});
			addToListButton.setFont(new Font("Monospaced", Font.BOLD, 20));
			addToListButton.setBounds(124, 195, 255, 51);
			panel.add(addToListButton);
			
			
			JButton removeFromListButton = new JButton("Remove From List");
			removeFromListButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        int selectedIndex = list.getSelectedIndex();
			        ListModel<String> model = list.getModel();

			        if (model.getSize() > 0 && selectedIndex != -1) 
			        {
			            DefaultListModel<String> listModel = (DefaultListModel<String>) model;
			            listModel.remove(selectedIndex);

			           
			            list.revalidate();
			            list.repaint();

			            System.out.println("User removed from Email List");
			        }
			        else 
			        {
			            JOptionPane.showMessageDialog(null, "You must select a user to remove.");
			        }
			    }
			});
			removeFromListButton.setFont(new Font("Monospaced", Font.BOLD, 20));
			removeFromListButton.setBounds(124, 734, 266, 51);
			panel.add(removeFromListButton);
			
			JButton saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ListModel<String> model = list.getModel();
			        int size = model.getSize();
			        
			        StringBuilder content = new StringBuilder();
			        
			        for (int i = 0; i < size; i++)
			        {
			            content.append(model.getElementAt(i)).append("\n");
			        }
			        
			        try {
			            Files.write(Paths.get(emailFilePath), content.toString().getBytes());
			            System.out.println("Email list saved successfully");
			        } 
			        catch (IOException ex) 
			        {
			            JOptionPane.showMessageDialog(null, "An error occurred while saving the email list.");
			            System.out.println("An error occurred while saving the email list.");
			            ex.printStackTrace();
			        }
			        callOffFrame.setEnabled(true);
			        callOffRForm.dispose();
				}
			});
			saveButton.setFont(new Font("Monospaced", Font.BOLD, 20));
			saveButton.setBounds(61, 841, 161, 51);
			panel.add(saveButton);
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					callOffFrame.setEnabled(true);
					callOffRForm.dispose();
				}
			});
			cancelButton.setFont(new Font("Monospaced", Font.BOLD, 20));
			cancelButton.setBounds(304, 841, 161, 51);
			panel.add(cancelButton);
			
			callOffRForm.setVisible(true);
			
			
			
			
			
		
		
		
	}

}
