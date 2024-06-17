package tfpdManagertwo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class EditHospitalRoster {
	
	JFrame editHospitalFrame;
	JPanel panel;
	JList list;
	JButton saveButton, deleteButton,addVehicleButton;
	JLabel addNewPrompt;
	JTextField textField;
	
	
	
	EditHospitalRoster(JFrame addRunFrame, String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame dailyLogFrame, DefaultListModel<String> runsListModel)
	{
		editHospitalFrame = new JFrame("Edit Hospital Roster");
		editHospitalFrame.setSize(538,785);
		editHospitalFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		editHospitalFrame.setLocationRelativeTo(null);
		editHospitalFrame.setResizable(false);
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		editHospitalFrame.setIconImage(img);
		
		panel=new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);
		editHospitalFrame.getContentPane().add(panel);
		
		JLabel currentRecipientsPrompt = new JLabel("Current Hospitals:");
		currentRecipientsPrompt.setForeground(Color.WHITE);
		currentRecipientsPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		currentRecipientsPrompt.setBounds(143, 11, 266, 67);
		panel.add(currentRecipientsPrompt);
		
		list = new JList();
		list.setBounds(98, 62, 311, 381);
		panel.add(list);
		
		//<------------------------------------------------------------Populate hospital Roster List-------------------------------------------------->
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
		String emailFilePath = "Hospitals/hospitalroster.txt";

		try (BufferedReader reader = new BufferedReader(new FileReader(emailFilePath))) {
		    String line;
		    while ((line = reader.readLine()) != null) {
		        listModel.addElement(line);
		    }
		    System.out.println("Hospital roster list loaded successfully");
		} catch (IOException e) {
		    JOptionPane.showMessageDialog(null, "An error occurred retrieving the hospital roster list.");
		    System.out.println("An error occurred retrieving the hospital roster list.");
		    e.printStackTrace();
		}

		list.setModel(listModel);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRunFrame.setEnabled(true);
				editHospitalFrame.dispose();
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		cancelButton.setBounds(353, 686, 161, 51);
		panel.add(cancelButton);
		
		saveButton = new JButton("Save");
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
		            System.out.println("Hospital Roster list saved successfully");
		        } 
		        catch (IOException ex) 
		        {
		            JOptionPane.showMessageDialog(null, "An error occurred while saving the Hospital roster list.");
		            System.out.println("An error occurred while saving the Hospital roster list.");
		            ex.printStackTrace();
		        }
		        addRunFrame.setEnabled(true);
		       
		        editHospitalFrame.dispose();
			}
		});
		saveButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		saveButton.setBounds(353, 627, 161, 51);
		panel.add(saveButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = list.getSelectedIndex();
				if (selectedIndex == -1) 
				
				{
				    JOptionPane.showMessageDialog(null, "No item selected to delete.");
				}
				 else 
				 {
					    
					    listModel.remove(selectedIndex);
				 }
				
			}
		});
		deleteButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		deleteButton.setBounds(171, 454, 161, 51);
		panel.add(deleteButton);
		
		addNewPrompt = new JLabel("Add New Hospital:");
		addNewPrompt.setForeground(Color.WHITE);
		addNewPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		addNewPrompt.setBounds(10, 541, 266, 67);
		panel.add(addNewPrompt);
		
		textField = new JTextField();
		textField.setBounds(10, 588, 204, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		addVehicleButton = new JButton("+");
		addVehicleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newTruckString = textField.getText().trim();
				
				if(newTruckString.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Invalid new hospital entry.");
					return;
				}
				else
				{
					listModel.addElement(newTruckString);
					textField.setText("");
				}
			}
		});
		addVehicleButton.setFont(new Font("Monospaced", Font.BOLD, 30));
		addVehicleButton.setBounds(68, 624, 61, 51);
		panel.add(addVehicleButton);
		
		editHospitalFrame.setVisible(true);
		
		
		
	}

}
